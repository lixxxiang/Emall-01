package com.example.emall_ec.main.demand.data;

import android.os.Parcel;
import android.os.Parcelable;

public class FindOrderDetailByOrderIdBean implements Parcelable {

    /**
     * data : {"commitTime":"2018-05-02 18:22:52","details":{"centerTime":"2017-04-08 22:16:20","cloud":"0","geo":"{\"type\":\"Polygon\",\"coordinates\":[[[116.263051,39.919634],[116.420897,39.938535],[116.436266,39.875576],[116.279603,39.856733],[116.263051,39.919634]]]}","imageDetailUrl":"http://59.110.162.194:8085/ygyg/NIGHT103B/JL103B_MSS_20170408221619_100001114_101_0013_001_L1A_MSS.jpg","latitude":"39.897520","longitude":"116.350035","originalPrice":"49500.00","productId":"JL103B_MSS_20170408221619_100001114_101_0013_001_L1A_MSS","productLevel":"L1A","promotionDescription":"遥感数据全类型开放购买","promotionName":"遥感易购","resolution":"0.91m","salePrice":"0.01","satelliteId":"JL103B","sceneId":"JL103B_MSS_20170408221619_100001114_101_0013_","sensor":"MSS","serviceDescription":"若今日24:00前下单，可交付","size":"96829857.3439639062","swingSatelliteAngle":"2.69"},"fresh":0,"invoiceState":0,"orderId":"1805021822000521701","parentOrderId":"1805021822000493609","payMethod":2,"payTime":"2018-05-02 18:23:01","payment":0.01,"planCommitTime":"2018-06-01 18:22:52","productId":"JL103B_MSS_20170408221619_100001114_101_0013_001_L1A_MSS","state":4,"type":5,"userId":"92209140003462"}
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }

    public static class DataBean {
        /**
         * commitTime : 2018-05-02 18:22:52
         * details : {"centerTime":"2017-04-08 22:16:20","cloud":"0","geo":"{\"type\":\"Polygon\",\"coordinates\":[[[116.263051,39.919634],[116.420897,39.938535],[116.436266,39.875576],[116.279603,39.856733],[116.263051,39.919634]]]}","imageDetailUrl":"http://59.110.162.194:8085/ygyg/NIGHT103B/JL103B_MSS_20170408221619_100001114_101_0013_001_L1A_MSS.jpg","latitude":"39.897520","longitude":"116.350035","originalPrice":"49500.00","productId":"JL103B_MSS_20170408221619_100001114_101_0013_001_L1A_MSS","productLevel":"L1A","promotionDescription":"遥感数据全类型开放购买","promotionName":"遥感易购","resolution":"0.91m","salePrice":"0.01","satelliteId":"JL103B","sceneId":"JL103B_MSS_20170408221619_100001114_101_0013_","sensor":"MSS","serviceDescription":"若今日24:00前下单，可交付","size":"96829857.3439639062","swingSatelliteAngle":"2.69"}
         * fresh : 0
         * invoiceState : 0
         * orderId : 1805021822000521701
         * parentOrderId : 1805021822000493609
         * payMethod : 2
         * payTime : 2018-05-02 18:23:01
         * payment : 0.01
         * planCommitTime : 2018-06-01 18:22:52
         * productId : JL103B_MSS_20170408221619_100001114_101_0013_001_L1A_MSS
         * state : 4
         * type : 5
         * userId : 92209140003462
         */

        private String commitTime;
        private DetailsBean details;
        private int fresh;
        private int invoiceState;
        private String orderId;
        private String parentOrderId;
        private int payMethod;
        private String payTime;
        private double payment;
        private String planCommitTime;
        private String productId;
        private int state;
        private int type;
        private String userId;

        public String getCommitTime() {
            return commitTime;
        }

        public void setCommitTime(String commitTime) {
            this.commitTime = commitTime;
        }

        public DetailsBean getDetails() {
            return details;
        }

        public void setDetails(DetailsBean details) {
            this.details = details;
        }

        public int getFresh() {
            return fresh;
        }

        public void setFresh(int fresh) {
            this.fresh = fresh;
        }

        public int getInvoiceState() {
            return invoiceState;
        }

        public void setInvoiceState(int invoiceState) {
            this.invoiceState = invoiceState;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getParentOrderId() {
            return parentOrderId;
        }

        public void setParentOrderId(String parentOrderId) {
            this.parentOrderId = parentOrderId;
        }

        public int getPayMethod() {
            return payMethod;
        }

        public void setPayMethod(int payMethod) {
            this.payMethod = payMethod;
        }

        public String getPayTime() {
            return payTime;
        }

        public void setPayTime(String payTime) {
            this.payTime = payTime;
        }

        public double getPayment() {
            return payment;
        }

        public void setPayment(double payment) {
            this.payment = payment;
        }

        public String getPlanCommitTime() {
            return planCommitTime;
        }

        public void setPlanCommitTime(String planCommitTime) {
            this.planCommitTime = planCommitTime;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public static class DetailsBean {
            /**
             * centerTime : 2017-04-08 22:16:20
             * cloud : 0
             * geo : {"type":"Polygon","coordinates":[[[116.263051,39.919634],[116.420897,39.938535],[116.436266,39.875576],[116.279603,39.856733],[116.263051,39.919634]]]}
             * imageDetailUrl : http://59.110.162.194:8085/ygyg/NIGHT103B/JL103B_MSS_20170408221619_100001114_101_0013_001_L1A_MSS.jpg
             * latitude : 39.897520
             * longitude : 116.350035
             * originalPrice : 49500.00
             * productId : JL103B_MSS_20170408221619_100001114_101_0013_001_L1A_MSS
             * productLevel : L1A
             * promotionDescription : 遥感数据全类型开放购买
             * promotionName : 遥感易购
             * resolution : 0.91m
             * salePrice : 0.01
             * satelliteId : JL103B
             * sceneId : JL103B_MSS_20170408221619_100001114_101_0013_
             * sensor : MSS
             * serviceDescription : 若今日24:00前下单，可交付
             * size : 96829857.3439639062
             * swingSatelliteAngle : 2.69
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
}
