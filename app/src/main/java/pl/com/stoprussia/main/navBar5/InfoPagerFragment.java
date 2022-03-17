package pl.com.stoprussia.main.navBar5;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentPagerAdapter;

import pl.com.stoprussia.MainActivity;
import pl.com.stoprussia.R;
import pl.com.stoprussia.core.fragment.ViewPageAdapter;
import pl.com.stoprussia.core.fragment.ViewPagerFragment;
import pl.com.stoprussia.core.websiteloader.InternetStatus;
import pl.com.stoprussia.core.websiteloader.OfflineMode;
import pl.com.stoprussia.main.navBar5.tab1.InfoAboutFragment;
import pl.com.stoprussia.main.navBar5.tab3.InfoReportFragment;
import pl.com.stoprussia.main.navBar5.tab2.InfoHelpFragment;

public class InfoPagerFragment extends ViewPagerFragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_info, container, false);

        viewPager = rootView.findViewById(R.id.viewpager_info);
        tabLayout = rootView.findViewById(R.id.tabs_info_layout);
        tabLayout.setupWithViewPager(viewPager);
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

            viewPageAdapter = new ViewPageAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            viewPageAdapter.addFragment(new InfoAboutFragment(), getResources().getText(R.string.tab_about).toString());
            viewPageAdapter.addFragment(new InfoHelpFragment(), getResources().getText(R.string.tab_help).toString());
            viewPageAdapter.addFragment(new InfoReportFragment(), getResources().getText(R.string.tab_report).toString());

            viewPager.setOffscreenPageLimit(viewPageAdapter.getCount() - 1);
            viewPager.setAdapter(viewPageAdapter);
        }

        return rootView;
    }
}