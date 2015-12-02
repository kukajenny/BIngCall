package com.example.zjp.bingcall.Main;

/**
 * Created by zjp on 15-11-1.
 */
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.zjp.bingcall.R;
import com.example.zjp.bingcall.Round_Button.ButtonM;

/**
 * ButtonM测试类
 * 自定义dialog
 * 把系统背景颜色设为透明
 * 按钮为ButtonM:圆形按钮
 */
public class ButtonMActivity extends Activity implements OnClickListener{
        //定义三个空布局用来装载Button控件，只为演示效果，实际开发中不推荐使用
        private LinearLayout llButtonM1;
        private LinearLayout llButtonM2;
        private LinearLayout llButtonM3;
        private ButtonM now;
        //定义三个ButtonM数组
        private ButtonM[] buttonM1;
    private ButtonM[] buttonM2;
    private ButtonM[] buttonM3;
    //定义两组颜色值，按下与未按下的按钮背景色
    private static final int[] colorList = {R.color.select_color1,R.color.select_color2,R.color.select_color3,R.color.select_color4,R.color.select_color5,R.color.select_color6,R.color.select_color7,R.color.select_color8,R.color.select_color9};
    private static final int[] colorSelectedList = {R.color.selected_color1,R.color.selected_color2,R.color.selected_color3,R.color.selected_color4,R.color.selected_color5,R.color.selected_color6,R.color.selected_color7,R.color.selected_color8,R.color.selected_color9,};
    private static final int size=3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_selectcolor);
        initView();

        ImageButton imageButton = (ImageButton)findViewById(R.id.imagebutton_dialog_close);
        imageButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ButtonMActivity.this.finish();
            }
        });
    }
    //初始化控件
    private void initView() {
        //实例化布局控件
        llButtonM1 = (LinearLayout) findViewById(R.id.linnear1);
        llButtonM2 = (LinearLayout) findViewById(R.id.linnear2);
        llButtonM3 = (LinearLayout) findViewById(R.id.linnear3);
        //实例化控件数组，各定义4个
        buttonM1 = new ButtonM[3];
        buttonM2 = new ButtonM[3];
        buttonM3 = new ButtonM[3];
        final Resources resources = getResources();
        //获取屏幕的宽度，每行四个Button，间隙为60共300，除4为每个控件的宽度
        @SuppressWarnings("deprecation")
        int btnWidth = (getWindowManager().getDefaultDisplay().getWidth() - 250)/3;
        //定义第一个布局
        LinearLayout.LayoutParams lp1;
        for (int i = 0; i < 3; i++) {
            //为buttonM1设置样式，直角矩形
            buttonM1[i] = new ButtonM(this);
            //字体颜色
            buttonM1[i].setTextColori(android.graphics.Color.WHITE);
            //字体大小
            buttonM1[i].setTextSize(14);
            buttonM1[i].setShape(GradientDrawable.OVAL);
            buttonM1[i].setFillet(true);
            //背景色
            buttonM1[i].setBackColor(resources.getColor(colorList[i]));
            //选中的背景色
            buttonM1[i].setBackColorSelected(resources.getColor(colorSelectedList[i]));
            //文字提示
            //此处设置Id的值为i，否则onClick中v.getId()将永远为-1
            buttonM1[i].setId(i);
            //定义buttonM1的布局，宽度自适应，高度为宽度的0.6倍，权重为1
            //也可以写成lp1 = new LinearLayout.LayoutParams(btnWidth,(int) (btnWidth * 0.6));
            lp1 = new LinearLayout.LayoutParams(btnWidth,btnWidth);
            //控件距离其右侧控件的距离，此处为60
            lp1.setMargins(0,0,50,0);
            buttonM1[i].setLayoutParams(lp1);
            //设置buttonM1的点击事件
            now=buttonM1[i];
            buttonM1[i].setOnClickListener(this);
            //设置PaddingLeft为60
            llButtonM1.setPadding(50, 0, 0, 0);
            //将buttonM1添加到llButtonM1中
            llButtonM1.addView(buttonM1[i]);
        }
        //定义第二个布局
        LinearLayout.LayoutParams lp2;
        for (int i = 0; i < 3; i++) {
            //为buttonM2设置样式，圆角矩形
            buttonM2[i] = new ButtonM(this);
            buttonM2[i].setTextColori(android.graphics.Color.WHITE);
            buttonM2[i].setTextSize(14);
            //设置是否为圆角
            buttonM2[i].setShape(GradientDrawable.OVAL);
            buttonM2[i].setFillet(true);
            buttonM2[i].setBackColor(resources.getColor(colorList[i + size]));
            buttonM2[i].setBackColorSelected(resources.getColor(colorSelectedList[i + size]));
            buttonM2[i].setId(size+i);
            lp2 = new LinearLayout.LayoutParams(btnWidth,btnWidth);
            lp2.setMargins(0,0,50,0);
            buttonM2[i].setLayoutParams(lp2);
            now=buttonM2[i];
            buttonM2[i].setOnClickListener(this);
            llButtonM2.setPadding(50, 0, 0, 0);
            llButtonM2.addView(buttonM2[i]);
        }
        //定义第三个布局
        LinearLayout.LayoutParams lp3;
        for (int i = 0; i < 3; i++) {
            //为buttonM3设置样式，圆形
            buttonM3[i] = new ButtonM(this);
            buttonM3[i].setTextColori(android.graphics.Color.WHITE);
            buttonM3[i].setTextSize(14);
            //设置为圆形，默认为矩形，GradientDrawable.RECTANGLE
            buttonM3[i].setShape(GradientDrawable.OVAL);
            buttonM3[i].setFillet(true);
            buttonM3[i].setBackColor(resources.getColor(colorList[i + 2 * size]));
            buttonM3[i].setBackColorSelected(resources.getColor(colorSelectedList[i + 2 * size]));
            buttonM3[i].setId(2*size+i);
            lp3 = new LinearLayout.LayoutParams(btnWidth,btnWidth);
            lp3.setMargins(0,0,50,0);
            buttonM3[i].setLayoutParams(lp3);
            now=buttonM3[i];
            buttonM3[i].setOnClickListener(this);
            llButtonM3.setPadding(50, 0, 0, 0);
            llButtonM3.addView(buttonM3[i]);
        }
    }

    @Override
    public void onClick(View v) {
        ((ButtonM)v).setBackGroundImageSeleted(R.drawable.colorselect);
        ((ButtonM)v).setBackgroundDrawable(newSelector(this, colorList[((ButtonM)v).getId()], R.drawable.colorselect,
                R.drawable.add_people, R.drawable.colorselect));
    }

    public static StateListDrawable newSelector(Context context, int idNormal, int idPressed, int idFocused,
                                                int idUnable) {
        StateListDrawable bg = new StateListDrawable();
        Drawable normal = idNormal == -1 ? null : context.getResources().getDrawable(idNormal);
        Drawable pressed = idPressed == -1 ? null : context.getResources().getDrawable(idPressed);
        Drawable focused = idFocused == -1 ? null : context.getResources().getDrawable(idFocused);
        Drawable unable = idUnable == -1 ? null : context.getResources().getDrawable(idUnable);
        // View.PRESSED_ENABLED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled }, pressed);
        // View.ENABLED_FOCUSED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_enabled, android.R.attr.state_focused }, focused);
        // View.ENABLED_STATE_SET
        //bg.addState(new int[] { android.R.attr.state_enabled }, normal);
        // View.FOCUSED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_focused }, focused);
        // View.WINDOW_FOCUSED_STATE_SET
        bg.addState(new int[] { android.R.attr.state_window_focused }, unable);
        // View.EMPTY_STATE_SET
        //bg.addState(new int[] {}, normal);
        return bg;
    }
}