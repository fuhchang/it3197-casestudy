package com.example.it3197_casestudy.ui_logic;

import java.io.File;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import com.example.it3197_casestudy.R;
import com.example.it3197_casestudy.R.layout;
import com.example.it3197_casestudy.R.menu;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CreateHobbyPost extends Activity {
	LinearLayout view1, view2, view3, view4;
	ImageView imgView;
	TextView imgTv, LocAddress, newAddress;
	Button imgUpload;
	String uriOfImage;
	GoogleMap map;
	Location location;
	String locToBeStored = "";
	double lat;
	double Lng;
	String Address;
	String City;
	private boolean gps_enabled = false;
	private boolean network_enabled = false;
	private static final int CAPTURE_PHOTO = 2;
	private static Bitmap Image = null;
	private static final int PICK_IMAGE = 1;
	private static final int selectedLoc = 3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_hobby_post);
		view1 = (LinearLayout) findViewById(R.id.view1);
		view1.setBackgroundColor(Color.LTGRAY);
		view2 = (LinearLayout) findViewById(R.id.view2);
		view2.setBackgroundColor(Color.LTGRAY);
		view3 = (LinearLayout) findViewById(R.id.view3);
		view3.setBackgroundColor(Color.LTGRAY);
		imgView = (ImageView) findViewById(R.id.gImg);
		imgTv = (TextView) findViewById(R.id.imgLink);
		view3.setVisibility(View.INVISIBLE);
		LocAddress = (TextView) findViewById(R.id.LocAddress);
		getMyCurrentLocation();
		newAddress = (TextView) findViewById(R.id.newAddress);
		newAddress.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getBaseContext(), HobbyLoSelection.class);
				intent.putExtra("lat", Double.toString(lat));
				intent.putExtra("Lng", Double.toString(Lng));
				startActivityForResult(intent, selectedLoc);
				
			}
			
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_hobby_post, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.addGallery:
			Intent intent = new Intent();
			intent.setType("image/*");
			// intent.setType("*/*");
			// intent.setAction(Intent.ACTION_GET_CONTENT);
			intent.setAction(Intent.ACTION_PICK);
			startActivityForResult(
					Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
			break;
		case R.id.addCapture:
			Intent intentPicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(intentPicture, CAPTURE_PHOTO);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case PICK_IMAGE:
				view3.setVisibility(View.VISIBLE);
				Uri mImageUri = data.getData();
				try {
					Image = Media.getBitmap(this.getContentResolver(),
							mImageUri);
					view3.setVisibility(View.VISIBLE);

					// uriOfImage = mImageUri.toString();
					uriOfImage = getRealPathFromURI(mImageUri);
					imgTv.setVisibility(View.VISIBLE);
					// String uriOfImage = "<b>Image: </b>"
					// +mImageUri.toString();
					// imageUriTv.setText(Html.fromHtml(uriOfImage));
					// String img = "<u>Image: " +
					// uriOfImage.substring(uriOfImage.lastIndexOf("/") +
					// 1,uriOfImage.length()) + "</u>";
					// imageUriTv.setText(Html.fromHtml(img));
					imgTv.setText(uriOfImage.substring(
							uriOfImage.lastIndexOf("/") + 1,
							uriOfImage.length()));
					imgView.setVisibility(View.VISIBLE);
					Image = decodeSampledBitmapFromResource(uriOfImage, 140,
							300);
					imgView.setImageBitmap(Image);
					// Added
					final int rotateImage = getCameraPhotoOrientation(this,
							mImageUri, uriOfImage);

					Matrix matrix = new Matrix();
					imgView.setScaleType(ScaleType.MATRIX); // required
					matrix.postRotate((float) rotateImage, imgView
							.getDrawable().getBounds().width() / 2, imgView
							.getDrawable().getBounds().height() / 2);
					imgView.setImageMatrix(matrix);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case CAPTURE_PHOTO:
				Uri capturedImageUri = data.getData();
				InputStream imageStream = null;
				try {
					imageStream = getContentResolver().openInputStream(
							capturedImageUri);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Bitmap yourSelectedImage = BitmapFactory
						.decodeStream(imageStream);

				uriOfImage = getRealPathFromURI(capturedImageUri);

				final int rotateImage = getCameraPhotoOrientation(this,
						capturedImageUri, uriOfImage);
				view3.setVisibility(View.VISIBLE);
				yourSelectedImage = decodeSampledBitmapFromResource(uriOfImage,
						140, 300);
				imgView.setImageBitmap(yourSelectedImage);
				int width = imgView.getWidth();
				int height = imgView.getHeight();

				Matrix matrix = new Matrix();
				imgView.setScaleType(ScaleType.MATRIX); // required
				matrix.postRotate((float) rotateImage, imgView.getDrawable()
						.getBounds().width() / 2, imgView.getDrawable()
						.getBounds().height() / 2);
				imgView.setImageMatrix(matrix);
				break;
			case selectedLoc:
				String selectedAdd = data.getStringExtra("selectedAdd");
				 String selectedLat = data.getStringExtra("selectedLat");
				 String selectedLon = data.getStringExtra("selectedLon");
				 LocAddress.setText(selectedAdd);
				break;
			}

		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public String getRealPathFromURI(Uri contentUri) {

		// can post image
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(contentUri, proj, // Which columns to
														// return
				null, // WHERE clause; which rows to return (all rows)
				null, // WHERE clause selection arguments (none)
				null); // Order-by clause (ascending by name)
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();

		return cursor.getString(column_index);
	}

	public int getCameraPhotoOrientation(Context context, Uri imageUri,
			String imagePath) {
		int rotate = 0;
		try {
			context.getContentResolver().notifyChange(imageUri, null);
			File imageFile = new File(imagePath);

			ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
			int orientation = exif.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);

			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_270:
				rotate = 270;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				rotate = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_90:
				rotate = 90;
				break;
			default:
				rotate = 0;
				break;
			}

			Log.i("RotateImage", "Exif orientation: " + orientation);
			Log.i("RotateImage", "Rotate value: " + rotate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rotate;
	}

	public static Bitmap decodeSampledBitmapFromResource(String pathName,
			int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(pathName, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(pathName, options);
	}

	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and
			// keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}

		return inSampleSize;
	}

	void getMyCurrentLocation() {

		LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		try {
			gps_enabled = locManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);
		} catch (Exception ex) {

		}
		try {
			network_enabled = locManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		} catch (Exception ex) {

		}

		if (gps_enabled) {
			location = locManager
					.getLastKnownLocation(LocationManager.GPS_PROVIDER);

		}

		if (network_enabled && location == null) {
			location = locManager
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

		}

		if (location != null) {
			// accLoc=location.getAccuracy();
			lat = location.getLatitude();
			Lng = location.getLongitude();
		}

		try {
			// Getting address based on coordinates.
			Geocoder geocoder;
			List<Address> addresses;
			geocoder = new Geocoder(this, Locale.getDefault());
			addresses = geocoder.getFromLocation(lat, Lng, 1);

			Address = addresses.get(0).getAddressLine(0);
			City = addresses.get(0).getAddressLine(1);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (Address != null && !Address.isEmpty()) {
			// mp.position(new LatLng(location.getLatitude(),
			// location.getLongitude()));
			// mp.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
			try {
				// Getting address based on coordinates.
				Geocoder geocoder;
				List<Address> addresses;
				geocoder = new Geocoder(this, Locale.getDefault());
				addresses = geocoder.getFromLocation(lat, Lng, 1);

				Address = addresses.get(0).getAddressLine(0);
				City = addresses.get(0).getAddressLine(1);
				
			} catch (Exception e) {
				e.printStackTrace();
			}

			// mp.title(Address + ", " + City);

			locToBeStored = Address + "\n" + City;
			

			LocAddress.setText("Estimated location: \n" + locToBeStored);

			
		} else {
			AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
			builder1.setTitle("Service Unavailable");
			builder1.setMessage("Unable to get your location, check if your GPS and Network are turned on.");
			builder1.setCancelable(true);
			builder1.setNegativeButton("OK",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});
			AlertDialog alert11 = builder1.create();
			alert11.show();

			LocAddress.setText("opps please check you gps.");
		}
	}
}
