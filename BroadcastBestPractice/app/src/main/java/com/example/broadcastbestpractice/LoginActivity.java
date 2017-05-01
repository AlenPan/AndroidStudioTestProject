package com.example.broadcastbestpractice;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private EditText accoutEdit;
    private EditText passwordEdit;
    private Button login;
    private CheckBox remeberPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        accoutEdit = (EditText) findViewById(R.id.accout);
        passwordEdit = (EditText) findViewById(R.id.password);
        remeberPass = (CheckBox) findViewById(R.id.remeber_pass);
        login = (Button) findViewById(R.id.login);

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemember = pref.getBoolean("remember_password", false);

        if (isRemember) {
            // 将账号和密码都设置到文本框中
            String account = pref.getString("account", "");
            String password = pref.getString("password", "");
            accoutEdit.setText(account);
            passwordEdit.setText(password);
            remeberPass.setChecked(true);
        }

        login.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String accout = accoutEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                Boolean isRemember = remeberPass.isChecked();

                if (accout.equals("admin") && password.equals("123456")) {
                    editor = pref.edit();
                    if (isRemember) {
                        editor.putBoolean("remember_password", isRemember);
                        editor.putString("account", accout);
                        editor.putString("password", password);
                    } else {
                        editor.clear();
                    }
                    editor.apply();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "account or password is invalid", Toast.LENGTH_SHORT).show();
                } 
            }
        });
    }


}

