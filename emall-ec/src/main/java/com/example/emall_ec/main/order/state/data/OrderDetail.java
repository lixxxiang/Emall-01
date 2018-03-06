package com.example.emall_ec.main.order.state.data;

import java.util.List;

/**
 * Created by lixiang on 2018/3/6.
 */

public class OrderDetail {

    /**
     * data : [{"commitTime":"2017-11-24 11:48:51","details":{"centerTime":"2016-11-13 09:27:54","cloud":"0","geo":"{\"type\":\"Polygon\",\"coordinates\":[[[139.820421,35.786176],[139.964701,35.762829],[139.935792,35.647949],[139.791757,35.671273],[139.820421,35.786176]]]}","imageDetailUrl":"http://59.110.162.194:8085/ygyg/101A/JL101A_PMS_20161113092742_000015634_101_0009_001_L1_MSS.jpg","latitude":"35.717030","longitude":"139.878406","originalPrice":"10380.00","productId":"JL101A_PMS_20161113092742_000015634_101_0009_001_L1_PAN","productLevel":"L1","promotionDescription":"遥感数据全类型开放购买","promotionName":"遥感易购","resolution":"0.81m+3.25m","salePrice":"10380.00","satelliteId":"JL101A","sceneId":"JL101A_PMS_20161113092742_000015634_101_0009","sensor":"PMS","serviceDescription":"若今日24:00前下单，可交付","size":"173005950.24796021","swingSatelliteAngle":"-18.758638"},"fresh":0,"invoiceState":0,"orderId":"1711241148000515657","parentOrderId":"1711241148000202865","payment":1,"planCommitTime":"2017-11-26 11:48:51","productId":"JL101A_PMS_20161113092742_000015634_101_0009_001_L1_PAN","state":2,"type":1,"userId":"92209410004772"},{"commitTime":"2017-11-24 11:26:18","details":{"centerTime":"2016-12-21 21:55:01","cloud":"0","geo":"{\"type\":\"Polygon\",\"coordinates\":[[[-56.326051,-34.757938],[-56.200412,-34.782163],[-56.230985,-34.890162],[-56.356782,-34.8659],[-56.326051,-34.757938]]]}","imageDetailUrl":"http://59.110.162.194:8085/ygyg/101A/JL101A_PMS_20161221215447_000017023_105_0011_001_L1_MSS.jpg","latitude":"-34.824071","longitude":"-56.278507","originalPrice":"8718.00","productId":"JL101A_PMS_20161221215447_000017023_105_0011_001_L1_PAN","productLevel":"L1","promotionDescription":"遥感数据全类型开放购买","promotionName":"遥感易购","resolution":"0.72m+2.90m","salePrice":"8718.00","satelliteId":"JL101A","sceneId":"JL101A_PMS_20161221215447_000017023_105_0011","sensor":"PMS","serviceDescription":"若今日24:00前下单，可交付","size":"145296598.265571266","swingSatelliteAngle":"-4.331561"},"fresh":0,"invoiceState":0,"orderId":"1711241126000181195","parentOrderId":"1711241125000296053","payment":3076,"planCommitTime":"2017-11-26 11:26:18","productId":"JL101A_PMS_20161221215447_000017023_105_0011_001_L1_PAN","state":4,"type":1,"userId":"92209410004772"},{"commitTime":"2017-11-21 17:18:39","details":{"cloud":"0","duration":"32s","geo":"{\"type\":\"Polygon\",\"coordinates\":[[[20.020223,32.126537],[20.152459,32.101888],[20.145221,32.055796],[20.01283,32.080842],[20.020223,32.126537]]]}","imageDetailUrl":"http://59.110.162.194:8085/ygyg/VIDEO103B/JL103B_MSS_20170823173205_100002070_102_001_L1B_MSS.jpg","latitude":"32.091364","longitude":"20.082430","originalPrice":"50000.00","productId":"JL103B_MSS_20170823173205_100002070_102_001_L1B_MSS","promotionDescription":"遥感数据全类型开放购买","promotionType":"遥感易购","resolution":"1.01","rollSatelliteAngleMajor":"18.297349929810","salePrice":"50000.00","satelliteId":"JL103B","sensor":"MSS","serviceDescription":"若今日24:00前下单，可交付","size":"65470509.7704740614","startTime":"2017-08-23 17:32:05","title":"班加西","videoPath":"http://59.110.162.194:8085/ygyg/VIDEO103B/JL103B_MSS_20170823173205_100002070_102_001_L1B_MSS.mp4"},"fresh":0,"invoiceState":0,"orderId":"1711211718000396547","parentOrderId":"1711211718000332658","payMethod":2,"payment":1,"planCommitTime":"2017-11-23 17:18:39","productId":"JL103B_MSS_20170823173205_100002070_102_001_L1B_MSS","state":4,"type":3,"url":"http://202.111.178.10:10083/webhdfs/v1/product_entity/videoProduct/JL103B_MSS_20170823173205_100002070_102_001_L1B_MSS.zip?op=OPEN&user.name=hadoop","userId":"92209410004772"}]
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
         * commitTime : 2017-11-24 11:48:51
         * details : {"centerTime":"2016-11-13 09:27:54","cloud":"0","geo":"{\"type\":\"Polygon\",\"coordinates\":[[[139.820421,35.786176],[139.964701,35.762829],[139.935792,35.647949],[139.791757,35.671273],[139.820421,35.786176]]]}","imageDetailUrl":"http://59.110.162.194:8085/ygyg/101A/JL101A_PMS_20161113092742_000015634_101_0009_001_L1_MSS.jpg","latitude":"35.717030","longitude":"139.878406","originalPrice":"10380.00","productId":"JL101A_PMS_20161113092742_000015634_101_0009_001_L1_PAN","productLevel":"L1","promotionDescription":"遥感数据全类型开放购买","promotionName":"遥感易购","resolution":"0.81m+3.25m","salePrice":"10380.00","satelliteId":"JL101A","sceneId":"JL101A_PMS_20161113092742_000015634_101_0009","sensor":"PMS","serviceDescription":"若今日24:00前下单，可交付","size":"173005950.24796021","swingSatelliteAngle":"-18.758638"}
         * fresh : 0
         * invoiceState : 0
         * orderId : 1711241148000515657
         * parentOrderId : 1711241148000202865
         * payment : 1
         * planCommitTime : 2017-11-26 11:48:51
         * productId : JL101A_PMS_20161113092742_000015634_101_0009_001_L1_PAN
         * state : 2
         * type : 1
         * userId : 92209410004772
         * payMethod : 2
         * url : http://202.111.178.10:10083/webhdfs/v1/product_entity/videoProduct/JL103B_MSS_20170823173205_100002070_102_001_L1B_MSS.zip?op=OPEN&user.name=hadoop
         */

