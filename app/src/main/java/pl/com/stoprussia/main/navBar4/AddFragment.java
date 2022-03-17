package pl.com.stoprussia.main.navBar4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import pl.com.stoprussia.MainActivity;
import pl.com.stoprussia.R;
import pl.com.stoprussia.core.fragment.WebViewFragment;
import pl.com.stoprussia.core.websiteloader.EnhancedUrl;
import pl.com.stoprussia.core.websiteloader.InternetStatus;
import pl.com.stoprussia.core.websiteloader.OfflineMode;
import pl.com.stoprussia.core.websiteloader.UrlType;
import pl.com.stoprussia.core.websiteloader.WebsiteLoader;

import java.util.Locale;

public class AddFragment extends WebViewFragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_add, container, false);

        webView = (WebView) rootView.findViewById(R.id.idWebViewAdd);
        progressBar = (ProgressBar) rootView.findViewById(R.id.idPBLoading);
        clOfflineMode = (ConstraintLayout)  rootView.findViewById(R.id.cl_offline_mode);

        if (InternetStatus.isOfflineMode()) {
            OfflineMode offlineMode = new OfflineMode(this);
            Button btOfflineGoToScanner = (Button) rootView.findViewById(R.id.bt_offline_go_to_scanner);
            btOfflineGoToScanner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity)getActivityNonNull()).changeFragmentProgrammatically(1);
                }
            });
            Button btOnlineMode = (Button) rootView.findViewById(R.id.bt_online_mode);
            btOnlineMode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InternetStatus.setOfflineMode(false);
                    getActivityNonNull().finish();
                    getActivityNonNull().startActivity(getActivity().getIntent());
                    getActivityNonNull().overridePendingTransition(0, 0);
                }
            });
        } else {

//            if (Locale.getDefault().getLanguage().toString().equals("pl")) {
//                websiteUrl = "https://stoprussia.com.pl/31aouqtmuaa7ktdxmc9uxcuwbx9/pl/add";
                mapOfInsideUrls.get(UrlType.DOMAIN_TO_CURRENT_WEBVIEW).add(new EnhancedUrl.Builder("https://stoprussia.com.pl/31aouqtmuaa7ktdxmc9uxcuwbx9/pl/add")
                        .build());
//            } else {
                websiteUrl = "https://stoprussia.com.pl/31aouqtmuaa7ktdxmc9uxcuwbx9/add";
                mapOfInsideUrls.get(UrlType.DOMAIN_TO_CURRENT_WEBVIEW).add(new EnhancedUrl.Builder("https://stoprussia.com.pl/31aouqtmuaa7ktdxmc9uxcuwbx9/add")
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