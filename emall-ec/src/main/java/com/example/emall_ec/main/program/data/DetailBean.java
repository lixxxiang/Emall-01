package com.example.emall_ec.main.program.data;

public class DetailBean {

    /**
     * data : {"originalPrice":"55092000.00","salePrice":"55092000.00","promotionDescription":"遥感数据全类型开放购买","promotionName":"遥感易购","link1":"","serviceDescription":"若今日24:00前下单，2018-05-20可交付","link2":"","type":"5"}
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
         * originalPrice : 55092000.00
         * salePrice : 55092000.00
         * promotionDescription : 遥感数据全类型开放购买
         * promotionName : 遥感易购
         * link1 :
         * serviceDescription : 若今日24:00前下单，2018-05-20可交付
         * link2 :
         * type : 5
         */

        private String originalPrice;
        private String salePrice;
        private String promotionDescription;
        private String promotionName;
        private String link1;
        private String serviceDescription;
        private String link2;
        private String type;

        public String getOriginalPrice() {
            return originalPrice;
        }

        public void setOriginalPrice(String originalPrice) {
            this.originalPrice = originalPrice;
        }

        public String getSalePrice() {
            return salePrice;
        }

        public void setSalePrice(String salePrice) {
            this.salePrice = salePrice;
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

        public String getLink1() {
            return link1;
        }

        public void setLink1(String link1) {
            this.link1 = link1;
        }

        public String getServiceDescription() {
            return serviceDescription;
        }

        public void setServiceDescription(String serviceDescription) {
            this.serviceDescription = serviceDescription;
        }

        public String getLink2() {
            return link2;
        }

        public void setLink2(String link2) {
            this.link2 = link2;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
