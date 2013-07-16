package com.totsp.Database;


import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataHelper extends SQLiteOpenHelper {
	
	static final String dbName="Database7";
	
	static final String contactTable="ContactListTable6";
	static final String contactName="Name";
	static final String contactNo="ContactNo";
	static final String message="Message";
	static final String birthday="Birthday";
	private SQLiteDatabase db;
	private static final String TAG="Data";

	static final String viewEmps="ViewEmps";
	
	public DataHelper(Context context){
		super(context, dbName, null, 1);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {		
		// TODO Auto-generated method stub
		 db.execSQL("CREATE TABLE "+contactTable+" ("+contactName+ " TEXT PRIMARY KEY , "+
				    contactNo+ " TEXT, "+message+" TEXT ,"+birthday+" TEXT)");		 
	}
	
	public int deleteAll(){
		 return db.delete(contactTable, null, null);
		}


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
		db.execSQL("DROP TABLE IF EXISTS "+contactTable);
		 
		  
		 
		  onCreate(db);
		
	}
	
	public Cursor getAllContacts(Activity A){
		SQLiteDatabase db = getReadableDatabase();
		String [] columns=new String[]{contactName,contactNo,message,birthday};
		   Cursor c=db.query(contactTable, columns, null, 
			null, null, null, null);
		   return c;
		
	}
	
	 public List<String> selectAll(Activity A) {
		 SQLiteDatabase db = getReadableDatabase();
	      List<String> list = new ArrayList<String>();
	      Log.i(TAG,"00www");
	      Cursor cursor = this.db.query(contactTable, new String[] { "Name" },"Name like " + "'%sandaruwan%'", null, null, null, null);
	      Log.i(TAG,"11www");
	      if (cursor.moveToFirst()) {
	         do {
	            list.add(cursor.getString(0)); 
	         } while (cursor.moveToNext());
	      }
	      if (cursor != null && !cursor.isClosed()) {
	         cursor.close();
	      }
	      Log.i(TAG,"22www");
	      return list;
	   }
	
	 public List<String> getMessage(Activity A){
		 List<String> all=new ArrayList<String>();
		 List<String> mes=new ArrayList<String>();
		 List<String> con=new ArrayList<String>();
		 SQLiteDatabase db = getReadableDatabase();
		              
		    Cursor cursor = db.query(contactTable, new String[] {"ContactNo","Message"},null, null, null, null, null);
		    if (cursor.moveToFirst()) {
		         do {
		             con.add(cursor.getString(0)); 
		        	 mes.add(cursor.getString(1));
		        	 
		        	
		         } while (cursor.moveToNext());
		      }
		      if (cursor != null && !cursor.isClosed()) {
		         cursor.close();
		      }
		      all.addAll(con);
		      all.addAll(mes);
		      return all;
	 }
	 
	 //get birthday
	 public List<String> getBirthday(Activity A){
		 List<String> birthList=new ArrayList<String>();
		 
		 SQLiteDatabase db = getReadableDatabase();
		             
		    Cursor cursor = db.query(contactTable, new String[] {"Birthday"},null, null, null, null, null);
		    if (cursor.moveToFirst()) {
		         do {
		           
		        	 birthList.add(cursor.getString(0));
		        	 
		        	
		         } while (cursor.moveToNext());
		      }
		      if (cursor != null && !cursor.isClosed()) {
		         cursor.close();
		      }
		      
		      return birthList;
	 }
	 //end of get birth day method
	 
	 //get relevant name
	 public List<String> getRelevantName(Activity A){
		 List<String> birthList=new ArrayList<String>();
		 List<String> contactList=new ArrayList<String>();
		 List<String> allList=new ArrayList<String>();
		
		 SQLiteDatabase db = getReadableDatabase();
		             
		    Cursor cursor = db.query(contactTable, new String[] {"ContactNo","Birthday"},null, null, null, null, null);
		   
		    if (cursor.moveToFirst()) {
		         do {
		           
		        	 birthList.add(cursor.getString(1));
		        	 contactList.add(cursor.getString(0));
		        	
		         } while (cursor.moveToNext());
		      }
		      if (cursor != null && !cursor.isClosed()) {
		         cursor.close();
		      }
		     allList.addAll(contactList);
		     allList.addAll(birthList);
		      return allList;
	 }
	 //end of get relevant name
	 
	 
	 
	 public List<String> getData(Activity A){
		 List<String> names=new ArrayList<String>();
		 
		 SQLiteDatabase db = getReadableDatabase();
		               
		    Cursor cursor = db.query(contactTable, new String[] {"Name"},null, null, null, null, null);
		    if (cursor.moveToFirst()) {
		         do {
		          
		        	 names.add(cursor.getString(0));
		        
		        	
		         } while (cursor.moveToNext());
		      }
		      if (cursor != null && !cursor.isClosed()) {
		         cursor.close();
		      }
		      return names;
	 }
	
	
	 public void delete(String name,Activity A)
	 {
	 String[] args={name};
	 getWritableDatabase().delete(contactTable, "Name=?", args);
	 } 
		
	 
	public String[] cursorToStringArray(Cursor cursor, String ColomnName,Activity A){
		
    	 ArrayList<String> TempArrayList= new ArrayList<String>();
    	 
    	if (cursor.moveToFirst()){
    	   do{
    	      String data = cursor.getString(cursor.getColumnIndex(ColomnName));
    	      TempArrayList.add(data);
    	      System.out.println("taken from database :"+data);
    	      
    	      
    	   }while(cursor.moveToNext());
    	}
    	
    	cursor.close();
    	String[] tempStringArray= new String[TempArrayList.size()];
    	int temp=0;
    	while(!TempArrayList.isEmpty()){
    		tempStringArray[temp]=TempArrayList.remove(0);
    		temp++;
    	}
    	return tempStringArray;
    	
	}
	
	public boolean addContact(String name, String  contactno, String message1, String birth,Activity A){  //add contact details to the database
		try{
			 SQLiteDatabase db= getWritableDatabase();    //get writable database
			 ContentValues cv= new ContentValues();
			 	cv.put(contactName, name);
			 	cv.put(contactNo, contactno);
	        	cv.put(message, message1);
	        	cv.put(birthday, birth);
	        	db.insert(contactTable, null, cv);  //insert into the database
	        	db.close();
	        	return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	



	

}
