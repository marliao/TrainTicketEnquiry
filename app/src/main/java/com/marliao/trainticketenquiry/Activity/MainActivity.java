package com.marliao.trainticketenquiry.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.marliao.trainticketenquiry.R;
import com.marliao.trainticketenquiry.Utils.GenerateJson;
import com.marliao.trainticketenquiry.Utils.HttpUtil;
import com.marliao.trainticketenquiry.Utils.ResolveJson;
import com.marliao.trainticketenquiry.Utils.SearchStation;
import com.marliao.trainticketenquiry.engine.MyApplication;
import com.marliao.trainticketenquiry.vo.Data;

import org.json.JSONException;

import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {
    private EditText etStartTrainStation;
    private EditText etEndTrainStation;
    private ImageView ivStartSearch;
    private TextView tvStartDate;
    private int mYear;
    private int mMonth;
    private int mDay;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            startActivity(new Intent(MainActivity.this, TrainInforListActivity.class));
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    private void initData() {
        final String startTrainStation = etStartTrainStation.getText().toString().trim();
        final String endTrainStation = etEndTrainStation.getText().toString().trim();
        String date=tvStartDate.getText().toString().trim();
        if ((startTrainStation != null || endTrainStation != null) &&
                (!TextUtils.isEmpty(startTrainStation) || !TextUtils.isEmpty(endTrainStation))) {
            getDataFromHttp(startTrainStation, endTrainStation, date);
        } else {
            MyApplication.showToast("查询信息不完整，请完善信息！");
        }

    }

    private void getDataFromHttp(final String startTrainStation, final String endTrainStation, final String date) {
        final String startStation = SearchStation.getInstence(this).getStationcoe(startTrainStation);
        final String endStation = SearchStation.getInstence(this).getStationcoe(endTrainStation);
        MyApplication.setTime(date);
        MyApplication.setStartEndStation(startTrainStation+"—"+endTrainStation);
        MyApplication.setStartStation(startTrainStation);
        MyApplication.setEndStation(endTrainStation);
        MyApplication.setStartStationCode(startStation);
        MyApplication.setEndStationCode(endStation);
        new Thread() {
            @Override
            public void run() {
                try {
                    String path = GenerateJson.getTrainTicket(date, startStation, startTrainStation, endStation, endTrainStation);
                    String httpResult = HttpUtil.doGet(path);
                    Data data = ResolveJson.resolveTicketInfo(httpResult);
                    MyApplication.setData(data);
                    mHandler.sendEmptyMessage(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                super.run();
            }
        }.start();
    }

    private void initUI() {
        tvStartDate = (TextView) findViewById(R.id.tv_start_date);
        //实现日期选择器
        dateSelector();

        etStartTrainStation = (EditText) findViewById(R.id.et_start_train_station);
        etEndTrainStation = (EditText) findViewById(R.id.et_end_train_station);
        ivStartSearch = (ImageView) findViewById(R.id.iv_start_search);
        ivStartSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
    }

    private void dateSelector() {
        Calendar calendar = Calendar.getInstance();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);
        tvStartDate.setText(mYear +"-"+(mMonth +1)+"-"+ mDay);
        tvStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用日期选择器
                new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mYear = year;
                        mMonth = month;
                        mDay = dayOfMonth;
                        String days;
                        if (mMonth + 1 < 10) {
                            if (mDay < 10) {
                                days = new StringBuffer().append(mYear).append("-").append("0"). append(mMonth + 1).append("-").append("0").append(mDay).toString();
                            } else {
                                days = new StringBuffer().append(mYear).append("-").append("0"). append(mMonth + 1).append("-").append(mDay).toString();
                            }
                        } else {
                            if (mDay < 10) {
                                days = new StringBuffer().append(mYear).append("-"). append(mMonth + 1).append("-").append("0").append(mDay).toString();
                            } else {
                                days = new StringBuffer().append(mYear).append("-"). append(mMonth + 1).append("-").append(mDay).toString();
                            }
                        }
                        tvStartDate.setText(days);
                    }
                }, mYear, mMonth, mDay).show();
            }
        });
    }
}
