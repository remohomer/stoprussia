package pl.com.stoprussia.core.websiteloader;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import pl.com.stoprussia.core.activity.WebViewActivity;
import pl.com.stoprussia.core.fragment.ViewPagerFragment;
import pl.com.stoprussia.core.fragment.WebViewFragment;
import pl.com.stoprussia.MainActivity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

public class WebsiteLoader {

    private final int RETURN_FALSE = 0;
    private final int RETURN_TRUE = 1;
    private final int CONTINUE = 2;

    private int priorityOfRedirectionToMainActivity = 5;
    private int priorityOfUrlToOutSideApp = 5;
    private int priorityOfUrlToAnotherWebView = 5;
    private int priorityOfUrlToCurrentWebView = 5;
    private int priorityOfDomainToAnotherWebView = 5;
    private int priorityOfDomainToCurrentWebView = 5;

    private final View whiteBackground;

    private static String customWebsiteUrl;
    private static boolean internetOffDialogCanBeShow;
    public static boolean canLoadLoginActivityFlag = true;

    private final Fragment fragment;
    private final Activity activity;
    private final Map<UrlType, ArrayList<EnhancedUrl>> mapOfUrls;

    private final WebView webView;
    private final String websiteUrl;
    private final ProgressBar progressBar;

    private final String pathToCssFile;
    private final String javascript;
    private final String className;
    private final String idName;

    private final boolean subDomains;
    private final int delay;

    public static class Builder {

        private WebViewFragment fragment;
        private Activity activity;
        private FragmentManager fragmentManager;
        private final Map<UrlType, ArrayList<EnhancedUrl>> mapOfUrls;
        private final WebView webView;
        private final String websiteUrl;
        private final ProgressBar progressBar;
        private final View whiteBackground;

        private String pathToCssFile = "";
        private String javascript = "";
        private String className = "";
        private String idName = "";

        private boolean subDomains;

        private int delay;

        public Builder(Fragment fragment) {
            this.fragment = (WebViewFragment)fragment;
            this.activity = ((WebViewFragment) fragment).getActivity();
            this.fragmentManager = ((WebViewFragment) fragment).getFragmentManager();
            this.webView = ((WebViewFragment) fragment).getWebView();
            this.progressBar = ((WebViewFragment) fragment).getProgressBar();
            this.websiteUrl = ((WebViewFragment) fragment).getWebsiteUrl();
            this.mapOfUrls = ((WebViewFragment) fragment).getMapOfInsideUrls();

            this.whiteBackground = ((WebViewFragment) fragment).getWhiteBackground();

            subDomains = true;
            internetOffDialogCanBeShow = true;
            delay = 0;
        }

        public Builder(Activity activity) {
            this.activity = activity;
            this.webView = ((WebViewActivity) activity).getWebView();
            this.progressBar = ((WebViewActivity) activity).getProgressBar();
            this.websiteUrl = ((WebViewActivity) activity).getWebsiteUrl();
            this.mapOfUrls = ((WebViewActivity) activity).getMapOfInsideUrls();
            this.whiteBackground = ((WebViewActivity) activity).getWhiteBackground();


            subDomains = true;
            internetOffDialogCanBeShow = true;
            delay = 0;
        }

        public Builder injectCss(String filename) {
            pathToCssFile = filename;
            return this;
        }

        public Builder injectJavaScript(String script) {
            javascript = script;
            return this;
        }

        public Builder hideSomeClassInCurrentHtml(String className) {
            this.className = className;
            return this;
        }

        public Builder hideSomeIdInCurrentHtml(String idName) {
            this.idName = idName;
            return this;
        }

        public Builder disableSubDomains() {
            subDomains = false;
            return this;
        }

        public Builder addLoadDelay(int delay) {
            this.delay = delay;
            return this;
        }

        public WebsiteLoader build() {
            return new WebsiteLoader(this);
        }
    }

