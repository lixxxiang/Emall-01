package com.example.emall_ec.main.search.data;

import java.util.List;

public class VideoSearchBean {

    /**
     * data : [{"detailPath":"http://59.110.162.194:8085/ygyg/VIDEO103B/JL103B_MSS_20171006162735_100002453_101_001_L1B_MSS.jpg","geo":"{\"type\":\"Polygon\",\"coordinates\":[[[35.457584,33.948135],[35.594112,33.92194],[35.586525,33.867798],[35.449474,33.894363],[35.457584,33.948135]]]}","price":"50000.00","productId":"JL103B_MSS_20171006162735_100002453_101_001_L1B_MSS","startTime":"2017-10-06 16:27:35","title":"黎巴嫩 贝努特"}]
     * message : success
     * status : 200
     */

    private String message;
    private int status;
    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * detailPath : http://59.110.162.194:8085/ygyg/VIDEO103B/JL103B_MSS_20171006162735_100002453_101_001_L1B_MSS.jpg
         * geo : {"type":"Polygon","coordinates":[[[35.457584,33.948135],[35.594112,33.92194],[35.586525,33.867798],[35.449474,33.894363],[35.457584,33.948135]]]}
         * price : 50000.00
         * productId : JL103B_MSS_20171006162735_100002453_101_001_L1B_MSS
         * startTime : 2017-10-06 16:27:35
         * title : 黎巴嫩 贝努特
         */

        private String detailPath;
        private String geo;
        private String price;
        private String productId;
        private String startTime;
        private String title;

        public String getDetailPath() {
            return detailPath;
        }

        public void setDetailPath(String detailPath) {
            this.detailPath = detailPath;
        }

        public String getGeo() {
            return geo;
        }

        public void setGeo(String geo) {
            this.geo = geo;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
