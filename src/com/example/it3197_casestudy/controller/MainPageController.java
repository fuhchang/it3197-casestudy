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
import com.example.it3197_casestudy.model.Riddle;
import com.example.it3197_casestudy.model.User;
import com.example.it3197_casestudy.ui_logic.ArticleUserView;
import com.example.it3197_casestudy.ui_logic.MainActivity;
import com.example.it3197_casestudy.ui_logic.MainPageAdapter;
import com.example.it3197_casestudy.ui_logic.RiddleActivity;
import com.example.it3197_casestudy.ui_logic.SubmitArticle;
import com.example.it3197_casestudy.ui_logic.ViewAllEventsActivity;
import com.example.it3197_casestudy.ui_logic.ViewEventsActivity;
import com.example.it3197_casestudy.ui_logic.ViewHobbiesMain;
import com.example.it3197_casestudy.util.Settings;

public class MainPageController extends AsyncTask<Object, Object, Object> implements Settings{
	
	private ArrayList<Hobby> hobbyList;
	private ArrayList<Event> eventList;
	private ArrayList<Article> articleList;
	private ArrayList<Riddle> riddleList;
	private ArrayList<Combined> combinedList;
	private MainActivity activity;
	private ListView listView;

	MainPageAdapter mpa;
	User user;
	
	private ProgressDialog dialog;
	
	
	public MainPageController(MainActivity activity, ListView listView, User user){
		this.activity = activity;
		this.listView = listView;
		this.user = user;
	}
	
	
	@Override
	protected void onPreExecute() {
		eventList = new ArrayList<Event>();
		hobbyList = new ArrayList<Hobby>();	
		articleList = new ArrayList<Article>();
		riddleList = new ArrayList<Riddle>();
		combinedList = new ArrayList<Combined>();
		dialog = ProgressDialog.show(activity, "Loading", "Please wait...", true);
	}
	
	
	@Override
	protected Object doInBackground(Object... arg0) {
		// TODO Auto-generated method stub
		return retrieveAllLatest();
	}

