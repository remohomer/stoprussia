package pl.com.stoprussia.core.fragment;


import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

import androidx.constraintlayout.widget.ConstraintLayout;

import pl.com.stoprussia.MainActivity;
import pl.com.stoprussia.R;
import pl.com.stoprussia.core.websiteloader.EnhancedUrl;
import pl.com.stoprussia.core.websiteloader.UrlType;
import pl.com.stoprussia.core.websiteloader.WebsiteLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public abstract class WebViewFragment extends BaseFragment {

    protected final Map<UrlType, ArrayList<EnhancedUrl>> mapOfInsideUrls = new HashMap<>();

    protected static int DELAY = 500;

    protected View whiteBackground;

    protected View rootView;
    protected WebView webView;
    protected ProgressBar progressBar;
    protected WebsiteLoader websiteLoader;

    protected String websiteUrl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setThreadPolicy();
        mapOfInsideUrls.put(UrlType.REDIRECTION_TO_MAIN_ACTIVITY, new ArrayList<>());
        mapOfInsideUrls.put(UrlType.DOMAIN_TO_CURRENT_WEBVIEW, new ArrayList<>());
        mapOfInsideUrls.put(UrlType.DOMAIN_TO_ANOTHER_WEBVIEW, new ArrayList<>());
        mapOfInsideUrls.put(UrlType.DOMAIN_TO_OUTSIDE_APP, new ArrayList<>());
        mapOfInsideUrls.put(UrlType.URL_TO_CURRENT_WEBVIEW, new ArrayList<>());
        mapOfInsideUrls.put(UrlType.URL_TO_ANOTHER_WEBVIEW, new ArrayList<>());
        mapOfInsideUrls.put(UrlType.URL_TO_OUTSIDE_APP, new ArrayList<>());
    }

    public View getRootView() {
        return rootView;
    }

    public WebView getWebView() {
        return webView;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public View getWhiteBackground() {
        return whiteBackground;
    }

    public Map<UrlType, ArrayList<EnhancedUrl>> getMapOfInsideUrls() {
        return mapOfInsideUrls;
    }

}
