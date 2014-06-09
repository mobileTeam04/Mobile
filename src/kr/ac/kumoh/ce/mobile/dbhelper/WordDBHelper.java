package kr.ac.kumoh.ce.mobile.dbhelper;

import android.content.Context; 
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class WordDBHelper extends SQLiteOpenHelper {
	private static final String DEBUG_TAG = "withpd";
	public WordDBHelper(Context context) {
		super(context, "memo.db", null, 1); 
		Log.i(DEBUG_TAG,"WordDBHelper 생성자 시작!");
	}
 
	public void onCreate(SQLiteDatabase db) { 
		Log.i(DEBUG_TAG,"WordDBHelper 에서 table 생성"); 
		db.execSQL("CREATE TABLE memo (_id INTEGER PRIMARY KEY AUTOINCREMENT,"+" title TEXT, con TEXT, pw TEXT, open TEXT, alarm TEXT);");
		db.execSQL("CREATE TABLE alarmlistdb ( _id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "memo_title STRING, alarm_con STRING, alarm_year INTEGER, alarm_month INTEGER, alarm_day INTEGER, alarm_hour INTEGER, alarm_minute INTEGER, alarm_id INTEGER);");
	}   
 
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.i(DEBUG_TAG,"WordDBHelper 에서 table Upgrade"); 
		db.execSQL("DROP TABLE IF EXISTS memo");
		db.execSQL("DROP TABLE IF EXISTS alarmlistdb");
		onCreate(db);
	}
}