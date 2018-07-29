package com.example.proxymall.adapters;

/**
 * Created by stephen on 5/22/2017.
 */


        import com.example.proxymall.R;
      //  import info.androidhive.customlistviewvolley.app.AppController;
        import com.example.proxymall.model.GetMalls;

        import java.util.List;

        import android.app.Activity;
        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.BaseAdapter;
        import android.widget.TextView;

   //     import com.android.volley.toolbox.ImageLoader;
   //     import com.android.volley.toolbox.NetworkImageView;

public class GetMallAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<GetMalls> mallItems;
  //  ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public GetMallAdapter(Activity activity, List<GetMalls> mallItems) {
        this.activity = activity;
        this.mallItems = mallItems;
    }

    @Override
    public int getCount() {
        return mallItems.size();
    }

    @Override
    public Object getItem(int location) {
        return mallItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.malls_items, null);
//
//        if (imageLoader == null)
//            imageLoader = AppController.getInstance().getImageLoader();
//        NetworkImageView thumbNail = (NetworkImageView) convertView
//                .findViewById(R.id.thumbnail);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView distance = (TextView) convertView.findViewById(R.id.distance);
  //      TextView genre = (TextView) convertView.findViewById(R.id.genre);
  //      TextView year = (TextView) convertView.findViewById(R.id.releaseYear);

        // getting movie data for the row
        GetMalls getMalls = mallItems.get(position);

        // thumbnail image
    //    thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);

        // title
        name.setText(getMalls.getId());


        distance.setText(getMalls.getName());



        return convertView;
    }

}