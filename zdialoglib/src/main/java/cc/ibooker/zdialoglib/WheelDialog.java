package cc.ibooker.zdialoglib;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import cc.ibooker.zdialoglib.bean.WheelDialogBean;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * 查看图片Dialog
 *
 * @author 邹峰立
 * https://github.com/zrunker/ZDialog
 */
public class WheelDialog {
    private Context context;
    private Dialog dialog;
    private ViewPager viewPager;
    private WheelPagerAdapter wheelPagerAdapter;
    private LinearLayout dotLayout;
    private TextView textView;
    private ArrayList<WheelDialogBean> mDatas;
    private int selectedRes, defaultRes;
    private ImageView[] mImageViews;
    private boolean isIndicatorVisible = true;// 标记指示器是否可见

    public enum PageIndicatorAlign {
        ALIGN_PARENT_LEFT, ALIGN_PARENT_RIGHT, CENTER_HORIZONTAL
    }

    public enum WheelDialogGravity {
        GRAVITY_CENTER,
        GRAVITY_LEFT,
        GRAVITY_RIGHT,
        GRAVITY_TOP,
        GRAVITY_BOTTOM
    }

    public Dialog getDialog() {
        return dialog;
    }

    public LinearLayout getDotLayout() {
        return dotLayout;
    }

    public void setDotLayout(LinearLayout dotLayout) {
        this.dotLayout = dotLayout;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }

    public WheelDialog(@NonNull Context context) {
        this(context, R.style.zdialog_translucentDialog);
    }

    public WheelDialog(@NonNull Context context, @StyleRes int themeResId) {
        this.dialog = new Dialog(context, themeResId);
        this.context = context;
        init();
    }

    // 初始化
    private void init() {
        dialog.setContentView(R.layout.zdialog_layout_wheel_dialog);

        viewPager = dialog.findViewById(R.id.viewPager);
        dotLayout = dialog.findViewById(R.id.dotLayout);
        textView = dialog.findViewById(R.id.textView);

        // 按返回键是否取消
        dialog.setCancelable(true);
        // 点击Dialog外围是否取消
        dialog.setCanceledOnTouchOutside(true);
        // 设置宽度
        setWheelDialogWidth(100);
    }

