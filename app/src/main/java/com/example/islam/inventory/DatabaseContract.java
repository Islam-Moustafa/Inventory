package com.example.islam.inventory;

/**
 * Created by islam on 3/28/2019.
 */

import android.provider.BaseColumns;


public class DatabaseContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private DatabaseContract() {}


    public static final class PetEntry implements BaseColumns {

        public final static String TABLE_NAME = "product";

        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_PRODUCTNAME = "productName";

        public final static String COLUMN_PRODUCTMODEL = "productModel";

        public final static String COLUMN_IMAGE = "image";

        public final static String COLUMN_PRICE = "price";

        public final static String COLUMN_QUANTITY = "quantity";

        public final static String COLUMN_SUPPLIERNAME = "supplierName";

        public final static String COLUMN_SUPPLIERPHONE = "supplierPhone";

        public final static String COLUMN_SUPPLIEREMAIL = "supplierEmail";

    }

}

