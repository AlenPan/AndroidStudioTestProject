package com.example.listviewtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

//    private String[] data = {"Apple", "Banana", "Orange", "Watermelon", "Pear", "Grape",
//            "Pineapple", "Strawberry", "Cherry", "Mango", "Apple", "Banana", "Orange",
//            "Watermelon", "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango"};
    private List<Fruit> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initView();

        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        FruitAdapter adapter = new FruitAdapter(this, R.layout.fruit_item, list);

        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fruit fruit = list.get(position);
                Toast.makeText(MainActivity.this, fruit.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        list = new ArrayList<>();
        for (int i = 0; i < 23; i++) {
            Fruit apple = new Fruit("apple", R.drawable.apple);
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
}
