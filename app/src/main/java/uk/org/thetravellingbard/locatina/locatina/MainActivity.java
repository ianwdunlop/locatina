package uk.org.thetravellingbard.locatina.locatina;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    ComponentName myServiceComponent;
    GPSTxtService myService;
/*    Handler myHandler= new Handler() {
      @Override
      public void handleMessage(Message msg) {
          myService = (GPSTxtService) msg.obj;
          myService.setUICallback(MainActivity.this);
      }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myServiceComponent = new ComponentName(this, GPSTxtService.class);
        final Intent myServiceIntent = new Intent(this, GPSTxtService.class);
        Button button = (Button) findViewById(R.id.buttonScheduleJob);
        final JobScheduler jobScheduler = (JobScheduler) getApplicationContext().getSystemService(JOB_SCHEDULER_SERVICE);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText phoneNumberEditText = (EditText) findViewById(R.id.editPhoneNumber);
                String phoneNumber = phoneNumberEditText.getText().toString();
                myServiceIntent.putExtra("PhoneNumber", phoneNumber);
                startService(myServiceIntent);
                JobInfo.Builder builder = new JobInfo.Builder(0, myServiceComponent);
                // Every 6 hours
                //builder.setPeriodic(6 * 60 * 60 * 1000);
                builder.setPeriodic(60 * 1000);
                builder.setRequiresCharging(false);
                jobScheduler.schedule(builder.build());
            }
        });
        //Intent myServiceIntent = new Intent(this, GPSTxtService.class);
        //myServiceIntent.putExtra("messenger", new Messenger(myHandler));
        //startService(myServiceIntent);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //fab.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View view) {
        //        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //                .setAction("Action", null).show();
        //    }
        //});
    }

    public void onClick(View v) {
/*        JobInfo.Builder builder = new JobInfo.Builder(0, myServiceComponent);
        // Every 6 hours
        builder.setPeriodic(6 * 60 * 60 * 1000);
        builder.setRequiresCharging(false);
        myService.scheduleJob(builder.build());*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
