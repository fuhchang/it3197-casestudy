package com.example.it3197_casestudy.ui_logic;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.dropbox.chooser.android.DbxChooser;
import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.controller.GetImageFromDropbox;
import com.example.it3197_casestudy.model.Hobby;
import com.example.it3197_casestudy.mysqlite.hobbySQL;
import com.example.it3197_casestudy.mysqlite.mySqLiteController;
import com.example.it3197_casestudy.util.Settings;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.app.DialogFragment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class CreateGroupActivityStep3 extends Activity implements Settings{
	Button imgUpload;
	String[] mUploadImg;
	static int selected;
	Button btnNext;
	String uriOfImage , title, type, gDesc;
	ImageView imgView;
	byte[] blobImg;
	Hobby hobby;
	hobbySQL con;
	byte[] byteImg;
	String path;
	private DbxChooser mChooser;
	private static Bitmap Image = null;
	private static final int PICK_IMAGE = 1;
	static final int DBX_CHOOSER_REQUEST = 0;
	private String posterFileName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_group_activity_step3);
		mUploadImg = getResources().getStringArray(R.array.imgBy);
		imgUpload = (Button) findViewById(R.id.uploadImg);
		imgView = (ImageView) findViewById(R.id.gImg);
		title = getIntent().getStringExtra("eventName");
		type = getIntent().getStringExtra("category");
		gDesc = getIntent().getStringExtra("eventDesc");
		mChooser = new DbxChooser(DROPBOX_API_KEY);	
		
		imgUpload.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				mChooser.forResultType(DbxChooser.ResultType.PREVIEW_LINK).launch(CreateGroupActivityStep3.this,  DBX_CHOOSER_REQUEST);
			}
			
		});
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId()){
		case R.id.action_next:
			if(!posterFileName.equals("")){
				Intent intent = new Intent(this, CreateGroupActivityStep4.class);
				intent.putExtra("hobbyName", title);
				intent.putExtra("category", type);
				intent.putExtra("hobbyDesc", gDesc);
				intent.putExtra("imgPath", posterFileName);
				intent.putExtra("nric", getIntent().getExtras().getString("nric"));
				startActivity(intent);
			}
			/*
			try {
				
				byteImg =  fileToByteArray(path);
				if(byteImg.length > 0){
				Toast.makeText(getApplicationContext(), Integer.toString(byteImg.length), Toast.LENGTH_LONG).show();
				Intent intent = new Intent(this, CreateGroupActivityStep4.class);
				intent.putExtra("eventName", title);
				intent.putExtra("category", type);
				intent.putExtra("eventDesc", gDesc);
				intent.putExtra("byteImg", byteImg);
				intent.putExtra("imgPath", path);
				intent.putExtra("nric", getIntent().getExtras().getString("nric"));
				startActivity(intent);
				}else{
					Toast.makeText(getApplicationContext(), byteImg.length, Toast.LENGTH_LONG).show();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Toast.makeText(getApplicationContext(), "Please check your image", Toast.LENGTH_LONG).show();
			}
			*/
			break;
		}
		return super.onOptionsItemSelected(item);
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case DBX_CHOOSER_REQUEST:
				 DbxChooser.Result result = new DbxChooser.Result(data);
				 Log.d("main", "Link to selected file name: " + result.getName());
				 String fileName = result.getName();
				 posterFileName = result.getLink().toString() + "?dl=1";
				 GetImageFromDropbox getImageFromDropbox = new GetImageFromDropbox(CreateGroupActivityStep3.this,imgView, posterFileName,fileName);
		          getImageFromDropbox.execute();
		          imgView.setVisibility(View.VISIBLE);
				break;
			}
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_group_activity_step3, menu);
		return true;
	}

	
	public static byte[] fileToByteArray(String path) throws IOException {
	    File imagefile = new File(path);
	    byte[] data = new byte[(int) imagefile.length()];
	    FileInputStream fis = new FileInputStream(imagefile);
	    fis.read(data);
	    fis.close();
	    return data;
	}
	
	// And to convert the image URI to the direct file system path of the image file
	public String getRealPathFromURI(Uri contentUri) {

	        // can post image
	        String [] proj={MediaStore.Images.Media.DATA};
	        Cursor cursor = managedQuery( contentUri,
	                        proj, // Which columns to return
	                        null,       // WHERE clause; which rows to return (all rows)
	                        null,       // WHERE clause selection arguments (none)
	                        null); // Order-by clause (ascending by name)
	        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	        cursor.moveToFirst();

	        return cursor.getString(column_index);
	}

}
