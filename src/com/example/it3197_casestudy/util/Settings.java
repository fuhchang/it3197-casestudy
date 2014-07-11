package com.example.it3197_casestudy.util;

import java.text.SimpleDateFormat;
/**
 * This interface is to make coding easier
 * @author Lee Zhuo Xun
 */
public interface Settings {
	String DROPBOX_API_KEY = "cqvf3nim3klslqb";
	//Put your ip address
	String API_URL = "http://192.168.1.7:8080/CommunityOutreach/";
	SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy");
	SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm a");
	SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("dd MMM yyyy h:mm:ss a");
	//E.g. Jul 27, 2014 9:00:00 AM
	SimpleDateFormat sqlDateTimeFormatter = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aa");
}
