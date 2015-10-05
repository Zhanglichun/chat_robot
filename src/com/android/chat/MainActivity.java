package com.android.chat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.chat.adapter.ChatMsgAdapter;
import com.android.chat.bean.ChatMsg;
import com.android.chat.bean.ChatMsg.Type;
import com.android.chat.utils.HttpUtils;

public class MainActivity extends Activity {

	private ListView lv_main;
	private ChatMsgAdapter adapter;
	private List<ChatMsg> list;
	
	private EditText input;
	private Button sendBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		initView();
		initdata();
		
		initListener();
	}

	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			
			ChatMsg fromMsg = (ChatMsg) msg.obj;
			list.add(fromMsg);
			adapter.notifyDataSetChanged();
			lv_main.setSelection(adapter.getCount());
		};
	};
	
	private void initListener() {
		sendBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final String toMsgString = input.getText().toString();
				if (TextUtils.isEmpty(toMsgString)) {
					Toast.makeText(MainActivity.this, "发送消息不可为空", 0).show();
					return;
				}
				
				ChatMsg toChatMsg = new ChatMsg();
				toChatMsg.setDate(new Date());
				toChatMsg.setMsg(toMsgString);
				toChatMsg.setType(Type.OUTCOMING);				
				list.add(toChatMsg);
				toChatMsg.setName("我");
				adapter.notifyDataSetChanged();
				lv_main.setSelection(adapter.getCount());
				
				input.setText("");
				
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						ChatMsg fromMsg = HttpUtils.sendMsg(toMsgString);
						Message message = Message.obtain();
						message.obj = fromMsg;
						handler.sendMessage(message);

					}
				}).start();

			}
		});
		
	}

	private void initdata() {
		list = new ArrayList<ChatMsg>();
		list.add(new ChatMsg("你好，图图为您服务", Type.INCOMING, new Date()));
		
		adapter = new ChatMsgAdapter(MainActivity.this, list);
		
		lv_main.setAdapter(adapter);
		lv_main.setSelection(adapter.getCount());
	}

	private void initView() {
		// TODO Auto-generated method stub
		lv_main = (ListView) findViewById(R.id.lv_main);
		input = (EditText) findViewById(R.id.input);
		sendBtn = (Button) findViewById(R.id.send);
		
	}
}
