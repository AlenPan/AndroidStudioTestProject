package com.example.recycleviewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.R.attr.name;
import static android.R.id.list;

public class MainActivity extends AppCompatActivity {

    private List<Fruit> list;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        //LinearLayoutManager manager = new LinearLayoutManager(this);
        //manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        FruitAdapter adapter = new FruitAdapter(list);
        recyclerView.setAdapter(adapter);
    }

    private void initView() {
        list = new ArrayList<>();
        for (int i = 0; i < 23; i++) {
            Fruit apple = new Fruit(getRandomlengthName("apple"), R.drawable.apple);
            list.add(apple);
//            Fruit banana = new Fruit("banana", R.drawable.banana);
//            list.add(banana);
//            Fruit cherry = new Fruit("cherry", R.drawable.cherry);
//            list.add(cherry);
//            Fruit grape = new Fruit("grape", R.drawable.grape);
//            list.add(grape);
//            Fruit orange = new Fruit("orange", R.drawable.orange);
//            list.add(orange);
//            Fruit pineapple = new Fruit("pineapple", R.drawable.pineapple);
//            list.add(pineapple);
//            Fruit pear = new Fruit("pear", R.drawable.pear);
//            list.add(pear);
//            Fruit strawberry = new Fruit("strawberry", R.drawable.strawberry);
//            list.add(strawberry);
//            Fruit watermelon = new Fruit("watermelon", R.drawable.watermelon);
//            list.add(watermelon);
        }

    }

    private String getRandomlengthName(String name) {
        Random random = new Random();
        int length = random.nextInt(20) + 1;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
                builder.append(name);
        }
        return builder.toString();
    }
}
