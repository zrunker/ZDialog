package cc.ibooker.zdialoglib;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import cc.ibooker.zdialoglib.bean.WheelDialogBean;

/**
 * 轮播适配器
 *
 * @author 邹峰立
 * https://github.com/zrunker/ZDialog
 */
public class WheelPagerAdapter extends PagerAdapter {
    private Context context;
    private ArrayList<WheelDialogBean> mDatas;
    private LayoutInflater inflater;
    private View[] mViews;
//    private int screenWidth;

    public WheelPagerAdapter(Context context, ArrayList<WheelDialogBean> datas) {
        this.context = context;
        this.mDatas = datas;
        this.inflater = LayoutInflater.from(context);
        this.mViews = new View[mDatas.size()];
//        this.screenWidth = getScreenWidth() - 80;
    }

    public void reflushData(ArrayList<WheelDialogBean> datas) {
        this.mDatas = datas;
        this.mViews = new View[mDatas.size()];
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View view = getView(container, position);
        if (view != null) {// 控件点击事件
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ZDialogClickUtil.isFastClick())
                        return;
                    if (onItemClickListener != null)
                        onItemClickListener.onItemClickListener(position);
                }
            });
        }
        if (view != null) {
            ViewParent vp = view.getParent();
            if (vp != null) {
                ViewGroup parent = (ViewGroup) vp;
                parent.removeView(view);
            }
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    private static class ViewHolder {
        ScaleImageView scaleImageView;
    }

    // 获取View
    private View getView(ViewGroup container, int position) {
        if (mViews != null && position < mViews.length) {
            View view = mViews[position];
            final ViewHolder viewHolder;
            if (view == null) {
                view = inflater.inflate(R.layout.zdialog_layout_wheel_dialog_item, container, false);
                viewHolder = new ViewHolder();
                viewHolder.scaleImageView = view.findViewById(R.id.scaleImageView);
                view.setTag(viewHolder);
                mViews[position] = view;
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            WheelDialogBean data = mDatas.get(position);
            if (data != null) {
//                if (screenWidth > 0)
//                    Glide.with(context)
//                            .load(data.getUrl())
//                            /*.asBitmap()
//                            .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
//                                @Override
//                                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                                    if (screenWidth > 0) {
//                                        int imageWidth = resource.getWidth();
//                                        int imageHeight = resource.getHeight();
//                                        int height = screenWidth * imageHeight / imageWidth;
//                                        ViewGroup.LayoutParams para = viewHolder.scaleImageView.getLayoutParams();
//                                        para.height = height;
//                                        para.width = screenWidth;
//                                    }
//                                    viewHolder.scaleImageView.setImageBitmap(resource);
//                                }
//                            });*/
//                            .override(screenWidth / 2, screenWidth)
//                            .into(viewHolder.scaleImageView);
//                else
                Glide.with(context)
                        .load(data.getUrl())
                        /*.asBitmap()
                        .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                if (screenWidth > 0) {
                                    int imageWidth = resource.getWidth();
                                    int imageHeight = resource.getHeight();
                                    int height = screenWidth * imageHeight / imageWidth;
                                    ViewGroup.LayoutParams para = viewHolder.scaleImageView.getLayoutParams();
                                    para.height = height;
                                    para.width = screenWidth;
                                }
                                viewHolder.scaleImageView.setImageBitmap(resource);
                            }
                        });*/
                        .into(viewHolder.scaleImageView);
            }
            return view;
        }
        return null;
    }

//    // 获取屏幕的宽度
//    private int getScreenWidth() {
//        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        return wm != null ? wm.getDefaultDisplay().getWidth() : 0;
//    }

    // 对外接口实现点击事件
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}