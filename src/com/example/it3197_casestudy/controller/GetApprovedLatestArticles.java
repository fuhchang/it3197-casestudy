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
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.example.it3197_casestudy.model.Article;
import com.example.it3197_casestudy.ui_logic.ArticleMainActivity;
import com.example.it3197_casestudy.ui_logic.ArticleSelectedActivityActivity;
import com.example.it3197_casestudy.ui_logic.SingleArticleActivity;
import com.example.it3197_casestudy.ui_logic.SubmitArticle;
import com.example.it3197_casestudy.util.Settings;

public class GetApprovedLatestArticles extends AsyncTask<Object, Object, Object> implements Settings{
	private ArrayList<Article> articleList;
	private ArticleMainActivity activity;
	private ListView artListView;
	SingleArticleActivity saa;

	private ProgressDialog dialog;
	
	public GetApprovedLatestArticles(ArticleMainActivity activity, ListView listView){
		this.activity = activity;
		this.artListView = listView;
	}
	
	@Override
	protected void onPreExecute() {
		articleList = new ArrayList<Article>(); 
		dialog = ProgressDialog.show(activity,
				"Retrieving Latest Articles", "Please wait...", true);
	}

	@Override
	protected String doInBackground(Object... arg0) {
		return retrieveApprovedLatestArticles();
	}

	@Override
	protected void onPostExecute(Object result) {
		parseJSONResponse((String) result);
		saa = new SingleArticleActivity(activity,articleList);
		artListView.setAdapter(saa);
		artListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				// TODO Auto-generated method stub
				Article a = new Article();
				
				Intent intent = new Intent(activity, ArticleSelectedActivityActivity.class);
				intent.putExtra("title", articleList.get(pos).getTitle());
				intent.putExtra("author", articleList.get(pos).getArticleUser());
				intent.putExtra("articleDate", articleList.get(pos).getArticleDate());
				intent.putExtra("content", articleList.get(pos).getContent());
				intent.putExtra("address", articleList.get(pos).getLocation());
				intent.putExtra("dbLat", articleList.get(pos).getDbLat());
				intent.putExtra("dbLon", articleList.get(pos).getDbLon());
				activity.startActivity(intent);
				
				//activity.finish();
			//	activity.startActivity(intent);
				//Toast.makeText(activity, Integer.toString(arg2), Toast.LENGTH_SHORT).show();
				
				//Toast.makeText(activity, articleList.get(arg2).getTitle(), Toast.LENGTH_SHORT).show();
			}
			
		});
		dialog.dismiss();
	}

	public String retrieveApprovedLatestArticles() {
		String responseBody = "";
		// Instantiate an HttpClient
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(API_URL + "DisplayArticleMainServletCS");
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
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
		Article article;
		try {
			json = new JSONObject(responseBody);
			System.out.println(responseBody);
			data_array = json.getJSONArray("artList");
			for (int i = 0; i < data_array.length(); i++) {
				JSONObject dataJob = new JSONObject(data_array.getString(i));
				//int active = dataJob.getInt("active");
				article = new Article();
				article.setArticleID(dataJob.getInt("articleID"));
				article.setTitle(dataJob.getString("title"));
				article.setContent(dataJob.getString("content"));
				//Date articleDate = sqlDateTimeFormatter.parse(dataJob.getString("articleDate"));
				//DateFormat df = new SimpleDateFormat("E, dd MMMM yyyy - hh:mm a");
				//String articleSubmittedDate = df.format(articleDate);
				article.setArticleDate(dataJob.getString("articleDate"));
				article.setCategory(dataJob.getString("category"));
				article.setLocation(dataJob.getString("location"));
				article.setUserNRIC(dataJob.getString("userNRIC"));
				article.setActive(1);
				article.setApproved(dataJob.getString("approved"));
				article.setDbLat(dataJob.getDouble("dbLat"));
				article.setDbLon(dataJob.getDouble("dbLon"));
				article.setArticleUser(dataJob.getString("articleUser"));
				
				
				articleList.add(article);
				//event.setEventID(dataJob.getInt("eventID"));
				//event.setEventName(dataJob.getString("eventName"));
				//event.setEventDateTimeFrom(sqlDateTimeFormatter.parse(dataJob.getString("eventDateTimeFrom")));
				//event.setEventDateTimeTo(sqlDateTimeFormatter.parse(dataJob.getString("eventDateTimeTo")));
				//System.out.println(event.getEventDateTimeFrom());
				//if(active == 1){
				//	eventArrList.add(event);
				//}
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
	            builder.setTitle("Error in retrieving articles ");
	            builder.setMessage("No articles are retrieved.");
	            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
					}
				});
	            builder.create().show();
	        }
	    });
	}
}