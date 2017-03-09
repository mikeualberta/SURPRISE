package com.cmput301w17t07.moody;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mike on 2017-03-08.
 */

public class TimelineAdapter extends ArrayAdapter<Mood> {
    ArrayList<Mood> moodTimeline;
    ArrayList<Mood> filteredMoodTimeline; //won't be implemented for now
    Context context;
    int layout_timeline_list;

    /**
     * Used https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
     * as a reference
     * @param context
     * @param layout_timeline_list
     * @param moodTimeline
     */
    public TimelineAdapter(Context context, int layout_timeline_list, ArrayList<Mood> moodTimeline){
        super(context, layout_timeline_list, moodTimeline);
        this.filteredMoodTimeline = moodTimeline; //current 'filter' is whatever is passed in.
        this.context = context;
        this.layout_timeline_list = layout_timeline_list;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){


        // Do I need to check the view?

        Mood mood = getItem(position); //do I need to override the getView position?

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.timeline_list, parent, false);
        }

        TextView username = (TextView) convertView.findViewById(R.id.usernameTV);
        username.setText(mood.getUsername());
//        username.setTypeface(font);

        TextView feelingText = (TextView) convertView.findViewById(R.id.feelingTV);
        feelingText.setText(mood.getFeeling());
//        feelingText.setTypeface(font);

        TextView dateText = (TextView) convertView.findViewById(R.id.dateTV);
        dateText.setText(mood.getDate());
//        dateText.setTypeface(font);

        return convertView;
    }


}