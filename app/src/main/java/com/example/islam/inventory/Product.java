package com.example.islam.inventory;

/**
 * Created by islam on 3/28/2019.
 */

import android.graphics.Bitmap;

// class Product contain information related to single product
public class Product {
    private String productName;
    private String productModel;
    private Bitmap productImage;
    private double productPrice;
    private int productQuantity;
    private String supplierName;
    private String supplierPhone;
    private String supplierEmail;

    // default constructor
    public Product(String pName, String pModel, Bitmap pImage, double pPrice, int pQuantity, String sName, String sPhone, String sEmail){
        this.productName = pName;
        this.productModel = pModel;
        this.productImage = pImage;
        this.productPrice = pPrice;
        this.productQuantity = pQuantity;
        this.supplierName = sName;
        this.supplierPhone = sPhone;
        this.supplierEmail = sEmail;
    }

    public String getProductName(){return productName;}
    public String getProductModel(){ return productModel;}
    public Bitmap getProductImage() { return productImage; }
    public double getProductPrice(){ return productPrice; }
    public int getProductQuantity(){ return productQuantity; }
    public String getSupplierName(){ return supplierName; }
    public String getSupplierPhone(){ return supplierPhone; }
    public String getSupplierEmail(){ return supplierEmail; }
}

