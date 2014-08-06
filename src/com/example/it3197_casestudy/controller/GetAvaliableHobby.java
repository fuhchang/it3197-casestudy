package com.example.it3197_casestudy.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
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
import android.widget.Toast;
import com.example.it3197_casestudy.model.Hobby;
import com.example.it3197_casestudy.ui_logic.UpdatePost;
import com.example.it3197_casestudy.ui_logic.ViewSingleHobby;
import com.example.it3197_casestudy.util.HobbyListView;
import com.example.it3197_casestudy.util.Settings;
import com.example.it3197_casestudy.util.SwipeDismissListViewTouchListener;

public class GetAvaliableHobby extends AsyncTask<Object, Object, Object>implements Settings{
	
	private ProgressDialog dialog;
	private ListView allList;
	private Activity activity;
	private ArrayList<Hobby> hobbyList;
	private int eventID;
	
	public GetAvaliableHobby(Activity activity, ListView allList, int  eventID){
		this.activity = activity;
		this.allList = allList;
		this.eventID = eventID;
	}
	@Override
	protected Object doInBackground(Object... arg0) {
		// TODO Auto-generated method stub
		return getAllHobby();
	}
	@Override
	protected void onPostExecute(Object result) {
		parseJSONResponse((String) result);
		HobbyListView adapter = new HobbyListView(activity, hobbyList, dialog);
		allList.setAdapter(adapter);
		allList.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(activity, ViewSingleHobby.class);
				intent.putExtra("grpID", hobbyList.get(position).getGroupID());
				intent.putExtra("grpType", hobbyList.get(position).getCategory());
				intent.putExtra("grpName", hobbyList.get(position).getGroupName());
				intent.putExtra("grpContent", hobbyList.get(position).getDescription());
				intent.putExtra("Lat", hobbyList.get(position).getLat());
				intent.putExtra("Lng", hobbyList.get(position).getLng());
				intent.putExtra("adminNric", hobbyList.get(position).getAdminNric());
				intent.putExtra("member", "none");
				intent.putExtra("userNric", "fromRequest");
				activity.startActivity(intent);
			}
			
		});
		dialog.dismiss();
		SwipeDismissListViewTouchListener touchListener = new SwipeDismissListViewTouchListener(allList, new SwipeDismissListViewTouchListener.DismissCallbacks(){

			@Override
			public boolean canDismiss(int position) {
				// TODO Auto-generated method stub
				return true;
			}

			@Override
			public void onDismiss(ListView listView,
					int[] reverseSortedPositions) {
				for(int position : reverseSortedPositions) {
					final int tempt = position;
					AlertDialog Builder = new AlertDialog.Builder(activity).create();
					Builder.setTitle("Request for help");
					Builder.setMessage("do you need help?");
					
					Builder.setButton(AlertDialog.BUTTON_NEGATIVE, "Request", new DialogInterface.OnClickListener() {

					      public void onClick(DialogInterface dialog, int id) {
					    	  DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
					    	  Date date = new Date();
					    	  CreateRequest request = new CreateRequest(activity, eventID, hobbyList.get(tempt).getGroupID(), dateFormat.format(date), dateFormat.format(date),hobbyList.get(tempt).getGroupName());
					    	  request.execute();
					    } 
					   }); 
					Builder.setButton(AlertDialog.BUTTON_POSITIVE, "Cancel", new DialogInterface.OnClickListener() {

					      public void onClick(DialogInterface dialog, int id) {
					    	  
					    } }); 
					Builder.show();
				}
				
			}
			
		});
		allList.setOnTouchListener(touchListener);
		allList.setOnScrollListener(touchListener.makeScrollListener());
	}
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		hobbyList = new ArrayList<Hobby>();
		dialog = ProgressDialog.show(activity, "Retrieving hobby"," Please wait....", true);
	}
	
	public String getAllHobby() {
		String responseBody = "";
		// Instantiate an HttpClient
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(API_URL + "getAvaHobbyServlet");
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
				hobby.setDescription(dataJob.getString("grpDesc"));
				hobby.setAdminNric(dataJob.getString("adminNric"));
				//hobby.setGrpImg(dataJob.getString("photo"));
				hobby.setLat(dataJob.getDouble("Lat"));
				hobby.setLng(dataJob.getDouble("Lng"));
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
				AlertDialog.Builder builder = new AlertDialog.Builder(activity);
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
