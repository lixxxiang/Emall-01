package com.example.emall_ec.main.index.dailypic.data;

import java.util.List;

/**
 * Created by lixiang on 2018/3/29.
 */

public class GetArticleAttachBean {

    /**
     * data : {"comments":[{"commentTime":"2017-12-05 10:51:09","content":"啊啊卡","userId":"22","userName":"lixxiang"},{"commentTime":"2017-12-05 10:50:50","content":"乌苏手机手机卡","userId":"22","userName":"lixxiang"},{"commentTime":"2017-12-05 10:50:43","content":"乌苏","userId":"22","userName":"lixxiang"},{"commentTime":"2017-12-05 08:41:51","content":"杉杉南泥湾","userId":"22","userName":"lixxiang"}],"upvoteMark":0,"commentAmount":996,"collectionMark":1,"upvoteAmount":3996}
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
         * comments : [{"commentTime":"2017-12-05 10:51:09","content":"啊啊卡","userId":"22","userName":"lixxiang"},{"commentTime":"2017-12-05 10:50:50","content":"乌苏手机手机卡","userId":"22","userName":"lixxiang"},{"commentTime":"2017-12-05 10:50:43","content":"乌苏","userId":"22","userName":"lixxiang"},{"commentTime":"2017-12-05 08:41:51","content":"杉杉南泥湾","userId":"22","userName":"lixxiang"}]
         * upvoteMark : 0
         * commentAmount : 996
         * collectionMark : 1
         * upvoteAmount : 3996
         */

        private int upvoteMark;
        private int commentAmount;
        private int collectionMark;
        private int upvoteAmount;
        private List<CommentsBean> comments;

        public int getUpvoteMark() {
            return upvoteMark;
        }

        public void setUpvoteMark(int upvoteMark) {
            this.upvoteMark = upvoteMark;
        }

        public int getCommentAmount() {
            return commentAmount;
        }

        public void setCommentAmount(int commentAmount) {
            this.commentAmount = commentAmount;
        }

        public int getCollectionMark() {
            return collectionMark;
        }

        public void setCollectionMark(int collectionMark) {
            this.collectionMark = collectionMark;
        }

        public int getUpvoteAmount() {
            return upvoteAmount;
        }

        public void setUpvoteAmount(int upvoteAmount) {
            this.upvoteAmount = upvoteAmount;
        }

        public List<CommentsBean> getComments() {
            return comments;
        }

        public void setComments(List<CommentsBean> comments) {
            this.comments = comments;
        }

        public static class CommentsBean {
            /**
             * commentTime : 2017-12-05 10:51:09
             * content : 啊啊卡
             * userId : 22
             * userName : lixxiang
             */

            private String commentTime;
            private String content;
            private String userId;
            private String userName;

            public String getCommentTime() {
                return commentTime;
            }

            public void setCommentTime(String commentTime) {
                this.commentTime = commentTime;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }
        }
    }
}
