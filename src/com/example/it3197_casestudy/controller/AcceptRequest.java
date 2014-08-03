package com.example.it3197_casestudy.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.it3197_casestudy.model.RequestHobby;
import com.example.it3197_casestudy.util.Settings;

public class AcceptRequest extends AsyncTask<Object, Object, Object>
implements Settings {
	private Activity activity;
	private RequestHobby requestHobby;
	private ProgressDialog dialog;
	public  AcceptRequest(Activity activity, RequestHobby requestHobby){
		this.activity = activity;
		this.requestHobby = requestHobby;
	}
	@Override
	protected Object doInBackground(Object... params) {
		// TODO Auto-generated method stub
		return updateRequest();
	}
	@Override
	protected void onPostExecute(Object result) {
		parseJSONResponse((String)result);
		dialog.dismiss();
	}
	@Override
	protected void onPreExecute() {
		dialog = ProgressDialog.show(activity, "Accepting","Please wait....", true);
	}
	
	public String updateRequest(){
		String responseBody= "";
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(API_URL + "AcceptRequestServlet");
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("requestID", Integer.toString(requestHobby.getRequestID())));
		postParameters.add(new BasicNameValuePair("eventID", Integer.toString(requestHobby.getEventID())));
		postParameters.add(new BasicNameValuePair("hobbyID", Integer.toString(requestHobby.getHobbyID())));
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		postParameters.add(new BasicNameValuePair("dateStart", df.format(requestHobby.getRequestDateStart())));
		postParameters.add(new BasicNameValuePair("endDate", df.format(requestHobby.getRequestDateEnd())));
		postParameters.add(new BasicNameValuePair("groupname", requestHobby.getGroupname()));
		postParameters.add(new BasicNameValuePair("status", "accept"));
		try {
			httppost.setEntity(new UrlEncodedFormEntity(postParameters));
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			responseBody = httpclient.execute(httppost, responseHandler);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responseBody;
	}
	
	private void parseJSONResponse(String responseBody){
		JSONObject json;
		System.out.println(responseBody);
		try {
			json = new JSONObject(responseBody);
			boolean success = json.getBoolean("success");
			if(success){
				dialog.dismiss();
				Toast.makeText(activity, "Hobby Group Created", Toast.LENGTH_LONG).show();
				activity.finish();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
