package com.marliao.trainticketenquiry.engine;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.marliao.trainticketenquiry.vo.Data;
import com.marliao.trainticketenquiry.vo.TrainInfoMore;

public class MyApplication extends Application {

    public static int position=0;

    private static Data data;
    private static String time;
    private static String startEndStation;
    private static String startStation;
    private static String endStation;
    private static String StartStationCode;
    private static String endStationCode;

    public static String getStartStationCode() {
        return StartStationCode;
    }

    public static void setStartStationCode(String startStationCode) {
        StartStationCode = startStationCode;
    }

    public static String getEndStationCode() {
        return endStationCode;
    }

    public static void setEndStationCode(String endStationCode) {
        MyApplication.endStationCode = endStationCode;
    }


    public static String getStartStation() {
        return startStation;
    }

    public static void setStartStation(String startStation) {
        MyApplication.startStation = startStation;
    }

    public static String getEndStation() {
        return endStation;
    }

    public static void setEndStation(String endStation) {
        MyApplication.endStation = endStation;
    }

    public static String getStartEndStation() {
        return startEndStation;
    }

    public static void setStartEndStation(String startEndStation) {
        MyApplication.startEndStation = startEndStation;
    }

    public static TrainInfoMore getTrainInfoMore() {
        return trainInfoMore;
    }

    public static void setTrainInfoMore(TrainInfoMore trainInfoMore) {
        MyApplication.trainInfoMore = trainInfoMore;
    }

    private static TrainInfoMore trainInfoMore;

    public static String getTime() {
        return time;
    }

    public static void setTime(String time) {
        MyApplication.time = time;
    }

    public static Data getData() {
        return data;
    }

    public static void setData(Data data) {
        MyApplication.data = data;
    }

    public static Context context;
    public static Toast toast;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
    }

    public static void showToast(String test) {
        toast.setText(test);
        toast.show();
    }

    public static Context getContext() {
        return context;
    }
}
