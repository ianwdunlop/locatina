package uk.org.thetravellingbard.locatina.locatina;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.EditText;

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
        //EditText phoneEditText = getfindViewById(R.id.editPhoneNumber);
        //LocationUpdateTask task = new LocationUpdateTask(findViewById(R.id.editPhoneNumber)
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
