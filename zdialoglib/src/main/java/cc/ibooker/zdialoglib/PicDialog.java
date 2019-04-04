package cc.ibooker.zdialoglib;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import cc.ibooker.zdialoglib.bean.PicDialogBean;

/**
 * 查看图片Dialog
 *
 * @author 邹峰立
 */
public class PicDialog {
    private Context context;
    private Dialog dialog;
    private ViewPager viewPager;
    private PicPagerAdapter picPagerAdapter;
    private LinearLayout dotLayout;

    public PicDialog(@NonNull Context context) {
        this(context, R.style.translucentDialog);
    }

    public PicDialog(@NonNull Context context, @StyleRes int themeResId) {
        this.dialog = new Dialog(context, themeResId);
        this.context = context;
        init();
    }

    // 初始化
    private void init() {
        dialog.setContentView(R.layout.layout_pic_dialog);

        viewPager = dialog.findViewById(R.id.viewPager);
        dotLayout = dialog.findViewById(R.id.dotLayout);

        // 按返回键是否取消
        dialog.setCancelable(true);
        // 点击Dialog外围是否取消
        dialog.setCanceledOnTouchOutside(false);
    }

    // 设置数据
    public PicDialog setDatas(ArrayList<PicDialogBean> datas) {
        // 初始化viewPager
        setPicPagerAdapter(datas);
        // 初始化dotLayout
        initDotLayout(datas);
        return this;
    }

    // 自定义setPicPagerAdapter
    private void setPicPagerAdapter(ArrayList<PicDialogBean> datas) {
        if (picPagerAdapter == null) {
            picPagerAdapter = new PicPagerAdapter(context, datas);
            viewPager.setAdapter(picPagerAdapter);
        } else {
            picPagerAdapter.reflushData(datas);
        }
    }

    // 初始化dotLayout
    private void initDotLayout(ArrayList<PicDialogBean> datas) {
        if (datas != null && datas.size() > 0) {
            dotLayout.removeAllViews();
            for (int i = 0; i < datas.size(); i++) {

            }
        }
    }

    class PicPagerAdapter extends PagerAdapter {
        private ArrayList<PicDialogBean> mDatas;
        private LayoutInflater inflater;

        public PicPagerAdapter(Context context, ArrayList<PicDialogBean> datas) {
            this.mDatas = datas;
            this.inflater = LayoutInflater.from(context);
        }

        public void reflushData(ArrayList<PicDialogBean> datas) {
            this.mDatas = datas;
            this.notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mDatas.size();
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = container.getChildAt(position);
            ViewHolder viewHolder;
            if (view == null) {
                view = inflater.inflate(R.layout.layout_pic_dialog_item, container, false);
                viewHolder = new ViewHolder();
                viewHolder.scaleImageView = view.findViewById(R.id.scaleImageView);
                viewHolder.textView = view.findViewById(R.id.textView);
                view.setTag(viewHolder);
                container.addView(view, position);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            PicDialogBean data = mDatas.get(position);
            if (data != null) {
                Glide.with(context)
                        .load(data.getUrl())
                        .into(viewHolder.scaleImageView);
                viewHolder.textView.setText(data.getName());
            }
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }
    }

    private static class ViewHolder {
        ScaleImageView scaleImageView;
        TextView textView;
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

}
