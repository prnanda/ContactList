package com.pranay.nanda.contactlist;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {
    private static final String TAG = "PRANAY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Todo: Remove ContentResolver query from the UI thread to a separate thread


        //Define the paramaters for the ContentResolver query to the Contacts Provider

        //Define the columns to be extracted
        String[] mProjection =
                {
                        Contacts._ID,
                        Contacts.DISPLAY_NAME
                };

        // Get the ContentResolver
        ContentResolver contentResolver = getContentResolver();

        //Define the selection criteria for rows to be displayed
        String mSelectionClause=null;

        /*String whereClause = "((" + Contacts.DISPLAY_NAME + " NOTNULL) AND ("
                + Contacts.DISPLAY_NAME + " != ' '))";*/

        // Initializes an array to contain selection arguments
        String[] mSelectionArgs = {""};


        // Sort alphabetically by Display Name
        String mSortOrder = Contacts.DISPLAY_NAME + " ASC";

        // query contacts ContentProvider
        Cursor cursor = contentResolver.query(Contacts.CONTENT_URI,
                mProjection, mSelectionClause, null, mSortOrder);


        //View the results in the Cursor object on the terminal

        if (null == cursor) {
        /*
         * Insert code here to handle the error. Be sure not to use the cursor! You may want to
         * call android.util.Log.e() to log this error.
         *
         */
         Log.e(TAG, "Contacts query failed!");

            // If the Cursor is empty, the provider found no matches
        } else if (cursor.getCount() < 1) {

            /*
         * Insert code here to notify the user that the search was unsuccessful. This isn't necessarily
         * an error. You may want to offer the user the option to insert a new row, or re-type the
         * search term.
         */

        } else {
            // Insert code here to do something with the results
            Log.d(TAG,"The total items returned by the query to content provider: " + cursor.getCount());

        }


        setContentView(R.layout.activity_main);


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

        if (id == R.id.action_settings) {
            return true;
        }
        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }
}
