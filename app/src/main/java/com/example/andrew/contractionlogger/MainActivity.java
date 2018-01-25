package com.example.andrew.contractionlogger;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static android.R.id.redo;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.media.CamcorderProfile.get;
import static com.example.andrew.contractionlogger.R.drawable.ic_greenbutton;

public class MainActivity extends AppCompatActivity {

    //TODO: Add a reset button
    //TODO: display some basic statistics
    TextView btnTimer;
    ImageView btnImage;
    ArrayList<ContractionRecord> records;
    private boolean isTimerRunning = false;
    private boolean loadOldData = false;
    private String filename = "Records_file";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Restore previous state and data
        //Get state from shared preferences
        SharedPreferences settings = getPreferences(MODE_PRIVATE);
        isTimerRunning = settings.getBoolean("timerState", false);
        Log.v("Timer state", Boolean.toString(isTimerRunning));
        loadOldData = settings.getBoolean("dataExists", false);
        // If this is first run, create a new array. Otherwise, get the previous records from internal storage
        if (loadOldData) {
            Log.v("Entry state", "Load");
            records = loadRecords();
            Toast.makeText(getApplicationContext(), "Previous data loaded", Toast.LENGTH_SHORT).show();
            Log.v("Entry state", "Load complete");
        } else {
            records = new ArrayList<>();
            Log.v("Entry state", "New");
        }

        //Instantiate a new adapter to load the record items into the list
        final RecordAdapter adapter = new RecordAdapter(this, records);

        // Instantiate a new ListView object to attach to the layout and connect the adapter
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        btnTimer = (TextView) findViewById(R.id.btn_timer);
        btnImage = (ImageView) findViewById(R.id.img_button);
        Button btnReset = (Button) findViewById(R.id.btn_reset);

        //Set button to correct state
        if (isTimerRunning) {
            Log.v("Set button", "start");
            btnTimer.setText("Stop");
            btnImage.setBackgroundResource(R.drawable.ic_redbutton);
            Log.v("Set button", "end");
        }

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

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTimerRunning) {
                    stopTimer(adapter);
                }
                records.clear();
                adapter.notifyDataSetChanged();
            }
        });
    }

    /*  @Override
      protected void onStop() {
          super.onStop();

          //Save state and data
          //Save state to shared preferences
          SharedPreferences settings = getPreferences(MODE_PRIVATE);
          SharedPreferences.Editor editor = settings.edit();
          editor.putBoolean("timerState", isTimerRunning);
          editor.putBoolean("dataExists", loadOldData);
          editor.commit();
          Log.v("onStop","1");

          //Save records to internal data storage
          storeRecords(records);
      }
  */
    @Override
    protected void onPause() {
        super.onPause();

        //Save state and data
        //Save state to shared preferences
        SharedPreferences settings = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("timerState", isTimerRunning);
        editor.putBoolean("dataExists", loadOldData);
        editor.commit();
        Log.v("onPause", "1");

        //Save records to internal data storage
        storeRecords(records);
    }

    private void startTimer(RecordAdapter mAdapter) {
        //Get the current time from the calendar
        Date currentTime = Calendar.getInstance().getTime();

        //If this is the first record, don't try to calculate the frequency
        if (records.size() == 0) {
            // Create a new record in the list and update the view
            records.add(0, new ContractionRecord(currentTime));
            mAdapter.notifyDataSetChanged();
            loadOldData = true;
        }
        // If not the first record, send the last start time through so that the frequency can be calculated
        else {
            // Get the last start time
            long lastTime = records.get(0).getDtStartTime();
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

    private ArrayList<ContractionRecord> loadRecords() {
        ArrayList<Character> byteStream = new ArrayList<>();
        ArrayList<ContractionRecord> restoredData = new ArrayList<>();

        Log.v("Entry", "Load records");

        try {
            //Open file
            FileInputStream fileStream = openFileInput(filename);

            //Read byte stream from the file
            int thisByte;

            while ((thisByte = fileStream.read()) != -1) {
                byteStream.add((char) thisByte);
            }

            // Create Contraction records from the data stream and add them to the new ArrayList
            int i = 0;
            while (i < byteStream.size()) {
                Log.v("Entry", "outer while");
                char thisChar = byteStream.get(i);
                String tempStart = "";
                String tempStop = "";
                String tempDur = "";
                String tempFreq = "";
                String tempDate = "";

                //Get the startTime
                while (thisChar != ',') {
                    tempStart = tempStart + Character.toString(thisChar);
                    thisChar = byteStream.get(++i);
                    Log.v("Start time", tempStart);
                }
                // move the index to the next element
                thisChar = byteStream.get(++i);

                //Get the stopTime
                while (thisChar != ',') {
                    tempStop = tempStop + Character.toString(thisChar);
                    thisChar = byteStream.get(++i);
                    Log.v("Stop time", tempStop);
                }
                // move the index to the next element
                thisChar = byteStream.get(++i);

                //Get the duration
                while (thisChar != ',') {
                    tempDur = tempDur + Character.toString(thisChar);
                    thisChar = byteStream.get(++i);
                    Log.v("Duration", tempDur);
                }
                // move the index to the next element
                thisChar = byteStream.get(++i);

                //Get the frequency
                while (thisChar != ',') {
                    tempFreq = tempFreq + Character.toString(thisChar);
                    thisChar = byteStream.get(++i);
                    Log.v("Frequency", tempFreq);
                }
                // move the index to the next element
                thisChar = byteStream.get(++i);

                //Get the dtStartTime
                while (thisChar != '|') {
                    tempDate = tempDate + Character.toString(thisChar);
                    thisChar = byteStream.get(++i);
                    Log.v("Time", tempDate);
                }

                // move the index to the next element
                ++i;

                try {
                    restoredData.add(new ContractionRecord(tempStart, tempStop, tempDur, tempFreq, Long.parseLong(tempDate)));
                    Log.v("Record", restoredData.get(0).toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            fileStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return restoredData;
    }

    private void storeRecords(ArrayList<ContractionRecord> aList) {
        try {
            FileOutputStream fileStream = openFileOutput(filename, MODE_PRIVATE);
            ContractionRecord conRecord;
            String strRecord;

            // for each element in the array, load the elements from the contraction record.
            for (int i = 0; i < aList.size(); i++) {
                conRecord = aList.get(i);
                strRecord = conRecord.toString() + "|";
                fileStream.write(strRecord.getBytes());
            }

            fileStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
