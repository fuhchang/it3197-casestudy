package com.example.it3197_casestudy.model;

public class RowItem {
private String title;
private int icon;
public RowItem(String title, int icon) {
	// TODO Auto-generated constructor stub
	this.title = title;
	this.icon = icon;
}
/**
 * @return the title
 */
public String getTitle() {
	return title;
}
/**
 * @param title the title to set
 */
public void setTitle(String title) {
	this.title = title;
}
/**
 * @return the icon
 */
public int getIcon() {
	return icon;
}
/**
 * @param icon the icon to set
 */
public void setIcon(int icon) {
	this.icon = icon;
}


}
