package com.marliao.trainticketenquiry.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.marliao.trainticketenquiry.R;
import com.marliao.trainticketenquiry.Utils.TrainInfoSort;
import com.marliao.trainticketenquiry.engine.MyApplication;
import com.marliao.trainticketenquiry.vo.Prices;
import com.marliao.trainticketenquiry.vo.TrainInfoMore;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TrainInforListActivity extends AppCompatActivity {

    private ImageView ivBack;
    private TextView tvStartEndStation;
    private ImageView ivSequence;
    private TextView tvStartTime;
    private ListView lvTrainInfo;
    private List<TrainInfoMore> mTrainInfoMoreList;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_infor_list);
        initUI();
        initAdapter();
    }

    private void initAdapter() {
        mTrainInfoMoreList = MyApplication.getData().getTrainInfoMoreList();
        myAdapter = new MyAdapter(mTrainInfoMoreList);
        lvTrainInfo.setAdapter(myAdapter);
    }

    private void initUI() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TrainInforListActivity.this, MainActivity.class));
                finish();
            }
        });

        tvStartEndStation = (TextView) findViewById(R.id.tv_start_end_station);
        tvStartEndStation.setText(MyApplication.getStartEndStation());

        ivSequence = (ImageView) findViewById(R.id.iv_sequence);
        ivSequence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioBoxSelectionSortingMethod();
            }
        });

        tvStartTime = (TextView) findViewById(R.id.tv_start_time);
        tvStartTime.setText(MyApplication.getTime());

        lvTrainInfo = (ListView) findViewById(R.id.lv_train_info);
        lvTrainInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //获取点击条目的数据
                List<TrainInfoMore> trainInfoMoreList = MyApplication.getData().getTrainInfoMoreList();
                TrainInfoMore trainInfoMore = trainInfoMoreList.get(position);
                MyApplication.setTrainInfoMore(trainInfoMore);
                //跳转到列车详情页面
                startActivity(new Intent(TrainInforListActivity.this, TrainDetailsActivity.class));
            }
        });
    }

    private void radioBoxSelectionSortingMethod() {
        final String[] sortMethod = new String[]{"最早出发", "最晚出发", "耗时最长"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("筛选方式");
        builder.setSingleChoiceItems(sortMethod, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String sort = sortMethod[which];
                sequence(sort);
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void sequence(String sort) {
        if (sort.equals("最早出发")) {
            TrainInfoSort.firstDeparture(mTrainInfoMoreList);
        } else if (sort.equals("最晚出发")) {
            TrainInfoSort.departureAtTheLatest(mTrainInfoMoreList);
        } else if (sort.equals("耗时最长")) {
            TrainInfoSort.longestTimeConsuming(mTrainInfoMoreList);
        }
        if (myAdapter != null) {
            myAdapter.notifyDataSetChanged();
        }
    }

    public class MyAdapter extends BaseAdapter {

        private List<TrainInfoMore> trainInfoMoreList;

        public MyAdapter(List<TrainInfoMore> trainInfoMoreList) {
            this.trainInfoMoreList = trainInfoMoreList;
        }

        @Override
        public int getCount() {
            return MyApplication.getData().getCount();
        }

        @Override
        public TrainInfoMore getItem(int position) {
            return trainInfoMoreList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = new ViewHolder();
            if (convertView == null) {
                convertView = View.inflate(TrainInforListActivity.this, R.layout.train_infor_item, null);
                viewHolder.tvStartTime = (TextView) convertView.findViewById(R.id.tv_start_time);
                viewHolder.tvStartStation = (TextView) convertView.findViewById(R.id.tv_start_station);
                viewHolder.tvTrainNum = (TextView) convertView.findViewById(R.id.tv_train_num);
                viewHolder.tvDuration = (TextView) convertView.findViewById(R.id.tv_duration);
                viewHolder.tvEndTime = (TextView) convertView.findViewById(R.id.tv_end_time);
                viewHolder.tvEdnStation = (TextView) convertView.findViewById(R.id.tv_edn_station);
                viewHolder.tvPrice = (TextView) convertView.findViewById(R.id.tv_price);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tvStartTime.setText(getItem(position).getDepartDepartTime());
            viewHolder.tvStartStation.setText(getItem(position).getDepartStationName());
            viewHolder.tvEndTime.setText(getItem(position).getDestArriveTime());
            viewHolder.tvEdnStation.setText(getItem(position).getDestStationName());
            viewHolder.tvTrainNum.setText(getItem(position).getTrainNum());
            viewHolder.tvDuration.setText(getItem(position).getDuration() + "");
            //价格排序，取最低价
            List<Prices> pricesList = getItem(position).getPrices();
            double lowPrice = 0;
            for (int i = 0; i < pricesList.size() - 1; i++) {
                int compare = Double.compare(pricesList.get(i).getPrice(), pricesList.get(i + 1).getPrice());
                if (compare <=0) {
                    lowPrice = pricesList.get(i).getPrice();
                }
            }
            viewHolder.tvPrice.setText("￥" + lowPrice);
            return convertView;
        }
    }

    class ViewHolder {
        TextView tvStartTime;
        TextView tvStartStation;
        TextView tvTrainNum;
        TextView tvDuration;
        TextView tvEndTime;
        TextView tvEdnStation;
        TextView tvPrice;
    }

}
