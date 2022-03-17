package pl.com.stoprussia.core.websiteloader;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import pl.com.stoprussia.MainActivity;
import pl.com.stoprussia.R;

public class InternetOffAlertDialog {

    public InternetOffAlertDialog(Activity activity) {

        if (Build.VERSION.SDK_INT <= 23) {
            Toast.makeText(activity, "Brak połączenia z internetem",
                    Toast.LENGTH_LONG).show();
        } else {
            Dialog dialog = new Dialog(activity);
            dialog.setContentView(R.layout.allert_dialog);
            dialog.setCanceledOnTouchOutside(false);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations =
                    android.R.style.Animation_Dialog;

            Button btTryAgain = dialog.findViewById(R.id.bt_try_again);
            btTryAgain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.finish();
                    activity.startActivity(activity.getIntent());
                    activity.overridePendingTransition(0, 0);
                }
            });
            Button btConnect = dialog.findViewById(R.id.bt_connect_to);
            btConnect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                }
            });

            Button btOfflineMode = dialog.findViewById(R.id.bt_offline_mode);
            btOfflineMode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InternetStatus.setOfflineMode(true);
                    activity.finish();
                    activity.startActivity(activity.getIntent());
                    activity.overridePendingTransition(0, 0);
                }
            });

            dialog.show();
        }
    }
}

