package com.refresh;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yt.pulltorefresh.recyclerview.SimpleFooterHolder;
import com.yt.pulltorefresh.view.SimpleFooterView;

import java.util.ArrayList;


public class GridAdapter_Redu extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


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
    public Context context;
    private ArrayList<String> dataList = new ArrayList<>();


    private final int NORMALLAYOUT = 0;
    private final int FOOTERLAYOUT = 1;
    public SimpleFooterHolder mFooterHolder;

    public GridAdapter_Redu(Context context) {
        this.context = context;
    }

    public void addAll(ArrayList<String> list) {
        int lastIndex = this.dataList.size();
        if (this.dataList.addAll(list)) {
            notifyItemRangeInserted(lastIndex, list.size());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == dataList.size())
            return FOOTERLAYOUT;
        else
            return NORMALLAYOUT;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == NORMALLAYOUT) {
            view = LayoutInflater.from(context).inflate(R.layout.grid_redu_item, parent, false);
            return new NormalHolder(view);
        } else {
            mFooterHolder = new SimpleFooterHolder(new SimpleFooterView(context));
            return mFooterHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NormalHolder) {
            //点击事件
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            ((NormalHolder) holder).setData(position);
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size() + 1;
    }


    class NormalHolder extends RecyclerView.ViewHolder {
        ImageView img;

        public NormalHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.homepage_grid_picpic);
        }

        public void setData(int position) {
            Glide.with(context)
                    .load(imgUrls[position % imgUrls.length])
                    .into(img);
        }
    }

}
