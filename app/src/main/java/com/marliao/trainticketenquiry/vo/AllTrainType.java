package com.marliao.trainticketenquiry.vo;

import java.util.List;

public class AllTrainType {
    private List<AllTrainTypeMore> list;
    private String departureCityName;
    private String arrivalCityName;

    public List<AllTrainTypeMore> getList() {
        return list;
    }

    public void setList(List<AllTrainTypeMore> list) {
        this.list = list;
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
}
