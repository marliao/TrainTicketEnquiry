package com.marliao.trainticketenquiry.vo;

public class Prices {
    private int leftNumber;
    private int seat;
    private double price;
    private double promotionPrice;
    private String seatName;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(double promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    public int getLeftNumber() {
        return leftNumber;
    }

    public void setLeftNumber(int leftNumber) {
        this.leftNumber = leftNumber;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public String getSeatName() {
        return seatName;
    }

    public void setSeatName(String seatName) {
        this.seatName = seatName;
    }
}
