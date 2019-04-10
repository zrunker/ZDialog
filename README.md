# ZDialog
自定义Dialog（一）：圆形进度条Dialog。自定义Dialog（二）：提示Dialog。自定义Dialog（三）：删除Dialog。自定义Dialog（四）：自定义视图Dialog。自定义Dialog（五）：选择图片Dialog。（六）：轮播Dialog。使用工具Android Studio。

![书客创作](http://upload-images.jianshu.io/upload_images/3480018-0a7f5d0ede43b538..jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

[阅读原文](http://ibooker.cc/article/227/detail)

#### 一、引入ZDialog框架
推荐两种方式引入：

##### 1、gradle引入，即在gradle.build文件中添加以下代码：
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}

dependencies {
	implementation 'com.github.zrunker:ZDialog:v1.1.2'
}
```
##### 2、maven引入，在maven配置文件中引入以下代码：
```
<repositories>
	<repository>
		<id>jitpack.io</id>
		<url>https://jitpack.io</url>
	</repository>
</repositories>

<dependency>
	<groupId>com.github.zrunker</groupId>
	<artifactId>ZDialog</artifactId>
	<version>v1.1.2</version>
</dependency>
```

#### 二、应用：

##### 1.1、进度条ProDialog：

**A、效果图**
![图片描述](http://www.ibookerfile.cc/upload/images/acontents/1_1554811149344acontentimage.gif)

**B、在Activity中的代码实现**
```
// 初始化ProDialog
ProDialog proDialog = new ProDialog(this);
// 设置取消事件
proDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialog) {

        }
})
        // 按返回键是否取消
        .setCancelable(true)
        // 点击Dialog外围是否取消
        .setCanceledOnTouchOutside(false)
        // 设置背景层透明度
        .setDimAmount(0.2f)
        // 置Dialog显示位置
        .setProDialogGravity(ProDialog.ProDialogGravity.GRAVITY_CENTER)
        // 显示Dialog
        .showProDialog();

proDialog.setMessage("加载中");
new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
            proDialog.setImageStatue(R.mipmap.ic_launcher)
                .setMessage("加载完毕")
                .setMessageColor("#FF0000");
        }
}, 3000);

new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
                proDialog.setProgressBar(true)
                    .setMessage("再次加载");
        }
}, 6000);

new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
            proDialog.closeProDialog();
        }
}, 10000);
```
**C、ProDialog方法说明**
```
// 获取Dialog
public Dialog getDialog();

// 获取显示图片控件
public ImageView getImageView();

// 获取显示文本控件
public TextView getMessageTv();

// 获取显示进度控件
public ProgressBar getProgressBar();

// Dialog是否展示
public boolean isShowing();

// 给Dialog设置提示信息
ProDialog.setMessage(CharSequence message);

// 给Dialog设置提示信息颜色 color 16进制颜色
ProDialog.setMessageColor(String color);

// 改变dialog的状态图片 source 资源文件
ProDialog.setImageStatue(int source);

// 是否显示ProgressBar
ProDialog.setProgressBar(boolean bool);

// 按返回键是否取消Dialog显示
ProDialog.setCancelable(boolean cancelable);

// 点击Dialog外围是否取消Dialog
ProDialog.setCanceledOnTouchOutside(boolean cancelable);

// 设置Dialog取消事件
ProDialog.setOnCancelListener(DialogInterface.OnCancelListener onCancelListener);

// 设置Dialog显示位置
ProDialog.setProDialogGravity(ProDialogGravity proDialogGravity);

// 设置背景层透明度  dimAmount 0~1
ProDialog.setDimAmount(float dimAmount);

// 设置Dialog宽度 proportion 和屏幕的宽度比(10代表10%) 0~100
ProDialog.setProDialogWidth(int proportion);

// 设置Dialog高度 proportion 和屏幕的高度比(10代表10%) 0~100
ProDialog.setProDialogHeight(int proportion);

// 设置Window动画  style R动画文件
ProDialog.setWindowAnimations(int style);

// 展示Dialog
public void showProDialog();

