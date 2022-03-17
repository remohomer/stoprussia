package pl.com.stoprussia.core;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class ActionBarSettings {

    public static void hide(Activity activity) {
        if (Objects.requireNonNull(((AppCompatActivity) activity).getSupportActionBar()).isShowing()) {
            Objects.requireNonNull(((AppCompatActivity) activity).getSupportActionBar()).hide();
        }
    }

    public static void show(Activity activity) {
        if (!Objects.requireNonNull(((AppCompatActivity) activity).getSupportActionBar()).isShowing()) {
            Objects.requireNonNull(((AppCompatActivity) activity).getSupportActionBar()).show();
        }
    }

    public static void setTitle(Activity activity, String title) {
            Objects.requireNonNull(((AppCompatActivity) activity).getSupportActionBar()).setTitle(title);
    }
}