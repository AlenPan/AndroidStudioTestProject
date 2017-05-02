package com.example.litepaltest;

import org.litepal.crud.DataSupport;

import static android.R.attr.id;

/**
 * Created by Administrator on 2017/5/2.
 */

public class Book extends DataSupport{

    private String author;

    private double price;

    private int pages;

    private String name;

    private String press;

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("name=%s, author=%s, price=%s, page=%s, press=%s", name, author, price, pages, press);
    }
}
