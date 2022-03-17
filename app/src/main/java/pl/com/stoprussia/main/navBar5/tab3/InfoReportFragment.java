package pl.com.stoprussia.main.navBar5.tab3;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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
import pl.com.stoprussia.core.websiteloader.InternetOffAlertDialog;
import pl.com.stoprussia.core.websiteloader.InternetStatus;
import pl.com.stoprussia.core.websiteloader.UrlType;
import pl.com.stoprussia.core.websiteloader.WebsiteLoader;

import java.util.Locale;

public class InfoReportFragment extends WebViewFragment {

    private String current = null;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_info_report, container, false);

        webView = (WebView) rootView.findViewById(R.id.idWebViewUpdate);
        progressBar = (ProgressBar) rootView.findViewById(R.id.idPBLoading);


        InternetStatus internetStatus = new InternetStatus(getActivityNonNull());
        if (!internetStatus.internetIsConnected()) {
            InternetOffAlertDialog internetOffAlertDialog = new InternetOffAlertDialog(getActivityNonNull());
        } else {

            try {
                PackageManager manager = requireContext().getPackageManager();
                PackageInfo info = manager.getPackageInfo(
                        requireContext().getPackageName(), 0);
                current = info.versionName;
            } catch (Exception e) {
                e.printStackTrace();
            }

            webView = (WebView) rootView.findViewById(R.id.idWebViewUpdate);
            progressBar = (ProgressBar) rootView.findViewById(R.id.idPBLoading);


//            if (Locale.getDefault().getLanguage().toString().equals("pl")) {
//                websiteUrl = "https://stoprussia.com.pl/31aouqtmuaa7ktdxmc9uxcuwbx9/pl/report";
                mapOfInsideUrls.get(UrlType.DOMAIN_TO_CURRENT_WEBVIEW).add(new EnhancedUrl.Builder("https://stoprussia.com.pl/31aouqtmuaa7ktdxmc9uxcuwbx9/pl/report")
                        .build());
//            } else {
                websiteUrl = "https://stoprussia.com.pl/31aouqtmuaa7ktdxmc9uxcuwbx9/report";
                mapOfInsideUrls.get(UrlType.DOMAIN_TO_CURRENT_WEBVIEW).add(new EnhancedUrl.Builder("https://stoprussia.com.pl/31aouqtmuaa7ktdxmc9uxcuwbx9/report")
                        .build());
//            }

            websiteLoader = new WebsiteLoader.Builder(this)
                    .build();

            websiteLoader.setPriorityOfDomainToCurrentWebView(1);
            websiteLoader.load();
        }
        return rootView;
    }
}