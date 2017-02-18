package com.example.fruitpictures.bean;

import java.util.List;

/**
 * Created by hwj on 2017/2/11.
 */

public class Images {

    /**
     * error : false
     * results : [{"_id":"589d31a2421aa9270bc7332e","createdAt":"2017-02-10T11:21:06.747Z","desc":"2-10","publishedAt":"2017-02-10T11:38:22.122Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/2017-02-10-16465759_171779496648995_128281069584646144_n.jpg","used":true,"who":"代码家"},{"_id":"589bb252421aa92dc0dfd3bf","createdAt":"2017-02-09T08:05:38.361Z","desc":"2-9","publishedAt":"2017-02-09T11:46:26.70Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/2017-02-09-16583339_172818256542563_353855393375453184_n.jpg","used":true,"who":"daimajia"},{"_id":"589a7161421aa92db8a0041b","createdAt":"2017-02-08T09:16:17.678Z","desc":"2-8","publishedAt":"2017-02-08T11:39:51.371Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/2017-02-08-16230686_191036801373876_4789664128824246272_n.jpg","used":true,"who":"daimajia"},{"_id":"58993f2c421aa970b84523ab","createdAt":"2017-02-07T11:29:48.689Z","desc":"2-7","publishedAt":"2017-02-07T11:37:03.683Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/2017-02-07-032924.jpg","used":true,"who":"daimajia"},{"_id":"5897d9e9421aa970bb154891","createdAt":"2017-02-06T10:05:29.443Z","desc":"2-6","publishedAt":"2017-02-06T11:36:12.36Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/2017-02-06-16464762_1304611439576933_4803269855073533952_n.jpg","used":true,"who":"代码家"},{"_id":"58947b15421aa970bb154878","createdAt":"2017-02-03T20:44:05.311Z","desc":"2-4","publishedAt":"2017-02-04T11:47:42.336Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/2017-02-03-123209.jpg","used":true,"who":"代码家"},{"_id":"5893f544421aa90e69b17f8c","createdAt":"2017-02-03T11:13:08.967Z","desc":"2-3","publishedAt":"2017-02-03T11:52:22.806Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/16123958_1630476787257847_7576387494862651392_n.jpg","used":true,"who":"daimajia"},{"_id":"58857746421aa95eae2d92b3","createdAt":"2017-01-23T11:23:50.64Z","desc":"1-23","publishedAt":"2017-01-23T11:35:32.450Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/16124047_121657248344062_4191645221970247680_n.jpg","used":true,"who":"代码家"},{"_id":"58841c08421aa95ea9de7a0c","createdAt":"2017-01-22T10:42:16.648Z","desc":"1-22","publishedAt":"2017-01-22T11:39:29.779Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/16229501_482786908558452_6858241750058663936_n.jpg","used":true,"who":"daimajia"},{"_id":"58817e1e421aa9119735ac5f","createdAt":"2017-01-20T11:03:58.727Z","desc":"1-20","publishedAt":"2017-01-20T11:50:52.750Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/2017-01-20-030332.jpg","used":true,"who":"daimajia"}]
     */

    private boolean error;
    private List<ResultsBean> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * _id : 589d31a2421aa9270bc7332e
         * createdAt : 2017-02-10T11:21:06.747Z
         * desc : 2-10
         * publishedAt : 2017-02-10T11:38:22.122Z
         * source : chrome
         * type : 福利
         * url : http://7xi8d6.com1.z0.glb.clouddn.com/2017-02-10-16465759_171779496648995_128281069584646144_n.jpg
         * used : true
         * who : 代码家
         */

        private String _id;
        private String createdAt;
        private String desc;
        private String publishedAt;
        private String source;
        private String type;
        private String url;
        private boolean used;
        private String who;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPublishedAt() {
            return publishedAt;
        }

        public void setPublishedAt(String publishedAt) {
            this.publishedAt = publishedAt;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public boolean isUsed() {
            return used;
        }

        public void setUsed(boolean used) {
            this.used = used;
        }

        public String getWho() {
            return who;
        }

        public void setWho(String who) {
            this.who = who;
        }

        @Override
        public String toString() {
            return "ResultsBean{" +
                    "_id='" + _id + '\'' +
                    ", createdAt='" + createdAt + '\'' +
                    ", desc='" + desc + '\'' +
                    ", publishedAt='" + publishedAt + '\'' +
                    ", source='" + source + '\'' +
                    ", type='" + type + '\'' +
                    ", url='" + url + '\'' +
                    ", used=" + used +
                    ", who='" + who + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Images{" +
                "error=" + error +
                ", results=" + results +
                '}';
    }
}
