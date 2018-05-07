package com.example.emall_ec.main.demand.data;


public class QueryOrderFailureBean {


    /**
     * data : 101
     * message : 未付款成功
     * status : 200
     */

    private int data;
    private String message;
    private int status;


    public int getData() {
        return data;
    }

    public void setData(int data) {
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
}
