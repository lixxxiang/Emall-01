package com.example.emall_ec.main.demand.data;

public class QueryOrderBean {

    /**
     * data : {"sumPrice":0.01,"planCommitTime":"2018-05-16 16:27:37"}
     * message : 已支付成功
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
         * sumPrice : 0.01
         * planCommitTime : 2018-05-16 16:27:37
         */

        private double sumPrice;
        private String planCommitTime;

        public double getSumPrice() {
            return sumPrice;
        }

        public void setSumPrice(double sumPrice) {
            this.sumPrice = sumPrice;
        }

        public String getPlanCommitTime() {
            return planCommitTime;
        }

        public void setPlanCommitTime(String planCommitTime) {
            this.planCommitTime = planCommitTime;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "sumPrice=" + sumPrice +
                    ", planCommitTime='" + planCommitTime + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "QueryOrderBean{" +
                "data=" + data +
                ", message='" + message + '\'' +
                ", status=" + status +
                '}';
    }
}
