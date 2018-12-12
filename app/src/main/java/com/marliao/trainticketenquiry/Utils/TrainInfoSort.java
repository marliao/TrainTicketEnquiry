package com.marliao.trainticketenquiry.Utils;

import android.util.Log;

import com.marliao.trainticketenquiry.engine.MyApplication;
import com.marliao.trainticketenquiry.vo.TrainInfoMore;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
                Date date1 = stringToDate(MyApplication.getTime()+" "+mTrainInfoMoreList.get(j).getDepartDepartTime());
                Date date2 = stringToDate(MyApplication.getTime()+" "+mTrainInfoMoreList.get(j+1).getDepartDepartTime());
                Log.i("+++++++++",date1+"---------"+date2);
            }
        }
    }

    /**
     * 最晚出发
     *
     * @param mTrainInfoMoreList
     */
    public static void departureAtTheLatest(List<TrainInfoMore> mTrainInfoMoreList) {
        //
    }

    /**
     * 耗时最长
     *
     * @param mTrainInfoMoreList
     */
    public static void longestTimeConsuming(List<TrainInfoMore> mTrainInfoMoreList) {
        //
    }
}
