//package com.example.proxymall.activities;
//
//import android.content.Context;
//import android.os.Handler;
//import android.support.v4.view.PagerAdapter;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.RelativeLayout;
//
//import com.example.mykit_campus.Activities.ImageLoaders.ImageLoader;
//import com.example.mykit_campus.R;
//
//
//public class goods_imfo_CustomPagerAdapter extends PagerAdapter {
//
//    Context mContext;
//    LayoutInflater mLayoutInflater;
//
//    private int index = 0;
//    ImageLoader imageLoader;
//
//    int[] mResources = { R.drawable.mtns,
//            R.drawable.scholarss, R.drawable.scholar, R.drawable.exxonn,
//            R.drawable.ecommerce, R.drawable.nlgg };
//
//    public goods_imfo_CustomPagerAdapter(Context context) {
//        mContext = context;
//        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        imageLoader = new ImageLoader(context);
//    }
//
//    @Override
//    public int getCount() {
//        return goods_info.Imageurls.size();
//    }
//
//    @Override
//    public boolean isViewFromObject(View view, Object object) {
//        return view == ((RelativeLayout) object);
//
//    }
//
//
//    @Override
//    public Object instantiateItem(final ViewGroup container, int position) {
//        final View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);
//
//
//        final ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
//        final ProgressBar progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
//        imageView.setImageResource(R.drawable.bg);
//
////        imageView.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Intent intent = new Intent(mContext, ImageDisplay.class);
////                intent.putExtra("flag", goods_info.Imageurls.get(index));
////                // intent.putExtra("name", TAG_NAME);
////                mContext.startActivity(intent);
////            }
////        });
//
////        Animation scaleAnimation = AnimationUtils.loadAnimation(
////                mContext , R.anim.alpha_animation);
////        scaleAnimation.setDuration(1500);
////        imageView.startAnimation(scaleAnimation);
//
//
//        final Handler handler = new Handler();
////      //  Runnable runnable = new Runnable() {
////            @Override
////            public void run() {
//
//
//             //   index = index % mResources.length;
//
//
//
//                if(index==goods_info.Imageurls.size()){
//                    index=0;
//                }
//
//
//
//              //  imageView.setImageResource(mResources[index]);
//               // Toast.makeText(mContext,Commerce.Imageurls.get(index),Toast.LENGTH_SHORT).show();
//
//
//
//                progressBar.setVisibility(View.GONE);
//                imageLoader.DisplayImage(goods_info.Imageurls.get(index), imageView);
//               // imageView.setImageResource(mResources[index]);
//             //   handler.postDelayed(this, 3000);
//
//
//                index++;
//    //}
////        };
////        handler.postDelayed(runnable, 5000);
//
//
//      //
//
//
//        //imageView.setImageResource(mResources[position]);
//
//
//
//
//
//        container.addView(itemView);
//
//        return itemView;
//    }
//
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        container.removeView((RelativeLayout) object);
//    }
//}