package me.mundane.parallaxrecyclerview.model;

import java.util.List;

/**
 * Created by mundane on 2018/1/25 下午3:25
 */

public class LofterDataResult {

    public MetaBean meta;
    public ResponseBean response;

    public static class MetaBean {
        public int status;
        public String msg;
    }

    public static class ResponseBean {
        public List<ListBean> list;

        public static class ListBean {
            public String unid;
            public String id;
            public int genType;
            public DataBean data;

            public static class DataBean {
                public int id;
                public String link;
                public int type;
                public int isTop;
                public String sign;
                public String mark;
                public String permalink;
                public String blogNickName;
                public String bigAvaImg;
                public PostBean post;

                public static class PostBean {
                    public String permalink;
                    public String image;
                    public String link;
                    public String content;
                    public String title;
                    public int type;
                    public int hot;
                    public ExtBean ext;
                    public List<Integer> wh;

                    public static class ExtBean {
                        public int imageNum;
                    }
                }
            }
        }
    }
}
