package cc.ibooker.zdialoglib;

/**
 * 点击事件处理
 * https://github.com/zrunker/ZDialog
 */
public class ZDialogClickUtil {
    private static long lastClickTime;

    // 判断是否为快速点击事件
    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500)
            return true;
        lastClickTime = time;
        return false;
    }
}