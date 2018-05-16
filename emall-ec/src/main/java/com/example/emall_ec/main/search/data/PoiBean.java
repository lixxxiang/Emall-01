package com.example.emall_ec.main.search.data;

import java.util.List;

/**
 * Created by lixiang on 2018/3/20.
 */

public class PoiBean {

    /**
     * type : 1
     * gdPois : {"poiList":[{"id":"B0FFHOF64P","name":"肯德基(宝泰餐厅)","pname":"吉林省","cityname":"四平市","adname":"铁西区","address":"地直街英雄大路2095号1层101","location":"124.34543894929625,43.173521975992585","type":"餐饮服务;快餐厅;肯德基"},{"id":"B0FFH0NFFP","name":"肯德基(万达店)","pname":"吉林省","cityname":"四平市","adname":"铁东区","address":"南九经街与紫气大路交汇处东北50米四平万达广场附近","location":"124.3852304319198,43.1530273681771","type":"餐饮服务;餐饮相关场所;餐饮相关"},{"id":"B0FFGW8IOM","name":"肯德基(伊通大街)","pname":"吉林省","cityname":"四平市","adname":"伊通满族自治县","address":"伊通大街北50米","location":"125.29881541661813,43.332813228433395","type":"餐饮服务;快餐厅;肯德基"},{"id":"B0FFFFYAPF","name":"肯德基(辽河大路店)","pname":"吉林省","cityname":"四平市","adname":"双辽市","address":"辽河路3399号国商百货1层","location":"123.4809812367444,43.51599073774858","type":"餐饮服务;快餐厅;肯德基"},{"id":"B01B2010BR","name":"肯德基(四平站店)","pname":"吉林省","cityname":"四平市","adname":"铁西区","address":"英雄大路四平站南侧一楼","location":"124.36640406705412,43.16504922886244","type":"餐饮服务;快餐厅;肯德基"},{"id":"B01B200WOV","name":"肯德基(中央路店)","pname":"吉林省","cityname":"四平市","adname":"铁东区","address":"中央东路欧亚购物中心楼下","location":"124.38127006328529,43.16609643717282","type":"餐饮服务;快餐厅;肯德基"},{"id":"B01B200AB9","name":"肯德基(英雄店)","pname":"吉林省","cityname":"四平市","adname":"铁西区","address":"英雄大街华展新天地1层","location":"124.36351122885254,43.16700229324728","type":"餐饮服务;快餐厅;肯德基"}],"poiNum":7}
     * images : {"imageList":[],"imageNum":0}
     * videos : {"videoNum":0,"videoList":[]}
     * thematics : {"thematicList":[{"thematicId":116,"name":"长春20160528","thematicArea":6332.23,"img":"http://202.111.178.10:10083/webhdfs/v1/Thumbnail-KEY/长春20160528专题.jpg?op=OPEN&user.name=hadoop"},{"thematicId":411,"name":"长春20160605","thematicArea":6332.23,"img":"http://202.111.178.10:10083/webhdfs/v1/Thumbnail-KEY/长春20160605专题.jpg?op=OPEN&user.name=hadoop"}],"thematicNum":7}
     * multiPoints : MULTIPOINT(124.34543894929625 43.173521975992585,124.24543894929626 43.073521975992584,124.24543894929626 43.27352197599259,124.44543894929625 43.073521975992584,124.44543894929625 43.27352197599259,124.3852304319198 43.1530273681771,124.2852304319198 43.0530273681771,124.2852304319198 43.2530273681771,124.48523043191979 43.0530273681771,124.48523043191979 43.2530273681771,125.29881541661813 43.332813228433395,125.19881541661813 43.23281322843339,125.19881541661813 43.432813228433396,125.39881541661812 43.23281322843339,125.39881541661812 43.432813228433396,123.4809812367444 43.51599073774858,123.3809812367444 43.41599073774858,123.3809812367444 43.61599073774858,123.58098123674439 43.41599073774858,123.58098123674439 43.61599073774858,124.36640406705412 43.16504922886244,124.26640406705413 43.06504922886244,124.26640406705413 43.265049228862445,124.46640406705411 43.06504922886244,124.46640406705411 43.265049228862445,124.38127006328529 43.16609643717282,124.28127006328529 43.06609643717282,124.28127006328529 43.26609643717282,124.48127006328528 43.06609643717282,124.48127006328528 43.26609643717282,124.36351122885254 43.16700229324728,124.26351122885255 43.06700229324728,124.26351122885255 43.26700229324728,124.46351122885254 43.06700229324728,124.46351122885254 43.26700229324728)
     * tools : {"toolNum":0,"toolList":[]}
     */

