package com.example.fruitpictures.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.fruitpictures.R;


public class FruitActivity extends AppCompatActivity {

    public static final String FRUIT_NAME = "fruitName";
    public static final String FRUIT_IMAGE_ID = "fruitImageId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit);
        Intent intent = getIntent();
       String  fruitName= intent.getStringExtra(FRUIT_NAME);
     //   int  fruitImageId= intent.getIntExtra(FRUIT_IMAGE_ID,0);
        String url = intent.getStringExtra(FRUIT_IMAGE_ID);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        ImageView fruitIamge = (ImageView) findViewById(R.id.fruit_image_view);
        TextView fruitContentText = (TextView) findViewById(R.id.fruit_content_text);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar!= null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        collapsingToolbarLayout.setTitle("图片");
        Glide.with(this).load(url).into(fruitIamge);
        String fruitContent = generateFruitContent(fruitName);
        fruitContentText.setText(fruitContent);
    }

    private String generateFruitContent(String fruitName) {
        for (int i = 0; i < 100; i++) {
            fruitName = fruitName + "手心上，亘古的月光，你翘首祈祷，而我矗立一旁.\n";
        }

        return fruitName;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
        }
        return true;
      //  return super.onOptionsItemSelected(item);
    }
}
