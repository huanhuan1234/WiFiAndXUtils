package bawei.com.huangminghuan;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.alibaba.fastjson.JSON;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import bawei.com.mylibrary.XListView;

public class MainActivity extends Activity implements XListView.IXListViewListener{

    private XListView xlv;

    int page=0;
    private MyAdapter adapter;
    private List<Movie.SubjectsBean> list;
    List<Movie.SubjectsBean> subjects;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        xlv = (XListView) findViewById(R.id.xlv);

        xlv.setPullRefreshEnable(true);
        xlv.setPullLoadEnable(true);
        xlv.setXListViewListener(this);

        RequestParams params = new RequestParams("https://api.douban.com/v2/movie/in_theaters?0b2bdeda43b5688921839c8ecb20399b&start=" + page + "&count=10");
        x.http().get(params, new Callback.CommonCallback<String>() {



            @Override
            public void onSuccess(String result) {
                Movie movie = JSON.parseObject(result, Movie.class);
                list = new ArrayList<>();
                subjects = movie.getSubjects();

                list.addAll(subjects);
                //设置适配器
                adapter = new MyAdapter(list,MainActivity.this);
                xlv.setAdapter(adapter);


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public void onRefresh() {

        page=0;
        RequestParams params = new RequestParams("https://api.douban.com/v2/movie/in_theaters?0b2bdeda43b5688921839c8ecb20399b&start=" + page + "&count=10");

        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Movie movie = JSON.parseObject(result, Movie.class);
                list = new ArrayList<>();
                subjects = movie.getSubjects();
                list.addAll(subjects);

                adapter=new MyAdapter(list,MainActivity.this);
                xlv.setAdapter(adapter);
                xlv.setRefreshTime("刚刚");
                xlv.stopRefresh();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public void onLoadMore() {

        RequestParams params = new RequestParams("https://api.douban.com/v2/movie/in_theaters?0b2bdeda43b5688921839c8ecb20399b&start=" + page++ + "&count=10");

        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Movie movie = JSON.parseObject(result, Movie.class);
                list.addAll(movie.getSubjects());
                adapter.notifyDataSetChanged();
                xlv.stopLoadMore();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