// 关闭Dialog
public void closeProDialog();
```

##### 1.2、进度条ProgressDialog：
**A、效果图**

![图片描述](http://www.ibookerfile.cc/upload/images/acontents/1_1554812676000acontentimage.gif)

**B、在Activity中的代码实现**
```
// 初始化ProgressDialog
ProgressDialog progressDialog = new ProgressDialog(this);
// 展示ProgressDialog
progressDialog.showProDialog();
```

**C、ProgressDialog方法说明**
```
// 获取Dialog
public Dialog getDialog();

// 获取进度条ProgressBar
public ProgressBar getProgressBar();

// Dialog是否展示
public boolean isShowing();

// 按返回键是否取消Dialog
ProgressDialog.setCancelable(boolean cancelable);

// 点击Dialog外围是否取消Dialog
ProgressDialog.setCanceledOnTouchOutside(boolean cancelable);

// 设置取消事件
ProgressDialog.setOnCancelListener(DialogInterface.OnCancelListener onCancelListener);

// 设置Dialog显示位置
ProgressDialog.setProDialogGravity(ProDialogGravity proDialogGravity);

// 设置背景层透明度  dimAmount 0~1
ProgressDialog.setDimAmount(float dimAmount);

// 设置Dialog宽度 proportion 和屏幕的宽度比(10代表10%) 0~100
ProgressDialog.setProDialogWidth(int proportion);

// 设置Dialog高度 proportion 和屏幕的高度比(10代表10%) 0~100
ProgressDialog.setProDialogHeight(int proportion);

// 设置Window动画 R文件
ProgressDialog.setWindowAnimations(int style);
```

##### 2、提示TipDialog：
**A、效果图**

![图片描述](http://www.ibookerfile.cc/upload/images/acontents/1_1554813421938acontentimage.gif)

**B、在Activity中的代码实现**
```
// 初始化TipDialog
TipDialog tipDialog = new TipDialog(this);
// 设置取消事件
tipDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialog) {

        }
})
        // 按返回键是否取消
        .setCancelable(true)
        // 点击Dialog外围是否取消
        .setCanceledOnTouchOutside(false)
        // 设置背景层透明度
        .setDimAmount(0.2f)
        // 置Dialog显示位置
        .setTipDialogGravity(TipDialog.TipDialogGravity.GRAVITY_CENTER)
        // 设置取消文本样式
        .setCancelColor("#40aff2")
        // 设置取消文本
        .setCancelText("取消")
        // 设置确定文本颜色
        .setEnsureColor("#40aff2")
        // 设置确定文本
        .setEnsureText("确定")
        .setTitleHeight(120)
        // 设置整体
        .setTopLayoutGravity(Gravity.START)
        .setTopLayoutMargin(50, 50, 50, 50)
        // 显示Dialog
        .showTipDialog();

tipDialog.setTitleText("您确定要删除吗");
tipDialog.setDescText("删除之后将无法恢复").setDescColor("#F00F00");
new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
            tipDialog.setDescText("删除成功")
                .setDescColor("#FF0000")
                .setTitleVisible(false);
        }
}, 3000);

new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
            tipDialog.setTitleVisible(true)
                .setTitleText("再次删除")
                .setDescVisible(false);
        }
}, 6000);

// 取消按钮监听事件
tipDialog.setOnTipCancelListener(new TipDialog.OnTipCancelListener() {
        @Override
        public void onCancel() {

        }
});

// 确定按钮监听事件
tipDialog.setOnTipEnsureListener(new TipDialog.OnTipEnsureListener() {
        @Override
        public void onEnsure() {

        }
});
```
**C、TipDialog方法说明**
```
// 获取Dialog
public Dialog getDialog();

// 获取顶部控件
public LinearLayout getTopLayout();

// 获取主题控件
public TextView getTitleTv();

// 获取描述控件
public TextView getDescTv();

// 获取确定按钮
public TextView getEnsureTv();

// 获取取消按钮
public TextView getCancelTv();

// Dialog是否展示
public boolean isShowing();

// 修改顶部整体显示位置
TipDialog.setTopLayoutGravity(int gravity);

// 修改顶部整体Padding - px
TipDialog.setTopLayoutPadding(int left, int top, int right, int bottom);

