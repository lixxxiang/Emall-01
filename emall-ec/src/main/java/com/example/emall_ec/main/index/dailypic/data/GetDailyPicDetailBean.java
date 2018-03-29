package com.example.emall_ec.main.index.dailypic.data;

/**
 * Created by lixiang on 2018/3/29.
 */

public class GetDailyPicDetailBean {

    /**
     * data : {"image1FilePath":"http://202.111.178.10:28085/upload/image/201711231236000028904_image.jpg","image2FilePath":"http://202.111.178.10:28085/upload/image/201711231236000116005_image.jpg","image3FilePath":"http://202.111.178.10:28085/upload/image/201711231236000393384_image.jpg","imageDate":"2017-11-18","imageId":"17112312370001242","imageName":"【每日一图】旅游百事系列之\u201c观光全球最大的海洋度假区\u201d","latitude":"22.10","longitude":"113.54","richText1":"珠海横琴长隆国际海洋度假区\n珠海横琴长隆国际海洋度假区地处与澳门近在咫尺的中国国家级开放新区\u2014\u2014横琴新区，是一个世界超大型的综合主题旅游度假区。","richText2":"长隆海洋王国设有海豚剧场、海豚湾、海狮港湾、企鹅馆、鲸鲨馆白鲸馆、鳐鱼池等场馆，馆内饲养有不同品种的珍奇鱼类多达15000条，是整个海洋度假区世界领先实力的一个惟妙惟肖的缩影。","richText3":"长隆企鹅酒店是极具特色的企鹅主题酒店，紧邻海洋王国入口，拥有2000间温馨舒适的客房，5间风格鲜明的特色主题餐厅，以及大型美食广场和商业街，是家人、朋友休闲娱乐的最佳场所。\n长隆横琴湾酒店坐落于海洋度假区的中心位置，总建筑面积达30万平方米，拥有1888间的豪华客房，是全国规模最大的海洋生态主题酒店。\n长隆国际马戏城占地面积500万平方米，是专业复合型的国际马戏场馆，有着超强的科技感，受到各国马戏队伍的高度肯定。"}
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
         * image1FilePath : http://202.111.178.10:28085/upload/image/201711231236000028904_image.jpg
         * image2FilePath : http://202.111.178.10:28085/upload/image/201711231236000116005_image.jpg
         * image3FilePath : http://202.111.178.10:28085/upload/image/201711231236000393384_image.jpg
         * imageDate : 2017-11-18
         * imageId : 17112312370001242
         * imageName : 【每日一图】旅游百事系列之“观光全球最大的海洋度假区”
         * latitude : 22.10
         * longitude : 113.54
         * richText1 : 珠海横琴长隆国际海洋度假区
         珠海横琴长隆国际海洋度假区地处与澳门近在咫尺的中国国家级开放新区——横琴新区，是一个世界超大型的综合主题旅游度假区。
         * richText2 : 长隆海洋王国设有海豚剧场、海豚湾、海狮港湾、企鹅馆、鲸鲨馆白鲸馆、鳐鱼池等场馆，馆内饲养有不同品种的珍奇鱼类多达15000条，是整个海洋度假区世界领先实力的一个惟妙惟肖的缩影。
         * richText3 : 长隆企鹅酒店是极具特色的企鹅主题酒店，紧邻海洋王国入口，拥有2000间温馨舒适的客房，5间风格鲜明的特色主题餐厅，以及大型美食广场和商业街，是家人、朋友休闲娱乐的最佳场所。
         长隆横琴湾酒店坐落于海洋度假区的中心位置，总建筑面积达30万平方米，拥有1888间的豪华客房，是全国规模最大的海洋生态主题酒店。
         长隆国际马戏城占地面积500万平方米，是专业复合型的国际马戏场馆，有着超强的科技感，受到各国马戏队伍的高度肯定。
         */

        private String image1FilePath;
        private String image2FilePath;
        private String image3FilePath;
        private String imageDate;
        private String imageId;
        private String imageName;
        private String latitude;
        private String longitude;
        private String richText1;
        private String richText2;
        private String richText3;

        public String getImage1FilePath() {
            return image1FilePath;
        }

        public void setImage1FilePath(String image1FilePath) {
            this.image1FilePath = image1FilePath;
        }

        public String getImage2FilePath() {
            return image2FilePath;
        }

        public void setImage2FilePath(String image2FilePath) {
            this.image2FilePath = image2FilePath;
        }

        public String getImage3FilePath() {
            return image3FilePath;
        }

        public void setImage3FilePath(String image3FilePath) {
            this.image3FilePath = image3FilePath;
        }

        public String getImageDate() {
            return imageDate;
        }

        public void setImageDate(String imageDate) {
            this.imageDate = imageDate;
        }

        public String getImageId() {
            return imageId;
        }

        public void setImageId(String imageId) {
            this.imageId = imageId;
        }

        public String getImageName() {
            return imageName;
        }

        public void setImageName(String imageName) {
            this.imageName = imageName;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getRichText1() {
            return richText1;
        }

        public void setRichText1(String richText1) {
            this.richText1 = richText1;
        }

        public String getRichText2() {
            return richText2;
        }

        public void setRichText2(String richText2) {
            this.richText2 = richText2;
        }

        public String getRichText3() {
            return richText3;
        }

        public void setRichText3(String richText3) {
            this.richText3 = richText3;
        }
    }
}
