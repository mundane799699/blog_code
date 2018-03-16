package me.mundane.retrofitanalysis.model;

import java.util.List;

/**
 * Created by mundane on 2018/3/12 下午4:32
 */

public class MeizhiModel {

    public boolean error;
    public List<ResultsBean> results;

    public static class ResultsBean {
        public String _id;
        public String createdAt;
        public String desc;
        public String publishedAt;
        public String source;
        public String type;
        public String url;
        public boolean used;
        public String who;

        @Override
        public String toString() {
            return "ResultsBean{"
                    + "_id='"
                    + _id
                    + '\''
                    + ", createdAt='"
                    + createdAt
                    + '\''
                    + ", desc='"
                    + desc
                    + '\''
                    + ", publishedAt='"
                    + publishedAt
                    + '\''
                    + ", source='"
                    + source
                    + '\''
                    + ", type='"
                    + type
                    + '\''
                    + ", url='"
                    + url
                    + '\''
                    + ", used="
                    + used
                    + ", who='"
                    + who
                    + '\''
                    + '}';
        }
    }

    @Override
    public String toString() {
        return "MeizhiModel{" + "error=" + error + ", results=" + results + '}';
    }
}
