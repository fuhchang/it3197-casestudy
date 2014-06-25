package com.example.it3197_casestudy.util;

import java.text.SimpleDateFormat;

/**
 * This interface is to make coding easier
 * @author Lee Zhuo Xun
 */
public interface Settings {
	String DROPBOX_API_KEY = "cqvf3nim3klslqb";
	//String API_URL = "http://192.168.1.185/CommunityOutreach/a/";
	String API_URL = "http://192.168.43.8/CommunityOutreach/a/";
	SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy");
	SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm a");
	SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("d MMM yyyy h:mm a");
}
