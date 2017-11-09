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
import android.widget.Button;

/**
 * 删除Dialog
 * Created by 邹峰立 on 2017/7/5.
 */
public class DelDialog {
    private Context context;
    private Dialog dialog;
    private Button delBtn, cancelBtn;

    public enum DelDialogGravity {
        GRAVITY_CENTER,
        GRAVITY_LEFT,
        GRAVITY_RIGHT,
        GRAVITY_TOP,
        GRAVITY_BOTTOM
    }

    public DelDialog(@NonNull Context context) {
        this(context, R.style.diydialog);
    }

    public DelDialog(@NonNull Context context, @StyleRes int themeResId) {
        dialog = new Dialog(context, themeResId);
        this.context = context;
        init();
    }

    /**
     * 初始化控件
     */
    private void init() {
        dialog.setContentView(R.layout.layout_del_dialog);
        delBtn = (Button) dialog.findViewById(R.id.btn_del);
        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ZDialogClickUtil.isFastClick())
                    return;
                closeDelDialog();
                if (onDelListener != null)
                    onDelListener.onDel();
            }
        });
        cancelBtn = (Button) dialog.findViewById(R.id.btn_cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ZDialogClickUtil.isFastClick())
                    return;
                closeDelDialog();
                if (onDelCancelListener != null)
                    onDelCancelListener.onCancel();
            }
        });

        // 按返回键是否取消
        dialog.setCancelable(true);
        // 点击Dialog外围是否取消
        dialog.setCanceledOnTouchOutside(true);
        // 设置默认透明度0.2f
        this.setDimAmount(0.2f);
        // 设置默认居中显示
        this.setDelDialogGravity(DelDialogGravity.GRAVITY_CENTER);
        // 设置和屏幕的宽度比
        this.setDelDialogWidth(60);
    }

    /**
     * 设置DelBtn文字大小
     *
     * @param size 文字大小
     */
    public DelDialog setDelBtnSize(float size) {
        if (delBtn != null)
            delBtn.setTextSize(size);
        return this;
    }

    /**
     * 设置DelBtn文字颜色
     *
     * @param color 文字颜色
     */
    public DelDialog setDelBtnColor(String color) {
        try {
            if (delBtn != null && !TextUtils.isEmpty(color))
                delBtn.setTextColor(Color.parseColor(color));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 设置CancelBtn文字大小
     *
     * @param size 文字大小
     */
    public DelDialog setCancelBtnSize(float size) {
        if (cancelBtn != null)
            cancelBtn.setTextSize(size);
        return this;
    }

    /**
     * 设置CancelBtn文字颜色
     *
     * @param color 文字颜色
     */
    public DelDialog setCancelBtnColor(String color) {
        try {
            if (cancelBtn != null && !TextUtils.isEmpty(color))
                cancelBtn.setTextColor(Color.parseColor(color));
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
    public DelDialog setCancelable(boolean cancelable) {
        if (dialog != null)
            dialog.setCancelable(cancelable);
        return this;
    }

    /**
     * 点击Dialog外围是否取消
     *
     * @param cancelable true 取消 false 不取消  默认false
     */
    public DelDialog setCanceledOnTouchOutside(boolean cancelable) {
        if (dialog != null)
            dialog.setCanceledOnTouchOutside(cancelable);
        return this;
    }

    /**
     * 设置取消事件
     *
     * @param onCancelListener 取消事件
     */
    public DelDialog setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        if (dialog != null)
            dialog.setOnCancelListener(onCancelListener);
        return this;
    }

    /**
     * 设置Dialog宽度
     *
     * @param proportion 和屏幕的宽度比(10代表10%) 0~100
     */
    public DelDialog setDelDialogWidth(int proportion) {
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
    public DelDialog setDelDialogHeight(int proportion) {
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
    public DelDialog setWindowAnimations(int style) {
        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null) {
                window.setWindowAnimations(style);
            }
        }
        return this;
    }

    /**
     * 设置Dialog显示位置
     *
     * @param delDialogGravity 左上右下中
     */
    public DelDialog setDelDialogGravity(DelDialogGravity delDialogGravity) {
        if (dialog != null) {
            Window window = dialog.getWindow();
            int gravity = Gravity.CENTER;
            if (delDialogGravity == DelDialogGravity.GRAVITY_BOTTOM) {
                gravity = Gravity.BOTTOM;
            } else if (delDialogGravity == DelDialogGravity.GRAVITY_CENTER) {
                gravity = Gravity.CENTER;
            } else if (delDialogGravity == DelDialogGravity.GRAVITY_LEFT) {
                gravity = Gravity.START;
            } else if (delDialogGravity == DelDialogGravity.GRAVITY_RIGHT) {
                gravity = Gravity.END;
            } else if (delDialogGravity == DelDialogGravity.GRAVITY_TOP) {
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
    public DelDialog setDimAmount(float dimAmount) {
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
     * 展示Dialog
     */
    public void showDelDialog() {
        if (dialog != null)
            dialog.show();
    }

    /**
     * 关闭Dialog
     */
    public void closeDelDialog() {
        if (dialog != null) {
            dialog.cancel();
        }
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

    // 执行删除接口
    public interface OnDelListener {
        void onDel();
    }

    private OnDelListener onDelListener;

    public void setOnDelListener(OnDelListener onDelListener) {
        this.onDelListener = onDelListener;
    }

    // 执行取消接口
    public interface OnDelCancelListener {
        void onCancel();
    }

    private OnDelCancelListener onDelCancelListener;

    public void setOnDelCancelListener(OnDelCancelListener onDelCancelListener) {
        this.onDelCancelListener = onDelCancelListener;
    }

}