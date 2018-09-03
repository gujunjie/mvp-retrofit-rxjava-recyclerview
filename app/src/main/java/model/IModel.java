package model;

import android.content.Context;

import java.util.List;

public interface IModel {

      void getImageBean(getImageBeanListener listener);

      void getContext(Context context);

      interface getImageBeanListener
      {
          void onSuccess(List<BaiduImage.ImgsBean> list);
          void onFailure(String error);
      }
}
