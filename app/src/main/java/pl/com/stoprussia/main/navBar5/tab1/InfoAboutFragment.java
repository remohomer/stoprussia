package pl.com.stoprussia.main.navBar5.tab1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;

import pl.com.stoprussia.R;
import pl.com.stoprussia.core.fragment.WebViewFragment;
import pl.com.stoprussia.core.websiteloader.EnhancedUrl;
import pl.com.stoprussia.core.websiteloader.UrlType;
import pl.com.stoprussia.core.websiteloader.WebsiteLoader;

import java.util.Locale;

public class InfoAboutFragment extends WebViewFragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_info_about, container, false);

        webView = (WebView) rootView.findViewById(R.id.idWebViewAbout);
        progressBar = (ProgressBar) rootView.findViewById(R.id.idPBLoading);

//        if (Locale.getDefault().getLanguage().toString().equals("pl")) {
//            websiteUrl = "https://stoprussia.com.pl/31aouqtmuaa7ktdxmc9uxcuwbx9/pl/about-111";
            mapOfInsideUrls.get(UrlType.DOMAIN_TO_CURRENT_WEBVIEW).add(new EnhancedUrl.Builder("https://stoprussia.com.pl/31aouqtmuaa7ktdxmc9uxcuwbx9/pl/about-111")
                    .build());
//        } else {
            websiteUrl = "https://stoprussia.com.pl/31aouqtmuaa7ktdxmc9uxcuwbx9/about-111";
            mapOfInsideUrls.get(UrlType.DOMAIN_TO_CURRENT_WEBVIEW).add(new EnhancedUrl.Builder("https://stoprussia.com.pl/31aouqtmuaa7ktdxmc9uxcuwbx9/about-111")
                    .build());
//        }

        websiteLoader = new WebsiteLoader.Builder(this)
                .build();

        websiteLoader.setPriorityOfDomainToCurrentWebView(1);
        websiteLoader.setPriorityOfUrlToOutSideApp(2);
        websiteLoader.load();

        return rootView;
    }
}