    public WebsiteLoader(Builder builder) {
        FragmentManager fragmentManager = builder.fragmentManager;

        whiteBackground = builder.whiteBackground;

        fragment = builder.fragment;
        activity = builder.activity;
        mapOfUrls = builder.mapOfUrls;
        webView = builder.webView;
        websiteUrl = builder.websiteUrl;
        progressBar = builder.progressBar;

        pathToCssFile = builder.pathToCssFile;
        javascript = builder.javascript;
        className = builder.className;
        idName = builder.idName;

        subDomains = builder.subDomains;
        delay = builder.delay;
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void load() {

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (progressBar != null) {
                    progressBar.setVisibility(View.VISIBLE);
                }

                if (delay != 0) {
                    whiteBackground.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // hide some useless footers
                if (!pathToCssFile.equals("")) {
                    injectCSS(pathToCssFile);
                }
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }

                if (delay != 0) {
                    new Handler().postDelayed(() -> whiteBackground.setVisibility(View.GONE), delay);
                }
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {

                if (!InternetStatus.isOfflineMode()) {
                    InternetStatus internetStatus = new InternetStatus(activity);
                    if (!internetStatus.internetIsConnected() && internetOffDialogCanBeShow) {
                        view.setVisibility(View.INVISIBLE);
                        InternetOffAlertDialog internetOffAlertDialog = new InternetOffAlertDialog(activity);

                        // flag to prevent to open more then one dialog window (because we have more then one WebView on MainActivity at the same time)
                        internetOffDialogCanBeShow = false;
                    }
                }
            }

            // ---------- instructions for inside links ----------
            public boolean shouldOverrideUrlLoading(WebView view, String insideUrl) {

                if (insideUrl.startsWith("tel:")) {
                    Intent tel = new Intent(Intent.ACTION_DIAL, Uri.parse(insideUrl));
                    activity.startActivity(tel);
                    return true;
                }

                if (insideUrl.contains("mailto:")) {
                    view.getContext().startActivity(
                            new Intent(Intent.ACTION_VIEW, Uri.parse(insideUrl)));
                    return true;
                }

                if (subDomains(insideUrl) == RETURN_TRUE) {
                    return true;
                } else if (subDomains(insideUrl) == RETURN_FALSE) {
                    return false;
                }


                for (int i = 1; i <= 10; i++) {

                    if (priorityOfRedirectionToMainActivity == i) {
                        if (redirectionToMainActivity(insideUrl) == RETURN_TRUE) {
                            return true;
                        } else if (redirectionToMainActivity(insideUrl) == RETURN_FALSE) {
                            return false;
                        }
                    }

                    if (priorityOfUrlToOutSideApp == i) {
                        if (urlToOutSideApp(insideUrl) == RETURN_TRUE) {
                            return true;
                        } else if (urlToOutSideApp(insideUrl) == RETURN_FALSE) {
                            return false;
                        }
                    }

                    if (priorityOfUrlToAnotherWebView == i) {
                        if (urlToAnotherWebView(insideUrl) == RETURN_TRUE) {
                            return true;
                        } else if (urlToAnotherWebView(insideUrl) == RETURN_FALSE) {
                            return false;
                        }
                    }

                    if (priorityOfUrlToCurrentWebView == i) {
                        if (urlToCurrentWebView(insideUrl) == RETURN_TRUE) {
                            return true;
                        } else if (urlToCurrentWebView(insideUrl) == RETURN_FALSE) {
                            return false;
                        }
                    }

                    if (priorityOfDomainToAnotherWebView == i) {
                        if (domainToAnotherWebView(insideUrl) == RETURN_TRUE) {
                            return true;
                        } else if (domainToAnotherWebView(insideUrl) == RETURN_FALSE) {
                            return false;
                        }
                    }

                    if (priorityOfDomainToCurrentWebView == i) {
                        if (domainToCurrentWebView(insideUrl) == RETURN_TRUE) {
                            return true;
                        } else if (domainToCurrentWebView(insideUrl) == RETURN_FALSE) {
                            return false;
                        }
                    }
                }
                return defaultAction(insideUrl) == RETURN_TRUE;
            }
        });