    private String type;
    private GdPoisBean gdPois;
    private ImagesBean images;
    private VideosBean videos;
    private ThematicsBean thematics;
    private String multiPoints;
    private ToolsBean tools;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public GdPoisBean getGdPois() {
        return gdPois;
    }

    public void setGdPois(GdPoisBean gdPois) {
        this.gdPois = gdPois;
    }

    public ImagesBean getImages() {
        return images;
    }

    public void setImages(ImagesBean images) {
        this.images = images;
    }

    public VideosBean getVideos() {
        return videos;
    }

    public void setVideos(VideosBean videos) {
        this.videos = videos;
    }

    public ThematicsBean getThematics() {
        return thematics;
    }

    public void setThematics(ThematicsBean thematics) {
        this.thematics = thematics;
    }

    public String getMultiPoints() {
        return multiPoints;
    }

    public void setMultiPoints(String multiPoints) {
        this.multiPoints = multiPoints;
    }

    public ToolsBean getTools() {
        return tools;
    }

    public void setTools(ToolsBean tools) {
        this.tools = tools;
    }

    public static class GdPoisBean {
        /**
         * poiList : [{"id":"B0FFHOF64P","name":"肯德基(宝泰餐厅)","pname":"吉林省","cityname":"四平市","adname":"铁西区","address":"地直街英雄大路2095号1层101","location":"124.34543894929625,43.173521975992585","type":"餐饮服务;快餐厅;肯德基"},{"id":"B0FFH0NFFP","name":"肯德基(万达店)","pname":"吉林省","cityname":"四平市","adname":"铁东区","address":"南九经街与紫气大路交汇处东北50米四平万达广场附近","location":"124.3852304319198,43.1530273681771","type":"餐饮服务;餐饮相关场所;餐饮相关"},{"id":"B0FFGW8IOM","name":"肯德基(伊通大街)","pname":"吉林省","cityname":"四平市","adname":"伊通满族自治县","address":"伊通大街北50米","location":"125.29881541661813,43.332813228433395","type":"餐饮服务;快餐厅;肯德基"},{"id":"B0FFFFYAPF","name":"肯德基(辽河大路店)","pname":"吉林省","cityname":"四平市","adname":"双辽市","address":"辽河路3399号国商百货1层","location":"123.4809812367444,43.51599073774858","type":"餐饮服务;快餐厅;肯德基"},{"id":"B01B2010BR","name":"肯德基(四平站店)","pname":"吉林省","cityname":"四平市","adname":"铁西区","address":"英雄大路四平站南侧一楼","location":"124.36640406705412,43.16504922886244","type":"餐饮服务;快餐厅;肯德基"},{"id":"B01B200WOV","name":"肯德基(中央路店)","pname":"吉林省","cityname":"四平市","adname":"铁东区","address":"中央东路欧亚购物中心楼下","location":"124.38127006328529,43.16609643717282","type":"餐饮服务;快餐厅;肯德基"},{"id":"B01B200AB9","name":"肯德基(英雄店)","pname":"吉林省","cityname":"四平市","adname":"铁西区","address":"英雄大街华展新天地1层","location":"124.36351122885254,43.16700229324728","type":"餐饮服务;快餐厅;肯德基"}]
         * poiNum : 7
         */

        private int poiNum;
        private List<PoiListBean> poiList;

        public int getPoiNum() {
            return poiNum;
        }

        public void setPoiNum(int poiNum) {
            this.poiNum = poiNum;
        }

        public List<PoiListBean> getPoiList() {
            return poiList;
        }

