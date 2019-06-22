package cc.ibooker.zdialoglib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

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
//    private int screenWidth, screenHeight;
    private String vpImageViewBackGroudColor, vpItemViewBackGroudColor;
    private int vpImageViewBackGroudRes, vpItemViewBackGroudRes;
    private Drawable vpImageViewBackGroudDrawable, vpItemViewBackGroudDrawable;
    private int paramWidth, paramHeight;

    public WheelPagerAdapter(Context context, ArrayList<WheelDialogBean> datas) {
        this.context = context;
        this.mDatas = datas;
        this.inflater = LayoutInflater.from(context);
        this.mViews = new View[mDatas.size()];
//        this.screenWidth = getScreenWidth();
//        this.screenHeight = getScreenHeight();
    }

    public void reflushData(ArrayList<WheelDialogBean> datas) {
        this.mDatas = datas;
        this.mViews = new View[mDatas.size()];
        this.notifyDataSetChanged();
    }

    public void setVpItemViewBackGroudColor(String vpItemViewBackGroudColor) {
        this.vpItemViewBackGroudColor = vpItemViewBackGroudColor;
    }

    public void setVpItemViewBackGroudRes(int vpItemViewBackGroudRes) {
        this.vpItemViewBackGroudRes = vpItemViewBackGroudRes;
    }

    public void setVpItemViewBackGroudDrawable(Drawable vpItemViewBackGroudDrawable) {
        this.vpItemViewBackGroudDrawable = vpItemViewBackGroudDrawable;
    }

    public void setVpImageViewBackGroudColor(String vpImageViewBackGroudColor) {
        this.vpImageViewBackGroudColor = vpImageViewBackGroudColor;
    }

    public void setVpImageViewBackGroudRes(int vpImageViewBackGroudRes) {
        this.vpImageViewBackGroudRes = vpImageViewBackGroudRes;
    }

    public void setVpImageViewBackGroudDrawable(Drawable vpImageViewBackGroudDrawable) {
        this.vpImageViewBackGroudDrawable = vpImageViewBackGroudDrawable;
    }

    public void setVpImageViewSize(int width, int height) {
        this.paramWidth = width;
        this.paramHeight = height;
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
            if (onItemClickListener != null)
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ZDialogClickUtil.isFastClick())
                            return;
                        onItemClickListener.onItemClickListener(position);
                    }
                });
            if (onItemLongClickListener != null)
                view.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        onItemLongClickListener.onItemLongClick(position);
                        return true;
                    }
                });
            // 设置背景
            if (!TextUtils.isEmpty(vpItemViewBackGroudColor))
                view.setBackgroundColor(Color.parseColor(vpItemViewBackGroudColor));
            else if (vpItemViewBackGroudDrawable != null)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                    view.setBackground(vpItemViewBackGroudDrawable);
                else
                    view.setBackgroundDrawable(vpItemViewBackGroudDrawable);
            else if (vpItemViewBackGroudRes != 0)
                view.setBackgroundResource(vpItemViewBackGroudRes);
            // 设置大小
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (paramHeight > 0)
                layoutParams.height = paramHeight;
            if (paramWidth > 0)
                layoutParams.width = paramWidth;
            view.setLayoutParams(layoutParams);

            // 移除父控件
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

    private static class ViewHolder2 {
        ImageView imageView;
    }

    // 获取View
    private View getView(ViewGroup container, final int position) {
        if (mViews != null && position < mViews.length) {
            View view = mViews[position];
            WheelDialogBean data = mDatas.get(position);
            if (data != null) {
                if (data.isCanScale()) {
                    final ViewHolder viewHolder;
                    if (view == null) {
                        view = inflater.inflate(R.layout.zdialog_layout_wheel_dialog_item, container, false);
                        viewHolder = new ViewHolder();
                        viewHolder.scaleImageView = view.findViewById(R.id.scaleImageView);
                        // 设置背景
                        if (!TextUtils.isEmpty(vpImageViewBackGroudColor))
                            viewHolder.scaleImageView.setBackgroundColor(Color.parseColor(vpImageViewBackGroudColor));
                        else if (vpImageViewBackGroudDrawable != null)
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                                viewHolder.scaleImageView.setBackground(vpImageViewBackGroudDrawable);
                            else
                                viewHolder.scaleImageView.setBackgroundDrawable(vpImageViewBackGroudDrawable);
                        else if (vpImageViewBackGroudRes != 0)
                            viewHolder.scaleImageView.setBackgroundResource(vpImageViewBackGroudRes);
                        view.setTag(viewHolder);
                        mViews[position] = view;
                    } else {
                        viewHolder = (ViewHolder) view.getTag();
                    }
                    Object object = data.getUrl();
                    if (object == null)
                        object = data.getBitmap();
                    if (object == null)
                        object = data.getRes();
                    viewHolder.scaleImageView.setLimitSize(data.isLimitSize());
                    Glide.with(context)
                            .load(object)
                            .placeholder(data.getDefaultRes())
                            .error(data.getErrorRes())
                            .into(viewHolder.scaleImageView);
                    // 设置图片点击监听
                    if (onItemImageClickListener != null)
                        viewHolder.scaleImageView.setOnMyClickListener(new ScaleImageView.OnMyClickListener() {
                            @Override
                            public void onMyClick(View v) {
                                if (ZDialogClickUtil.isFastClick())
                                    return;
                                onItemImageClickListener.onItemImageClick(position);
                            }
                        });
                    if (onItemImageLongClickListener != null)
                        viewHolder.scaleImageView.setOnMyLongClickListener(new ScaleImageView.OnMyLongClickListener() {
                            @Override
                            public void onMyLongClick(View v) {
                                onItemImageLongClickListener.onItemImageLongClick(position);
                            }
                        });
                } else {
                    final ViewHolder2 viewHolder2;
                    if (view == null) {
                        view = inflater.inflate(R.layout.zdialog_layout_wheel_dialog_item_2, container, false);
                        viewHolder2 = new ViewHolder2();
                        viewHolder2.imageView = view.findViewById(R.id.image);
                        // 设置背景
                        if (!TextUtils.isEmpty(vpImageViewBackGroudColor))
                            viewHolder2.imageView.setBackgroundColor(Color.parseColor(vpImageViewBackGroudColor));
                        else if (vpImageViewBackGroudDrawable != null)
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                                viewHolder2.imageView.setBackground(vpImageViewBackGroudDrawable);
                            else
                                viewHolder2.imageView.setBackgroundDrawable(vpImageViewBackGroudDrawable);
                        else if (vpImageViewBackGroudRes != 0)
                            viewHolder2.imageView.setBackgroundResource(vpImageViewBackGroudRes);
                        view.setTag(viewHolder2);
                        mViews[position] = view;
                    } else {
                        viewHolder2 = (ViewHolder2) view.getTag();
                    }
                    Object object = data.getUrl();
                    if (object == null)
                        object = data.getBitmap();
                    if (object == null)
                        object = data.getRes();
                    if (!data.isLimitSize())
                        Glide.with(context)
                                .load(object)
                                .placeholder(data.getDefaultRes())
                                .error(data.getErrorRes())
                                .into(viewHolder2.imageView);
                    else
                        Glide.with(context)
                                .load(object)
                                .asBitmap()
                                .placeholder(data.getDefaultRes())
                                .error(data.getErrorRes())
                                .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                                    @Override
                                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                        int imageWidth = resource.getWidth();
                                        int imageHeight = resource.getHeight();
                                        ViewGroup.LayoutParams layoutParams = viewHolder2.imageView.getLayoutParams();
                                        layoutParams.height = imageHeight;
                                        layoutParams.width = imageWidth;
                                        viewHolder2.imageView.setImageBitmap(resource);
                                    }
                                });
                    // 设置图片点击监听
                    if (onItemImageClickListener != null)
                        viewHolder2.imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (ZDialogClickUtil.isFastClick())
                                    return;
                                onItemImageClickListener.onItemImageClick(position);
                            }
                        });
                    if (onItemImageLongClickListener != null)
                        viewHolder2.imageView.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View view) {
                                onItemImageLongClickListener.onItemImageLongClick(position);
                                return true;
                            }
                        });
                }
                return view;
            }
        }
        return null;
    }

//    // 获取屏幕的宽度
//    private int getScreenWidth() {
//        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        return wm != null ? wm.getDefaultDisplay().getWidth() : 0;
//    }

//    // 获取屏幕的高度
//    private int getScreenHeight() {
//        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        return wm != null ? wm.getDefaultDisplay().getHeight() : 0;
//    }

    // 对外接口实现点击事件
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private OnItemLongClickListener onItemLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    private OnItemImageClickListener onItemImageClickListener;

    public void setOnItemImageClickListener(OnItemImageClickListener onItemImageClickListener) {
        this.onItemImageClickListener = onItemImageClickListener;
    }

    private OnItemImageLongClickListener onItemImageLongClickListener;

    public void setOnItemImageLongClickListener(OnItemImageLongClickListener onItemImageLongClickListener) {
        this.onItemImageLongClickListener = onItemImageLongClickListener;
    }
}