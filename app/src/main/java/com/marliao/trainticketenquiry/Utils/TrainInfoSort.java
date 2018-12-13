package com.marliao.trainticketenquiry.Utils;

import android.util.Log;

import com.marliao.trainticketenquiry.engine.MyApplication;
import com.marliao.trainticketenquiry.vo.TrainInfoMore;

import java.io.CharArrayReader;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class TrainInfoSort {

    public static Date stringToDate(String dateString) {
        ParsePosition position = new ParsePosition(0);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateValue = simpleDateFormat.parse(dateString, position);
        return dateValue;
    }

    /**
     * 最早出发
     *
     * @param mTrainInfoMoreList
     */
    public static void firstDeparture(List<TrainInfoMore> mTrainInfoMoreList) {
        for (int i = 0; i < mTrainInfoMoreList.size() - 1; i++) {
            for (int j = 0; j < mTrainInfoMoreList.size() - 1 - i; j++) {
                String time1 = MyApplication.getTime() + " " + mTrainInfoMoreList.get(j).getDepartDepartTime() + ":00";
                String time2 = MyApplication.getTime() + " " + mTrainInfoMoreList.get(j + 1).getDepartDepartTime() + ":00";
                Date date1 = stringToDate(time1);
                Date date2 = stringToDate(time2);
                if (date1.after(date2)) {
                    TrainInfoMore trainInfoMore = new TrainInfoMore();
                    trainInfoMore = mTrainInfoMoreList.get(j);
                    mTrainInfoMoreList.set(j, mTrainInfoMoreList.get(j + 1));
                    mTrainInfoMoreList.set(j + 1, trainInfoMore);
                }
            }
        }
    }

    /**
     * 最晚出发
     *
     * @param mTrainInfoMoreList
     */
    public static void departureAtTheLatest(List<TrainInfoMore> mTrainInfoMoreList) {
        for (int i = 0; i < mTrainInfoMoreList.size() - 1; i++) {
            for (int j = 0; j < mTrainInfoMoreList.size() - 1 - i; j++) {
                String time1 = MyApplication.getTime() + " " + mTrainInfoMoreList.get(j).getDepartDepartTime() + ":00";
                String time2 = MyApplication.getTime() + " " + mTrainInfoMoreList.get(j + 1).getDepartDepartTime() + ":00";
                Date date1 = stringToDate(time1);
                Date date2 = stringToDate(time2);
                if (date1.before(date2)) {
                    TrainInfoMore trainInfoMore = new TrainInfoMore();
                    trainInfoMore = mTrainInfoMoreList.get(j);
                    mTrainInfoMoreList.set(j, mTrainInfoMoreList.get(j + 1));
                    mTrainInfoMoreList.set(j + 1, trainInfoMore);
                }
            }
        }
    }

    /**
     * 耗时最长
     *
     * @param mTrainInfoMoreList
     */
    public static void longestTimeConsuming(List<TrainInfoMore> mTrainInfoMoreList) {
        for (int i = 0; i < mTrainInfoMoreList.size() - 1; i++) {
            for (int j = 0; j < mTrainInfoMoreList.size() - 1 - i; j++) {
                String time1 = MyApplication.getTime() + " " + mTrainInfoMoreList.get(j).getDepartDepartTime() + ":00";
                String time2 = MyApplication.getTime() + " " + mTrainInfoMoreList.get(j).getDestArriveTime() + ":00";
                long timeConsuming1 = stringToTimestamp(time1, time2);
                String time3 = MyApplication.getTime() + " " + mTrainInfoMoreList.get(j + 1).getDepartDepartTime() + ":00";
                String time4 = MyApplication.getTime() + " " + mTrainInfoMoreList.get(j + 1).getDestArriveTime() + ":00";
                long timeConsuming2 = stringToTimestamp(time3, time4);
                if (timeConsuming1 < timeConsuming2) {
                    TrainInfoMore trainInfoMore = new TrainInfoMore();
                    trainInfoMore = mTrainInfoMoreList.get(j);
                    mTrainInfoMoreList.set(j, mTrainInfoMoreList.get(j + 1));
                    mTrainInfoMoreList.set(j + 1, trainInfoMore);
                }
            }
        }
    }

    public static long stringToTimestamp(String date1, String date2) {
        long timeConsuming = 0;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date start = simpleDateFormat.parse(date1);
            Date end = simpleDateFormat.parse(date2);
            if (end.before(start)) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(end);
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                end = calendar.getTime();
            }

            timeConsuming = (end.getTime() - start.getTime()) / (1000 * 60);
            if (timeConsuming < 0) {
                timeConsuming = -timeConsuming;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeConsuming;
    }
}
