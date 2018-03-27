package com.example.emall_ec.main.demand.data;

import java.util.List;

/**
 * Created by lixiang on 2018/3/27.
 */

public class ViewDemandBean {

    /**
     * data : {"totalPrice":"15410700.00","demands":[{"cut":"","originalPrice":"15410700.00","productId":"1801120958000113507","salePrice":"15410700.00","title":"38526.75","type":"8"}]}
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

    public static class DataBean {
        /**
         * totalPrice : 15410700.00
         * demands : [{"cut":"","originalPrice":"15410700.00","productId":"1801120958000113507","salePrice":"15410700.00","title":"38526.75","type":"8"}]
         */

        private String totalPrice;
        private List<DemandsBean> demands;

        public String getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(String totalPrice) {
            this.totalPrice = totalPrice;
        }

        public List<DemandsBean> getDemands() {
            return demands;
        }

        public void setDemands(List<DemandsBean> demands) {
            this.demands = demands;
        }

        public static class DemandsBean {
            /**
             * cut :
             * originalPrice : 15410700.00
             * productId : 1801120958000113507
             * salePrice : 15410700.00
             * title : 38526.75
             * type : 8
             */

            private String cut;
            private String originalPrice;
            private String productId;
            private String salePrice;
            private String title;
            private String type;

            public String getCut() {
                return cut;
            }

            public void setCut(String cut) {
                this.cut = cut;
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

            public String getSalePrice() {
                return salePrice;
            }

            public void setSalePrice(String salePrice) {
                this.salePrice = salePrice;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
