package com.example.fruitpictures.view;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.fruitpictures.R;
import com.example.fruitpictures.bean.Fruit;
import com.example.fruitpictures.presenter.FruitAdapter;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Fruit[] fruits = {new Fruit("Apple", R.drawable.apple), new Fruit("Banana", R.drawable.banana),
            new Fruit("Orange", R.drawable.orange), new Fruit("Watermelon", R.drawable.watermelon),
            new Fruit("Pear", R.drawable.pear), new Fruit("Grape", R.drawable.grape),
            new Fruit("Pineapple", R.drawable.pineapple), new Fruit("Strawberry", R.drawable.strawberry),
            new Fruit("Cherry", R.drawable.cherry), new Fruit("Mango", R.drawable.mango)};

    private DrawerLayout mDrawlayout;
    private FruitAdapter mAdapter;
    private ArrayList<Fruit> mFruits = new ArrayList<>();
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //获取actionbar也就是toolbar的实例
        ActionBar actionBar = getSupportActionBar();

        mDrawlayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        if (actionBar != null) {
            //显示导航栏
            actionBar.setDisplayHomeAsUpEnabled(true);
            //设置导航啦图标
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        //设置navigationView中的menu中的默认选中条目
        navView.setCheckedItem(R.id.nav_call);
        //给navigationView设置监听（嵌套了group，item只能单个选择）
        navView.setNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        ;
        //给悬浮按钮设置监听，用法功能基本和普通button一样
        fab.setOnClickListener(mFabOnClickListener);

        initFruits();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        //设置recycleView显示的布局方式，有三种
        recyclerView.setLayoutManager(gridLayoutManager);
        mAdapter = new FruitAdapter(mFruits);
        recyclerView.setAdapter(mAdapter);

        //下拉刷新
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruits();
            }
        });

    }

    private void refreshFruits() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initFruits();
                        mAdapter.notifyDataSetChanged();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }

    private void initFruits() {
        mFruits.clear();
        for (int i = 0; i < 50; i++) {
            Random random = new Random();
            int index = random.nextInt(fruits.length);
            mFruits.add(fruits[index]);
        }

}
//悬浮按钮点击监听
    private View.OnClickListener mFabOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Snackbar.make(view, "Data delete", Snackbar.LENGTH_SHORT).setAction("Undo", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainActivity.this, "floatingActionbutton Clicked", Toast.LENGTH_SHORT).show();
                }
            }).show();
        }
    };

    private NavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            mDrawlayout.closeDrawers();
            return true;
        }
    };
    //菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tollbar, menu);
        return true;
    }
    //菜单条目点击
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.backup:
                Toast.makeText(this, "You Clicked BackUp", Toast.LENGTH_SHORT).show();

                break;
            case R.id.delete:
                Toast.makeText(this, "You Clicked delete", Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                Toast.makeText(this, "You Clicked settings", Toast.LENGTH_SHORT).show();
                break;

            case android.R.id.home:
                mDrawlayout.openDrawer(GravityCompat.START);
                break;
        }

        return true;
    }


}