        private String commitTime;
        private DetailsBean details;
        private int fresh;
        private int invoiceState;
        private String orderId;
        private String parentOrderId;
        private int payment;
        private String planCommitTime;
        private String productId;
        private int state;
        private int type;
        private String userId;
        private int payMethod;
        private String url;

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

        public int getPayment() {
            return payment;
        }

        public void setPayment(int payment) {
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

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public static class DetailsBean {
            /**
             * centerTime : 2016-11-13 09:27:54
             * cloud : 0
             * geo : {"type":"Polygon","coordinates":[[[139.820421,35.786176],[139.964701,35.762829],[139.935792,35.647949],[139.791757,35.671273],[139.820421,35.786176]]]}
             * imageDetailUrl : http://59.110.162.194:8085/ygyg/101A/JL101A_PMS_20161113092742_000015634_101_0009_001_L1_MSS.jpg
             * latitude : 35.717030
             * longitude : 139.878406
             * originalPrice : 10380.00
             * productId : JL101A_PMS_20161113092742_000015634_101_0009_001_L1_PAN
             * productLevel : L1
             * promotionDescription : 遥感数据全类型开放购买
             * promotionName : 遥感易购
             * resolution : 0.81m+3.25m
             * salePrice : 10380.00
             * satelliteId : JL101A
             * sceneId : JL101A_PMS_20161113092742_000015634_101_0009
             * sensor : PMS
             * serviceDescription : 若今日24:00前下单，可交付
             * size : 173005950.24796021
             * swingSatelliteAngle : -18.758638
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
