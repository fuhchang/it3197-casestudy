package com.example.it3197_casestudy.ui_logic;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.controller.CheckInEvent;
import com.example.it3197_casestudy.controller.CreateEvent;
import com.example.it3197_casestudy.controller.GetAllEvents;
import com.example.it3197_casestudy.controller.GetEvent;
import com.example.it3197_casestudy.controller.GetEventParticipants;
import com.example.it3197_casestudy.controller.GetImageFromFacebook;
import com.example.it3197_casestudy.controller.JoinEvent;
import com.example.it3197_casestudy.controller.UnjoinEvent;
import com.example.it3197_casestudy.model.Event;
import com.example.it3197_casestudy.model.EventParticipants;
import com.example.it3197_casestudy.util.CheckNetworkConnection;
import com.example.it3197_casestudy.util.FriendPickerApplication;
import com.example.it3197_casestudy.util.MySharedPreferences;
import com.example.it3197_casestudy.util.Settings;
import com.facebook.AppEventsLogger;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.RequestBatch;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphObject;
import com.facebook.model.GraphUser;
import com.facebook.model.OpenGraphAction;
import com.facebook.model.OpenGraphObject;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;

/**
 * A fragment to ddisplay event details
 * 
 * @author Lee Zhuo Xun
 */
public class ViewEventsDetailsFragment extends Fragment implements Settings{
	private TextView tvEventID, tvEventName, tvEventCategory, tvEventDescription, tvEventDateTimeFrom,
					 tvEventDateTimeTo, tvEventOccur, tvEventNoOfParticipants;
	private ImageView ivEventPoster;
	private Button btnCheckIn;
	private Event event = new Event();
	private boolean joined;
	private String nric;
	private ArrayList<EventParticipants> eventParticipantsArrList = new ArrayList<EventParticipants>();
	private String nricList[] = new String[10];
	MenuItem menuItemJoin;
	MenuItem menuItemUnjoin;
	MenuItem menuItemUpdate;
	private String pictureURL;
	private UiLifecycleHelper uiHelper;

	ProgressDialog dialog;
	private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");
	private static final String PENDING_PUBLISH_KEY = "pendingPublishReauthorization";
	private boolean pendingPublishReauthorization = false;
	
	public ImageView getIvEventPoster() {
		return ivEventPoster;
	}

