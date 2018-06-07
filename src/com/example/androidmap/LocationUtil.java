package com.example.androidmap;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationUtil {

	private static Location location;
	private static LocationManager locationManager;
	private static LocationListener listener = new LocationListener() {
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onProviderDisabled(String provider) {
		}

		@Override
		public void onLocationChanged(Location location) {
			LocationUtil.location = location;
			MainActivity.changeLocation(location);
		}
	};

	public static Location getLocation(Context ctx) {
		Context context = ctx.getApplicationContext();
		if (locationManager == null)
			locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
//		Criteria criteria = new Criteria();
//		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
//		criteria.setPowerRequirement(Criteria.POWER_LOW);
//		String bestProvider = locationManager.getBestProvider(criteria, true);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 5, listener);
		if (location == null) {
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 5, listener);
			location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		}
		try {
			if (location == null) {
				Thread.sleep(1000);
				locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 5, listener);
				location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			}
			if (location == null) {
				Thread.sleep(2000);
				locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 5, listener);
				location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			}
		} catch (Exception e) {
			
		}

		if (location == null) {
			location = new Location("not found");
			location.setLatitude(0);
			location.setLongitude(0);

		}
		return location;
	}

}
