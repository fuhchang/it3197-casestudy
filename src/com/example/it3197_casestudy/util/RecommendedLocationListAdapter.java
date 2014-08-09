package com.example.it3197_casestudy.util;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.googlePlaces.Place;
import com.example.it3197_casestudy.model.Event;
import com.example.it3197_casestudy.mysqlite.EventSQLController;
import com.example.it3197_casestudy.mysqlite.SavedEventSQLController;
import com.example.it3197_casestudy.ui_logic.SuggestLocationActivity;
import com.example.it3197_casestudy.ui_logic.ViewAllEventsActivity;

public class RecommendedLocationListAdapter extends BaseExpandableListAdapter implements Settings{
	private ExpandableListView lvViewAllEvents;
	private SuggestLocationActivity activity;
    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<Place>> _listDataChild;
 
    public RecommendedLocationListAdapter(Context context, SuggestLocationActivity activity, List<String> listDataHeader,HashMap<String, List<Place>> listChildData, ExpandableListView lvViewAllEvents) {
        this._context = context;
        this.activity = activity;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.lvViewAllEvents = lvViewAllEvents;
    }
 
    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosititon);
    }
 
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }
 
    @Override
    public View getChildView(int groupPosition, final int childPosition,boolean isLastChild, View convertView, ViewGroup parent) { 

        final Place place = (Place) getChild(groupPosition, childPosition);
        
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_recommended_locations, null);
        }
 
        TextView tvRecommendedLocations = (TextView) convertView.findViewById(R.id.tv_recommended_locations);
        tvRecommendedLocations.setText(place.getName());
        //txtListChild.setText(childText);
        return convertView;
    }
 
    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }
 
    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }
 
    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }
 
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }
 
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
            View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_events_header, null);
        }
        //convertView
        TextView lblListHeader = (TextView) convertView.findViewById(R.id.tv_header);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
 
        return convertView;
    }
 
    @Override
    public boolean hasStableIds() {
        return false;
    }
 
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
