package com.example.it3197_casestudy.ui_logic;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.controller.CreateEvent;
import com.example.it3197_casestudy.controller.GetEventParticipants;
import com.example.it3197_casestudy.controller.GetImageFromFacebook;
import com.example.it3197_casestudy.controller.JoinEvent;
import com.example.it3197_casestudy.controller.UnjoinEvent;
import com.example.it3197_casestudy.model.EventParticipants;
import com.example.it3197_casestudy.model.FBComments;
import com.example.it3197_casestudy.mysqlite.FBCommentsSQLController;
import com.example.it3197_casestudy.util.CheckNetworkConnection;
import com.example.it3197_casestudy.util.EventsTimelineListAdapter;
import com.example.it3197_casestudy.util.Settings;
import com.facebook.FacebookException;
import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.RequestBatch;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;

/**
 * A dummy fragment representing a section of the app, but that simply displays
 * dummy text.
 */
public class ViewEventsTimelineFragment extends Fragment implements Settings{
	private ListView lvEventTimeline;
	private UiLifecycleHelper uiHelper;
	private MenuItem menuItemPost;

	private static final List<String> PERMISSIONS = Arrays.asList("publish_actions","publish_stream");
	private static final String PENDING_PUBLISH_KEY = "pendingPublishReauthorization";
	private boolean pendingPublishReauthorization = false;

	private String eventFBPostID;

	public ViewEventsTimelineFragment() {
	}

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
		inflater.inflate(R.menu.view_events_timeline_fragment_menu, menu);
		menuItemPost = menu.findItem(R.id.post_comments);
		if(!CheckNetworkConnection.haveNetworkConnection(ViewEventsTimelineFragment.this.getActivity()) || (eventFBPostID.equals("0"))){
			menuItemPost.setVisible(false);
		}
		else{
			menuItemPost.setVisible(true);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		switch (item.getItemId()) {
		case R.id.post_comments:
			if(!CheckNetworkConnection.haveNetworkConnection(ViewEventsTimelineFragment.this.getActivity())){
				AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
				builder.setTitle("Unable to post comment");
				builder.setMessage("Please try again later or contact the event organizer.");
				builder.setPositiveButton("OK", null);
				builder.create().show();
			}
			else if(eventFBPostID.equals("0")){
				AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
				builder.setTitle("Unable to post comment");
				builder.setMessage("Please try again later or contact the event organizer.");
				builder.setPositiveButton("OK", null);
				builder.create().show();
			}
			else{
				AlertDialog.Builder alert = new AlertDialog.Builder(
						this.getActivity());
	
				alert.setTitle("Post Comments");
				alert.setMessage("Enter your comments");
	
				// Set an EditText view to get user input
				final EditText input = new EditText(this.getActivity());
				input.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
				input.setMaxLines(2);
				alert.setView(input);
	
				alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								String value = input.getText().toString();
								// Do something with value!
								ProgressDialog pDialog = ProgressDialog.show(ViewEventsTimelineFragment.this.getActivity(), "Posting comments","Please wait");
								publishComments(pDialog, value);
							}
						});
	
				alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whichButton) {
								// Canceled.
							}
						});
	
				alert.show();
			}
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(
				R.layout.fragment_view_events_timeline, container, false);
		Bundle bundle = getArguments();
		if (bundle.getString("eventFBPostID") != null) {
			eventFBPostID = bundle.getString("eventFBPostID");
		} else {
			eventFBPostID = "0";
		}
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		lvEventTimeline = (ListView) getActivity().findViewById(R.id.lv_events_timeline);
		if(!CheckNetworkConnection.haveNetworkConnection(ViewEventsTimelineFragment.this.getActivity())){
			FBCommentsSQLController controller = new FBCommentsSQLController(ViewEventsTimelineFragment.this.getActivity());
			
			ArrayList<FBComments> fbCommentsList = controller.getAllFBComments();

			String[][] timelineList = new String[fbCommentsList.size()][3]; 
			for(int i=0;i<fbCommentsList.size();i++){
				String comments = fbCommentsList.get(i).getComment();
				String name = fbCommentsList.get(i).getName();
				String time = dateTimeFormatter.format(fbCommentsList.get(i).getDateTimeMade());
				timelineList[i][0] = name;
				timelineList[i][1] = time;
				timelineList[i][2] = comments;
			}
			EventsTimelineListAdapter adapter = new EventsTimelineListAdapter(ViewEventsTimelineFragment.this.getActivity(),timelineList);
			lvEventTimeline.setAdapter(adapter);
		}			
		else if(eventFBPostID.equals("0")){
			AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
			builder.setTitle("Unable to retrieve comment");
			builder.setMessage("Please try again later or contact the event organizer.");
			builder.setPositiveButton("OK", null);
			builder.create().show();
		}
		else{
			if((Session.getActiveSession() != null) && (Session.getActiveSession().isOpened()) && (!eventFBPostID.equals("0"))){
				ProgressDialog pDialog = ProgressDialog.show(ViewEventsTimelineFragment.this.getActivity(), "Retrieving comments","Please wait");
				getComments(pDialog);
			}
		}

	}

	private void publishComments(final ProgressDialog dialog, final String value) {
		Session session = Session.getActiveSession();

		if (session != null) {

			// Check for publish permissions
			List<String> permissions = session.getPermissions();
			if (!isSubsetOf(PERMISSIONS, permissions)) {
				pendingPublishReauthorization = true;
				Session.NewPermissionsRequest newPermissionsRequest = new Session.NewPermissionsRequest(
						this.getActivity(), PERMISSIONS);
				session.requestNewPublishPermissions(newPermissionsRequest);
			}
			Request request = new Request(Session.getActiveSession(),eventFBPostID, null, HttpMethod.GET,new Request.Callback() {
						public void onCompleted(Response response) {
							String postActionID = "0"; 
							try {
								postActionID = response.getGraphObject().getInnerJSONObject().getString("post_action_id");
							} catch (JSONException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							RequestBatch requestBatch = new RequestBatch();

							Bundle postParams = new Bundle();

							postParams.putString("message", value);
							Request.Callback callback = new Request.Callback() {
								public void onCompleted(Response response) {
									dialog.dismiss();
									try {
										if (response != null) {
											JSONObject graphResponse = response.getGraphObject().getInnerJSONObject();
											System.out.println(response.toString());
											getComments(dialog);
										}
									} catch (Exception e) {
										Log.i("Tag",
												"JSON error "
														+ e.getMessage());
									}
									FacebookRequestError error = response.getError();
									if (error != null) {
										Toast.makeText(
												ViewEventsTimelineFragment.this
														.getActivity(),
												error.getErrorMessage(),
												Toast.LENGTH_SHORT).show();
										Log.i("Tag",
												"JSON error "
														+ error.getErrorMessage());
									}
								}
							};
							Request request = new Request(Session.getActiveSession(), postActionID + "/comments", postParams,HttpMethod.POST, callback);

							request.setBatchEntryName("object");

							// Add the request to the batch
							requestBatch.add(request);

							// TO DO: Add the publish action request to the
							// batch

							// Execute the batch request
							requestBatch.executeAsync();
						}
					});
			RequestAsyncTask task = new RequestAsyncTask(request);
			task.execute();
		}
	}
	
	public void getComments(final ProgressDialog dialog){
		Request request = new Request(Session.getActiveSession(), eventFBPostID, null, HttpMethod.GET, new Request.Callback() {
			public void onCompleted(Response response) {
				System.out.println(response);
				String postActionID = "0"; 
				try {
					if(response.getGraphObject() != null){
						postActionID = response.getGraphObject().getInnerJSONObject().getString("post_action_id");
						Request request = new Request(Session.getActiveSession(), postActionID + "/comments", null, HttpMethod.GET, new Request.Callback() {
							public void onCompleted(Response response) {
								try {
									JSONArray data = response.getGraphObject().getInnerJSONObject().getJSONArray("data");
									FBCommentsSQLController controller = new FBCommentsSQLController(ViewEventsTimelineFragment.this.getActivity());
									
									controller.deleteAllFBComments();
									String[][] timelineList = new String[data.length()][3]; 
									for(int i=0;i<data.length();i++){
										String comments = data.getJSONObject(i).getString("message");
										String name = data.getJSONObject(i).getJSONObject("from").getString("name");
										String time = data.getJSONObject(i).getString("created_time");
										timelineList[i][0] = name;
										timelineList[i][1] = time;
										timelineList[i][2] = comments;
										Date timeX;
										try {
											timeX = fbDateTimeFormatter.parse(time);
											controller.insertComments(new FBComments(eventFBPostID,name,comments,timeX));
										} catch (ParseException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
									}
									
									EventsTimelineListAdapter adapter = new EventsTimelineListAdapter(ViewEventsTimelineFragment.this.getActivity(),timelineList);
									lvEventTimeline.setAdapter(adapter);
									dialog.dismiss();
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									dialog.dismiss();
									e.printStackTrace();
								}
							}
						});
						RequestAsyncTask task = new RequestAsyncTask(request);
					    task.execute();
					}
					else{
						dialog.dismiss();
						FacebookRequestError error = response.getError();
						if (error != null) {
							Toast.makeText(ViewEventsTimelineFragment.this.getActivity(),error.getErrorMessage(),Toast.LENGTH_SHORT).show();
							Log.i("Tag","JSON error "+ error.getErrorMessage());
						}
					}
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		RequestAsyncTask task = new RequestAsyncTask(request);
        task.execute();
	}

	private boolean isSubsetOf(Collection<String> subset,
			Collection<String> superset) {
		for (String string : subset) {
			if (!superset.contains(string)) {
				return false;
			}
		}
		return true;
	}
}
