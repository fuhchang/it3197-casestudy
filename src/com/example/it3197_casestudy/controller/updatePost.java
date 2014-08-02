package com.example.it3197_casestudy.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.it3197_casestudy.model.HobbyPost;
import com.example.it3197_casestudy.ui_logic.UpdatePost;
import com.example.it3197_casestudy.ui_logic.ViewSingleHobby;
import com.example.it3197_casestudy.util.Settings;

public class updatePost extends AsyncTask<Object, Object, Object> implements Settings{
	private ProgressDialog dialog;
	private UpdatePost activity;
	private HobbyPost hp;
	private String grpName;
	private String admiNric;
	public updatePost(UpdatePost up, HobbyPost hp, String grpName, String adminNric){
		this.activity = up;
		this.hp = hp;
		this.grpName  = grpName;
		this.admiNric = adminNric;
	}
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		dialog = ProgressDialog.show(activity, "updating post",
				"updating", true);
	}

	@Override
	protected Object doInBackground(Object... arg0) {
		// TODO Auto-generated method stub
		return postUpdate();
	}

	@Override
	protected void onPostExecute(Object result) {
		// TODO Auto-generated method stub
		parseJSONResponse((String)result);
		Intent intent = new Intent(activity, ViewSingleHobby.class);
		intent.putExtra("grpName", hp.getGrpID());
		intent.putExtra("grpID", hp.getGrpID());
		intent.putExtra("adminNric", admiNric);
		intent.putExtra("userNric", hp.getPosterNric());
		activity.finish();
		activity.startActivity(intent);
		
	}
	
	
	public String postUpdate(){
		String responseBody= "";
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(API_URL + "UpdatPostServlet");
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		
		postParameters.add(new BasicNameValuePair("nric", hp.getPosterNric()));
		postParameters.add(new BasicNameValuePair("postID", Integer.toString(hp.getPostID())));
		postParameters.add(new BasicNameValuePair("postTitle", hp.getPostTitle()));
		postParameters.add(new BasicNameValuePair("content", hp.getContent()));
		postParameters.add(new BasicNameValuePair("postLat", Double.toString(hp.getLat())));
		postParameters.add(new BasicNameValuePair("postLng", Double.toString(hp.getLng())));
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
