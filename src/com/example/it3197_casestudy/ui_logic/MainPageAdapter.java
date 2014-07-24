package com.example.it3197_casestudy.ui_logic;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.model.Article;
import com.example.it3197_casestudy.model.Combined;
import com.example.it3197_casestudy.model.Event;
import com.example.it3197_casestudy.model.Hobby;

public class MainPageAdapter extends ArrayAdapter<Combined>{
	private final Activity context;
	private ArrayList<Article> articleArray = new ArrayList<Article>();
	private ArrayList<Event> eventArray = new ArrayList<Event>();
	private ArrayList<Hobby> hobbyArray = new ArrayList<Hobby>();
	
	private ArrayList<Combined> combinedArray = new ArrayList<Combined>();
	
	public MainPageAdapter(Context context, ArrayList<Article> articleList, ArrayList<Event> eventList, ArrayList<Hobby> hobbyList, ArrayList<Combined> combinedList) {
		super(context, R.layout.main_page_adapter_layout, combinedList);
		// TODO Auto-generated constructor stub
		this.context = (Activity) context;
		this.articleArray = articleList;
		this.eventArray = eventList;
		this.hobbyArray=hobbyList;
		this.combinedArray=combinedList;
	}
	
	public int getCount() {
        return 3;
    }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
				LayoutInflater inflater = context.getLayoutInflater();
				View rowView = inflater.inflate(R.layout.main_page_adapter_layout, null, true);

				TextView txtType = (TextView) rowView.findViewById(R.id.type);
				TextView txtTitle = (TextView) rowView.findViewById(R.id.title);
				TextView txtDate = (TextView) rowView.findViewById(R.id.dateTime);
				TextView txtDesc = (TextView) rowView.findViewById(R.id.desc);
				ImageView iv = (ImageView) rowView.findViewById(R.id.image);
				ImageView go = (ImageView) rowView.findViewById(R.id.go);
				go.setBackgroundResource(R.drawable.main_go);
				
				if(position == 0){
					txtType.setText(" Event: ");
					
					String myHexColor = "#FF0000";
					//txtType.setBackgroundColor(Color.parseColor(myHexColor));
					txtTitle.setText(eventArray.get(0).getEventName());
					
					txtDate.setText("Date here");
					txtDesc.setText("The big brown fox jumps over the lazy dog.");
					iv.setImageResource(R.drawable.events);
					
				}
				if(position==1){
					
					String myHexColor = "#FF9900";
					
					//txtType.setBackgroundColor(Color.parseColor(myHexColor));
					txtType.setText(" Hobby: ");
					txtTitle.setText(hobbyArray.get(0).getGroupName());
					txtDate.setText("Date here");
					txtDesc.setText("The big brown fox jumps over the lazy dog.");
					
					iv.setImageResource(R.drawable.dance);
				}
				if(position==2){
					String myHexColor = "#00FF00";
					//txtType.setBackgroundColor(Color.parseColor(myHexColor));
					
					
					txtType.setText(" Latest Article: ");
					txtTitle.setText(articleArray.get(0).getTitle());		
					txtDate.setText(articleArray.get(0).getArticleDate());
					
					String content = articleArray.get(0).getContent();
					if(content.length()<=150){
						txtDesc.setText(articleArray.get(0).getContent());
					}
					System.out.println(content.length());
					
					if(content.length()>150){
						txtDesc.setText(content.substring(0, 150) + "...");
					}
					
					iv.setImageResource(R.drawable.article);

				}
				else{
					
				}
				
				return rowView;
	}


}