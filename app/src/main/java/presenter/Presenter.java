package presenter;

import android.content.Context;

import java.util.List;

import Base.BasePresenter;
import model.BaiduImage;
import model.IModel;
import model.RetrofitModel;
import view.IView;

public class Presenter extends BasePresenter<IView> implements IModel.getImageBeanListener{

    IView iView;
    IModel iModel;

    public Presenter(IView iView)
    {
        this.iView=iView;
        iModel=new RetrofitModel();
    }


    public void getImageList(Context context)
    {
        iModel.getContext(context);
        iModel.getImageBean(this);
    }


    @Override
    public void onSuccess(List<BaiduImage.ImgsBean> list) {
            iView.showImage(list);
    }

    @Override
    public void onFailure(String error) {

    }
}
