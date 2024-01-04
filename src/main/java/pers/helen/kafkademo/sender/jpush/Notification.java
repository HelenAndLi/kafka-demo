package pers.helen.kafkademo.sender.jpush;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class Notification {

    private String alert;

    private Android android;

    private Ios ios;

    @Data
    public static final class Ios implements Serializable {

        private static final long serialVersionUID = -7496767931099438858L;

        private Alert alert;

        private Integer badge;

        @JsonProperty("content-available")
        private Boolean contentAvailable;

        private String category;

        private Map<String, Object> extras;

        @Data
        public static final class Sound {

            private Integer critical;

            private String name;

            private Double volume;
        }

        @Data
        public static final class Alert implements Serializable {

            private static final long serialVersionUID = 5488170878714419880L;

            private String title;

            private String body;
        }
    }

    @Data
    public static final class Android implements Serializable {

        private static final long serialVersionUID = 1715271739526334623L;

        /**
         * 通知内容，必填
         */
        private String alert;

        /**
         * 通知标题，可选
         */
        private String title;

        private String category;

        private Integer style;

        private Map<String, Object> inbox;

        private String big_pic_path;

        private Map<String, Object> extras;

        private String large_icon;
    }
}
