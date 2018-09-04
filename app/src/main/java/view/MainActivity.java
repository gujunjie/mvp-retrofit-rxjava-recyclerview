package view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.abc.myapplication29.R;

import java.util.ArrayList;
import java.util.List;

import Base.BaseActivity;
import Base.BasePresenter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.CompositeDisposable;
import model.BaiduImage;
import presenter.Presenter;

public class MainActivity extends BaseActivity<IView,Presenter> implements IView {



    Presenter presenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);



        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);

        } else {
            getImageList();

        }




    }

    @Override
    public Presenter createPresenter() {
        presenter = new Presenter(this);
        return presenter;
    }


    @Override
    public void showImage(List<BaiduImage.ImgsBean> list) {



        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.rv_recyclerView);
        StaggeredGridLayoutManager manager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);

        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);

        PhotoAdapter adapter=new PhotoAdapter(list,MainActivity.this);


        recyclerView.setAdapter(adapter);
    }



    public void getImageList()
    {
        presenter.getImageList(MainActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CompositeDisposable disposable = new CompositeDisposable();
        disposable.dispose();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    getImageList();

                } else {

                    Toast.makeText(MainActivity.this,"你没有授予权限",Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }

}
