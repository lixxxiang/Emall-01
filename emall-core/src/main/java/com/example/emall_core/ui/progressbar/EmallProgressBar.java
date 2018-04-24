package com.example.emall_core.ui.progressbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.example.emall_core.R;
import com.example.emall_core.delegates.EmallDelegate;
import com.example.emall_core.util.dimen.DimenUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

/**
 * Created by lixiang on 2018/2/27.
 */

public class EmallProgressBar {
    private static final int LOADER_SIZE_SCALE = 8;
    private static final ArrayList<AppCompatDialog> LOADERS = new ArrayList<>();
    public static AppCompatDialog dialog = null;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceType")
    public static void showProgressbar(Context context) {

        ProgressBar progressBar = new ProgressbarCreator().creator(context);
//        progressBar.setIndeterminateDrawable(context.getDrawable(R.drawable.progressbar));
        dialog = new AppCompatDialog(context, R.style.dialog);
        dialog.setContentView(progressBar);

        int deviceWidth = new DimenUtil().getScreenWidth();
        int deviceHeight =  new DimenUtil().getScreenHeight();

        final Window dialogWindow = dialog.getWindow();

        if (dialogWindow != null) {
            final WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            lp.width = deviceWidth / LOADER_SIZE_SCALE;
            lp.height = deviceHeight / LOADER_SIZE_SCALE;
            lp.gravity = Gravity.CENTER;
        }
        LOADERS.add(dialog);
        dialog.show();
    }


    public static void hideProgressbar() {
        for (AppCompatDialog dialog : LOADERS) {
            if (dialog != null) {
                if (dialog.isShowing()) {
                    dialog.cancel();
                }
            }
        }
    }
}
