package com.android.chat.adapter;

import java.net.ContentHandler;
import java.text.SimpleDateFormat;
import java.util.List;

import com.android.chat.R;
import com.android.chat.bean.ChatMsg;
import com.android.chat.bean.ChatMsg.Type;
import com.android.chat.constant.Constant;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ChatMsgAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<ChatMsg> list;
	
	
	public ChatMsgAdapter(Context context, List<ChatMsg> list){
		inflater = LayoutInflater.from(context);
		this.list = list;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressLint("SimpleDateFormat")
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		
		ChatMsg msg = list.get(arg0);
		
		ViewHolder holder = null;
		if (arg1 == null) {
			
			//设置不同的布局
			if (getItemViewType(arg0) == 0) {
				arg1 = inflater.inflate(R.layout.item_from_msg, arg2, false);
				holder = new ViewHolder();
				holder.mData = (TextView) arg1.findViewById(R.id.from_time);
				holder.mMsg = (TextView) arg1.findViewById(R.id.content);
				
			}else{
				arg1 = inflater.inflate(R.layout.item_to_msg, arg2, false);
				holder = new ViewHolder();
				holder.mData = (TextView) arg1.findViewById(R.id.to_time);
				holder.mMsg = (TextView) arg1.findViewById(R.id.to_msg);
			}
			arg1.setTag(holder);
		}else{
			holder = (ViewHolder) arg1.getTag();
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		holder.mData.setText(format.format(msg.getDate()));
		holder.mMsg.setText(msg.getMsg());
		
		return arg1;
	}
	
	private final class ViewHolder{
		
		TextView mData;
		TextView mMsg;
	}

	/**
	 * 两种布局
	 */
	@Override
	public int getItemViewType(int position) {
		
		ChatMsg msg = list.get(position);
		if (msg.getType() == Type.INCOMING) {
			return 0;
		}else{
			return 1;
		}
	}
	
	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}

}
