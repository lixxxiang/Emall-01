package com.example.emall_ec.main.me.collect.data;

import java.util.List;

public class MyAllCollectionBean {

    /**
     * data : {"pages":1,"count":5,"collection":[{"duration":"","originalPrice":"17328.00","productId":"JL101A_PMS_20180317160429_000021558_203_0018_001_L1_PAN","productType":"1","shootingTime":"2018-03-17 16:04:54","thumbnailUrl":"http://59.110.162.194:8085/ygyg/101A/2018/03/17/JL101A_PMS_20180317160429_000021558_203_001_L1_MSS/JL101A_PMS_20180317160429_000021558_203_0018_001_L1_MSS/JL101A_PMS_20180317160429_000021558_203_0018_001_L1_MSS_thumb.jpg","title":""},{"duration":"","originalPrice":"17332.00","productId":"JL101A_PMS_20180317160429_000021558_203_0017_001_L1_PAN","productType":"1","shootingTime":"2018-03-17 16:04:52","thumbnailUrl":"http://59.110.162.194:8085/ygyg/101A/2018/03/17/JL101A_PMS_20180317160429_000021558_203_001_L1_MSS/JL101A_PMS_20180317160429_000021558_203_0017_001_L1_MSS/JL101A_PMS_20180317160429_000021558_203_0017_001_L1_MSS_thumb.jpg","title":""},{"duration":"32s","originalPrice":"50000.00","productId":"JL103B_MSS_20171028150947_100002675_101_001_L1B_MSS","productType":"3","shootingTime":"2017-10-28 15:09:47","thumbnailUrl":"http://59.110.162.194:8085/ygyg/VIDEO103B/JL103B_MSS_20171028150947_100002675_101_001_L1B_MSS_thumb.jpg","title":"阿联酋 阿布扎比"},{"duration":"","originalPrice":"49500.00","productId":"JL103B_MSS_20170408221619_100001114_101_0013_001_L1A_MSS","productType":"5","shootingTime":"2017-04-08 22:16:20","thumbnailUrl":"http://59.110.162.194:8085/ygyg/NIGHT103B/JL103B_MSS_20170408221619_100001114_101_0013_001_L1A_MSS_thumb.jpg","title":""},{"duration":"","originalPrice":"49500.00","productId":"JL103B_MSS_20170401054343_100001082_101_0040_001_L1A_MSS","productType":"5","shootingTime":"2017-04-01 05:43:47","thumbnailUrl":"http://59.110.162.194:8085/ygyg/NIGHT103B/JL103B_MSS_20170401054343_100001082_101_0040_001_L1A_MSS_thumb.jpg","title":""}]}
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
         * count : 5
         * collection : [{"duration":"","originalPrice":"17328.00","productId":"JL101A_PMS_20180317160429_000021558_203_0018_001_L1_PAN","productType":"1","shootingTime":"2018-03-17 16:04:54","thumbnailUrl":"http://59.110.162.194:8085/ygyg/101A/2018/03/17/JL101A_PMS_20180317160429_000021558_203_001_L1_MSS/JL101A_PMS_20180317160429_000021558_203_0018_001_L1_MSS/JL101A_PMS_20180317160429_000021558_203_0018_001_L1_MSS_thumb.jpg","title":""},{"duration":"","originalPrice":"17332.00","productId":"JL101A_PMS_20180317160429_000021558_203_0017_001_L1_PAN","productType":"1","shootingTime":"2018-03-17 16:04:52","thumbnailUrl":"http://59.110.162.194:8085/ygyg/101A/2018/03/17/JL101A_PMS_20180317160429_000021558_203_001_L1_MSS/JL101A_PMS_20180317160429_000021558_203_0017_001_L1_MSS/JL101A_PMS_20180317160429_000021558_203_0017_001_L1_MSS_thumb.jpg","title":""},{"duration":"32s","originalPrice":"50000.00","productId":"JL103B_MSS_20171028150947_100002675_101_001_L1B_MSS","productType":"3","shootingTime":"2017-10-28 15:09:47","thumbnailUrl":"http://59.110.162.194:8085/ygyg/VIDEO103B/JL103B_MSS_20171028150947_100002675_101_001_L1B_MSS_thumb.jpg","title":"阿联酋 阿布扎比"},{"duration":"","originalPrice":"49500.00","productId":"JL103B_MSS_20170408221619_100001114_101_0013_001_L1A_MSS","productType":"5","shootingTime":"2017-04-08 22:16:20","thumbnailUrl":"http://59.110.162.194:8085/ygyg/NIGHT103B/JL103B_MSS_20170408221619_100001114_101_0013_001_L1A_MSS_thumb.jpg","title":""},{"duration":"","originalPrice":"49500.00","productId":"JL103B_MSS_20170401054343_100001082_101_0040_001_L1A_MSS","productType":"5","shootingTime":"2017-04-01 05:43:47","thumbnailUrl":"http://59.110.162.194:8085/ygyg/NIGHT103B/JL103B_MSS_20170401054343_100001082_101_0040_001_L1A_MSS_thumb.jpg","title":""}]
         */

        private int pages;
        private int count;
        private List<CollectionBean> collection;

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

        public List<CollectionBean> getCollection() {
            return collection;
        }

        public void setCollection(List<CollectionBean> collection) {
            this.collection = collection;
        }

        public static class CollectionBean {
            /**
             * duration :
             * originalPrice : 17328.00
             * productId : JL101A_PMS_20180317160429_000021558_203_0018_001_L1_PAN
             * productType : 1
             * shootingTime : 2018-03-17 16:04:54
             * thumbnailUrl : http://59.110.162.194:8085/ygyg/101A/2018/03/17/JL101A_PMS_20180317160429_000021558_203_001_L1_MSS/JL101A_PMS_20180317160429_000021558_203_0018_001_L1_MSS/JL101A_PMS_20180317160429_000021558_203_0018_001_L1_MSS_thumb.jpg
             * title :
             */

            private String duration;
            private String originalPrice;
            private String productId;
            private String productType;
            private String shootingTime;
            private String thumbnailUrl;
            private String title;

            public String getDuration() {
                return duration;
            }

            public void setDuration(String duration) {
                this.duration = duration;
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

            public String getProductType() {
                return productType;
            }

            public void setProductType(String productType) {
                this.productType = productType;
            }

            public String getShootingTime() {
                return shootingTime;
            }

            public void setShootingTime(String shootingTime) {
                this.shootingTime = shootingTime;
            }

            public String getThumbnailUrl() {
                return thumbnailUrl;
            }

            public void setThumbnailUrl(String thumbnailUrl) {
                this.thumbnailUrl = thumbnailUrl;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
