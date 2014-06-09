package kr.ac.kumoh.ce.mobile;

import kr.ac.kumoh.ce.mobile.dbhelper.WordDBHelper;

import com.thedon.MapSearch.PlaceActivity;
import com.thedon.MapSearch.R;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.os.Build;

public class MainActivity extends FragmentActivity {
	ImageButton writeBtn;
	ImageButton placeBtn;
	ImageButton listBtn;
	ImageButton settingBtn;
	
	WordDBHelper helper;
	Intent intent;
    Cursor cursor;
    ContentValues row;
    SQLiteDatabase db;
    Boolean check_open;
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
        helper = new WordDBHelper(this);
        db = helper.getWritableDatabase();
        row = new ContentValues();
        
        intent = getIntent();
        
    	cursor = db.rawQuery("SELECT * FROM memo", null); 
    	startManagingCursor(cursor);

        SimpleCursorAdapter Adapter = null;
        Adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2,cursor,new String[]{"title","open"},new int[]{android.R.id.text1,android.R.id.text2});
        ListView list = (ListView)findViewById(R.id.save_list);
        list.setAdapter(Adapter); //여기서 에러난대. 왜?
        list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
			      cursor.moveToPosition(position);
			      String check = cursor.getString(4);
			      if(check.equals("공개")){
						check_open = true;
					}else
						check_open = false;
					if(check_open.equals(true)){
						Intent intent = new Intent(getApplicationContext(),conView.class);
						intent.putExtra("position",position);
						startActivity(intent);
					}
			}
		});
		
		//startActivity(new Intent(this, SplashActivity.class));
//		android.app.ActionBar abar = getActionBar();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main_activity_actions, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_new:
	        	Intent writeIntent = new Intent(MainActivity.this, WriteActivity.class);
	        	startActivity(writeIntent);
	            return true;
	        case R.id.action_place:
	        	Intent placeIntent = new Intent(MainActivity.this, com.thedon.MapSearch.PlaceActivity.class);
				startActivity(placeIntent);
	            return true;
	        case R.id.action_refresh:
	        	return true;
	        case R.id.action_settings:
	        	Intent settingIntent = new Intent(MainActivity.this, SettingActivity.class);
				startActivity(settingIntent);
	        	return true;
	            
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

}
