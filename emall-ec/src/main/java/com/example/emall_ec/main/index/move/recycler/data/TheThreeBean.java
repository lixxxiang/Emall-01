package com.example.emall_ec.main.index.move.recycler.data;

/**
 * Created by lixiang on 2018/3/9.
 */

public class TheThreeBean {
    private String imageUrl;
    private String type;
    private String link;
    private String productId;

    public TheThreeBean(String imageUrl, String type, String link,String productId) {
        this.imageUrl = imageUrl;
        this.type = type;
        this.link = link;
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
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
