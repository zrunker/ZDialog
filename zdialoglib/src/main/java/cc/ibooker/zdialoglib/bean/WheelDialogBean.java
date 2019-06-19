package cc.ibooker.zdialoglib.bean;

import android.graphics.Bitmap;

/**
 * 轮播Dialog相关数据
 *
 * @author 邹峰立
 * https://github.com/zrunker/ZDialog
 */
public class WheelDialogBean {
    private String name;// 图片名称
    private String url;// 待显示图片URL
    private Bitmap bitmap;// 待显示Bitmap
    private int res;// 待显示图片url
    private boolean isLimitSize;// 是否限制大小
    private int defaultRes;// 占位图
    private int errorRes;// 错误图

    public WheelDialogBean() {
        super();
    }

    public WheelDialogBean(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public WheelDialogBean(String name, Bitmap bitmap) {
        this.name = name;
        this.bitmap = bitmap;
    }

    public WheelDialogBean(String name, int res) {
        this.name = name;
        this.res = res;
    }

    public WheelDialogBean(String name, String url, boolean isLimitSize) {
        this.name = name;
        this.url = url;
        this.isLimitSize = isLimitSize;
    }

    public WheelDialogBean(String name, Bitmap bitmap, boolean isLimitSize) {
        this.name = name;
        this.bitmap = bitmap;
        this.isLimitSize = isLimitSize;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public boolean isLimitSize() {
        return isLimitSize;
    }

    public void setLimitSize(boolean limitSize) {
        isLimitSize = limitSize;
    }

    public int getDefaultRes() {
        return defaultRes;
    }

    public void setDefaultRes(int defaultRes) {
        this.defaultRes = defaultRes;
    }

    public int getErrorRes() {
        return errorRes;
    }

    public void setErrorRes(int errorRes) {
        this.errorRes = errorRes;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    @Override
    public String toString() {
        return "WheelDialogBean{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", bitmap=" + bitmap +
                ", res=" + res +
                ", isLimitSize=" + isLimitSize +
                ", defaultRes=" + defaultRes +
                ", errorRes=" + errorRes +
                '}';
    }
}
