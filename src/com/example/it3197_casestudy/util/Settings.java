package com.example.it3197_casestudy.util;

import java.text.SimpleDateFormat;
/**
 * This interface is to make coding easier
 * @author Lee Zhuo Xun
 */
public interface Settings {
	String DROPBOX_API_KEY = "cqvf3nim3klslqb";
	//Put your ip address
	//String API_URL = "http://172.27.178.193:8080/CommunityOutreach/";
	String API_URL = "http://192.168.1.4:8080/CommunityOutreach/";
	//String API_URL = "http://127.6.36.3:8080/";
	SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy");
	SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm a");
	SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
	//E.g. Jul 27, 2014 9:00:00 AM
	SimpleDateFormat sqlDateTimeFormatter = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss aa");
	SimpleDateFormat sqliteDateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	SimpleDateFormat fbDateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
}
