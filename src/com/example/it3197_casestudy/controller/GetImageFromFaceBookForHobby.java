package com.example.it3197_casestudy.controller;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.Toast;

public class GetImageFromFaceBookForHobby extends AsyncTask<Object,Object,Object>{
	private ImageView imgView;
	private Activity activity;
	private String filename;
	private ProgressDialog dialog;
	private Bitmap myBitmap;
	public GetImageFromFaceBookForHobby (Activity activity, ImageView imgView, String filename){
		this.activity = activity;
		this.imgView = imgView;
		this.filename = filename;
	}
	@Override
	protected Object doInBackground(Object... arg0) {
		// TODO Auto-generated method stub
		return  getImageFromFaceBook();
	}

	@Override
	protected void onPostExecute(Object result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		dialog.dismiss();
		imgView.setImageBitmap(myBitmap);
	}

	@Override
	protected void onPreExecute() {
		dialog = ProgressDialog.show(activity, "Getting Image", "Please wait...",
				true);
	}
	
	public String getImageFromFaceBook(){
		
		try{
			URL url = new URL(filename);
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
		return filename;
		
	}
	
	private void errorOnExecuting(){
		this.cancel(true);
		new Handler(Looper.getMainLooper()).post(new Runnable() {
	        public void run() {
	        	dialog.dismiss();
	            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
	            builder.setTitle("Error in retrieve Image ");
	            builder.setMessage("Unable to retrieve Image. Please try again.");
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
}
