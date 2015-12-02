package com.example.zjp.bingcall.Database;

/**
 * Created by zjp on 15-11-3.
 */


        import java.io.ByteArrayOutputStream;

        import android.content.ContentResolver;
        import android.content.ContentValues;
        import android.content.Context;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.graphics.Bitmap;
        import android.graphics.Bitmap.CompressFormat;
        import android.graphics.drawable.BitmapDrawable;
        import android.graphics.drawable.Drawable;
        import android.provider.BaseColumns;
        import android.widget.Toast;

        import com.example.zjp.bingcall.R;

public class PeopleDatabaseHelper extends SQLiteOpenHelper {

    //数据库的字段
    public static class PictureColumns implements BaseColumns {
        public static final String PICTURE = "picture";
    }

    private Context mContext;

    //数据库名
    private static final String DATABASE_NAME = "people.db";
    //数据库版本号
    private static final int DATABASE_Version = 1;
    //表名
    private static final String TABLE_NAME = "connect_people";

    //创建数据库
    public PeopleDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_Version);
        this.mContext = context;
    }

    //创建表并初始化表
    @Override
    public void onCreate (SQLiteDatabase db) {
        String sql = "Create table " + TABLE_NAME + "(" + BaseColumns._ID
                + " integer primary key autoincrement," + PictureColumns.PICTURE
                + " blob not null,num string,name string,color int);";
        db.execSQL(sql);
        Toast.makeText(mContext, "shujuku保存成功", Toast.LENGTH_SHORT).show();
        //初始化
    }

    //将转换后的图片存入到数据库中
    public void insertitem (SQLiteDatabase db, Bitmap bitmap,String name,String num,int color) {
        ContentValues cv = new ContentValues();
        cv.put("picture", getPicture(bitmap));
        cv.put("num",num);
        cv.put("name",name);
        cv.put("color",color);
        db.insert(TABLE_NAME, null, cv);
    }


    //将drawable转换成可以用来存储的byte[]类型
    private byte[] getPicture(Bitmap bitmap) {
        if(bitmap == null) {
            return null;
        }

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 100, os);
        return os.toByteArray();
    }

    //更新数据库
    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = " DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }
}
