package bawei.com.huangminghuan;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by huanhuan on 2017/4/24.
 */
public class MyAdapter extends BaseAdapter{
    List<Movie.SubjectsBean> list;
    Context context;
    private final ImageLoader imageLoader;
    private TextView tv_title;
    private TextView tv_otitle;
    private ImageView iv1;
    private ImageView iv2;
    private ImageView iv3;

    public MyAdapter(List<Movie.SubjectsBean> list, Context context) {
        this.context=context;
        this.list=list;
        imageLoader = ImageLoader.getInstance();


    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Viewholder viewholder=null;
        if (convertView==null){
            viewholder = new Viewholder();
            convertView=View.inflate(context,R.layout.lv_item,null);
            viewholder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            viewholder. tv_otitle = (TextView) convertView.findViewById(R.id.tv_otitle);
            viewholder. iv1 = (ImageView) convertView.findViewById(R.id.iv1);
            viewholder.iv2 = (ImageView) convertView.findViewById(R.id.iv2);
            viewholder. iv3 = (ImageView) convertView.findViewById(R.id.iv3);
            convertView.setTag(viewholder);

        }else {
            viewholder= (Viewholder) convertView.getTag();
        }

        if (WifiUtils.getAPNType(context)==1){

            viewholder.tv_title.setText(list.get(position).getTitle());
            viewholder.tv_otitle.setText(list.get(position).getOriginal_title());

            imageLoader.displayImage(list.get(position).getCasts().get(0).getAvatars().getSmall(),viewholder.iv1);
            imageLoader.displayImage(list.get(position).getCasts().get(1).getAvatars().getMedium(),viewholder.iv2);
            imageLoader.displayImage(list.get(position).getCasts().get(2).getAvatars().getLarge(),viewholder.iv3);
        }else {
            viewholder.tv_title.setText(list.get(position).getTitle());
            viewholder.tv_otitle.setText(list.get(position).getOriginal_title());
        }

        return convertView;
    }

    class Viewholder{
        TextView tv_title;
        TextView tv_otitle;
        ImageView iv1;
        ImageView iv2;
        ImageView iv3;

    }
}
