package com.marliao.trainticketenquiry.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.marliao.trainticketenquiry.R;
import com.marliao.trainticketenquiry.engine.MyApplication;
import com.marliao.trainticketenquiry.vo.Prices;
import com.marliao.trainticketenquiry.vo.TrainInfo;
import com.marliao.trainticketenquiry.vo.TrainInfoMore;

public class TrainDetailsActivity extends AppCompatActivity {

    private ImageView ivBack;
    private TextView tvTime;
    private ImageView ivSequence;
    private TextView tvStartTime;
    private TextView tvStartStation;
    private TextView tvTrainNum;
    private TextView tvDuration;
    private TextView tvEndTime;
    private TextView tvEdnStation;
    private ListView lvSeatInfo;
    private TrainInfoMore trainInfoMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_details);
        trainInfoMore = MyApplication.getTrainInfoMore();
        initUI();
        initAdapter();
    }

    private void initAdapter() {
        MyAdapter myAdapter = new MyAdapter(trainInfoMore);
        lvSeatInfo.setAdapter(myAdapter);
    }

    private void initUI() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TrainDetailsActivity.this, TrainInforListActivity.class));
                finish();
            }
        });

        tvTime = (TextView) findViewById(R.id.tv_time);
        tvTime.setText(MyApplication.getTime());

        ivSequence = (ImageView) findViewById(R.id.iv_sequence);
        ivSequence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApplication.showToast("功能未开放，敬请期待！");
            }
        });

        tvStartTime = (TextView) findViewById(R.id.tv_start_time);
        tvStartTime.setText(trainInfoMore.getDepartDepartTime());

        tvStartStation = (TextView) findViewById(R.id.tv_start_station);
        tvStartStation.setText(trainInfoMore.getDestStationName());

        tvTrainNum = (TextView) findViewById(R.id.tv_train_num);
        tvTrainNum.setText(trainInfoMore.getTrainNum());

        tvDuration = (TextView) findViewById(R.id.tv_duration);
        tvDuration.setText(trainInfoMore.getDurationStr());

        tvEndTime = (TextView) findViewById(R.id.tv_end_time);
        tvEndTime.setText(trainInfoMore.getDestArriveTime());

        tvEdnStation = (TextView) findViewById(R.id.tv_edn_station);
        tvEdnStation.setText(trainInfoMore.getDestStationName());

        lvSeatInfo = (ListView) findViewById(R.id.lv_seat_info);
    }

    public class MyAdapter extends BaseAdapter {

        private TrainInfoMore trainInfoMore;

        public MyAdapter(TrainInfoMore trainInfoMore) {
            this.trainInfoMore = trainInfoMore;
        }

        @Override
        public int getCount() {
            return trainInfoMore.getPrices().size();
        }

        @Override
        public Prices getItem(int position) {
            return trainInfoMore.getPrices().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = new ViewHolder();
            if (convertView == null) {
                convertView = View.inflate(TrainDetailsActivity.this, R.layout.train_details_item, null);
                viewHolder.tvSeat = (TextView) convertView.findViewById(R.id.tv_seat);
                viewHolder.tvIsHaveSeat = (TextView) convertView.findViewById(R.id.tv_is_have_seat);
                viewHolder.tvPrice = (TextView) convertView.findViewById(R.id.tv_price);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.tvSeat.setText(getItem(position).getSeatName());
            if (getItem(position).getLeftNumber() == 99) {
                viewHolder.tvIsHaveSeat.setText("票量充足");
            } else {
                viewHolder.tvIsHaveSeat.setText("预估放票较少");
            }
            viewHolder.tvPrice.setText(getItem(position).getPrice()+"");
            return convertView;
        }
    }

    class ViewHolder {
        TextView tvSeat;
        TextView tvIsHaveSeat;
        TextView tvPrice;
    }

}
