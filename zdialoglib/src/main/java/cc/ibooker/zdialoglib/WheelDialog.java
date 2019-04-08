package cc.ibooker.zdialoglib;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import cc.ibooker.zdialoglib.bean.WheelDialogBean;

/**
 * 查看图片Dialog
 *
 * @author 邹峰立
 */
public class WheelDialog {
    private Context context;
    private Dialog dialog;
    private ViewPager viewPager;
    private WheelPagerAdapter wheelPagerAdapter;
    private LinearLayout dotLayout;
    private TextView textView;
    private ArrayList<WheelDialogBean> mDatas;
    private int selectedRes, defalutRes;
    private ImageView[] mImageViews;

    public enum PageIndicatorAlign {
        ALIGN_PARENT_LEFT, ALIGN_PARENT_RIGHT, CENTER_HORIZONTAL
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
        this(context, R.style.translucentDialog);
    }

    public WheelDialog(@NonNull Context context, @StyleRes int themeResId) {
        this.dialog = new Dialog(context, themeResId);
        this.context = context;
        init();
    }

    // 初始化
    private void init() {
        dialog.setContentView(R.layout.layout_wheel_dialog);

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
    public WheelDialog setDatas(final ArrayList<WheelDialogBean> datas) {
        if (datas != null && datas.size() > 0) {
            mDatas = datas;
            textView.setText(datas.get(0).getName());
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                @Override
                public void onPageScrolled(int i, float v, int i1) {

                }

                @Override
                public void onPageSelected(int position) {
                    if (datas.size() > position && position >= 0) {
                        textView.setText(datas.get(position).getName());
                        if (mImageViews != null) {
                            for (int i = 0; i < mImageViews.length; i++) {
                                mImageViews[position].setBackgroundResource(selectedRes);
                                if (position != i) {
                                    mImageViews[i].setBackgroundResource(defalutRes);
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
            setPageIndicator(R.drawable.bg_dot_cccccc_8, R.drawable.bg_dot_3e3e3e_8);
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
     * @param defalutRes  未选中图标地址
     */
    public WheelDialog setPageIndicator(int selectedRes, int defalutRes) {
        this.selectedRes = selectedRes;
        this.defalutRes = defalutRes;
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
                    mImageViews[k].setBackgroundResource(defalutRes);
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
     * 修改顶部整体Padding - px
     */
    public WheelDialog setDotLayoutPadding(int left, int top, int right, int bottom) {
        if (dotLayout != null) {
            dotLayout.setPadding(left, top, right, bottom);
        }
        return this;
    }

    /**
     * 修改顶部整体Margin - px
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
     * 展示Dialog
     */
    public void showPicDialog() {
        if (dialog != null)
            dialog.show();
    }

    /**
     * 关闭Dialog
     */
    public void closePicDialog() {
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

}
