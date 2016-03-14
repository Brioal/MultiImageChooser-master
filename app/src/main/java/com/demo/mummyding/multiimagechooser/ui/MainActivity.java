package com.demo.mummyding.multiimagechooser.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;

import com.demo.mummyding.multiimagechooser.R;
import com.demo.mummyding.multiimagechooser.Utils.ScreenUtil;
import com.demo.mummyding.multiimagechooser.adapter.ShowImageAdapter;
import com.demo.mummyding.multiimagechooser.model.ImageBean;
import com.facebook.drawee.backends.pipeline.Fresco;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private GridView gridView; // 显示图片
    private ShowImageAdapter adapter ;
    private List<ImageBean> selectedImage = new ArrayList<>(); // 存储要显示的图片
    public static final int PICK_IMG = 1; // 请求码
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
         * initialized before setContentView
         * Fresco is a powerful image-loader library
         * ScreenUtil is used for getting screen size
         */
        Fresco.initialize(this); // 初始化Fresco
        ScreenUtil.init(this);
        setContentView(R.layout.activity_main);
        initView();
    }
    private void initView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        gridView = (GridView) findViewById(R.id.image_grid);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.text_image_chooser);

        adapter = (ShowImageAdapter) new ShowImageAdapter(this).setList(selectedImage);
        gridView.setAdapter(adapter);
    }

    /*
     * this function is binding to "Image Choose" Button
     */
    public void chooseImage(View view) {
        Intent intent = new Intent(MainActivity.this,PhotoWallActivity.class);
        //传入以及已经选择的图片list
        //便于选择列表显示选中
        intent.putExtra(getString(R.string.id_selected_img), (Serializable) adapter.getList());
        startActivityForResult(intent,PICK_IMG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case PICK_IMG:
                if(data != null){ // 返回的序列化对象
                    adapter.setList((List<ImageBean>) data.getSerializableExtra(getString(R.string.id_selected_img)))
                           .notifyDataSetChanged();
                }
                break;
        }
    }
}
