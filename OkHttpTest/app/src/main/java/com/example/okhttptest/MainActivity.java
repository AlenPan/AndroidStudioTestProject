package com.example.okhttptest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.view.View.X;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    TextView responseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button sendRequest = (Button) findViewById(R.id.send_request);
        responseText = (TextView) findViewById(R.id.response_text);
        sendRequest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.send_request) {
            sendRequestWithHttpURLConnection();
        }
    }

    private void sendRequestWithHttpURLConnection() {
        // 开启线程来发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                sendGETRequest();
                //sendPOSTRequest();
            }
        }).start();
    }

    private void sendGETRequest() {
        // 获取xml数据
//        String address = "http://192.168.1.103/get_data.xml";
        // 获取json数据
        String address = "http://192.168.1.103/get_data.json";

        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String responseData = response.body().string();
                    showResponse(responseData);

                    // 用Pull解析XML数据
                    //parseXMLWithPull(responseData);
                    // 用Sax解析XML数据
                    //parseXMLWithSax(responseData);

                    // 用JSONObject解析Json数据
                    //parseJSONWithJSONObject(responseData);
                    // 用Gson解析Json数据
                    parseJSONWithGSON(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void parseJSONWithGSON(String responseData) {
        Gson gson = new Gson();
        List<App> apps = gson.fromJson(responseData, new TypeToken<List<App>>() {
        }.getType());
        for (App app : apps) {
            String id = app.getId();
            String name = app.getName();
            String version = app.getVersion();

            Log.d(TAG, "parseJSONWithGSON: id = " + id);
            Log.d(TAG, "parseJSONWithGSON: name = " + name);
            Log.d(TAG, "parseJSONWithGSON: version = " + version);
        }

    }

    private void parseJSONWithJSONObject(String responseData) {
        try {
            JSONArray jsonArray = new JSONArray(responseData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String name = jsonObject.getString("name");
                String version = jsonObject.getString("version");
                Log.d(TAG, "parseJSONWithJSONObject: id = " + id);
                Log.d(TAG, "parseJSONWithJSONObject: name = " + name);
                Log.d(TAG, "parseJSONWithJSONObject: version = " + version);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void parseXMLWithSax(String responseData) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            XMLReader xmlReader = factory.newSAXParser().getXMLReader();
            xmlReader.setContentHandler(new ContentHandler());
            xmlReader.parse(new InputSource(new StringReader(responseData)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseXMLWithPull(String responseData) {
        try {
            // 获取XmlPullParseFactory实例
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            // 获取XmlPullParse对象
            XmlPullParser xmlPullParser = factory.newPullParser();
            // 调用setInput()方法将服务器返回的xml数据设置进去解析
            xmlPullParser.setInput(new StringReader(responseData));
            // 通过getEvenType获取当前的解析事件
            int eventType = xmlPullParser.getEventType();
            String id = "";
            String name = "";
            String version = "";
            while (eventType != XmlPullParser.END_DOCUMENT) {
                // 解析数据
                String nodeName = xmlPullParser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("id".equals(nodeName)) {
                            id = xmlPullParser.nextText();
                        } else if ("name".equals(nodeName)) {
                            name = xmlPullParser.nextText();
                        } else if ("version".equals(nodeName)) {
                            version = xmlPullParser.nextText();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if ("app".equals(nodeName)) {
                            Log.d(TAG, "parseXMLWithPull: id is = " + id);
                            Log.d(TAG, "parseXMLWithPull: name is = " + name);
                            Log.d(TAG, "parseXMLWithPull: version is = " + version);
                        }
                        break;
                    default:
                        break;
                }

                // 如果当前解析事件不等于XmlPullParser.END_DOCUMENT，
                // 说明解析工作还没有完成，调用next()方法获取下一个解析事件
                eventType = xmlPullParser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void sendPOSTRequest() {
        // 获取OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        // 获取RequestBody对象
        RequestBody requestBody = new FormBody.Builder()
                .add("username", "admin")
                .add("passwork", "123456")
                .build();
        // 获取Request对象
        Request request = new Request.Builder()
                .url("http://www.bilibili.com")
                .post(requestBody)
                .build();
        try {
            // 调用OKHttpClient的newCall()方法创建一个Call对象，并调用execute()方法来发送请求并获取服务器返回的数据
            Response response = client.newCall(request).execute();
            String responseData = response.body().string();
            showResponse(responseData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showResponse(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                responseText.setText(response);
            }
        });
    }
}
