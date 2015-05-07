package com.pranay.nanda.contactlist;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class MainActivity extends Activity {
    private static final String TAG = "PRANAY";
    private Cursor mCursor = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Todo: Remove ContentResolver query from the UI thread to a separate thread(CursorLoader):
        //AsyncTask Class, don't spawn new threads'
        //View the implementation in the Omni app

        /*

         8. turn abortOnError back on. critical lint findings are important
         */


        // Get the ContentResolver
        ContentResolver contentResolver = getContentResolver();

        //Define the paramaters for the ContentResolver query to the Contacts Provider

        //Define the columns to be extracted
        String[] Projection =
                {
                        Contacts._ID,
                        Contacts.DISPLAY_NAME_PRIMARY
                };


        //Define the selection criteria for rows to be displayed
        String SelectionClause=null;  //Contacts._ID ? IS not null

        // Todo: Understand the selection clause and selection arguments better(querying content provider)

        // Initializes an array to contain selection arguments
        String[] SelectionArgs = {""};


        // Sort alphabetically by Display Name
        String SortOrder = Contacts.DISPLAY_NAME_PRIMARY + " ASC";

        // query contacts ContentProvider
        mCursor = contentResolver.query(Contacts.CONTENT_URI,
                Projection, SelectionClause, null, SortOrder);


        //Inflate the layout and associate it with the Activity
        setContentView(R.layout.activity_main);

        if (null != mCursor) {

            Log.d(TAG,"The total items returned by the query to content provider: " + mCursor.getCount());
            //Debugging: Print the cursor to the logs
            //Log.d(TAG,"Cursor: " + dumpCursorToString(cursor));

            //Define list of columns to retrieve from Cursor to load to output row
            String [] NameColumns = {
                    Contacts.DISPLAY_NAME_PRIMARY
            } ;

            // Define a list of View IDs that will receive the Cursor columns for each row
            int[] WordListItems={R.id.name};

            //Create a simple cursor adapter
            SimpleCursorAdapter CursorAdapter = new SimpleCursorAdapter(
                    getApplicationContext(), //Application's Context Object
                    R.layout.list_item,       //XML layout for one row in the listview
                    mCursor,                  //Result from the query
                    NameColumns,            //String array with the column names
                    WordListItems,         //Integer array of view IDs in the row layout
                    0);

            //Set the adapter for the listview
            ListView listView = (ListView)findViewById(
                    R.id.listview_names);
            listView.setAdapter(CursorAdapter);


        } else {

            Log.e(TAG, "Contacts query failed!");

        }

        Log.d(TAG, "end of Oncreate");

    }

    @Override
    protected void onStop(){
        super.onStop();
        mCursor.close();
        Log.d(TAG, "end of onStop");
    }


}
