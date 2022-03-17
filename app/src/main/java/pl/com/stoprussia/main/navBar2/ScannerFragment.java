package pl.com.stoprussia.main.navBar2;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;

import pl.com.stoprussia.R;
import pl.com.stoprussia.core.fragment.BaseFragment;

import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class ScannerFragment extends BaseFragment {

    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 1;

    private CodeScanner mCodeScanner;

    private ConstraintLayout clResult;
    private ConstraintLayout clCameraPermission;

    private ImageView ivTitleIcon;

    private TextView tvTip;
    private TextView tvTittle;
    private TextView tvResult;
    private TextView tvShare;

    private Button btScanNext;
    Button btPermission;

    Activity activity;
    CodeScannerView scannerView;

    private View rootView;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_scanner, container, false);

        clResult = (ConstraintLayout) rootView.findViewById(R.id.cl_result);
        ivTitleIcon = (ImageView) rootView.findViewById(R.id.iv_title_icon);

        tvTip = (TextView) rootView.findViewById(R.id.tv_tip);
        tvTittle = (TextView) rootView.findViewById(R.id.tv_tittle);
        tvResult = (TextView) rootView.findViewById(R.id.tv_result);

        btScanNext = (Button) rootView.findViewById(R.id.bt_scan_next);
        btScanNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clResult.setVisibility(View.GONE);
                mCodeScanner.startPreview();
            }
        });


        Activity activity = getActivity();
        CodeScannerView scannerView = rootView.findViewById(R.id.scanner_view);


        mCodeScanner = new CodeScanner(activity, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        clResult.setVisibility(View.VISIBLE);

                        try {
                            tvResult.setText(result.getText());
                            String trimmedString = result.toString().substring(0, 3);
                            int trimmedInt = Integer.parseInt(trimmedString);

                            if (trimmedInt >= 0 && trimmedInt <= 19) {
                                tvTittle.setText(getResources().getString(R.string.country_usa));
                            } else if (trimmedInt >= 20 && trimmedInt <= 29) {
                                tvTittle.setText(getResources().getString(R.string.other_supermarket_weighing_goods_extensions));
                            } else if (trimmedInt >= 30 && trimmedInt <= 39) {
                                tvTittle.setText(getResources().getString(R.string.other_usa_medications));
                            } else if (trimmedInt >= 40 && trimmedInt <= 49) {
                                tvTittle.setText(getResources().getString(R.string.other_limited_distribution_extensions));
                            } else if (trimmedInt >= 50 && trimmedInt <= 139) {
                                tvTittle.setText(getResources().getString(R.string.country_usa));
                            } else if (trimmedInt >= 200 && trimmedInt <= 299) {
                                tvTittle.setText(getResources().getString(R.string.other_limited_distribution));
                            } else if (trimmedInt >= 300 && trimmedInt <= 379) {
                                tvTittle.setText(getResources().getString(R.string.country_france_monaco));
                            } else if (trimmedInt == 380) {
                                tvTittle.setText(getResources().getString(R.string.country_bulgaria));
                            } else if (trimmedInt == 383) {
                                tvTittle.setText(getResources().getString(R.string.country_slovenia));
                            } else if (trimmedInt == 385) {
                                tvTittle.setText(getResources().getString(R.string.country_croatia));
                            } else if (trimmedInt == 387) {
                                tvTittle.setText(getResources().getString(R.string.country_bosnia_and_herzegovina));
                            } else if (trimmedInt == 389) {
                                tvTittle.setText(getResources().getString(R.string.country_montenegro));
                            } else if (trimmedInt >= 400 && trimmedInt <= 440) {
                                tvTittle.setText(getResources().getString(R.string.country_germany));
                            } else if (trimmedInt >= 450 && trimmedInt <= 459) {
                                tvTittle.setText(getResources().getString(R.string.country_japan));
                            } else if (trimmedInt >= 460 && trimmedInt <= 469) {
                                tvTittle.setText(getResources().getString(R.string.country_russia));
                            } else if (trimmedInt == 470) {
                                tvTittle.setText(getResources().getString(R.string.country_kyrgyzstan));
                            } else if (trimmedInt == 471) {
                                tvTittle.setText(getResources().getString(R.string.country_taiwan));
                            } else if (trimmedInt == 474) {
                                tvTittle.setText(getResources().getString(R.string.country_estonia));
                            } else if (trimmedInt == 475) {
                                tvTittle.setText(getResources().getString(R.string.country_latvia));
                            } else if (trimmedInt == 476) {
                                tvTittle.setText(getResources().getString(R.string.country_azerbaijan));
                            } else if (trimmedInt == 477) {
                                tvTittle.setText(getResources().getString(R.string.country_lithuanian));
                            } else if (trimmedInt == 478) {
                                tvTittle.setText(getResources().getString(R.string.country_uzbekistan));
                            } else if (trimmedInt == 479) {
                                tvTittle.setText(getResources().getString(R.string.country_sri_lanka));
                            } else if (trimmedInt == 480) {
                                tvTittle.setText(getResources().getString(R.string.country_philippines));
                            } else if (trimmedInt == 481) {
                                tvTittle.setText(getResources().getString(R.string.country_belarus));
                            } else if (trimmedInt == 482) {
                                tvTittle.setText(getResources().getString(R.string.country_ukraine));
                            } else if (trimmedInt == 483) {
                                tvTittle.setText(getResources().getString(R.string.country_turkmenistan));
                            } else if (trimmedInt == 484) {
                                tvTittle.setText(getResources().getString(R.string.country_moldova));
                            } else if (trimmedInt == 485) {
                                tvTittle.setText(getResources().getString(R.string.country_armenia));
                            } else if (trimmedInt == 486) {
                                tvTittle.setText(getResources().getString(R.string.country_georgia));
                            } else if (trimmedInt == 487) {
                                tvTittle.setText(getResources().getString(R.string.country_kazakhstan));
                            } else if (trimmedInt == 488) {
                                tvTittle.setText(getResources().getString(R.string.country_tajikistan));
                            } else if (trimmedInt == 489) {
                                tvTittle.setText(getResources().getString(R.string.country_hong_kong));
                            } else if (trimmedInt >= 490 && trimmedInt <= 499) {
                                tvTittle.setText(getResources().getString(R.string.country_japan));
                            } else if (trimmedInt >= 500 && trimmedInt <= 509) {
                                tvTittle.setText(getResources().getString(R.string.country_great_britain));
                            } else if (trimmedInt == 520) {
                                tvTittle.setText(getResources().getString(R.string.country_greece));
                            } else if (trimmedInt == 528) {
                                tvTittle.setText(getResources().getString(R.string.country_lebanon));
                            } else if (trimmedInt == 529) {
                                tvTittle.setText(getResources().getString(R.string.country_cyprus));
                            } else if (trimmedInt == 530) {
                                tvTittle.setText(getResources().getString(R.string.country_albania));
                            } else if (trimmedInt == 531) {
                                tvTittle.setText(getResources().getString(R.string.country_north_macedonia));
                            } else if (trimmedInt == 535) {
                                tvTittle.setText(getResources().getString(R.string.country_malta));
                            } else if (trimmedInt == 539) {
                                tvTittle.setText(getResources().getString(R.string.country_ireland));
                            } else if (trimmedInt >= 540 && trimmedInt <= 549) {
                                tvTittle.setText(getResources().getString(R.string.country_belgium_luxembourg));
                            } else if (trimmedInt == 560) {
                                tvTittle.setText(getResources().getString(R.string.country_portugal));
                            } else if (trimmedInt == 569) {
                                tvTittle.setText(getResources().getString(R.string.country_iceland));
                            } else if (trimmedInt >= 570 && trimmedInt <= 579) {
                                tvTittle.setText(getResources().getString(R.string.country_denmark_faroe_islands_greenlan));
                            } else if (trimmedInt == 590) {
                                tvTittle.setText(getResources().getString(R.string.country_poland));
                            } else if (trimmedInt == 594) {
                                tvTittle.setText(getResources().getString(R.string.country_romania));
                            } else if (trimmedInt == 599) {
                                tvTittle.setText(getResources().getString(R.string.country_hungary));
                            } else if (trimmedInt == 600 || trimmedInt == 601) {
                                tvTittle.setText(getResources().getString(R.string.country_rpa));
                            } else if (trimmedInt == 603) {
                                tvTittle.setText(getResources().getString(R.string.country_ghana));
                            } else if (trimmedInt == 604) {
                                tvTittle.setText(getResources().getString(R.string.country_senegal));
                            } else if (trimmedInt == 608) {
                                tvTittle.setText(getResources().getString(R.string.country_bahrain));
                            } else if (trimmedInt == 609) {
                                tvTittle.setText(getResources().getString(R.string.country_mauritis));
                            } else if (trimmedInt == 611) {
                                tvTittle.setText(getResources().getString(R.string.country_morocco));
                            } else if (trimmedInt == 613) {
                                tvTittle.setText(getResources().getString(R.string.country_algeria));
                            } else if (trimmedInt == 615) {
                                tvTittle.setText(getResources().getString(R.string.country_nigeria));
                            } else if (trimmedInt == 616) {
                                tvTittle.setText(getResources().getString(R.string.country_kenya));
                            } else if (trimmedInt == 617) {
                                tvTittle.setText(getResources().getString(R.string.country_cameroon));
                            } else if (trimmedInt == 618) {
                                tvTittle.setText(getResources().getString(R.string.country_ivory_coast));
                            } else if (trimmedInt == 619) {
                                tvTittle.setText(getResources().getString(R.string.country_tunisia));
                            } else if (trimmedInt == 620) {
                                tvTittle.setText(getResources().getString(R.string.country_tanzania));
                            } else if (trimmedInt == 621) {
                                tvTittle.setText(getResources().getString(R.string.country_syria));
                            } else if (trimmedInt == 622) {
                                tvTittle.setText(getResources().getString(R.string.country_egypt));
                            } else if (trimmedInt == 623) {
                                tvTittle.setText(getResources().getString(R.string.country_brunei));
                            } else if (trimmedInt == 624) {
                                tvTittle.setText(getResources().getString(R.string.country_libya));
                            } else if (trimmedInt == 625) {
                                tvTittle.setText(getResources().getString(R.string.country_jordan));
                            } else if (trimmedInt == 626) {
                                tvTittle.setText(getResources().getString(R.string.country_iran));
                            } else if (trimmedInt == 627) {
                                tvTittle.setText(getResources().getString(R.string.country_kuwait));
                            } else if (trimmedInt == 628) {
                                tvTittle.setText(getResources().getString(R.string.country_saudi_arabia));
                            } else if (trimmedInt == 629) {
                                tvTittle.setText(getResources().getString(R.string.country_united_arab_emirates));
                            } else if (trimmedInt == 630) {
                                tvTittle.setText(getResources().getString(R.string.country_qatar));
                            } else if (trimmedInt == 631) {
                                tvTittle.setText(getResources().getString(R.string.country_namibia));
                            } else if (trimmedInt >= 640 && trimmedInt <= 649) {
                                tvTittle.setText(getResources().getString(R.string.country_finland));
                            } else if (trimmedInt >= 690 && trimmedInt <= 699) {
                                tvTittle.setText(getResources().getString(R.string.country_china));
                            } else if (trimmedInt >= 700 && trimmedInt <= 709) {
                                tvTittle.setText(getResources().getString(R.string.country_norway));
                            } else if (trimmedInt == 729) {
                                tvTittle.setText(getResources().getString(R.string.country_israel));
                            } else if (trimmedInt >= 730 && trimmedInt <= 739) {
                                tvTittle.setText(getResources().getString(R.string.country_sweden));
                            } else if (trimmedInt == 740) {
                                tvTittle.setText(getResources().getString(R.string.country_gwaremala));
                            } else if (trimmedInt == 741) {
                                tvTittle.setText(getResources().getString(R.string.country_el_salvador));
                            } else if (trimmedInt == 742) {
                                tvTittle.setText(getResources().getString(R.string.country_honduras));
                            } else if (trimmedInt == 743) {
                                tvTittle.setText(getResources().getString(R.string.country_nicaragua));
                            } else if (trimmedInt == 744) {
                                tvTittle.setText(getResources().getString(R.string.country_costa_rica));
                            } else if (trimmedInt == 745) {
                                tvTittle.setText(getResources().getString(R.string.country_panama));
                            } else if (trimmedInt == 746) {
                                tvTittle.setText(getResources().getString(R.string.country_dominican_republic));
                            } else if (trimmedInt == 750) {
                                tvTittle.setText(getResources().getString(R.string.country_mexico));
                            } else if (trimmedInt == 754 || trimmedInt == 755) {
                                tvTittle.setText(getResources().getString(R.string.country_canada));
                            } else if (trimmedInt == 759) {
                                tvTittle.setText(getResources().getString(R.string.country_venezuelan));
                            } else if (trimmedInt >= 760 && trimmedInt <= 769) {
                                tvTittle.setText(getResources().getString(R.string.country_switzerland_liechtenstein));
                            } else if (trimmedInt == 770 || trimmedInt == 771) {
                                tvTittle.setText(getResources().getString(R.string.country_colombia));
                            } else if (trimmedInt == 773) {
                                tvTittle.setText(getResources().getString(R.string.country_uruguay));
                            } else if (trimmedInt == 775) {
                                tvTittle.setText(getResources().getString(R.string.country_peru));
                            } else if (trimmedInt == 777) {
                                tvTittle.setText(getResources().getString(R.string.country_bolivia));
                            } else if (trimmedInt == 778 || trimmedInt == 779) {
                                tvTittle.setText(getResources().getString(R.string.country_argentina));
                            } else if (trimmedInt == 780) {
                                tvTittle.setText(getResources().getString(R.string.country_chile));
                            } else if (trimmedInt == 784) {
                                tvTittle.setText(getResources().getString(R.string.country_paraguay));
                            } else if (trimmedInt == 786) {
                                tvTittle.setText(getResources().getString(R.string.country_ecuador));
                            } else if (trimmedInt == 789 || trimmedInt == 790) {
                                tvTittle.setText(getResources().getString(R.string.country_brazil));
                            } else if (trimmedInt >= 800 && trimmedInt <= 839) {
                                tvTittle.setText(getResources().getString(R.string.country_italy_san_marino_vatican));
                            } else if (trimmedInt >= 840 && trimmedInt <= 849) {
                                tvTittle.setText(getResources().getString(R.string.country_spain_andorra));
                            } else if (trimmedInt == 850) {
                                tvTittle.setText(getResources().getString(R.string.country_cuba));
                            } else if (trimmedInt == 858) {
                                tvTittle.setText(getResources().getString(R.string.country_slovakia));
                            } else if (trimmedInt == 859) {
                                tvTittle.setText(getResources().getString(R.string.country_czech_republic));
                            } else if (trimmedInt == 860) {
                                tvTittle.setText(getResources().getString(R.string.country_serbia));
                            } else if (trimmedInt == 865) {
                                tvTittle.setText(getResources().getString(R.string.country_mongolia));
                            } else if (trimmedInt == 867) {
                                tvTittle.setText(getResources().getString(R.string.country_north_korea));
                            } else if (trimmedInt == 868 || trimmedInt == 869) {
                                tvTittle.setText(getResources().getString(R.string.country_turkey));
                            } else if (trimmedInt >= 870 && trimmedInt <= 879) {
                                tvTittle.setText(getResources().getString(R.string.country_netherlands));
                            } else if (trimmedInt == 880) {
                                tvTittle.setText(getResources().getString(R.string.country_south_korea));
                            } else if (trimmedInt == 883) {
                                tvTittle.setText(getResources().getString(R.string.country_burma));
                            } else if (trimmedInt == 884) {
                                tvTittle.setText(getResources().getString(R.string.country_cambodian));
                            } else if (trimmedInt == 885) {
                                tvTittle.setText(getResources().getString(R.string.country_thailand));
                            } else if (trimmedInt == 888) {
                                tvTittle.setText(getResources().getString(R.string.country_singapore));
                            } else if (trimmedInt == 890) {
                                tvTittle.setText(getResources().getString(R.string.country_india));
                            } else if (trimmedInt == 893) {
                                tvTittle.setText(getResources().getString(R.string.country_vietnam));
                            } else if (trimmedInt == 896) {
                                tvTittle.setText(getResources().getString(R.string.country_pakistan));
                            } else if (trimmedInt == 899) {
                                tvTittle.setText(getResources().getString(R.string.country_indonesia));
                            } else if (trimmedInt >= 900 && trimmedInt <= 919) {
                                tvTittle.setText(getResources().getString(R.string.country_austria));
                            } else if (trimmedInt >= 930 && trimmedInt <= 939) {
                                tvTittle.setText(getResources().getString(R.string.country_australia));
                            } else if (trimmedInt >= 940 && trimmedInt <= 949) {
                                tvTittle.setText(getResources().getString(R.string.country_new_zealand));
                            } else if (trimmedInt == 950) {
                                tvTittle.setText(getResources().getString(R.string.other_gs1_global_office));
                            } else if (trimmedInt == 951) {
                                tvTittle.setText(getResources().getString(R.string.other_gs1_global_office_epcglobal));
                            } else if (trimmedInt == 955) {
                                tvTittle.setText(getResources().getString(R.string.country_malaysia));
                            } else if (trimmedInt == 958) {
                                tvTittle.setText(getResources().getString(R.string.country_macau));
                            } else if (trimmedInt >= 960 && trimmedInt <= 969) {
                                tvTittle.setText(getResources().getString(R.string.other_global_office_gs1));
                            } else if (trimmedInt == 977) {
                                tvTittle.setText(getResources().getString(R.string.other_issn));
                            } else if (trimmedInt == 978 || trimmedInt == 979) {
                                tvTittle.setText(getResources().getString(R.string.other_isbn));
                            } else if (trimmedInt == 980) {
                                tvTittle.setText(getResources().getString(R.string.other_return_bills));
                            } else if (trimmedInt == 981 || trimmedInt == 982) {
                                tvTittle.setText(getResources().getString(R.string.other_coupons_for_the_common_currency));
                            } else if (trimmedInt >= 990 && trimmedInt <= 999) {
                                tvTittle.setText(getResources().getString(R.string.other_coupons));
                            } else {
                                tvTittle.setText(getResources().getString(R.string.scanner_invalid));
                            }

                            if (tvTittle.getText().equals(getResources().getString(R.string.country_russia))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_belarus))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_cuba))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_nicaragua))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_north_korea))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_serbia))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_syria))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_venezuelan))) {
                                ivTitleIcon.setImageResource(R.drawable.ic_bad);
                                clResult.setBackgroundColor(getResources().getColor(R.color.russia_red));
                                btScanNext.setTextColor(getResources().getColor(R.color.russia_red));
                            } else if (tvTittle.getText().equals(getResources().getString(R.string.country_hungary))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_china))) {
                                ivTitleIcon.setImageResource(R.drawable.ic_good_maybe);
                                clResult.setBackgroundColor(getResources().getColor(R.color.yellow));
                                btScanNext.setTextColor(getResources().getColor(R.color.yellow));
                            } else if (tvTittle.getText().equals(getResources().getString(R.string.country_albania))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_algeria))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_argentina))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_armenia))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_australia))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_austria))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_azerbaijan))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_bahrain))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_belgium_luxembourg))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_bolivia))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_brazil))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_bosnia_and_herzegovina))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_brunei))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_bulgaria))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_burma))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_cambodian))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_cameroon))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_canada))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_chile))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_colombia))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_costa_rica))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_croatia))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_cyprus))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_czech_republic))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_denmark_faroe_islands_greenlan))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_dominican_republic))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_ecuador))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_egypt))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_el_salvador))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_estonia))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_finland))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_france_monaco))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_georgia))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_germany))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_ghana))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_great_britain))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_greece))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_gwaremala))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_honduras))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_hong_kong))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_iceland))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_india))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_indonesia))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_iran))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_ireland))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_israel))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_italy_san_marino_vatican))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_ivory_coast))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_japan))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_jordan))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_kazakhstan))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_kenya))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_kuwait))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_kyrgyzstan))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_latvia))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_lebanon))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_libya))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_lithuanian))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_macau))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_malaysia))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_malta))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_mauritis))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_mexico))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_moldova))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_mongolia))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_montenegro))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_morocco))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_netherlands))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_new_zealand))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_nigeria))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_north_macedonia))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_norway))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_pakistan))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_panama))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_paraguay))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_peru))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_philippines))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_poland))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_portugal))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_romania))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_rpa))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_saudi_arabia))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_senegal))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_singapore))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_slovakia))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_slovenia))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_south_korea))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_spain_andorra))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_sri_lanka))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_sweden))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_switzerland_liechtenstein))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_taiwan))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_tajikistan))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_tanzania))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_thailand))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_tunisia))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_turkey))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_turkmenistan))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_ukraine))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_united_arab_emirates))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_usa))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_uruguay))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_uzbekistan))
                                    || tvTittle.getText().equals(getResources().getString(R.string.country_vietnam))) {
                                ivTitleIcon.setImageResource(R.drawable.ic_good);
                                clResult.setBackgroundColor(getResources().getColor(R.color.green));
                                btScanNext.setTextColor(getResources().getColor(R.color.green));
                            } else if (tvTittle.getText().equals(getResources().getString(R.string.other_supermarket_weighing_goods_extensions))
                                    || tvTittle.getText().equals(getResources().getString(R.string.other_limited_distribution_extensions))
                                    || tvTittle.getText().equals(getResources().getString(R.string.other_limited_distribution))
                                    || tvTittle.getText().equals(getResources().getString(R.string.other_gs1_global_office))
                                    || tvTittle.getText().equals(getResources().getString(R.string.other_gs1_global_office_epcglobal))
                                    || tvTittle.getText().equals(getResources().getString(R.string.other_global_office_gs1))
                                    || tvTittle.getText().equals(getResources().getString(R.string.other_issn))
                                    || tvTittle.getText().equals(getResources().getString(R.string.other_isbn))
                                    || tvTittle.getText().equals(getResources().getString(R.string.other_return_bills))
                                    || tvTittle.getText().equals(getResources().getString(R.string.other_coupons_for_the_common_currency))
                                    || tvTittle.getText().equals(getResources().getString(R.string.other_coupons))) {
                                ivTitleIcon.setImageResource(R.drawable.ic_question_mark);
                                clResult.setBackgroundColor(getResources().getColor(R.color.russia_blue));
                                btScanNext.setTextColor(getResources().getColor(R.color.russia_blue));
                            } else {
                                ivTitleIcon.setImageResource(R.drawable.ic_question_mark);
                                clResult.setBackgroundColor(getResources().getColor(R.color.russia_blue));
                                btScanNext.setTextColor(getResources().getColor(R.color.russia_blue));
                            }
                        } catch (Exception e) {
                            tvTittle.setText(getResources().getString(R.string.scanner_invalid));
                            clResult.setBackgroundColor(getResources().getColor(R.color.russia_blue));
                            btScanNext.setTextColor(getResources().getColor(R.color.russia_blue));
                            tvResult.setText(result.getText());
                        }
                    }
                });
            }
        });

        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            mCodeScanner.startPreview();
        } else {
            clCameraPermission = (ConstraintLayout) rootView.findViewById(R.id.cl_camera_permission);
            clCameraPermission.setVisibility(View.VISIBLE);

            btPermission = (Button) rootView.findViewById(R.id.bt_permission);
            btPermission.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    requestForCamera();
                }
            });
        }

        return rootView;
    }


    private void requestForCamera() {

        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            goToApplicationSettings();
        } else {

            Dexter.withContext(getActivity()).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
                @Override
                public void onPermissionGranted(PermissionGrantedResponse response) {
                    Toast.makeText(getContext(), Html.fromHtml(getResources().getString(R.string.permission_granted).toString()), Toast.LENGTH_LONG).show();
                    clCameraPermission.setVisibility(View.GONE);
                    mCodeScanner.startPreview();
                }

                @Override
                public void onPermissionDenied(PermissionDeniedResponse response) {
                    Toast.makeText(getContext(), Html.fromHtml(getResources().getString(R.string.permission_denied).toString()), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                    token.continuePermissionRequest();
                }
            }).check();
        }

    }


    @Override
    public void onResume() {
        super.onResume();
//        requestForCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mCodeScanner.releaseResources();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        rootView = null;
    }

    private void goToApplicationSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }


}