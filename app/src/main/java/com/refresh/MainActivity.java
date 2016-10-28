package com.refresh;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.yt.pulltorefresh.utils.State;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    //private GridAdapter_Redu gridReDuAdapter;
    private TestAdapter testAdapter;
    // 服务器端一共多少条数据
    private static final int TOTAL_COUNTER = 50;
    // 每一页展示多少条数据
    private static final int REQUEST_COUNT = 12;
    // 已经获取到多少条数据了
    private int mCurrentCounter = 0;
    //模拟的数据源
    private ArrayList<String> dataList;

    protected State mState = State.RESET;
    protected void setState(State mState) {
        this.mState = mState;
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                changeAdaperState();
            }
        });
    }
    //改变底部bottom的样式
    protected void changeAdaperState() {
        /*if (gridReDuAdapter != null && gridReDuAdapter.mFooterHolder != null) {
            gridReDuAdapter.mFooterHolder.setData(mState);
        }*/
        if (testAdapter != null && testAdapter.simpleFooterHolder != null) {
            testAdapter.simpleFooterHolder.setData(mState);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.home_page_recyclerview);
        initGridView();
    }

    private View initGridView() {
        mRecyclerView.setHasFixedSize(true);
        //滑动暂停加载网络图片,而且可以监听recycler是否滑动到底部
        mRecyclerView.addOnScrollListener(mOnScrollListener);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //gridReDuAdapter = new GridAdapter_Redu(MainActivity.this);
        testAdapter = new TestAdapter(MainActivity.this, getRemoteData());
        //gridReDuAdapter.addAll(getRemoteData());
        mRecyclerView.setAdapter(testAdapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                //如果是最后一个item，则设置占据3列，否则占据1列
                boolean isFooter = position == testAdapter.getItemCount() - 1;
                return isFooter ? 2 : 1;
            }
        });
        mRecyclerView.setLayoutManager(layoutManager);
        return mRecyclerView;
    }

    private EndlessRecyclerOnScrollListener mOnScrollListener = new EndlessRecyclerOnScrollListener() {
        @Override
        public void onLoadNextPage(View view) {
            super.onLoadNextPage(view);

            if (mState == State.LOADING) {
                Log.d("@TAG", "the state is Loading, just wait..");
                return;
            }

            if (mCurrentCounter < TOTAL_COUNTER) {
                // loading more
                requestData();
                Log.d("TAG", "请求数据");
            } else {
                //the end
                setState(State.RESET);
            }
        }
    };


    /**
     * 模拟请求网络
     */
    private void requestData() {
        setState(State.LOADING);
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //模拟请求远程数据
                testAdapter.addAll(getRemoteData());
                //加载完毕时
                setState(com.yt.pulltorefresh.utils.State.COMPLETE);
                Log.d("TAG", mCurrentCounter + "");

            }
        }.start();
    }

    //模拟请求数据
    private ArrayList<String> getRemoteData() {
        if (dataList == null)
            dataList = new ArrayList<>();
        //每次都清空一下
        dataList.clear();
        //要减去adapter最后一页
        for (int i = 0; i < REQUEST_COUNT; i++) {
            if (dataList.size() + mCurrentCounter >= TOTAL_COUNTER) {
                break;
            }
            dataList.add("账号" + (mCurrentCounter + i));
        }
        mCurrentCounter += dataList.size();
        return dataList;
    }

}
