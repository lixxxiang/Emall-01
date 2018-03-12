package com.example.emall_ec.main.special.beans;

/**
 * Created by lixiang on 2018/3/12.
 */

public class SpecialVerticalBean {
    private String dataType;
    private String imageUrl;
    private String posTitle;
    private String posDescription;
    private String price;

    public SpecialVerticalBean(String dataType, String imageUrl, String posTitle, String posDescription, String price) {
        this.dataType = dataType;
        this.imageUrl = imageUrl;
        this.posTitle = posTitle;
        this.posDescription = posDescription;
        this.price = price;
    }

    public String getDataType() {
        return dataType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPosTitle() {
        return posTitle;
    }

    public String getPosDescription() {
        return posDescription;
    }

    public String getPrice() {
        return price;
    }
}