// 修改顶部整体Margin - px
TipDialog.setTopLayoutMargin(int leftMargin, int topMargin, int rightMargin, int bottomMargin);

// 修改主题文字
TipDialog.setTitleText(CharSequence text);

// 修改主题文字大小
TipDialog.setTitleTextSize(int size);

// 修改主题文字颜色
TipDialog.setTitleColor(String color);

// 修改主题文字高度
TipDialog.setTitleHeight(int height);

// 修改主题显示位置
TipDialog.setTitleVisible(boolean visible);

// 修改描述文字
TipDialog.setDescText(CharSequence text);

// 修改描述文字大小
TipDialog.setDescTextSize(int size);

// 修改描述文字颜色 color 文字颜色 16进制
TipDialog.setDescColor(String color);

// 修改描述是否显示
TipDialog.setDescVisible(boolean visible);

// 修改描述高度
TipDialog.setDescHeight(int height);

// 修改描述显示位置
TipDialog.setDescGravity(int gravity);

// 修改确定文字
TipDialog.setEnsureText(CharSequence text);

// 修改确定文字
TipDialog.setEnsureTextSize(int size);

// 修改确定文字颜色
TipDialog.setEnsureColor(String color);

// 修改确定文字高度
TipDialog.setEnsureHeight(int height);

// 修改确定文字显示位置
TipDialog.setEnsureGravity(int gravity);

// 修改取消文字
TipDialog.setCancelText(CharSequence text);

// 修改取消文字大小
TipDialog.setCancelTextSize(int size);

// 修改取消文字颜色
TipDialog.setCancelColor(String color);

// 修改取消文字高度
TipDialog.setCancelHeight(int height);

// 修改取消文字显示位置
TipDialog.setCancelGravity(int gravity);

// 按返回键是否取消
TipDialog.setCancelable(boolean cancelable);

// 点击Dialog外围是否取消
TipDialog.setCanceledOnTouchOutside(boolean cancelable);

// 设置取消事件
TipDialog.setOnCancelListener(DialogInterface.OnCancelListener onCancelListener);

// 设置Dialog显示位置
TipDialog.setTipDialogGravity(TipDialogGravity tipDialogGravity);

// 设置背景层透明度 dimAmount 0~1
TipDialog.setDimAmount(float dimAmount);

// 设置Window动画 style R文件
TipDialog.setWindowAnimations(int style);

// 设置Dialog宽度 proportion 和屏幕的宽度比(10代表10%) 0~100
TipDialog.setTipDialogWidth(int proportion);

// 设置Dialog高度  proportion 和屏幕的高度比(10代表10%) 0~100
TipDialog.setTipDialogHeight(int proportion);

// 展示Dialog
public void showTipDialog();

// 关闭Dialog
public void closeTipDialog();
```
##### 3、删除DelDialog：
**A、效果图**

![图片描述](http://www.ibookerfile.cc/upload/images/acontents/1_1554861343516acontentimage.gif)

**B、在Activity中的代码实现**
```
// 初始化DelDialog
DelDialog delDialog = new DelDialog(this);
// 设置取消事件
delDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialog) {

        }
})
        // 按返回键是否取消
        .setCancelable(true)
        // 点击Dialog外围是否取消
        .setCanceledOnTouchOutside(false)
        // 设置背景层透明度
        .setDimAmount(0.2f)
        // 置Dialog显示位置
        .setDelDialogGravity(DelDialog.DelDialogGravity.GRAVITY_CENTER)
        // 设置取消文本样式
        .setCancelBtnColor("#40aff2")
        // 设置取消文本
        .setCancelBtnSize(15)
        // 设置确定文本颜色
        .setDelBtnColor("#40aff2")
        // 设置确定文本
        .setDelBtnSize(15)
        // 显示Dialog
        .showDelDialog();

// 取消按钮监听事件
delDialog.setOnDelCancelListener(new DelDialog.OnDelCancelListener() {
        @Override
        public void onCancel() {

        }
});

// 确定按钮监听事件
delDialog.setOnDelListener(new DelDialog.OnDelListener() {
        @Override
        public void onDel() {

        }
});
```
**C、DelDialog方法说明**
```
// 获取Dialog
public Dialog getDialog();

// 获取删除按钮
public Button getDelBtn();

