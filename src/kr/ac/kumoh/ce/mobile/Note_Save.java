package kr.ac.kumoh.ce.mobile;

import static android.view.View.inflate;

import com.thedon.MapSearch.R;

import android.app.Activity; 
import android.app.AlertDialog;
import android.app.Dialog;
import kr.ac.kumoh.ce.mobile.dbhelper.*;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.LinearGradient;
import android.os.Bundle;
import android.text.InputFilter.LengthFilter;
import android.util.Log;
import android.view.View;
//import android.view.ContextMenu;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.ContextMenu.ContextMenuInfo;
//import android.widget.AdapterView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
//import android.widget.AdapterView.AdapterContextMenuInfo;
//import android.widget.AdapterView;
import android.widget.TextView;

public class Note_Save extends Activity {
	private static final String DEBUG_TAG = "withpd";
	WordDBHelper helper;
	Intent intent;
	String mTitle;
	String mContent;
	String mOpen; 
//	String mPw;
    Cursor cursor;
    ContentValues row;
    SQLiteDatabase db;
    Button goMain; 
    Boolean check_open;
    Dialog dialog; 

    public void onCreate(Bundle savedInstanceState) {
    	Log.i(DEBUG_TAG,"Note_List onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = new WordDBHelper(this);
        db = helper.getWritableDatabase();
        row = new ContentValues();
        String bool;
        
        intent = getIntent();
        if(intent.getExtras().getString("check").equals("save")){
	      	mTitle = intent.getExtras().getString("title");
			mContent = intent.getExtras().getString("con");
	//		mPw = intent.getExtras().getString("pw");
			mOpen = intent.getExtras().getString("open");
			row.put("title",mTitle);
			row.put("con", mContent);
		//	row.put("pw", mPw); 
			row.put("open", mOpen);
			db.insert("memo", null, row); 
        }
        if(intent.getExtras().getString("check").equals("alarm")){
        	Log.i(DEBUG_TAG,"예약있을 때 : "+ intent.getExtras().getString("check"));
        	row.put("alarm","예약");
        	cursor = db.rawQuery("SELECT * FROM memo", null); 
        	startManagingCursor(cursor);  
        	
        	SimpleCursorAdapter Adapter = null; 
            Adapter = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_2,cursor,new String[]{"title","alarm"},new int[]{android.R.id.text1,android.R.id.text2});
            ListView list = (ListView)findViewById(R.id.save_list);
            goMain = (Button)findViewById(R.id.gomain);
            list.setAdapter(Adapter);
        }
        else{
	    	cursor = db.rawQuery("SELECT * FROM memo", null); 
	    	startManagingCursor(cursor);
	
	        SimpleCursorAdapter Adapter = null;
	        Adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2,cursor,new String[]{"title","open"},new int[]{android.R.id.text1,android.R.id.text2});
	        ListView list = (ListView)findViewById(R.id.save_list);
	        goMain = (Button)findViewById(R.id.gomain);
	        list.setAdapter(Adapter); //여기서 에러난대. 왜?
	        list.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
				      cursor.moveToPosition(position);
				      String check = cursor.getString(4);
				      Log.i(DEBUG_TAG,"check : " +check);
				      if(check.equals("공개")){
							check_open = true;
						}else
							check_open = false;
						Log.i(DEBUG_TAG,"check_open : " +check_open); 
						if(check_open.equals(true)){
							Intent intent = new Intent(getApplicationContext(),conView.class);
							intent.putExtra("position",position);
							startActivity(intent);
						}
				}
			});
	    }
    }
    
    public void mOnClick(View v){
    	Intent intent = new Intent(this, kr.ac.kumoh.ce.mobile.MainActivity.class);
    	startActivity(intent);
    }
}