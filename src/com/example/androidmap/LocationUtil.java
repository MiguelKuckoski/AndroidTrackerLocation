package com.example.androidmap;

import java.util.List;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationUtil {

	private static Location location;
	private static LocationManager locationManager;

	private static LocationListener listener;

	private static Location setLocation(Context ctx) {
		Context context = ctx.getApplicationContext();
		if (locationManager == null)
			locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

		List<String> providers = locationManager.getProviders(true);

		for (String provider : providers) {
			locationManager.requestLocationUpdates(provider, 0, 8, new LocationListener() {

				public void onLocationChanged(Location location) {
					LocationUtil.location = location;
					MainActivity.changeLocation(location);
				}

				public void onProviderDisabled(String provider) {
				}

				public void onProviderEnabled(String provider) {
				}

				public void onStatusChanged(String provider, int status, Bundle extras) {

				}
			});
			
			if(locationManager.getLastKnownLocation(provider) != null ) {
				if(location == null) {
					location = locationManager.getLastKnownLocation(provider);
				}else if (locationManager.getLastKnownLocation(provider).getAccuracy() > location.getAccuracy()) {
					location = locationManager.getLastKnownLocation(provider);
				}
			}
				
		}
		
		return location;
		
	}

	public static Location getLocation(Context ctx) {
			
			setLocation(ctx);

			try {
				if (location == null) {
					Thread.sleep(1000);
					setLocation(ctx);
				}
				if (location == null) {
					Thread.sleep(2000);
					setLocation(ctx);
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
