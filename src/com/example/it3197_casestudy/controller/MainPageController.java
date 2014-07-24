package com.example.it3197_casestudy.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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
import android.location.Location;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.example.it3197_casestudy.model.Article;
import com.example.it3197_casestudy.model.Combined;
import com.example.it3197_casestudy.model.Event;
import com.example.it3197_casestudy.model.Hobby;
import com.example.it3197_casestudy.ui_logic.MainActivity;
import com.example.it3197_casestudy.ui_logic.MainPageAdapter;
import com.example.it3197_casestudy.ui_logic.SubmitArticle;
import com.example.it3197_casestudy.ui_logic.ViewAllEventsActivity;
import com.example.it3197_casestudy.ui_logic.ViewHobbiesMain;
import com.example.it3197_casestudy.util.Settings;

public class MainPageController extends AsyncTask<Object, Object, Object> implements Settings{
	
	private ArrayList<Hobby> hobbyList;
	private ArrayList<Event> eventList;
	private ArrayList<Article> articleList;
	private ArrayList<Combined> combinedList;
	private MainActivity activity;
	private ListView listView;

	MainPageAdapter mpa;
	
	private ProgressDialog dialog;
	
	
	public MainPageController(MainActivity activity, ListView listView){
		this.activity = activity;
		this.listView = listView;
	}
	
	
	@Override
	protected void onPreExecute() {
		eventList = new ArrayList<Event>();
		hobbyList=new ArrayList<Hobby>();	
		articleList = new ArrayList<Article>();
		combinedList = new ArrayList<Combined>();
		dialog = ProgressDialog.show(activity,
				"Loading", "Please wait...", true);
	}
	
	
	@Override
	protected Object doInBackground(Object... arg0) {
		// TODO Auto-generated method stub
		return retrieveAllLatest();
	}

	@Override
	protected void onPostExecute(Object result) {
		parseJSONResponse((String) result);

		Toast.makeText(activity, "EventList: " + String.valueOf(eventList.size()) , Toast.LENGTH_SHORT).show();
		Toast.makeText(activity, "ArtList: " + String.valueOf(articleList.size()) , Toast.LENGTH_SHORT).show();
		Toast.makeText(activity, "HobbyList: " + String.valueOf(hobbyList.size()) , Toast.LENGTH_SHORT).show();
		
		mpa = new MainPageAdapter(activity,articleList, eventList, hobbyList, combinedList);
		listView.setAdapter(mpa);
		listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				// TODO Auto-generated method stub
				
				
				if(pos == 0){
					//intent to event
					Intent eve = new Intent(activity, ViewAllEventsActivity.class);
					activity.startActivity(eve);
					//Toast.makeText(activity, "Link to Event", Toast.LENGTH_SHORT).show();
				}
				
				if(pos==1){
					//intent to hobby
					Intent hob = new Intent(activity, ViewHobbiesMain.class);
					activity.startActivity(hob);
					//Toast.makeText(activity, "Link to Hobby", Toast.LENGTH_SHORT).show();
				}
				
				if(pos ==2){
					//intent to article
					Intent art = new Intent(activity, SubmitArticle.class);
					activity.startActivity(art);
					//Toast.makeText(activity, "Link to Article", Toast.LENGTH_SHORT).show();
					
				}
			}
		});
		dialog.dismiss();
	}
	
	
	
	public String retrieveAllLatest() {
		String responseBody = "";
		// Instantiate an HttpClient
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(API_URL + "CombinedServletCS");
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
		JSONArray art_array;
		JSONArray event_array;
		JSONArray hobby_array;
		JSONObject artJson;
		JSONObject eventJson;
		JSONObject hobbyJson;
		Article article;
		Event event;
		Hobby hobby;
		//Combined combined = new Combined();
		try {
			artJson = new JSONObject(responseBody);
			System.out.println(responseBody);
			art_array = artJson.getJSONArray("artList");
			for (int i = 0; i < art_array.length(); i++) {
				JSONObject dataJob = new JSONObject(art_array.getString(i));
				article = new Article();
				article.setTitle(dataJob.getString("title"));
				article.setArticleDate(dataJob.getString("articleDate"));
				article.setContent(dataJob.getString("content"));
				articleList.add(article);
			}
			
			eventJson = new JSONObject(responseBody);
			System.out.println(responseBody);
			event_array = eventJson.getJSONArray("eventList");
			for(int i = 0 ; i< event_array.length(); i++){
				JSONObject dataJob = new JSONObject(event_array.getString(i));
				event = new Event();
				
				event.setEventName(dataJob.getString("eventName"));				
			

				
				eventList.add(event);
			}
			
			
			hobbyJson = new JSONObject(responseBody);
			System.out.println(responseBody);
			hobby_array =hobbyJson.getJSONArray("hobbyList");
			for(int i =0; i<hobby_array.length();i++){
				JSONObject dataJob = new JSONObject (hobby_array.getString(i));
				hobby = new Hobby();
				
				hobby.setGroupName(dataJob.getString("grpName"));

				
				
				hobbyList.add(hobby);
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
	            builder.setTitle("Error in retrieving");
	            builder.setMessage("Unable to retrieve, check your connection.");
	            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						//activity.finish();
					}
				});
	            builder.create().show();
	        }
	    });
	}
	
}