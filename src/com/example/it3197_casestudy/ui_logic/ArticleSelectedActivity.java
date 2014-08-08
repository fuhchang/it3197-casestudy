package com.example.it3197_casestudy.ui_logic;

import org.json.JSONException;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.controller.GetImageFromFacebook;
import com.example.it3197_casestudy.controller.GetImageFromFacebookArticle;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ArticleSelectedActivity extends Fragment {

	
	TextView titleTv, authorTv, articleDateTv, contentTv, addTv;
	
	String title, author,date, content, address, postID;
	
	
	private String pictureURL;
	
	private ImageView artPoster;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.article_selected_activity, container, false);		
		Bundle bundle = getArguments();
		title = bundle.getString("title");
		author = bundle.getString("author");
		date = bundle.getString("articleDate");
		content = bundle.getString("content");
		address = bundle.getString("address");
		postID= bundle.getString("postID");
		
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		getActivity().getActionBar().setTitle("");
		
		titleTv =(TextView)getActivity().findViewById(R.id.title);
		authorTv = (TextView)getActivity().findViewById(R.id.author);
		articleDateTv = (TextView)getActivity().findViewById(R.id.date);
		contentTv = (TextView)getActivity().findViewById(R.id.content);
		
		titleTv.setText(title);
		authorTv.setText("Author: " + author);
		articleDateTv.setText(date);
		contentTv.setText(content);
		
		ImageView iv = (ImageView)getActivity().findViewById(R.id.image);
		iv.setImageResource(R.drawable.article123);
		
		
		artPoster = (ImageView) getActivity().findViewById(R.id.art_poster);
		if(postID.equals("0")){
			artPoster.setVisibility(View.GONE);
		}
		else{
			getPoster();
		}
	}

	
	
	
	public void getPoster(){
		Request request = new Request(Session.getActiveSession(), postID, null, HttpMethod.GET, new Request.Callback() {
			public void onCompleted(Response response) {
		    /* handle the result */
				try {
					if((response.getGraphObject().getInnerJSONObject().getJSONArray("image") != null) && (response.getGraphObject().getInnerJSONObject().getJSONArray("image").length() > 0)){
						pictureURL = response.getGraphObject().getInnerJSONObject().getJSONArray("image").getJSONObject(0).getString("url").toString().replace("\"/", "/");
						//System.out.println("Picture URL: " + pictureURL);
						GetImageFromFacebookArticle getImageFromFacebook = new GetImageFromFacebookArticle(ArticleSelectedActivity.this.getActivity(),artPoster,pictureURL);
						getImageFromFacebook.execute();
					}
					else{
						pictureURL = "";
						artPoster.setVisibility(View.GONE);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					artPoster.setVisibility(View.GONE);
				}
			}
		});
		RequestAsyncTask task = new RequestAsyncTask(request);
        task.execute();
	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	
	
}
