package com.example.islam.inventory;

/**
 * Created by islam on 3/28/2019.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class ProductCursorAdapter extends ArrayAdapter<Product> {


    public ProductCursorAdapter(Context context, List<Product> productList) {

        super(context, 0, productList);
    }


    // getView method return a view that displays data at specific position
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // getItem(position) to return data item at a specific position in the list
        Product currentElement = getItem(position);


        TextView num = (TextView)listItemView.findViewById(R.id.num);
        num.setText(String.valueOf(position+1));

        // Find the TextView with view ID title
        TextView firstName = (TextView) listItemView.findViewById(R.id.firstName);
        firstName.setText(currentElement.getProductName() +" " + currentElement.getProductModel());

        ImageView imageView = (ImageView)listItemView.findViewById(R.id.img);
        imageView.setImageBitmap(currentElement.getProductImage());

        TextView price = (TextView)listItemView.findViewById(R.id.price);
        price.setText(String.valueOf(currentElement.getProductPrice()) + "$");

        TextView quantity = (TextView)listItemView.findViewById(R.id.quantity);
        quantity.setText(String.valueOf(currentElement.getProductQuantity()) + " pcs");


        /**
         if(currentElement.getImage() != null){
         imageView.setImageBitmap(currentElement.getImage());
         }
         else
         imageView.setImageResource(R.drawable.product_image);
         */

        return listItemView;
    }

}

