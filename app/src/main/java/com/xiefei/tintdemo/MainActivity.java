package com.xiefei.tintdemo;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.graphics.drawable.DrawableWrapper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.xiefei.tintdemo.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView imageView = (ImageView) findViewById(R.id.image);
        ImageView imageView1 = (ImageView) findViewById(R.id.image1);
        ImageView imageView2 = (ImageView) findViewById(R.id.image2);
        Drawable drawable = ContextCompat.getDrawable(this,R.mipmap.icon);
        //简单的使用tint改变drawable颜色
        Drawable drawable1 = getTintDrawable(drawable,ContextCompat.getColor(this,R.color.pink));
        imageView.setImageDrawable(drawable1);

        int[] colors = new int[] { ContextCompat.getColor(this,R.color.pink),ContextCompat.getColor(this,R.color.pink1)};
        int[][] states = new int[2][];
        states[0] = new int[] { android.R.attr.state_pressed};
        states[1] = new int[] {};

        //该方法使用后会发现tint失效
//        StateListDrawable stateListDrawable = new StateListDrawable();
//        stateListDrawable.addState(states[0],ContextCompat.getDrawable(this,R.mipmap.pic));
//        stateListDrawable.addState(states[1],ContextCompat.getDrawable(this,R.mipmap.picl));
//        imageView1.setImageDrawable(stateListDrawable);

        //该方法6.0以下有效
        Drawable drawable2 = getStateDrawable(drawable,colors,states);
        imageView1.setImageDrawable(drawable2);

        StateListDrawable stateListDrawable = getStateListDrawable(drawable, states);
        Drawable drawable3 = getStateDrawable(stateListDrawable, colors, states);
        imageView2.setImageDrawable(drawable3);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private Drawable getTintDrawable(Drawable drawable,@ColorInt int color) {
        Drawable.ConstantState state = drawable.getConstantState();
        Drawable drawable1 = DrawableCompat.wrap(state == null ? drawable : state.newDrawable()).mutate();
        drawable1.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        DrawableCompat.setTint(drawable1, color);
        return drawable1;
    }

    /**
     *
     * @param drawable
     * @param colors
     * @param states
     * @return
     */
    private Drawable getStateDrawable(Drawable drawable, int[] colors, int[][] states) {
        ColorStateList colorList = new ColorStateList(states, colors);

        Drawable.ConstantState state = drawable.getConstantState();
        drawable = DrawableCompat.wrap(state == null ? drawable : state.newDrawable()).mutate();
        DrawableCompat.setTintList(drawable,colorList);
        return drawable;
    }

    @NonNull
    private StateListDrawable getStateListDrawable(Drawable drawable, int[][] states) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        for (int[] state:states) {
            stateListDrawable.addState(state,drawable);
        }
        return stateListDrawable;
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
