package com.example.androidmap;

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

public class MainActivity extends Activity {

	private Location location;
	private static GoogleMap map;
	private static MarkerOptions markerOptions;
	private static Marker marker;
	private static List<LatLng> rotaExecutada;
	private static LatLng currentPosition;

	@Override

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment)).getMap();

		location = LocationUtil.getLocation(this);
		LatLng here = new LatLng(location.getLatitude(), location.getLongitude());

		map.moveCamera(CameraUpdateFactory.newLatLngZoom(here, 15));
		map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

		markerOptions = new MarkerOptions();
		markerOptions.position(here);

		if (here.longitude != 0 || here.latitude != 0) {
			marker = map.addMarker(
					markerOptions.position(here).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_foot)));
			marker.showInfoWindow();
		}
		

	}

	public static void changeLocation(Location location) {
		 map.clear();
		if (location != null) {
			LatLng currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
			map.moveCamera(CameraUpdateFactory.newLatLng(currentPosition));

			marker = map.addMarker(markerOptions.position(currentPosition)
					.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_foot)));

			marker.showInfoWindow();

			// getRotaExecutada().add(currentPosition);
			// MainActivity.currentPosition = currentPosition;
		}
		
		// TODO passar localização para os botões
	}

	// @Override
	// public void onClick(View view) {
	// map.clear();
	// if (view == findViewById(R.id.btnLocalAtual)) {
	// mostrarAtual();
	// } else if (view == findViewById(R.id.btnRota)) {
	// mostrarRota();
	// }
	// }
	//
	// private void mostrarAtual() {
	// markerOptions.position(currentPosition);
	// marker =
	// map.addMarker(markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_foot)));
	// }
	//
	// private void mostrarRota() {
	// for (LatLng latLgn : getRotaExecutada()) {
	// markerOptions = new MarkerOptions();
	// markerOptions.position(latLgn);
	// marker =
	// map.addMarker(markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_foot)));
	// }
	//
	// }
	//
	// public static List<LatLng> getRotaExecutada() {
	// if (rotaExecutada == null)
	// rotaExecutada = new ArrayList<LatLng>();
	// return rotaExecutada;
	// }

}
