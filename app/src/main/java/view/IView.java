package view;

import java.util.List;

import model.BaiduImage;

public interface IView {

       void showImage(List<BaiduImage.ImgsBean> list);//显示图片，需要的数据：网络请求的数据
}
