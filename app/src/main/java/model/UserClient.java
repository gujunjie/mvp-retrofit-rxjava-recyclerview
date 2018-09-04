package model;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface UserClient {

    @GET("imgs/?col=美女&tag=比基尼&sort=0&pn=60&rn=60&p=channel&from=1")
    Observable<BaiduImage> getImage();
}
