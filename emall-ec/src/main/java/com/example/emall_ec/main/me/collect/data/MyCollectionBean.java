package com.example.emall_ec.main.me.collect.data;

import java.util.List;

public class MyCollectionBean {

    /**
     * data : {"pages":1,"collections":[{"contentDate":"2017-11-17","contentId":"17120515020005943","contentName":"澳大利亚-布鲁斯","duration":"00:42","playCount":"1578","thumbnail1Path":"http://202.111.178.10:28085/upload/image/201712051502000312744_image.png","type":"2","style":"2","thumbnail2Path":"http://202.111.178.10:28085/upload/image/201711151644000581206_thumb.jpg","thumbnail3Path":"http://202.111.178.10:28085/upload/image/201711151645000098146_thumb.jpg"},{"contentDate":"2017-11-24","contentId":"17111516450002548","contentName":"【每日一图】旅游百事系列之\u201c人人向往的海港小城\u201d","style":"2","thumbnail1Path":"http://202.111.178.10:28085/upload/image/201711151644000461646_thumb.jpg","thumbnail2Path":"http://202.111.178.10:28085/upload/image/201711151644000581206_thumb.jpg","thumbnail3Path":"http://202.111.178.10:28085/upload/image/201711151645000098146_thumb.jpg","type":"1"},{"contentDate":"2017-11-25","contentId":"17111516460001646","contentName":"【每日一图】旅游百事系列之\u201d新疆阿克苏\u201c","style":"2","thumbnail1Path":"http://202.111.178.10:28085/upload/image/201711151645000412863_thumb.jpg","thumbnail2Path":"http://202.111.178.10:28085/upload/image/201711151645000471623_thumb.jpg","thumbnail3Path":"http://202.111.178.10:28085/upload/image/201711151645000582794_thumb.jpg","type":"1"}],"count":3}
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
         * pages : 1
         * collections : [{"contentDate":"2017-11-17","contentId":"17120515020005943","contentName":"澳大利亚-布鲁斯","duration":"00:42","playCount":"1578","thumbnail1Path":"http://202.111.178.10:28085/upload/image/201712051502000312744_image.png","type":"2"},{"contentDate":"2017-11-24","contentId":"17111516450002548","contentName":"【每日一图】旅游百事系列之\u201c人人向往的海港小城\u201d","style":"2","thumbnail1Path":"http://202.111.178.10:28085/upload/image/201711151644000461646_thumb.jpg","thumbnail2Path":"http://202.111.178.10:28085/upload/image/201711151644000581206_thumb.jpg","thumbnail3Path":"http://202.111.178.10:28085/upload/image/201711151645000098146_thumb.jpg","type":"1"},{"contentDate":"2017-11-25","contentId":"17111516460001646","contentName":"【每日一图】旅游百事系列之\u201d新疆阿克苏\u201c","style":"2","thumbnail1Path":"http://202.111.178.10:28085/upload/image/201711151645000412863_thumb.jpg","thumbnail2Path":"http://202.111.178.10:28085/upload/image/201711151645000471623_thumb.jpg","thumbnail3Path":"http://202.111.178.10:28085/upload/image/201711151645000582794_thumb.jpg","type":"1"}]
         * count : 3
         */

        private int pages;
        private int count;
        private List<CollectionsBean> collections;

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public List<CollectionsBean> getCollections() {
            return collections;
        }

        public void setCollections(List<CollectionsBean> collections) {
            this.collections = collections;
        }

        public static class CollectionsBean {
            /**
             * contentDate : 2017-11-17
             * contentId : 17120515020005943
             * contentName : 澳大利亚-布鲁斯
             * duration : 00:42
             * playCount : 1578
             * thumbnail1Path : http://202.111.178.10:28085/upload/image/201712051502000312744_image.png
             * type : 2
             * style : 2
             * thumbnail2Path : http://202.111.178.10:28085/upload/image/201711151644000581206_thumb.jpg
             * thumbnail3Path : http://202.111.178.10:28085/upload/image/201711151645000098146_thumb.jpg
             */

            private String contentDate;
            private String contentId;
            private String contentName;
            private String duration;
            private String playCount;
            private String thumbnail1Path;
            private String type;
            private String style;
            private String thumbnail2Path;
            private String thumbnail3Path;

            public String getContentDate() {
                return contentDate;
            }

            public void setContentDate(String contentDate) {
                this.contentDate = contentDate;
            }

            public String getContentId() {
                return contentId;
            }

            public void setContentId(String contentId) {
                this.contentId = contentId;
            }

            public String getContentName() {
                return contentName;
            }

            public void setContentName(String contentName) {
                this.contentName = contentName;
            }

            public String getDuration() {
                return duration;
            }

            public void setDuration(String duration) {
                this.duration = duration;
            }

            public String getPlayCount() {
                return playCount;
            }

            public void setPlayCount(String playCount) {
                this.playCount = playCount;
            }

            public String getThumbnail1Path() {
                return thumbnail1Path;
            }

            public void setThumbnail1Path(String thumbnail1Path) {
                this.thumbnail1Path = thumbnail1Path;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getStyle() {
                return style;
            }

            public void setStyle(String style) {
                this.style = style;
            }

            public String getThumbnail2Path() {
                return thumbnail2Path;
            }

            public void setThumbnail2Path(String thumbnail2Path) {
                this.thumbnail2Path = thumbnail2Path;
            }

            public String getThumbnail3Path() {
                return thumbnail3Path;
            }

            public void setThumbnail3Path(String thumbnail3Path) {
                this.thumbnail3Path = thumbnail3Path;
            }
        }
    }
}
