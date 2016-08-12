package com.example.jamesrondina.calendarcontentprovider;

import android.content.ContentUris;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    CursorAdapter mCursorAdapter;
    public static final int EVENT_LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ListView listView = (ListView) findViewById(R.id.listView);

        mCursorAdapter = new android.support.v4.widget.SimpleCursorAdapter(
                this, //context
                R.layout.date, //layout
                null,
                new String[]{CalendarContract.Events.TITLE, CalendarContract.Events.DTSTART}, //where data comes from
                new int[]{R.id.text1, R.id.text2}, //view data is being output to
                0
        );


        getSupportLoaderManager().initLoader(
                EVENT_LOADER,
                null,
                this);

        listView.setAdapter(mCursorAdapter);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                getContentResolver().delete(ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI,
                        l),
                        null,
                        null);
                return false;
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: ADD ITEM

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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id){
            case EVENT_LOADER:
                return new CursorLoader(
                        this,
                        CalendarContract.Events.CONTENT_URI,
                        new String[]{CalendarContract.Events._ID, CalendarContract.Events.TITLE,
                                CalendarContract.Events.DTSTART},
                        null,
                        null,
                        null
                );
            default:
                return null;
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.changeCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.changeCursor(null);

    }
}
