package com.example.it3197_casestudy.ui_logic;

import java.util.List;
import java.util.Locale;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.R.id;
import com.example.it3197_casestudy.R.layout;
import com.example.it3197_casestudy.R.menu;
import com.example.it3197_casestudy.controller.ArticleUpdateFeedbackStatus;
import com.example.it3197_casestudy.model.Article;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class ArticleSelectedFeedbackActivity extends Activity {

	TextView titleTv, authorTv, articleDateTv, contentTv, addTv;
	double currentLat;
	double currentLon;
	double lat;
	double lon;
	
	String Address;
	String City;
	
	
	int articleIDForUpdate;
	
	Location location; 
    private boolean gps_enabled=false;
	private boolean network_enabled=false;
	LocationManager lm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_article_selected_feedback);
		
		getActionBar().setTitle("");
		
		titleTv =(TextView)findViewById(R.id.title);
		authorTv = (TextView)findViewById(R.id.author);
		articleDateTv = (TextView) findViewById(R.id.date);
		contentTv = (TextView)findViewById(R.id.content);
		
		Bundle extras = this.getIntent().getExtras();
		String title = extras.getString("title");
		String author = extras.getString("author");
		String date = extras.getString("articleDate");
		String content = extras.getString("content");
		String address = extras.getString("address");
		String articleId = String.valueOf(extras.getInt("articleID"));
		articleIDForUpdate = extras.getInt("articleID");
		
	//	Toast.makeText(getApplicationContext(), articleId, Toast.LENGTH_SHORT).show();
		
		titleTv.setText(title);
		authorTv.setText("Author: " + author);
		articleDateTv.setText(date);
		contentTv.setText(content);
		
		
		getMyCurrentLocation();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.article_selected_feedback, menu);
		return true;
	}

	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		
		int id = item.getItemId();
		
		   Bundle extras = this.getIntent().getExtras();
		   String address = extras.getString("address");
		   double dbLat = extras.getDouble("dbLat");
		   double dbLon = extras.getDouble("dbLon");
		   
		   
		   if(id==R.id.navigate){
			/*	Intent intent = new Intent(ArticleSelectedFeedbackActivity.this, ArticleLatestMoreDetailActivity.class);
				intent.putExtra("articleLoc", address);
				intent.putExtra("dbLat", dbLat);
				intent.putExtra("dbLon", dbLon);
				startActivity(intent);
				*/
			   
			   Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
			   Uri.parse("http://maps.google.com/maps?saddr= " + currentLat + ","  + currentLon + "&daddr=" + dbLat + "," + dbLon));
			   //Uri.parse("http://maps.google.com/maps?saddr=1.3792222033267854, 103.84977746969753&daddr=1.3728403867740486,103.84756732946926+to:1.3748461023871494, 103.8455932236343"));
			   startActivity(intent);
			}
		
		   if(id == R.id.confirm){
			  
			   
			   	Article article = new Article();
				article.setArticleID(articleIDForUpdate);
				article.setApproved("Confirmed");
				ArticleUpdateFeedbackStatus aufs = new ArticleUpdateFeedbackStatus(ArticleSelectedFeedbackActivity.this,article);
				aufs.execute();
			   
		   }
		   
		   if(id == R.id.discard){
			   Article article = new Article();
				article.setArticleID(articleIDForUpdate);
				article.setApproved("Discard");

				ArticleUpdateFeedbackStatus aufs = new ArticleUpdateFeedbackStatus(ArticleSelectedFeedbackActivity.this,article);
				aufs.execute();
			   
		   }
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//Intent intent = new Intent(ArticleSelectedActivityActivity.this, ArticleMainActivity.class);
		//startActivity(intent);
		Intent intent = new Intent(ArticleSelectedFeedbackActivity.this, FeedbackArticleActivity.class);
		startActivity(intent);
		ArticleSelectedFeedbackActivity.this.finish();
		super.onBackPressed();
	}  
	
	
	/** Check the type of GPS Provider available at that instance and  collect the location informations**/
	   void getMyCurrentLocation() {    
		   		   
		   
	       LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
	       try{
	       		gps_enabled=locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	       }catch(Exception ex){
	       	
	       }
	       try{
	       	network_enabled=locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
	       }catch(Exception ex){
	       	
	       }

	       if(gps_enabled){
	           location=locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

	       }          

	       
	       if(network_enabled && location==null)    {
	           location=locManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

	       }

		   if (location != null) {          
		   	//accLoc=location.getAccuracy();
		       lat = location.getLatitude();
			   lon= location.getLongitude();
		   } 
	   
	      try
	       {
	   	   //Getting address based on coordinates.
	       Geocoder geocoder;  
	       List<Address> addresses;
	       geocoder = new Geocoder(this, Locale.getDefault());
	       addresses = geocoder.getFromLocation(lat, lon, 1);
	 
	        Address = addresses.get(0).getAddressLine(0);
	        City = addresses.get(0).getAddressLine(1);
	       }
	       catch (Exception e)
	       {
	           e.printStackTrace();
	       }

	      if (Address != null && !Address.isEmpty()) {
		   	   try
		   	      {
		   	  	   //Getting address based on coordinates.
		   	      Geocoder geocoder;  
		   	      List<Address> addresses;
		   	      geocoder = new Geocoder(this, Locale.getDefault());
		   	      addresses = geocoder.getFromLocation(lat, lon, 1);
		   	
		   	       Address = addresses.get(0).getAddressLine(0);
		   	       City = addresses.get(0).getAddressLine(1);
		   	       
		   	       currentLat = lat;
		   	       currentLon = lon;
		   	      }
		   	      catch (Exception e)
		   	      {
		   	          e.printStackTrace();
		   	      }

	      }
	      else{ 
	         AlertDialog.Builder builder1 = new AlertDialog.Builder(ArticleSelectedFeedbackActivity.this);
	         builder1.setTitle("Service Unavailable");
	   		 builder1.setMessage("Unable to get your location, check if your GPS and Network are turned on.");
	   		 builder1.setCancelable(true);
	         builder1.setNegativeButton("OK",new DialogInterface.OnClickListener() {
	               public void onClick(DialogInterface dialog, int id) {
	            	   dialog.cancel();
	               }
	           });
	           AlertDialog alert11 = builder1.create();
	           alert11.show();
	           
	           lat=1.3667;
	           lon=103.8;    
	           currentLat = lat;
		   	   currentLon = lon;  
	      }
	      
	      

	      
	      
	      
	      
	   }
}
