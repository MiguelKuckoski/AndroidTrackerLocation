package com.example.androidmap;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity  implements OnClickListener{

	private Location location;
	private static GoogleMap map;
	private static MarkerOptions markerOptions;
	private static Marker marker;
	private static Button btnAtual;
	private static Button btnRota;
	private static List<LatLng> rotaExecutada;
	// private static LatLng localAtual;

	@Override

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment)).getMap();
		map.setMyLocationEnabled(true);
		location = LocationUtil.getLocation(this);

		markerOptions = new MarkerOptions();
		btnAtual = (Button) findViewById(R.id.btnLocalAtual);
		btnRota = (Button) findViewById(R.id.btnRota);
		
		LatLng frameworkSystemLocation = new LatLng(location.getLatitude(), location.getLongitude());
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(frameworkSystemLocation, 15));
		map.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, null);
				
		
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(frameworkSystemLocation, 15));
		map.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, null);
		markerOptions.position(frameworkSystemLocation);	
		
		marker = map.addMarker(new MarkerOptions().position(frameworkSystemLocation)
				.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_foot)));

		marker.showInfoWindow();
		
	}
	
	public static void changeLocation(Location location) {
		
		if (location != null) {
			LatLng frameworkSystemLocation = new LatLng(location.getLatitude(), location.getLongitude());
			markerOptions.position(frameworkSystemLocation);
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(frameworkSystemLocation, 15));
			
			marker = map.addMarker(new MarkerOptions().position(frameworkSystemLocation)
					.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_foot)));

			marker.showInfoWindow();
			
		//	getRotaExecutada().add(frameworkSystemLocation);
		}	
		
		//TODO passar localização para os botões
	}

	@Override
	public void onClick(View view) {
		map.clear();
		if(view == findViewById(R.id.btnLocalAtual)) {
			
		}else if(view == findViewById(R.id.btnRota)) {
			mostrarRota();
		}		
	}

	private void mostrarRota() {
		for(int i =0; i < getRotaExecutada().size(); i++) {
			
			markerOptions.position(getRotaExecutada().get(i));	
			marker = map.addMarker(new MarkerOptions().position(getRotaExecutada().get(i))
					.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_foot)));
		}
		
	}

	public static List<LatLng> getRotaExecutada() {
		if(rotaExecutada == null)
			rotaExecutada = new ArrayList<LatLng>();
		return rotaExecutada;
	}

	
	
}
