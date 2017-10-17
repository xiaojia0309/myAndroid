package com.example.john.myapplication.activity;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.john.myapplication.R;
import com.example.john.myapplication.tools_used.Fruit;
import com.example.john.myapplication.tools_used.FruitAdapter;

import java.util.ArrayList;
import java.util.List;

public class Main3Activity extends AppCompatActivity {

    private String[] data = {"Apple", "Banana", "Orange", "Watermelon",
            "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango",
            "WatermeLon", "Pear", "Grape",
            "Apple", "Banana", "Orange",
            "Pineapple", "Strawberry", "Cherry", "Mango"};

    private List<Fruit> fruitList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        //去除title
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        setContentView(R.layout.activity_main3);

        initFruits();//先初始化数据
        FruitAdapter fruitAdapter = new FruitAdapter(Main3Activity.this,R.layout.fruit_item,fruitList);
        ListView listView = (ListView) findViewById(R.id.list_item);
        listView.setAdapter(fruitAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Fruit fruit = fruitList.get(i);
                Toast.makeText(Main3Activity.this, fruit.getName(), Toast.LENGTH_SHORT).show();
            }
        });

/*
        //选取合适的适配器！s
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Main3Activity.this,android.R.layout.simple_list_item_1,data);
        ListView listView = (ListView) findViewById(R.id.list_item);
        listView.setAdapter(adapter);*/



    }
    private void initFruits() {
        for (int i = 0; i < 2; i++) {
            Fruit apple = new Fruit("Apple", R.drawable.apple_pic);
            fruitList.add(apple);
            Fruit banana = new Fruit("Banana", R.drawable.banana_pic);
            fruitList.add(banana);
            Fruit orange = new Fruit("Orange", R.drawable.orange_pic);
            fruitList.add(orange);
            Fruit watermelon = new Fruit("Watermelon", R.drawable.watermelon_pic);
            fruitList.add(watermelon);
            Fruit pear = new Fruit("Pear", R.drawable.pear_pic);
            fruitList.add(pear);
            Fruit grape = new Fruit("Grape", R.drawable.grape_pic);
            fruitList.add(grape);
            Fruit pineapple = new Fruit("Pineapple", R.drawable.pineapple_pic);
            fruitList.add(pineapple);
            Fruit strawberry = new Fruit("Strawberry", R.drawable.strawberry_pic);
            fruitList.add(strawberry);
            Fruit cherry = new Fruit("Cherry", R.drawable.cherry_pic);
            fruitList.add(cherry);
            Fruit mango = new Fruit("Mango", R.drawable.mango_pic);
            fruitList.add(mango);
        }
    }
}
