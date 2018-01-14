package com.example.andrew.contractionlogger;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.media.CamcorderProfile.get;
import static com.example.andrew.contractionlogger.R.drawable.ic_greenbutton;

public class MainActivity extends AppCompatActivity {

    TextView btnTimer;
    ImageView btnImage;
    ArrayList<ContractionRecord> records;
    private boolean isTimerRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        records = new ArrayList<>();

        //Instantiate a new adapter to load the record items into the list
        final RecordAdapter adapter = new RecordAdapter(this, records);

        // Instantiate a new ListView object to attach to the layout and connect the adapter
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        btnTimer = (TextView) findViewById(R.id.btn_timer);
        btnImage = (ImageView) findViewById(R.id.img_button);

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTimerRunning) {
                    stopTimer(adapter);
                } else {
                    startTimer(adapter);
                }
            }
        });
    }

    private void startTimer(RecordAdapter mAdapter) {
        //Get the current time from the calendar
        Date currentTime = Calendar.getInstance().getTime();

        //If this is the first record, don't try to calculate the frequency
        if (records.size() == 0) {
            // Create a new record in the list and update the view
            records.add(0, new ContractionRecord(currentTime));
            mAdapter.notifyDataSetChanged();
        }
        // If not the first record, send the last start time through so that the frequency can be calculated
        else {
            // Get the last start time
            Date lastTime = records.get(0).getDtStartTime();
            // Create a new record in the list and update the view
            records.add(0, new ContractionRecord(currentTime, lastTime));
            mAdapter.notifyDataSetChanged();
        }

        //Update the state and timer button before exiting
        isTimerRunning = true;
        btnTimer.setText("Stop");
        btnImage.setBackgroundResource(R.drawable.ic_redbutton);
    }

    private void stopTimer(RecordAdapter mAdapter) {
        //Get the current time from the calendar
        Date currentTime = Calendar.getInstance().getTime();

        // Update the current active record and then update the view
        ContractionRecord record = records.get(0);
        record.setStopTime(currentTime);
        records.set(0, record);
        mAdapter.notifyDataSetChanged();

        //Update the state and timer button before exiting
        isTimerRunning = false;
        btnTimer.setText("Start");
        btnImage.setBackgroundResource(R.drawable.ic_greenbutton);
    }
}
