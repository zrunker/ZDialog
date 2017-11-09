package cc.ibooker.zdialoglib;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 自定义进度条Dialog
 * Created by 邹峰立 on 2017/7/5.
 */
public class ProDialog {
    private Context context;
    private Dialog dialog;
    private ImageView imageView;
    private TextView messageTv;
    private ProgressBar progressBar;

    public enum ProDialogGravity {
        GRAVITY_CENTER,
        GRAVITY_LEFT,
        GRAVITY_RIGHT,
        GRAVITY_TOP,
        GRAVITY_BOTTOM
    }

    public ProDialog(@NonNull Context context) {
        this(context, R.style.proDialog);
    }

    public ProDialog(@NonNull Context context, @StyleRes int themeResId) {
        dialog = new Dialog(context, themeResId);
        this.context = context;
        init();
    }

    /**
     * 初始化控件
     */
    private void init() {
        dialog.setContentView(R.layout.layout_progress_dialog);

        imageView = (ImageView) dialog.findViewById(R.id.dialog_statue);
        imageView.setVisibility(View.GONE);
        messageTv = (TextView) dialog.findViewById(R.id.dialog_message);
        messageTv.setVisibility(View.GONE);
        progressBar = (ProgressBar) dialog.findViewById(R.id.dialog_progress);
        progressBar.setVisibility(View.VISIBLE);

        // 按返回键是否取消
        dialog.setCancelable(true);
        // 点击Dialog外围是否取消
        dialog.setCanceledOnTouchOutside(false);
        // 设置默认透明度0.2f
        this.setDimAmount(0.2f);
    }

    /**
     * 给Dialog设置提示信息
     */
    public ProDialog setMessage(CharSequence message) {
        if (dialog.isShowing() && !TextUtils.isEmpty(message)) {
            messageTv.setVisibility(View.VISIBLE);
            messageTv.setText(message);
            messageTv.invalidate();// 强制刷新
        }
        return this;
    }

    /**
     * 给Dialog设置提示信息颜色
     *
     * @param color 16进制颜色
     */
    public ProDialog setMessageColor(String color) {
        try {
            if (dialog.isShowing() && messageTv != null) {
                messageTv.setTextColor(Color.parseColor(color));
                messageTv.invalidate();// 强制刷新
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 改变dialog的状态图片
     */
    public ProDialog setImageStatue(int source) {
        if (dialog.isShowing()) {
            progressBar.setVisibility(View.GONE);
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(source);
            imageView.invalidate();// 强制刷新
        }
        return this;
    }

    /**
     * 显示ProgressBar
     */
    public ProDialog setProgressBar(boolean bool) {
        if (dialog.isShowing()) {
            if (bool) {
                imageView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
            } else
                progressBar.setVisibility(View.GONE);
            progressBar.invalidate();
        }
        return this;
    }

    /**
     * 按返回键是否取消
     *
     * @param cancelable true 取消 false 不取消  默认true
     */
    public ProDialog setCancelable(boolean cancelable) {
        if (dialog != null)
            dialog.setCancelable(cancelable);
        return this;
    }

    /**
     * 点击Dialog外围是否取消
     *
     * @param cancelable true 取消 false 不取消  默认false
     */
    public ProDialog setCanceledOnTouchOutside(boolean cancelable) {
        if (dialog != null)
            dialog.setCanceledOnTouchOutside(cancelable);
        return this;
    }

    /**
     * 设置取消事件
     *
     * @param onCancelListener 取消事件
     */
    public ProDialog setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        if (dialog != null)
            dialog.setOnCancelListener(onCancelListener);
        return this;
    }

    /**
     * 设置Dialog显示位置
     *
     * @param proDialogGravity 左上右下中
     */
    public ProDialog setProDialogGravity(ProDialogGravity proDialogGravity) {
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
    public ProDialog setDimAmount(float dimAmount) {
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
    public ProDialog setDelDialogWidth(int proportion) {
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
    public ProDialog setProDialogHeight(int proportion) {
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
    public ProDialog setWindowAnimations(int style) {
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
        wm.getDefaultDisplay().getMetrics(outMetrics);
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