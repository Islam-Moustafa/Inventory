package com.example.islam.inventory;

/**
 * Created by islam on 3/28/2019.
 */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

// Add new Product into database
public class AddActivity extends AppCompatActivity {

    // define global variables
    private EditText productNameET, productModelET, productPriceET, productQuantityET,
            supplierNameET, supplierPhoneET, supplierEmailET;
    private ImageView addImage;

    // object from DatabaseHelper to deal with database
    DatabaseHelper myDB;

    // to take image by camera
    static final int REQUEST_CAMERA = 1;
    byte[] imageBlob = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // reference to views into activity_add
        productNameET = (EditText) findViewById(R.id.productNameET);
        productModelET = (EditText) findViewById(R.id.productModelET);
        productPriceET = (EditText)findViewById(R.id.productPriceET);
        productQuantityET = (EditText)findViewById(R.id.productQuantityET);
        supplierNameET = (EditText)findViewById(R.id.supplierNameET);
        supplierPhoneET = (EditText)findViewById(R.id.supplierPhoneET);
        supplierEmailET = (EditText) findViewById(R.id.supplierEmailET);
        addImage = (ImageView) findViewById(R.id.addImage);

        myDB = new DatabaseHelper(this);

        // take picture by camera when click into imageView
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getPackageManager()) != null)
                    startActivityForResult(intent, REQUEST_CAMERA);
            }
        });

    }  // end onCreate method


    // to take picture by camera
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CAMERA) {
            if (resultCode == RESULT_OK) {
                Bitmap image = (Bitmap) data.getExtras().get("data");

                // change size of image and put into imageView
                Bitmap sizeImage =  getResizedBitmap(image, 800, 800);
                addImage.setImageBitmap(sizeImage);

                // compress of image and convert into byteArray to can put into BLOB database
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                sizeImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                imageBlob = stream.toByteArray();
            }
        }
    }


    /**
     * to change size of picture taken by image
     */
    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);

        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        return resizedBitmap;
    }


    // to save new product into database
    public void saveProduct(String productName, String productModel, double price, int quantity,
                            String supplierName, String supplierPhone, String supplierEmail) {
        // put product_image if no taken picture by camera
        if(imageBlob == null){
            // convert image from drawable to byte[] array
            Resources res = getResources();
            Drawable drawable = res.getDrawable(R.drawable.product_image);
            Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            imageBlob = stream.toByteArray();

            boolean insertData = myDB.addData(productName, productModel, imageBlob, price, quantity,
                    supplierName, supplierPhone, supplierEmail);

            if(insertData==true){
                Toast.makeText(this, "Product Saved", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Error With Saving Product", Toast.LENGTH_SHORT).show();
            }
        }

        else{
            // Put image taken by camera into database
            boolean insertData = myDB.addData(productName, productModel, imageBlob, price, quantity,
                    supplierName, supplierPhone, supplierEmail);
            if(insertData==true){
                Toast.makeText(this, "Product Saved", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Error With Saving Product", Toast.LENGTH_SHORT).show();
            }
        }
    } // end saveProduct method


    // setting of menu_add

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu_add options from the res/menu/menu_add.xml file.
        // This adds menu_add items to the app bar into AddActivity.
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    /**
     * This method is called after invalidateOptionsMenu(), so that the
     * menu_add can be updated (some menu_add items can be hidden or made visible).
     */
    /*
    @Override
    public boolean onPrepareOptionsMenu(Menu menu_add) {
        super.onPrepareOptionsMenu(menu_add);
        // If this is a new pet, hide the "Delete" menu_add item.
        if (mCurrentPetUri == null) {
            MenuItem menuItem = menu_add.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }
    */


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu_add option in the app bar overflow menu_add
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu_add option
            case R.id.action_save:
                // Save Product into database
                String productName = productNameET.getText().toString().trim();
                String productModel = productModelET.getText().toString().trim();
                double price = Double.parseDouble(productPriceET.getText().toString().trim());
                int quantity = Integer.parseInt(productQuantityET.getText().toString().trim());
                String supplierName = supplierNameET.getText().toString().trim();
                String supplierPhone = supplierPhoneET.getText().toString().trim();
                String supplierEmail = supplierEmailET.getText().toString().trim();

                saveProduct(productName, productModel, price, quantity,
                        supplierName, supplierPhone, supplierEmail);

                // to sense by change synchronously
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);

                // Exit activity
                finish();
                return true;

            // Respond to a click on the "Up" arrow button in the app bar or back button into navigation bar
            case android.R.id.home:
                // display Dialog Box to warning when user back to home screen
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User clicked "Discard" button, navigate to parent activity.
                                NavUtils.navigateUpFromSameTask(AddActivity.this);
                            }
                        };

                // Show a dialog that notifies the user they have unsaved changes
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * This method is called when the back button is pressed.
     */
    @Override
    public void onBackPressed() {
        // if productName and productModel is null then back without check
        if (productNameET.length()==0 && productModelET.length()==0 && productPriceET.length()==0 ) {
            super.onBackPressed();
            return;
        }


        // Otherwise if there are unsaved changes, setup a dialog to warn the user.
        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User clicked "YES" button, close the current activity.
                        finish();
                    }
                };

        // Show dialog that there are unsaved changes
        showUnsavedChangesDialog(discardButtonClickListener);
    }


    /**
     * Show a dialog that warns the user there are unsaved changes that will be lost
     */
    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("QUIT WITHOUT SAVING");
        builder.setPositiveButton("YES", discardButtonClickListener);
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "NO" button, so dismiss the dialog and continue
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

} // end class

