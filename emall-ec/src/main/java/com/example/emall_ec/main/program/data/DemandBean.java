package com.example.emall_ec.main.program.data;

public class DemandBean {

    /**
     * data : {"orderIdArray":"1804200031000193436"}
     * msg : 成功
     * status : 200
     */

    private DataBean data;
    private String msg;
    private int status;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class DataBean {
        /**
         * orderIdArray : 1804200031000193436
         */

        private String orderIdArray;

        public String getOrderIdArray() {
            return orderIdArray;
        }

        public void setOrderIdArray(String orderIdArray) {
            this.orderIdArray = orderIdArray;
        }
    }
}
