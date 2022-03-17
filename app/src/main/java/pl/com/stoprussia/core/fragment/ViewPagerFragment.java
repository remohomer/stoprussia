package pl.com.stoprussia.core.fragment;

import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;

import pl.com.stoprussia.main.navBar5.InfoPagerFragment;
import com.google.android.material.tabs.TabLayout;

public abstract class ViewPagerFragment extends BaseFragment {

    protected TabLayout tabLayout;
    protected ViewPager viewPager;
    protected ViewPageAdapter viewPageAdapter;

    public static ViewPagerFragment newInstance() {
        // TODO
        InfoPagerFragment fragment = new InfoPagerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ViewPager getViewPager() {
        return viewPager;
    }
}
