package com.example.it3197_casestudy.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * This class is to set the custom shared preferences
 * @author Lee Zhuo Xun
 *
 */
public class MySharedPreferences{
	private SharedPreferences preferences;
	
	/**
	 * This is to initialise the shared preferences
	 * @param context
	 */
	public MySharedPreferences(Context context){
		preferences = PreferenceManager.getDefaultSharedPreferences(context);
	}
	
	public void addPreferences(String name,String value){
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString(name,value);
		editor.apply();
	}
	
	public void addPreferences(String name,int value){
		SharedPreferences.Editor editor = preferences.edit();
		editor.putInt(name,value);
		editor.apply();
	}
	
	public String getPreferences(String name,String defValue){
		return preferences.getString(name,defValue);
	}
	
	public int getPreferences(String name,int defValue){
		return preferences.getInt(name,defValue);
	}
}
