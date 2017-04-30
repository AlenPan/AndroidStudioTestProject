package com.example.fragmenttest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button leftFragmentBtn;
//    TextView rightFragmentText;
    FrameLayout rightLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        leftFragmentBtn = (Button) findViewById(R.id.left_fragment_btn);
//        rightFragmentText = (TextView) findViewById(R.id.right_fragment_text);
//        rightLayout = (FrameLayout) findViewById(R.id.right_layout);
//        replaceFragment(new RightFragment());

        leftFragmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                rightFragmentText.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
//                replaceFragment(new AnotherRightFragment());
            }
        });
    }

//    private void replaceFragment(Fragment fragment) {
//        FragmentManager manager = getSupportFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();
//        transaction.addToBackStack(null);
//        transaction.replace(R.id.right_layout, fragment);
//        transaction.commit();
//    }
}
