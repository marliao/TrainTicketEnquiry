package com.marliao.trainticketenquiry.Utils;

import android.util.Log;

import com.marliao.trainticketenquiry.vo.AllTrainType;
import com.marliao.trainticketenquiry.vo.AllTrainTypeMore;
import com.marliao.trainticketenquiry.vo.Data;
import com.marliao.trainticketenquiry.vo.Prices;
import com.marliao.trainticketenquiry.vo.TrainInfoMore;
import com.marliao.trainticketenquiry.vo.TrainTypeDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ResolveJson {

    public static Data resolveTicketInfo(String jsonStr) throws JSONException {

        Data data = new Data();

        JSONObject dataJson = new JSONObject(jsonStr).getJSONObject("data");

        if (dataJson.getInt("count") == 0) {
            data = null;
            return data;
        }

        data.setCount(dataJson.getInt("count"));

        JSONArray trainTypeDetails = dataJson.getJSONArray("trainTypeDetails");
        List<TrainTypeDetails> trainTypeDetailsList = new ArrayList<>();
        for (int i = 0; i < trainTypeDetails.length(); i++) {
            TrainTypeDetails trainTypeDetailsBean = new TrainTypeDetails();
            trainTypeDetailsBean.setTrainType(trainTypeDetails.getJSONObject(i).getInt("trainType"));
            trainTypeDetailsBean.setNumber(trainTypeDetails.getJSONObject(i).getInt("number"));
            trainTypeDetailsBean.setTrainTypeName(trainTypeDetails.getJSONObject(i).getString("trainTypeName"));
            trainTypeDetailsList.add(trainTypeDetailsBean);
        }
        data.setTrainTypeDetails(trainTypeDetailsList);

        JSONArray list = dataJson.getJSONArray("list");
        List<TrainInfoMore> trainInfoMoreList = new ArrayList<>();
        for (int i = 0; i < list.length(); i++) {
            TrainInfoMore trainInfoMore = new TrainInfoMore();
            trainInfoMore.setTrainId(list.getJSONObject(i).getInt("trainId"));
            trainInfoMore.setTrainNum(list.getJSONObject(i).getString("trainNum"));
            trainInfoMore.setTrainType(list.getJSONObject(i).getInt("trainType"));
            trainInfoMore.setTrainTypeName(list.getJSONObject(i).getString("trainTypeName"));
            trainInfoMore.setDepartStationName(list.getJSONObject(i).getString("departStationName"));
            trainInfoMore.setDestStationName(list.getJSONObject(i).getString("destStationName"));
            trainInfoMore.setDepartDepartTime(list.getJSONObject(i).getString("departDepartTime"));
            trainInfoMore.setDestArriveTime(list.getJSONObject(i).getString("destArriveTime"));
            trainInfoMore.setDuration(list.getJSONObject(i).getInt("duration"));

            List<Prices> pricesList = new ArrayList<>();
            JSONArray pricesJson = list.getJSONObject(i).getJSONArray("prices");
            for (int j = 0; j < pricesJson.length(); j++) {
                Prices prices = new Prices();
                prices.setLeftNumber(pricesJson.getJSONObject(j).getInt("leftNumber"));
                prices.setSeat(pricesJson.getJSONObject(j).getInt("seat"));
                prices.setPrice(pricesJson.getJSONObject(j).getDouble("price"));
                prices.setPromotionPrice(pricesJson.getJSONObject(j).getDouble("promotionPrice"));
                prices.setSeatName(pricesJson.getJSONObject(j).getString("seatName"));
                pricesList.add(prices);
            }

            trainInfoMore.setPrices(pricesList);
            trainInfoMore.setDepartureCityCode(list.getJSONObject(i).getInt("departureCityCode"));
            trainInfoMore.setArrivalCityCode(list.getJSONObject(i).getInt("arrivalCityCode"));
            trainInfoMore.setDepartureCityName(list.getJSONObject(i).getString("departureCityName"));
            trainInfoMore.setArrivalCityName(list.getJSONObject(i).getString("arrivalCityName"));
            trainInfoMore.setAccessByIdcard(list.getJSONObject(i).getString("accessByIdcard"));
            trainInfoMore.setDurationStr(list.getJSONObject(i).getString("durationStr"));
            trainInfoMoreList.add(trainInfoMore);
        }
        data.setTrainInfoMoreList(trainInfoMoreList);

        JSONObject allTrainTypeJson = dataJson.getJSONObject("allTrainType");
        AllTrainType allTrainType = new AllTrainType();
        JSONArray allTrainTypeJsonJSONArray = allTrainTypeJson.getJSONArray("list");
        List<AllTrainTypeMore> allTrainTypeMoreList = new ArrayList<>();
        for (int i = 0; i < allTrainTypeJsonJSONArray.length(); i++) {
            AllTrainTypeMore allTrainTypeMore = new AllTrainTypeMore();
            allTrainTypeMore.setTrainType(allTrainTypeJsonJSONArray.getJSONObject(i).getInt("trainType"));
            allTrainTypeMore.setTrainTypeName(allTrainTypeJsonJSONArray.getJSONObject(i).getString("trainTypeName"));
            allTrainTypeMore.setTrainTypeCode(allTrainTypeJsonJSONArray.getJSONObject(i).getString("trainTypeCode"));
            allTrainTypeMore.setLink(allTrainTypeJsonJSONArray.getJSONObject(i).getString("link"));
            allTrainTypeMoreList.add(allTrainTypeMore);
        }
        allTrainType.setList(allTrainTypeMoreList);
        allTrainType.setDepartureCityName(allTrainTypeJson.getString("departureCityName"));
        allTrainType.setArrivalCityName(allTrainTypeJson.getString("arrivalCityName"));
        data.setAllTrainType(allTrainType);
        return data;
    }
}