// 获取取消按钮
public Button getCancelBtn();

// Dialog是否展示
public boolean isShowing();

// 设置DelBtn文字大小
DelDialog.setDelBtnSize(float size);

// 设置DelBtn文字颜色
DelDialog.setDelBtnColor(String color);

// 设置CancelBtn文字大小
DelDialog.setCancelBtnSize(float size);

// 设置CancelBtn文字颜色
DelDialog.setCancelBtnColor(String color);

// 按返回键是否取消
DelDialog.setCancelable(boolean cancelable);

// 点击Dialog外围是否取消
DelDialog.setCanceledOnTouchOutside(boolean cancelable);

// 设置取消事件
DelDialog.setOnCancelListener(DialogInterface.OnCancelListener onCancelListener);

// 设置Dialog宽度 proportion 和屏幕的宽度比(10代表10%) 0~100
DelDialog.setDelDialogWidth(int proportion);

// 设置Dialog高度  proportion 和屏幕的高度比(10代表10%) 0~100
DelDialog.setDelDialogHeight(int proportion);

// 设置Window动画 style R文件
DelDialog.setWindowAnimations(int style);

// 设置Dialog显示位置
DelDialog.setDelDialogGravity(DelDialogGravity delDialogGravity);

// 设置背景层透明度 dimAmount 0~1
DelDialog.setDimAmount(float dimAmount);

// 展示Dialog
public void showDelDialog();

// 关闭Dialog
public void closeDelDialog();
```

##### 4、自定义布局DiyDialog：
**A、效果图**

![图片描述](http://www.ibookerfile.cc/upload/images/acontents/1_1554862413594acontentimage.gif)

**B、在Activity中的代码实现**
```
// 初始化DiyDialog
DiyDialog diyDialog = new DiyDialog(this, LayoutInflater.from(this).inflate(R.layout.activity_diy, null));
// 设置取消事件
diyDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialog) {

        }
})
        // 按返回键是否取消
        .setCancelable(true)
        // 点击Dialog外围是否取消
        .setCanceledOnTouchOutside(false)
        // 设置背景层透明度
        .setDimAmount(0.2f)
        // 置Dialog显示位置
        .setDiyDialogGravity(DiyDialog.DiyDialogGravity.GRAVITY_CENTER)
        // 显示Dialog
        .showDiyDialog();
```
自定义布局文件activity_diy.xml
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:gravity="center"
    android:orientation="vertical">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:padding="30dp"
        android:text="@string/app_name" />

</LinearLayout>
```
**C、DiyDialog方法说明**
```
// 获取Dialog
public Dialog getDialog();

// Dialog是否展示
public boolean isShowing();

// 按返回键是否取消
DiyDialog.setCancelable(boolean cancelable);

// 点击Dialog外围是否取消
DiyDialog.setCanceledOnTouchOutside(boolean cancelable);

// 设置取消事件
DiyDialog.setOnCancelListener(DialogInterface.OnCancelListener onCancelListener);

// 设置Dialog宽度  proportion 和屏幕的宽度比(10代表10%) 0~100
DiyDialog.setDiyDialogWidth(int proportion);

// 设置Dialog高度 proportion 和屏幕的高度比(10代表10%) 0~100
DiyDialog.setDiyDialogHeight(int proportion);

// 设置Dialog显示位置
DiyDialog.setDiyDialogGravity(DiyDialogGravity diyDialogGravity);

// 设置背景层透明度 dimAmount 0~1
DiyDialog.setDimAmount(float dimAmount);

// 设置Window动画 style R文件
DiyDialog.setWindowAnimations(int style);

// 展示Dialog
public void showDiyDialog();

// 关闭Dialog
public void closeDiyDialog();
```

##### 5、图片选择ChoosePictrueDialog：
**A、效果图**

