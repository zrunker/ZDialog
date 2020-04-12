package cc.ibooker.zdialoglib;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 提示Dialog
 * Created by 邹峰立 on 2017/7/5.
 * https://github.com/zrunker/ZDialog
 */
public class TipDialog {
    private Context context;
    private Dialog dialog;
    private LinearLayout topLayout;
    private TextView titleTv, descTv, ensureTv, cancelTv;

    public enum TipDialogGravity {
        GRAVITY_CENTER,
        GRAVITY_LEFT,
        GRAVITY_RIGHT,
        GRAVITY_TOP,
        GRAVITY_BOTTOM
    }

    public Dialog getDialog() {
        return dialog;
    }

    public LinearLayout getTopLayout() {
        return topLayout;
    }

    public TextView getTitleTv() {
        return titleTv;
    }

    public TextView getDescTv() {
        return descTv;
    }

    public TextView getEnsureTv() {
        return ensureTv;
    }

    public TextView getCancelTv() {
        return cancelTv;
    }

    public TipDialog(@NonNull Context context) {
        this(context, R.style.zdialog_diyDialog);
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
        dialog.setContentView(R.layout.zdialog_layout_tip_dialog);
        topLayout = dialog.findViewById(R.id.top_layout);
        titleTv = dialog.findViewById(R.id.tv_title);
        titleTv.setGravity(Gravity.CENTER);
        descTv = dialog.findViewById(R.id.tv_desc);
        descTv.setGravity(Gravity.CENTER);
        ensureTv = dialog.findViewById(R.id.tv_ensure);
        ensureTv.setGravity(Gravity.CENTER);
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
        cancelTv.setGravity(Gravity.CENTER);
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

    // 是否展示
    public boolean isShowing() {
        return dialog != null && dialog.isShowing();
    }

    /**
     * 修改顶部整体显示位置
     *
     * @param gravity 位置
     */
    public TipDialog setTopLayoutGravity(int gravity) {
        if (topLayout != null
                && (gravity == Gravity.CENTER_HORIZONTAL
                || gravity == Gravity.CENTER
                || gravity == Gravity.BOTTOM
                || gravity == Gravity.TOP
                || gravity == Gravity.START
                || gravity == Gravity.END
                || gravity == Gravity.CENTER_VERTICAL
                || gravity == Gravity.NO_GRAVITY)) {
            topLayout.setGravity(gravity);
        }
        return this;
    }

    /**
     * 修改顶部整体Padding - px
     */
    public TipDialog setTopLayoutPadding(int left, int top, int right, int bottom) {
        if (topLayout != null) {
            topLayout.setPadding(left, top, right, bottom);
        }
        return this;
    }

    /**
     * 修改顶部整体Margin - px
     */
    public TipDialog setTopLayoutMargin(int leftMargin, int topMargin, int rightMargin, int bottomMargin) {
        if (topLayout != null) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) topLayout.getLayoutParams();
            layoutParams.leftMargin = leftMargin;
            layoutParams.topMargin = topMargin;
            layoutParams.rightMargin = rightMargin;
            layoutParams.bottomMargin = bottomMargin;
            topLayout.invalidate();
        }
        return this;
    }

    /**
     * 修改主题文字
     *
     * @param text 文字内容
     */
    public TipDialog setTitleText(CharSequence text) {
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
            titleTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
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
     * 修改主题文字高度
     *
     * @param height px 高度
     */
    public TipDialog setTitleHeight(int height) {
        try {
            if (titleTv != null && height > 0) {
                titleTv.setHeight(height);
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
     * 修改主题显示位置
     *
     * @param gravity 位置
     */
    public TipDialog setTitleGravity(int gravity) {
        if (titleTv != null
                && (gravity == Gravity.CENTER_HORIZONTAL
                || gravity == Gravity.CENTER
                || gravity == Gravity.BOTTOM
                || gravity == Gravity.TOP
                || gravity == Gravity.START
                || gravity == Gravity.END
                || gravity == Gravity.CENTER_VERTICAL
                || gravity == Gravity.NO_GRAVITY)) {
            titleTv.setGravity(gravity);
        }
        return this;
    }

    /**
     * 修改描述文字
     *
     * @param text 文字内容
     */
    public TipDialog setDescText(CharSequence text) {
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
            descTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
            descTv.invalidate();
        }
        return this;
    }

    /**
     * 修改描述文字颜色
     *
     * @param color 文字颜色 16进制
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
     * 修改描述高度
     *
     * @param height px 高度
     */
    public TipDialog setDescHeight(int height) {
        if (descTv != null && height > 0) {
            descTv.setHeight(height);
            descTv.invalidate();
        }
        return this;
    }

    /**
     * 修改描述显示位置
     *
     * @param gravity 位置
     */
    public TipDialog setDescGravity(int gravity) {
        if (descTv != null
                && (gravity == Gravity.CENTER_HORIZONTAL
                || gravity == Gravity.CENTER
                || gravity == Gravity.BOTTOM
                || gravity == Gravity.TOP
                || gravity == Gravity.START
                || gravity == Gravity.END
                || gravity == Gravity.CENTER_VERTICAL
                || gravity == Gravity.NO_GRAVITY)) {
            descTv.setGravity(gravity);
        }
        return this;
    }

    /**
     * 修改确定文字
     *
     * @param text 文字内容
     */
    public TipDialog setEnsureText(CharSequence text) {
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
            ensureTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
            ensureTv.invalidate();
        }
        return this;
    }

    /**
     * 修改确定文字颜色
     *
     * @param color 文字颜色 16进制
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
     * 修改确定文字高度
     *
     * @param height px 高度
     */
    public TipDialog setEnsureHeight(int height) {
        if (ensureTv != null && height > 0) {
            ensureTv.setHeight(height);
            ensureTv.invalidate();
        }
        return this;
    }

    /**
     * 修改确定文字显示位置
     *
     * @param gravity 位置
     */
    public TipDialog setEnsureGravity(int gravity) {
        if (ensureTv != null
                && (gravity == Gravity.CENTER_HORIZONTAL
                || gravity == Gravity.CENTER
                || gravity == Gravity.BOTTOM
                || gravity == Gravity.TOP
                || gravity == Gravity.START
                || gravity == Gravity.END
                || gravity == Gravity.CENTER_VERTICAL
                || gravity == Gravity.NO_GRAVITY)) {
            ensureTv.setGravity(gravity);
        }
        return this;
    }

    /**
     * 修改取消文字
     *
     * @param text 文字内容
     */
    public TipDialog setCancelText(CharSequence text) {
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
            cancelTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
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
     * 修改取消文字高度
     *
     * @param height px 高度
     */
    public TipDialog setCancelHeight(int height) {
        if (cancelTv != null && height > 0) {
            cancelTv.setHeight(height);
            cancelTv.invalidate();
        }
        return this;
    }

    /**
     * 修改取消文字显示位置
     *
     * @param gravity 位置
     */
    public TipDialog setCancelGravity(int gravity) {
        if (cancelTv != null
                && (gravity == Gravity.CENTER_HORIZONTAL
                || gravity == Gravity.CENTER
                || gravity == Gravity.BOTTOM
                || gravity == Gravity.TOP
                || gravity == Gravity.START
                || gravity == Gravity.END
                || gravity == Gravity.CENTER_VERTICAL
                || gravity == Gravity.NO_GRAVITY)) {
            cancelTv.setGravity(gravity);
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
    public TipDialog showTipDialog() {
        if (dialog != null && !dialog.isShowing())
            dialog.show();
        return this;
    }

    /**
     * 关闭Dialog
     */
    public TipDialog closeTipDialog() {
        if (dialog != null)
            dialog.cancel();
        return this;
    }

    /**
     * 设置背景
     *
     * @param resource 资源文件地址
     */
    public TipDialog setBackgroundDrawable(int resource) {
        if (dialog != null && dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawableResource(resource);
        return this;
    }

    /**
     * 设置背景
     *
     * @param drawable drawable资源
     */
    public TipDialog setBackgroundDrawable(Drawable drawable) {
        if (dialog != null && dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(drawable);
        return this;
    }

    // 确定事件监听
    public interface OnTipEnsureListener {
        void onEnsure();
    }

    private OnTipEnsureListener onTipEnsureListener;

    public TipDialog setOnTipEnsureListener(OnTipEnsureListener onTipEnsureListener) {
        this.onTipEnsureListener = onTipEnsureListener;
        return this;
    }

    // 取消事件监听
    public interface OnTipCancelListener {
        void onCancel();
    }

    private OnTipCancelListener onTipCancelListener;

    public TipDialog setOnTipCancelListener(OnTipCancelListener onTipCancelListener) {
        this.onTipCancelListener = onTipCancelListener;
        return this;
    }
}
