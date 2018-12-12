package com.marliao.trainticketenquiry.vo;

import java.util.List;

public class Data {
    private Integer count;
    private List<TrainTypeDetails> trainTypeDetails;
    private List<TrainInfoMore> trainInfoMoreList;
    private AllTrainType allTrainType;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<TrainTypeDetails> getTrainTypeDetails() {
        return trainTypeDetails;
    }

    public void setTrainTypeDetails(List<TrainTypeDetails> trainTypeDetails) {
        this.trainTypeDetails = trainTypeDetails;
    }

    public List<TrainInfoMore> getTrainInfoMoreList() {
        return trainInfoMoreList;
    }

    public void setTrainInfoMoreList(List<TrainInfoMore> trainInfoMoreList) {
        this.trainInfoMoreList = trainInfoMoreList;
    }

    public AllTrainType getAllTrainType() {
        return allTrainType;
    }

    public void setAllTrainType(AllTrainType allTrainType) {
        this.allTrainType = allTrainType;
    }
}
