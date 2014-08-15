IT3197 Casestudy -- Community Outreach
================
CONTENTS OF THIS FILE
---------------------
* Introduction
* Requirements
* Installation
* Installation for backend  

Introduction
---------------------

Requirements
---------------------
In order to use this application, you will need:
* Dropbox SDK
* Facebook SDK
* Google Play Services Lib
* Android Map Util
* Android Map Extension

For the backend, you will need: 
* Tomcat v7 server
* MySQL Server v5.5
* MySQL Workbench v6.0
* Java JDK v7
* Eclipse with J2ee development kit
* javax.mail.jar
* gson-2.2.4.jar
* jstl-1.2.jar
* mysql-connector-5.1.29.jar

Installation
---------------------
* 1) After installing all the necessary SDK above, copy and paste all of the SDK into the project's workspace
* 2) Import all the necessary SDK into the eclipse workspace. (Note: Use the General -> File System)
* 3) Go to the project -> properties -> Android and add all the necessary SDK as a library project.

Installation for backend
-------------------------
* 1) Install Tomcat Server, MySQL Server and Workbench
* 2) Install Eclipse with j2ee. (Note: You can also use Android All-in-one Eclipse but you will need to install j2ee software in order to use both android and j2ee at the same time.)
* 3) If necessary, install java jdk v7
* 4) Copy and paste the jar files above into the WebContent -> WEB-INF -> lib folder of the project.


Note before running the application:
* Please ensure that the Tomcat Server has started.
