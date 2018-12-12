package com.marliao.trainticketenquiry.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

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
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {
    private EditText etStartTrainStation;
    private EditText etEndTrainStation;
    private EditText etStartYear;
    private EditText etStartMonth;
    private EditText etStartDay;
    private ImageView ivStartSearch;
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
        String year = etStartYear.getText().toString().trim();
        String month = etStartMonth.getText().toString().trim();
        String day = etStartDay.getText().toString().trim();
        if ((startTrainStation != null || endTrainStation != null || year != null || month != null || day != null) &&
                (!TextUtils.isEmpty(startTrainStation) || !TextUtils.isEmpty(endTrainStation) || !TextUtils.isEmpty(year)
                        || !TextUtils.isEmpty(month) || !TextUtils.isEmpty(day))) {
            getDataFromHttp(startTrainStation, endTrainStation, year, month, day);
        } else {
            MyApplication.showToast("查询信息不完整，请完善信息！");
        }

    }

    private void getDataFromHttp(final String startTrainStation, final String endTrainStation, String year, String month, String day) {
        final String startStation = SearchStation.getInstence(this).getStationcoe(startTrainStation);
        final String endStation = SearchStation.getInstence(this).getStationcoe(endTrainStation);
        final String date = year + "-" + month + "-" + day;
        MyApplication.setTime(date);
        MyApplication.setStartEndStation(startTrainStation+"—"+endTrainStation);
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
        etStartTrainStation = (EditText) findViewById(R.id.et_start_train_station);
        etEndTrainStation = (EditText) findViewById(R.id.et_end_train_station);
        etStartYear = (EditText) findViewById(R.id.et_start_year);
        etStartMonth = (EditText) findViewById(R.id.et_start_month);
        etStartDay = (EditText) findViewById(R.id.et_start_day);
        ivStartSearch = (ImageView) findViewById(R.id.iv_start_search);
        ivStartSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
    }
}
