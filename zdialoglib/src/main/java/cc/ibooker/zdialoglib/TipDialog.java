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
import android.widget.TextView;

/**
 * 提示Dialog
 * Created by 邹峰立 on 2017/7/5.
 */
public class TipDialog {
    private Context context;
    private Dialog dialog;
    private TextView titleTv, descTv, ensureTv, cancelTv;

    public enum TipDialogGravity {
        GRAVITY_CENTER,
        GRAVITY_LEFT,
        GRAVITY_RIGHT,
        GRAVITY_TOP,
        GRAVITY_BOTTOM
    }

    public TipDialog(@NonNull Context context) {
        this(context, R.style.diydialog);
    }

    public TipDialog(@NonNull Context context, @StyleRes int themeResId) {
        dialog = new Dialog(context, themeResId);
        this.context = context;
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        dialog.setContentView(R.layout.layout_tip_dialog);
        titleTv = dialog.findViewById(R.id.tv_title);
        descTv = dialog.findViewById(R.id.tv_desc);
        ensureTv = dialog.findViewById(R.id.tv_ensure);
        ensureTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ZDialogClickUtil.isFastClick())
                    return;
                // 关闭Dialog
                closeTipDialog();
                if (onTipEnsureListener != null) {
                    onTipEnsureListener.onEnsure();
                }
            }
        });
        cancelTv = dialog.findViewById(R.id.tv_cancel);
        cancelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ZDialogClickUtil.isFastClick())
                    return;
                // 关闭Dialog
                closeTipDialog();
                if (onTipCancelListener != null) {
                    onTipCancelListener.onCancel();
                }
            }
        });

        // 按返回键是否取消
        dialog.setCancelable(true);
        // 点击Dialog外围是否取消
        dialog.setCanceledOnTouchOutside(true);
        // 设置默认透明度0.2f
        this.setDimAmount(0.2f);
        // 设置Dialog宽度
        this.setTipDialogWidth(70);
        // 设置Dialog默认位置居中
        this.setTipDialogGravity(TipDialogGravity.GRAVITY_CENTER);
    }

    /**
     * 修改主题文字
     *
     * @param text 文字内容
     */
    public TipDialog setTitleText(String text) {
        if (titleTv != null && !TextUtils.isEmpty(text)) {
            titleTv.setText(text);
            titleTv.invalidate();
        }
        return this;
    }

    /**
     * 修改主题文字大小
     *
     * @param size 文字大小
     */
    public TipDialog setTitleTextSize(int size) {
        if (titleTv != null) {
            titleTv.setTextSize(size);
            titleTv.invalidate();
        }
        return this;
    }

    /**
     * 修改主题文字颜色
     *
     * @param color 文字颜色
     */
    public TipDialog setTitleColor(String color) {
        try {
            if (titleTv != null && !TextUtils.isEmpty(color)) {
                titleTv.setTextColor(Color.parseColor(color));
                titleTv.invalidate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 修改主题是否显示
     *
     * @param visible true显示  false不显示 默认显示
     */
    public TipDialog setTitleVisible(boolean visible) {
        if (titleTv != null) {
            titleTv.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
        return this;
    }

    /**
     * 修改描述文字
     *
     * @param text 文字内容
     */
    public TipDialog setDescText(String text) {
        if (descTv != null && !TextUtils.isEmpty(text)) {
            descTv.setText(text);
            descTv.invalidate();
        }
        return this;
    }

    /**
     * 修改描述文字大小
     *
     * @param size 文字大小
     */
    public TipDialog setDescTextSize(int size) {
        if (descTv != null) {
            descTv.setTextSize(size);
            descTv.invalidate();
        }
        return this;
    }

    /**
     * 修改描述文字颜色
     *
     * @param color 文字颜色
     */
    public TipDialog setDescColor(String color) {
        try {
            if (descTv != null && !TextUtils.isEmpty(color)) {
                descTv.setTextColor(Color.parseColor(color));
                descTv.invalidate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 修改描述是否显示
     *
     * @param visible true显示  false不显示 默认显示
     */
    public TipDialog setDescVisible(boolean visible) {
        if (descTv != null) {
            descTv.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
        return this;
    }

    /**
     * 修改确定文字
     *
     * @param text 文字内容
     */
    public TipDialog setEnsureText(String text) {
        if (ensureTv != null && !TextUtils.isEmpty(text)) {
            ensureTv.setText(text);
            ensureTv.invalidate();
        }
        return this;
    }

    /**
     * 修改确定文字
     *
     * @param size 文字大小
     */
    public TipDialog setEnsureTextSize(int size) {
        if (ensureTv != null) {
            ensureTv.setTextSize(size);
            ensureTv.invalidate();
        }
        return this;
    }

    /**
     * 修改确定文字颜色
     *
     * @param color 文字颜色
     */
    public TipDialog setEnsureColor(String color) {
        try {
            if (ensureTv != null && !TextUtils.isEmpty(color)) {
                ensureTv.setTextColor(Color.parseColor(color));
                ensureTv.invalidate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 修改取消文字
     *
     * @param text 文字内容
     */
    public TipDialog setCancelText(String text) {
        if (cancelTv != null && !TextUtils.isEmpty(text)) {
            cancelTv.setText(text);
            cancelTv.invalidate();
        }
        return this;
    }

    /**
     * 修改取消文字大小
     *
     * @param size 文字大小
     */
    public TipDialog setCancelTextSize(int size) {
        if (cancelTv != null) {
            cancelTv.setTextSize(size);
            cancelTv.invalidate();
        }
        return this;
    }

    /**
     * 修改取消文字颜色
     *
     * @param color 文字颜色
     */
    public TipDialog setCancelColor(String color) {
        try {
            if (cancelTv != null && !TextUtils.isEmpty(color)) {
                cancelTv.setTextColor(Color.parseColor(color));
                cancelTv.invalidate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 按返回键是否取消
     *
     * @param cancelable true 取消 false 不取消  默认true
     */
    public TipDialog setCancelable(boolean cancelable) {
        if (dialog != null)
            dialog.setCancelable(cancelable);
        return this;
    }

    /**
     * 点击Dialog外围是否取消
     *
     * @param cancelable true 取消 false 不取消  默认true
     */
    public TipDialog setCanceledOnTouchOutside(boolean cancelable) {
        if (dialog != null)
            dialog.setCanceledOnTouchOutside(cancelable);
        return this;
    }

    /**
     * 设置取消事件
     *
     * @param onCancelListener 取消事件
     */
    public TipDialog setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        if (dialog != null)
            dialog.setOnCancelListener(onCancelListener);
        return this;
    }

    /**
     * 设置Dialog显示位置
     *
     * @param tipDialogGravity 左上右下中
     */
    public TipDialog setTipDialogGravity(TipDialogGravity tipDialogGravity) {
        Window window = dialog.getWindow();
        int gravity = Gravity.CENTER;
        if (tipDialogGravity == TipDialogGravity.GRAVITY_BOTTOM) {
            gravity = Gravity.BOTTOM;
        } else if (tipDialogGravity == TipDialogGravity.GRAVITY_CENTER) {
            gravity = Gravity.CENTER;
        } else if (tipDialogGravity == TipDialogGravity.GRAVITY_LEFT) {
            gravity = Gravity.START;
        } else if (tipDialogGravity == TipDialogGravity.GRAVITY_RIGHT) {
            gravity = Gravity.END;
        } else if (tipDialogGravity == TipDialogGravity.GRAVITY_TOP) {
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
    public TipDialog setDimAmount(float dimAmount) {
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
     * 设置Window动画
     *
     * @param style R文件
     */
    public TipDialog setWindowAnimations(int style) {
        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null) {
                window.setWindowAnimations(style);
            }
        }
        return this;
    }

    /**
     * 设置Dialog宽度
     *
     * @param proportion 和屏幕的宽度比(10代表10%) 0~100
     */
    public TipDialog setTipDialogWidth(int proportion) {
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
    public TipDialog setTipDialogHeight(int proportion) {
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
    public void showTipDialog() {
        if (dialog != null)
            dialog.show();
    }

    /**
     * 关闭Dialog
     */
    public void closeTipDialog() {
        if (dialog != null)
            dialog.cancel();
    }

    // 确定事件监听
    public interface OnTipEnsureListener {
        void onEnsure();
    }

    private OnTipEnsureListener onTipEnsureListener;

    public void setOnTipEnsureListener(OnTipEnsureListener onTipEnsureListener) {
        this.onTipEnsureListener = onTipEnsureListener;
    }

    // 取消事件监听
    public interface OnTipCancelListener {
        void onCancel();
    }

    private OnTipCancelListener onTipCancelListener;

    public void setOnTipCancelListener(OnTipCancelListener onTipCancelListener) {
        this.onTipCancelListener = onTipCancelListener;
    }
}
