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

        final ContractionRecord cr = (ContractionRecord) getItem(position);

        //TODO: Below here is still mostly code from the Miwok app. need to convert.

        //find all the views so that the content of the list item can be configured
        TextView miwok_vw = (TextView) recordView.findViewById(R.id.list_item_miwok);
        TextView english_vw = (TextView) recordView.findViewById(R.id.list_item_english);
        ImageView image_vw = (ImageView) recordView.findViewById(R.id.image_view);
        ImageView playButton = (ImageView) recordView.findViewById(R.id.play_button);
        LinearLayout linearLayout = (LinearLayout) recordView.findViewById(R.id.word_layout);

        miwok_vw.setText(wp.getMiwokWord());
        english_vw.setText(wp.getEnglishWord());

        // If the item has an image, set the appropriate resource. Otherwise, hide the image
        if (wp.hasImage()) {
            image_vw.setVisibility(View.VISIBLE);
            image_vw.setImageResource(wp.getWordImage());
        } else {
            image_vw.setVisibility(View.GONE);
        }

        //Set the background colour corresponding to the category
        int colour = ContextCompat.getColor(getContext(), categoryColour);
        linearLayout.setBackgroundColor(colour);
        playButton.setBackgroundColor(colour);

        return recordView;
    }
}
