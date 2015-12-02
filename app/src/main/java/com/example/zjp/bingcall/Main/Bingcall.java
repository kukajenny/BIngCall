package com.example.zjp.bingcall.Main;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.zjp.bingcall.BaseFlipAdapter;
import com.example.zjp.bingcall.Bingcall_List.Listitem;
import com.example.zjp.bingcall.Database.PeopleDatabaseHelper;
import com.example.zjp.bingcall.R;

import com.example.zjp.bingcall.SlidingLayout.SlidingLayout;
import com.example.zjp.bingcall.SlidingLayout.ThreeDSlidingLayout;
import com.yalantis.flipviewpager.utils.FlipSettings;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zjp on 15-10-27.
 */
public class Bingcall extends AppCompatActivity{
    private Toolbar toolbar;
    private  final int get_photo=1;
    private ListView listView;
    public  List<Listitem> friends = new ArrayList<>();
    private ListAdapter listAdapter;
    private ThreeDSlidingLayout slidingLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bingcall);
        init();
    }

    private void init() {
        Log.d("BingCall","init");
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        listView = (ListView)findViewById(R.id.list_view);
        FlipSettings settings = new FlipSettings.Builder().defaultPage(1).build();

        PeopleDatabaseHelper dbhelper = new PeopleDatabaseHelper(this);
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor cursor=db.query("connect_people", null, null, null, null, null, null);

        ImageButton home=(ImageButton)findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (slidingLayout.isLeftLayoutVisible()) {
                    slidingLayout.scrollToRightLayout();
                } else {
                    slidingLayout.scrollToLeftLayout();
                }
            }
        });

        Button exit = (Button)findViewById(R.id.bingcall_exit);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Bingcall.this,MainActivity.class);
                intent.putExtra("data","data");
                startActivity(intent);
                Bingcall.this.finish();
            }
        });

        Button contact_import = (Button)findViewById(R.id.bingcall_contact_import);
        contact_import.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getpeople();
            }
        });



        if(cursor.moveToFirst()){
            do{
                String name=cursor.getString(cursor.getColumnIndex("name"));
                String num = cursor.getString(cursor.getColumnIndex("num"));
                int color = cursor.getInt(cursor.getColumnIndex("color"));
                byte[] b = cursor.getBlob(cursor.getColumnIndexOrThrow("picture"));
                Log.d("Bingcallcolor",color+"");
                //将获取的数据转换成drawable
                Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length, null);
                Bitmap newbm = getbitmap(bitmap);
                friends.add(new Listitem(num,name,newbm
                        ,color));

            }while(cursor.moveToNext());
        }
        listAdapter = new ListAdapter(this, friends, settings);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Listitem listitem = (Listitem) listView.getAdapter().getItem(position);
                //Toast.makeText(Bingcall.this, "点击" + position + listitem.getName() + listView.getAdapter().getItemId(position), Toast.LENGTH_SHORT).show();
                String num = listitem.getCallnumber();
                Pattern p = Pattern.compile("\\d+?");
                Matcher match = p.matcher(num);
                //正则验证输入的是否为数字
                if (match.matches()) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + num));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    Toast.makeText(Bingcall.this, "号码不对", Toast.LENGTH_LONG).show();
                }
                ListAdapter ld=(ListAdapter)listView.getAdapter();
                Log.d("haha1", ld.getFlipViewPager() + "");
            }
        });


        /*slidingLayout = (SlidingLayout) findViewById(R.id.slidingLayout);
        slidingLayout.setScrollEvent(listView);*/

           /* friends.add(new Listitem("110", "周娟萍", R.drawable.anastasia, R.color.select_color1));
            friends.add(new Listitem( "222","IRENE",R.drawable.irene, R.color.select_color2));
        friends.add(new Listitem( "222","IRENE",R.drawable.irene, R.color.select_color3));*/

            /*friends.add(new Friend(R.drawable.kate, "KATE", R.color.green, "Sales", "Pets", "Skiing", "Hairstyles", "Сoffee"));
            friends.add(new Friend(R.drawable.paul, "PAUL", R.color.pink, "Android", "Development", "Design", "Wearables", "Pets"));
            friends.add(new Friend(R.drawable.daria, "DARIA", R.color.orange, "Design", "Fitness", "Healthcare", "UI/UX", "Chatting"));
            friends.add(new Friend(R.drawable.kirill, "KIRILL", R.color.saffron, "Development", "Android", "Healthcare", "Sport", "Rock Music"));
            friends.add(new Friend(R.drawable.julia, "JULIA", R.color.green, "Cinema", "Music", "Tatoo", "Animals", "Management"));
            friends.add(new Friend(R.drawable.yalantis, "YALANTIS", R.color.purple, "Android", "IOS", "Application", "Development", "Company"));*/

         slidingLayout = (ThreeDSlidingLayout) findViewById(R.id.slidingLayout);
        slidingLayout.setScrollEvent(listView);

    }

    private void getpeople() {
        ContentResolver contentResolver = getContentResolver();
// 获得所有的联系人
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
// 循环遍历
        if (cursor.moveToFirst()) {

            int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);

            int displayNameColumn = cursor.getColumnIndex(
                    ContactsContract.Contacts.DISPLAY_NAME);

            do {
// 获得联系人的ID号
                String contactId = cursor.getString(idColumn);

// 获得联系人姓名
                String disPlayName = cursor.getString(displayNameColumn);


// 查看该联系人有多少个电话号码。如果没有这返回值为0
                int phoneCount = cursor.getInt(cursor.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER));

                if (phoneCount > 0) {

// 获得联系人的电话号码列表
                    Cursor phonesCursor = getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                    + " = " + contactId, null, null);
                    if (phonesCursor.moveToFirst()) {
                        do {
// 遍历所有的电话号码
                            String phoneNumber = phonesCursor
                                    .getString(phonesCursor
                                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            Bitmap  bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.picture1);
                            friends.add(new Listitem(phoneNumber, disPlayName,getbitmap(bitmap) , getResources().getColor(R.color.select_color1)));
                            listAdapter.notifyDataSetChanged();
                        } while (phonesCursor.moveToNext());
                    }
                    phonesCursor.close();

                }
            }while(cursor.moveToNext());

            AlertDialog.Builder dialog = new AlertDialog.Builder(Bingcall.this);
            dialog.setTitle("导入通讯录成功");
            dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            slidingLayout.scrollToRightLayout();
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
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_people) {
            Intent intent = new Intent(Bingcall.this,AddActivity.class);
            startActivityForResult(intent,get_photo);
        }
        if(id==R.id.home){
            if (slidingLayout.isLeftLayoutVisible()) {
                slidingLayout.scrollToRightLayout();
            } else {
                slidingLayout.scrollToLeftLayout();
            }
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            switch (requestCode) {
                case get_photo:
                    Bitmap bitmap = data.getParcelableExtra("return_data");
                    //ImageView imageView = (ImageView)findViewById(R.id.bingcall_imageview);
                    //imageView.setImageBitmap(bitmap);
                    String return_name=data.getStringExtra("return_add_name");
                    String return_num=data.getStringExtra("return_add_num");
                    int return_color = data.getIntExtra("return_color", 0);

                    Bitmap newbm = getbitmap(bitmap);

                    friends.add(new Listitem(return_num, return_name, newbm, return_color));
                    listAdapter.notifyDataSetChanged();
                    Log.d("BingCall1","yes");
                    break;
            /*case 2:Bitmap bitmap = data.getParcelableExtra("data");
                imageButton.setImageBitmap(bitmap);
                break;*/
            }
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


    class ListAdapter extends BaseFlipAdapter<Listitem> {

        private final int PAGES = 3;

        public ListAdapter(Context context, List<Listitem> items, FlipSettings settings) {
            super(context, items, settings);
        }



        @Override
        public View getPage(int position, View convertView, ViewGroup parent, Listitem friend1, Listitem friend2) {

            final FriendsHolder holder;
            if (convertView == null) {
                holder = new FriendsHolder();
                convertView = getLayoutInflater().inflate(R.layout.listitem, parent, false);
                holder.leftAvatar = (ImageButton) convertView.findViewById(R.id.listitem_left_imagebutton);
                holder.rightAvatar = (ImageButton) convertView.findViewById(R.id.listitem_right_imagebutton);
                /*holder.left_nickname = (TextView)convertView.findViewById(R.id.listitem_left_textview);
                holder.right_nickname = (TextView)convertView.findViewById(R.id.listitem_right_textview);*/

                holder.infoPage = getLayoutInflater().inflate(R.layout.listitem_page, parent, false);
                holder.nickName = (TextView) holder.infoPage.findViewById(R.id.nickname);
                holder.telnum = (TextView) holder.infoPage.findViewById(R.id.textview_page_telnum);
                holder.button_edit = (Button)holder.infoPage.findViewById(R.id.button_page_modify);
                holder.button_delect = (Button)holder.infoPage.findViewById(R.id.button_page_delete);
                convertView.setTag(holder);
            } else {
                holder = (FriendsHolder) convertView.getTag();
            }

            switch (position) {
                // Merged page with 2 friends
                case 1:
                    holder.leftAvatar.setImageBitmap(friend1.getBitmap());
                    /*holder.left_nickname.setText(friend1.getName());*/
                    if (friend2 != null){
                        holder.rightAvatar.setImageBitmap( friend2.getBitmap());
                        /*holder.right_nickname.setText(friend2.getName());*/
                        Log.d("BingCall1",friend1.getName());
                    }

                    break;
                default:
                    fillHolder(holder, position == 0 ? friend1 : friend2);
                    holder.infoPage.setTag(holder);
                    return holder.infoPage;
            }
            Log.d("haha3", holder.infoPage + "");
            return convertView;
        }


        @Override
        public int getPagesCount() {
            return PAGES;
        }

        private void fillHolder(FriendsHolder holder, Listitem friend) {
            if (friend == null)
                return;
            holder.infoPage.setBackgroundColor(friend.getBackground());
            holder.nickName.setText(friend.getName());
            holder.telnum.setText(friend.getCallnumber());

        }


        class FriendsHolder {
            ImageButton leftAvatar;
            ImageButton rightAvatar;
            View infoPage;
            TextView nickName,left_nickname,right_nickname;
            TextView telnum;
            Button button_edit,button_delect;

        }
    }

}

