package com.example.emall_ec.main.sign.data;

/**
 * Created by lixiang on 2018/3/30.
 */

public class CheckMessageBean {

    /**
     * userInfo : {"userId":92209140003462,"username":"lixxiang","level":1,"label":null,"userTelephone":"13331749289","userPassword":"3564c8cb39b7a25967923d6a6278600b","imagePath":null,"gmtCreated":null}
     * meta : success
     * register : 1
     */

    private UserInfoBean userInfo;
    private String meta;
    private String register;

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public static class UserInfoBean {
        /**
         * userId : 92209140003462
         * username : lixxiang
         * level : 1
         * label : null
         * userTelephone : 13331749289
         * userPassword : 3564c8cb39b7a25967923d6a6278600b
         * imagePath : null
         * gmtCreated : null
         */

        private long userId;
        private String username;
        private int level;
        private Object label;
        private String userTelephone;
        private String userPassword;
        private Object imagePath;
        private Object gmtCreated;

        public long getUserId() {
            return userId;
        }

        public void setUserId(long userId) {
            this.userId = userId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public Object getLabel() {
            return label;
        }

        public void setLabel(Object label) {
            this.label = label;
        }

        public String getUserTelephone() {
            return userTelephone;
        }

        public void setUserTelephone(String userTelephone) {
            this.userTelephone = userTelephone;
        }

        public String getUserPassword() {
            return userPassword;
        }

        public void setUserPassword(String userPassword) {
            this.userPassword = userPassword;
        }

        public Object getImagePath() {
            return imagePath;
        }

        public void setImagePath(Object imagePath) {
            this.imagePath = imagePath;
        }

        public Object getGmtCreated() {
            return gmtCreated;
        }

        public void setGmtCreated(Object gmtCreated) {
            this.gmtCreated = gmtCreated;
        }
    }
}
