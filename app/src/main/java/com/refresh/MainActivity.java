package com.refresh;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;

import com.yt.pulltorefresh.i.callback.OnLoadingListener;
import com.yt.pulltorefresh.i.callback.OnRefreshListener;
import com.yt.pulltorefresh.recyclerview.RefreshRecyclerView;
import com.yt.pulltorefresh.view.RefreshLayout;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private TestAdapter testAdapter;
    private RefreshRecyclerView recyclerView;
    public String[] imgUrls = {
            "http://img5.duitang.com/uploads/item/201402/22/20140222113830_X3ddd.jpeg",
            "http://v1.qzone.cc/avatar/201403/01/10/36/531147afa4197738.jpg!200x200.jpg",
            "http://g.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=e4d7ed147af40ad115b1cfe7621c3de9/b7fd5266d016092445b47837d50735fae6cd340d.jpg",
            "http://img5q.duitang.com/uploads/item/201502/19/20150219182507_vGVaK.jpeg",
            "http://p1.qqyou.com/touxiang/uploadpic/2013-3/12/2013031212190118646.jpg",
            "http://img5.duitang.com/uploads/item/201412/08/20141208221323_YVJFk.png",
            "http://cdn.duitang.com/uploads/item/201408/02/20140802222651_GWuU2.png",
            "http://ent.dzwww.com/yulezhuanti/mtcbg/201510/W020151027467479100669.jpg",
            "http://p1.qqyou.com/touxiang/uploadpic/2013-3/10/2013031009323656495.jpg",
            "http://p1.qqyou.com/touxiang/uploadpic/2013-3/12/2013031212295986807.jpg",
            "http://f.hiphotos.baidu.com/zhidao/wh%3D600%2C800/sign=10742594d739b6004d9b07b1d9601912/9f2f070828381f30ec9eabdeab014c086f06f0c5.jpg",
            "http://a.hiphotos.baidu.com/zhidao/wh%3D600%2C800/sign=5bda8a18a71ea8d38a777c02a73a1c76/5882b2b7d0a20cf4598dc37c77094b36acaf9977.jpg",
            "http://a1.att.hudong.com/36/98/300001051406133039983418031.jpg"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RefreshLayout refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        if (refreshLayout != null) {
            // 刷新状态的回调
            refreshLayout.setRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh() {
                    // 延迟3秒后刷新成功
                    refreshLayout.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            refreshLayout.refreshComplete();
                        }
                    }, 3000);
                }
            });
        }
        //refreshLayout.autoRefresh();
        initGridView();
    }

    private void initGridView() {
        recyclerView = (RefreshRecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        testAdapter = new TestAdapter(MainActivity.this, Arrays.asList(imgUrls));
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setAdapter(testAdapter);
        recyclerView.setOnLoadingListener(new OnLoadingListener() {
            @Override
            public void loading() {
                // 延迟3秒后刷新成功
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        recyclerView.complete();
                        testAdapter.addAll(Arrays.asList(imgUrls));
                    }
                }, 3000);
            }
        });
        recyclerView.setLayoutManager(layoutManager);
    }
}
