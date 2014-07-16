package com.example.it3197_casestudy.controller;

import java.util.ArrayList;


import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.it3197_casestudy.listview.HobbyListView;
import com.example.it3197_casestudy.model.Hobby;
import com.example.it3197_casestudy.ui_logic.Hobbies_All;
import com.example.it3197_casestudy.ui_logic.ViewSingleHobby;
import com.example.it3197_casestudy.util.Settings;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class GetAllHobbyGroup extends AsyncTask<Object, Object, Object>
		implements Settings {
	private ArrayList<Hobby> hobbyList;
	private Hobbies_All activity;
	private ProgressDialog dialog;
	HobbyListView hobbyListView;
	ListView allList;
	public GetAllHobbyGroup(Hobbies_All activity, ListView allList) {
		this.activity = activity;
		this.allList = allList;
	}

	@Override
	protected Object doInBackground(Object... params) {
		// TODO Auto-generated method stub
		return getAllHobby();
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		hobbyList = new ArrayList<Hobby>();
		dialog = ProgressDialog.show(activity.getActivity(),
				"Retrieving Hobby", "Please wait...", true);
	}

	@Override
	protected void onPostExecute(Object result) {
		parseJSONResponse((String) result);
		hobbyListView = new HobbyListView(activity.getActivity(), hobbyList);
		allList.setAdapter(hobbyListView);
		allList.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(activity.getActivity(), ViewSingleHobby.class);
				activity.startActivity(intent);
			}
			
		});
		dialog.dismiss();	
	}

	public String getAllHobby() {
		String responseBody = "";
		// Instantiate an HttpClient
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(API_URL + "GetAllHobbyServlet");
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		// Instantiate a POST HTTP method
		try {
			httppost.setEntity(new UrlEncodedFormEntity(postParameters));
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			responseBody = httpclient.execute(httppost, responseHandler);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(responseBody);
		return responseBody;

	}

	public void parseJSONResponse(String responseBody) {
		JSONArray data_array;
		JSONObject json;
		Hobby hobby;
		try {
			json = new JSONObject(responseBody);
			data_array = json.getJSONArray("hobbyList");
			for (int i = 0; i < data_array.length(); i++) {
				JSONObject dataJob = new JSONObject(data_array.getString(i));
				hobby = new Hobby();
				hobby.setGroupID(dataJob.getInt("grpID"));
				hobby.setGroupName(dataJob.getString("grpName"));
				hobby.setCategory(dataJob.getString("category"));
				hobby.setLat(dataJob.getInt("Lat"));
				hobby.setLng(dataJob.getInt("Lng"));
				hobbyList.add(hobby);
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorOnExecuting();
		}
		
	}

	private void errorOnExecuting() {
		this.cancel(true);
		new Handler(Looper.getMainLooper()).post(new Runnable() {
			public void run() {
				dialog.dismiss();
				AlertDialog.Builder builder = new AlertDialog.Builder(activity
						.getActivity());
				builder.setTitle("Error in retrieving hobby ");
				builder.setMessage("Unable to retrieve hobby. Please try again.");
				builder.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
							}
						});
				builder.create().show();
			}
		});
	}	
}
