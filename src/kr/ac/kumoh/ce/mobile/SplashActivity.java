package kr.ac.kumoh.ce.mobile;

import com.thedon.MapSearch.R;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.os.Build;

public class SplashActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		Handler hd = new Handler();
		hd.postDelayed(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				finish();
			}
		
		}, 1000);
	}

}