![图片描述](http://www.ibookerfile.cc/upload/images/acontents/1_1554865767453acontentimage.gif)

**B、在Activity中的代码实现**
```
// 初始化ChoosePictrueDialog
ChoosePictrueDialog choosePictrueDialog = new ChoosePictrueDialog(this);
// 也可以修改各个按钮的属性
choosePictrueDialog.setCancelBtnSize(16)
        .setChoosePictrueDialogWidth(50)
        .setBtnHeight(200)
        .setCancelBtnColor("#40aff2")
        .setCancelBtnText("取消")
        .showChoosePictrueDialog();
//        // 也可以自定义各个按钮的点击事件
//        choosePictrueDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//            @Override
//            public void onCancel(DialogInterface dialog) {
//                // 取消事件
//            }
//        });
//        choosePictrueDialog.setOnLocalListener(new ChoosePictrueDialog.OnLocalListener() {
//            @Override
//            public void onLocal() {
//                // 从本地选择事件
//            }
//        });
//        choosePictrueDialog.setOnPhotoListener(new ChoosePictrueDialog.OnPhotoListener() {
//            @Override
//            public void onPhoto() {
//                // 拍照事件
//            }
//        });
```
**C、ChoosePictrueDialog方法说明**
```
// 获取Dialog
public Dialog getDialog();

// 获取本地按钮
public Button getLocalBtn();

// 获取拍照按钮
public Button getPhotoBtn();

// 获取取消按钮
public Button getCancelBtn();

// Dialog是否展示
public boolean isShowing();

// 启动拍照
public void startPhoto();

// 设置三个按钮的高度
ChoosePictrueDialog.setBtnHeight(int pixels);

// 设置三个按钮的字体颜色
ChoosePictrueDialog.setBtnColor(String color);

// 设置三个按钮的字体大小
ChoosePictrueDialog.setBtnSize(float size);

// 修改本地按钮文本颜色
ChoosePictrueDialog.setLocalBtnColor(String color);

// 修改拍照按钮文本颜色
ChoosePictrueDialog.setPhotoBtnColor(String color);

// 修改取消按钮文本颜色
ChoosePictrueDialog.setCancelBtnColor(String color);

// 修改本地按钮文本字体大小
ChoosePictrueDialog.setLocalBtnSize(float size);

// 修改拍照按钮文本字体大小
ChoosePictrueDialog.setPhotoBtnSize(float size);

// 修改取消按钮文本字体大小
ChoosePictrueDialog.setCancelBtnSize(float size);

// 修改本地按钮文本
ChoosePictrueDialog.setLocalBtnText(CharSequence text);

// 修改拍照按钮文本
ChoosePictrueDialog.setPhotoBtnText(CharSequence text);

// 修改取消按钮文本
ChoosePictrueDialog.setCancelBtnText(CharSequence text);

// 按返回键是否取消
ChoosePictrueDialog.setCancelable(boolean cancelable);

// 点击Dialog外围是否取消
ChoosePictrueDialog.setCanceledOnTouchOutside(boolean cancelable);

// 设置取消事件
ChoosePictrueDialog.setOnCancelListener(DialogInterface.OnCancelListener onCancelListener);

// 设置Dialog显示位置
ChoosePictrueDialog.setChoosePictrueDialogGravity(ChoosePictrueDialogGravity choosePictrueDialogGravity);

// 设置背景层透明度 dimAmount 0~1
ChoosePictrueDialog.setDimAmount(float dimAmount);

// 设置Window动画 style R文件
ChoosePictrueDialog.setWindowAnimations(int style);

// 设置Dialog宽度 proportion 和屏幕的宽度比(10代表10%) 0~100
ChoosePictrueDialog.setChoosePictrueDialogWidth(int proportion);

// 设置Dialog高度 proportion 和屏幕的高度比(10代表10%) 0~100
ChoosePictrueDialog.setChoosePictrueDialogHeight(int proportion);

// 展示Dialog
public void showChoosePictrueDialog();

// 关闭Dialog
public void closeChoosePictrueDialog();
```

##### 6、轮播WheelDialog：
**A、效果图**

![图片描述](http://www.ibookerfile.cc/upload/images/acontents/1_1554871564875acontentimage.gif)

**B、在Activity中的代码实现**
```
// 初始化WheelDialog
WheelDialog wheelDialog = new WheelDialog(this);
// 设置数据
ArrayList<WheelDialogBean> datas = new ArrayList<>();
datas.add(new WheelDialogBean("选项1", "http://pic38.nipic.com/20140225/2531170_214014788000_2.jpg"));
datas.add(new WheelDialogBean("选项2", "http://www.pptbz.com/pptpic/UploadFiles_6909/201406/2014063021281300.gif"));
datas.add(new WheelDialogBean("选项3", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554738709986&di=90d889de1910eeae0f26c9a65d583435&imgtype=0&src=http%3A%2F%2Fimg4.duitang.com%2Fuploads%2Fblog%2F201401%2F16%2F20140116125541_ZkGAw.thumb.200_200_c.gif"));
// wheelDialog属性设置
wheelDialog.setDatas(datas, "http://www.pptbz.com/pptpic/UploadFiles_6909/201406/2014063021281300.gif")
        .setPageIndicatorAlign(WheelDialog.PageIndicatorAlign.CENTER_HORIZONTAL)
        .showWheelDialog();
```
**C、WheelDialog方法说明**
```
// 获取Dialog
public Dialog getDialog();

// 获取指示器控件
public LinearLayout getDotLayout();

// 获取说明控件
public TextView getTextView();

// 设置数据，datas 带显示的数据集，currentUrl当前要显示的图片地址
WheelDialog.setDatas(final ArrayList<WheelDialogBean> datas, String currentUrl);

// 底部指示器资源图片 selectedRes 选中图标地址, defaultRes  未选中图标地址
WheelDialog.setPageIndicator(int selectedRes, int defaultRes);

// 指示器的方向
WheelDialog.setPageIndicatorAlign(PageIndicatorAlign align);

// 设置指示器是否可见
WheelDialog.setPointViewVisible(boolean isVisible);

// Dialog是否展示
public boolean isShowing();

// 设置Dialog宽度 proportion 和屏幕的宽度比(10代表10%) 0~100
WheelDialog.setWheelDialogWidth(int proportion);

// 设置Dialog高度 proportion 和屏幕的高度比(10代表10%) 0~100
WheelDialog.setWheelDialogHeight(int proportion);

// 按返回键是否取消
WheelDialog.setCancelable(boolean cancelable);

// 设置取消事件
WheelDialog.setOnCancelListener(DialogInterface.OnCancelListener onCancelListener);

// 设置Dialog显示位置
WheelDialog.setWheelDialogGravity(WheelDialogGravity wheelDialogGravity);

// 设置背景层透明度 dimAmount 0~1
WheelDialog.setDimAmount(float dimAmount);

// 设置Window动画 style R文件
WheelDialog.setWindowAnimations(int style);

// 点击Dialog外围是否取消
WheelDialog.setCanceledOnTouchOutside(boolean cancelable);

// 修改游标指示器整体Padding - px
WheelDialog.setDotLayoutPadding(int left, int top, int right, int bottom);

// 修改游标指示器整体Margin - px
WheelDialog.setDotLayoutMargin(int leftMargin, int topMargin, int rightMargin, int bottomMargin);

// 修改TextView整体Padding - px
WheelDialog.setTextViewPadding(int left, int top, int right, int bottom);

// 修改TextView整体Margin - px
WheelDialog.setTextViewMargin(int leftMargin, int topMargin, int rightMargin, int bottomMargin);

// 修改TextView字体大小
WheelDialog.setTextViewSize(int textSize);

// 修改TextView字体颜色
WheelDialog.setTextViewColor(String color);

// 展示Dialog
public void showWheelDialog();

// 关闭Dialog
public void closeWheelDialog();
```

##### 7、轮播WheelDialog2：
**A、效果图**

![图片描述](http://www.ibookerfile.cc/upload/images/acontents/1_1554880213797acontentimage.gif)

**B、在Activity中的代码实现**
```
// 展示轮播Dialog相关数据集
ArrayList<WheelDialogBean> datas = new ArrayList<>();
// 展示轮播Dialog
public void onWheelDialog2(final View v) {
        WheelDialog2 wheelDialog2 = new WheelDialog2<>(this);

        if (datas == null)
            datas = new ArrayList<>();
        datas.clear();
        datas.add(new WheelDialogBean("选项1", "http://pic38.nipic.com/20140225/2531170_214014788000_2.jpg"));
        datas.add(new WheelDialogBean("选项2", "http://www.pptbz.com/pptpic/UploadFiles_6909/201406/2014063021281300.gif"));
        datas.add(new WheelDialogBean("选项3", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554738709986&di=90d889de1910eeae0f26c9a65d583435&imgtype=0&src=http%3A%2F%2Fimg4.duitang.com%2Fuploads%2Fblog%2F201401%2F16%2F20140116125541_ZkGAw.thumb.200_200_c.gif"));
        // 设置wheelDialog2属性
        wheelDialog2.init(new HolderCreator() {
            @Override
            public Object createHolder() {
                return new ViewHolder();
            }
        }, datas, 1)
                .showWheelDialog2();
}

// 构造显示视图ViewHolder
class ViewHolder implements Holder<WheelDialogBean> {
    private LayoutInflater inflater;
    private ScaleImageView imageView;
    private TextView textView;

    @Override
    public View createView(Context context) {
        inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_wheel2_item, null);
        imageView = view.findViewById(R.id.scaleImageView);
        textView = view.findViewById(R.id.textView);
        return view;
    }

    @Override
    public void updateUI(Context context, int position, WheelDialogBean data) {
        WheelDialogBean wheelDialogBean = datas.get(position);
        Picasso.get()
            .load(wheelDialogBean.getUrl())
            .into(imageView);
        textView.setText(wheelDialogBean.getName());
    }
}
```
布局文件layout_wheel2_item.xml
```
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <cc.ibooker.zdialoglib.ScaleImageView
        android:id="@+id/scaleImageView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_gravity="center" />

    <TextView
        android:id="@+id/textView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom|center_horizontal"
        android:layout_marginBottom="44dp"
        android:text="@string/app_name" />
</FrameLayout>
```
**C、WheelDialog2方法说明**
```
// 获取Dialog
public Dialog getDialog();

// 获取指示器控件
public LinearLayout getDotLayout();

// 初始化 holderCreator - ViewHolder构造类；datas - 数据源；currentPosition 当前页 默认0
WheelDialog2.init(HolderCreator holderCreator, final List<T> datas, int currentPosition);

// 底部指示器资源图片 selectedRes 选中图标地址；defaultRes  未选中图标地址
WheelDialog2.setPageIndicator(int selectedRes, int defaultRes);

// 指示器的方向
WheelDialog2.setPageIndicatorAlign(PageIndicatorAlign align);

// 设置指示器是否可见
WheelDialog2.setPointViewVisible(boolean isVisible);

// Dialog是否展示
public boolean isShowing();

// 设置Dialog宽度 proportion 和屏幕的宽度比(10代表10%) 0~100
WheelDialog2.setWheelDialogWidth(int proportion);

// 设置Dialog高度 proportion 和屏幕的高度比(10代表10%) 0~100
WheelDialog2.setWheelDialogHeight(int proportion);

// 按返回键是否取消
WheelDialog2.setCancelable(boolean cancelable);

// 设置Dialog显示位置
WheelDialog2.setWheelDialogGravity(WheelDialogGravity wheelDialogGravity);

// 设置背景层透明度 dimAmount 0~1
WheelDialog2.setDimAmount(float dimAmount);

// 设置Window动画 style R文件
WheelDialog2.setWindowAnimations(int style);

// 点击Dialog外围是否取消
WheelDialog2.setCanceledOnTouchOutside(boolean cancelable);

// 设置取消事件
WheelDialog2.setOnCancelListener(DialogInterface.OnCancelListener onCancelListener);

// 修改游标指示器整体Padding - px
WheelDialog2.setDotLayoutPadding(int left, int top, int right, int bottom);

// 修改游标指示器整体Margin - px
WheelDialog2.setDotLayoutMargin(int leftMargin, int topMargin, int rightMargin, int bottomMargin);

// 展示Dialog
public void showWheelDialog2();

// 关闭Dialog
public void closeWheelDialog2();
```

[Github地址](https://github.com/zrunker/ZDialog)

**推荐阅读：**
[漂亮的圆角Dialog【Android】](http://ibooker.cc/article/42/detail)
[图片选择（拍照+本地相册）Dialog封装，使用【Android】](http://ibooker.cc/article/19/detail)
