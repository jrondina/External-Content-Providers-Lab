package com.example.jamesrondina.calendarcontentprovider;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by jamesrondina on 8/11/16.
 */
public class CalendarCursorAdapter extends CursorAdapter {
    private final LayoutInflater mInflater;

    public CalendarCursorAdapter(Context context, Cursor cursor) {
        super(context, cursor, false);
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return mInflater.inflate(R.layout.date, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        long time = cursor.getLong(cursor.getColumnIndex("DATE_DATE")) * 1000L;

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);

        String format = "M/dd h:mm a";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        String dateString = sdf.format(cal.getTime());

        ((TextView) view.findViewById(R.id.text2)).setText(dateString);
    }
}
