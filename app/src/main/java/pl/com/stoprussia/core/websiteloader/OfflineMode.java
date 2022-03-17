package pl.com.stoprussia.core.websiteloader;

import android.view.View;
import android.widget.Button;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import pl.com.stoprussia.core.fragment.BaseFragment;

public class OfflineMode {

    public OfflineMode(Fragment fragment) {
        ConstraintLayout clOfflineMode = ((BaseFragment) fragment).getClOfflineMode();
        clOfflineMode.setVisibility(View.VISIBLE);
    }
}
