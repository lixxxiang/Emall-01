package com.example.emall_ec.main.detail.data;

/**
 * Created by lixiang on 2018/3/13.
 */

public class VideoDetailBean {

    /**
     * data : {"cloud":"0","duration":"32s","geo":"{\"type\":\"Polygon\",\"coordinates\":[[[35.457584,33.948135],[35.594112,33.92194],[35.586525,33.867798],[35.449474,33.894363],[35.457584,33.948135]]]}","imageDetailUrl":"http://59.110.162.194:8085/ygyg/VIDEO103B/JL103B_MSS_20171006162735_100002453_101_001_L1B_MSS.jpg","latitude":"33.908194","longitude":"35.521781","originalPrice":"50000.00","productId":"JL103B_MSS_20171006162735_100002453_101_001_L1B_MSS","promotionDescription":"遥感数据全类型开放购买","promotionType":"遥感易购","resolution":"0.94","rollSatelliteAngleMajor":"10.23","salePrice":"50000.00","satelliteId":"JL103B","sensor":"MSS","serviceDescription":"若今日24:00前下单，2018年03月16日可交付","size":"77.85","startTime":"2017-10-06 16:27:35","title":"黎巴嫩 贝努特","videoPath":"http://59.110.162.194:8085/ygyg/VIDEO103B/JL103B_MSS_20171006162735_100002453_101_001_L1B_MSS.mp4"}
     * message : success
     * status : 200
     */

    private DataBean data;
    private String message;
    private int status;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

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

    public static class DataBean {
        /**
         * cloud : 0
         * duration : 32s
         * geo : {"type":"Polygon","coordinates":[[[35.457584,33.948135],[35.594112,33.92194],[35.586525,33.867798],[35.449474,33.894363],[35.457584,33.948135]]]}
         * imageDetailUrl : http://59.110.162.194:8085/ygyg/VIDEO103B/JL103B_MSS_20171006162735_100002453_101_001_L1B_MSS.jpg
         * latitude : 33.908194
         * longitude : 35.521781
         * originalPrice : 50000.00
         * productId : JL103B_MSS_20171006162735_100002453_101_001_L1B_MSS
         * promotionDescription : 遥感数据全类型开放购买
         * promotionType : 遥感易购
         * resolution : 0.94
         * rollSatelliteAngleMajor : 10.23
         * salePrice : 50000.00
         * satelliteId : JL103B
         * sensor : MSS
         * serviceDescription : 若今日24:00前下单，2018年03月16日可交付
         * size : 77.85
         * startTime : 2017-10-06 16:27:35
         * title : 黎巴嫩 贝努特
         * videoPath : http://59.110.162.194:8085/ygyg/VIDEO103B/JL103B_MSS_20171006162735_100002453_101_001_L1B_MSS.mp4
         */

        private String cloud;
        private String duration;
        private String geo;
        private String imageDetailUrl;
        private String latitude;
        private String longitude;
        private String originalPrice;
        private String productId;
        private String promotionDescription;
        private String promotionType;
        private String resolution;
        private String rollSatelliteAngleMajor;
        private String salePrice;
        private String satelliteId;
        private String sensor;
        private String serviceDescription;
        private String size;
        private String startTime;
        private String title;
        private String videoPath;

        public String getCloud() {
            return cloud;
        }

        public void setCloud(String cloud) {
            this.cloud = cloud;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getGeo() {
            return geo;
        }

        public void setGeo(String geo) {
            this.geo = geo;
        }

        public String getImageDetailUrl() {
            return imageDetailUrl;
        }

        public void setImageDetailUrl(String imageDetailUrl) {
            this.imageDetailUrl = imageDetailUrl;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(String originalPrice) {
            this.originalPrice = originalPrice;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getPromotionDescription() {
            return promotionDescription;
        }

        public void setPromotionDescription(String promotionDescription) {
            this.promotionDescription = promotionDescription;
        }

        public String getPromotionType() {
            return promotionType;
        }

        public void setPromotionType(String promotionType) {
            this.promotionType = promotionType;
        }

        public String getResolution() {
            return resolution;
        }

        public void setResolution(String resolution) {
            this.resolution = resolution;
        }

        public String getRollSatelliteAngleMajor() {
            return rollSatelliteAngleMajor;
        }

        public void setRollSatelliteAngleMajor(String rollSatelliteAngleMajor) {
            this.rollSatelliteAngleMajor = rollSatelliteAngleMajor;
        }

        public String getSalePrice() {
            return salePrice;
        }

        public void setSalePrice(String salePrice) {
            this.salePrice = salePrice;
        }

        public String getSatelliteId() {
            return satelliteId;
        }

        public void setSatelliteId(String satelliteId) {
            this.satelliteId = satelliteId;
        }

        public String getSensor() {
            return sensor;
        }

        public void setSensor(String sensor) {
            this.sensor = sensor;
        }

        public String getServiceDescription() {
            return serviceDescription;
        }

        public void setServiceDescription(String serviceDescription) {
            this.serviceDescription = serviceDescription;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
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

        public String getVideoPath() {
            return videoPath;
        }

        public void setVideoPath(String videoPath) {
            this.videoPath = videoPath;
        }
    }
}
