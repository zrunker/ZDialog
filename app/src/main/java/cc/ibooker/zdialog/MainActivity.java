package cc.ibooker.zdialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import cc.ibooker.zdialoglib.ChoosePictrueDialog;
import cc.ibooker.zdialoglib.DelDialog;
import cc.ibooker.zdialoglib.DiyDialog;
import cc.ibooker.zdialoglib.Holder;
import cc.ibooker.zdialoglib.HolderCreator;
import cc.ibooker.zdialoglib.ProDialog;
import cc.ibooker.zdialoglib.ProgressDialog;
import cc.ibooker.zdialoglib.ScaleImageView;
import cc.ibooker.zdialoglib.TipDialog;
import cc.ibooker.zdialoglib.WheelDialog;
import cc.ibooker.zdialoglib.WheelDialog2;
import cc.ibooker.zdialoglib.ZDialogConstantUtil;
import cc.ibooker.zdialoglib.bean.WheelDialogBean;

public class MainActivity extends AppCompatActivity {
    private ProDialog proDialog;
    private TipDialog tipDialog;
    private DelDialog delDialog;
    private DiyDialog diyDialog;
    private ChoosePictrueDialog choosePictrueDialog;
    private WheelDialog wheelDialog;
    private WheelDialog2<WheelDialogBean> wheelDialog2;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Intent intent = new Intent(this, WheelActivity.class);
//        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (proDialog != null)
            proDialog.closeProDialog();
        if (tipDialog != null)
            tipDialog.closeTipDialog();
        if (delDialog != null)
            delDialog.closeDelDialog();
        if (diyDialog != null)
            diyDialog.closeDiyDialog();
        if (choosePictrueDialog != null)
            choosePictrueDialog.closeChoosePictrueDialog();
        if (wheelDialog != null)
            wheelDialog.closeWheelDialog();
        if (wheelDialog2 != null)
            wheelDialog2.closeWheelDialog2();
        if (progressDialog != null)
            progressDialog.closeProDialog();
    }

    // 显示进度条Dialog
    public void onProDialog(View view) {
        proDialog = new ProDialog(this);
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
    }

    public void onProgressDialog(View view) {
        progressDialog = new ProgressDialog(this);
        progressDialog.showProDialog();
    }

    // 显示提示Dialog
    public void onTipDialog(View view) {
        tipDialog = new TipDialog(this);
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
    }

    // 显示删除Dialog
    public void onDelDialog(View view) {
        delDialog = new DelDialog(this);
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
    }

    // 显示DiyDialog
    public void onDiyDialog(View view) {
        diyDialog = new DiyDialog(this, LayoutInflater.from(this).inflate(R.layout.activity_diy, null));
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
    }

    // 图片选择
    public void onDiyChoosePictrueDialog(View view) {
        choosePictrueDialog = new ChoosePictrueDialog(this);
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
        // 也可以修改各个按钮的属性
        choosePictrueDialog.setCancelBtnSize(16)
                .setChoosePictrueDialogWidth(50)
                .setBtnHeight(200)
                .setCancelBtnColor("#40aff2")
                .setCancelBtnText("取消")
                .showChoosePictrueDialog();
    }

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

    // 展示图片Dialog
    public void onWheelDialog(View v) {
        wheelDialog = new WheelDialog(this);
        ArrayList<WheelDialogBean> datas = new ArrayList<>();
        datas.add(new WheelDialogBean("选项1", "http://pic38.nipic.com/20140225/2531170_214014788000_2.jpg"));
        datas.add(new WheelDialogBean("选项2", "http://www.pptbz.com/pptpic/UploadFiles_6909/201406/2014063021281300.gif"));
        datas.add(new WheelDialogBean("选项3", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554738709986&di=90d889de1910eeae0f26c9a65d583435&imgtype=0&src=http%3A%2F%2Fimg4.duitang.com%2Fuploads%2Fblog%2F201401%2F16%2F20140116125541_ZkGAw.thumb.200_200_c.gif"));
        datas.add(new WheelDialogBean("选项4", "https://graph.baidu.com/resource/11172b58e94a4bdf170e701561186015.jpg", true, true));
        wheelDialog.setDatas(datas, "http://www.pptbz.com/pptpic/UploadFiles_6909/201406/2014063021281300.gif")
                .setPageIndicatorAlign(WheelDialog.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setCanceledOnTouchOutside(true)
                .setVpImageViewBackGroudColor("#FFFFFF")
                .showWheelDialog();
    }

    // 展示轮播Dialog
    ArrayList<WheelDialogBean> datas = new ArrayList<>();

    public void onWheelDialog2(final View v) {
        wheelDialog2 = new WheelDialog2<>(this);

        if (datas == null)
            datas = new ArrayList<>();
        datas.clear();
        datas.add(new WheelDialogBean("选项1", "http://pic38.nipic.com/20140225/2531170_214014788000_2.jpg"));
        datas.add(new WheelDialogBean("选项2", "http://www.pptbz.com/pptpic/UploadFiles_6909/201406/2014063021281300.gif"));
        datas.add(new WheelDialogBean("选项3", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554738709986&di=90d889de1910eeae0f26c9a65d583435&imgtype=0&src=http%3A%2F%2Fimg4.duitang.com%2Fuploads%2Fblog%2F201401%2F16%2F20140116125541_ZkGAw.thumb.200_200_c.gif"));

        wheelDialog2.init(new HolderCreator() {
            @Override
            public Object createHolder() {
                return new ViewHolder();
            }
        }, datas, 1)
                .showWheelDialog2();
    }

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
}
