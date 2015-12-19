package com.example.zjp.bingcall.Main;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.zjp.bingcall.R;

import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private CheckBox pass;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();


    }

    public void init(){

        username=(EditText)findViewById(R.id.main_username);
        password=(EditText)findViewById(R.id.main_password);
        pass=(CheckBox)findViewById(R.id.rembmberCheckBox);
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        pass=(CheckBox)findViewById(R.id.rembmberCheckBox);

        boolean isremember=pref.getBoolean("rembmbered", false);

        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        if(data!=null){
            username.setText("");
            password.setText("");
            SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("rembmbered", false);
                editor.putString("account", "");
                editor.putString("password", "");
            editor.commit();
        }


        Button button=(Button)findViewById(R.id.signin_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String account = username.getText().toString();
                String passwordtext = password.getText().toString();
                if (account.equals("admin") && passwordtext.equals("123")) {
                    Toast.makeText(MainActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = pref.edit();
                    if (pass.isChecked()) {
                        editor.putBoolean("rembmbered", true);
                        editor.putString("account", account);
                        editor.putString("password", passwordtext);
                    } else {
                        editor.clear();
                    }
                    editor.commit();

                    //进入相机
                   /* Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(openCameraIntent, 1);*/


                    //进入相册
                    /*Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    openAlbumIntent.setType("image");
                    startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);*/

                    Intent intent1=new Intent(MainActivity.this,Bingcall.class);
                    startActivity(intent1);
                } else {
                    Toast.makeText(MainActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                    username.setText("");
                    password.setText("");
                }


            }
        });
        if(isremember){
            if(data==null){
                String account=pref.getString("account","");
                String passwordtext=pref.getString("password","");
                username.setText(account);
                password.setText(passwordtext);
                pass.setChecked(true);
                Intent intent1=new Intent(MainActivity.this,Bingcall.class);
                startActivity(intent1);
            }
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
}
