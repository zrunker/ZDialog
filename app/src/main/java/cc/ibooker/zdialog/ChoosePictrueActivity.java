package cc.ibooker.zdialog;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cc.ibooker.zbitmaplib.BitmapFun;
import cc.ibooker.zdialoglib.ChoosePictrueDialog;
import cc.ibooker.zdialoglib.ChoosePictrueUtil;
import cc.ibooker.zdialoglib.ProDialog;
import cc.ibooker.zdialoglib.ZDialogConstantUtil;
import cc.ibooker.zfilelib.FileUtil;

/**
 * 选择图片Dialog
 * Created by 邹峰立 on 2017/7/12.
 */
public class ChoosePictrueActivity extends AppCompatActivity {
    private String imgPath, imgfile;
    private Bitmap picBitmap;
    private ChoosePictrueDialog choosePictrueDialog;
    private ProDialog mProDialog;
    private ExecutorService mExecutorService = Executors.newCachedThreadPool();
    private ImageView picImg;
    private TextView imgFileTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zdialog_activity_choosepictrue);

        picImg = (ImageView) findViewById(R.id.img_pic);
        imgFileTv = (TextView) findViewById(R.id.tv_imgfile);
        showChoosePictrueDialog();
    }

    @Override
    protected void onStop() {
        super.onStop();
        closeChoosePictrueDialog();
        closeDialog();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (picBitmap != null)
            picBitmap.recycle();
        System.gc();
        if (mExecutorService != null)
            mExecutorService.shutdownNow();
    }

    // 显示选择图片Dialog
    private void showChoosePictrueDialog() {
        if (choosePictrueDialog == null)
            choosePictrueDialog = new ChoosePictrueDialog(this);
        choosePictrueDialog.showChoosePictrueDialog();
    }

    // 关闭选择图片Dialog
    private void closeChoosePictrueDialog() {
        if (choosePictrueDialog != null)
            choosePictrueDialog.closeChoosePictrueDialog();
    }

    // 显示进度条Dialog
    private void showDialog() {
        if (mProDialog == null)
            mProDialog = new ProDialog(this);
        mProDialog.setProgressBar(true).showProDialog();
    }

    // 关闭进度条Dialog
    private void closeDialog() {
        if (mProDialog != null)
            mProDialog.closeProDialog();
    }

    /**
     * 通过回调方法处理图片
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ZDialogConstantUtil.RESULT_PHOTO_CODE:
                /**
                 * 拍照
                 */
                closeChoosePictrueDialog();
                Uri photoUri = ChoosePictrueUtil.photoUri;
                if (photoUri != null) {
                    // 通知系统刷新图库
                    Intent localIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, photoUri);
                    sendBroadcast(localIntent);
                    // 启动裁剪
                    cropImageUri(photoUri);
                }
                break;
            case ZDialogConstantUtil.RESULT_LOAD_CODE:
                /**
                 * 从相册中选择图片
                 */
                closeChoosePictrueDialog();
                if (data == null) {
                    return;
                } else {
                    Uri uri = data.getData();// 获取图片是以content开头
                    if (uri != null) {
                        cropImageUri(uri);// 开始裁剪
                    }
                }
                break;
            case ZDialogConstantUtil.REQUEST_CROP_CODE: // 裁剪图片结果处理
                if (data != null) {
                    compressUri(imgPath);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 开启Android截图的功能
     */
    private void cropImageUri(Uri uri) {
        try {
            String dirfilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Ibooker" + File.separator + "photoCache";
            if (!FileUtil.isFileExist(dirfilePath)) {// 创建文件夹
                FileUtil.createSDDirs(dirfilePath);
            }
            // 照片URL地址-获取系统时间 然后将裁剪后的图片保存至指定的文件夹
            imgPath = dirfilePath + File.separator + System.currentTimeMillis() + ".jpg";
            File imgFile = new File(imgPath);
            Uri imageUri = Uri.fromFile(imgFile);

            /**
             * 开启Android截图的功能
             */
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(uri, "image/*");
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 100);
            intent.putExtra("aspectY", 100);
            intent.putExtra("outputX", 500);//X方向上的比例
            intent.putExtra("outputY", 500);//Y方向上的比例
            intent.putExtra("scale", true);//是否保留比例
            // 添加权限
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            // 输出路径
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            // 输出格式
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            // 不启用人脸识别
            intent.putExtra("noFaceDetection", true);
            intent.putExtra("return-data", false);
            // 竖屏
            intent.putExtra(MediaStore.Images.ImageColumns.ORIENTATION, 0);
            startActivityForResult(intent, ZDialogConstantUtil.REQUEST_CROP_CODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 压缩图片并显示
    private void compressUri(final String imgPath) {
        if (!TextUtils.isEmpty(imgPath)) {
            showDialog();
            mProDialog.setMessage("图片压缩中...");
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        picBitmap = BitmapFun.imgPathToBitmap1(imgPath, BitmapFun.BitmapFunConfig.RGB_565);
//                        picBitmap = BitmapFun.imgPathToBitmap2(imgPath);
                        if (picBitmap == null) {
                            imgfile = "";
                        } else {
                            // 压缩
                            picBitmap = BitmapFun.compressBitmapByRatio(picBitmap, 400, 400, BitmapFun.BitmapFunConfig.RGB_565, BitmapFun.BitmapFunCompressFormat.JPEG);
                            // 将字节转换成base64码
                            imgfile = bitMapToBase64(picBitmap);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    myHandler.sendEmptyMessage(3);
                }
            });
            if (mExecutorService == null || mExecutorService.isShutdown())
                mExecutorService = Executors.newCachedThreadPool();
            mExecutorService.execute(thread);
        }
    }

    // 将BitMap转换成Base64
    private String bitMapToBase64(Bitmap bitmap) {
        try {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
            byte[] bytes = stream.toByteArray();
            // 将字节转换成base64码
            String str = Base64.encodeToString(bytes, Base64.DEFAULT);
            stream.close();
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过handler执行主线程
     */
    private MyHandler myHandler = new MyHandler(this);

    private static class MyHandler extends Handler {
        // 定义一个对象用来引用Activity中的方法
        private final WeakReference<Activity> mActivity;

        MyHandler(Activity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ChoosePictrueActivity currentActivity = (ChoosePictrueActivity) mActivity.get();
            currentActivity.closeDialog();
            currentActivity.closeChoosePictrueDialog();
            switch (msg.what) {
                case 3:// 上传图片
                    if (currentActivity.picBitmap != null) {
                        currentActivity.imgFileTv.setText(currentActivity.imgfile);
                        // 更新用户图像
                        currentActivity.picImg.setImageBitmap(currentActivity.picBitmap);
                        // 上传至服务端
//                        currentActivity.updatePic();
                    } else {
                        Toast.makeText(currentActivity, "图片获取失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }

    // 权限设置结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case ZDialogConstantUtil.PERMISSION_CAMERA_REQUEST_CODE:
                if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    choosePictrueDialog.startPhoto();
                } else {
                    Toast.makeText(this, "获取拍照权限失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case ZDialogConstantUtil.PERMISSION_READ_EXTERNAL_STORAGE_REQUEST_CODE:
                if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    choosePictrueDialog.startPhoto();
                } else {
                    Toast.makeText(this, "sdcard中读取数据的权限失败", Toast.LENGTH_SHORT).show();
                }
                break;
            case ZDialogConstantUtil.PERMISSION_WRITE_EXTERNAL_STORAGE_REQUEST_CODE:
                if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    choosePictrueDialog.startPhoto();
                } else {
                    Toast.makeText(this, "写入数据到扩展存储卡(SD)权限失败", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
