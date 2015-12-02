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
    private Bitmap bitmap;
    private int background;
    int imageid;

    public int getImageid() {
        return imageid;
    }

    public Listitem(String callnumber,String name,Bitmap bitmap,int background){
        this.callnumber=callnumber;
        this.name=name;
        //this.imageid=imageid;
        this.background=background;
        this.bitmap=bitmap;
    }

    public String getCallnumber() {
        return callnumber;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getName() {
        return name;
    }
    public int getBackground() {
        return background;
    }

}
