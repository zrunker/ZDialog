package cc.ibooker.zdialoglib;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

/**
 * 自定义进度条Dialog
 * Created by 邹峰立 on 2017/7/5.
 * https://github.com/zrunker/ZDialog
 */
public class ProgressDialog {
    private Context context;
    private Dialog dialog;
    private ProgressBar progressBar;

    public enum ProDialogGravity {
        GRAVITY_CENTER,
        GRAVITY_LEFT,
        GRAVITY_RIGHT,
        GRAVITY_TOP,
        GRAVITY_BOTTOM
    }

    public Dialog getDialog() {
        return dialog;
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public ProgressDialog(@NonNull Context context) {
        this(context, R.style.zdialog_proDialog);
    }

    public ProgressDialog(@NonNull Context context, @StyleRes int themeResId) {
        dialog = new Dialog(context, themeResId);
        this.context = context;
        init();
    }

    /**
     * 初始化控件
     */
    private void init() {
        dialog.setContentView(R.layout.zdialog_layout_progress_dialog2);
        progressBar = dialog.findViewById(R.id.dialog_progress);
        // 按返回键是否取消
        dialog.setCancelable(true);
        // 点击Dialog外围是否取消
        dialog.setCanceledOnTouchOutside(false);
        // 设置默认透明度0.2f
        this.setDimAmount(0.2f);
    }

    // 是否展示
    public boolean isShowing() {
        return dialog != null && dialog.isShowing();
    }

    /**
     * 按返回键是否取消
     *
     * @param cancelable true 取消 false 不取消  默认true
     */
    public ProgressDialog setCancelable(boolean cancelable) {
        if (dialog != null)
            dialog.setCancelable(cancelable);
        return this;
    }

    /**
     * 点击Dialog外围是否取消
     *
     * @param cancelable true 取消 false 不取消  默认false
     */
    public ProgressDialog setCanceledOnTouchOutside(boolean cancelable) {
        if (dialog != null)
            dialog.setCanceledOnTouchOutside(cancelable);
        return this;
    }

    /**
     * 设置取消事件
     *
     * @param onCancelListener 取消事件
     */
    public ProgressDialog setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        if (dialog != null)
            dialog.setOnCancelListener(onCancelListener);
        return this;
    }

    /**
     * 设置Dialog显示位置
     *
     * @param proDialogGravity 左上右下中
     */
    public ProgressDialog setProDialogGravity(ProDialogGravity proDialogGravity) {
        Window window = dialog.getWindow();
        int gravity = Gravity.CENTER;
        if (proDialogGravity == ProDialogGravity.GRAVITY_BOTTOM) {
            gravity = Gravity.BOTTOM;
        } else if (proDialogGravity == ProDialogGravity.GRAVITY_CENTER) {
            gravity = Gravity.CENTER;
        } else if (proDialogGravity == ProDialogGravity.GRAVITY_LEFT) {
            gravity = Gravity.START;
        } else if (proDialogGravity == ProDialogGravity.GRAVITY_RIGHT) {
            gravity = Gravity.END;
        } else if (proDialogGravity == ProDialogGravity.GRAVITY_TOP) {
            gravity = Gravity.TOP;
        }
        if (window != null)
            window.getAttributes().gravity = gravity;
        return this;
    }

    /**
     * 设置背景层透明度
     *
     * @param dimAmount 0~1
     */
    public ProgressDialog setDimAmount(float dimAmount) {
        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            // 设置背景层透明度
            lp.dimAmount = dimAmount;
            window.setAttributes(lp);
        }
        return this;
    }

    /**
     * 设置Dialog宽度
     *
     * @param proportion 和屏幕的宽度比(10代表10%) 0~100
     */
    public ProgressDialog setProDialogWidth(int proportion) {
        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null) {
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.width = getScreenW(context) * proportion / 100;
                window.setAttributes(lp);
            }
        }
        return this;
    }

    /**
     * 设置Dialog高度
     *
     * @param proportion 和屏幕的高度比(10代表10%) 0~100
     */
    public ProgressDialog setProDialogHeight(int proportion) {
        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null) {
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.height = getScreenH(context) * proportion / 100;
                window.setAttributes(lp);
            }
        }
        return this;
    }

    /**
     * 设置Window动画
     *
     * @param style R文件
     */
    public ProgressDialog setWindowAnimations(int style) {
        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null) {
                window.setWindowAnimations(style);
            }
        }
        return this;
    }

    /**
     * 获取屏幕宽度
     */
    private int getScreenW(Context aty) {
        DisplayMetrics dm = aty.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕的高度
     */
    private int getScreenH(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        if (wm != null) {
            wm.getDefaultDisplay().getMetrics(outMetrics);
        }
        return outMetrics.heightPixels;
    }

    /**
     * 展示Dialog
     */
    public void showProDialog() {
        if (dialog != null)
            dialog.show();
    }

    /**
     * 关闭Dialog
     */
    public void closeProDialog() {
        if (dialog != null)
            dialog.cancel();
    }

}