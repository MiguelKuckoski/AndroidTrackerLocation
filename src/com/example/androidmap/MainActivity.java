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
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {

	private Location location;
	private static GoogleMap map;
	private static MarkerOptions markerOptions;
	private static Marker marker;
	private static List<LatLng> rotaExecutada;
	private static LatLng currentPosition;
	private Button rota;
	private static Button atual;
	private static View view;

	@Override

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment)).getMap();
		markerOptions = new MarkerOptions();
		rota = (Button) findViewById(R.id.btnRota);
		atual = (Button) findViewById(R.id.btnLocalAtual);
		rota.setOnClickListener(this);
		atual.setOnClickListener(this);

		location = LocationUtil.getLocation(this);
		changeLocation(location);

	}

	public static void changeLocation(Location location) {

		if (location != null) {
			LatLng currentPosition = new LatLng(location.getLatitude(), location.getLongitude());
			map.moveCamera(CameraUpdateFactory.newLatLng(currentPosition));

			if (MainActivity.currentPosition == null)
				map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

			if (view == atual || view == null)
				map.clear();

			marker = map.addMarker(markerOptions.position(currentPosition)
					.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_foot)));

			marker.showInfoWindow();

			getRotaExecutada().add(currentPosition);
			MainActivity.currentPosition = currentPosition;
		}
	}

	@Override
	public void onClick(View view) {
		MainActivity.view = view;
		if (view == findViewById(R.id.btnLocalAtual)) {
			mostrarAtual();
		} else if (view == findViewById(R.id.btnRota)) {
			mostrarRota();
		}
	}

	private void mostrarAtual() {
		map.clear();
		markerOptions.position(currentPosition);
		marker = map.addMarker(markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_foot)));
		map.moveCamera(CameraUpdateFactory.newLatLng(currentPosition));
		map.animateCamera(CameraUpdateFactory.zoomTo(20), 2000, null);
	}

	private void mostrarRota() {
		for (LatLng latLgn : getRotaExecutada()) {
			markerOptions = new MarkerOptions();
			markerOptions.position(latLgn);
			marker = map.addMarker(markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_foot)));
		}
		map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
	}

	public static List<LatLng> getRotaExecutada() {
		if (rotaExecutada == null)
			rotaExecutada = new ArrayList<LatLng>();
		return rotaExecutada;
	}

}
