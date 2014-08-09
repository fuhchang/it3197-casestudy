package com.example.it3197_casestudy.model;

import com.google.android.gms.maps.model.LatLng;

public class MyItem {//implements ClusterItem {
    private final LatLng mPosition;
    private final String mTitle;
    private final String mAddress;
    private final String mHyperLink;
    
    public MyItem(String title, String address, String hyperLink, double lat, double lng) {
    	mTitle = title;
    	mAddress = address;
    	mHyperLink = hyperLink;
        mPosition = new LatLng(lat, lng);
    }
    
    public LatLng getPosition() {
        return mPosition;
    }

	public String getTitle() {
		return mTitle;
	}

	public String getAddress() {
		return mAddress;
	}

	public String getHyperLink() {
		return mHyperLink;
	}
}
