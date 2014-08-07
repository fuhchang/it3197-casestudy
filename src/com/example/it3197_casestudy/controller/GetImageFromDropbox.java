package com.example.it3197_casestudy.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.it3197_casestudy.ui_logic.ViewAllEventsActivity;
import com.example.it3197_casestudy.util.MySharedPreferences;

public class GetImageFromDropbox extends AsyncTask<Object,Object,Object>{
	private Context context;
	private ImageView ivEventPoster;
	private String posterFileName;
	private Bitmap myBitmap;
	private ProgressDialog dialog;
	private String simplifiedPosterFileName;
	
	public GetImageFromDropbox(Context context,ImageView ivEventPoster, String posterFileName){
		this.context = context;
		this.ivEventPoster = ivEventPoster;
		this.posterFileName = posterFileName;
	}
	
	@Override
	protected void onPreExecute() {
		dialog = ProgressDialog.show(context,"Retrieving Poster", "Please wait");
	}

	@Override
	protected String doInBackground(Object... arg0) {
		return getImageFromDropbox();
	}

	@Override
	protected void onPostExecute(Object result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		ivEventPoster.setVisibility(View.VISIBLE);
        dialog.dismiss();
	}

	public String getImageFromDropbox() {
		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		if (currentapiVersion <= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2){
			new Handler(Looper.getMainLooper()).post(new Runnable() {
			    @Override
			    public void run() {
					try {
						URL url = new URL(posterFileName);
						simplifiedPosterFileName = posterFileName.substring(posterFileName.lastIndexOf("/"),posterFileName.length() - 5);
						File f = new File(context.getCacheDir(), simplifiedPosterFileName);
						if(!f.exists()){
							InputStream is = url.openStream();
							ByteArrayOutputStream os = new ByteArrayOutputStream();			
							byte[] buf = new byte[4096];
							int n;			
							while ((n = is.read(buf)) >= 0) 
								os.write(buf, 0, n);
							os.close();
							is.close();			
							byte[] data = os.toByteArray();
							myBitmap = BitmapFactory.decodeByteArray(data, 0, os.size());
							
							f.createNewFile();
							FileOutputStream fos = new FileOutputStream(f);
							fos.write(data);
							fos.close();
							ivEventPoster.setImageBitmap(myBitmap);
						}
						else{
							ivEventPoster.setImageURI(Uri.fromFile(f));
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    }
			});
		}
		else{
			try {
				URL url = new URL(posterFileName);
				simplifiedPosterFileName = posterFileName.substring(posterFileName.lastIndexOf("/"),posterFileName.length() - 5);
				File f = new File(context.getCacheDir(), simplifiedPosterFileName);
				if(!f.exists()){
					InputStream is = url.openStream();
					ByteArrayOutputStream os = new ByteArrayOutputStream();			
					byte[] buf = new byte[4096];
					int n;			
					while ((n = is.read(buf)) >= 0) 
						os.write(buf, 0, n);
					os.close();
					is.close();			
					byte[] data = os.toByteArray();
					myBitmap = BitmapFactory.decodeByteArray(data, 0, os.size());
					
					f.createNewFile();
					FileOutputStream fos = new FileOutputStream(f);
					fos.write(data);
					fos.close();
					ivEventPoster.setImageBitmap(myBitmap);
				}
				else{
					ivEventPoster.setImageURI(Uri.fromFile(f));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	private void errorOnExecuting(){
		this.cancel(true);
		new Handler(Looper.getMainLooper()).post(new Runnable() {
	        public void run() {
	        	dialog.dismiss();
	            AlertDialog.Builder builder = new AlertDialog.Builder(context);
	            builder.setTitle("Error in retrieve poster ");
	            builder.setMessage("Unable to retrieve poster. Please try again.");
	            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
			            cancel(true);
					}
				});
	            builder.create().show();
	        }
	    });
	}
	
	public File getCacheFolder(Context context) {
		File cacheDir = null;
	        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
	            cacheDir = new File(Environment.getExternalStorageDirectory(), "cachefolder");
	            if(!cacheDir.isDirectory()) {
	            	cacheDir.mkdirs();
	            }
	        }
	        
	        if(!cacheDir.isDirectory()) {
	            cacheDir = context.getCacheDir(); //get system cache folder
	        }
	        
		return cacheDir;
	}
}
