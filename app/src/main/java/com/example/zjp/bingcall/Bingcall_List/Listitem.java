package com.example.zjp.bingcall.Bingcall_List;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zjp on 15-10-27.
 */
public class Listitem {
    private String callnumber,name;
    private Bitmap bitmap,lisbitmap;
    private int background;
    int id;


    public Listitem(String callnumber,String name,Bitmap lisbitmap,Bitmap bitmap,int background,int id){
        this.callnumber=callnumber;
        this.name=name;
        this.background=background;
        this.bitmap=bitmap;
        this.lisbitmap=lisbitmap;
        this.id=id;
    }

    public String getCallnumber() {
        return callnumber;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public Bitmap getLisbitmap() {
        return lisbitmap;
    }

    public String getName() {
        return name;
    }
    public int getBackground() {
        return background;
    }
    public int getId(){return id;}

}
