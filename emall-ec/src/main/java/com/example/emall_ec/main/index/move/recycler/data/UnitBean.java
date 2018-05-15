package com.example.emall_ec.main.index.move.recycler.data;

/**
 * Created by lixiang on 2018/2/23.
 */

public class UnitBean {
    private String title;
    private String detail;
    private String imageUrl;
    private String type;
    private String link;
    private String productId;

    public UnitBean(String title, String detail, String imageUrl, String type, String link, String productId) {
        this.title = title;
        this.detail = detail;
        this.imageUrl = imageUrl;
        this.type = type;
        this.link = link;
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }

    public String getTitle() {
        return title;
    }

    public String getDetail() {
        return detail;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getType() {
        return type;
    }

    public String getLink() {
        return link;
    }
}
