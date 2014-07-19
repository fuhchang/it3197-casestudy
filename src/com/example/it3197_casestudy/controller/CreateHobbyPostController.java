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

import com.example.it3197_casestudy.model.HobbyPost;
import com.example.it3197_casestudy.ui_logic.CreateHobbyPost;
import com.example.it3197_casestudy.ui_logic.ViewHobbiesMain;
import com.example.it3197_casestudy.util.Settings;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

public class CreateHobbyPostController extends
		AsyncTask<Object, Object, Object> implements Settings {
	private ProgressDialog dialog;
	private HobbyPost hobbypost;
	private CreateHobbyPost activity;

	public CreateHobbyPostController(CreateHobbyPost activity,
			HobbyPost hobbypost) {
		this.activity = activity;
		this.hobbypost = hobbypost;
	}

	@Override
	protected Object doInBackground(Object... arg0) {
		// TODO Auto-generated method stub
		return createPost();
	}

	@Override
	protected void onPostExecute(Object result) {
		// TODO Auto-generated method stub
		parseJSONResponse((String)result);
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		dialog = ProgressDialog.show(activity, "Creating Post", "Creating...Please Wait",
				true);

	}

	public String createPost() {
		String responseBody = "";
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(API_URL + "CreatePostServlet");
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();

		postParameters.add(new BasicNameValuePair("grpID", Integer
				.toString(hobbypost.getGrpID())));
		
		postParameters.add(new BasicNameValuePair("postTitle", hobbypost.getPostTitle()));
		postParameters.add(new BasicNameValuePair("postContent", hobbypost
				.getContent()));
		postParameters.add(new BasicNameValuePair("postLat", Double
				.toString(hobbypost.getLat())));
		postParameters.add(new BasicNameValuePair("postLng", Double
				.toString(hobbypost.getLng())));
		postParameters.add(new BasicNameValuePair("postLng", Double
				.toString(hobbypost.getLng())));
		
		postParameters.add(new BasicNameValuePair("posterNric", hobbypost.getPosterNric()));
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
				Toast.makeText(activity, "Post Created", Toast.LENGTH_LONG).show();
				activity.finish();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
