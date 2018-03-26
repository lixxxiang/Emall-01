package com.example.emall_ec.main.classify.data;

/**
 * Created by lixiang on 2018/3/26.
 */

public class SceneDetail {

    /**
     * data : {"centerTime":"2018-03-16 18:46:59","cloud":"42","geo":"{\"type\":\"Polygon\",\"coordinates\":[[[2.233075,48.847742],[2.404263,48.82245],[2.365522,48.709491],[2.194951,48.734706],[2.233075,48.847742]]]}","imageDetailUrl":"http://59.110.162.194:8085/ygyg/101A/2018/03/16/JL101A_PMS_20180316184644_000021554_204_001_L1_MSS/JL101A_PMS_20180316184644_000021554_204_0011_001_L1_MSS/JL101A_PMS_20180316184644_000021554_204_0011_001_L1_MSS.jpg","latitude":"48.778593","longitude":"2.299650","originalPrice":"19885.00","productId":"JL101A_PMS_20180316184644_000021554_204_0011_001_L1_PAN","productLevel":"L1","promotionDescription":"遥感数据全类型开放购买","promotionName":"遥感易购","resolution":"0.78m+3.12m","salePrice":"19885.00","satelliteId":"JL101A","sceneId":"JL101A_PMS_20180316184644_000021554_204_0011","sensor":"PMS","serviceDescription":"若今日24:00前下单，2018-03-29可交付","size":"165.71","swingSatelliteAngle":"-14.788041"}
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
         * centerTime : 2018-03-16 18:46:59
         * cloud : 42
         * geo : {"type":"Polygon","coordinates":[[[2.233075,48.847742],[2.404263,48.82245],[2.365522,48.709491],[2.194951,48.734706],[2.233075,48.847742]]]}
         * imageDetailUrl : http://59.110.162.194:8085/ygyg/101A/2018/03/16/JL101A_PMS_20180316184644_000021554_204_001_L1_MSS/JL101A_PMS_20180316184644_000021554_204_0011_001_L1_MSS/JL101A_PMS_20180316184644_000021554_204_0011_001_L1_MSS.jpg
         * latitude : 48.778593
         * longitude : 2.299650
         * originalPrice : 19885.00
         * productId : JL101A_PMS_20180316184644_000021554_204_0011_001_L1_PAN
         * productLevel : L1
         * promotionDescription : 遥感数据全类型开放购买
         * promotionName : 遥感易购
         * resolution : 0.78m+3.12m
         * salePrice : 19885.00
         * satelliteId : JL101A
         * sceneId : JL101A_PMS_20180316184644_000021554_204_0011
         * sensor : PMS
         * serviceDescription : 若今日24:00前下单，2018-03-29可交付
         * size : 165.71
         * swingSatelliteAngle : -14.788041
         */

        private String centerTime;
        private String cloud;
        private String geo;
        private String imageDetailUrl;
        private String latitude;
        private String longitude;
        private String originalPrice;
        private String productId;
        private String productLevel;
        private String promotionDescription;
        private String promotionName;
        private String resolution;
        private String salePrice;
        private String satelliteId;
        private String sceneId;
        private String sensor;
        private String serviceDescription;
        private String size;
        private String swingSatelliteAngle;

        public String getCenterTime() {
            return centerTime;
        }

        public void setCenterTime(String centerTime) {
            this.centerTime = centerTime;
        }

        public String getCloud() {
            return cloud;
        }

        public void setCloud(String cloud) {
            this.cloud = cloud;
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

        public String getProductLevel() {
            return productLevel;
        }

        public void setProductLevel(String productLevel) {
            this.productLevel = productLevel;
        }

        public String getPromotionDescription() {
            return promotionDescription;
        }

        public void setPromotionDescription(String promotionDescription) {
            this.promotionDescription = promotionDescription;
        }

        public String getPromotionName() {
            return promotionName;
        }

        public void setPromotionName(String promotionName) {
            this.promotionName = promotionName;
        }

        public String getResolution() {
            return resolution;
        }

        public void setResolution(String resolution) {
            this.resolution = resolution;
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

        public String getSceneId() {
            return sceneId;
        }

        public void setSceneId(String sceneId) {
            this.sceneId = sceneId;
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

        public String getSwingSatelliteAngle() {
            return swingSatelliteAngle;
        }

        public void setSwingSatelliteAngle(String swingSatelliteAngle) {
            this.swingSatelliteAngle = swingSatelliteAngle;
        }
    }
}
