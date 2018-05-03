package com.example.emall_ec.main.order.state.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by lixiang on 2018/3/6.
 */

public class OrderDetail implements Parcelable{

    /**
     * data : [{"commitTime":"2018-04-11 11:31:54","details":{"centerTime":"2017-04-08 22:16:20","cloud":"0","geo":"{\"type\":\"Polygon\",\"coordinates\":[[[116.263051,39.919634],[116.420897,39.938535],[116.436266,39.875576],[116.279603,39.856733],[116.263051,39.919634]]]}","imageDetailUrl":"http://59.110.162.194:8085/ygyg/NIGHT103B/JL103B_MSS_20170408221619_100001114_101_0013_001_L1A_MSS.jpg","latitude":"39.897520","longitude":"116.350035","originalPrice":"49500.00","productId":"JL103B_MSS_20170408221619_100001114_101_0013_001_L1A_MSS","productLevel":"L1A","promotionDescription":"遥感数据全类型开放购买","promotionName":"遥感易购","resolution":"0.91m","salePrice":"0.01","satelliteId":"JL103B","sceneId":"JL103B_MSS_20170408221619_100001114_101_0013_","sensor":"MSS","serviceDescription":"若今日24:00前下单，可交付","size":"96829857.3439639062","swingSatelliteAngle":"2.69"},"fresh":0,"invoiceState":0,"orderId":"180411113100054697","parentOrderId":"180411113100052742","payment":0.01,"planCommitTime":"2018-05-11 11:31:54","productId":"JL103B_MSS_20170408221619_100001114_101_0013_001_L1A_MSS","state":2,"type":5,"userId":"92209140003462"},{"commitTime":"2017-11-23 16:32:51","details":{"centerTime":"2016-07-28 19:41:22","cloud":"0","geo":"{\"type\":\"Polygon\",\"coordinates\":[[[-9.140887,38.514158],[-9.005061,38.488205],[-9.039959,38.378883],[-9.175843,38.404833],[-9.140887,38.514158]]]}","imageDetailUrl":"http://59.110.162.194:8085/ygyg/101A/old/JL101A_PMS_20160728194103_000011622_205_0014_001_L1_MSS.jpg","latitude":"38.446583","longitude":"-9.090770","originalPrice":"9161.00","productId":"JL101A_PMS_20160728194103_000011622_205_0014_001_L1_PAN","productLevel":"L1","promotionDescription":"遥感数据全类型开放购买","promotionName":"遥感易购","resolution":"1.50m+1.50m","salePrice":"9161.00","satelliteId":"JL101A","sceneId":"JL101A_PMS_20160728194103_000011622_205_0014","sensor":"PMS","serviceDescription":"若今日24:00前下单，可交付","size":"152685227.310682923","swingSatelliteAngle":"10.215837"},"fresh":0,"invoiceState":0,"orderId":"1711231632000516052","parentOrderId":"1711231632000317236","payMethod":2,"payTime":"2017-11-23 16:33:53","payment":1,"planCommitTime":"2017-11-25 16:32:51","productId":"JL101A_PMS_20160728194103_000011622_205_0014_001_L1_PAN","state":4,"type":1,"userId":"92209140003462"},{"commitTime":"2017-11-23 16:32:49","details":{"centerTime":"2016-07-28 19:41:22","cloud":"0","geo":"{\"type\":\"Polygon\",\"coordinates\":[[[-9.140887,38.514158],[-9.005061,38.488205],[-9.039959,38.378883],[-9.175843,38.404833],[-9.140887,38.514158]]]}","imageDetailUrl":"http://59.110.162.194:8085/ygyg/101A/old/JL101A_PMS_20160728194103_000011622_205_0014_001_L1_MSS.jpg","latitude":"38.446583","longitude":"-9.090770","originalPrice":"9161.00","productId":"JL101A_PMS_20160728194103_000011622_205_0014_001_L1_PAN","productLevel":"L1","promotionDescription":"遥感数据全类型开放购买","promotionName":"遥感易购","resolution":"1.50m+1.50m","salePrice":"9161.00","satelliteId":"JL101A","sceneId":"JL101A_PMS_20160728194103_000011622_205_0014","sensor":"PMS","serviceDescription":"若今日24:00前下单，可交付","size":"152685227.310682923","swingSatelliteAngle":"10.215837"},"fresh":0,"invoiceState":0,"orderId":"1711231632000491514","parentOrderId":"1711231632000317236","payment":1,"planCommitTime":"2017-11-25 16:32:49","productId":"JL101A_PMS_20160728194103_000011622_205_0014_001_L1_PAN","state":2,"type":1,"userId":"92209140003462"}]
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }

    public static class DataBean {
        /**
         * commitTime : 2018-04-11 11:31:54
         * details : {"centerTime":"2017-04-08 22:16:20","cloud":"0","geo":"{\"type\":\"Polygon\",\"coordinates\":[[[116.263051,39.919634],[116.420897,39.938535],[116.436266,39.875576],[116.279603,39.856733],[116.263051,39.919634]]]}","imageDetailUrl":"http://59.110.162.194:8085/ygyg/NIGHT103B/JL103B_MSS_20170408221619_100001114_101_0013_001_L1A_MSS.jpg","latitude":"39.897520","longitude":"116.350035","originalPrice":"49500.00","productId":"JL103B_MSS_20170408221619_100001114_101_0013_001_L1A_MSS","productLevel":"L1A","promotionDescription":"遥感数据全类型开放购买","promotionName":"遥感易购","resolution":"0.91m","salePrice":"0.01","satelliteId":"JL103B","sceneId":"JL103B_MSS_20170408221619_100001114_101_0013_","sensor":"MSS","serviceDescription":"若今日24:00前下单，可交付","size":"96829857.3439639062","swingSatelliteAngle":"2.69"}
         * fresh : 0
         * invoiceState : 0
         * orderId : 180411113100054697
         * parentOrderId : 180411113100052742
         * payment : 0.01
         * planCommitTime : 2018-05-11 11:31:54
         * productId : JL103B_MSS_20170408221619_100001114_101_0013_001_L1A_MSS
         * state : 2
         * type : 5
         * userId : 92209140003462
         * payMethod : 2
         * payTime : 2017-11-23 16:33:53
         */

        private String commitTime;
        private DetailsBean details;
        private int fresh;
        private int invoiceState;
        private String orderId;
        private String parentOrderId;
        private double payment;
        private String planCommitTime;
        private String productId;
        private int state;
        private int type;
        private String userId;
        private int payMethod;
        private String payTime;

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
            private String startTime;

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }
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