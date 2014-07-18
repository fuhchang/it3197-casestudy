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

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.it3197_casestudy.model.Hobby;
import com.example.it3197_casestudy.ui_logic.EditHobbyGrp;
import com.example.it3197_casestudy.util.Settings;

public class UpdateHobbyGroup extends AsyncTask<Object, Object, Object>
implements Settings{
	private ProgressDialog dialog;
	private Hobby hobby;
	private EditHobbyGrp activity;
	
	public UpdateHobbyGroup(EditHobbyGrp editGrp, Hobby hobby){
		this.activity= editGrp;
		this.hobby = hobby;
	}
	@Override
	protected Object doInBackground(Object... arg0) {
		// TODO Auto-generated method stub
		return updateHobby();
	}

	@Override
	protected void onPostExecute(Object result) {
		// TODO Auto-generated method stub
		dialog.dismiss();
		Toast.makeText(activity, "Update successful", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		dialog = ProgressDialog.show(activity, "Updating Hobby Group",
				"Updattng..... Please wait", true);
	}
	
	public String updateHobby(){
		String responseBody= "";
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(API_URL + "UpdateHobbyServlet");
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("grpID", Integer.toString(hobby.getGroupID())));
		postParameters.add(new BasicNameValuePair("gtitle", hobby.getGroupName()));
		postParameters.add(new BasicNameValuePair("grpType", hobby.getCategory()));
		postParameters.add(new BasicNameValuePair("gDesc", hobby.getDescription()));
		postParameters.add(new BasicNameValuePair("gLat", Double.toString(hobby.getLat())));
		postParameters.add(new BasicNameValuePair("gLng", Double.toString(hobby.getLng())));
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
	
	
}
