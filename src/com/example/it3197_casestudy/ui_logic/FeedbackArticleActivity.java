package com.example.it3197_casestudy.ui_logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.R.layout;
import com.example.it3197_casestudy.R.menu;
import com.example.it3197_casestudy.controller.GetPendingFeedbackArticles;
import com.example.it3197_casestudy.controller.GetPendingFeedbackArticles2;
import com.example.it3197_casestudy.model.Article;
import com.google.android.gms.maps.model.LatLng;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;

public class FeedbackArticleActivity extends Activity {

	SingleArticleActivity saa;
	ListView list;
	ArrayList<Article> resultArray = new ArrayList<Article>();
	
	final Context context = this;
	
	double lat;
	double lon;
	String Address;
	String City;
	private static LatLng fromPosition = null;
    private static LatLng toPosition = null;

    Location location; 
    private boolean gps_enabled=false;
	private boolean network_enabled=false;
	LocationManager lm;
	
	int selectedDist=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_feedback_article);
		
		
		
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
		selectedDist = sp.getInt("officerDistanceSelected", 0);
		//Toast.makeText(getApplicationContext(), "Refreshed: " + String.valueOf(selectedDist), Toast.LENGTH_LONG).show();
		
		getActionBar().setTitle("Pending");
		
		if(selectedDist>0){
			getActionBar().setTitle("Pending (" + selectedDist + "km)");
		}
		else if(selectedDist==0){
			getActionBar().setTitle("Pending");
		}
		
		getMyCurrentLocation();

		list = (ListView) findViewById(R.id.articleListView);
		//list.setBackgroundColor(Color.WHITE);
		GetPendingFeedbackArticles gpfa = new GetPendingFeedbackArticles(this, list, lat, lon,selectedDist);
		gpfa.execute();
		
		
		
		
		
final SwipeRefreshLayout swipeView = (SwipeRefreshLayout) findViewById(R.id.swipe); 
		
		swipeView.setEnabled(false); 
		
		swipeView.setColorScheme(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
		swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				swipeView.setRefreshing(true);
				( new Handler()).postDelayed(new Runnable() {
					@Override
					public void run() { 						
						Intent intent = new Intent(FeedbackArticleActivity.this, FeedbackArticleActivity.class);
						startActivity(intent);
						FeedbackArticleActivity.this.finish();
						swipeView.setRefreshing(false);
						
						}
					}, 1500);
				
			}
		});
		
		
		list.setOnScrollListener(new AbsListView.OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int i) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				if (firstVisibleItem == 0)
					swipeView.setEnabled(true);
				else
					swipeView.setEnabled(false); 
			}
		} );
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.feedback_article, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		
		int id = item.getItemId();
		
		if(id==R.id.refresh){
			Intent intent = new Intent(FeedbackArticleActivity.this, FeedbackArticleActivity.class);
			startActivity(intent);
			FeedbackArticleActivity.this.finish();
		}
		
		if(id==R.id.showArticlesLocation){
			GetPendingFeedbackArticles2 gpfa2 = new GetPendingFeedbackArticles2(this, list, lat, lon,selectedDist);
			gpfa2.execute();
			//Toast.makeText(getApplicationContext(), "CANNOT", Toast.LENGTH_SHORT).show();
		}
		
		
		if(id==R.id.distanceSelection){
			

			// get prompts.xml view 
			                LayoutInflater layoutInflater = LayoutInflater.from(context); 
			                View promptView = layoutInflater.inflate(R.layout.article_prompts, null); 
			                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context); 
			                // set prompts.xml to be the layout file of the alertdialog builder 
			                alertDialogBuilder.setView(promptView); 
			                //final EditText input = (EditText) promptView.findViewById(R.id.userInput); 
			                
			                
			                 NumberPicker np = (NumberPicker) promptView.findViewById(R.id.numberPicker1);
			                String[] nums = new String[20];
			                for(int i=0; i<nums.length; i++)
			                       nums[i] = Integer.toString(i);

			                np.setMinValue(0);
			                np.setMaxValue(20);
			                np.setWrapSelectorWheel(false);
			                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
			        		selectedDist = sp.getInt("officerDistanceSelected", 0);
			        		np.setValue(selectedDist);
			        		//np.setDisplayedValues(nums);
			                //np.setValue(1);;
			                //np.clearFocus();
			                //np.setDisplayedValues(nums);
			                np.setOnValueChangedListener(new OnValueChangeListener() {
			        			
			            		@Override
			            		public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
			            			// TODO Auto-generated method stub
			            			
			            			selectedDist = newVal;
			            		}
			            	});

			                
			                
			                
			                
			                // setup a dialog window 
			                alertDialogBuilder 
			                
			                		.setCancelable(false).setPositiveButton("OK", new DialogInterface.OnClickListener() { 
			                                    public void onClick(DialogInterface dialog, int id) { 
			                                        // get user input and set it to result 
			                                        //editTextMainScreen.setText(input.getText()); 
			                                    	
			                                    	SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(FeedbackArticleActivity.this);
			                                		Editor edit = sp.edit();
			                             
			                                		edit.putInt("officerDistanceSelected", selectedDist);
			                                		edit.commit();
			                                		
			                                    	
			                                		if(selectedDist>0){
			                                			getActionBar().setTitle("Pending (" + selectedDist + "km)");
			                                		}
			                                		else if(selectedDist==0){
			                                			getActionBar().setTitle("Pending");
			                                		}
			                                		
			                                		
			                                    //	Toast.makeText(getApplicationContext(), String.valueOf(selectedDist), Toast.LENGTH_LONG).show();
			                                		GetPendingFeedbackArticles gpfa = new GetPendingFeedbackArticles(FeedbackArticleActivity.this, list, lat, lon,selectedDist);
			                                		gpfa.execute();
			                                    } 
			                                }) 
			                        .setNegativeButton("Cancel", 
			                                new DialogInterface.OnClickListener() { 
			                                    public void onClick(DialogInterface dialog, int id) { 
			                                        dialog.cancel(); 
			                                    } 
			                                }); 
			                // create an alert dialog 
			                AlertDialog alertD = alertDialogBuilder.create(); 
			                alertD.show();
			
		}
		
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
		Intent i = new Intent(FeedbackArticleActivity.this,LoginActivity.class);
		startActivity(i);
		FeedbackArticleActivity.this.finish();
		
		super.onBackPressed();
	}
	
	
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
		   	      }
		   	      catch (Exception e)
		   	      {
		   	          e.printStackTrace();
		   	      }
		   	   
	      }
	      else{ 
	         AlertDialog.Builder builder1 = new AlertDialog.Builder(FeedbackArticleActivity.this);
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
	    	 // Toast.makeText(getApplicationContext(), "Unable to get your location, check if your GPS and Network are turned on.", Toast.LENGTH_SHORT).show();
	    	  
	    	  if (lat==0 && lon == 0){
					//menu.removeItem(R.id.backToMainLink);
	    		  getActionBar().setTitle("Pending");
				}
	      }
	   }

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		if (lat==0 && lon == 0){
			//menu.removeItem(R.id.backToMainLink);
			menu.removeItem(R.id.distanceSelection);
				
			menu.removeItem(R.id.showArticlesLocation);

		}
		
		menu.removeItem(R.id.refresh);
		
		return super.onPrepareOptionsMenu(menu);
	}

}
