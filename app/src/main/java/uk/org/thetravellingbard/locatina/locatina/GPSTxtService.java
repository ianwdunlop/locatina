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

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class GPSTxtService extends JobService{

    String phoneNumber;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i(this.getClass().getName(), "GPS Texting service created");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(this.getClass().getName(), "GPS Texting service destroyed");
    }

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        Log.i(this.getClass().getName(), "on start job");
        LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationUpdateTask task = new LocationUpdateTask(phoneNumber);
        task.doInBackground(locManager);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.i(this.getClass().getName(), "on stop job");
        return true;
    }
    MainActivity myMainActivity;

    public void setUICallback(MainActivity activity) {
        myMainActivity = activity;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle extras = intent.getExtras();
        String phoneNumber = (String) extras.get("PhoneNumber");
        this.phoneNumber = phoneNumber;
        Log.i(this.getClass().getName(), "Setting up job for " + phoneNumber);
        return START_NOT_STICKY;
    }

    public void scheduleJob(JobInfo build) {
        Log.i(this.getClass().getName(), "Scheduling job");
        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(build);
    }
}
