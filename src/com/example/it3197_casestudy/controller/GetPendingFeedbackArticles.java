package com.example.it3197_casestudy.controller;

import java.util.ArrayList;

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

import com.example.it3197_casestudy.model.Article;
import com.example.it3197_casestudy.ui_logic.ArticleMainActivity;
import com.example.it3197_casestudy.ui_logic.ArticleSelectedActivityActivity;
import com.example.it3197_casestudy.ui_logic.ArticleSelectedFeedbackActivity;
import com.example.it3197_casestudy.ui_logic.FeedbackArticleActivity;
import com.example.it3197_casestudy.ui_logic.FeedbackListRow;
import com.example.it3197_casestudy.ui_logic.MainLinkPage;
import com.example.it3197_casestudy.ui_logic.SingleArticleActivity;
import com.example.it3197_casestudy.util.Settings;

public class GetPendingFeedbackArticles extends AsyncTask<Object, Object, Object> implements Settings{

	private ArrayList<Article> articleList;
	private FeedbackArticleActivity activity;
	private ListView artListView;
	FeedbackListRow flr;

    private ProgressDialog dialog;
    
    public GetPendingFeedbackArticles(FeedbackArticleActivity activity, ListView listView){
		this.activity = activity;
		this.artListView = listView;
	}
    
    
    

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		articleList = new ArrayList<Article>(); 
		dialog = ProgressDialog.show(activity, "Retrieving Pending Feebacks", "Please wait...", true);
	}




	@Override
	protected Object doInBackground(Object... arg0) {
		// TODO Auto-generated method stub
		return retrievePendingFeedbackArticles();
	}
	
	
	@Override
	protected void onPostExecute(Object result) {
		parseJSONResponse((String) result);
		flr = new FeedbackListRow(activity,articleList);
		artListView.setAdapter(flr);
		artListView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				// TODO Auto-generated method stub
				Article a = new Article();
				
				Intent intent = new Intent(activity, ArticleSelectedFeedbackActivity.class);
				intent.putExtra("articleID", articleList.get(pos).getArticleID());
				intent.putExtra("title", articleList.get(pos).getTitle());
				intent.putExtra("author", articleList.get(pos).getArticleUser());
				intent.putExtra("articleDate", articleList.get(pos).getArticleDate());
				intent.putExtra("content", articleList.get(pos).getContent());
				intent.putExtra("address", articleList.get(pos).getLocation());
				intent.putExtra("dbLat", articleList.get(pos).getDbLat());
				intent.putExtra("dbLon", articleList.get(pos).getDbLon());
				activity.startActivity(intent);
				activity.finish();
				
			}
			
		});
		dialog.dismiss();
	}
	
	
	public String retrievePendingFeedbackArticles() {
		String responseBody = "";
		// Instantiate an HttpClient
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(API_URL + "DisplayPendingFeedbackServletCS");
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
	            builder.setMessage("Unable to retrieve articles, check your connection.");
	            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
						//activity.finish();
						//Intent intent = new Intent(activity, MainLinkPage.class);
						//activity.startActivity(intent);
					}
				});
	            builder.create().show();
	        }
	    });
	}
}