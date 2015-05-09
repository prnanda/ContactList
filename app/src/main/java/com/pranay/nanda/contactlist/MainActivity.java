package com.pranay.nanda.contactlist;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class MainActivity extends Activity implements
        LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "PRANAY";
    SimpleCursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Define list of columns to retrieve from Cursor to load to output row
        String [] NameColumns = {
                Contacts.DISPLAY_NAME_PRIMARY
        } ;

        // Define a list of View IDs that will receive the Cursor columns for each row
        int[] WordListItems={R.id.name};

        //Create empty adapter that will be used to display the data
        mAdapter = new SimpleCursorAdapter(
                getApplicationContext(), //Application's Context Object
                R.layout.list_item,  //XML layout for one row in the listview
                null,
                NameColumns, //String array with the column names
                WordListItems, //Integer array of view IDs in the row layout
                0);

        //Set the adapter for the listview
        ListView listView = (ListView)findViewById(
                R.id.listview_names);
        listView.setAdapter(mAdapter);

        // Prepare the loader
        getLoaderManager().restartLoader(0, null, this);

        /*
         Todo: Turn abortOnError back on. critical lint findings are important
         */

    }

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        //Define the paramaters for the query to the Contacts Provider

        //Define the columns to be extracted
        String[] Projection =
                {
                        Contacts._ID,
                        Contacts.DISPLAY_NAME_PRIMARY
                };

        // Sort alphabetically by Display Name
        String SortOrder = Contacts.DISPLAY_NAME_PRIMARY + " ASC";

        //Create and return CursorLoader that will take care of creating
        //a Cursor for the data being displayed
        return new CursorLoader(getApplicationContext(), Contacts.CONTENT_URI, Projection,
                null, null, SortOrder);
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Swap the new cursor in.  (The framework will take care of closing the
        // old cursor once we return.)
        mAdapter.swapCursor(data);
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        // This is called when the last Cursor provided to onLoadFinished()
        // above is about to be closed.  We need to make sure we are no
        // longer using it.
        mAdapter.swapCursor(null);
    }



}
