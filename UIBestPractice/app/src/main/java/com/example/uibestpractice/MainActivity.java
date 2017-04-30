package com.example.uibestpractice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import static android.os.Build.VERSION_CODES.M;

public class MainActivity extends AppCompatActivity {

    private List<Msg> mMsgList;
    private EditText inputText;
    private Button send;
    private RecyclerView recylerView;
    private MsgAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initMsg();

        inputText = (EditText) findViewById(R.id.input_text);
        send = (Button) findViewById(R.id.send);
        recylerView = (RecyclerView) findViewById(R.id.msg_recycler_view);
        adapter = new MsgAdapter(mMsgList);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recylerView.setLayoutManager(manager);
        recylerView.setAdapter(adapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = inputText.getText().toString();

                if (!TextUtils.isEmpty(content)) {
                    Msg msg = new Msg(content, Msg.TYPE_SEND);
                    mMsgList.add(msg);
                    // 当有新消息的时候，刷新RecyclerView中的显示
                    adapter.notifyItemChanged(mMsgList.size() - 1);

                    // 将RecylerView定位到最后一位
                    recylerView.scrollToPosition(mMsgList.size() - 1);
                    // 清空输入框的内容
                    inputText.setText("");
                }
            }
        });
    }

    private void initMsg() {
        mMsgList = new ArrayList<>();
        Msg msg1 = new Msg("Hello guy.", Msg.TYPE_RECEIVED);
        mMsgList.add(msg1);
        Msg msg2 = new Msg("Who are you?", Msg.TYPE_SEND);
        mMsgList.add(msg2);
        Msg msg3 = new Msg("I am Alen.We can be friends.", Msg.TYPE_RECEIVED);
        mMsgList.add(msg3);
    }
}
