package com.example.it3197_casestudy.controller;

import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;

import org.apache.http.client.methods.HttpPost;
/*
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
*/
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.it3197_casestudy.model.Hobby;
import com.example.it3197_casestudy.ui_logic.CreateGroupActivityStep4;
import com.example.it3197_casestudy.ui_logic.ViewHobbiesMain;
import com.example.it3197_casestudy.util.Settings;

public class ImageUploader extends AsyncTask<Object, Object, Object> implements Settings {
	
	private CreateGroupActivityStep4 activity;
	private Hobby hobby;
	private ProgressDialog dialog;
	private Bitmap bm;
	private String imgPath;
	public ImageUploader(CreateGroupActivityStep4 activity, Hobby hobby, String imgPath){
		this.activity = activity;
		this.hobby = hobby;
		this.imgPath = imgPath;
	}
	@Override
	protected Object doInBackground(Object... arg0) {
		// TODO Auto-generated method stub
		return ImageUpload();
	}

	@Override
	protected void onPostExecute(Object result) {
		// TODO Auto-generated method stub
		parseJSONResponse((String) result);
		Intent intent = new Intent(activity, ViewHobbiesMain.class);
		intent.putExtra("nric", hobby.getAdminNric());
		activity.startActivity(intent);
		activity.finish();
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		dialog = ProgressDialog.show(activity, "Uploading Image",
				"Uploading....", true);
	}
	
	
	@SuppressWarnings("deprecation")
	public String ImageUpload(){
		HttpResponse response = null;
		 /*
         try {
        	
        	 bm = BitmapFactory.decodeFile(imgPath);
        	 ByteArrayOutputStream bos = new ByteArrayOutputStream();
     		bm.compress(CompressFormat.JPEG, 75, bos);
     		byte[] data = bos.toByteArray();
     		HttpClient httpClient = new DefaultHttpClient();
     		HttpPost postRequest = new HttpPost("http://192.168.1.2:80/CommunityOutreach/UploadImageServlet");
     		ByteArrayBody bab = new ByteArrayBody(data, "myimg.jpg");
     		MultipartEntity reqEntity = new MultipartEntity( HttpMultipartMode.BROWSER_COMPATIBLE);
     		reqEntity.addPart("uploaded", bab);
     		reqEntity.addPart("photoCaption", new StringBody("sfsdfsdf"));
     		postRequest.setEntity(reqEntity);
     		response = httpClient.execute(postRequest);
     		
              
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         */
		return response.toString();

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
