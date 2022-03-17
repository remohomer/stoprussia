package pl.com.stoprussia.core.websiteloader;

import android.app.Activity;

public class EnhancedUrl {

    private final String url;

    private final Activity activity;

    private final int botNavItem;
    private final int pagerItem;


    public static class Builder {
        private final String url;

        private Activity activity;

        private int botNavItem;
        private int pagerItem;


        public Builder(String url) {
            this.url = url;
            botNavItem = 0;
            pagerItem = 0;
            activity = null;
        }

        public Builder activityDestiny(Activity activity) {
            this.activity = activity;
            return this;
        }

        public Builder navItemDestiny(int botNavItem) {
            this.botNavItem = botNavItem;
            return this;
        }

        public Builder pagerItemDestiny(int pagerItem) {
            this.pagerItem = pagerItem;
            return this;
        }

        public EnhancedUrl build() {
            return new EnhancedUrl(this);
        }
    }

    public EnhancedUrl(Builder builder) {
        url = builder.url;
        botNavItem = builder.botNavItem;
        pagerItem = builder.pagerItem;
        activity = builder.activity;
    }

    public static String trimUrl(String url) {
        String tempUrl = url;
        if (tempUrl.startsWith("http://www.")) {
            tempUrl = tempUrl.substring(11);
        } else if (tempUrl.startsWith("https://www.")) {
            tempUrl = tempUrl.substring(12);
        } else if (tempUrl.startsWith("http://")) {
            tempUrl = tempUrl.substring(7);
        } else if (tempUrl.startsWith("https://")) {
            tempUrl = tempUrl.substring(8);
        } else if (tempUrl.startsWith("www.")) {
            tempUrl = tempUrl.substring(4);
        }
        if (tempUrl.endsWith("/")) {
            tempUrl = tempUrl.substring(0, tempUrl.length() - 1);
        }
        return tempUrl;
    }

    // -- getters

    public String getUrl() {
        return url;
    }

    public int getBotNavItem() {
        return botNavItem;
    }

    public int getPagerItem() {
        return pagerItem;
    }

    public Activity getActivity() {
        return activity;
    }
}
