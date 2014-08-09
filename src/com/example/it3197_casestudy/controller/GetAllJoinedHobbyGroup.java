package com.example.it3197_casestudy.controller;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.it3197_casestudy.model.Hobby;
import com.example.it3197_casestudy.ui_logic.Hobbies_All;
import com.example.it3197_casestudy.ui_logic.Hobbies_Joined;
import com.example.it3197_casestudy.ui_logic.ViewSingleHobby;
import com.example.it3197_casestudy.util.HobbyListView;
import com.example.it3197_casestudy.util.Settings;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;

public class GetAllJoinedHobbyGroup extends AsyncTask<Object, Object, Object>
implements Settings{
	private ArrayList<Hobby> allJoinedhobbyList;
	private ProgressDialog dialog;
	private Hobbies_Joined activity;
	HobbyListView hobbyListView;
	String nric;
	ListView allList;
	
	public GetAllJoinedHobbyGroup(Hobbies_Joined activity, ListView allList, String nric) {
		this.activity = activity;
		this.allList = allList;
		this.nric = nric;
	}
	@Override
	protected Object doInBackground(Object... params) {
		// TODO Auto-generated method stub
		return  getAllHobby();
	}

	@Override
	protected void onPostExecute(Object result) {
		// TODO Auto-generated method stub
		
		parseJSONResponse((String) result);
		hobbyListView = new HobbyListView(activity.getActivity(), allJoinedhobbyList, dialog);
		allList.setAdapter(hobbyListView);
		allList.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(activity.getActivity(), ViewSingleHobby.class);
				intent.putExtra("grpID", allJoinedhobbyList.get(position).getGroupID());
				intent.putExtra("grpType", allJoinedhobbyList.get(position).getCategory());
				intent.putExtra("grpName", allJoinedhobbyList.get(position).getGroupName());
				intent.putExtra("grpContent", allJoinedhobbyList.get(position).getDescription());
				intent.putExtra("Lat", allJoinedhobbyList.get(position).getLat());
				intent.putExtra("Lng", allJoinedhobbyList.get(position).getLng());
				intent.putExtra("adminNric", allJoinedhobbyList.get(position).getAdminNric());
				intent.putExtra("grpImg", allJoinedhobbyList.get(position).getGrpImg());
				intent.putExtra("fbID", allJoinedhobbyList.get(position).getHobbyFBPostID());
				intent.putExtra("member", "member");
				intent.putExtra("userNric", nric);
				activity.startActivity(intent);
			}
			
		});
		
	}

	@Override
	protected void onPreExecute() {
		
		allJoinedhobbyList = new ArrayList<Hobby>();
		allJoinedhobbyList.clear();
		dialog = ProgressDialog.show(activity.getActivity(),
				"Retrieving Hobby", "Please wait...", true);
	}
	
	public String getAllHobby() {
		String responseBody = "";
		// Instantiate an HttpClient
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(API_URL + "GetAllJoinedHobbyServlet");
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		// Instantiate a POST HTTP method
		postParameters.add(new BasicNameValuePair("nric", nric));
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
			data_array = json.getJSONArray("JoinedhobbyList");
			for (int i = 0; i < data_array.length(); i++) {
				JSONObject dataJob = new JSONObject(data_array.getString(i));
				hobby = new Hobby();
				hobby.setGroupID(dataJob.getInt("grpID"));
				hobby.setGroupName(dataJob.getString("grpName"));
				hobby.setCategory(dataJob.getString("category"));
				hobby.setDescription(dataJob.getString("grpDesc"));
				hobby.setAdminNric(dataJob.getString("adminNric"));
				hobby.setLat(dataJob.getDouble("Lat"));
				hobby.setLng(dataJob.getDouble("Lng"));
				hobby.setHobbyFBPostID(dataJob.getString("hobbyFBPostID"));
				allJoinedhobbyList.add(hobby);
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
				builder.setMessage("Unable to retrieve Hobby that you joined. Please try again.");
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
