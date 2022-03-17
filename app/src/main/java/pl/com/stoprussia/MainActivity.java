package pl.com.stoprussia;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import pl.com.stoprussia.core.ActionBarSettings;
import pl.com.stoprussia.core.activity.BottomNavActivity;
import pl.com.stoprussia.core.websiteloader.InternetStatus;
import pl.com.stoprussia.main.navBar1.HomeFragment;
import pl.com.stoprussia.main.navBar2.ScannerFragment;
import pl.com.stoprussia.main.navBar3.ListFragment;
import pl.com.stoprussia.main.navBar4.AddFragment;
import pl.com.stoprussia.main.navBar5.InfoPagerFragment;

public class MainActivity extends BottomNavActivity {

    private WebView backgroundWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navBarFragments.add(new HomeFragment());
        navBarFragments.add(new ScannerFragment());
        navBarFragments.add(new ListFragment());
        navBarFragments.add(new AddFragment());
        navBarFragments.add(new InfoPagerFragment());
        init();
    }

    // ---------- ActionBar ----------

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    // ---------- Bottom NavigationBar ----------

    @SuppressLint("NonConstantResourceId")
    @Override
    public Fragment getNavBarFragment(int menuItemId) {
        switch (menuItemId) {
            case R.id.navItem1:
                ActionBarSettings.hide(this);
                bottomNavigationView.getMenu().getItem(0).setChecked(true);
                return navBarFragments.get(0);
            case R.id.navItem2:
                ActionBarSettings.hide(this);
                bottomNavigationView.getMenu().getItem(1).setChecked(true);
                return navBarFragments.get(1);
            case R.id.navItem3:
                ActionBarSettings.hide(this);
                bottomNavigationView.getMenu().getItem(2).setChecked(true);
                return navBarFragments.get(2);
            case R.id.navItem4:
                ActionBarSettings.hide(this);
                bottomNavigationView.getMenu().getItem(3).setChecked(true);
                return navBarFragments.get(3);
            case R.id.navItem5: {
                ActionBarSettings.hide(this);
                bottomNavigationView.getMenu().getItem(4).setChecked(true);
                return navBarFragments.get(4);
            }
            default: {
                ActionBarSettings.hide(this);
                bottomNavigationView.getMenu().getItem(0).setChecked(true);
                return new HomeFragment();
            }
        }
    }

    public void changeFragmentProgrammatically(int navItemId) {
        saveMenuItemHistory(navItemId);
        getSupportFragmentManager()
                .beginTransaction()
                .hide((this)
                        .getActiveFragment())
                .show((this)
                        .getNavBarFragments()
                        .get(navItemId))
                .commit();
        setActiveFragment((this)
                .getNavBarFragments()
                .get(navItemId));
        getBottomNavigationView()
                .getMenu()
                .getItem(navItemId)
                .setChecked(true);
    }

}