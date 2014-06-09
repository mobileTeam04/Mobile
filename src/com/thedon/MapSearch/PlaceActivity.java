package com.thedon.MapSearch;



import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import android.os.Build;

public class PlaceActivity extends FragmentActivity implements LocationListener, android.location.LocationListener {

	private GoogleMap mmap;
    private LocationManager locationManager;
    private String provider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        
//        mmap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
//       mmap.setMyLocationEnabled(true);
//       mmap.moveCamera(CameraUpdateFactory.newLatLngZoom(locationManager.,16));
        

        GooglePlayServicesUtil.isGooglePlayServicesAvailable(PlaceActivity.this);
       	locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, true);

        if(provider==null){  //위치정보 설정이 안되어 있으면 설정하는 엑티비티로 이동합니다
         	new AlertDialog.Builder(PlaceActivity.this)
	        .setTitle("위치서비스 동의")
	        .setNeutralButton("이동" ,new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
					startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);
				}
			}).setOnCancelListener(new DialogInterface.OnCancelListener() {

				@Override
				public void onCancel(DialogInterface dialog) {
					finish();
				}
			}).show();
        }else{   //위치 정보 설정이 되어 있으면 현재위치를 받아옵니다
    		locationManager.requestLocationUpdates(provider, 1, 1, PlaceActivity.this);
        	setUpMapIfNeeded();
        }
    }

    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {//위치설정 엑티비티 종료 후 
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 0:
			locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			Criteria criteria = new Criteria();
			provider = locationManager.getBestProvider(criteria, true);
			if(provider==null){//사용자가 위치설정동의 안했을때 종료 
				finish();
				}else{//사용자가 위치설정 동의 했을때 
					locationManager.requestLocationUpdates(provider, 1L, 2F, PlaceActivity.this);
					setUpMapIfNeeded();
				}
			break;
			}
		}
    @Override
	public void onBackPressed() {
		this.finish();
	}

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded(); 
        }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    private void setUpMapIfNeeded() {
		if (mmap == null) {
			mmap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		if (mmap != null) {
			setUpMap();
		}
		}
	}

    private void setUpMap() {
    	mmap.setMyLocationEnabled(true);
    	mmap.getMyLocation();
	}
    boolean locationTag=true;
    @Override
    public void onLocationChanged(Location location) {
    	if(locationTag){//한번만 위치를 가져오기 위해서 tag를 주었습니다
    	Log.d("myLog"  , "onLocationChanged: !!"  + "onLocationChanged!!");
    	double lat =  location.getLatitude();
    	double lng = location.getLongitude();
    	Toast.makeText(PlaceActivity.this, "위도  : " + lat +  " 경도: "  + lng ,  Toast.LENGTH_SHORT).show();

    	locationTag=false;
    	}
    }

	public void onProviderDisabled(String provider) {

		// TODO Auto-generated method stub

	}


	public void onProviderEnabled(String provider) {

		// TODO Auto-generated method stub

	}

	public void onStatusChanged(String provider, int status, Bundle extras) {

		// TODO Auto-generated method stub	

	}
}
