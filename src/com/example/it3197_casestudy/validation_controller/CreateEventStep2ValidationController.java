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
import com.facebook.RequestBatch;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.model.GraphObject;
import com.facebook.model.OpenGraphAction;
import com.facebook.model.OpenGraphObject;
import com.facebook.widget.FacebookDialog;

public class CreateEventStep2ValidationController implements Settings{
	private CreateEventStep2Activity activity;
	private EventLocationDetail eventLocationDetails;
	private Event event;
	private static final List<String> PERMISSIONS = Arrays.asList("publish_actions","publish_stream");
	private static final String PENDING_PUBLISH_KEY = "pendingPublishReauthorization";
	private boolean pendingPublishReauthorization = false;
	private String posterFileName;
	private boolean requestHelp;
	
	public CreateEventStep2ValidationController(CreateEventStep2Activity activity, EventLocationDetail eventLocationDetails, String posterFileName){
		this.activity = activity;
		this.eventLocationDetails = eventLocationDetails;
		this.posterFileName = posterFileName;
	}
	
	public void validateForm(Intent intent, Calendar calendarFrom, Calendar calendarTo, Event event, String occurence, boolean requestHelp){
		this.event = event; 
		this.requestHelp = requestHelp;
		
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
				CreateEvent createEvent = new CreateEvent(activity,event,eventLocationDetails,dialog,requestHelp);
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
	        }	        
	        
	        RequestBatch requestBatch = new RequestBatch();

	        
	        Bundle postParams = new Bundle();

	        postParams.putString("fb:app_id", "1448892845363751");
	        postParams.putString("og:title", event.getEventName());
	        postParams.putString("og:type", "community_outreach:event");
	        postParams.putString("og:description", event.getEventDescription());
	        postParams.putString("og:url", "localhost:8080/CommunityOutreach/");
	        postParams.putString("og:image", posterFileName);
	        if((posterFileName.length() > 0) && (posterFileName != null)){
		        postParams.putString("object","{\"title\":\""+event.getEventName()+"\",\"type\":\"community_outreach:event\",\"image\":\""+posterFileName+"\",\"description\":\""+event.getEventDescription()+"\"}");	
	        }
	        else{
	        	postParams.putString("object","{\"title\":\""+event.getEventName()+"\",\"type\":\"community_outreach:event\",\"description\":\""+event.getEventDescription()+"\"}");
	        }
	        //System.out.println(event.getEventName() + "(" + event.getEventDateTimeFrom() + "-" + event.getEventDateTimeTo() + "), " + event.getEventDescription() + " -- " + posterFileName);
	        Request.Callback callback= new Request.Callback() {
	            public void onCompleted(Response response) {
	                try {
	                	if(response != null){
			                JSONObject graphResponse = response.getGraphObject().getInnerJSONObject();
			                System.out.println(response.toString());
		                	if(graphResponse.getString("id") != null){
		                		event.setEventFBPostID(graphResponse.getString("id"));
		                	}
		                	else{
		                		event.setEventFBPostID("0");
		                	}
		        			CreateEvent createEvent = new CreateEvent(activity,event,eventLocationDetails,dialog,requestHelp);
		        			createEvent.execute();
	                	}
	                } catch (Exception e) {
	                    Log.i("Tag",
	                        "JSON error "+ e.getMessage());
	                }
	                FacebookRequestError error = response.getError();
	                if (error != null) {
	                    Toast.makeText(activity, error.getErrorMessage(), Toast.LENGTH_SHORT).show();
	                    Log.i("Tag",
		                        "JSON error "+ error.getErrorMessage());
	                }
	            }
	        };
	        Request request = new Request(session, "me/objects/community_outreach:event", postParams, HttpMethod.POST, callback);

	        request.setBatchEntryName("object");

		     // Add the request to the batch
		     requestBatch.add(request);
	
		     // TO DO: Add the publish action request to the batch
	
		     // Execute the batch request
		     requestBatch.executeAsync();
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
