package com.example.it3197_casestudy.ui_logic;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.controller.GetEvent;
import com.example.it3197_casestudy.controller.GetEventParticipants;
import com.example.it3197_casestudy.controller.GetImageFromFacebook;
import com.example.it3197_casestudy.controller.JoinEvent;
import com.example.it3197_casestudy.controller.UnjoinEvent;
import com.example.it3197_casestudy.model.Event;
import com.example.it3197_casestudy.model.EventParticipants;
import com.example.it3197_casestudy.util.MySharedPreferences;
import com.example.it3197_casestudy.util.Settings;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.UiLifecycleHelper;

/**
 * A dummy fragment representing a section of the app, but that simply
 * displays dummy text.
 * 
 * @author Lee Zhuo Xun
 */
public class ViewEventsDetailsFragment extends Fragment implements Settings{
	private TextView tvEventID, tvEventName, tvEventCategory, tvEventDescription, tvEventDateTimeFrom,
					 tvEventDateTimeTo, tvEventOccur, tvEventNoOfParticipants;
	private ImageView ivEventPoster;
	private Button btnCheckIn;
	private Event event = new Event();
	private String nric;
	private ArrayList<EventParticipants> eventParticipantsArrList = new ArrayList<EventParticipants>();
	private String nricList[] = new String[10];
	MenuItem menuItemJoin;
	MenuItem menuItemUnjoin;
	MenuItem menuItemUpdate;

	private UiLifecycleHelper uiHelper;
	
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
		uiHelper = new UiLifecycleHelper(ViewEventsDetailsFragment.this.getActivity(), null);
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
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		
		switch(item.getItemId()){
		case R.id.update:
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
				startActivity(i);
				ViewEventsDetailsFragment.this.getActivity().finish();
			}
			break;
		case R.id.join : 
			Calendar todayDate = Calendar.getInstance();
			EventParticipants eventParticipants = new EventParticipants(event.getEventID(),nric,todayDate.getTime(),0);
			JoinEvent joinEvent = new JoinEvent(ViewEventsDetailsFragment.this,eventParticipants);
			joinEvent.execute();
			break;
		case R.id.unjoin:
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
		
		ivEventPoster = (ImageView) getActivity().findViewById(R.id.iv_event_poster);

		if((Session.getActiveSession() != null) && (Session.getActiveSession().isOpened()) && (!event.getEventFBPostID().equals("0"))){
			getPoster();
		}
		else{
			ivEventPoster.setVisibility(View.GONE);
		}
        Calendar todayDate = Calendar.getInstance();
        
		tvEventID.setText("Event No: #" + event.getEventID());
		tvEventName.setText(event.getEventName());
		tvEventCategory.setText("Category: \n" + event.getEventCategory());
		tvEventDescription.setText("Description: \n" + event.getEventDescription());
		tvEventDateTimeFrom.setText("From: \n" + dateTimeFormatter.format(event.getEventDateTimeFrom()));
		tvEventDateTimeTo.setText("To: \n" + dateTimeFormatter.format(event.getEventDateTimeTo()));
		tvEventOccur.setText("Occurs: \n" + event.getOccurence());
		tvEventNoOfParticipants.setText("No of participants allowed: \n" + event.getNoOfParticipantsAllowed());

		GetEventParticipants getEventParticipants = new GetEventParticipants(ViewEventsDetailsFragment.this, event.getEventID());
		getEventParticipants.execute();
	}
	
	public void getPoster(){
			Bundle b = new Bundle();
			b.putString("fields", "full_picture");
			Request request = new Request(Session.getActiveSession(), event.getEventFBPostID(), b, HttpMethod.GET, new Request.Callback() {
				public void onCompleted(Response response) {
			    /* handle the result */
					try {
						if((response.getGraphObject().getInnerJSONObject().getString("full_picture") != null) && (response.getGraphObject().getInnerJSONObject().getString("full_picture").length() > 0)){
							String pictureURL = response.getGraphObject().getInnerJSONObject().getString("full_picture");
							GetImageFromFacebook getImageFromFacebook = new GetImageFromFacebook(ViewEventsDetailsFragment.this.getActivity(),ivEventPoster,pictureURL);
							getImageFromFacebook.execute();
						}
						else{
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
		if(requestCode == 1){
			if(resultCode == Activity.RESULT_OK){
				System.out.println(data.getExtras().getString("newEventAdminNRIC"));
				String newEventAdminNRIC = data.getExtras().getString("newEventAdminNRIC");
				UnjoinEvent unjoinEvent = new UnjoinEvent(ViewEventsDetailsFragment.this,event.getEventID(), nric, newEventAdminNRIC);
				unjoinEvent.execute();
			}
		}
	}
}

