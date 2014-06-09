package kr.ac.kumoh.ce.mobile;

import com.thedon.MapSearch.R;

import kr.ac.kumoh.ce.mobile.dbhelper.WordDBHelper; 
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class conView extends Activity {
	private static final String DEBUG_TAG = "withpd";
	final static String DB_ALARM = "alarmlistdb";
	private WordDBHelper helper;
	private Button modi_btn;
	private Button dele_btn;
	private TextView conView;
	private int index;
	private SQLiteDatabase db;
	Cursor cursor;
	String con;
	View v;
	int alarm_count;
	ContentValues row;
	String mTitle;
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.conview);
	   // modi_btn = (Button)findViewById(R.id.modify);
	    dele_btn = (Button)findViewById(R.id.delete);
	    conView = (TextView)findViewById(R.id.content);
        helper = new WordDBHelper(this);
        db = helper.getWritableDatabase();
        //helper = new WordDBHelper(this);
	    
	    Intent intent = getIntent();
	    index = intent.getExtras().getInt("position");
        cursor = db.rawQuery("SELECT * FROM memo", null);
        startManagingCursor(cursor);
        
        cursor.moveToPosition(index);
    	con = cursor.getString(2);
    	mTitle = cursor.getString(0);
        conView.setText(con);
        helper.close();
	}
	public void mOnClick(View v){
		switch(v.getId()){
		case R.id.delete :
			db = helper.getWritableDatabase();
//			cursor.moveToPosition(index);
			Log.i(DEBUG_TAG,"delete 의 index값 : "+index);
//			db.execSQL("DELETE FROM memo WHERE _id="+0+";");
			db.execSQL("DELETE FROM memo ");
			helper.close();
			Intent intent = new Intent(this, kr.ac.kumoh.ce.mobile.MainActivity.class);
			startActivity(intent);
			break;
		}
	}
	
	/*public void input_alarm_count(final View v) {

		final LinearLayout alarm_count_dialog = (LinearLayout) View.inflate(this,
				R.layout.input_alarm_count, null);
		final EditText alarm_input_count = (EditText) alarm_count_dialog
		.findViewById(R.id.alam_input_count);

		new AlertDialog.Builder(this)
				.setTitle("알람갯수를 입력해 주세요.")
				.setView(alarm_count_dialog)
				.setPositiveButton("확인", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						
						String alarm_count_info = alarm_input_count.getText().toString();
						if(alarm_count_info.equals("")){
							input_alarm_count(v);
							Toast.makeText(conView.this, "알람갯수를 입력해주세요.", Toast.LENGTH_LONG).show();
						} else{
						    alarm_count = Integer.parseInt(alarm_count_info);
						    input_alarm_info(v);
						}
					}
				})
				.setNegativeButton("취소", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}
				}).show();
	}
	
	public void input_alarm_info(final View v){
		final LinearLayout alarm_info_dialog = (LinearLayout)View.inflate(this, R.layout.alarm_create, null);
		final DatePicker Alarm_Date =(DatePicker) alarm_info_dialog.findViewById(R.id.Alarm_Date);
		final TimePicker Alarm_Time = (TimePicker) alarm_info_dialog.findViewById(R.id.Alarm_Time);
		final EditText Alarm_con = (EditText) alarm_info_dialog.findViewById(R.id.alarm_title);
			
		new AlertDialog.Builder(this)
		.setTitle("알람정보를 입력해 주세요.")
		.setView(alarm_info_dialog)
		.setPositiveButton("확인", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				 int mYear, mMonth, mDay, mHour, mMinute;
				 String mAlarm_con = Alarm_con.getText().toString();
				 
				 mYear = Alarm_Date.getYear();
				 mMonth = Alarm_Date.getMonth();
				 mDay = Alarm_Date.getDayOfMonth();
				 
				 mHour = Alarm_Time.getCurrentHour();
				 mMinute = Alarm_Time.getCurrentMinute();
				 
					Cursor cursor_alarm_insert;
					
					String[] columns_alarm_insert = { "memo_title", "alarm_con", "alarm_year", "alarm_month", "alarm_day", "alarm_hour", "alarm_minute", "alarm_id"};
					
					db = helper.getWritableDatabase(); 
					
					Log.i(DEBUG_TAG,"conView test : DB_ALARM :"+DB_ALARM+"columns_alarm_insert : "+columns_alarm_insert);

					cursor_alarm_insert = db.query(false, DB_ALARM, columns_alarm_insert, null,
							null, null, null, null, null);
					int now_alarm_id = 1;
					
					cursor_alarm_insert.moveToFirst();
					while(!cursor_alarm_insert.isAfterLast()){
						if(mTitle.equals(cursor_alarm_insert.getString(0))){
							now_alarm_id++;
						}
						cursor_alarm_insert.moveToNext();
					}
					row = new ContentValues();
					// db.execSQL("INSERT INTO memo VALUES();");
					row.put("memo_title", mTitle);
					row.put("alarm_con", mAlarm_con);
					row.put("alarm_year", mYear);
					row.put("alarm_month", mMonth);
					row.put("alarm_Day", mDay);
					row.put("alarm_Hour", mHour);
					row.put("alarm_Minute", mMinute);
					row.put("alarm_id", now_alarm_id);
					db.insert(DB_ALARM, null, row);
					
					cursor_alarm_insert.close();
					db.close();
				 
				 alarm_count--;
				 if(alarm_count > 0){
					 input_alarm_info(v);
				 }
			}		
		})
		.setNegativeButton("취소", new DialogInterface.OnClickListener() {

		
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				alarm_count = 0;
			}
		}).show();
	}*/
}