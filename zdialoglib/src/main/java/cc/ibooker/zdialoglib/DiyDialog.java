package cc.ibooker.zdialoglib;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * 自定义Dialog
 * Created by 邹峰立 on 2017/7/6.
 */
public class DiyDialog {
    private Context context;
    private Dialog dialog;

    public enum DiyDialogGravity {
        GRAVITY_CENTER,
        GRAVITY_LEFT,
        GRAVITY_RIGHT,
        GRAVITY_TOP,
        GRAVITY_BOTTOM
    }

    public Dialog getDialog() {
        return dialog;
    }

    public DiyDialog(@NonNull Context context, @NonNull View view) {
        this(context, R.style.diydialog, view);
    }

    public DiyDialog(@NonNull Context context, @StyleRes int themeResId, @NonNull View view) {
        dialog = new Dialog(context, themeResId);
        this.context = context;
        init(view);
    }

    /**
     * 初始化控件
     */
    private void init(View view) {
        dialog.setContentView(view);
        // 按返回键是否取消
        dialog.setCancelable(true);
        // 点击Dialog外围是否取消
        dialog.setCanceledOnTouchOutside(true);
        // 设置默认透明度0.2f
        this.setDimAmount(0.2f);
        // 设置默认居中显示
        this.setDiyDialogGravity(DiyDialogGravity.GRAVITY_CENTER);
    }

    // 是否展示
    public boolean isShowing() {
        return dialog != null && dialog.isShowing();
    }

    /**
     * 设置Dialog显示内容
     *
     * @param view 内容View
     */
    private void setContentView(View view) {
        if (view != null)
            dialog.setContentView(view);
    }

    /**
     * 按返回键是否取消
     *
     * @param cancelable true 取消 false 不取消  默认true
     */
    public DiyDialog setCancelable(boolean cancelable) {
        if (dialog != null)
            dialog.setCancelable(cancelable);
        return this;
    }

    /**
     * 点击Dialog外围是否取消
     *
     * @param cancelable true 取消 false 不取消  默认false
     */
    public DiyDialog setCanceledOnTouchOutside(boolean cancelable) {
        if (dialog != null)
            dialog.setCanceledOnTouchOutside(cancelable);
        return this;
    }

    /**
     * 设置取消事件
     *
     * @param onCancelListener 取消事件
     */
    public DiyDialog setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        if (dialog != null)
            dialog.setOnCancelListener(onCancelListener);
        return this;
    }

    /**
     * 设置Dialog宽度
     *
     * @param proportion 和屏幕的宽度比(10代表10%) 0~100
     */
    public DiyDialog setDiyDialogWidth(int proportion) {
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
    public DiyDialog setDiyDialogHeight(int proportion) {
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
     * 设置Dialog显示位置
     *
     * @param diyDialogGravity 左上右下中
     */
    public DiyDialog setDiyDialogGravity(DiyDialogGravity diyDialogGravity) {
        if (dialog != null) {
            Window window = dialog.getWindow();
            int gravity = Gravity.CENTER;
            if (diyDialogGravity == DiyDialogGravity.GRAVITY_BOTTOM) {
                gravity = Gravity.BOTTOM;
            } else if (diyDialogGravity == DiyDialogGravity.GRAVITY_CENTER) {
                gravity = Gravity.CENTER;
            } else if (diyDialogGravity == DiyDialogGravity.GRAVITY_LEFT) {
                gravity = Gravity.START;
            } else if (diyDialogGravity == DiyDialogGravity.GRAVITY_RIGHT) {
                gravity = Gravity.END;
            } else if (diyDialogGravity == DiyDialogGravity.GRAVITY_TOP) {
                gravity = Gravity.TOP;
            }
            if (window != null)
                window.getAttributes().gravity = gravity;
        }
        return this;
    }

    /**
     * 设置背景层透明度
     *
     * @param dimAmount 0~1
     */
    public DiyDialog setDimAmount(float dimAmount) {
        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null) {
                WindowManager.LayoutParams lp = window.getAttributes();
                // 设置背景层透明度
                lp.dimAmount = dimAmount;
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
    public DiyDialog setWindowAnimations(int style) {
        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null) {
                window.setWindowAnimations(style);
            }
        }
        return this;
    }

    /**
     * 展示Dialog
     */
    public void showDiyDialog() {
        if (dialog != null)
            dialog.show();
    }

    /**
     * 关闭Dialog
     */
    public void closeDiyDialog() {
        if (dialog != null) {
            dialog.cancel();
        }
    }

    /**
     * 获取屏幕宽度
     */
    private int getScreenW(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
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

}
