package com.example.emall_ec.main.detail.data;

/**
 * Created by lixiang on 2018/3/27.
 */

public class SceneDetailBean {

    /**
     * data : {"centerTime":"2016-08-20 10:26:05","cloud":"0","geo":"{\"type\":\"Polygon\",\"coordinates\":[[[125.159646,43.557791],[125.350448,43.532529],[125.317636,43.406865],[125.125979,43.432256],[125.159646,43.557791]]]}","imageDetailUrl":"http://59.110.162.194:8085/ygyg/101A/old/JL101A_PMS_20160820102530_000012697_101_0021_001_L1_MSS.jpg","latitude":"43.482402","longitude":"125.238632","originalPrice":"13405.00","productId":"JL101A_PMS_20160820102530_000012697_101_0021_001_L1_PAN","productLevel":"L1","promotionDescription":"遥感数据全类型开放购买","promotionName":"遥感易购","resolution":"1.50m+1.50m","salePrice":"13405.00","satelliteId":"JL101A","sceneId":"JL101A_PMS_20160820102530_000012697_101_0021","sensor":"PMS","serviceDescription":"若今日24:00前下单，2018-03-30可交付","size":"223.41","swingSatelliteAngle":"-27.289980"}
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
         * centerTime : 2016-08-20 10:26:05
         * cloud : 0
         * geo : {"type":"Polygon","coordinates":[[[125.159646,43.557791],[125.350448,43.532529],[125.317636,43.406865],[125.125979,43.432256],[125.159646,43.557791]]]}
         * imageDetailUrl : http://59.110.162.194:8085/ygyg/101A/old/JL101A_PMS_20160820102530_000012697_101_0021_001_L1_MSS.jpg
         * latitude : 43.482402
         * longitude : 125.238632
         * originalPrice : 13405.00
         * productId : JL101A_PMS_20160820102530_000012697_101_0021_001_L1_PAN
         * productLevel : L1
         * promotionDescription : 遥感数据全类型开放购买
         * promotionName : 遥感易购
         * resolution : 1.50m+1.50m
         * salePrice : 13405.00
         * satelliteId : JL101A
         * sceneId : JL101A_PMS_20160820102530_000012697_101_0021
         * sensor : PMS
         * serviceDescription : 若今日24:00前下单，2018-03-30可交付
         * size : 223.41
         * swingSatelliteAngle : -27.289980
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

        @Override
        public String toString() {
            return "DataBean{" +
                    "centerTime='" + centerTime + '\'' +
                    ", cloud='" + cloud + '\'' +
                    ", geo='" + geo + '\'' +
                    ", imageDetailUrl='" + imageDetailUrl + '\'' +
                    ", latitude='" + latitude + '\'' +
                    ", longitude='" + longitude + '\'' +
                    ", originalPrice='" + originalPrice + '\'' +
                    ", productId='" + productId + '\'' +
                    ", productLevel='" + productLevel + '\'' +
                    ", promotionDescription='" + promotionDescription + '\'' +
                    ", promotionName='" + promotionName + '\'' +
                    ", resolution='" + resolution + '\'' +
                    ", salePrice='" + salePrice + '\'' +
                    ", satelliteId='" + satelliteId + '\'' +
                    ", sceneId='" + sceneId + '\'' +
                    ", sensor='" + sensor + '\'' +
                    ", serviceDescription='" + serviceDescription + '\'' +
                    ", size='" + size + '\'' +
                    ", swingSatelliteAngle='" + swingSatelliteAngle + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SceneDetailBean{" +
                "data=" + data +
                ", message='" + message + '\'' +
                ", status=" + status +
                '}';
    }
}
