package com.example.it3197_casestudy.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.model.Hobby;
import com.example.it3197_casestudy.model.HobbyPost;
import com.example.it3197_casestudy.ui_logic.CreateHobbyPost;
import com.example.it3197_casestudy.ui_logic.ViewHobbiesMain;
import com.example.it3197_casestudy.ui_logic.ViewSingleHobby;
import com.example.it3197_casestudy.util.Settings;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.widget.ListView;
import android.widget.Toast;

public class CreateHobbyPostController extends
		AsyncTask<Object, Object, Object> implements Settings {
	private ProgressDialog dialog;
	private HobbyPost hobbypost;
	private CreateHobbyPost activity;
	private int adminRight;
	private ViewSingleHobby vsh;
	private ListView listView;
	private String grpName;
	Hobby hobby;
	public CreateHobbyPostController(CreateHobbyPost activity,
			HobbyPost hobbypost, int adminRight) {
		this.activity = activity;
		this.hobbypost = hobbypost;
		this.adminRight = adminRight;
	}

	@Override
	protected Object doInBackground(Object... arg0) {
		// TODO Auto-generated method stub
		return createPost();
	}

	@Override
	protected void onPostExecute(Object result) {
		// TODO Auto-generated method stub
		parseJSONResponse((String)result);
		
	
		Intent intent = new Intent(activity, ViewSingleHobby.class);
		intent.putExtra("grpName", hobby.getGroupName());
		intent.putExtra("grpID", hobby.getGroupID());
		intent.putExtra("adminNric", hobby.getAdminNric());
		intent.putExtra("userNric", hobbypost.getPosterNric());
		activity.startActivity(intent);
		activity.finish();
		
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		hobby = new Hobby();
		dialog = ProgressDialog.show(activity, "Creating Post", "Creating...Please Wait",
				true);

	}

	public String createPost() {
		String responseBody = "";
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(API_URL + "CreatePostServlet");
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();

		postParameters.add(new BasicNameValuePair("grpID", Integer
				.toString(hobbypost.getGrpID())));
		
		postParameters.add(new BasicNameValuePair("postTitle", hobbypost.getPostTitle()));
		postParameters.add(new BasicNameValuePair("postContent", hobbypost
				.getContent()));
		postParameters.add(new BasicNameValuePair("postLat", Double
				.toString(hobbypost.getLat())));
		postParameters.add(new BasicNameValuePair("postLng", Double
				.toString(hobbypost.getLng())));
		
		
		postParameters.add(new BasicNameValuePair("posterNric", hobbypost.getPosterNric()));
		try {
			httppost.setEntity(new UrlEncodedFormEntity(postParameters));
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			responseBody = httpclient.execute(httppost, responseHandler);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responseBody;
	}
	
	private void parseJSONResponse(String responseBody){
		JSONArray data_array;
		JSONObject json;
		System.out.println(responseBody);
		try {
			json = new JSONObject(responseBody);
			data_array = json.getJSONArray("hobby");
			
				JSONObject dataJob = new JSONObject(data_array.getString(0));
				hobby = new Hobby();
				hobby.setGroupID(dataJob.getInt("grpID"));
				hobby.setGroupName(dataJob.getString("grpName"));
				hobby.setCategory(dataJob.getString("category"));
				hobby.setDescription(dataJob.getString("grpDesc"));
				hobby.setAdminNric(dataJob.getString("adminNric"));
				hobby.setLat(dataJob.getDouble("Lat"));
				hobby.setLng(dataJob.getDouble("Lng"));
			
		} catch (Exception e) {
			e.printStackTrace();
			errorOnExecuting();
		}
		
	}
	
	private void errorOnExecuting() {
		this.cancel(true);
		new Handler(Looper.getMainLooper()).post(new Runnable() {
			public void run() {
				dialog.dismiss();
				AlertDialog.Builder builder = new AlertDialog.Builder(activity);
				builder.setTitle("Error in retrieving hobby ");
				builder.setMessage("Unable to retrieve hobby. Please try again.");
				builder.setPositiveButton("Ok",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
							}
						});
				builder.create().show();
			}
		});
	}	
}
