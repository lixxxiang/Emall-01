package com.example.emall_ec.main.me.collect;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.emall_core.util.view.GlideRoundTransform;
import com.example.emall_core.util.view.RoundImageView;
import com.example.emall_core.util.view.SquareImageView;
import com.example.emall_ec.R;
import com.example.emall_ec.main.index.dailypic.data.HomePageBean;
import com.example.emall_ec.main.me.collect.data.MyCollectionBean;

import java.util.List;

public class MyCollectionListViewAdapter extends BaseAdapter {
    private static final int TYPE_PIC_1 = 0;
    private static final int TYPE_PIC_2 = 1;
    private static final int TYPE_VIDEO = 2;

    private Context context;
    private List<MyCollectionBean.DataBean.CollectionsBean> homePageData;

    public MyCollectionListViewAdapter() {
        super();
    }

    public MyCollectionListViewAdapter(Context context, List<MyCollectionBean.DataBean.CollectionsBean> homePageData) {
        super();
        this.context = context;
        this.homePageData = homePageData;
    }

    @Override
    public int getCount() {
        return homePageData.size();
    }

    @Override
    public Object getItem(int position) {
        return homePageData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {

        if (homePageData.get(position).getStyle() != null) {
            if (homePageData.get(position).getType().equals("1") && homePageData.get(position).getStyle().equals("1")) {
                return TYPE_PIC_1;
            } else if (homePageData.get(position).getType().equals("1") && homePageData.get(position).getStyle().equals("2")) {
                return TYPE_PIC_2;
            }
        } else
            return TYPE_VIDEO;
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyCollectionListViewAdapter.Pic1Holder pic1Holder;
        MyCollectionListViewAdapter.Pic2Holder pic2Holder;
        MyCollectionListViewAdapter.VideoHolder videoHolder;
        RequestOptions myOptions = new RequestOptions()
                .centerCrop()
                .transform(new GlideRoundTransform(context,5));
        switch (getItemViewType(position)) {
            case TYPE_PIC_1:
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_pic_style_1, null);
                    pic1Holder = new MyCollectionListViewAdapter.Pic1Holder();
                    pic1Holder.pic1Name = convertView.findViewById(R.id.item_pic_1_detail_tv);
                    pic1Holder.pic1Date = convertView.findViewById(R.id.item_pic_1_date_tv);
                    pic1Holder.pic1Pic = convertView.findViewById(R.id.item_pic_1_riv);
                    convertView.setTag(pic1Holder);
                } else {
                    pic1Holder = (MyCollectionListViewAdapter.Pic1Holder) convertView.getTag();
                }

                Glide.with(context)
                        .load(homePageData.get(position).getThumbnail1Path())
                        .into(pic1Holder.pic1Pic);
                pic1Holder.pic1Name.setText(homePageData.get(position).getContentName());
                pic1Holder.pic1Date.setText("每日一图 · " + homePageData.get(position).getContentDate()
                        .substring(homePageData.get(position).getContentDate().length() - 5, homePageData.get(position).getContentDate().length()));
                break;
            case TYPE_PIC_2:
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_pic_style_2, null);
                    pic2Holder = new MyCollectionListViewAdapter.Pic2Holder();
                    pic2Holder.pic2Name = convertView.findViewById(R.id.item_pic_2_detail_tv);
                    pic2Holder.pic2Date = convertView.findViewById(R.id.item_pic_2_date_tv);
                    pic2Holder.pic2Pic1 = convertView.findViewById(R.id.item_pic_2_pic1_sriv);
                    pic2Holder.pic2Pic2 = convertView.findViewById(R.id.item_pic_2_pic2_sriv);
                    pic2Holder.pic2Pic3 = convertView.findViewById(R.id.item_pic_2_pic3_sriv);
                    convertView.setTag(pic2Holder);
                } else {
                    pic2Holder = (MyCollectionListViewAdapter.Pic2Holder) convertView.getTag();
                }
                pic2Holder.pic2Name.setText(homePageData.get(position).getContentName());
                pic2Holder.pic2Date.setText("每日一图 · " + homePageData.get(position).getContentDate()
                        .substring(homePageData.get(position).getContentDate().length() - 5, homePageData.get(position).getContentDate().length()));

                Glide.with(context)
                        .load(homePageData.get(position).getThumbnail1Path())
                        .apply(myOptions)
                        .into(pic2Holder.pic2Pic1);


                if (homePageData.get(position).getThumbnail2Path().length() == 0) {
                    pic2Holder.pic2Pic2.setVisibility(View.INVISIBLE);
                } else {
                    Glide.with(context)
                            .load(homePageData.get(position).getThumbnail2Path())
                            .apply(myOptions)
                            .into(pic2Holder.pic2Pic2);
                }
                if (homePageData.get(position).getThumbnail3Path().length() == 0) {
                    pic2Holder.pic2Pic3.setVisibility(View.INVISIBLE);
                } else {
                    Glide.with(context)
                            .load(homePageData.get(position).getThumbnail3Path())
                            .apply(myOptions)
                            .into(pic2Holder.pic2Pic3);
                }
                break;
            case TYPE_VIDEO:
                if (convertView == null) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_video, null);
                    videoHolder = new MyCollectionListViewAdapter.VideoHolder();
                    videoHolder.videoName = convertView.findViewById(R.id.item_video_title_tv);
                    videoHolder.videoPic = convertView.findViewById(R.id.item_video_riv);
                    videoHolder.playTimes = convertView.findViewById(R.id.item_video_play_times_tv);
                    videoHolder.duration = convertView.findViewById(R.id.item_video_duration_tv);
                    videoHolder.date = convertView.findViewById(R.id.item_video_date_tv);
                    convertView.setTag(videoHolder);
                } else {
                    videoHolder = (MyCollectionListViewAdapter.VideoHolder) convertView.getTag();
                }
                videoHolder.videoName.setText(homePageData.get(position).getContentName());
                Glide.with(context)
                        .load(homePageData.get(position).getThumbnail1Path())
                        .into(videoHolder.videoPic);
                videoHolder.playTimes.setText(homePageData.get(position).getPlayCount() + "次播放");
                videoHolder.duration.setText(homePageData.get(position).getDuration());
                videoHolder.date.setText("脉动地球 · " + homePageData.get(position).getContentDate()
                        .substring(homePageData.get(position).getContentDate().length() - 5, homePageData.get(position).getContentDate().length()));

        }
        return convertView;
    }

    class Pic1Holder {
        RoundImageView pic1Pic;
        AppCompatTextView pic1Name;
        AppCompatTextView pic1Date;
    }

    class Pic2Holder {
        SquareImageView pic2Pic1;
        SquareImageView pic2Pic2;
        SquareImageView pic2Pic3;
        AppCompatTextView pic2Name;
        AppCompatTextView pic2Date;
    }

    class VideoHolder {
        AppCompatTextView videoName;
        RoundImageView videoPic;
        AppCompatTextView playTimes;
        AppCompatTextView duration;
        AppCompatTextView date;
    }
}
