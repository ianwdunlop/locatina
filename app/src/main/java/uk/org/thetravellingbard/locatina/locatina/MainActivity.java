package uk.org.thetravellingbard.locatina.locatina;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    ComponentName myServiceComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myServiceComponent = new ComponentName(this, GPSTxtService.class);
        final Intent myServiceIntent = new Intent(this, GPSTxtService.class);
        final Button button = findViewById(R.id.buttonScheduleJob);
        final Button cancelButton = findViewById(R.id.buttonCancelAllScheduledJobs);
        final EditText phoneNumberEditText = findViewById(R.id.editPhoneNumber);
        final JobScheduler jobScheduler = (JobScheduler) getApplicationContext().getSystemService(JOB_SCHEDULER_SERVICE);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = phoneNumberEditText.getText().toString();
                myServiceIntent.putExtra("PhoneNumber", phoneNumber);
                startService(myServiceIntent);
                JobInfo.Builder builder = new JobInfo.Builder(0, myServiceComponent);
                // Every 6 hours
                builder.setPeriodic(6 * 60 * 60 * 1000);
                //builder.setPeriodic(60 * 1000);
                builder.setRequiresCharging(false);
                jobScheduler.schedule(builder.build());
                phoneNumberEditText.setEnabled(false);
                cancelButton.setVisibility(View.VISIBLE);
                button.setVisibility(View.INVISIBLE);
            }
        });
        cancelButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                jobScheduler.cancelAll();
                button.setVisibility(View.VISIBLE);
                cancelButton.setVisibility(View.INVISIBLE);
                phoneNumberEditText.setVisibility(View.VISIBLE);
                Log.i("Main", "Cancelling all jobs");
            }
        });
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
