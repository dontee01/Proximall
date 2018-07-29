/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 * */
package com.example.proxymall.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

public class SQLiteHandler extends SQLiteOpenHelper {

	private static final String TAG = SQLiteHandler.class.getSimpleName();

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "ProxyMall";

	// Login table name
	private static final String TABLE_USER = "user";

	// Login Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_USER_ID = "userId";
	private static final String KEY_PHONENUMBER = "phoneNumber";
	private static final String KEY_CREATED_AT = "created_at";
	private static final String KEY_DEPARTMENT = "department";
	private static final String KEY_INSTITUTION = "institution";
	private static final String KEY_ABOUT = "about";
	private static final String KEY_PROFILE_PIC = "profile_pic";

	private static final String KEY_TABLE_CART = "cart";
	private static final String KEY_PRODUCT_ID = "id";
	private static final String KEY_MALL_ID = "mallId";
	private static final String KEY_UNIT = "unit";

	public SQLiteHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
				+ KEY_EMAIL + " TEXT UNIQUE,"+ KEY_PHONENUMBER + " TEXT," + KEY_USER_ID + " TEXT,"
				+ KEY_CREATED_AT + " TEXT," +  KEY_PROFILE_PIC + " BLOB" +")";
		db.execSQL(CREATE_LOGIN_TABLE);

		Log.d(TAG, "Database tables created");
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

		// Create tables again
		onCreate(db);
	}


	public void addUser(String name, String email, String phoneNumber,String uid, String created_at,byte[] profile_pic ) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, name); // Name
		values.put(KEY_EMAIL, email);
		values.put(KEY_PHONENUMBER, phoneNumber);// Email
		values.put(KEY_USER_ID, uid); // Email
		values.put(KEY_CREATED_AT, created_at); // Created At
//		values.put(KEY_DEPARTMENT, department);
//		values.put(KEY_INSTITUTION, institution); // Email
//		values.put(KEY_ABOUT, about); // Created At
		values.put(KEY_PROFILE_PIC, profile_pic);

		// Inserting Row
		long id = db.insert(TABLE_USER, null, values);
		db.close(); // Closing database connection

		Log.d(TAG, "New user inserted into sqlite: " + id);
	}


	public HashMap<String, byte[]> getUserPic() {

		HashMap<String, byte[]> userPic = new HashMap<>();
		String selectQuery = "SELECT  * FROM " + TABLE_USER;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {
			userPic.put("profile_pic", cursor.getBlob(9));


		}
		cursor.close();
		db.close();
		// return user
		//Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

		return userPic;
	}

	public void AddUserAdditionalInformation(String department,String about,String userId,byte[] profilePic) {
		SQLiteDatabase db = this.getReadableDatabase();


		//Your Update to SQLite
		db = this.getReadableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_DEPARTMENT, department);
		values.put(KEY_ABOUT, about);
		values.put(KEY_PROFILE_PIC, profilePic);

		db.update(TABLE_USER, values, KEY_USER_ID + "=?", new String[]{userId});
		db.close();

		Log.d(TAG, "Imformation Added successfully!");


	}


	public HashMap<String, String> getUserDetails() {
		HashMap<String, String> user = new HashMap<String, String>();
		//HashMap<String, byte[]> userPic = new HashMap<>();
		String selectQuery = "SELECT  * FROM " + TABLE_USER;

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		// Move to first row
		cursor.moveToFirst();
		if (cursor.getCount() > 0) {
			user.put("name", cursor.getString(1));
			user.put("email", cursor.getString(2));
			user.put("phoneNumber", cursor.getString(3));
			user.put("userId", cursor.getString(4));
			user.put("created_at", cursor.getString(5));
			user.put("department", cursor.getString(6));
			user.put("institution", cursor.getString(7));
			user.put("about", cursor.getString(8));
		//	userPic.put("profile_pic", cursor.getBlob(9));

		}
		cursor.close();
		db.close();
		// return user
		Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

		return user;
	}

	/**
	 * Re crate database Delete all tables and create them again
	 * */
	public void deleteUsers() {
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(TABLE_USER, null, null);
		db.close();

		Log.d(TAG, "Deleted all user info from sqlite");
	}


}
