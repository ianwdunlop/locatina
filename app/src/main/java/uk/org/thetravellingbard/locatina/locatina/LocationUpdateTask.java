/* Locatina. Send your location to friends and family.
   Copyright (C) 2018  Ian Dunlop

   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <https://www.gnu.org/licenses/>.*/
package uk.org.thetravellingbard.locatina.locatina;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
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
