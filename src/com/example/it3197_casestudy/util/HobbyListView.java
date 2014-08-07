package com.example.it3197_casestudy.util;

import java.io.File;
import java.util.ArrayList;

import org.json.JSONException;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.controller.GetImageFromFaceBookForHobby;
import com.example.it3197_casestudy.model.Hobby;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.google.android.gms.common.images.ImageManager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.ImageReader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HobbyListView extends ArrayAdapter<Hobby> {
	private final Activity context;
	private ArrayList<Hobby> resultArray = new ArrayList<Hobby>();
	private ImageView imgView;
	private String pictureURL;
	private ProgressDialog dialog;

	public HobbyListView(Context context, ArrayList<Hobby> hobbyList, ProgressDialog dialog) {
		super(context, R.layout.activity_hobby_list_view, hobbyList);
		this.context = (Activity) context;
		this.resultArray = hobbyList;
		this.dialog = dialog;
	}

	

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.activity_hobby_list_view,
				null, true);
		rowView.setBackgroundColor(Color.WHITE);
		
		TextView gTitle = (TextView) rowView.findViewById(R.id.gTitle);
		TextView gCate = (TextView) rowView.findViewById(R.id.gType);
		TextView gDesc = (TextView) rowView.findViewById(R.id.gDesc);
		
		if((Session.getActiveSession() != null) && (Session.getActiveSession().isOpened()) && (!resultArray.get(position).getHobbyFBPostID().equals("0"))){
				getImage(position, imgView, resultArray.get(position).getHobbyFBPostID(), rowView);
		}else{
			Toast.makeText(getContext(), "no session found", Toast.LENGTH_LONG).show();
			
		}
		
		gTitle.setTextSize(35);
		gCate.setTextSize(20);
		gDesc.setTextSize(15);
		View hr = rowView.findViewById(R.id.hr);
		hr.setBackgroundColor(Color.GRAY);
		View hr2 = rowView.findViewById(R.id.hr2);
		hr2.setBackgroundColor(Color.GRAY);
		gTitle.setText(resultArray.get(position).getGroupName());
		gCate.setText(resultArray.get(position).getCategory());
		gDesc.setText(resultArray.get(position).getDescription());
		return rowView;
	}
	
	
	public void getImage(int position, ImageView imgview, String imgFile, final View rowView){
		
		Request request = new Request(Session.getActiveSession(), imgFile, null, HttpMethod.GET, new Request.Callback() {

			@Override
			public void onCompleted(Response response) {
				// TODO Auto-generated method stub
				System.out.println(response);
				try{
					if((response.getGraphObject().getInnerJSONObject().getJSONArray("image") != null) && (response.getGraphObject().getInnerJSONObject().getJSONArray("image").length() > 0)){
						pictureURL = response.getGraphObject().getInnerJSONObject().getJSONArray("image").getJSONObject(0).getString("url").toString().replace("\"/", "/");
						GetImageFromFaceBookForHobby getImageFromFacebook = new GetImageFromFaceBookForHobby(context,imgView, pictureURL, dialog, rowView); 
						getImageFromFacebook.execute();
					}else{
						pictureURL = "";
					}
				}catch(JSONException e){
					e.printStackTrace();
				}
			}
			
		});
		
		RequestAsyncTask task = new RequestAsyncTask(request);
        task.execute();
		
	}

}
