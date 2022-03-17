package pl.com.stoprussia.core.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import pl.com.stoprussia.R;
import pl.com.stoprussia.core.ActionBarSettings;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public abstract class BottomNavActivity extends BaseActivity implements MenuItemHistory, NavBarFragment {

    protected long pressTime = 0;

    protected final ArrayList<Fragment> navBarFragments = new ArrayList<>();
    protected final Deque<Integer> integerDeque = new ArrayDeque<>(5);

    protected final FragmentManager fragmentManager = getSupportFragmentManager();

    protected Fragment activeFragment;
    protected BottomNavigationView bottomNavigationView;

    protected boolean dequeFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBarSettings.hide(this);
    }

    protected void init() {
        activeFragment = navBarFragments.get(0);
        for (int i = 0; i < navBarFragments.size(); i++) {
            if (i == 0) {
                fragmentManager.beginTransaction()
                        .add(R.id.fragment_layout, navBarFragments.get(i))
                        .commit();
            } else {
                fragmentManager.beginTransaction()
                        .add(R.id.fragment_layout, navBarFragments.get(i))
                        .hide(navBarFragments.get(i))
                        .commit();
            }
        }

        // Load bottom nav
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        bottomNavigationView.setOnNavigationItemReselectedListener(onNavigationItemReselectedListener);

        // Add HomeFragment to bottom nav history
        integerDeque.push(R.id.navItem1);
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {

        int id = item.getItemId();
        if (integerDeque.contains(id)) {
            if (id == R.id.navItem1) {
                if (integerDeque.size() != 1) {
                    if (dequeFlag) {
                        integerDeque.addFirst(R.id.navItem1);
                        dequeFlag = false;
                    }
                }
            }
            integerDeque.remove(id);
        }
        integerDeque.push(id);
        loadFragment(getNavBarFragment(item.getItemId()));
        return true;
    };

    private final BottomNavigationView.OnNavigationItemReselectedListener onNavigationItemReselectedListener
            = new BottomNavigationView.OnNavigationItemReselectedListener() {
        @SuppressLint("DetachAndAttachSameFragment")
        @Override
        public void onNavigationItemReselected(@NonNull MenuItem item) {

            // reload fragment after double click on nav bar
            Fragment currentFragment = getActiveFragment();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                fragmentManager.beginTransaction().detach(currentFragment).commitNow();
                fragmentManager.beginTransaction().attach(currentFragment).commitNow();
            } else {
                fragmentManager.beginTransaction().detach(currentFragment).attach(currentFragment).commit();
            }
        }
    };

    @Override
    public void onBackPressed() {
        if (integerDeque.size() >= 1) {
            integerDeque.pop();
        }
        if (!integerDeque.isEmpty()) {
            loadFragment(getNavBarFragment(integerDeque.peek()));

        } else {
//            if (pressTime + 200 > System.currentTimeMillis()) {
                super.onBackPressed();
                finish();
//            }
//            pressTime = System.currentTimeMillis();
        }
    }

    private void loadFragment(Fragment fragment) {
        fragmentManager.beginTransaction()
                .hide(activeFragment)
                .show(fragment).commit();
        activeFragment = fragment;
    }

    public void changeFragmentTo(Activity activity, int navItemId) {
        saveMenuItemHistory(navItemId);
        ((BottomNavActivity) activity).getSupportFragmentManager()
                .beginTransaction()
                .hide(((BottomNavActivity) activity)
                        .getActiveFragment())
                .show(((BottomNavActivity) activity)
                        .getNavBarFragments()
                        .get(navItemId))
                .commit();
        ((BottomNavActivity) activity).setActiveFragment(((BottomNavActivity) activity)
                .getNavBarFragments()
                .get(navItemId));
        ((BottomNavActivity) activity).getBottomNavigationView()
                .getMenu()
                .getItem(navItemId)
                .setChecked(true);
    }


    @Override
    public void saveMenuItemHistory(int menuItemId) {
        if (integerDeque.contains(menuItemId)) {
            if (menuItemId == R.id.navItem1) {
                if (integerDeque.size() != 1) {
                    if (dequeFlag) {
                        integerDeque.addFirst(R.id.navItem1);
                        dequeFlag = false;
                    }
                }
            }
            integerDeque.remove(menuItemId);
        }
        integerDeque.push(menuItemId);
    }

    // -------------------- getters and setters ------------------- //

    public BottomNavigationView getBottomNavigationView() {
        return bottomNavigationView;
    }

    public Fragment getActiveFragment() {
        return activeFragment;
    }

    public void setActiveFragment(Fragment activeFragment) {
        this.activeFragment = activeFragment;
    }

    public ArrayList<Fragment> getNavBarFragments() {
        return navBarFragments;
    }

}
