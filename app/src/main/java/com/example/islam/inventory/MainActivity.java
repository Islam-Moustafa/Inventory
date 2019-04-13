package com.example.islam.inventory;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/// import applications.editablelistview.data.DatabaseContract;

public class MainActivity extends AppCompatActivity {

    /** define global variables used in this class. **/

    // object from helper class
    DatabaseHelper myDB;

    // adapter to put products into ListView
    ProductCursorAdapter productCursorAdapter;
    // listView to handle list of products
    ListView listView;
    // TextView displayed when listView is empty
    TextView empty_view;

    Button mAddButton ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setup FAB button
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        myDB = new DatabaseHelper(this);

        //populate an ArrayList<String> from the database and then view it
        /// ArrayList<String> theList = new ArrayList<>();

        // create adapter takes list of Product
        productCursorAdapter = new ProductCursorAdapter(this, new ArrayList<Product>());
        // reference to listView in activity_main
        listView = (ListView) findViewById(R.id.listView);
        // set adapter in listView to display elements
        listView.setAdapter(productCursorAdapter);
        empty_view = (TextView)findViewById(R.id.empty_view);
        listView.setEmptyView(empty_view);


        // Setup the item click listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Toast.makeText(MainActivity.this, "Hello From ListView",Toast.LENGTH_SHORT).show();

                // create intent to go to DetailsActivity
                Intent myIntent = new Intent(MainActivity.this, DetailsActivity.class);
                myIntent.putExtra("key", String.valueOf(position)); //Optional parameters
                MainActivity.this.startActivity(myIntent);

            }
        });


        // load data from database into listView

        Cursor data = myDB.getListContents();
        //// int count = 0;

        //// if(data.getCount() == 0)
        /////     Toast.makeText(this, "There are no contents in this list!",Toast.LENGTH_LONG).show();

        while(data.moveToNext()) {
            //// for (int i = 0; i < data.getCount(); i++) {
            ////   data.moveToPosition(i);

            int firstNameColumnIndex = data.getColumnIndex(DatabaseContract.PetEntry.COLUMN_PRODUCTNAME);
            String f = data.getString(firstNameColumnIndex);

            int lastNameColumnIndex = data.getColumnIndex(DatabaseContract.PetEntry.COLUMN_PRODUCTMODEL);
            String l = data.getString(lastNameColumnIndex);

            int imageColumnIndex = data.getColumnIndex(DatabaseContract.PetEntry.COLUMN_IMAGE);
            byte[] b = data.getBlob(imageColumnIndex);
            Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);


            int priceColumnIndex = data.getColumnIndex(DatabaseContract.PetEntry.COLUMN_PRICE);
            double p = data.getDouble(priceColumnIndex);

            int quantityColumnIndex = data.getColumnIndex(DatabaseContract.PetEntry.COLUMN_QUANTITY);
            int q = data.getInt(quantityColumnIndex);

            int supplierName = data.getColumnIndex(DatabaseContract.PetEntry.COLUMN_SUPPLIERNAME);
            String sn = data.getString(supplierName);

            int supplierPhone = data.getColumnIndex(DatabaseContract.PetEntry.COLUMN_SUPPLIERPHONE);
            String sp = data.getString(supplierPhone);

            int supplierEmail = data.getColumnIndex(DatabaseContract.PetEntry.COLUMN_SUPPLIEREMAIL);
            String se = data.getString(supplierEmail);



            Product product = new Product(f,l, bitmap, p, q, sn, sp, se);
            //// list.add(product);

            productCursorAdapter.add(product);
            listView.setAdapter(productCursorAdapter);
        }


        //// } else
        /// Toast.makeText(this, "There are no contents in this list!",Toast.LENGTH_LONG).show();
    }



    /**
     * Helper method to insert product dummy data into the database. For debugging purposes only.
     */
    private void insertDummyData() {
        // convert Camera image from drawable to byte[] array
        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.camera);
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        /// byte[] bitMapData = stream.toByteArray();
        byte[] image = stream.toByteArray();
        // then convert image to bitmap to display by same resolution
        Bitmap b = BitmapFactory.decodeByteArray(image, 0, image.length);

        boolean insertData = myDB.addData("Camera", "2020", image, 100, 7,
                "Ahmed Mohamed", "0123456789", "ahmed@gmail.com");

        if(insertData==true){
            Toast.makeText(this, "Product Saved", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Error With Saving Product", Toast.LENGTH_SHORT).show();
        }

        // to show into ListView
        Product product = new Product("Camera", "2020", b, 100,
                7, "Ahmed Mohamed", "0123456789", "ahmed@gmail.com");

        // add this new dummy data to adapter to display into listView
        productCursorAdapter.add(product);
        /// listView.setAdapter(productCursorAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu_add options from the res/menu_add/menu_catalog.xml file.
        // This adds menu_add items to the app bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu_add option in the app bar overflow menu_add
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu_add option
            case R.id.action_insert_dummy_data:
                insertDummyData();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

