package kr.ac.kumoh.ce.mobile;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.os.Build;

import java.util.Calendar;

import com.thedon.MapSearch.R;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.DatePicker;
import android.widget.TimePicker;
import kr.ac.kumoh.ce.mobile.Note_Save;

public class WriteActivity extends ActionBarActivity{
	private static final String DEBUG_TAG = "withpd";
	EditText etDate, etTime, etPlace;
	ImageButton imageDate, imageTime, imagePlace;
	Button saveBtn, cancleBtn;
	private int  mMonth, mYear, mDay, mHour, mMin;
	String buffer;
	private String open;

	public static final int DTPKR = 1;
	public static final int TMPKR = 2;
	public WriteActivity(){
		Log.i(DEBUG_TAG,"Note_Write 생성자 시작");
		open = "공개";
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_write);
		imageDate = (ImageButton) findViewById(R.id.calendarBtn);
		imageTime = (ImageButton) findViewById(R.id.clockBtn);
		imagePlace = (ImageButton) findViewById(R.id.placeBtn); //위치받아오는 버튼
		saveBtn = (Button) findViewById(R.id.savebtn);
		cancleBtn = (Button) findViewById(R.id.canclebtn);
		etDate=(EditText) findViewById(R.id.etDate);
		etTime=(EditText) findViewById(R.id.etTime);
		etPlace=(EditText) findViewById(R.id.etPlace);
		getCurrentDate();
		getCurrentTime();
		 Log.i(DEBUG_TAG,"Note_Write onCreate 끝 인텐트 받음"); 
		
		imageDate.setOnClickListener(new OnClickListener() {
			@Override

		     public void onClick(View v) {     
		      showDialog(DTPKR);
		     }
			

		    });
		imageTime.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(TMPKR);
				}
			}); 
		imagePlace.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Intent placeIntent = new Intent(WriteActivity.this, com.thedon.MapSearch.MapSearchActivity.class);
				startActivity(placeIntent);
			}
		});
		saveBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				Log.i(DEBUG_TAG,"mOnClick 의 R.id.save 로 들어왔다.");
				if(etDate.getText().toString().equals("")||etTime.getText().toString().equals("")){
					 Toast.makeText(WriteActivity.this, "제목과 내용을 입력하세요.", Toast.LENGTH_SHORT).show();
					 return;
				}
				else{
					 Log.i(DEBUG_TAG,"mOnClick 의 R.id.save의 else 문 시작");
					 Intent intent = new Intent(WriteActivity.this, Note_Save.class);
					 intent.putExtra("title", etDate.getText().toString());
					 intent.putExtra("con", etTime.getText().toString());
					 intent.putExtra("open",open);
					 intent.putExtra("check", "save");
					 Log.i(DEBUG_TAG,"open 값 : "+ open);
					 Log.i(DEBUG_TAG,"mOnClick 의 R.id.save의 else 문 끝");
					 Log.i(DEBUG_TAG,"Note_List로 넘어가기 위해 Activity 실행!");
					 startActivity(intent);
				 } 
			}
		});
		cancleBtn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v){
				finish();
			}
		});
	}

	public void getCurrentDate(){
	     final Calendar c = Calendar.getInstance();
	      mYear = c.get(Calendar.YEAR);
	      mMonth = c.get(Calendar.MONTH);
	      mDay = c.get(Calendar.DAY_OF_MONTH);

	} 
	public void getCurrentTime(){
		final Calendar c = Calendar.getInstance();
		mHour = c.get(Calendar.HOUR_OF_DAY);
		mMin = c.get(Calendar.MINUTE);
	} 
	
	protected Dialog onCreateDialog(int id) {

	      switch (id) {
	      case DTPKR:
	          return new DatePickerDialog(this,lisDate, mYear, mMonth, mDay);
	      case TMPKR:
	          return new TimePickerDialog(this,lisTime,mHour, mMin, false);
	      }
	      return null;
	  }
	 DatePickerDialog.OnDateSetListener lisDate = new DatePickerDialog.OnDateSetListener() {
		 @Override
		 public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
	     mYear = year;
	     mMonth = monthOfYear;
	     mDay = dayOfMonth;
	     etDate.setText(new StringBuilder() .append(mDay).append("/").append(mMonth+1).append("/").append(mYear));
	     getCurrentDate();   
	     }
		 };

		 TimePickerDialog.OnTimeSetListener lisTime=new TimePickerDialog.OnTimeSetListener() {
			 @Override
			 public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
				 mHour=hourOfDay;
				 mMin=minute;
				 String AM_PM;
				 if(hourOfDay < 12){
					 AM_PM = "AM"; 
				 } 
				 else {
					 AM_PM = "PM";
					 mHour=mHour-12;
				 }
				 etTime.setText(mHour+":"+mMin+" "+AM_PM);
				 	getCurrentDate();
			 	}
		 	};

}
