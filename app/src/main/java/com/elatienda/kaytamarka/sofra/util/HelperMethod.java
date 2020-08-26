package com.elatienda.kaytamarka.sofra.util;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.elatienda.kaytamarka.sofra.R;
import com.elatienda.kaytamarka.sofra.databinding.ActivityHomeCycleBinding;

import java.io.File;
import java.util.HashMap;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.elatienda.kaytamarka.sofra.util.Constants.USER_CLIENT;
import static com.elatienda.kaytamarka.sofra.util.Constants.USER_RESTAURANT;
import static com.elatienda.kaytamarka.sofra.util.Constants.USER_TYPE_FILE;
import static com.elatienda.kaytamarka.sofra.util.Constants.USER_TYPE_KEY;
import static com.elatienda.kaytamarka.sofra.view.activity.HomeCycleActivity.navBar;
import static com.elatienda.kaytamarka.sofra.view.activity.HomeCycleActivity.toolbarImgLeft;
import static com.elatienda.kaytamarka.sofra.view.activity.HomeCycleActivity.toolbarImgRight;
import static com.elatienda.kaytamarka.sofra.view.activity.HomeCycleActivity.toolbarTitle;

public class HelperMethod {

    private static ProgressDialog checkDialog;
    public static AlertDialog alertDialog;
    private static boolean isKeyboardShowing;


    public static void replaceFragment(FragmentManager fragmentManager, int id, Fragment fragment) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(id,fragment);
        ft.addToBackStack(null);
        //ft.commit();
        ft.commitAllowingStateLoss();
    }


    public static void closeKeypad(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public static void onLoadImageFromUrl(ImageView imageView, String URl, Context context) {
        Glide.with(context)
                .load(URl)
                .into(imageView);
    }

    public static void showProgressDialog(Activity activity, String title) {
        try {
            checkDialog = new ProgressDialog(activity);
            checkDialog.setMessage(title);
            checkDialog.setIndeterminate(false);
            checkDialog.setCancelable(false);
            checkDialog.show();
        } catch (Exception e) {
        }
    }

    public static void dismissProgressDialog() {
        try {
            checkDialog.dismiss();
        } catch (Exception e) {
        }
    }

    public static void changeLang(Context context, String lang) {
        Resources res = context.getResources();
        // Change locale settings in the app.
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(lang)); // API 17+ only.
        // Use conf.locale = new Locale(...) if targeting lower versions
        res.updateConfiguration(conf, dm);
    }

    public static void onPermission(Activity activity) {
        String[] perms = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.CALL_PHONE};

        ActivityCompat.requestPermissions(activity,
                perms,
                100);

    }

//    // if the user Not Allow Permission
//    public static void requestPermission(Activity activity, String permission, String title, String message, String[] permissions, int permissionCode) {
//        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) { //
//            // make alert dialog to show message that explain why we use the permission
//            new androidx.appcompat.app.AlertDialog.Builder(activity)
//                    .setTitle(title)
//                    .setMessage(message)
//                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            // reshow the permission to keep user choose
//                            ActivityCompat.requestPermissions(activity,
//                                    permissions, permissionCode);
//                        }
//                    })
//                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    })
//                    .create().show();
//
//        } else { // show the permission to keep user choose
//            ActivityCompat.requestPermissions(activity, permissions, permissionCode);
//        }
//    }

    public static void createAlertDialog(final Activity activity, final FragmentManager fragmentManager, final int id, final Fragment fragment){
        new AlertDialog.Builder(activity)
                .setTitle("Connection Failed")
                .setMessage("Please Check Your Internet Connection")
                .setIcon(R.drawable.ic_internet_connection_icon)
                .setCancelable(false)
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //Intent i = new Intent(ShowData.this, ShowData.class);
                        //finish();
                        activity.overridePendingTransition(0,0);
                        //startActivity(i);
                        replaceFragment(fragmentManager,id,fragment);
                        activity.overridePendingTransition(0,0);
                    }
                })
                .setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.finishAndRemoveTask();
                    }
                })
                .create()
                .show();
    }

    public static void customToolbar(Activity activity, Boolean titleVisibility, String title, int textColor){
        if(activity!=null){
            SharedPreferences userTypePreference = activity.getSharedPreferences(USER_TYPE_FILE, Context.MODE_PRIVATE);
            String userType = userTypePreference.getString(USER_TYPE_KEY,null);
            if (userType != null) {
                if (toolbarImgLeft != null && toolbarImgRight != null) {
                    if (userType.equals(USER_CLIENT)){
                        toolbarImgLeft.setImageResource(R.drawable.ic_cart_icon);
                    }else if (userType.equals(USER_RESTAURANT)){
                        toolbarImgLeft.setImageResource(R.drawable.ic_calculator_icon);
                    }
                    toolbarImgLeft.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(activity, "left", Toast.LENGTH_SHORT).show();
                        }
                    });
                    toolbarImgRight.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(activity, "right", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }
        if (toolbarTitle!=null){
            if(titleVisibility){
                toolbarTitle.setVisibility(View.VISIBLE);
                toolbarTitle.setText(title);
                toolbarTitle.setTextColor(textColor);
            }else {
                toolbarTitle.setVisibility(View.GONE);
            }
        }
    }

    public static void keyboardVisibility(View contentView){
        isKeyboardShowing = false;

        // ContentView is the root view of the layout of this activity/fragment
        contentView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                        Rect r = new Rect();
                        contentView.getWindowVisibleDisplayFrame(r);
                        int screenHeight = contentView.getRootView().getHeight();

                        // r.bottom is the position above soft keypad or device button.
                        // if keypad is shown, the r.bottom is smaller than that before.
                        int keypadHeight = screenHeight - r.bottom;

                        //Log.d(TAG, "keypadHeight = " + keypadHeight);

                        if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
                            // keyboard is opened
                            if (!isKeyboardShowing) {
                                isKeyboardShowing = true;
                                onKeyboardVisibilityChanged(true);
                            }
                        }
                        else {
                            // keyboard is closed
                            if (isKeyboardShowing) {
                                isKeyboardShowing = false;
                                onKeyboardVisibilityChanged(false);
                            }
                        }
                    }
                });
    }
    private static void onKeyboardVisibilityChanged(boolean opened) {
        if (navBar != null){
            if (opened){
                navBar.setVisibility(View.GONE);
            }else {
                navBar.setVisibility(View.VISIBLE);
            }
        }
    }

    public static RequestBody convertToRequestBody(String part) {
        try {
            if (!part.equals("")) {
                //RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), part);
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), part);
                return requestBody;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static MultipartBody.Part convertFileToMultipart(String pathImageFile, String Key) {
        if (pathImageFile != null) {
            File file = new File(pathImageFile);
            RequestBody reqFileselect = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part Imagebody = MultipartBody.Part.createFormData(Key, file.getName(), reqFileselect);
            return Imagebody;
        } else {
            return null;
        }
    }
}
