package com.example.proxymall.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

//import com.example.mykit_campus.Activities.CommerceFile.ShowRoom.goods_info;
//import com.example.mykit_campus.Activities.ImageLoaders.ImageLoader;
import com.example.proxymall.Imageloader.ImageLoader;
import com.example.proxymall.R;
import com.example.proxymall.activities.CategoryProductActivity;
import com.example.proxymall.activities.MainActivity;
import com.example.proxymall.activities.MallActivity;
import com.example.proxymall.app.AppConfig;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class MainActivityMallAdapter extends BaseAdapter {

	// Declare Variables
	Context context;
	LayoutInflater inflater;
	ArrayList<HashMap<String, String>> data;
	ImageLoader imageLoader;
	HashMap<String, String> resultp = new HashMap<String, String>();

	public MainActivityMallAdapter(Context context,
                                   ArrayList<HashMap<String, String>> arraylist) {
		this.context = context;
		data = arraylist;
		imageLoader = new ImageLoader(context);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		// Declare Variables
		TextView name,distance,datePosted,senderLocation,description;
		TextView question;
		TextView date_posted;
		ImageView image;

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View itemView = inflater.inflate(R.layout.malls_items, parent, false);
		// Get the position
		resultp = data.get(position);

		// Locate the TextViews in listview_item.xml
		name = (TextView) itemView.findViewById(R.id.name);
		distance = (TextView) itemView.findViewById(R.id.distance);
	//	datePosted = (TextView) itemView.findViewById(R.id.date_posted);
		//description = (TextView) itemView.findViewById(R.id.goods_description);
		//senderLocation = (TextView) itemView.findViewById(R.id.goods_location);

		// Locate the ImageView in listview_item.xml
		image = (ImageView) itemView.findViewById(R.id.image);

		//DecimalFormat formatter = new DecimalFormat("#,###,###");
		//String pricee = formatter.format(String.valueOf(resultp.get(Commerce.TAG_PRICE)));

		// Capture position and set results to the TextViews
		name.setText(resultp.get(AppConfig.TAG_NAME));
		//price.setText("#"+resultp.get(Commerce.TAG_PRICE));
		//price.setText(NumberFormat.getNumberInstance(Locale.US).format(Integer.parseInt(resultp.get(Commerce.TAG_PRICE)))+"NGN");

		//description.setText(resultp.get(Commerce.TAG_BRIEF_DESCRIPTION));
		distance.setText(resultp.get(AppConfig.TAG_DISTANCE));
		//senderLocation.setText(resultp.get(Commerce.TAG_SENDER_LOCATION));
		// Capture position and set results to the ImageView
		// Passes flag images URL into ImageLoader.class
		Picasso.with(context)
				.load(resultp.get(AppConfig.TAG_IMAGE))
				.into(image);
	//	imageLoader.DisplayImage(resultp.get(AppConfig.TAG_IMAGE), image);
		// Capture ListView item click
		itemView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// Get the position
				resultp = data.get(position);
				Intent intent = new Intent(context, MallActivity.class);

//				intent.putExtra("category",resultp.get(AppConfig.TAG_NAME));
				intent.putExtra("image",resultp.get(AppConfig.TAG_IMAGE));
				intent.putExtra("name",resultp.get(AppConfig.TAG_NAME));
				intent.putExtra("mallId",resultp.get(AppConfig.TAG_MALL_ID));
				intent.putExtra("imageNumber",resultp.get(AppConfig.TAG_IMAGE_NUMBERS));

//				intent.putExtra("from", "commerce");
//				intent.putExtra("id", resultp.get(Commerce.TAG_ID));
//				intent.putExtra("name", resultp.get(Commerce.TAG_NAME));
//				intent.putExtra("category", resultp.get(Commerce.TAG_AD_CATEGORY));
//				intent.putExtra("sender_phone_number", resultp.get(Commerce.TAG_SENDER_PHONE_NUMBER));
//				intent.putExtra("price", resultp.get(Commerce.TAG_PRICE));
//				intent.putExtra("condition", resultp.get(Commerce.TAG_CONDITION));
//				intent.putExtra("description", resultp.get(Commerce.TAG_BRIEF_DESCRIPTION));
//
//				intent.putExtra("image", resultp.get(Commerce.TAG_IMAGE));
//
//				intent.putExtra("image_numbers", resultp.get(Commerce.TAG_IMAGE_NUMBERS));
//				intent.putExtra("sender_location", resultp.get(Commerce.TAG_SENDER_LOCATION));
//				// Start SingleItemView Class
				context.startActivity(intent);
// 				((Activity) context). overridePendingTransition(R.anim.moving_in_side,
//						R.anim.hold_animmation);

			}
		});
		return itemView;
	}
}