        webView.setWebChromeClient(new MyChrome());

        WebSettings webSettings = webView.getSettings();
        initWebSettings(webSettings);

        if (customWebsiteUrl == null) {
            webView.loadUrl(websiteUrl);
        } else {
            webView.loadUrl(getCustomWebsiteUrl());
            setCustomWebsiteUrl(null);
        }

        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_BACK: {
                            if (webView.canGoBack()) {
                                webView.goBack();

                                return true;
                            }
                            break;
                        }
                    }
                }
                return false;
            }
        });

    }

    private void initWebSettings(WebSettings webSettings) {
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setDomStorageEnabled(true);

    }

    private int defaultAction(String insideUrl) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(insideUrl));
        activity.startActivity(intent);
        return RETURN_TRUE;
    }

    private int redirectionToMainActivity(String insideUrl) {
        if (!mapOfUrls.get(UrlType.REDIRECTION_TO_MAIN_ACTIVITY).isEmpty()) {
            for (int i = 0; i < mapOfUrls.get(UrlType.REDIRECTION_TO_MAIN_ACTIVITY).size(); i++) {
                if (EnhancedUrl.trimUrl(insideUrl).contains(EnhancedUrl.trimUrl(mapOfUrls.get(UrlType.REDIRECTION_TO_MAIN_ACTIVITY).get(i).getUrl()))) {
                    Intent mainActivity = new Intent(activity.getApplicationContext(), MainActivity.class);
                    activity.startActivity(mainActivity);
                    return RETURN_FALSE;
                }
            }
        }
        return CONTINUE;
    }

    private int domainToCurrentWebView(String insideUrl) {

        if (!mapOfUrls.get(UrlType.DOMAIN_TO_CURRENT_WEBVIEW).isEmpty()) {

            for (int i = 0; i < mapOfUrls.get(UrlType.DOMAIN_TO_CURRENT_WEBVIEW).size(); i++) {
                String trimmedUrl = EnhancedUrl.trimUrl(mapOfUrls.get(UrlType.DOMAIN_TO_CURRENT_WEBVIEW).get(i).getUrl());
                if (insideUrl.contains(trimmedUrl)) {
                    return RETURN_FALSE;
                }
            }
        }

        //

        return CONTINUE;
    }

    private int domainToAnotherWebView(String insideUrl) {
        if (!mapOfUrls.get(UrlType.DOMAIN_TO_ANOTHER_WEBVIEW).isEmpty()) {
            for (int i = 0; i < mapOfUrls.get(UrlType.DOMAIN_TO_ANOTHER_WEBVIEW).size(); i++) {
                EnhancedUrl url = mapOfUrls.get(UrlType.DOMAIN_TO_ANOTHER_WEBVIEW).get(i);
                String trimmedUrl = EnhancedUrl.trimUrl(mapOfUrls.get(UrlType.DOMAIN_TO_ANOTHER_WEBVIEW).get(i).getUrl());
                if (insideUrl.contains(trimmedUrl)) {
                    if (((MainActivity) activity).getNavBarFragments().get(url.getBotNavItem()) instanceof ViewPagerFragment && url.getPagerItem() != 0) {
                        ((ViewPagerFragment) ((MainActivity) activity).getNavBarFragments()
                                .get(url.getBotNavItem()))
                                .getViewPager()
                                .setCurrentItem(url.getPagerItem());
                    }
                    if (((MainActivity) activity).getNavBarFragments().get(url.getBotNavItem()) instanceof WebViewFragment) {
                        ((WebViewFragment) ((MainActivity) activity).getNavBarFragments()
                                .get(url.getBotNavItem()))
                                .getWebView()
                                .loadUrl(insideUrl);
                    }
                    changeFragmentTo(url.getBotNavItem());

                    return RETURN_TRUE;
                }
            }
        }
        return CONTINUE;
    }

    private int urlToCurrentWebView(String insideUrl) {
        if (!mapOfUrls.get(UrlType.URL_TO_CURRENT_WEBVIEW).isEmpty()) {
            for (int i = 0; i < mapOfUrls.get(UrlType.URL_TO_CURRENT_WEBVIEW).size(); i++) {
                String TrimmedUrl = EnhancedUrl.trimUrl(mapOfUrls.get(UrlType.URL_TO_CURRENT_WEBVIEW).get(i).getUrl());
                if (insideUrl.contains(TrimmedUrl)) {
                    return RETURN_FALSE;
                }
            }
        }
        return CONTINUE;
    }

    private int urlToAnotherWebView(String insideUrl) {
        if (!mapOfUrls.get(UrlType.URL_TO_ANOTHER_WEBVIEW).isEmpty()) {
            for (int i = 0; i < mapOfUrls.get(UrlType.URL_TO_ANOTHER_WEBVIEW).size(); i++) {
                EnhancedUrl url = mapOfUrls.get(UrlType.URL_TO_ANOTHER_WEBVIEW).get(i);
                String trimmedUrl = EnhancedUrl.trimUrl(mapOfUrls.get(UrlType.URL_TO_ANOTHER_WEBVIEW).get(i).getUrl());
                if (insideUrl.contains(trimmedUrl)) {
                    if (((MainActivity) activity).getNavBarFragments().get(url.getBotNavItem()) instanceof ViewPagerFragment) {
                        ((ViewPagerFragment) ((MainActivity) activity).getNavBarFragments()
                                .get(url.getBotNavItem()))
                                .getViewPager()
                                .setCurrentItem(url.getPagerItem());
                    }
                    if (((MainActivity) activity).getNavBarFragments().get(url.getBotNavItem()) instanceof WebViewFragment) {
                        ((WebViewFragment) ((MainActivity) activity).getNavBarFragments()
                                .get(url.getBotNavItem()))
                                .getWebView()
                                .loadUrl(insideUrl);
                    }
                    changeFragmentTo(url.getBotNavItem());

                    return RETURN_TRUE;
                }
            }
        }
        return CONTINUE;
    }

    private int urlToOutSideApp(String insideUrl) {
        if (!mapOfUrls.get(UrlType.URL_TO_OUTSIDE_APP).isEmpty()) {
            for (int i = 0; i < mapOfUrls.get(UrlType.URL_TO_OUTSIDE_APP).size(); i++) {
                String trimmedUrl = EnhancedUrl.trimUrl(mapOfUrls.get(UrlType.URL_TO_OUTSIDE_APP).get(i).getUrl());
                if (insideUrl.contains(trimmedUrl)) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(insideUrl));
                    activity.startActivity(intent);
                    return RETURN_TRUE;
                }
            }
        }
        return CONTINUE;
    }

    private int subDomains(String insideUrl) {
        if (!subDomains) {
            String TrimmedUrl = EnhancedUrl.trimUrl(mapOfUrls.get(UrlType.DOMAIN_TO_CURRENT_WEBVIEW).get(0).getUrl());
            String trimmedInsideUrl = EnhancedUrl.trimUrl(insideUrl);
            if (insideUrl.contains(TrimmedUrl)
                    && !insideUrl.startsWith(TrimmedUrl)) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(insideUrl));
                activity.startActivity(intent);
                return RETURN_TRUE;
            }
        }
        return CONTINUE;
    }

    public static String getCustomWebsiteUrl() {
        return customWebsiteUrl;
    }

    public static void setCustomWebsiteUrl(String customWebsiteUrl) {
        WebsiteLoader.customWebsiteUrl = customWebsiteUrl;
    }

    public void hideSomeClassInCurrentHtml(String className) {
        webView.loadUrl("javascript:(function() { " +
                "document.getElementsByClassName('" + className + "')[0].style.display='none'; })()");
    }

    public void hideSomeIdInCurrentHtml(String id) {
        webView.loadUrl("javascript:(function() { " +
                "document.getElementsById(\"" + id + "\")[0].style.display='none'; })()");
    }

    public void injectJS(String script) {
        webView.loadUrl("javascript:(function() { " + script + " })()");
    }

    private void injectCSS(String pathToCssFile) {
        try {

            InputStream inputStream = activity.getAssets().open(pathToCssFile);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            String encoded = Base64.encodeToString(buffer, Base64.NO_WRAP);
            webView.loadUrl("javascript:(function() {" +
                    "var parent = document.getElementsByTagName('head').item(0);" +
                    "var style = document.createElement('style');" +
                    "style.type = 'text/css';" +
                    // Tell the browser to BASE64-decode the string into your script !!!
                    "style.innerHTML = window.atob('" + encoded + "');" +
                    "parent.appendChild(style)" +
                    "})()");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void changeFragmentTo(int navItemId) {
        ((MainActivity) activity).saveMenuItemHistory(navItemId);
        ((MainActivity) activity).getSupportFragmentManager()
                .beginTransaction()
                .hide(((MainActivity) activity)
                        .getActiveFragment())
                .show(((MainActivity) activity)
                        .getNavBarFragments()
                        .get(navItemId))
                .commit();
        ((MainActivity) activity).setActiveFragment(((MainActivity) activity)
                .getNavBarFragments()
                .get(navItemId));
        ((MainActivity) activity).getBottomNavigationView()
                .getMenu()
                .getItem(navItemId)
                .setChecked(true);
    }

    private class MyChrome extends WebChromeClient {

        private View mCustomView;
        private WebChromeClient.CustomViewCallback mCustomViewCallback;
        protected FrameLayout mFullscreenContainer;
        private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;

        MyChrome() {
        }

        public Bitmap getDefaultVideoPoster() {
            if (mCustomView == null) {
                return null;
            }
            return BitmapFactory.decodeResource(activity.getApplicationContext().getResources(), 2130837573);
        }

        public void onHideCustomView() {
            ((FrameLayout) activity.getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            activity.getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            activity.setRequestedOrientation(this.mOriginalOrientation);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
        }

        public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback) {
            if (this.mCustomView != null) {
                onHideCustomView();
                return;
            }
            this.mCustomView = paramView;
            this.mOriginalSystemUiVisibility = activity.getWindow().getDecorView().getSystemUiVisibility();
            this.mOriginalOrientation = activity.getRequestedOrientation();
            this.mCustomViewCallback = paramCustomViewCallback;
            ((FrameLayout) activity.getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
            activity.getWindow().getDecorView().setSystemUiVisibility(3846 | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }


    // ---------- setters and getters ----------


    public void setPriorityOfRedirectionToMainActivity(int priorityOfRedirectionToMainActivity) {
        this.priorityOfRedirectionToMainActivity = priorityOfRedirectionToMainActivity;
    }

    public void setPriorityOfUrlToOutSideApp(int priorityOfUrlToOutSideApp) {
        this.priorityOfUrlToOutSideApp = priorityOfUrlToOutSideApp;
    }

    public void setPriorityOfUrlToCurrentWebView(int priorityOfUrlToCurrentWebView) {
        this.priorityOfUrlToCurrentWebView = priorityOfUrlToCurrentWebView;
    }

    public void setPriorityOfUrlToAnotherWebView(int priorityOfUrlToAnotherWebView) {
        this.priorityOfUrlToAnotherWebView = priorityOfUrlToAnotherWebView;
    }

    public void setPriorityOfDomainToCurrentWebView(int priorityOfDomainToCurrentWebView) {
        this.priorityOfDomainToCurrentWebView = priorityOfDomainToCurrentWebView;
    }

    public void setPriorityOfDomainToAnotherWebView(int priorityOfDomainToAnotherWebView) {
        this.priorityOfDomainToAnotherWebView = priorityOfDomainToAnotherWebView;
    }

}


