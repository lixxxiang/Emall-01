package com.example.emall_ec.main.coupon.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CalculatePriceByCouponIdBean {

    private DataBean data;
    private String message;
    private int status;

    public static CalculatePriceByCouponIdBean objectFromData(String str) {

        return new Gson().fromJson(str, CalculatePriceByCouponIdBean.class);
    }

    public static CalculatePriceByCouponIdBean objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), CalculatePriceByCouponIdBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<CalculatePriceByCouponIdBean> arrayCalculatePriceByCouponIdBeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<CalculatePriceByCouponIdBean>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<CalculatePriceByCouponIdBean> arrayCalculatePriceByCouponIdBeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<CalculatePriceByCouponIdBean>>() {
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
        private String totalPrice;
        private String totalDiscount;
        private List<ProductPriceBean> productPrice;

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

        public String getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
        }

        public String getTotalDiscount() {
            return totalDiscount;
        }

        public void setTotalDiscount(String totalDiscount) {
            this.totalDiscount = totalDiscount;
        }

        public List<ProductPriceBean> getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(List<ProductPriceBean> productPrice) {
            this.productPrice = productPrice;
        }

        public static class ProductPriceBean {
            private String user_couponId;
            private String price;
            private String id;
            private String coupon_type;

            public static ProductPriceBean objectFromData(String str) {

                return new Gson().fromJson(str, ProductPriceBean.class);
            }

            public static ProductPriceBean objectFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    return new Gson().fromJson(jsonObject.getString(str), ProductPriceBean.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            public static List<ProductPriceBean> arrayProductPriceBeanFromData(String str) {

                Type listType = new TypeToken<ArrayList<ProductPriceBean>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public static List<ProductPriceBean> arrayProductPriceBeanFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);
                    Type listType = new TypeToken<ArrayList<ProductPriceBean>>() {
                    }.getType();

                    return new Gson().fromJson(jsonObject.getString(str), listType);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return new ArrayList();


            }

            public String getUser_couponId() {
                return user_couponId;
            }

            public void setUser_couponId(String user_couponId) {
                this.user_couponId = user_couponId;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getCoupon_type() {
                return coupon_type;
            }

            public void setCoupon_type(String coupon_type) {
                this.coupon_type = coupon_type;
            }
        }
    }
}
