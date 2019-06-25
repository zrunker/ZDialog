package cc.ibooker.zdialog;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import cc.ibooker.zdialoglib.WheelDialog;
import cc.ibooker.zdialoglib.bean.WheelDialogBean;

public class WheelActivity extends Activity {

    private ImageView image1, image2, image3, image4;
    private WheelDialog wheelDialog;
    private ArrayList<WheelDialogBean> datas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wheel);

        datas = new ArrayList<>();
        datas.add(new WheelDialogBean("选项1", "http://pic38.nipic.com/20140225/2531170_214014788000_2.jpg"));
        datas.add(new WheelDialogBean("选项2", "http://www.pptbz.com/pptpic/UploadFiles_6909/201406/2014063021281300.gif"));
        datas.add(new WheelDialogBean("选项3", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554738709986&di=90d889de1910eeae0f26c9a65d583435&imgtype=0&src=http%3A%2F%2Fimg4.duitang.com%2Fuploads%2Fblog%2F201401%2F16%2F20140116125541_ZkGAw.thumb.200_200_c.gif"));
        datas.add(new WheelDialogBean("选项4", "https://graph.baidu.com/resource/11172b58e94a4bdf170e701561186015.jpg", true, true));


        image1 = findViewById(R.id.image1);
        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click("http://pic38.nipic.com/20140225/2531170_214014788000_2.jpg");
            }
        });
        image2 = findViewById(R.id.image2);
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click("http://www.pptbz.com/pptpic/UploadFiles_6909/201406/2014063021281300.gif");
            }
        });
        image3 = findViewById(R.id.image3);
        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554738709986&di=90d889de1910eeae0f26c9a65d583435&imgtype=0&src=http%3A%2F%2Fimg4.duitang.com%2Fuploads%2Fblog%2F201401%2F16%2F20140116125541_ZkGAw.thumb.200_200_c.gif");
            }
        });
        image4 = findViewById(R.id.image4);
        image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click("https://graph.baidu.com/resource/11172b58e94a4bdf170e701561186015.jpg");
            }
        });

        Picasso.get().load("http://pic38.nipic.com/20140225/2531170_214014788000_2.jpg").into(image1);
        Picasso.get().load("http://www.pptbz.com/pptpic/UploadFiles_6909/201406/2014063021281300.gif").into(image2);
        Picasso.get().load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1554738709986&di=90d889de1910eeae0f26c9a65d583435&imgtype=0&src=http%3A%2F%2Fimg4.duitang.com%2Fuploads%2Fblog%2F201401%2F16%2F20140116125541_ZkGAw.thumb.200_200_c.gif").into(image3);
        Picasso.get().load("https://graph.baidu.com/resource/11172b58e94a4bdf170e701561186015.jpg").into(image4);


    }

    private void click(String url) {
        if (wheelDialog == null)
            wheelDialog = new WheelDialog(this);
        wheelDialog.setDatas(datas, url)
                .showWheelDialog();
    }
}
