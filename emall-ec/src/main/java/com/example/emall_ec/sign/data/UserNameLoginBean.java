package com.example.emall_ec.sign.data;

/**
 * Created by lixiang on 2018/4/2.
 */

public class UserNameLoginBean {

    /**
     * meta : success
     * user : {"userId":92209410004772,"username":"王昊","level":1,"label":"1","userTelephone":null,"userPassword":null,"imagePath":null,"gmtCreated":null}
     */

    private String meta;
    private UserBean user;

    public String getMeta() {
        return meta;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean {
        /**
         * userId : 92209410004772
         * username : 王昊
         * level : 1
         * label : 1
         * userTelephone : null
         * userPassword : null
         * imagePath : null
         * gmtCreated : null
         */

        private long userId;
        private String username;
        private int level;
        private String label;
        private Object userTelephone;
        private Object userPassword;
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

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public Object getUserTelephone() {
            return userTelephone;
        }

        public void setUserTelephone(Object userTelephone) {
            this.userTelephone = userTelephone;
        }

        public Object getUserPassword() {
            return userPassword;
        }

        public void setUserPassword(Object userPassword) {
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
