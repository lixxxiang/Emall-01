package com.example.emall_ec.main.demand.data;

import com.google.gson.annotations.SerializedName;

public class AppPayBean {

    /**
     * code : 0
     * data : {"package":"Sign=WXPay","appid":"wxd12cdf5edf0f42fd","sign":"AA54134CF0EE0C2A32D219DA9367E760","prepayid":"wx2315221370790274a8adf0363901890536","mch_id":"1497690652","noncestr":"1524468133724","timestamp":"1524468133"}
     */

    private int code;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * package : Sign=WXPay
         * appid : wxd12cdf5edf0f42fd
         * sign : AA54134CF0EE0C2A32D219DA9367E760
         * prepayid : wx2315221370790274a8adf0363901890536
         * mch_id : 1497690652
         * noncestr : 1524468133724
         * timestamp : 1524468133
         */

        @SerializedName("package")
        private String packageX;
        private String appid;
        private String sign;
        private String prepayid;
        private String mch_id;
        private String noncestr;
        private String timestamp;

        public String getPackageX() {
            return packageX;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getPrepayid() {
            return prepayid;
        }

        public void setPrepayid(String prepayid) {
            this.prepayid = prepayid;
        }

        public String getMch_id() {
            return mch_id;
        }

        public void setMch_id(String mch_id) {
            this.mch_id = mch_id;
        }

        public String getNoncestr() {
            return noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(String timestamp) {
            this.timestamp = timestamp;
        }
    }
}
