package com.example.emall_core.ui.recycler.data;

/**
 * Created by lixiang on 2018/3/9.
 */

public class TheThreeBean {
    private String imageUrl;
    private String type;
    private String link;

    public TheThreeBean(String imageUrl, String type, String link) {
        this.imageUrl = imageUrl;
        this.type = type;
        this.link = link;
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
