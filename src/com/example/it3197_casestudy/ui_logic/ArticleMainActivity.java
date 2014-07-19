package com.example.it3197_casestudy.ui_logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.R.layout;
import com.example.it3197_casestudy.R.menu;
import com.example.it3197_casestudy.controller.GetApprovedLatestArticles;
import com.example.it3197_casestudy.controller.GetApprovedLatestArticles2;
import com.example.it3197_casestudy.model.Article;
import com.google.android.gms.maps.model.LatLng;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ArticleMainActivity extends Activity {

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
		setContentView(R.layout.article_main);
		
		
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
		selectedDist = sp.getInt("distanceSelected", 0);
		//Toast.makeText(getApplicationContext(), "Refreshed: " + String.valueOf(selectedDist), Toast.LENGTH_LONG).show();
		
		getActionBar().setTitle("News");
		
		if(selectedDist>0){
			getActionBar().setTitle("News (" + selectedDist + "km)");
		}
		else if(selectedDist==0){
			getActionBar().setTitle("News");
		}
		
		//ActionBar bar = getActionBar();
		//bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
		
		
	//	TextView tv = (TextView) findViewById(R.id.tv);
		
		
	//	String txt = "<u>Latest News</u>";
		//imageUriTv.setText(Html.fromHtml(img));
		
	//	tv.setText(Html.fromHtml(txt));
		
		getMyCurrentLocation();
		
		
		
		
		
		list = (ListView) findViewById(R.id.articleListView);
		//list.setBackgroundColor(Color.WHITE);
		GetApprovedLatestArticles gala = new GetApprovedLatestArticles(this, list, lat, lon,selectedDist);
		gala.execute();
		
		
		
		
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.article_main, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
		Intent intent = new Intent(ArticleMainActivity.this, MainLinkPage.class);
		startActivity(intent);
		ArticleMainActivity.this.finish();
		
		super.onBackPressed();
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		
		int id = item.getItemId();
		
		
		if(id==R.id.backToMainLink){
			//Intent intent = new Intent(ArticleMainActivity.this, MainLinkPage.class);
			Intent intent = new Intent (ArticleMainActivity.this, FeedbackArticleActivity.class);
			startActivity(intent);
			ArticleMainActivity.this.finish();
		}
		
		if(id==R.id.submitArt){
			
			Intent intent = new Intent(ArticleMainActivity.this, SubmitArticle.class);
			startActivity(intent);
			//ArticleMainActivity.this.finish();
			
		}
		
		
		if(id==R.id.refresh){
			Intent intent = new Intent(ArticleMainActivity.this, ArticleMainActivity.class);
			startActivity(intent);
			ArticleMainActivity.this.finish();
		}
		if(id==R.id.showArticlesLocation){
			GetApprovedLatestArticles2 gala2 = new GetApprovedLatestArticles2(ArticleMainActivity.this, list, lat, lon,selectedDist);
    		gala2.execute();
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
			        		selectedDist = sp.getInt("distanceSelected", 0);
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
			                                    	
			                                    	SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ArticleMainActivity.this);
			                                		Editor edit = sp.edit();
			                             
			                                		edit.putInt("distanceSelected", selectedDist);
			                                		edit.commit();
			                                		
			                                    	
			                                		if(selectedDist>0){
			                                			getActionBar().setTitle("News (" + selectedDist + "km)");
			                                		}
			                                		else if(selectedDist==0){
			                                			getActionBar().setTitle("News");
			                                		}
			                                		
			                                		
			                                    //	Toast.makeText(getApplicationContext(), String.valueOf(selectedDist), Toast.LENGTH_LONG).show();
			                                    	GetApprovedLatestArticles gala = new GetApprovedLatestArticles(ArticleMainActivity.this, list, lat, lon,selectedDist);
			                                		gala.execute();
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
	         AlertDialog.Builder builder1 = new AlertDialog.Builder(ArticleMainActivity.this);
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
	    		  getActionBar().setTitle("News");
				}
	      }
	   }

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		
		menu.removeItem(R.id.backToMainLink);
		
			if (lat==0 && lon == 0){
				//menu.removeItem(R.id.backToMainLink);
				menu.removeItem(R.id.distanceSelection);
					
				menu.removeItem(R.id.showArticlesLocation);

			}
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}  
	
	
}
