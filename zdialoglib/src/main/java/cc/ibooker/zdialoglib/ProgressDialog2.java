package cc.ibooker.zdialoglib;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

/**
 * 自定义进度条Dialog
 * Created by 邹峰立 on 2017/7/5.
 * https://github.com/zrunker/ZDialog
 */
public class ProgressDialog2 {
    private Context context;
    private ProgressDialog dialog;

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

    public ProgressDialog2(@NonNull Context context) {
        this(context, R.style.zdialog_proDialog);
    }

    public ProgressDialog2(@NonNull Context context, @StyleRes int themeResId) {
        dialog = new ProgressDialog(context, themeResId);
        this.context = context;
        init();
    }

    /**
     * 初始化控件
     */
    private void init() {
        setProgressStyle(ProgressDialog.STYLE_SPINNER);
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
    public ProgressDialog2 setCancelable(boolean cancelable) {
        if (dialog != null)
            dialog.setCancelable(cancelable);
        return this;
    }

    /**
     * 点击Dialog外围是否取消
     *
     * @param cancelable true 取消 false 不取消  默认false
     */
    public ProgressDialog2 setCanceledOnTouchOutside(boolean cancelable) {
        if (dialog != null)
            dialog.setCanceledOnTouchOutside(cancelable);
        return this;
    }

    /**
     * 设置取消事件
     *
     * @param onCancelListener 取消事件
     */
    public ProgressDialog2 setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        if (dialog != null)
            dialog.setOnCancelListener(onCancelListener);
        return this;
    }

    /**
     * 设置Dialog显示位置
     *
     * @param proDialogGravity 左上右下中
     */
    public ProgressDialog2 setProDialogGravity(ProDialogGravity proDialogGravity) {
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
    public ProgressDialog2 setDimAmount(float dimAmount) {
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
    public ProgressDialog2 setProDialogWidth(int proportion) {
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
    public ProgressDialog2 setProDialogHeight(int proportion) {
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
    public ProgressDialog2 setWindowAnimations(int style) {
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
    public ProgressDialog2 showProDialog() {
        if (dialog != null && !dialog.isShowing())
            dialog.show();
        return this;
    }

    /**
     * 关闭Dialog
     */
    public ProgressDialog2 closeProDialog() {
        if (dialog != null)
            dialog.cancel();
        return this;
    }

    /**
     * 设置进度条风格，ProgressDialog.STYLE_SPINNER-风格为圆形，旋转的
     */
    public ProgressDialog2 setProgressStyle(int style) {
        dialog.setProgressStyle(style);
        return this;
    }

    /**
     * 设置ProgressDialog 标题
     */
    public ProgressDialog2 setTitle(String title) {
        if (!TextUtils.isEmpty(title))
            dialog.setTitle(title);
        return this;
    }

    /**
     * 设置ProgressDialog 提示信息
     */
    public ProgressDialog2 setMessage(String message) {
        if (!TextUtils.isEmpty(message))
            dialog.setMessage(message);
        return this;
    }

    /**
     * 设置ProgressDialog 标题图标
     */
    public ProgressDialog2 setIcon(int iconRes) {
        if (iconRes != 0)
            dialog.setIcon(iconRes);
        return this;
    }

    /**
     * 设置ProgressDialog 的一个Button
     */
    public ProgressDialog2 setButton(String btnText, DialogInterface.OnClickListener onClickListener) {
        if (!TextUtils.isEmpty(btnText))
            dialog.setButton(btnText, onClickListener);
        return this;
    }

    /**
     * 设置ProgressDialog 的进度条是否不明确
     */
    public ProgressDialog2 setIndeterminate(boolean bool) {
        dialog.setIndeterminate(false);
        return this;
    }

    /**
     * 设置ProgressDialog 进度条进度
     */
    public ProgressDialog2 setProgress(int num) {
        if (num >= 0)
            dialog.setProgress(num);
        return this;
    }

    /**
     * 设置背景
     *
     * @param resource 资源文件地址
     */
    public ProgressDialog2 setBackgroundDrawable(int resource) {
        if (dialog != null && dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawableResource(resource);
        return this;
    }

    /**
     * 设置背景
     *
     * @param drawable drawable资源
     */
    public ProgressDialog2 setBackgroundDrawable(Drawable drawable) {
        if (dialog != null && dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(drawable);
        return this;
    }
}