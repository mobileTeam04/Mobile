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

        if(provider==null){  //��ġ���� ������ �ȵǾ� ������ �����ϴ� ��Ƽ��Ƽ�� �̵��մϴ�
         	new AlertDialog.Builder(PlaceActivity.this)
	        .setTitle("��ġ���� ����")
	        .setNeutralButton("�̵�" ,new DialogInterface.OnClickListener() {
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
        }else{   //��ġ ���� ������ �Ǿ� ������ ������ġ�� �޾ƿɴϴ�
    		locationManager.requestLocationUpdates(provider, 1, 1, PlaceActivity.this);
        	setUpMapIfNeeded();
        }
    }

    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {//��ġ���� ��Ƽ��Ƽ ���� �� 
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case 0:
			locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			Criteria criteria = new Criteria();
			provider = locationManager.getBestProvider(criteria, true);
			if(provider==null){//����ڰ� ��ġ�������� �������� ���� 
				finish();
				}else{//����ڰ� ��ġ���� ���� ������ 
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
    	if(locationTag){//�ѹ��� ��ġ�� �������� ���ؼ� tag�� �־����ϴ�
    	Log.d("myLog"  , "onLocationChanged: !!"  + "onLocationChanged!!");
    	double lat =  location.getLatitude();
    	double lng = location.getLongitude();
    	Toast.makeText(PlaceActivity.this, "����  : " + lat +  " �浵: "  + lng ,  Toast.LENGTH_SHORT).show();

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
