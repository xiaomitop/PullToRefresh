package com.refresh;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yt.pulltorefresh.recyclerview.BaseAdapterHelper;
import com.yt.pulltorefresh.recyclerview.RefreshRecyclerAdapter;

import java.util.List;

/**
 * 功能：
 * 作者：yangtao
 * 创建时间：2016/10/28 17:33
 */
public class TestAdapter extends RefreshRecyclerAdapter<String>{

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

    public TestAdapter(Context context, List<String> data) {
        super(context, data);
    }

    @Override
    public int getItemLayoutId() {
        return R.layout.grid_redu_item;
    }

    @Override
    public void bindData(BaseAdapterHelper helper, int position, String item) {
        Glide.with(context)
                .load(item)
                .into((ImageView) helper.getView(R.id.homepage_grid_picpic));
    }
}
