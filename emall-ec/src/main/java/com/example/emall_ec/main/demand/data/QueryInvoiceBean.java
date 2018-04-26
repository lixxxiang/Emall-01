package com.example.emall_ec.main.demand.data;

public class QueryInvoiceBean {

    /**
     * data : {"bankAccount":"220381199111017638","depositBank":"中国建设银行","fullAddress":"前进大街2699号","invoiceId":1711151352000572000,"invoiceType":1,"location":"吉林省/长春市/朝阳区","mobilePhone":"17519446880","payerNumber":"220381199111017638","receiver":"王昊","registerAddress":"吉林省长春市","registerTel":"0431-88508890","title":"吉林大学","userId":"wanghao"}
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
         * bankAccount : 220381199111017638
         * depositBank : 中国建设银行
         * fullAddress : 前进大街2699号
         * invoiceId : 1711151352000572000
         * invoiceType : 1
         * location : 吉林省/长春市/朝阳区
         * mobilePhone : 17519446880
         * payerNumber : 220381199111017638
         * receiver : 王昊
         * registerAddress : 吉林省长春市
         * registerTel : 0431-88508890
         * title : 吉林大学
         * userId : wanghao
         */

        private String bankAccount;
        private String depositBank;
        private String fullAddress;
        private long invoiceId;
        private int invoiceType;
        private String location;
        private String mobilePhone;
        private String payerNumber;
        private String receiver;
        private String registerAddress;
        private String registerTel;
        private String title;
        private String userId;

        public String getBankAccount() {
            return bankAccount;
        }

        public void setBankAccount(String bankAccount) {
            this.bankAccount = bankAccount;
        }

        public String getDepositBank() {
            return depositBank;
        }

        public void setDepositBank(String depositBank) {
            this.depositBank = depositBank;
        }

        public String getFullAddress() {
            return fullAddress;
        }

        public void setFullAddress(String fullAddress) {
            this.fullAddress = fullAddress;
        }

        public long getInvoiceId() {
            return invoiceId;
        }

        public void setInvoiceId(long invoiceId) {
            this.invoiceId = invoiceId;
        }

        public int getInvoiceType() {
            return invoiceType;
        }

        public void setInvoiceType(int invoiceType) {
            this.invoiceType = invoiceType;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getMobilePhone() {
            return mobilePhone;
        }

        public void setMobilePhone(String mobilePhone) {
            this.mobilePhone = mobilePhone;
        }

        public String getPayerNumber() {
            return payerNumber;
        }

        public void setPayerNumber(String payerNumber) {
            this.payerNumber = payerNumber;
        }

        public String getReceiver() {
            return receiver;
        }

        public void setReceiver(String receiver) {
            this.receiver = receiver;
        }

        public String getRegisterAddress() {
            return registerAddress;
        }

        public void setRegisterAddress(String registerAddress) {
            this.registerAddress = registerAddress;
        }

        public String getRegisterTel() {
            return registerTel;
        }

        public void setRegisterTel(String registerTel) {
            this.registerTel = registerTel;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
