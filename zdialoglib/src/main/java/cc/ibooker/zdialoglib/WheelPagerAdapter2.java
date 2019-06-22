package cc.ibooker.zdialoglib;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import java.util.List;

/**
 * 轮播适配器
 *
 * @author 邹峰立
 * https://github.com/zrunker/ZDialog
 */
public class WheelPagerAdapter2<T> extends PagerAdapter {
    private List<T> mDatas;
    private HolderCreator holderCreator;

    public WheelPagerAdapter2(HolderCreator holderCreator, List<T> list) {
        this.holderCreator = holderCreator;
        this.mDatas = list;
    }

    public void reflushData(HolderCreator holderCreator, List<T> list) {
        this.holderCreator = holderCreator;
        this.mDatas = list;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View view = getView(position, null, container);
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

            // 如果View已经在之前添加到了一个父组件，则必须先remove，否则会抛出IllegalStateException。
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

    // 获取View
    private View getView(int position, View view, ViewGroup container) {
        Holder<T> holder;
        if (view == null) {
            holder = (Holder<T>) holderCreator.createHolder();
            view = holder.createView(container.getContext());
            view.setTag(holder);
        } else {
            holder = (Holder<T>) view.getTag();
        }
        if (mDatas != null && !mDatas.isEmpty())
            holder.updateUI(container.getContext(), position, mDatas.get(position));
        return view;
    }

    // 对外接口实现点击事件
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private OnItemLongClickListener onItemLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }
}