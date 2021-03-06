package com.example.andrew.contractionlogger;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Andrew on 9/01/2018.
 */

public class RecordAdapter extends ArrayAdapter {

    public RecordAdapter(Context context, ArrayList<ContractionRecord> arrayList) {
        super(context, 0, arrayList);
    }

    /**
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View recordView = convertView;
        if (recordView == null) {
            recordView = LayoutInflater.from(getContext()).inflate(
                    R.layout.data_view, parent, false);
        }

        ContractionRecord cr = (ContractionRecord) getItem(position);

        //find all the views so that the content of the list item can be configured
        TextView start_vw = (TextView) recordView.findViewById(R.id.vw_start_time);
        //TextView stop_vw = (TextView) recordView.findViewById(R.id.vw_stop_time);
        TextView dur_vw = (TextView) recordView.findViewById(R.id.vw_duration);
        TextView freq_vw = (TextView) recordView.findViewById(R.id.vw_frequency);

        start_vw.setText(cr.getStartTime());
        //stop_vw.setText(cr.getStopTime());
        dur_vw.setText(cr.getDuration());
        freq_vw.setText(cr.getFrequency());

        //Set the background colour for the rows in the list so that it alternates for readability
        LinearLayout linearLayout = (LinearLayout) recordView.findViewById(R.id.recordRow);
        if (position % 2 == 0) {
            //Make the background color grey
            linearLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorRowBkgnd));
        } else {
            //Make the background color white
            linearLayout.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorHighlightText));
        }

        return recordView;
    }
}
