package com.example.emall_ec.main.index.dailypic.data;

import java.util.List;

/**
 * Created by lixiang on 2018/3/21.
 */

public class BannerBean {

    /**
     * data : [{"contentId":"17112312390000340","imageUrl":"http://202.111.178.10:28085/upload/image/201711231238000369901_image.jpg","number":"1","title":"【每日一图】旅游百事系列之\u201c孙中山故居纪念馆\u201d","type":"1"},{"contentId":"17112312380001643","imageUrl":"http://202.111.178.10:28085/upload/image/201711231237000358178_image.jpg","number":"2","title":"【每日一图】科普知识系列之\u201c世界十大水库\u201d","type":"1"},{"contentId":"17112312370001242","imageUrl":"http://202.111.178.10:28085/upload/image/201711231236000028904_image.jpg","number":"3","title":"【每日一图】旅游百事系列之\u201c观光全球最大的海洋度假区\u201d","type":"1"},{"contentId":"17112312350003785","imageUrl":"http://202.111.178.10:28085/upload/image/201711231234000446508_image.jpg","number":"4","title":"【每日一图】科普知识系列之\u201c广州本田汽车研究基地\u201d","type":"1"},{"contentId":"17111613530004882","imageUrl":"http://202.111.178.10:28085/upload/image/201711161352000446075_image.jpg","number":"5","title":"【每日一图】旅游百事系列之\u201c发展的增城区\u201d","type":"1"}]
     * message : success
     * status : 200
     */

    private String message;
    private int status;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * contentId : 17112312390000340
         * imageUrl : http://202.111.178.10:28085/upload/image/201711231238000369901_image.jpg
         * number : 1
         * title : 【每日一图】旅游百事系列之“孙中山故居纪念馆”
         * type : 1
         */

        private String contentId;
        private String imageUrl;
        private String number;
        private String title;
        private String type;

        public String getContentId() {
            return contentId;
        }

        public void setContentId(String contentId) {
            this.contentId = contentId;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
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
