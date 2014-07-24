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
import android.support.v4.app.FragmentManager;
import android.view.MenuItem;

import com.example.it3197_casestudy.model.Event;
import com.example.it3197_casestudy.model.EventParticipants;
import com.example.it3197_casestudy.ui_logic.SelectNewEventAdminActivity;
import com.example.it3197_casestudy.ui_logic.ViewEventsDetailsFragment;
import com.example.it3197_casestudy.util.MySharedPreferences;
import com.example.it3197_casestudy.util.Settings;
import com.example.it3197_casestudy.util.ViewEventsAdapter;

public class GetEventParticipants extends AsyncTask<Object, Object, Object> implements Settings{
	private ViewEventsDetailsFragment activity;
	private ArrayList<EventParticipants> eventParticipantsArrList = new ArrayList<EventParticipants>();
	private int eventID;
	private ProgressDialog dialog;
	
	public GetEventParticipants(ViewEventsDetailsFragment activity, int eventID){
		this.activity = activity;
		this.eventID = eventID;
	}
	
	@Override
	protected void onPreExecute() { 
		dialog = ProgressDialog.show(activity.getActivity(),
				"Retrieving event participants", "Please wait...", true);
	}

	@Override
	protected String doInBackground(Object... arg0) {
		return retrieveEventParticipants();
	}

	@Override
	protected void onPostExecute(Object result) {
		parseJSONResponse((String) result);
		try{
			String nricList[] = new String[eventParticipantsArrList.size()];

			MySharedPreferences preferences = new MySharedPreferences(activity.getActivity());
			String nric = preferences.getPreferences("nric","");
			for(int i=0;i<eventParticipantsArrList.size();i++){
				nricList[i] = eventParticipantsArrList.get(i).getUserNRIC();
			}
			activity.setEventParticipantsArrList(eventParticipantsArrList);
			activity.setNricList(nricList);

			MenuItem menuItemJoin = activity.getMenuItemJoin();
			MenuItem menuItemUnjoin = activity.getMenuItemUnjoin();
			
			boolean joined = false;
			for(int i=0;i<eventParticipantsArrList.size();i++){
				if(nric.equals(eventParticipantsArrList.get(i).getUserNRIC())){
					joined = true;
					break;
				}
			}
			if(joined){
				menuItemJoin.setVisible(false);
			}
			else{
				menuItemUnjoin.setVisible(false);
			}
			
			if(!nric.equals(activity.getEvent().getEventAdminNRIC())){
				activity.getMenuItemUpdate().setVisible(false);
			}
			
		}
		catch(Exception e){
			errorOnExecuting();
			e.printStackTrace();
		}
		dialog.dismiss();
	}

	public String retrieveEventParticipants() {
		String responseBody = "";
		// Instantiate an HttpClient
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(API_URL + "retrieveEventParticipant");
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
		EventParticipants eventParticipants;
		try {
			json = new JSONObject(responseBody);
			System.out.println(responseBody);
			data_array = json.getJSONArray("eventParticipantsInfo");
			for (int i = 0; i < data_array.length(); i++) {
				JSONObject dataJob = new JSONObject(data_array.getString(i));
				eventParticipants = new EventParticipants();
				eventParticipants.setEventID(dataJob.getInt("eventID"));
				eventParticipants.setUserNRIC(dataJob.getString("userNRIC"));
				eventParticipants.setDateTimeJoined(sqlDateTimeFormatter.parse(dataJob.getString("dateTimeJoined")));
				eventParticipants.setCheckIn(dataJob.getInt("checkIn"));
				eventParticipantsArrList.add(eventParticipants);
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
	            AlertDialog.Builder builder = new AlertDialog.Builder(activity.getActivity());
	            builder.setTitle("Error in retrieving event participants.");
	            builder.setMessage("Unable to retrieve event participants. Please try again.");
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
