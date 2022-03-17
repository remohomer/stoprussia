package pl.com.stoprussia.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

import pl.com.stoprussia.core.ActionBarSettings;
import pl.com.stoprussia.core.websiteloader.EnhancedUrl;
import pl.com.stoprussia.core.websiteloader.UrlType;
import pl.com.stoprussia.core.websiteloader.WebsiteLoader;
import pl.com.stoprussia.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class WebViewActivity extends BaseActivity {

    protected final Map<UrlType, ArrayList<EnhancedUrl>> mapOfInsideUrls = new HashMap<>();

    protected static int DELAY = 500;

    protected View whiteBackground;

    protected WebView webView;
    protected ProgressBar progressBar;
    protected String websiteUrl;

    protected WebsiteLoader websiteLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBarSettings.show(this);

        mapOfInsideUrls.put(UrlType.REDIRECTION_TO_MAIN_ACTIVITY, new ArrayList<>());
        mapOfInsideUrls.put(UrlType.DOMAIN_TO_CURRENT_WEBVIEW, new ArrayList<>());
        mapOfInsideUrls.put(UrlType.DOMAIN_TO_ANOTHER_WEBVIEW, new ArrayList<>());
        mapOfInsideUrls.put(UrlType.DOMAIN_TO_OUTSIDE_APP, new ArrayList<>());
        mapOfInsideUrls.put(UrlType.URL_TO_CURRENT_WEBVIEW, new ArrayList<>());
        mapOfInsideUrls.put(UrlType.URL_TO_ANOTHER_WEBVIEW, new ArrayList<>());
        mapOfInsideUrls.put(UrlType.URL_TO_OUTSIDE_APP, new ArrayList<>());
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
