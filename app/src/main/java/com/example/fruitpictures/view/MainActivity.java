package com.example.fruitpictures.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fruitpictures.R;
import com.example.fruitpictures.bean.Images;
import com.example.fruitpictures.model.ImageServices;
import com.example.fruitpictures.presenter.FruitAdapter;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {
//    private Fruit[] fruits = {new Fruit("Apple", R.drawable.apple), new Fruit("Banana", R.drawable.banana),
//            new Fruit("Orange", R.drawable.orange), new Fruit("Watermelon", R.drawable.watermelon),
//            new Fruit("Pear", R.drawable.pear), new Fruit("Grape", R.drawable.grape),
//            new Fruit("Pineapple", R.drawable.pineapple), new Fruit("Strawberry", R.drawable.strawberry),
//            new Fruit("Cherry", R.drawable.cherry), new Fruit("Mango", R.drawable.mango)};

    private DrawerLayout mDrawlayout;
    int count = 1;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private static final int SUCCESS = 1;
    private static Handler mHandler = new Handler() {

        private FruitAdapter mMAdapter;

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESS:

                    ArrayList<Images.ResultsBean> results = (ArrayList<Images.ResultsBean>) msg.obj;
                    ArrayList<String> mImages = new ArrayList<>();

                    for (int i = 0; i < results.size(); i++) {

                        mImages.add(results.get(i).getUrl());
                    }

                    mMAdapter = new FruitAdapter(mImages);
                    mRecyclerView.setAdapter(mMAdapter);

                    break;
            }

            super.handleMessage(msg);
        }
    };
    private static RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private RelativeLayout mSuspensionBar;
    private TextView mSuspensionTv;
    private ImageView mSuspensionIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //获取actionbar也就是toolbar的实例
        ActionBar actionBar = getSupportActionBar();
        ShareSDK.initSDK(this);

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
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //  GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        //设置recycleView显示的布局方式，有三种
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mSuspensionBar = (RelativeLayout) findViewById(R.id.suspension_bar);
        mSuspensionTv = (TextView) findViewById(R.id.tv_nickname);
        mSuspensionIv = (ImageView) findViewById(R.id.iv_avatar);
        //下拉刷新
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFruits();
            }
        });


        mRecyclerView.addOnScrollListener(mOnScrollChangeListener);
        updateSuspensionBar();

    }

    private int mCurrentPosition = 0;
    private int mSuspensionHeight;
    private RecyclerView.OnScrollListener mOnScrollChangeListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            mSuspensionHeight = mSuspensionBar.getHeight();
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            View view = mLinearLayoutManager.findViewByPosition(mCurrentPosition + 1);
            if (view != null) {
                if (view.getTop() <= mSuspensionHeight) {
                    mSuspensionBar.setY(-(mSuspensionHeight - view.getTop()));
                } else {
                    mSuspensionBar.setY(0);
                }
            }

            if (mCurrentPosition != mLinearLayoutManager.findFirstVisibleItemPosition()) {
                mCurrentPosition = mLinearLayoutManager.findFirstVisibleItemPosition();
                mSuspensionBar.setY(0);

                updateSuspensionBar();
            }
        }
    };

    private void updateSuspensionBar() {
        Picasso.with(MainActivity.this)
                .load(getAvatarResId(mCurrentPosition))
                .centerInside()
                .fit()
                .into(mSuspensionIv);

        mSuspensionTv.setText("Taeyeon " + mCurrentPosition);
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
//                        mAdapter.notifyDataSetChanged();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();


    }

    private int getAvatarResId(int position) {
        switch (position % 4) {
            case 0:
                return R.drawable.avatar1;
            case 1:
                return R.drawable.avatar2;
            case 2:
                return R.drawable.avatar3;
            case 3:
                return R.drawable.avatar4;
        }
        return 0;
    }

    private void initFruits() {
       /* mFruits.clear();
        for (int i = 0; i < 50; i++) {
            Random random = new Random();
            int index = random.nextInt(fruits.length);
            mFruits.add(fruits[index]);
        }*/

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/")
                //增加返回值为String的支持
                .addConverterFactory(ScalarsConverterFactory.create())
                //增加返回值为Gson的支持(以实体类返回)
                .addConverterFactory(GsonConverterFactory.create())
                //增加返回值为Oservable<T>的支持
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        ImageServices imageServices = retrofit.create(ImageServices.class);

        String page = count++ % 8 + "";
        Call<String> call = imageServices.loagImages(page);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String s = response.body().toString();
                Gson gson = new Gson();

                Images images = gson.fromJson(s, Images.class);
                List<Images.ResultsBean> results = images.getResults();

                mHandler.obtainMessage(SUCCESS, results).sendToTarget();

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

    //悬浮按钮点击监听
    private View.OnClickListener mFabOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            PopupMenu pop = new PopupMenu(MainActivity.this, view);
            pop.getMenuInflater().inflate(R.menu.pop_menu, pop.getMenu());
            pop.show();
            pop.setOnMenuItemClickListener(mOnMenuItemClickListener);

           /* Snackbar.make(view, "Data delete", Snackbar.LENGTH_SHORT).setAction("Undo", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(MainActivity.this, "floatingActionbutton Clicked", Toast.LENGTH_SHORT).show();
                }
            }).show();*/
        }
    };
    PopupMenu.OnMenuItemClickListener mOnMenuItemClickListener = new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {

            switch (item.getItemId()) {

                case R.id.pie_Chart:
                    startActivity(new Intent(MainActivity.this, AssetsActivity.class));
                    break;
                case R.id.ling_Chart:
                    startActivity(new Intent(MainActivity.this, LineChartActivity.class));
                    break;
                case R.id.Bar_Chart:
                    startActivity(new Intent(MainActivity.this, ProfitActivity.class));
                    break;
            }

            return false;
        }
    };
    private NavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {

            switch (item.getItemId()) {

                case R.id.nav_mail:
                    break;
                case R.id.nav_task:
                    item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            OnekeyShare oks = new OnekeyShare();
                            //关闭sso授权
                            oks.disableSSOWhenAuthorize();
                            // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
                            oks.setTitle("标题");
                            // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
                            oks.setTitleUrl("http://sharesdk.cn");
                            // text是分享文本，所有平台都需要这个字段
                            oks.setText("我是分享文本");
                            //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
                            oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
                            // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
                            //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
                            // url仅在微信（包括好友和朋友圈）中使用
                            oks.setUrl("http://sharesdk.cn");
                            // comment是我对这条分享的评论，仅在人人网和QQ空间使用
                            oks.setComment("我是测试评论文本");
                            // site是分享此内容的网站名称，仅在QQ空间使用
                            oks.setSite("ShareSDK");
                            // siteUrl是分享此内容的网站地址，仅在QQ空间使用
                            oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
                            oks.show(MainActivity.this);
                            return false;
                        }
                    });
                    break;
                default:
                    mDrawlayout.closeDrawers();
                    break;

            }
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
