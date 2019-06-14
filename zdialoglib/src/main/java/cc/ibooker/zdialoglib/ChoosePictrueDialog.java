package cc.ibooker.zdialoglib;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

/**
 * 选择图片Dialog
 * Created by 邹峰立 on 2017/7/10.
 * https://github.com/zrunker/ZDialog
 */
public class ChoosePictrueDialog {
    private Context context;
    private Dialog dialog;
    private Button localBtn, photoBtn, cancelBtn;

    public enum ChoosePictrueDialogGravity {
        GRAVITY_BOTTOM,
        GRAVITY_CENTER,
        GRAVITY_LEFT,
        GRAVITY_RIGHT,
        GRAVITY_TOP
    }

    public Dialog getDialog() {
        return dialog;
    }

    public Button getLocalBtn() {
        return localBtn;
    }

    public Button getPhotoBtn() {
        return photoBtn;
    }

    public Button getCancelBtn() {
        return cancelBtn;
    }

    public ChoosePictrueDialog(@NonNull Context context) {
        this(context, R.style.zdialog_diyDialog);
        this.context = context;
    }

    public ChoosePictrueDialog(@NonNull Context context, @StyleRes int themeResId) {
        this.context = context;
        dialog = new Dialog(context, themeResId);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.zdialog_layout_choose_pictrue, null);
        localBtn = view.findViewById(R.id.btn_local);
        localBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ZDialogClickUtil.isFastClick())
                    return;
                closeChoosePictrueDialog();
                if (onLocalListener != null) {
                    onLocalListener.onLocal();
                } else {
                    // 启动本地相册
                    ChoosePictrueUtil.startLocal(context);
                }
            }
        });
        photoBtn = view.findViewById(R.id.btn_photo);
        photoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ZDialogClickUtil.isFastClick())
                    return;
                closeChoosePictrueDialog();
                startPhoto();
            }
        });
        cancelBtn = view.findViewById(R.id.btn_cancel);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ZDialogClickUtil.isFastClick())
                    return;
                closeChoosePictrueDialog();
                if (onCancelListener != null) {
                    onCancelListener.onCancel();
                }
            }
        });
        dialog.setContentView(view);

        // 按返回键是否取消
        dialog.setCancelable(true);
        // 点击Dialog外围是否取消
        dialog.setCanceledOnTouchOutside(true);
        // 设置居中
        this.setChoosePictrueDialogGravity(ChoosePictrueDialogGravity.GRAVITY_CENTER);
        // 设置宽度
        this.setChoosePictrueDialogWidth(75);
    }

    // 是否展示
    public boolean isShowing() {
        return dialog != null && dialog.isShowing();
    }

    // 启动拍照
    public void startPhoto() {
        if (onPhotoListener != null) {
            onPhotoListener.onPhoto();
        } else {
            // 判断是否需要拍照权限
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, ZDialogConstantUtil.PERMISSION_CAMERA_REQUEST_CODE);
            } else {
                // 判断是否需要sdcard中读取数据的权限
                int checkCallSDReadPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
                if (checkCallSDReadPermission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, ZDialogConstantUtil.PERMISSION_READ_EXTERNAL_STORAGE_REQUEST_CODE);
                } else {
                    // 判断是否需要写入数据到扩展存储卡(SD)的权限
                    int checkCallSDWritePermission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (checkCallSDWritePermission != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, ZDialogConstantUtil.PERMISSION_WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                    } else {
                        // 启动拍照
                        ChoosePictrueUtil.startPhoto3(context);
                    }
                }
            }
        }
    }

    /**
     * 设置三个按钮的高度
     *
     * @param pixels 待设置高度px
     */
    public ChoosePictrueDialog setBtnHeight(int pixels) {
        try {
            localBtn.getLayoutParams().height = pixels;
            photoBtn.getLayoutParams().height = pixels;
            cancelBtn.getLayoutParams().height = pixels;
//            localBtn.setHeight(pixels);
//            photoBtn.setHeight(pixels);
//            cancelBtn.setHeight(pixels);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 设置三个按钮的字体颜色
     *
     * @param color 颜色值16进制
     */
    public ChoosePictrueDialog setBtnColor(String color) {
        try {
            localBtn.setTextColor(Color.parseColor(color));
            photoBtn.setTextColor(Color.parseColor(color));
            cancelBtn.setTextColor(Color.parseColor(color));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 设置三个按钮的字体大小
     *
     * @param size 字体大小值
     */
    public ChoosePictrueDialog setBtnSize(float size) {
        localBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        photoBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        cancelBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        return this;
    }

    /**
     * 修改本地按钮文本颜色
     *
     * @param color 文本颜色
     */
    public ChoosePictrueDialog setLocalBtnColor(String color) {
        try {
            localBtn.setTextColor(Color.parseColor(color));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 修改拍照按钮文本颜色
     *
     * @param color 文本颜色
     */
    public ChoosePictrueDialog setPhotoBtnColor(String color) {
        try {
            photoBtn.setTextColor(Color.parseColor(color));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 修改取消按钮文本颜色
     *
     * @param color 文本颜色
     */
    public ChoosePictrueDialog setCancelBtnColor(String color) {
        try {
            cancelBtn.setTextColor(Color.parseColor(color));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 修改本地按钮文本字体大小
     *
     * @param size 字体大小
     */
    public ChoosePictrueDialog setLocalBtnSize(float size) {
        localBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        return this;
    }

    /**
     * 修改拍照按钮文本字体大小
     *
     * @param size 字体大小
     */
    public ChoosePictrueDialog setPhotoBtnSize(float size) {
        photoBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        return this;
    }

    /**
     * 修改取消按钮文本字体大小
     *
     * @param size 字体大小
     */
    public ChoosePictrueDialog setCancelBtnSize(float size) {
        cancelBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        return this;
    }

    /**
     * 修改本地按钮文本
     *
     * @param text 文本信息
     */
    public ChoosePictrueDialog setLocalBtnText(CharSequence text) {
        if (!TextUtils.isEmpty(text))
            localBtn.setText(text);
        return this;
    }

    /**
     * 修改拍照按钮文本
     *
     * @param text 文本信息
     */
    public ChoosePictrueDialog setPhotoBtnText(CharSequence text) {
        if (!TextUtils.isEmpty(text))
            photoBtn.setText(text);
        return this;
    }

    /**
     * 修改取消按钮文本
     *
     * @param text 文本信息
     */
    public ChoosePictrueDialog setCancelBtnText(CharSequence text) {
        if (!TextUtils.isEmpty(text))
            cancelBtn.setText(text);
        return this;
    }

    /**
     * 按返回键是否取消
     *
     * @param cancelable true 取消 false 不取消  默认true
     */
    public ChoosePictrueDialog setCancelable(boolean cancelable) {
        if (dialog != null)
            dialog.setCancelable(cancelable);
        return this;
    }

    /**
     * 点击Dialog外围是否取消
     *
     * @param cancelable true 取消 false 不取消  默认false
     */
    public ChoosePictrueDialog setCanceledOnTouchOutside(boolean cancelable) {
        if (dialog != null)
            dialog.setCanceledOnTouchOutside(cancelable);
        return this;
    }

    /**
     * 设置取消事件
     *
     * @param onCancelListener 取消事件
     */
    public ChoosePictrueDialog setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        if (dialog != null)
            dialog.setOnCancelListener(onCancelListener);
        return this;
    }

    /**
     * 设置Dialog显示位置
     *
     * @param choosePictrueDialogGravity 左上右下中
     */
    public ChoosePictrueDialog setChoosePictrueDialogGravity(ChoosePictrueDialogGravity choosePictrueDialogGravity) {
        Window window = dialog.getWindow();
        int gravity = Gravity.CENTER;
        if (choosePictrueDialogGravity == ChoosePictrueDialogGravity.GRAVITY_BOTTOM) {
            gravity = Gravity.BOTTOM;
        } else if (choosePictrueDialogGravity == ChoosePictrueDialogGravity.GRAVITY_CENTER) {
            gravity = Gravity.CENTER;
        } else if (choosePictrueDialogGravity == ChoosePictrueDialogGravity.GRAVITY_LEFT) {
            gravity = Gravity.START;
        } else if (choosePictrueDialogGravity == ChoosePictrueDialogGravity.GRAVITY_RIGHT) {
            gravity = Gravity.END;
        } else if (choosePictrueDialogGravity == ChoosePictrueDialogGravity.GRAVITY_TOP) {
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
    public ChoosePictrueDialog setDimAmount(float dimAmount) {
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
    public ChoosePictrueDialog setWindowAnimations(int style) {
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
    public ChoosePictrueDialog setChoosePictrueDialogWidth(int proportion) {
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
    public ChoosePictrueDialog setChoosePictrueDialogHeight(int proportion) {
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
        if (wm != null)
            wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    /**
     * 展示Dialog
     */
    public void showChoosePictrueDialog() {
        if (dialog != null)
            dialog.show();
    }

    /**
     * 关闭Dialog
     */
    public void closeChoosePictrueDialog() {
        if (dialog != null)
            dialog.cancel();
    }

    // 本地按钮点击接口
    public interface OnLocalListener {
        void onLocal();
    }

    private OnLocalListener onLocalListener;

    public void setOnLocalListener(OnLocalListener onLocalListener) {
        this.onLocalListener = onLocalListener;
    }

    // 拍照按钮点击接口
    public interface OnPhotoListener {
        void onPhoto();
    }

    private OnPhotoListener onPhotoListener;

    public void setOnPhotoListener(OnPhotoListener onPhotoListener) {
        this.onPhotoListener = onPhotoListener;
    }

    // 取消按钮点击接口
    public interface OnCancelListener {
        void onCancel();
    }

    private OnCancelListener onCancelListener;

    public void setOnCancelListener(OnCancelListener onCancelListener) {
        this.onCancelListener = onCancelListener;
    }

}
