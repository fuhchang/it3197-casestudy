package com.example.it3197_casestudy.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;

public class GetImageFromFacebook extends AsyncTask<Object,Object,Object>{
	private Context context;
	private ImageView ivEventPoster;
	private String posterFileName;
	private Bitmap myBitmap;
	private ProgressDialog dialog;
	private String simplifiedPosterFileName;
	
	
	public GetImageFromFacebook(Context context,ImageView ivEventPoster, String posterFileName){
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
		return getImageFromFaceBook();
	}

	@Override
	protected void onPostExecute(Object result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		ivEventPoster.setVisibility(View.VISIBLE);
		ivEventPoster.setImageBitmap(myBitmap);
        dialog.dismiss();
	}

	public String getImageFromFaceBook(){
		
		try{
			URL url = new URL(posterFileName);
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
			
		}catch(Exception e){
			e.printStackTrace();
			errorOnExecuting();
		}
		return posterFileName;
		
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
