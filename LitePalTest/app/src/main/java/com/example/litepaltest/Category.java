package com.example.litepaltest;

import org.litepal.crud.DataSupport;

import static android.R.attr.id;

/**
 * Created by Administrator on 2017/5/2.
 */

public class Category extends DataSupport{

    private String categoryName;

    private int categoryCode;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(int categoryCode) {
        this.categoryCode = categoryCode;
    }
}
