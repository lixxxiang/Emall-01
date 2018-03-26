package com.example.emall_ec.main.classify.data;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lixiang on 2018/3/7.
 */

public class SceneSearch implements Serializable{

    /**
     * data : {"pages":2,"searchReturnDtoList":[{"centerTime":"2017-05-25 19:09:55","cloud":"0","geo":"{\"type\":\"Polygon\",\"coordinates\":[[[2.256133,48.88142],[2.417378,48.85133],[2.369755,48.74391],[2.20883,48.773933],[2.256133,48.88142]]]}","price":"9179.00","productId":"JL101A_PMS_20170525190937_000019751_103_0013_006_L1_PAN","resolution":"0.75m+3.00m","satelliteId":"JL101A","sceneId":"JL101A_PMS_20170525190937_000019751_103_0013","sensor":"PMS","thumbnailUrl":"http://59.110.162.194:8085/ygyg/101A/JL101A_PMS_20170525190937_000019751_103_0013_006_L1_MSS_thumb.jpg"},{"centerTime":"2017-05-25 19:09:53","cloud":"0","geo":"{\"type\":\"Polygon\",\"coordinates\":[[[2.296206,48.971775],[2.457732,48.94163],[2.409955,48.83419],[2.248755,48.864273],[2.296206,48.971775]]]}","price":"9182.00","productId":"JL101A_PMS_20170525190937_000019751_103_0012_006_L1_PAN","resolution":"0.75m+3.00m","satelliteId":"JL101A","sceneId":"JL101A_PMS_20170525190937_000019751_103_0012","sensor":"PMS","thumbnailUrl":"http://59.110.162.194:8085/ygyg/101A/JL101A_PMS_20170525190937_000019751_103_0012_006_L1_MSS_thumb.jpg"},{"centerTime":"2017-01-17 18:57:19","cloud":"0","geo":"{\"type\":\"Polygon\",\"coordinates\":[[[2.273589,48.90799],[2.430366,48.88276],[2.390064,48.775696],[2.233965,48.80081],[2.273589,48.90799]]]}","price":"8698.00","productId":"JL101A_PMS_20170117185657_000017713_205_0016_002_L1_PAN","resolution":"0.72m+2.89m","satelliteId":"JL101A","sceneId":"JL101A_PMS_20170117185657_000017713_205_0016","sensor":"PMS","thumbnailUrl":"http://59.110.162.194:8085/ygyg/101A/JL101A_PMS_20170117185657_000017713_205_0016_002_L1_MSS_thumb.jpg"},{"centerTime":"2017-01-17 18:57:17","cloud":"0","geo":"{\"type\":\"Polygon\",\"coordinates\":[[[2.307208,48.998024],[2.463989,48.972813],[2.423722,48.865726],[2.26707,48.890934],[2.307208,48.998024]]]}","price":"8698.00","productId":"JL101A_PMS_20170117185657_000017713_205_0015_002_L1_PAN","resolution":"0.72m+2.89m","satelliteId":"JL101A","sceneId":"JL101A_PMS_20170117185657_000017713_205_0015","sensor":"PMS","thumbnailUrl":"http://59.110.162.194:8085/ygyg/101A/JL101A_PMS_20170117185657_000017713_205_0015_002_L1_MSS_thumb.jpg"},{"centerTime":"2016-09-12 19:05:51","cloud":"0","geo":"{\"type\":\"Polygon\",\"coordinates\":[[[2.190344,48.898808],[2.34752,48.870754],[2.302519,48.763344],[2.14553,48.791348],[2.190344,48.898808]]]}","price":"8882.00","productId":"JL101A_PMS_20160912190542_000013611_105_0007_001_L1_PAN","resolution":"1.50m+1.50m","satelliteId":"JL101A","sceneId":"JL101A_PMS_20160912190542_000013611_105_0007","sensor":"PMS","thumbnailUrl":"http://59.110.162.194:8085/ygyg/101A/JL101A_PMS_20160912190542_000013611_105_0007_001_L1_MSS_thumb.jpg"},{"centerTime":"2016-09-12 19:05:51","cloud":"0","geo":"{\"type\":\"Polygon\",\"coordinates\":[[[2.190344,48.898808],[2.34752,48.870754],[2.302519,48.763344],[2.14553,48.791348],[2.190344,48.898808]]]}","price":"8882.00","productId":"JL101A_PMS_20160912190542_000013612_105_0007_001_L1_PAN","resolution":"1.50m+1.50m","satelliteId":"JL101A","sceneId":"JL101A_PMS_20160912190542_000013612_105_0007","sensor":"PMS","thumbnailUrl":"http://59.110.162.194:8085/ygyg/101A/JL101A_PMS_20160912190542_000013612_105_0007_001_L1_MSS_thumb.jpg"},{"centerTime":"2016-09-12 19:05:49","cloud":"0","geo":"{\"type\":\"Polygon\",\"coordinates\":[[[2.228008,48.98916],[2.385485,48.961056],[2.34039,48.853653],[2.18321,48.881702],[2.228008,48.98916]]]}","price":"8880.00","productId":"JL101A_PMS_20160912190542_000013612_105_0006_001_L1_PAN","resolution":"1.50m+1.50m","satelliteId":"JL101A","sceneId":"JL101A_PMS_20160912190542_000013612_105_0006","sensor":"PMS","thumbnailUrl":"http://59.110.162.194:8085/ygyg/101A/JL101A_PMS_20160912190542_000013612_105_0006_001_L1_MSS_thumb.jpg"},{"centerTime":"2016-08-25 19:10:11","cloud":"0","geo":"{\"type\":\"Polygon\",\"coordinates\":[[[2.396033,48.861305],[2.563381,48.829853],[2.514015,48.719105],[2.347045,48.75049],[2.396033,48.861305]]]}","price":"9834.00","productId":"JL101A_PMS_20160825190954_000012932_204_0012_001_L1_PAN","resolution":"1.50m+1.50m","satelliteId":"JL101A","sceneId":"JL101A_PMS_20160825190954_000012932_204_0012","sensor":"PMS","thumbnailUrl":"http://59.110.162.194:8085/ygyg/101A/JL101A_PMS_20160825190954_000012932_204_0012_001_L1_MSS_thumb.jpg"},{"centerTime":"2016-08-25 19:10:09","cloud":"0","geo":"{\"type\":\"Polygon\",\"coordinates\":[[[2.437376,48.954895],[2.604774,48.923412],[2.555274,48.812626],[2.388174,48.84404],[2.437376,48.954895]]]}","price":"9827.00","productId":"JL101A_PMS_20160825190954_000012932_204_0011_001_L1_PAN","resolution":"1.50m+1.50m","satelliteId":"JL101A","sceneId":"JL101A_PMS_20160825190954_000012932_204_0011","sensor":"PMS","thumbnailUrl":"http://59.110.162.194:8085/ygyg/101A/JL101A_PMS_20160825190954_000012932_204_0011_001_L1_MSS_thumb.jpg"},{"centerTime":"2016-08-25 19:10:07","cloud":"0","geo":"{\"type\":\"Polygon\",\"coordinates\":[[[2.389822,48.846966],[2.556929,48.815544],[2.507738,48.704773],[2.340661,48.736164],[2.389822,48.846966]]]}","price":"9833.00","productId":"JL101A_PMS_20160825190954_000012934_204_0010_002_L1_PAN","resolution":"1.50m+1.50m","satelliteId":"JL101A","sceneId":"JL101A_PMS_20160825190954_000012934_204_0010","sensor":"PMS","thumbnailUrl":"http://59.110.162.194:8085/ygyg/101A/JL101A_PMS_20160825190954_000012934_204_0010_002_L1_MSS_thumb.jpg"}],"count":13}
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
         * pages : 2
         * searchReturnDtoList : [{"centerTime":"2017-05-25 19:09:55","cloud":"0","geo":"{\"type\":\"Polygon\",\"coordinates\":[[[2.256133,48.88142],[2.417378,48.85133],[2.369755,48.74391],[2.20883,48.773933],[2.256133,48.88142]]]}","price":"9179.00","productId":"JL101A_PMS_20170525190937_000019751_103_0013_006_L1_PAN","resolution":"0.75m+3.00m","satelliteId":"JL101A","sceneId":"JL101A_PMS_20170525190937_000019751_103_0013","sensor":"PMS","thumbnailUrl":"http://59.110.162.194:8085/ygyg/101A/JL101A_PMS_20170525190937_000019751_103_0013_006_L1_MSS_thumb.jpg"},{"centerTime":"2017-05-25 19:09:53","cloud":"0","geo":"{\"type\":\"Polygon\",\"coordinates\":[[[2.296206,48.971775],[2.457732,48.94163],[2.409955,48.83419],[2.248755,48.864273],[2.296206,48.971775]]]}","price":"9182.00","productId":"JL101A_PMS_20170525190937_000019751_103_0012_006_L1_PAN","resolution":"0.75m+3.00m","satelliteId":"JL101A","sceneId":"JL101A_PMS_20170525190937_000019751_103_0012","sensor":"PMS","thumbnailUrl":"http://59.110.162.194:8085/ygyg/101A/JL101A_PMS_20170525190937_000019751_103_0012_006_L1_MSS_thumb.jpg"},{"centerTime":"2017-01-17 18:57:19","cloud":"0","geo":"{\"type\":\"Polygon\",\"coordinates\":[[[2.273589,48.90799],[2.430366,48.88276],[2.390064,48.775696],[2.233965,48.80081],[2.273589,48.90799]]]}","price":"8698.00","productId":"JL101A_PMS_20170117185657_000017713_205_0016_002_L1_PAN","resolution":"0.72m+2.89m","satelliteId":"JL101A","sceneId":"JL101A_PMS_20170117185657_000017713_205_0016","sensor":"PMS","thumbnailUrl":"http://59.110.162.194:8085/ygyg/101A/JL101A_PMS_20170117185657_000017713_205_0016_002_L1_MSS_thumb.jpg"},{"centerTime":"2017-01-17 18:57:17","cloud":"0","geo":"{\"type\":\"Polygon\",\"coordinates\":[[[2.307208,48.998024],[2.463989,48.972813],[2.423722,48.865726],[2.26707,48.890934],[2.307208,48.998024]]]}","price":"8698.00","productId":"JL101A_PMS_20170117185657_000017713_205_0015_002_L1_PAN","resolution":"0.72m+2.89m","satelliteId":"JL101A","sceneId":"JL101A_PMS_20170117185657_000017713_205_0015","sensor":"PMS","thumbnailUrl":"http://59.110.162.194:8085/ygyg/101A/JL101A_PMS_20170117185657_000017713_205_0015_002_L1_MSS_thumb.jpg"},{"centerTime":"2016-09-12 19:05:51","cloud":"0","geo":"{\"type\":\"Polygon\",\"coordinates\":[[[2.190344,48.898808],[2.34752,48.870754],[2.302519,48.763344],[2.14553,48.791348],[2.190344,48.898808]]]}","price":"8882.00","productId":"JL101A_PMS_20160912190542_000013611_105_0007_001_L1_PAN","resolution":"1.50m+1.50m","satelliteId":"JL101A","sceneId":"JL101A_PMS_20160912190542_000013611_105_0007","sensor":"PMS","thumbnailUrl":"http://59.110.162.194:8085/ygyg/101A/JL101A_PMS_20160912190542_000013611_105_0007_001_L1_MSS_thumb.jpg"},{"centerTime":"2016-09-12 19:05:51","cloud":"0","geo":"{\"type\":\"Polygon\",\"coordinates\":[[[2.190344,48.898808],[2.34752,48.870754],[2.302519,48.763344],[2.14553,48.791348],[2.190344,48.898808]]]}","price":"8882.00","productId":"JL101A_PMS_20160912190542_000013612_105_0007_001_L1_PAN","resolution":"1.50m+1.50m","satelliteId":"JL101A","sceneId":"JL101A_PMS_20160912190542_000013612_105_0007","sensor":"PMS","thumbnailUrl":"http://59.110.162.194:8085/ygyg/101A/JL101A_PMS_20160912190542_000013612_105_0007_001_L1_MSS_thumb.jpg"},{"centerTime":"2016-09-12 19:05:49","cloud":"0","geo":"{\"type\":\"Polygon\",\"coordinates\":[[[2.228008,48.98916],[2.385485,48.961056],[2.34039,48.853653],[2.18321,48.881702],[2.228008,48.98916]]]}","price":"8880.00","productId":"JL101A_PMS_20160912190542_000013612_105_0006_001_L1_PAN","resolution":"1.50m+1.50m","satelliteId":"JL101A","sceneId":"JL101A_PMS_20160912190542_000013612_105_0006","sensor":"PMS","thumbnailUrl":"http://59.110.162.194:8085/ygyg/101A/JL101A_PMS_20160912190542_000013612_105_0006_001_L1_MSS_thumb.jpg"},{"centerTime":"2016-08-25 19:10:11","cloud":"0","geo":"{\"type\":\"Polygon\",\"coordinates\":[[[2.396033,48.861305],[2.563381,48.829853],[2.514015,48.719105],[2.347045,48.75049],[2.396033,48.861305]]]}","price":"9834.00","productId":"JL101A_PMS_20160825190954_000012932_204_0012_001_L1_PAN","resolution":"1.50m+1.50m","satelliteId":"JL101A","sceneId":"JL101A_PMS_20160825190954_000012932_204_0012","sensor":"PMS","thumbnailUrl":"http://59.110.162.194:8085/ygyg/101A/JL101A_PMS_20160825190954_000012932_204_0012_001_L1_MSS_thumb.jpg"},{"centerTime":"2016-08-25 19:10:09","cloud":"0","geo":"{\"type\":\"Polygon\",\"coordinates\":[[[2.437376,48.954895],[2.604774,48.923412],[2.555274,48.812626],[2.388174,48.84404],[2.437376,48.954895]]]}","price":"9827.00","productId":"JL101A_PMS_20160825190954_000012932_204_0011_001_L1_PAN","resolution":"1.50m+1.50m","satelliteId":"JL101A","sceneId":"JL101A_PMS_20160825190954_000012932_204_0011","sensor":"PMS","thumbnailUrl":"http://59.110.162.194:8085/ygyg/101A/JL101A_PMS_20160825190954_000012932_204_0011_001_L1_MSS_thumb.jpg"},{"centerTime":"2016-08-25 19:10:07","cloud":"0","geo":"{\"type\":\"Polygon\",\"coordinates\":[[[2.389822,48.846966],[2.556929,48.815544],[2.507738,48.704773],[2.340661,48.736164],[2.389822,48.846966]]]}","price":"9833.00","productId":"JL101A_PMS_20160825190954_000012934_204_0010_002_L1_PAN","resolution":"1.50m+1.50m","satelliteId":"JL101A","sceneId":"JL101A_PMS_20160825190954_000012934_204_0010","sensor":"PMS","thumbnailUrl":"http://59.110.162.194:8085/ygyg/101A/JL101A_PMS_20160825190954_000012934_204_0010_002_L1_MSS_thumb.jpg"}]
         * count : 13
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
             * centerTime : 2017-05-25 19:09:55
             * cloud : 0
             * geo : {"type":"Polygon","coordinates":[[[2.256133,48.88142],[2.417378,48.85133],[2.369755,48.74391],[2.20883,48.773933],[2.256133,48.88142]]]}
             * price : 9179.00
             * productId : JL101A_PMS_20170525190937_000019751_103_0013_006_L1_PAN
             * resolution : 0.75m+3.00m
             * satelliteId : JL101A
             * sceneId : JL101A_PMS_20170525190937_000019751_103_0013
             * sensor : PMS
             * thumbnailUrl : http://59.110.162.194:8085/ygyg/101A/JL101A_PMS_20170525190937_000019751_103_0013_006_L1_MSS_thumb.jpg
             */

            private String centerTime;
            private String cloud;
            private String geo;
            private String price;
            private String productId;
            private String resolution;
            private String satelliteId;
            private String sceneId;
            private String sensor;
            private String thumbnailUrl;

            public String getCenterTime() {
                return centerTime;
            }

            public void setCenterTime(String centerTime) {
                this.centerTime = centerTime;
            }

            public String getCloud() {
                return cloud;
            }

            public void setCloud(String cloud) {
                this.cloud = cloud;
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

            public String getResolution() {
                return resolution;
            }

            public void setResolution(String resolution) {
                this.resolution = resolution;
            }

            public String getSatelliteId() {
                return satelliteId;
            }

            public void setSatelliteId(String satelliteId) {
                this.satelliteId = satelliteId;
            }

            public String getSceneId() {
                return sceneId;
            }

            public void setSceneId(String sceneId) {
                this.sceneId = sceneId;
            }

            public String getSensor() {
                return sensor;
            }

            public void setSensor(String sensor) {
                this.sensor = sensor;
            }

            public String getThumbnailUrl() {
                return thumbnailUrl;
            }

            public void setThumbnailUrl(String thumbnailUrl) {
                this.thumbnailUrl = thumbnailUrl;
            }
        }
    }
}
