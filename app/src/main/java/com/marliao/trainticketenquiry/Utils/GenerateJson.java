package com.marliao.trainticketenquiry.Utils;

import java.net.URLEncoder;

public class GenerateJson {
    public static String getTrainTicket(String date, String StartCityCode, String StartCityName,
                                        String EndCityCode, String EndCityName) {
        String getTicket = "http://huoche.tuniu.com/yii.php?r=train/trainTicket/getTickets&primary%5BdepartureDate%5D=" + date
                + "&primary%5BdepartureCityCode%5D=" + StartCityCode + "&primary%5BdepartureCityName%5D=" + URLEncoder.encode(StartCityName)
                + "&primary%5BarrivalCityCode%5D=" + EndCityCode + "&primary%5BarrivalCityName%5D=" + URLEncoder.encode(EndCityName) + "&start=0&limit=0";
        return getTicket;
    }
}
