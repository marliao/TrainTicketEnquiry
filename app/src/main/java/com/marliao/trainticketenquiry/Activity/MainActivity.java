package com.marliao.trainticketenquiry.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
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
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private static final int HAVEDATA = 100;
    private static final int NODATA = 101;
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
            switch (msg.what){
                case HAVEDATA:
                    startActivity(new Intent(MainActivity.this, TrainInforListActivity.class));
                    break;
                case NODATA:
                    showDialogToTellUser();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private void showDialogToTellUser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("小贴士");
        builder.setMessage("您要购买的当日车票已售完，请重新选择日期，谢谢！");
        builder.setPositiveButton("好的", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //
            }
        });
        builder.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
    }

    private void initData() {
        final String startTrainStation = etStartTrainStation.getText().toString().trim();
        final String endTrainStation = etEndTrainStation.getText().toString().trim();
        String date = tvStartDate.getText().toString().trim();
        if ((startTrainStation != null || endTrainStation != null) &&
                (!TextUtils.isEmpty(startTrainStation) || !TextUtils.isEmpty(endTrainStation))) {
            String regular = "[\\u4e00-\\u9fa5]{2,10}";
            Pattern pattern = Pattern.compile(regular);
            if (pattern.matcher(startTrainStation).matches() && pattern.matcher(endTrainStation).matches()) {
                String startStation = SearchStation.getInstence(this).getStationcoe(startTrainStation);
                String endStation = SearchStation.getInstence(this).getStationcoe(endTrainStation);
                if (startStation != null && endStation != null) {
                    getDataFromHttp(startStation, startTrainStation, endStation, endTrainStation, date);
                }else {
                    MyApplication.showToast("您查询的城市不存在，请输入合法的城市名！");
                }
            } else {
                MyApplication.showToast("请输入合法的城市名称！");
            }
        } else {
            MyApplication.showToast("查询信息不完整，请完善信息！");
        }

    }

    private void getDataFromHttp(final String startStation, final String startTrainStation, final String endStation, final String endTrainStation, final String date) {
        MyApplication.setTime(date);
        MyApplication.setStartEndStation(startTrainStation + "—" + endTrainStation);
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
                    Log.i("+++++++++++++",httpResult);
                    Data data = ResolveJson.resolveTicketInfo(httpResult);
                    if (data != null) {
                        MyApplication.setData(data);
                        Message msg = Message.obtain();
                        msg.what=HAVEDATA;
                        mHandler.sendMessage(msg);
                    }else {
                        Message msg = Message.obtain();
                        msg.what=NODATA;
                        mHandler.sendMessage(msg);
                    }
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
        tvStartDate.setText(mYear + "-" + (mMonth + 1) + "-" + mDay);
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
                                days = new StringBuffer().append(mYear).append("-").append("0").append(mMonth + 1).append("-").append("0").append(mDay).toString();
                            } else {
                                days = new StringBuffer().append(mYear).append("-").append("0").append(mMonth + 1).append("-").append(mDay).toString();
                            }
                        } else {
                            if (mDay < 10) {
                                days = new StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append("0").append(mDay).toString();
                            } else {
                                days = new StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay).toString();
                            }
                        }
                        tvStartDate.setText(days);
                    }
                }, mYear, mMonth, mDay).show();
            }
        });
    }
}
