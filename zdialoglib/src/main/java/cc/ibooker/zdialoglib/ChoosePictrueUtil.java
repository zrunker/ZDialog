package cc.ibooker.zdialoglib;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import java.io.File;

/**
 * 选择图片管理类
 * Created by 邹峰立 on 2017/7/10.
 * https://github.com/zrunker/ZDialog
 */
public class ChoosePictrueUtil {
    public static Uri photoUri;// 拍照URI，拍照相对于新生产的，所有要进行转换
    public static String imgPath;// 图片地址

    // 生成一个URI地址
    public static Uri createImageViewUri(@NonNull Context context) {
        String name = "tmp" + System.currentTimeMillis();
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, name);
        values.put(MediaStore.Images.Media.DISPLAY_NAME, name + ".png");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/*");
        return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

    // 刪除URI
    public static void deleteUri(@NonNull Context context, @NonNull Uri uri) {
        context.getContentResolver().delete(uri, null, null);
    }

    // 启动拍照1
    public static void startPhoto(@NonNull Context context) {
        photoUri = createImageViewUri(context);
        if (photoUri != null) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri); // 将拍照后的图像保存到photoUri
            ((Activity) context).startActivityForResult(intent, ZDialogConstantUtil.RESULT_PHOTO_CODE);
        }
    }

    // 启动拍照1
    public static void startPhoto(@NonNull Fragment fragment) {
        if (fragment.getContext() != null)
            photoUri = createImageViewUri(fragment.getContext());
        if (photoUri != null) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri); // 将拍照后的图像保存到photoUri
            fragment.startActivityForResult(intent, ZDialogConstantUtil.RESULT_PHOTO_CODE);
        }
    }

    // 启动拍照2
    public static void startPhoto2(@NonNull Context context) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ((Activity) context).startActivityForResult(intent, ZDialogConstantUtil.RESULT_PHOTO_CODE);
    }

    // 启动拍照2
    public static void startPhoto2(@NonNull Fragment fragment) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fragment.startActivityForResult(intent, ZDialogConstantUtil.RESULT_PHOTO_CODE);
    }

    // 启动拍照3
    public static void startPhoto3(@NonNull Context context) {
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                // 创建文件夹
                boolean isSuccess = true;
                File dirFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "ZDialogPhotoCache");
                if (!dirFile.exists()) {
                    isSuccess = dirFile.mkdirs();
                }
                if (isSuccess) {
                    // 创建文件
                    imgPath = dirFile.getAbsolutePath() + File.separator + System.currentTimeMillis() + ".png";
                    File imgFile = new File(imgPath);

                    Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    // 判断是否是AndroidN以及更高的版本
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        photoUri = ZFileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".ZFileProvider", imgFile);
                        // 添加权限
                        openCameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    } else
                        photoUri = Uri.fromFile(imgFile);
                    openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    ((Activity) context).startActivityForResult(openCameraIntent, ZDialogConstantUtil.RESULT_PHOTO_CODE);
                }
            } else {
                Toast.makeText(context, "SD卡不存在,请插入SD卡！", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 启动拍照3
    public static void startPhoto3(@NonNull Fragment fragment) {
        try {
            if (fragment.getContext() != null) {
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    // 创建文件夹
                    boolean isSuccess = true;
                    File dirFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "ZDialogPhotoCache");
                    if (!dirFile.exists()) {
                        isSuccess = dirFile.mkdirs();
                    }
                    if (isSuccess) {
                        // 创建文件
                        imgPath = dirFile.getAbsolutePath() + File.separator + System.currentTimeMillis() + ".png";
                        File imgFile = new File(imgPath);

                        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        // 判断是否是AndroidN以及更高的版本
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            photoUri = ZFileProvider.getUriForFile(fragment.getContext(), fragment.getContext().getApplicationContext().getPackageName() + ".ZFileProvider", imgFile);
                            // 添加权限
                            openCameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        } else
                            photoUri = Uri.fromFile(imgFile);
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                        fragment.startActivityForResult(openCameraIntent, ZDialogConstantUtil.RESULT_PHOTO_CODE);
                    }
                } else {
                    Toast.makeText(fragment.getContext(), "SD卡不存在,请插入SD卡！", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 从相册中获取1
    public static void startLocal(@NonNull Context context) {
        // 调用android的图库
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        ((Activity) context).startActivityForResult(intent, ZDialogConstantUtil.RESULT_LOAD_CODE);
    }

    // 从相册中获取1
    public static void startLocal(@NonNull Fragment fragment) {
        // 调用android的图库
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        fragment.startActivityForResult(intent, ZDialogConstantUtil.RESULT_LOAD_CODE);
    }

    // 从相册中获取2
    public static void startLocal2(Context context) {
        // 调用android的图库
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        ((Activity) context).startActivityForResult(intent, ZDialogConstantUtil.RESULT_LOAD_CODE);
    }

    // 从相册中获取2
    public static void startLocal2(@NonNull Fragment fragment) {
        // 调用android的图库
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        fragment.startActivityForResult(intent, ZDialogConstantUtil.RESULT_LOAD_CODE);
    }
}
