package com.example.islam.inventory;

/**
 * Created by islam on 3/28/2019.
 */

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

// import applications.editablelistview.data.DatabaseContract;


public class DetailsActivity extends AppCompatActivity {

    DatabaseHelper myDB;
    TextView supplierNameTextView;
    TextView textView2;
    TextView supplierEmailTextView;
    ImageView call, email;

    String sp, se;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        supplierNameTextView = (TextView) findViewById(R.id.supplierName);
        textView2 = (TextView) findViewById(R.id.supplierPhone);
        supplierEmailTextView = (TextView) findViewById(R.id.supplierEmail);

        call = (ImageView) findViewById(R.id.call);
        email = (ImageView) findViewById(R.id.email);


        // explicit intent to DetailsActivity
        Intent intent = getIntent();
        String value = intent.getStringExtra("key"); //if it's a string you stored.

        // Toast.makeText(DetailsActivity.this, "intent is= " + value, Toast.LENGTH_SHORT).show();

        myDB = new DatabaseHelper(this);
        Cursor data = myDB.getListContents();
        int p = Integer.parseInt(value);
        if (data.moveToPosition(p)) {
            int supplierName = data.getColumnIndex(DatabaseContract.PetEntry.COLUMN_SUPPLIERNAME);
            String sn = data.getString(supplierName);
            supplierNameTextView.setText(sn);

            int supplierPhone = data.getColumnIndex(DatabaseContract.PetEntry.COLUMN_SUPPLIERPHONE);
            sp = data.getString(supplierPhone);
            textView2.setText(sp);

            final int supplierEmail = data.getColumnIndex(DatabaseContract.PetEntry.COLUMN_SUPPLIEREMAIL);
            se = data.getString(supplierEmail);
            supplierEmailTextView.setText(se);


            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // implicit intent to call
                    Intent intent2 = new Intent(Intent.ACTION_DIAL);
                    intent2.setData(Uri.parse("tel:" + sp));
                    //// if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent2);
                    //// }
                }
            });


            email.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // implicit intent to email
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                            "mailto",se, null));
                    //// emailIntent.putExtra(Intent.EXTRA_SUBJECT, "s");
                    //// emailIntent.putExtra(Intent.EXTRA_TEXT, "b");
                    startActivity(Intent.createChooser(emailIntent, "Send email..."));
                }
            });
        }  //end if statement
    }  //end onCreate method

}

