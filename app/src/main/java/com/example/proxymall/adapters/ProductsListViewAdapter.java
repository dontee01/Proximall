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

import com.example.proxymall.Imageloader.ImageLoader;
import com.example.proxymall.R;
import com.example.proxymall.activities.goods_info;
import com.example.proxymall.app.AppConfig;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

//import com.example.mykit_campus.Activities.CommerceFile.ShowRoom.goods_info;
//import com.example.mykit_campus.Activities.ImageLoaders.ImageLoader;

public class ProductsListViewAdapter extends BaseAdapter {

	// Declare Variables
	Context context;
	LayoutInflater inflater;
	ArrayList<HashMap<String, String>> data;
	ImageLoader imageLoader;
	HashMap<String, String> resultp = new HashMap<String, String>();

	public ProductsListViewAdapter(Context context,
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
		TextView name,price,distance,datePosted,senderLocation,description;
		TextView question;
		TextView date_posted;
		ImageView image;

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View itemView = inflater.inflate(R.layout.product_listview_item, parent, false);
		// Get the position
		resultp = data.get(position);

		// Locate the TextViews in listview_item.xml
		name = (TextView) itemView.findViewById(R.id.name);
		price = (TextView) itemView.findViewById(R.id.price);

		image = (ImageView) itemView.findViewById(R.id.image);

		name.setText(resultp.get(AppConfig.TAG_NAME));
		price.setText(resultp.get(AppConfig.TAG_PRICE)+"NGN");
		//price.setText(NumberFormat.getNumberInstance(Locale.US).format(Integer.parseInt(resultp.get(Commerce.TAG_PRICE)))+"NGN");

//		Picasso.with(context)
//				.load(resultp.get(AppConfig.TAG_IMAGE))
//				.into(image);
		//description.setText(resultp.get(Commerce.TAG_BRIEF_DESCRIPTION));
	//	distance.setText(resultp.get(AppConfig.TAG_DISTANCE));
		//senderLocation.setText(resultp.get(Commerce.TAG_SENDER_LOCATION));
		// Capture position and set results to the ImageView
		// Passes flag images URL into ImageLoader.class

		Picasso.with(context)
				.load(resultp.get(AppConfig.TAG_IMAGE))
				.into(image);

		// Capture ListView item click
		itemView.setOnClickListener(new View.OnClickListener() {
//imageLoader.DisplayImage(resultp.get(AppConfig.TAG_IMAGE), image);
			@Override
			public void onClick(View arg0) {
				// Get the position
				resultp = data.get(position);
				Intent intent = new Intent(context, goods_info.class);
				// Pass all data rank
//				intent.putExtra("from", "commerce");
				intent.putExtra("id", resultp.get(AppConfig.TAG_MALL_ID));
				intent.putExtra("name", resultp.get(AppConfig.TAG_NAME));
				intent.putExtra("category", resultp.get(AppConfig.TAG_AD_CATEGORY));
				intent.putExtra("sender_phone_number", resultp.get(AppConfig.TAG_SENDER_PHONE_NUMBER));
				intent.putExtra("mallName", resultp.get(AppConfig.TAG_MALL_NAME));
				// Pass all data country
				intent.putExtra("price", resultp.get(AppConfig.TAG_PRICE));
				intent.putExtra("condition", resultp.get(AppConfig.TAG_CONDITION));
				intent.putExtra("mallImageUrl", resultp.get(AppConfig.TAG_MALL_IMAGE));
				intent.putExtra("description", resultp.get(AppConfig.TAG_SPECIFICATION));
				// Pass all data population
			//	intent.putExtra("date",resultp.get(AskForAdviceMain.TAG_DATE));
				// Pass all data flag
				intent.putExtra("image", resultp.get(AppConfig.TAG_IMAGE));

				intent.putExtra("image_numbers", resultp.get(AppConfig.TAG_IMAGE_NUMBERS));
				intent.putExtra("sender_location", resultp.get(AppConfig.TAG_SENDER_LOCATION));
				// Start SingleItemView Class
				context.startActivity(intent);
				((Activity) context). overridePendingTransition(R.anim.moving_in_side,
						R.anim.hold_animmation);

			}
		});
		return itemView;
	}
}
