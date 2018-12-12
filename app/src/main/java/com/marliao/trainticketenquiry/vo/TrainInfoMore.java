package com.marliao.trainticketenquiry.vo;

import java.util.List;

public class TrainInfoMore {
    private int trainId;
    private String trainNum;
    private int trainType;
    private String trainTypeName;
    private String departStationName;
    private String destStationName;
    private String departDepartTime;
    private String destArriveTime;
    private int duration;
    private List<Prices> prices;
    private int departureCityCode;
    private int arrivalCityCode;
    private String departureCityName;
    private String arrivalCityName;
    private String accessByIdcard;
    private String durationStr;

    public int getTrainId() {
        return trainId;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }

    public String getTrainNum() {
        return trainNum;
    }

    public void setTrainNum(String trainNum) {
        this.trainNum = trainNum;
    }

    public int getTrainType() {
        return trainType;
    }

    public void setTrainType(int trainType) {
        this.trainType = trainType;
    }

    public String getTrainTypeName() {
        return trainTypeName;
    }

    public void setTrainTypeName(String trainTypeName) {
        this.trainTypeName = trainTypeName;
    }

    public String getDepartStationName() {
        return departStationName;
    }

    public void setDepartStationName(String departStationName) {
        this.departStationName = departStationName;
    }

    public String getDestStationName() {
        return destStationName;
    }

    public void setDestStationName(String destStationName) {
        this.destStationName = destStationName;
    }

    public String getDepartDepartTime() {
        return departDepartTime;
    }

    public void setDepartDepartTime(String departDepartTime) {
        this.departDepartTime = departDepartTime;
    }

    public String getDestArriveTime() {
        return destArriveTime;
    }

    public void setDestArriveTime(String destArriveTime) {
        this.destArriveTime = destArriveTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public List<Prices> getPrices() {
        return prices;
    }

    public void setPrices(List<Prices> prices) {
        this.prices = prices;
    }

    public int getDepartureCityCode() {
        return departureCityCode;
    }

    public void setDepartureCityCode(int departureCityCode) {
        this.departureCityCode = departureCityCode;
    }

    public int getArrivalCityCode() {
        return arrivalCityCode;
    }

    public void setArrivalCityCode(int arrivalCityCode) {
        this.arrivalCityCode = arrivalCityCode;
    }

    public String getDepartureCityName() {
        return departureCityName;
    }

    public void setDepartureCityName(String departureCityName) {
        this.departureCityName = departureCityName;
    }

    public String getArrivalCityName() {
        return arrivalCityName;
    }

    public void setArrivalCityName(String arrivalCityName) {
        this.arrivalCityName = arrivalCityName;
    }

    public String getAccessByIdcard() {
        return accessByIdcard;
    }

    public void setAccessByIdcard(String accessByIdcard) {
        this.accessByIdcard = accessByIdcard;
    }

    public String getDurationStr() {
        return durationStr;
    }

    public void setDurationStr(String durationStr) {
        this.durationStr = durationStr;
    }
}