	@Override
	protected void onPostExecute(Object result) {
		parseJSONResponse((String) result);

		/*Toast.makeText(activity, "EventList: " + String.valueOf(eventList.size()) , Toast.LENGTH_SHORT).show();
		Toast.makeText(activity, "ArtList: " + String.valueOf(articleList.size()) , Toast.LENGTH_SHORT).show();
		Toast.makeText(activity, "HobbyList: " + String.valueOf(hobbyList.size()) , Toast.LENGTH_SHORT).show();
		Toast.makeText(activity, "RiddleList: " + String.valueOf(riddleList.size()) , Toast.LENGTH_SHORT).show();
		*/
		mpa = new MainPageAdapter(activity,articleList, eventList, hobbyList, riddleList, combinedList);
		listView.setAdapter(mpa);
		listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int pos,
					long arg3) {
				// TODO Auto-generated method stub
				
				
				if(pos == 0){
					//intent to event
					Intent eve = new Intent(activity, ViewEventsActivity.class);
			        eve.putExtra("eventID", view.getId());
			        Event event = new Event();
			        for(int i=0;i<eventList.size();i++){
			        	if(eventList.get(i).getEventID() == view.getId()){
			        		event = eventList.get(i);
			        	}
			        }
					eve.putExtra("eventAdminNRIC", event.getEventAdminNRIC());
					eve.putExtra("eventName", event.getEventName());
					eve.putExtra("eventCategory", event.getEventCategory());
					eve.putExtra("eventDescription", event.getEventDescription());
					eve.putExtra("eventDateTimeFrom", sqlDateTimeFormatter.format(event.getEventDateTimeFrom()));
					eve.putExtra("eventDateTimeTo", sqlDateTimeFormatter.format(event.getEventDateTimeTo()));
					eve.putExtra("occurence", event.getOccurence());
					eve.putExtra("noOfParticipants", event.getNoOfParticipantsAllowed());
					eve.putExtra("active", event.getActive());

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
					Intent intent = new Intent(activity, ArticleUserView.class);
					intent.putExtra("title", articleList.get(0).getTitle());
					intent.putExtra("author", articleList.get(0).getArticleUser());
					intent.putExtra("articleDate", articleList.get(0).getArticleDate());
					intent.putExtra("content", articleList.get(0).getContent());
					intent.putExtra("address", articleList.get(0).getLocation());
					intent.putExtra("dbLat", articleList.get(0).getDbLat());
					intent.putExtra("dbLon", articleList.get(0).getDbLon());
					intent.putExtra("dist", articleList.get(0).getDist());
					intent.putExtra("fromMain", "YES");
					intent.putExtra("postID", articleList.get(0).getArticleFBPostID());
					activity.startActivity(intent);
					
					//Toast.makeText(activity, "Link to Article", Toast.LENGTH_SHORT).show();
					
				}
				if(pos == 3){
					// Intent to RiddleActivity
					Intent intent = new Intent(activity, RiddleActivity.class);
					intent.putExtra("user", user);
					activity.startActivity(intent);
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
		JSONArray riddle_array;
		JSONObject artJson;
		JSONObject eventJson;
		JSONObject hobbyJson;
		JSONObject riddleJson;
		Article article;
		Event event;
		Hobby hobby;
		Riddle riddle;
		//Combined combined = new Combined();
		try {
			artJson = new JSONObject(responseBody);
			System.out.println(responseBody);
			art_array = artJson.getJSONArray("artList");
			for (int i = 0; i < art_array.length(); i++) {
				JSONObject dataJob = new JSONObject(art_array.getString(i));
				article = new Article();
				article.setArticleID(dataJob.getInt("articleID"));
				article.setTitle(dataJob.getString("title"));
				article.setContent(dataJob.getString("content"));
				article.setArticleDate(dataJob.getString("articleDate"));
				article.setCategory(dataJob.getString("category"));
				article.setLocation(dataJob.getString("location"));
				article.setUserNRIC(dataJob.getString("userNRIC"));
				article.setActive(1);
				article.setApproved(dataJob.getString("approved"));
				article.setDbLat(dataJob.getDouble("dbLat"));
				article.setDbLon(dataJob.getDouble("dbLon"));
				article.setArticleUser(dataJob.getString("articleUser"));
				article.setArticleFBPostID(dataJob.getString("articleFBPostID"));
				
				
				articleList.add(article);
			}
			
			eventJson = new JSONObject(responseBody);
			System.out.println(responseBody);
			event_array = eventJson.getJSONArray("eventList");
			for(int i = 0 ; i< event_array.length(); i++){
				JSONObject dataJob = new JSONObject(event_array.getString(i));
				event = new Event();

				event.setEventID(dataJob.getInt("eventID"));
				event.setEventAdminNRIC(dataJob.getString("eventAdminNRIC"));
				event.setEventName(dataJob.getString("eventName"));
				event.setEventCategory(dataJob.getString("eventCategory"));
				event.setEventDescription(dataJob.getString("eventDescription"));
				event.setEventDateTimeFrom(sqlDateTimeFormatter.parse(dataJob.getString("eventDateTimeFrom")));
				event.setEventDateTimeTo(sqlDateTimeFormatter.parse(dataJob.getString("eventDateTimeTo")));
				event.setOccurence(dataJob.getString("occurence"));
				event.setNoOfParticipantsAllowed(dataJob.getInt("noOfParticipantsAllowed"));
				event.setActive(dataJob.getInt("active"));
				
				eventList.add(event);
			}
			
			
			hobbyJson = new JSONObject(responseBody);
			System.out.println(responseBody);
			hobby_array =hobbyJson.getJSONArray("hobbyList");
			for(int i =0; i<hobby_array.length();i++){
				JSONObject dataJob = new JSONObject (hobby_array.getString(i));
				hobby = new Hobby();
				
				hobby.setGroupName(dataJob.getString("grpName"));
				hobby.setDescription(dataJob.getString("grpDesc"));
				
				
				hobbyList.add(hobby);
			}
			
			riddleJson = new JSONObject(responseBody);
			System.out.println(responseBody);
			riddle_array =riddleJson.getJSONArray("riddleList");
			for(int i =0; i<riddle_array.length();i++){
				JSONObject data = new JSONObject (riddle_array.getString(i));
				riddle = new Riddle();
				riddle.setRiddleID(data.getInt("riddleID"));
				JSONObject userObj = data.getJSONObject("user");
				riddle.setUser(new User(userObj.getString("nric"), userObj.getString("name"), userObj.getString("type"), userObj.getString("password"), userObj.getString("contactNo"), userObj.getString("address"), userObj.getString("email"), userObj.getInt("active"), userObj.getInt("points")));
				riddle.setRiddleTitle(data.getString("riddleTitle"));
				riddle.setRiddleContent(data.getString("riddleContent"));
				riddle.setRiddleStatus(data.getString("riddleStatus"));
				riddle.setRiddlePoint(data.getInt("riddlePoint"));				
				riddleList.add(riddle);
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
