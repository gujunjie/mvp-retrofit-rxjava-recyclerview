package model;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import util.NetworkUtil;


public class RetrofitModel implements IModel{

    private Context context;


    @Override
    public void getImageBean(final getImageBeanListener listener) {

        this.context=context;

        File cacheFile=new File(context.getExternalCacheDir().toString(),"cache");

        int cacheSize=10*1024*1024;

        Cache cache=new Cache(cacheFile,cacheSize);

        OkHttpClient client=new OkHttpClient.Builder()
                .addInterceptor(new CacheInterceptor())
                .addNetworkInterceptor(new CacheInterceptor())
                .cache(cache)
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://image.baidu.com/data/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        UserClient userClient = retrofit.create(UserClient.class);

        userClient.getImage().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaiduImage>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaiduImage value) {
                        listener.onSuccess(value.getImgs());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    @Override
    public void getContext(Context context) {
        this.context=context;
    }


    class CacheInterceptor implements Interceptor
    {


        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetworkUtil.isNetworkConnected(context)) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            okhttp3.Response originalResponse=chain.proceed(request);
            if (NetworkUtil.isNetworkConnected(context)) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置(注掉部分)
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        //.header("Cache-Control", "max-age=3600")
                        .removeHeader("Pragma") // 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                        .build();
            } else {
                int maxAge= 60 * 60;
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-age=" + maxAge)
                        .removeHeader("Pragma")
                        .build();
            }
        }


    }





}
