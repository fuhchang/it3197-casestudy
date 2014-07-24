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
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;

import com.example.it3197_casestudy.model.EventParticipants;
import com.example.it3197_casestudy.model.User;
import com.example.it3197_casestudy.ui_logic.SelectNewEventAdminActivity;
import com.example.it3197_casestudy.ui_logic.ViewEventsDetailsFragment;
import com.example.it3197_casestudy.util.Settings;

public class GetEventParticipantsInfo extends AsyncTask<Object, Object, Object> implements Settings{
	private SelectNewEventAdminActivity activity;
	private int eventID;
	private ArrayList<User> userArrList = new ArrayList<User>();
	private String[] nricList;
	private String userNRIC;
	private ProgressDialog dialog;
	
	public GetEventParticipantsInfo(SelectNewEventAdminActivity activity, int eventID, String[] nricList, String userNRIC){
		this.activity = activity;
		this.eventID = eventID;
		this.nricList = nricList;
		this.userNRIC = userNRIC;
	}
	
	@Override
	protected void onPreExecute() { 
		dialog = ProgressDialog.show(activity,
				"Retrieving event participants information", "Please wait...", true);
	}

	@Override
	protected String doInBackground(Object... arg0) {
		return retrieveEventParticipantsInfo();
	}

	@Override
	protected void onPostExecute(Object result) {
		parseJSONResponse((String) result);
		try{
			String nameList[] = new String[userArrList.size()];
			for(int i=0;i<userArrList.size();i++){
				nameList[i] = userArrList.get(i).getName();
			}
			activity.setUserArrList(userArrList);
	        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity,android.R.layout.simple_list_item_single_choice,nameList);
	        activity.getLvEventAdmin().setAdapter(adapter);
	        activity.getLvEventAdmin().setOnItemClickListener(new OnItemClickListener(){
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int position,
						long arg3) {
					// TODO Auto-generated method stub
					activity.setNewEventAdminNRIC(userArrList.get(position).getNric().toString());
					System.out.println(activity.getNewEventAdminNRIC());
				}
	        });
		}
		catch(Exception e){
			errorOnExecuting();
			e.printStackTrace();
		}
		dialog.dismiss();
	}

	public String retrieveEventParticipantsInfo() {
		String responseBody = "";
		// Instantiate an HttpClient
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(API_URL + "retrieveAllUsers");
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("eventID",String.valueOf(eventID)));
		// Instantiate a POST HTTP method
		try {
			httppost.setEntity(new UrlEncodedFormEntity(postParameters));
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			responseBody = httpclient.execute(httppost, responseHandler);
		} catch (Exception e) {
			errorOnExecuting();
			e.printStackTrace();
		}
		System.out.println(responseBody);
		return responseBody;
	}

	public void parseJSONResponse(String responseBody) {
		JSONArray data_array;
		JSONObject json;
		User user;
		try {
			json = new JSONObject(responseBody);
			System.out.println(responseBody);
			data_array = json.getJSONArray("userInfo");
			for(int index=0;index<nricList.length;index++){
				for (int i = 0; i < data_array.length(); i++) {
					JSONObject dataJob = new JSONObject(data_array.getString(i));
					if(nricList[index].equals(dataJob.getString("nric"))){
						user = new User();
						user.setNric(dataJob.getString("nric"));
						user.setName(dataJob.getString("name"));
						user.setType(dataJob.getString("type"));
						user.setPassword(dataJob.getString("password"));
						user.setContactNo(dataJob.getString("contactNo"));
						user.setAddress(dataJob.getString("address"));
						user.setEmail(dataJob.getString("email"));
						user.setActive(dataJob.getInt("active"));
						//user.setPoints(dataJob.getInt("points"));
						if(nricList[index].equals(userNRIC)){
							
						}
						else{
							userArrList.add(user);
						}
					}
				}
			}
		} catch (Exception e) {
			errorOnExecuting();
			e.printStackTrace();
		}
	}
	
	private void errorOnExecuting(){
		this.cancel(true);
		new Handler(Looper.getMainLooper()).post(new Runnable() {
	        public void run() {
	        	dialog.dismiss();
	            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
	            builder.setTitle("Error in retrieving event participants information.");
	            builder.setMessage("Unable to retrieve event participants information. Please try again.");
	            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
			            cancel(true);
					}
				});
	            builder.create().show();
	        }
	    });
	}
}
