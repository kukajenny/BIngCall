package com.example.zjp.bingcall.Main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import com.example.zjp.bingcall.Database.PeopleDatabaseHelper;
import com.example.zjp.bingcall.R;
import com.example.zjp.bingcall.colorpicker.ColorPickerDialog;
import com.example.zjp.bingcall.colorpicker.ColorPickerSwatch;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import at.markushi.ui.CircleButton;

/**
 * Created by zjp on 15-10-28.
 */
public class AddActivity extends AppCompatActivity {

    private final int CHOOSE_PICTURE=2;
    private final int Get_picture=3;
    private final int RETURN_INTENT=1;
    private static final int REQUEST_CONTACT = 4;
    private ImageButton imageButton;
    private Toolbar toolbar;
    private Button button_add_save;
    private TextView add_name,add_num;
    private String string_add_name,string_add_num;
    private Intent return_intent;
    private AlertDialog.Builder builder;
    boolean flag=false;
    int save_color;
    Bitmap save_bitmap;
    private CircleButton circleButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        init();
    }


    private void startContact() {

        Intent intent = new Intent();

        intent.setAction(Intent.ACTION_PICK);

        intent.setData(ContactsContract.Contacts.CONTENT_URI);

        startActivityForResult(intent, REQUEST_CONTACT);

    }

    private void init() {

        add_name = (TextView)findViewById(R.id.add_name);
        add_num = (TextView)findViewById(R.id.add_num);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);

        return_intent = new Intent();
        save_color=getResources().getColor(R.color.select_color1);

        imageButton = (ImageButton)findViewById(R.id.add_people);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                openAlbumIntent.setType("image/*");
                startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);

            }
        });

        button_add_save = (Button)findViewById(R.id.add_save);
        button_add_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save_people();
            }
        });

        Button add_book=(Button)findViewById(R.id.add_book);
        add_book.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startContact();
            }
        });


        circleButton = (CircleButton)findViewById(R.id.circlebutton1);
        final ColorPickerDialog colorPickerDialog = new ColorPickerDialog();
        final Resources resources = getResources();
        colorPickerDialog.initialize(R.string.dialog_title, new int[]{resources.getColor(R.color.select_color1), resources.getColor(R.color.select_color2), resources.getColor(R.color.select_color3), resources.getColor(R.color.select_color4), resources.getColor(R.color.select_color5), resources.getColor(R.color.select_color6), resources.getColor(R.color.select_color7), resources.getColor(R.color.select_color8), resources.getColor(R.color.select_color9),}, Color.YELLOW, 3, 2);
        colorPickerDialog.setOnColorSelectedListener(new ColorPickerSwatch.OnColorSelectedListener() {

            @Override
            public void onColorSelected(int color) {
                circleButton.setColor(color);
                save_color = color;

            }
        });


        circleButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(AddActivity.this,ButtonMActivity.class);
                startActivityForResult(intent,return_color);*/
                colorPickerDialog.show(getSupportFragmentManager(), "colorpicker");
            }
        });

        Intent getintent=getIntent();
            String name=getintent.getStringExtra("name");
            String number=getintent.getStringExtra("number");
            Bitmap bitmap=getintent.getParcelableExtra("bitmap");
            int color=getintent.getIntExtra("background", 0);
            add_name.setText(name);
            add_num.setText(number);
            if(bitmap!=null){
                imageButton.setImageBitmap(getbitmap(bitmap));
                save_bitmap=bitmap;
                flag=true;
            }
            if(color!=0){
                circleButton.setColor(color);
                save_color=color;
            }





    }

    public Bitmap getbitmap(Bitmap bitmap){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        //放大為1.2倍
        float screenWidth  = getWindowManager().getDefaultDisplay().getWidth();		// 屏幕宽（像素，如：480px）
        float screenHeight = getWindowManager().getDefaultDisplay().getHeight();		// 屏幕高（像素，如：800p）
        Log.d("screen",screenWidth+"");
        float scaleWidth = screenWidth/2/width;
        float scaleHeight = screenWidth/2/width;

        // 取得想要缩放的matrix參數
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的圖片
        Bitmap newbm = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix,true);
        return newbm;
    }
    public void save_people(){
        if(!flag){
            AlertDialog.Builder dialog = new AlertDialog.Builder(AddActivity.this);
            dialog.setTitle("请选择照片");
            dialog.setMessage("从图库选择照片");
            dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                            openAlbumIntent.setType("image/*");
                            startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                        }
                    }
            );
            dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialog.show();
            return;
        }

        string_add_name = add_name.getText().toString();
        string_add_num = add_num.getText().toString();
        Log.d("string_return", string_add_name + " jbj " + string_add_num);
        if(string_add_name.equals("")||string_add_num.equals("")){
            AlertDialog.Builder dialog = new AlertDialog.Builder(AddActivity.this);
            dialog.setTitle("请输入");
            dialog.setMessage("请输入姓名和电话");
            dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }
            );
            dialog.show();
            return;
        }
      /*  try{

        }catch ()*/
        else{
            return_intent.putExtra("return_add_name", string_add_name);
            return_intent.putExtra("return_add_num", string_add_num);
            return_intent.putExtra("return_color",save_color);
            return_intent.putExtra("return_data",save_bitmap);
            setResult(RETURN_INTENT, return_intent);
            Log.d("AddActivity", "yes");
            Toast.makeText(AddActivity.this, "保存成功", Toast.LENGTH_SHORT).show();


            AddActivity.this.finish();
        }


    }







    public Dialog onCreateDialog() {

        builder = new AlertDialog.Builder(AddActivity.this);
        // Get the layout inflater
        LayoutInflater inflater = AddActivity.this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_selectcolor, null));
                // Add action buttons
        //初始化控件

        return builder.create();
    }




    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);

        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", false);// 取消人脸识别
        intent.putExtra("return-data", true);
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        startActivityForResult(intent, Get_picture);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null) {
            switch (requestCode) {
                case CHOOSE_PICTURE:
                    if (resultCode == RESULT_OK) {//从相册选择照片不裁切
                        Uri uri = data.getData();
                   /*     try{
                            Bitmap photo = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            imageButton.setImageBitmap(photo);
                            Log.d("fff","yes");
                            return_intent.putExtra("return_data",photo);
                            flag=true;
                        }catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        String s=uri.toString();
                        Log.d("string1",s);*/
                        crop(uri);
                    }
                    break;
                case Get_picture:
                    Bitmap bitmap = data.getParcelableExtra("data");
                    imageButton.setImageBitmap(getbitmap(bitmap));
                    save_bitmap = bitmap;
                    //return_intent.putExtras(data);
                    flag = true;
                    break;
                case REQUEST_CONTACT:
                    if (resultCode == RESULT_OK) {

                        Uri contactData = data.getData();
                        CursorLoader cursorLoader = new CursorLoader(this, contactData,
                                null, null, null, null);
                        // 查询联系人信息
                        Cursor cursor = cursorLoader.loadInBackground();
                        // 如果查询到指定的联系人
                        if (cursor.moveToFirst()) {
                            String contactId = cursor.getString(cursor
                                    .getColumnIndex(ContactsContract.Contacts._ID));
                            // 获取指定的联系人查询该联系人的信息
                            String name = cursor
                                    .getString(cursor
                                            .getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME));
                            String phoneNumbere = "此联系人暂时未输入电话号码";
                            Cursor phones = getContentResolver().query(
                                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                    null,
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                            + "=" + contactId, null, null);
                            if (phones.moveToFirst()) {
                                // 取出电话号码
                                phoneNumbere = phones
                                        .getString(phones
                                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            }
                            //关闭游标
                            phones.close();
                            //显示联系人与联系电话
                            Log.d("bug",name+"  "+phoneNumbere);
                            add_name.setText(name);
                            add_num.setText(phoneNumbere);
                            break;

                        }
                    }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                this.finish();
                //noinspection SimplifiableIfStatement
        }
        return super.onOptionsItemSelected(item);
    }
}
