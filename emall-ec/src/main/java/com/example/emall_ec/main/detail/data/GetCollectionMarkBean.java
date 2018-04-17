package com.example.emall_ec.main.detail.data;

public class GetCollectionMarkBean {

    /**
     * data : {"collectionMark":1}
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
         * collectionMark : 1
         */

        private int collectionMark;

        public int getCollectionMark() {
            return collectionMark;
        }

        public void setCollectionMark(int collectionMark) {
            this.collectionMark = collectionMark;
        }
    }
}