	public void setIvEventPoster(ImageView ivEventPoster) {
		this.ivEventPoster = ivEventPoster;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public ArrayList<EventParticipants> getEventParticipantsArrList() {
		return eventParticipantsArrList;
	}

	public void setEventParticipantsArrList(
			ArrayList<EventParticipants> eventParticipantsArrList) {
		this.eventParticipantsArrList = eventParticipantsArrList;
	}

	public String[] getNricList() {
		return nricList;
	}

	public void setNricList(String nricList[]) {
		this.nricList = nricList;
	}

	public MenuItem getMenuItemJoin() {
		return menuItemJoin;
	}

	public void setMenuItemJoin(MenuItem menuItemJoin) {
		this.menuItemJoin = menuItemJoin;
	}

	public MenuItem getMenuItemUnjoin() {
		return menuItemUnjoin;
	}

	public void setMenuItemUnjoin(MenuItem menuItemUnjoin) {
		this.menuItemUnjoin = menuItemUnjoin;
	}

	public String getNric() {
		return nric;
	}

	public void setNric(String nric) {
		this.nric = nric;
	}

	public MenuItem getMenuItemUpdate() {
		return menuItemUpdate;
	}

	public void setMenuItemUpdate(MenuItem menuItemUpdate) {
		this.menuItemUpdate = menuItemUpdate;
	}

	public ViewEventsDetailsFragment(){}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	    uiHelper = new UiLifecycleHelper(this.getActivity(), null);
	    uiHelper.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.view_events_details_fragment_menu, menu);
		menuItemJoin = menu.findItem(R.id.join);
		menuItemUnjoin = menu.findItem(R.id.unjoin);
		menuItemUpdate = menu.findItem(R.id.update);
		Bundle bundle = getArguments();
		joined = bundle.getBoolean("joined");
		System.out.println("Joined: " + joined);
		if(!joined){
			menuItemJoin.setVisible(true);
			menuItemUnjoin.setVisible(false);
		}
		else{
			menuItemJoin.setVisible(false);
			menuItemUnjoin.setVisible(true);
		}
		if(!nric.equals(event.getEventAdminNRIC())){
			menuItemUpdate.setVisible(false);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch(item.getItemId()){
		case R.id.update:
			if(!CheckNetworkConnection.haveNetworkConnection(ViewEventsDetailsFragment.this.getActivity())){
				AlertDialog.Builder builder = new AlertDialog.Builder(ViewEventsDetailsFragment.this.getActivity());
				builder.setTitle("You are in offline mode");
				builder.setMessage("Please check your internet connection and try again.");
				builder.setPositiveButton("OK", null);
				builder.create().show();
			}
			else{
				if(!nric.equals(event.getEventAdminNRIC())){
		            AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
		            builder.setTitle("Access denied");
		            builder.setMessage("You do not have the permissions to update the event");
		            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
				            
						}
					});
		            builder.create().show();
				}
				else{
					Intent i = new Intent(ViewEventsDetailsFragment.this.getActivity(), UpdateEventStep1Activity.class);
					i.putExtra("eventID", String.valueOf(event.getEventID()));
					i.putExtra("eventName", event.getEventName());
					i.putExtra("eventCategory", event.getEventCategory());
					i.putExtra("eventDescription", event.getEventDescription());
					i.putExtra("eventDateTimeFrom", sqlDateTimeFormatter.format(event.getEventDateTimeFrom()));
					i.putExtra("eventDateTimeTo", sqlDateTimeFormatter.format(event.getEventDateTimeTo()));
					i.putExtra("occurence", event.getOccurence());
					i.putExtra("noOfParticipants", String.valueOf(event.getNoOfParticipantsAllowed()));
					i.putExtra("pictureURL", pictureURL);
					startActivity(i);
					ViewEventsDetailsFragment.this.getActivity().finish();
				}
			}
			break;
		case R.id.join : 
			if(!CheckNetworkConnection.haveNetworkConnection(ViewEventsDetailsFragment.this.getActivity())){
				AlertDialog.Builder builder = new AlertDialog.Builder(ViewEventsDetailsFragment.this.getActivity());
				builder.setTitle("You are in offline mode");
				builder.setMessage("Please check your internet connection and try again.");
				builder.setPositiveButton("OK", null);
				builder.create().show();
			}
			else{
				Calendar todayDate = Calendar.getInstance();
				EventParticipants eventParticipants = new EventParticipants(event.getEventID(),nric,todayDate.getTime(),0);
				JoinEvent joinEvent = new JoinEvent(ViewEventsDetailsFragment.this,eventParticipants);
				joinEvent.execute();
			}
			break;
		case R.id.unjoin:
			if(!CheckNetworkConnection.haveNetworkConnection(ViewEventsDetailsFragment.this.getActivity())){
				AlertDialog.Builder builder = new AlertDialog.Builder(ViewEventsDetailsFragment.this.getActivity());
				builder.setTitle("You are in offline mode");
				builder.setMessage("Please check your internet connection and try again.");
				builder.setPositiveButton("OK", null);
				builder.create().show();
			}
			else{
				if((eventParticipantsArrList.size() >= 3) && (nric.equals(event.getEventAdminNRIC()))){
					Intent intent = new Intent(ViewEventsDetailsFragment.this.getActivity(), SelectNewEventAdminActivity.class);
					intent.putExtra("eventID", event.getEventID());
					intent.putExtra("nricList", nricList);
					intent.putExtra("userNRIC", nric);
					this.getActivity().startActivityFromFragment(ViewEventsDetailsFragment.this, intent, 1);
				}
				else{
					UnjoinEvent unjoinEvent = new UnjoinEvent(ViewEventsDetailsFragment.this,event.getEventID(), nric, "");
					unjoinEvent.execute();
				}
			}
			break;

		case R.id.share:
			//Archive this for you to pick friends
            /*Intent intent = new Intent(this.getActivity(), PickFriendsActivity.class);
            PickFriendsActivity.populateParameters(intent, "me", true, true);
            startActivityForResult(intent, 100);*/
			if(!CheckNetworkConnection.haveNetworkConnection(ViewEventsDetailsFragment.this.getActivity())){
				AlertDialog.Builder builder = new AlertDialog.Builder(ViewEventsDetailsFragment.this.getActivity());
				builder.setTitle("You are in offline mode");
				builder.setMessage("Please check your internet connection and try again.");
				builder.setPositiveButton("OK", null);
				builder.create().show();
			}
			else{
	            if (FacebookDialog.canPresentOpenGraphActionDialog(this.getActivity().getApplicationContext(), FacebookDialog.OpenGraphActionDialogFeature.OG_ACTION_DIALOG)) {
	            	publishStory();
	            }
	            else{
	            	Bundle postParams = new Bundle();
	    	        postParams.putString("title", event.getEventName());
	    	        postParams.putString("type", "community_outreach:event");
	    	        postParams.putString("description", event.getEventDescription());
	    	        postParams.putString("url", "localhost:8080/CommunityOutreach/");
	    	        postParams.putString("image", pictureURL);
	    	        postParams.putString("to", "947027648641504");
	                WebDialog feedDialog = new WebDialog.FeedDialogBuilder(this.getActivity(), Session.getActiveSession(),postParams).setOnCompleteListener(new OnCompleteListener() {
						@Override
						public void onComplete(Bundle values,
								FacebookException error) {
							// TODO Auto-generated method stub
							
						}
	                }).build();
	                feedDialog.show();
	            }
			}
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_view_events_details, container, false);
		Bundle bundle = getArguments();
		
		event.setEventID(bundle.getInt("eventID"));
		event.setEventAdminNRIC(bundle.getString("eventAdminNRIC"));
		event.setEventName(bundle.getString("eventName"));
		event.setEventCategory(bundle.getString("eventCategory"));
		event.setEventDescription(bundle.getString("eventDescription"));
		try {
			event.setEventDateTimeFrom(sqlDateTimeFormatter.parse(bundle.getString("eventDateTimeTo")));
			event.setEventDateTimeTo(sqlDateTimeFormatter.parse(bundle.getString("eventDateTimeFrom")));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		event.setOccurence(bundle.getString("occurence"));
		event.setNoOfParticipantsAllowed(bundle.getInt("noOfParticipants"));
		event.setActive(bundle.getInt("active"));
		if(bundle.getString("eventFBPostID") != null){
			event.setEventFBPostID(bundle.getString("eventFBPostID"));
		}
		else{
			event.setEventFBPostID("0");
		}
		joined = bundle.getBoolean("joined");
		MySharedPreferences preferences = new MySharedPreferences(this.getActivity());
		nric = preferences.getPreferences("nric","");

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		tvEventID = (TextView) getActivity().findViewById(R.id.tv_event_id);
		tvEventName = (TextView) getActivity().findViewById(R.id.tv_event_name);
		ivEventPoster = (ImageView) getActivity().findViewById(R.id.iv_event_poster);
		tvEventCategory = (TextView) getActivity().findViewById(R.id.tv_event_category);
		tvEventDescription = (TextView) getActivity().findViewById(R.id.tv_event_description);
		tvEventDateTimeFrom = (TextView) getActivity().findViewById(R.id.tv_event_date_time_from);
		tvEventDateTimeTo = (TextView) getActivity().findViewById(R.id.tv_event_date_time_to);
		tvEventOccur = (TextView) getActivity().findViewById(R.id.tv_event_occur);
		tvEventNoOfParticipants = (TextView) getActivity().findViewById(R.id.tv_event_no_of_participants);
		btnCheckIn = (Button) getActivity().findViewById(R.id.btn_check_in);
		btnCheckIn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(!CheckNetworkConnection.haveNetworkConnection(ViewEventsDetailsFragment.this.getActivity())){
					AlertDialog.Builder builder = new AlertDialog.Builder(ViewEventsDetailsFragment.this.getActivity());
					builder.setTitle("You are in offline mode");
					builder.setMessage("Please check your internet connection and try again.");
					builder.setPositiveButton("OK", null);
					builder.create().show();
				}
				else{
					CheckInEvent checkInEvent = new CheckInEvent(ViewEventsDetailsFragment.this.getActivity(),event);
					checkInEvent.execute();
				}
			}
		});
		
		ivEventPoster = (ImageView) getActivity().findViewById(R.id.iv_event_poster);

        Calendar todayDate = Calendar.getInstance();
        Calendar eventDateTimeFrom = Calendar.getInstance();
        eventDateTimeFrom.setTime(event.getEventDateTimeFrom());
        
        long minutesDifference = eventDateTimeFrom.getTimeInMillis() - todayDate.getTimeInMillis();
        System.out.println(minutesDifference);
        if(minutesDifference > 600000){
        	btnCheckIn.setVisibility(View.GONE);
        }
        else{
        	btnCheckIn.setVisibility(View.VISIBLE);
        }

		tvEventID.setText("Event No: #" + event.getEventID());
		tvEventName.setText(event.getEventName());
		tvEventCategory.setText("Category: \n" + event.getEventCategory());
		tvEventDescription.setText("Description: \n" + event.getEventDescription());
		tvEventDateTimeFrom.setText("From: \n" + dateTimeFormatter.format(event.getEventDateTimeFrom()));
		tvEventDateTimeTo.setText("To: \n" + dateTimeFormatter.format(event.getEventDateTimeTo()));
		tvEventOccur.setText("Occurs: \n" + event.getOccurence());
		tvEventNoOfParticipants.setText("No of participants allowed: \n" + event.getNoOfParticipantsAllowed());
		
		if(!CheckNetworkConnection.haveNetworkConnection(ViewEventsDetailsFragment.this.getActivity())){
			ivEventPoster.setVisibility(View.GONE);
		}
		else{
			if((Session.getActiveSession() != null) && (Session.getActiveSession().isOpened()) && (!event.getEventFBPostID().equals("0"))){
				getPoster();
			}
			else{
				pictureURL = "";
				ivEventPoster.setVisibility(View.GONE);
			}
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	public void getPoster(){
			Request request = new Request(Session.getActiveSession(), event.getEventFBPostID(), null, HttpMethod.GET, new Request.Callback() {
				public void onCompleted(Response response) {
			    /* handle the result */
					try {
						if((response.getGraphObject().getInnerJSONObject().getJSONArray("image") != null) && (response.getGraphObject().getInnerJSONObject().getJSONArray("image").length() > 0)){
							pictureURL = response.getGraphObject().getInnerJSONObject().getJSONArray("image").getJSONObject(0).getString("url").toString().replace("\"/", "/");
							//System.out.println("Picture URL: " + pictureURL);

							GetImageFromFacebook getImageFromFacebook = new GetImageFromFacebook(ViewEventsDetailsFragment.this.getActivity(),ivEventPoster,pictureURL);
							getImageFromFacebook.execute();
						}
						else{
							pictureURL = "";
							ivEventPoster.setVisibility(View.GONE);
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						ivEventPoster.setVisibility(View.GONE);
					}
				}
			});
			RequestAsyncTask task = new RequestAsyncTask(request);
	        task.execute();
	}
	

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data){
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode,resultCode,data);
		/*if(requestCode == 10){
			uiHelper.onActivityResult(requestCode, resultCode, data, null);
		}*/
		if(requestCode == 1){
			if(resultCode == Activity.RESULT_OK){
				System.out.println(data.getExtras().getString("newEventAdminNRIC"));
				String newEventAdminNRIC = data.getExtras().getString("newEventAdminNRIC");
				UnjoinEvent unjoinEvent = new UnjoinEvent(ViewEventsDetailsFragment.this,event.getEventID(), nric, newEventAdminNRIC);
				unjoinEvent.execute();
			}
		}
	}
	
	private void publishStory() {
	    Session session = Session.getActiveSession();

	    if (session != null){

	        // Check for publish permissions    
	        List<String> permissions = session.getPermissions();
	        if (!isSubsetOf(PERMISSIONS, permissions)) {
	            pendingPublishReauthorization = true;
	            Session.NewPermissionsRequest newPermissionsRequest = new Session.NewPermissionsRequest(this.getActivity(), PERMISSIONS);
	            session.requestNewPublishPermissions(newPermissionsRequest);
	        }	        
			OpenGraphObject eventObj = OpenGraphObject.Factory.createForPost("community_outreach.event");
			if((pictureURL != null) && (pictureURL.length() > 0)){
				eventObj.setProperty("og:image:url", pictureURL);
			}
			else{
				eventObj.setProperty("og:image:url", "");
			}
	        eventObj.setTitle(event.getEventName());
	        eventObj.setUrl("https://developers.facebook.com/docs/android/share");
	        eventObj.setDescription(event.getEventDescription());
	        
            OpenGraphAction readAction = GraphObject.Factory.create(OpenGraphAction.class);
            readAction.setProperty("event",eventObj);
            FacebookDialog shareDialog = new FacebookDialog.OpenGraphActionDialogBuilder(this.getActivity(), readAction, "community_outreach:share", "event").build();
            shareDialog.present();

	    }
	}
	
	private boolean isSubsetOf(Collection<String> subset, Collection<String> superset) {
	    for (String string : subset) {
	        if (!superset.contains(string)) {
	            return false;
	        }
	    }
	    return true;
	}
}

