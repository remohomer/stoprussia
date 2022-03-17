package pl.com.stoprussia.core.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public abstract class BaseFragment extends Fragment {

    protected ConstraintLayout clOfflineMode;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(getClass().getSimpleName(),": onAttach()");
        Log.d("FOCUS: "," " + getActivityNonNull().getCurrentFocus());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(getClass().getSimpleName(),": onCreate()");
        Log.d("FOCUS: "," " + getActivityNonNull().getCurrentFocus());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(getClass().getSimpleName(),": onCreateView()");
        Log.d("FOCUS: "," " + getActivityNonNull().getCurrentFocus());
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(getClass().getSimpleName(),": onViewCreated()");
        Log.d("FOCUS: "," " + getActivityNonNull().getCurrentFocus());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d(getClass().getSimpleName(),": onViewStateRestored()");
        Log.d("FOCUS: "," " + getActivityNonNull().getCurrentFocus());
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(getClass().getSimpleName(),": onStart()");
        Log.d("FOCUS: "," " + getActivityNonNull().getCurrentFocus());
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(getClass().getSimpleName(),": onResume()");
        Log.d("FOCUS: "," " + getActivityNonNull().getCurrentFocus());
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(getClass().getSimpleName(),": onPause()");
        Log.d("FOCUS: "," " + getActivityNonNull().getCurrentFocus());
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(getClass().getSimpleName(),": onStop()");
        Log.d("FOCUS: "," " + getActivityNonNull().getCurrentFocus());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(getClass().getSimpleName(),": onSaveInstanceState()");
        Log.d("FOCUS: "," " + getActivityNonNull().getCurrentFocus());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(getClass().getSimpleName(),": onDestroyView()");
        Log.d("FOCUS: "," " + getActivityNonNull().getCurrentFocus());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(getClass().getSimpleName(),": onDestroy()");
        Log.d("FOCUS: "," " + getActivityNonNull().getCurrentFocus());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(getClass().getSimpleName(),": onDetach()");
        Log.d("FOCUS: "," " + getActivityNonNull().getCurrentFocus());
    }

    public FragmentActivity getActivityNonNull() {
        if (super.getActivity() != null) {
            return super.getActivity();
        } else {
            throw new RuntimeException("null returned from getActivity()");
        }
    }

    protected void setThreadPolicy() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public ConstraintLayout getClOfflineMode() {
        return clOfflineMode;
    }
}
