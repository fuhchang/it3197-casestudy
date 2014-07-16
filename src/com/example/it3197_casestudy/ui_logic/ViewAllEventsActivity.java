package com.example.it3197_casestudy.ui_logic;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.controller.GetAllEvents;

import android.os.Bundle;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class ViewAllEventsActivity extends FragmentActivity {
	ListView lvViewAllEvents;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_all_events);

		lvViewAllEvents = (ListView) findViewById(R.id.lv_view_all_events);
		
		GetAllEvents getAllEvents = new GetAllEvents(ViewAllEventsActivity.this,lvViewAllEvents);
		getAllEvents.execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_all_events_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.create_event:
				SelectTypeOfEventDialog selectTypeOfEventDialog = new SelectTypeOfEventDialog();
				selectTypeOfEventDialog.show(this.getSupportFragmentManager(),"EventTypeDialog");
				break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public static class SelectTypeOfEventDialog extends DialogFragment{
		public Dialog onCreateDialog(Bundle savedInstanceState){
			AlertDialog.Builder selectTypeOfEventDialog = new AlertDialog.Builder(getActivity());
			selectTypeOfEventDialog.setTitle("Select type of event");
			
			selectTypeOfEventDialog.setItems(R.array.type_of_events, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					String[] typeOfEvents = getResources().getStringArray(R.array.type_of_events);
					String choice = typeOfEvents[which];
					Intent intent = new Intent(getActivity(), CreateEventStep1Activity.class);
	    			intent.putExtra("typeOfEvent", choice);
	    			startActivity(intent);
	    			getActivity().finish();
				}
			});
			return selectTypeOfEventDialog.create();
		}
	}

}
