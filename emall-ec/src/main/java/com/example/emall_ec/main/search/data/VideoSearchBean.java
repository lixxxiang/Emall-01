package com.example.emall_ec.main.search.data;

import java.util.List;

public class VideoSearchBean {

    /**
     * data : {"pages":1,"searchReturnDtoList":[{"detailPath":"http://59.110.162.194:8085/ygyg/VIDEO103B/JL103B_MSS_20171025104215_100002646_102_001_L1B_MSS.jpg","duration":"31s","geo":"{\"type\":\"Polygon\",\"coordinates\":[[[125.24703,43.923443],[125.40621,43.894405],[125.394875,43.848297],[125.23565,43.87775],[125.24703,43.923443]]]}","price":"50000.00","productId":"JL103B_MSS_20171025104215_100002646_102_001_L1B_MSS","startTime":"2017-10-25 10:42:15","title":"吉林省长春市朝阳区柳条路460号"},{"detailPath":"http://59.110.162.194:8085/ygyg/VIDEO103B/JL103B_MSS_20170921102808_100002351_103_001_L1B_MSS.jpg","duration":"32s","geo":"{\"type\":\"Polygon\",\"coordinates\":[[[127.99179,42.06502],[128.13689,42.039692],[128.12723,41.995243],[127.98202,42.02092],[127.99179,42.06502]]]}","price":"50000.00","productId":"JL103B_MSS_20170921102808_100002351_103_001_L1B_MSS","startTime":"2017-09-21 10:28:08","title":"吉林省延边朝鲜族自治州安图县"},{"detailPath":"http://59.110.162.194:8085/ygyg/VIDEO103B/JL103B_MSS_20170920103559_100002341_102_001_L1B_MSS.jpg","duration":"32s","geo":"{\"type\":\"Polygon\",\"coordinates\":[[[125.35213,42.78528],[125.489525,42.764168],[125.47957,42.721714],[125.34209,42.74301],[125.35213,42.78528]]]}","price":"50000.00","productId":"JL103B_MSS_20170920103559_100002341_102_001_L1B_MSS","startTime":"2017-09-20 10:35:59","title":"吉林省辽源市东丰县"}],"count":3}
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
         * searchReturnDtoList : [{"detailPath":"http://59.110.162.194:8085/ygyg/VIDEO103B/JL103B_MSS_20171025104215_100002646_102_001_L1B_MSS.jpg","duration":"31s","geo":"{\"type\":\"Polygon\",\"coordinates\":[[[125.24703,43.923443],[125.40621,43.894405],[125.394875,43.848297],[125.23565,43.87775],[125.24703,43.923443]]]}","price":"50000.00","productId":"JL103B_MSS_20171025104215_100002646_102_001_L1B_MSS","startTime":"2017-10-25 10:42:15","title":"吉林省长春市朝阳区柳条路460号"},{"detailPath":"http://59.110.162.194:8085/ygyg/VIDEO103B/JL103B_MSS_20170921102808_100002351_103_001_L1B_MSS.jpg","duration":"32s","geo":"{\"type\":\"Polygon\",\"coordinates\":[[[127.99179,42.06502],[128.13689,42.039692],[128.12723,41.995243],[127.98202,42.02092],[127.99179,42.06502]]]}","price":"50000.00","productId":"JL103B_MSS_20170921102808_100002351_103_001_L1B_MSS","startTime":"2017-09-21 10:28:08","title":"吉林省延边朝鲜族自治州安图县"},{"detailPath":"http://59.110.162.194:8085/ygyg/VIDEO103B/JL103B_MSS_20170920103559_100002341_102_001_L1B_MSS.jpg","duration":"32s","geo":"{\"type\":\"Polygon\",\"coordinates\":[[[125.35213,42.78528],[125.489525,42.764168],[125.47957,42.721714],[125.34209,42.74301],[125.35213,42.78528]]]}","price":"50000.00","productId":"JL103B_MSS_20170920103559_100002341_102_001_L1B_MSS","startTime":"2017-09-20 10:35:59","title":"吉林省辽源市东丰县"}]
         * count : 3
         */

        private int pages;
        private int count;
        private List<SearchReturnDtoListBean> searchReturnDtoList;

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

        public List<SearchReturnDtoListBean> getSearchReturnDtoList() {
            return searchReturnDtoList;
        }

        public void setSearchReturnDtoList(List<SearchReturnDtoListBean> searchReturnDtoList) {
            this.searchReturnDtoList = searchReturnDtoList;
        }

        public static class SearchReturnDtoListBean {
            /**
             * detailPath : http://59.110.162.194:8085/ygyg/VIDEO103B/JL103B_MSS_20171025104215_100002646_102_001_L1B_MSS.jpg
             * duration : 31s
             * geo : {"type":"Polygon","coordinates":[[[125.24703,43.923443],[125.40621,43.894405],[125.394875,43.848297],[125.23565,43.87775],[125.24703,43.923443]]]}
             * price : 50000.00
             * productId : JL103B_MSS_20171025104215_100002646_102_001_L1B_MSS
             * startTime : 2017-10-25 10:42:15
             * title : 吉林省长春市朝阳区柳条路460号
             */

            private String detailPath;
            private String duration;
            private String geo;
            private String price;
            private String productId;
            private String startTime;
            private String title;

            public String getDetailPath() {
                return detailPath;
            }

            public void setDetailPath(String detailPath) {
                this.detailPath = detailPath;
            }

            public String getDuration() {
                return duration;
            }

            public void setDuration(String duration) {
                this.duration = duration;
            }

            public String getGeo() {
                return geo;
            }

            public void setGeo(String geo) {
                this.geo = geo;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getProductId() {
                return productId;
            }

            public void setProductId(String productId) {
                this.productId = productId;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            @Override
            public String toString() {
                return "SearchReturnDtoListBean{" +
                        "detailPath='" + detailPath + '\'' +
                        ", duration='" + duration + '\'' +
                        ", geo='" + geo + '\'' +
                        ", price='" + price + '\'' +
                        ", productId='" + productId + '\'' +
                        ", startTime='" + startTime + '\'' +
                        ", title='" + title + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "pages=" + pages +
                    ", count=" + count +
                    ", searchReturnDtoList=" + searchReturnDtoList +
                    '}';
        }
    }
}
