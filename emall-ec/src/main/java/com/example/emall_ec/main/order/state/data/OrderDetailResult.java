package com.example.emall_ec.main.order.state.data;

/**
 * Created by lixiang on 2018/3/6.
 */

public class OrderDetailResult {

    /**
     * data : {"imageDetailUrl":"http://59.110.162.194:8085/ygyg/101A/JL101A_PMS_20161113092742_000015634_101_0009_001_L1_MSS.jpg","orderId":"1711241148000515657","payment":1,"state":2,"type":1,"userId":"92209410004772"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * imageDetailUrl : http://59.110.162.194:8085/ygyg/101A/JL101A_PMS_20161113092742_000015634_101_0009_001_L1_MSS.jpg
         * orderId : 1711241148000515657
         * payment : 1
         * state : 2
         * type : 1
         * userId : 92209410004772
         */

        private String imageDetailUrl;
        private String orderId;
        private int payment;
        private int state;
        private int type;
        private String userId;

        public String getImageDetailUrl() {
            return imageDetailUrl;
        }

        public void setImageDetailUrl(String imageDetailUrl) {
            this.imageDetailUrl = imageDetailUrl;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public int getPayment() {
            return payment;
        }

        public void setPayment(int payment) {
            this.payment = payment;
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
    }
}