    // 设置数据
    public WheelDialog setDatas(final ArrayList<WheelDialogBean> datas, String currentUrl) {
        if (datas != null && datas.size() > 0) {
            mDatas = datas;
            textView.setText(datas.get(0).getName());
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                @Override
                public void onPageScrolled(int i, float v, int i1) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (isIndicatorVisible && datas.size() > position && position >= 0) {
                        textView.setText(datas.get(position).getName());
                        if (mImageViews != null) {
                            for (int i = 0; i < mImageViews.length; i++) {
                                mImageViews[position].setBackgroundResource(selectedRes);
                                if (position != i) {
                                    mImageViews[i].setBackgroundResource(defaultRes);
                                }
                            }
                        }
                    }
                }

                @Override
                public void onPageScrollStateChanged(int i) {

                }
            });
            // 初始化viewPager
            setWheelPagerAdapter(datas);
            // 初始化dotLayout
            setPageIndicator(R.drawable.zdialog_bg_dot_cccccc_8, R.drawable.zdialog_bg_dot_3e3e3e_8);
            // 设置缓存
            viewPager.setOffscreenPageLimit(datas.size());
            // 设置当前页
            if (!TextUtils.isEmpty(currentUrl)) {
                int realPosition = 0;
                for (int i = 0; i < datas.size(); i++) {
                    WheelDialogBean data = datas.get(i);
                    if (currentUrl.equals(data.getUrl())) {
                        realPosition = i;
                        break;
                    }
                }
                viewPager.setCurrentItem(realPosition);
            }
        }
        return this;
    }

    // 自定义setPicPagerAdapter
    private void setWheelPagerAdapter(ArrayList<WheelDialogBean> datas) {
        if (wheelPagerAdapter == null) {
            wheelPagerAdapter = new WheelPagerAdapter(context, datas);
            viewPager.setAdapter(wheelPagerAdapter);
        } else {
            wheelPagerAdapter.reflushData(datas);
        }
    }

    /**
     * 底部指示器资源图片
     *
     * @param selectedRes 选中图标地址
     * @param defaultRes  未选中图标地址
     */
    public WheelDialog setPageIndicator(int selectedRes, int defaultRes) {
        this.selectedRes = selectedRes;
        this.defaultRes = defaultRes;
        if (mDatas != null && mDatas.size() > 0) {
            dotLayout.removeAllViews();
            mImageViews = new ImageView[mDatas.size()];
            // 小图标
            for (int k = 0; k < mDatas.size(); k++) {
                ImageView mImageView = new ImageView(context);
                LinearLayout.LayoutParams dotParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dotParams.setMargins(10, 0, 10, 0);
                dotParams.gravity = Gravity.CENTER_VERTICAL;
                mImageView.setLayoutParams(dotParams);

                mImageViews[k] = mImageView;
                if (k == 0) {// 选中
                    mImageViews[k].setBackgroundResource(selectedRes);
                } else {// 未选中
                    mImageViews[k].setBackgroundResource(defaultRes);
                }
                dotLayout.addView(mImageViews[k]);
            }
        }
        return this;
    }

    /**
     * 指示器的方向
     *
     * @param align 三个方向：居左 ，居中 ，居右
     */
    public WheelDialog setPageIndicatorAlign(PageIndicatorAlign align) {
        if (align == PageIndicatorAlign.ALIGN_PARENT_LEFT) {
            dotLayout.setGravity(Gravity.START);
        } else if (align == PageIndicatorAlign.ALIGN_PARENT_RIGHT) {
            dotLayout.setGravity(Gravity.END);
        } else if (align == PageIndicatorAlign.CENTER_HORIZONTAL) {
            dotLayout.setGravity(Gravity.CENTER);
        }
        return this;
    }

    /**
     * 设置指示器是否可见
     *
     * @param isVisible true 可见  false 不可见 默认true
     */
    public WheelDialog setPointViewVisible(boolean isVisible) {
        if (dotLayout != null) {
            isIndicatorVisible = isVisible;
            dotLayout.setVisibility(isVisible ? VISIBLE : GONE);
        }
        return this;
    }

    // 是否展示
    public boolean isShowing() {
        return dialog != null && dialog.isShowing();
    }

    /**
     * 设置Dialog宽度
     *
     * @param proportion 和屏幕的宽度比(10代表10%) 0~100
     */
    public WheelDialog setWheelDialogWidth(int proportion) {
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
    public WheelDialog setWheelDialogHeight(int proportion) {
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
     * 按返回键是否取消
     *
     * @param cancelable true 取消 false 不取消  默认true
     */
    public WheelDialog setCancelable(boolean cancelable) {
        if (dialog != null)
            dialog.setCancelable(cancelable);
        return this;
    }

    /**
     * 设置取消事件
     *
     * @param onCancelListener 取消事件
     */
    public WheelDialog setOnCancelListener(DialogInterface.OnCancelListener onCancelListener) {
        if (dialog != null)
            dialog.setOnCancelListener(onCancelListener);
        return this;
    }

    /**
     * 设置Dialog显示位置
     *
     * @param wheelDialogGravity 左上右下中
     */
    public WheelDialog setWheelDialogGravity(WheelDialogGravity wheelDialogGravity) {
        Window window = dialog.getWindow();
        int gravity = Gravity.CENTER;
        if (wheelDialogGravity == WheelDialogGravity.GRAVITY_BOTTOM) {
            gravity = Gravity.BOTTOM;
        } else if (wheelDialogGravity == WheelDialogGravity.GRAVITY_CENTER) {
            gravity = Gravity.CENTER;
        } else if (wheelDialogGravity == WheelDialogGravity.GRAVITY_LEFT) {
            gravity = Gravity.START;
        } else if (wheelDialogGravity == WheelDialogGravity.GRAVITY_RIGHT) {
            gravity = Gravity.END;
        } else if (wheelDialogGravity == WheelDialogGravity.GRAVITY_TOP) {
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
    public WheelDialog setDimAmount(float dimAmount) {
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
    public WheelDialog setWindowAnimations(int style) {
        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null) {
                window.setWindowAnimations(style);
            }
        }
        return this;
    }

    /**
     * 点击Dialog外围是否取消
     *
     * @param cancelable true 取消 false 不取消  默认true
     */
    public WheelDialog setCanceledOnTouchOutside(boolean cancelable) {
        if (dialog != null)
            dialog.setCanceledOnTouchOutside(cancelable);
        return this;
    }

    /**
     * 修改游标指示器整体Padding - px
     */
    public WheelDialog setDotLayoutPadding(int left, int top, int right, int bottom) {
        if (dotLayout != null) {
            dotLayout.setPadding(left, top, right, bottom);
        }
        return this;
    }

    /**
     * 修改游标指示器整体Margin - px
     */
    public WheelDialog setDotLayoutMargin(int leftMargin, int topMargin, int rightMargin, int bottomMargin) {
        if (dotLayout != null) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) dotLayout.getLayoutParams();
            layoutParams.leftMargin = leftMargin;
            layoutParams.topMargin = topMargin;
            layoutParams.rightMargin = rightMargin;
            layoutParams.bottomMargin = bottomMargin;
            dotLayout.invalidate();
        }
        return this;
    }

    /**
     * 修改TextView整体Padding - px
     */
    public WheelDialog setTextViewPadding(int left, int top, int right, int bottom) {
        if (textView != null) {
            textView.setPadding(left, top, right, bottom);
        }
        return this;
    }

    /**
     * 修改TextView整体Margin - px
     */
    public WheelDialog setTextViewMargin(int leftMargin, int topMargin, int rightMargin, int bottomMargin) {
        if (textView != null) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) textView.getLayoutParams();
            layoutParams.leftMargin = leftMargin;
            layoutParams.topMargin = topMargin;
            layoutParams.rightMargin = rightMargin;
            layoutParams.bottomMargin = bottomMargin;
            textView.invalidate();
        }
        return this;
    }

    /**
     * 修改TextView字体大小
     *
     * @param textSize 字体大小
     */
    public WheelDialog setTextViewSize(int textSize) {
        if (textView != null) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        }
        return this;
    }

    /**
     * 修改TextView字体颜色
     *
     * @param color 字体颜色 16进制
     */
    public WheelDialog setTextViewColor(String color) {
        try {
            if (textView != null && !TextUtils.isEmpty(color)) {
                textView.setTextColor(Color.parseColor(color));
                textView.invalidate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 展示Dialog
     */
    public void showWheelDialog() {
        if (dialog != null)
            dialog.show();
    }

    /**
     * 关闭Dialog
     */
    public void closeWheelDialog() {
        if (dialog != null)
            dialog.cancel();
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

}
