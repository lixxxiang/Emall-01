package com.example.emall_ec.main.index.move.recycler.data;

/**
 * Created by lixiang on 2018/3/11.
 */

public class GuessLikeBean {
    private String dataType;
    private String imageUrl;
    private String posTitle;
    private String posDescription;
    private String price;
    private String productId;

    public GuessLikeBean(String dataType, String imageUrl, String posTitle, String posDescription, String price, String productId) {
        this.dataType = dataType;
        this.imageUrl = imageUrl;
        this.posTitle = posTitle;
        this.posDescription = posDescription;
        this.price = price;
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
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

