package uk.org.thetravellingbard.locatina.locatina;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.telephony.SmsManager;
import android.util.Log;

public class LocationUpdateTask extends AsyncTask<LocationManager, Integer, Boolean> {

    String phoneNumber;

    public LocationUpdateTask(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    protected Boolean doInBackground(LocationManager... locationManagers) {
        Log.i(this.getClass().getName(), "fetching current location");
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }
        final String className = this.getClass().getName();
        locationManagers[0].requestSingleUpdate(criteria, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Double latitude = location.getLatitude();
                Double longitude = location.getLongitude();
                SmsManager smsManager = SmsManager.getDefault();
                Log.i(className, "Sending location: " + latitude + " " + longitude);
                smsManager.sendTextMessage(phoneNumber, null, "Test from Ian: " + latitude + " " + longitude, null, null);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        }, Looper.myLooper());
        Looper.loop();
        return true;
    }
}
