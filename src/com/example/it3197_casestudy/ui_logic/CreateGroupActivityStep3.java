package com.example.it3197_casestudy.ui_logic;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.model.Hobby;
import com.example.it3197_casestudy.mysqlite.hobbySQL;
import com.example.it3197_casestudy.mysqlite.mySqLiteController;

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
public class CreateGroupActivityStep3 extends Activity {
	Button imgUpload;
	String[] mUploadImg;
	static int selected;
	Button btnNext;
	String uriOfImage , title, type, gDesc;
	ImageView imgView;
	byte[] blobImg;
	Hobby hobby;
	hobbySQL con;
	
	private static Bitmap Image = null;
	private static final int PICK_IMAGE = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_group_activity_step3);
		mUploadImg = getResources().getStringArray(R.array.imgBy);
		imgUpload = (Button) findViewById(R.id.uploadImg);
		imgView = (ImageView) findViewById(R.id.gImg);
		btnNext = (Button) findViewById(R.id.btnNext);
		btnNext.setVisibility(View.INVISIBLE);
		title = getIntent().getStringExtra("eventName");
		type = getIntent().getStringExtra("category");
		gDesc = getIntent().getStringExtra("eventDesc");
		imgUpload.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_PICK);
				startActivityForResult(
						Intent.createChooser(intent, "Select Picture"),
						PICK_IMAGE);

			}

		});
		
		btnNext.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(CreateGroupActivityStep3.this, CreateGroupActivityStep4.class);
				intent.putExtra("eventName", title);
				intent.putExtra("category", type);
				intent.putExtra("eventDesc", gDesc);
				intent.putExtra("nric", getIntent().getExtras().getString("nric"));
				startActivity(intent);
			}
			
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case PICK_IMAGE:
				Uri imageUri = data.getData();

				try {
					uriOfImage = imageUri.toString();
					Image = Media
							.getBitmap(this.getContentResolver(), imageUri);
					imgView.setImageBitmap(Image);
					btnNext.setVisibility(View.VISIBLE);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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

	public byte[] convertImage(String uri) throws IOException {
		int bufferSize = 1024;
		Uri imageUri = Uri.parse(uri);
		InputStream iStream = getContentResolver().openInputStream(imageUri);
		byte[] buffer = new byte[bufferSize];
		ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
		int len = 0;
		while ((len = iStream.read(buffer)) != 1) {
			byteBuffer.write(buffer, 0, len);
		}
		return byteBuffer.toByteArray();

	}

	

}
