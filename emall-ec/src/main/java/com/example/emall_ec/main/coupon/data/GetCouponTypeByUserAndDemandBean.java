package com.example.emall_ec.main.coupon.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GetCouponTypeByUserAndDemandBean {

    private DataBean data;
    private String message;
    private int status;

    public static GetCouponTypeByUserAndDemandBean objectFromData(String str) {

        return new Gson().fromJson(str, GetCouponTypeByUserAndDemandBean.class);
    }

    public static GetCouponTypeByUserAndDemandBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), GetCouponTypeByUserAndDemandBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<GetCouponTypeByUserAndDemandBean> arrayGetCouponTypeByUserAndDemandBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<GetCouponTypeByUserAndDemandBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<GetCouponTypeByUserAndDemandBean> arrayGetCouponTypeByUserAndDemandBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<GetCouponTypeByUserAndDemandBean>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(str), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

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
        private List<String> coupon;

        public static DataBean objectFromData(String str) {

            return new Gson().fromJson(str, DataBean.class);
        }

        public static DataBean objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(str), DataBean.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public static List<DataBean> arrayDataBeanFromData(String str) {

            Type listType = new TypeToken<ArrayList<DataBean>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public static List<DataBean> arrayDataBeanFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);
                Type listType = new TypeToken<ArrayList<DataBean>>() {
                }.getType();

                return new Gson().fromJson(jsonObject.getString(str), listType);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return new ArrayList();


        }

        public List<String> getCoupon() {
            return coupon;
        }

        public void setCoupon(List<String> coupon) {
            this.coupon = coupon;
        }
    }
}
