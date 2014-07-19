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

import com.example.it3197_casestudy.model.Article;
import com.example.it3197_casestudy.ui_logic.ArticleSelectedFeedbackActivity;
import com.example.it3197_casestudy.ui_logic.FeedbackArticleActivity;
import com.example.it3197_casestudy.ui_logic.SubmitArticle;
import com.example.it3197_casestudy.util.Settings;

public class ArticleUpdateFeedbackStatus extends AsyncTask<Object, Object, Object> implements Settings{
	
	
	private ArticleSelectedFeedbackActivity activity;
	private Article article;
	private ProgressDialog dialog;

	public ArticleUpdateFeedbackStatus(ArticleSelectedFeedbackActivity activity, Article article) {
		this.activity = activity;
		this.article = article;
	}
	
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		dialog = ProgressDialog.show(activity, "Updating Article Status",
				"Updating...Please Wait", true);
	}

	@Override
	protected Object doInBackground(Object... params) {
		// TODO Auto-generated method stub
		return updateFeedbackArticleStatus();
	}

	
	@Override
	protected void onPostExecute(Object result) {
		// TODO Auto-generated method stub
		parseJSONResponse((String)result);
		
		//Intent intent = new Intent(activity, FeedbackArticleActivity.class);
		//activity.startActivity(intent);
	}
	
	public String updateFeedbackArticleStatus(){
		String responseBody= "";
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(API_URL + "ArticleUpdateFeedbackStatusServlet");
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		
		
		postParameters.add(new BasicNameValuePair("articleIDToUpdate", String.valueOf(article.getArticleID())));
		postParameters.add(new BasicNameValuePair("articleStatus", article.getApproved()));
		//postParameters.add(new BasicNameValuePair("title", article.getTitle()));
		//postParameters.add(new BasicNameValuePair("category", article.getCategory()));
		//postParameters.add(new BasicNameValuePair("content", article.getContent()));
		//postParameters.add(new BasicNameValuePair("address", article.getLocation()));
		//postParameters.add(new BasicNameValuePair("storingLat", String.valueOf(article.getDbLat())));
		//postParameters.add(new BasicNameValuePair("storingLon", String.valueOf(article.getDbLon())));
		
		
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
				Toast.makeText(activity, "Article Updated", Toast.LENGTH_LONG).show();
				//Intent intent = new Intent(activity, ArticleMainActivity.class);
				//activity.startActivity(intent);
				Intent intent = new Intent(activity, FeedbackArticleActivity.class);
				activity.startActivity(intent);
				activity.finish();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
