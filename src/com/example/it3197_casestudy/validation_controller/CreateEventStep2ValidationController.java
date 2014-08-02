package com.example.it3197_casestudy.validation_controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.it3197_casestudy.controller.CreateEvent;
import com.example.it3197_casestudy.crouton.Crouton;
import com.example.it3197_casestudy.crouton.Style;
import com.example.it3197_casestudy.model.Event;
import com.example.it3197_casestudy.model.EventLocationDetail;
import com.example.it3197_casestudy.ui_logic.CreateEventStep1Activity;
import com.example.it3197_casestudy.ui_logic.CreateEventStep2Activity;
import com.example.it3197_casestudy.ui_logic.ViewAllEventsActivity;
import com.example.it3197_casestudy.util.Settings;
import com.example.it3197_casestudy.util.TimeIgnoringComparator;
import com.example.it3197_casestudy.validation.Form;
import com.example.it3197_casestudy.validation.Validate;
import com.example.it3197_casestudy.validation.validator.NotEmptyValidator;
import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;

public class CreateEventStep2ValidationController implements Settings{
	private CreateEventStep2Activity activity;
	private EventLocationDetail eventLocationDetails;
	private Event event;
	private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");
	private static final String PENDING_PUBLISH_KEY = "pendingPublishReauthorization";
	private boolean pendingPublishReauthorization = false;
	private String posterFileName;
	
	public CreateEventStep2ValidationController(CreateEventStep2Activity activity, EventLocationDetail eventLocationDetails, String posterFileName){
		this.activity = activity;
		this.eventLocationDetails = eventLocationDetails;
		this.posterFileName = posterFileName;
	}
	
	public void validateForm(Intent intent, Calendar calendarFrom, Calendar calendarTo, Event event, String occurence){
		this.event = event; 
		Calendar calendarCurrentDate = Calendar.getInstance();
		TimeIgnoringComparator t = new TimeIgnoringComparator();
		int differenceInDays = (int) Math.floor((calendarFrom.getTimeInMillis()-calendarTo.getTimeInMillis())/-86400000);
		int days = -86400000 * differenceInDays;
		double compare = (calendarFrom.getTimeInMillis()-calendarTo.getTimeInMillis()) - days;
		System.out.println(compare);
		if((calendarFrom.before(calendarCurrentDate) || (calendarTo.before(calendarCurrentDate) || (t.compare(calendarFrom, calendarCurrentDate) == 0 ) || (t.compare(calendarTo, calendarCurrentDate) == 0 )))){
			Crouton crouton = Crouton.makeText(activity,"Please choose another date after today.",Style.ALERT);
			crouton.show();
			return;
		}
		if(compare > -1800000){
			Crouton crouton = Crouton.makeText(activity,"Please select a ending time 30 min before the starting time.",Style.ALERT);
			crouton.show();
			return;
		}
		if((calendarFrom.after(calendarTo))){
			Crouton crouton = Crouton.makeText(activity,"Please choose another date after the starting date.",Style.ALERT);
			crouton.show();
			return;
		}
		else{
			event.setEventDateTimeFrom(calendarFrom.getTime());
			event.setEventDateTimeTo(calendarTo.getTime());
			event.setOccurence(occurence);

    		ProgressDialog dialog = ProgressDialog.show(activity,
    				"Creating event", "Please wait...", true);
			if(!Session.getActiveSession().isClosed()){
				publishStory(dialog);
			}
			else{
				Toast.makeText(activity, "Unable to share event to Facebook", Toast.LENGTH_LONG).show();
				event.setEventFBPostID("0");
				CreateEvent createEvent = new CreateEvent(activity,event,eventLocationDetails,dialog);
				createEvent.execute();
			}
		}
	}
	
	private void publishStory(final ProgressDialog dialog) {
	    Session session = Session.getActiveSession();

	    if (session != null){

	        // Check for publish permissions    
	        List<String> permissions = session.getPermissions();
	        if (!isSubsetOf(PERMISSIONS, permissions)) {
	            pendingPublishReauthorization = true;
	            Session.NewPermissionsRequest newPermissionsRequest = new Session.NewPermissionsRequest(activity, PERMISSIONS);
	            session.requestNewPublishPermissions(newPermissionsRequest);
	            return;
	        }

	        Bundle postParams = new Bundle();
	        postParams.putString("name", event.getEventName());
	        postParams.putString("caption", dateTimeFormatter.format(event.getEventDateTimeFrom()) + " -- " + dateTimeFormatter.format(event.getEventDateTimeTo()));
	        postParams.putString("description", event.getEventDescription());
	        postParams.putString("link", "localhost:8080/CommunityOutreach/");
	        postParams.putString("picture", posterFileName);
	        System.out.println(event.getEventName() + "(" + event.getEventDateTimeFrom() + "-" + event.getEventDateTimeTo() + "), " + event.getEventDescription() + " -- " + posterFileName);
	        Request.Callback callback= new Request.Callback() {
	            public void onCompleted(Response response) {
	                try {
	                	if(response != null){
			                JSONObject graphResponse = response.getGraphObject().getInnerJSONObject();
		                	if(graphResponse.getString("id") != null){
		                		event.setEventFBPostID(graphResponse.getString("id"));
		                	}
		                	else{
		                		event.setEventFBPostID("0");
		                	}
		        			CreateEvent createEvent = new CreateEvent(activity,event,eventLocationDetails,dialog);
		        			createEvent.execute();
	                	}
	                } catch (Exception e) {
	                    Log.i("Tag",
	                        "JSON error "+ e.getMessage());
	                }
	                FacebookRequestError error = response.getError();
	                if (error != null) {
	                    Toast.makeText(activity, error.getErrorMessage(), Toast.LENGTH_SHORT).show();
	                }
	            }
	        };
	        Request request = new Request(session, "614675458630326/feed", postParams, HttpMethod.POST, callback);
	        RequestAsyncTask task = new RequestAsyncTask(request);
	        task.execute();
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