        public void setPoiList(List<PoiListBean> poiList) {
            this.poiList = poiList;
        }

        public static class PoiListBean {
            /**
             * id : B0FFHOF64P
             * name : 肯德基(宝泰餐厅)
             * pname : 吉林省
             * cityname : 四平市
             * adname : 铁西区
             * address : 地直街英雄大路2095号1层101
             * location : 124.34543894929625,43.173521975992585
             * type : 餐饮服务;快餐厅;肯德基
             */

            private String id;
            private String name;
            private String pname;
            private String cityname;
            private String adname;
            private Object address;
            private String location;
            private String type;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPname() {
                return pname;
            }

            public void setPname(String pname) {
                this.pname = pname;
            }

            public String getCityname() {
                return cityname;
            }

            public void setCityname(String cityname) {
                this.cityname = cityname;
            }

            public String getAdname() {
                return adname;
            }

            public void setAdname(String adname) {
                this.adname = adname;
            }

            public Object getAddress() {
                return address;
            }

            public void setAddress(Object address) {
                this.address = address;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }

    public static class ImagesBean {
        /**
         * imageList : []
         * imageNum : 0
         */

        private int imageNum;
        private List<?> imageList;

        public int getImageNum() {
            return imageNum;
        }

        public void setImageNum(int imageNum) {
            this.imageNum = imageNum;
        }

        public List<?> getImageList() {
            return imageList;
        }

        public void setImageList(List<?> imageList) {
            this.imageList = imageList;
        }
    }

    public static class VideosBean {
        /**
         * videoNum : 0
         * videoList : []
         */

        private int videoNum;
        private List<?> videoList;

        public int getVideoNum() {
            return videoNum;
        }

        public void setVideoNum(int videoNum) {
            this.videoNum = videoNum;
        }

        public List<?> getVideoList() {
            return videoList;
        }

        public void setVideoList(List<?> videoList) {
            this.videoList = videoList;
        }
    }

    public static class ThematicsBean {
        /**
         * thematicList : [{"thematicId":116,"name":"长春20160528","thematicArea":6332.23,"img":"http://202.111.178.10:10083/webhdfs/v1/Thumbnail-KEY/长春20160528专题.jpg?op=OPEN&user.name=hadoop"},{"thematicId":411,"name":"长春20160605","thematicArea":6332.23,"img":"http://202.111.178.10:10083/webhdfs/v1/Thumbnail-KEY/长春20160605专题.jpg?op=OPEN&user.name=hadoop"}]
         * thematicNum : 7
         */

        private int thematicNum;
        private List<ThematicListBean> thematicList;

        public int getThematicNum() {
            return thematicNum;
        }

        public void setThematicNum(int thematicNum) {
            this.thematicNum = thematicNum;
        }

        public List<ThematicListBean> getThematicList() {
            return thematicList;
        }

        public void setThematicList(List<ThematicListBean> thematicList) {
            this.thematicList = thematicList;
        }

        public static class ThematicListBean {
            /**
             * thematicId : 116
             * name : 长春20160528
             * thematicArea : 6332.23
             * img : http://202.111.178.10:10083/webhdfs/v1/Thumbnail-KEY/长春20160528专题.jpg?op=OPEN&user.name=hadoop
             */

            private int thematicId;
            private String name;
            private double thematicArea;
            private String img;

            public int getThematicId() {
                return thematicId;
            }

            public void setThematicId(int thematicId) {
                this.thematicId = thematicId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public double getThematicArea() {
                return thematicArea;
            }

            public void setThematicArea(double thematicArea) {
                this.thematicArea = thematicArea;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }
        }
    }

    public static class ToolsBean {
        /**
         * toolNum : 0
         * toolList : []
         */

        private int toolNum;
        private List<?> toolList;

        public int getToolNum() {
            return toolNum;
        }

        public void setToolNum(int toolNum) {
            this.toolNum = toolNum;
        }

        public List<?> getToolList() {
            return toolList;
        }

        public void setToolList(List<?> toolList) {
            this.toolList = toolList;
        }
    }
}
