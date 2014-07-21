package com.example.it3197_casestudy.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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
import android.widget.Button;

import com.example.it3197_casestudy.model.Riddle;
import com.example.it3197_casestudy.model.RiddleAnswer;
import com.example.it3197_casestudy.ui_logic.ViewRiddleActivity;
import com.example.it3197_casestudy.util.Settings;

public class RetrieveAllRiddleAnswer extends AsyncTask<Object, Object, Object> implements Settings  {
	private ViewRiddleActivity activity;
	private Riddle riddle; 
	private RiddleAnswer[] riddleAnsList;
	
	ProgressDialog dialog;
	
	private Button btn_riddleAns1, btn_riddleAns2, btn_riddleAns3, btn_riddleAns4;
	
	public RetrieveAllRiddleAnswer(ViewRiddleActivity activity, Riddle riddle, Button btn_riddleAns1, Button btn_riddleAns2, Button btn_riddleAns3, Button btn_riddleAns4) {
		this.activity = activity;
		this.riddle = riddle;
		this.btn_riddleAns1 = btn_riddleAns1;
		this.btn_riddleAns2 = btn_riddleAns2;
		this.btn_riddleAns3 = btn_riddleAns3;
		this.btn_riddleAns4 = btn_riddleAns4;
	}
	
	@Override
	protected Object doInBackground(Object... params) {
		return retrieveAllRiddleAnswer();
	}

	@Override
	protected void onPreExecute() {
		riddleAnsList = new RiddleAnswer[4];

		dialog = ProgressDialog.show(activity, null, "Retrieving...", true);
	}

	@Override
	protected void onPostExecute(Object result) {
		parseJSONResponse((String) result);
		Collections.shuffle(Arrays.asList(riddleAnsList));
		btn_riddleAns1.setText(riddleAnsList[0].getRiddleAnswer());
		btn_riddleAns2.setText(riddleAnsList[1].getRiddleAnswer());
		btn_riddleAns3.setText(riddleAnsList[2].getRiddleAnswer());
		btn_riddleAns4.setText(riddleAnsList[3].getRiddleAnswer());
		
		dialog.dismiss();
	}
	
	public void parseJSONResponse(String responseBody) {
		JSONArray dataArray;
		JSONObject jsonObj;
		RiddleAnswer riddleAns;
		
		try {
			jsonObj = new JSONObject(responseBody);
			dataArray = jsonObj.getJSONArray("riddleAnsList");
			
			for(int i = 0; i < dataArray.length(); i++) {
				JSONObject data = new JSONObject(dataArray.getString(i));
				riddleAnsList[i] = new RiddleAnswer();
				riddleAns = new RiddleAnswer();
				riddleAns.setRiddleAnswerID(data.getInt("riddleAnswerID"));
				riddleAns.setRiddle(riddle);
				riddleAns.setRiddleAnswer(data.getString("riddleAnswer"));
				riddleAns.setRiddleAnswerStatus(data.getString("riddleAnswerStatus"));
				riddleAnsList[i] = riddleAns;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			errorOnExecuting();
		}
	}
	
	public String retrieveAllRiddleAnswer() {
		String responseBody = "";
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(API_URL + "RetrieveAllRiddleAnswerServlet");
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		
		postParameters.add(new BasicNameValuePair("riddleID", Integer.toString(riddle.getRiddleID())));
		
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(postParameters));
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			responseBody = httpClient.execute(httpPost, responseHandler);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseBody;
	}
	
	private void errorOnExecuting() {
		this.cancel(true);
		new Handler(Looper.getMainLooper()).post(new Runnable() {
			@Override
			public void run() {
				dialog.dismiss();
				AlertDialog.Builder builder = new AlertDialog.Builder(activity);
				builder.setTitle("Error").setMessage("Unable to retrieve riddle answers. Please try again.");
				builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
				builder.create().show();
			}
		});
	}
}
