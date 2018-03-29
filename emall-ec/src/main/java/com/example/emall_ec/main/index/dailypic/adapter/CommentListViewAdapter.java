package com.example.emall_ec.main.index.dailypic.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.emall_ec.R;
import com.example.emall_ec.main.index.dailypic.data.GetArticleAttachBean;

/**
 * Created by lixiang on 2017/11/27.
 */

public class CommentListViewAdapter extends BaseAdapter {
    private Context context;
    private GetArticleAttachBean.DataBean content;

    public CommentListViewAdapter(Context context, GetArticleAttachBean.DataBean content) {
        super();
        this.context = context;
        this.content = content;
    }

    @Override
    public int getCount() {
        return content.getComments().size();
    }

    @Override
    public Object getItem(int position) {
        return content.getComments().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VideoHolder videoHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.comment_listview, null);
            videoHolder = new VideoHolder();
            videoHolder.userName = convertView.findViewById(R.id.user_name);
            videoHolder.commentTime = convertView.findViewById(R.id.comment_time);
            videoHolder.commentDetail = convertView.findViewById(R.id.comment_detail);
            convertView.setTag(videoHolder);
        } else {
            videoHolder = (VideoHolder) convertView.getTag();
        }
            videoHolder.userName.setText( content.getComments().get(position).getUserName());
            videoHolder.commentTime.setText( content.getComments().get(position).getCommentTime());
            videoHolder.commentDetail.setText( content.getComments().get(position).getContent());
        return convertView;
    }

    @Nullable
    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }

    class VideoHolder {
        TextView userName;
        TextView commentTime;
        TextView commentDetail;
    }
}
