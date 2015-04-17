package com.pranay.nanda.contactlist;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class MainActivity extends ActionBarActivity {
    private static final String TAG = "PRANAY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Todo: Remove ContentResolver query from the UI thread to a separate thread(CursorLoader)



        // Get the ContentResolver
        ContentResolver contentResolver = getContentResolver();

        //Define the paramaters for the ContentResolver query to the Contacts Provider

        //Define the columns to be extracted
        String[] mProjection =
                {
                        Contacts._ID,
                        Contacts.DISPLAY_NAME_PRIMARY
                };


        //Define the selection criteria for rows to be displayed
        String mSelectionClause=null;

        /*String whereClause = "((" + Contacts.DISPLAY_NAME + " NOTNULL) AND ("
                + Contacts.DISPLAY_NAME + " != ' '))";*/

        // Todo: Understand the selection clause and selection arguments better(querying content provider)

        // Initializes an array to contain selection arguments
        String[] mSelectionArgs = {""};


        // Sort alphabetically by Display Name
        String mSortOrder = Contacts.DISPLAY_NAME_PRIMARY + " ASC";

        // query contacts ContentProvider
        Cursor cursor = contentResolver.query(Contacts.CONTENT_URI,
                mProjection, mSelectionClause, null, mSortOrder);


        //View the results in the Cursor object on the terminal

        if (null == cursor) {
         Log.e(TAG, "Contacts query failed!");


        } else if (cursor.getCount() < 1) {
            // If the Cursor is empty, the provider found no matches
            Log.d(TAG, "Cursor is empty, no contacts found");

        } else {

            Log.d(TAG,"The total items returned by the query to content provider: " + cursor.getCount());
            //Debugging: Print the cursor to the logs
            //Log.d(TAG,"Cursor: " + dumpCursorToString(cursor));

        }


        //Define list of columns to retrieve from Cursor to load to output row
        String [] mNameColumns = {
                Contacts.DISPLAY_NAME_PRIMARY
        } ;

        // Define a list of View IDs that will receive the Cursor columns for each row
        int[] mWordListItems={R.id.name};

        //Create a simple cursor adapter
        SimpleCursorAdapter mCursorAdapter = new SimpleCursorAdapter(
                getApplicationContext(), //Application's Context Object
                R.layout.list_item,       //XML layout for one row in the listview
                cursor,                  //Result from the query
                mNameColumns,            //String array with the column names
                mWordListItems,         //Integer array of view IDs in the row layout
                0);

        //Inflate the layout and associate it with the Activity
        setContentView(R.layout.activity_main);

        //Set the adapter for the listview
        ListView listView = (ListView)findViewById(
                R.id.listview_names);
        listView.setAdapter(mCursorAdapter);


    }


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

        if (id == R.id.action_settings) {
            return true;
        }
        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }
}
