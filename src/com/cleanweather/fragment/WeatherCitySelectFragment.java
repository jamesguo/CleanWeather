package com.cleanweather.fragment;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;

import com.cleanweather.R;
import com.cleanweather.activity.WeatherDisplayActivity;
import com.cleanweather.model.CityModel;
import com.cleanweather.util.DBUtil;
import com.cleanweather.util.WeatherCNCitySelectUtil;

public class WeatherCitySelectFragment extends Fragment {
	private ListView listView;
	private ArrayList<CityModel> arrayList = new ArrayList<CityModel>();
	private ArrayList<CityModel> subedList = new ArrayList<CityModel>();
	private LayoutInflater inflater;
	private int cellHeight, indexHeight;
	@SuppressWarnings("deprecation")
	protected LayoutParams layoutParams = new LayoutParams(LayoutParams.FILL_PARENT, 0, 1.0f);
//	protected LinearLayout indexLayout;
//	protected ArrayList<String> indexStrings = new ArrayList<String>();
	protected float indexHight = 0;
	private EditText editText;
	private ImageView clearImage;
	private CityListAdapter resultAdapter;
//	protected OnTouchListener ontouch = new OnTouchListener() {
//		@Override
//		public boolean onTouch(View v, MotionEvent event) {
//			switch (event.getAction()) {
//			case MotionEvent.ACTION_DOWN:
//			case MotionEvent.ACTION_MOVE:
//				hideInput();
//				if (indexStrings.size() > 0) {
//					indexHight = v.getHeight();
//					if (indexHight <= 0) {
//						indexHight = CleanWeatherApplication.windowHeight - CleanWeatherApplication.getStatuBar() - getResources().getDimension(R.dimen.titlePreferredHeight)
//								- getResources().getDimension(R.dimen.city_select_search_height);
//					}
//					int l = (int) (event.getY() / (float) (indexHight / indexStrings.size()));
//					if (l >= indexStrings.size()) {
//						l = indexStrings.size() - 1;
//					} else if (l < 0) {
//						l = 0;
//					}
//					int index = getIndexPosition(l);
//					if (index >= 0) {
//						listView.setSelection(index);
//					} else {
//						listView.setSelection(0);
//					}
//				}
//				break;
//			case MotionEvent.ACTION_UP:
//				// alphabetLayout.setBackgroundResource(0);
//				break;
//			}
//			return true;
//		}
//	};
	private OnItemClickListener itemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			// TODO Auto-generated method stub
			CityModel cityModel = (CityModel) parent.getAdapter().getItem(position);
			if(cityModel!=null&&!cityModel.area_id.equals("-1")){
				boolean add=false;
				if(subedList.indexOf(arrayList.get(position))>=0){
					DBUtil.updateCity(cityModel.area_id, false);
					add=false;
				}else{
					DBUtil.updateCity(cityModel.area_id, true);
					add=true;
				}
				subedList = WeatherCNCitySelectUtil.getSubArrayList();
				((WeatherDisplayActivity)getActivity()).updateAdapter(cityModel,add);
				editText.setText("");
				resetList();
				hideInput();
			}
		}
	};
	public OnScrollListener onScrollListener = new OnScrollListener() {
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			// TODO Auto-generated method stub
			if(scrollState==SCROLL_STATE_TOUCH_SCROLL||scrollState==SCROLL_STATE_FLING){
				hideInput();
			}
		}
		@Override
		public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
			// TODO Auto-generated method stub   
		}
	};
	private OnClickListener clearClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			editText.setText("");
			resetList();
		}
	};
	protected TextWatcher cityTextWatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			String name = s.toString().replace(" ", "");
			// citySelectBaseFragment.onSingleCitySelected();
			if(name.equals("")){
				clearImage.setVisibility(View.GONE);
				resetList();
			}else{
				clearImage.setVisibility(View.VISIBLE);
//				indexLayout.setVisibility(View.GONE);
				arrayList = getMatchedList(name);
				if (arrayList == null) {
					arrayList = new ArrayList<CityModel>();
				}
				resultAdapter.notifyDataSetChanged();
			}
		}
	}; 
	public static WeatherCitySelectFragment newInstance(Bundle bundle) {
		WeatherCitySelectFragment f = new WeatherCitySelectFragment();
		f.setArguments(bundle);
		return f;
	}
	@SuppressWarnings("unchecked")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(getArguments()!=null){
			if(getArguments().getSerializable("SUBLIST")!=null){
				subedList = (ArrayList<CityModel>) getArguments().getSerializable("SUBLIST");
			}else{
				subedList = WeatherCNCitySelectUtil.getSubArrayList();
			}
		}
		View view = inflater.inflate(R.layout.weather_city_list_layout, null);
		this.inflater = inflater;
		cellHeight = (int) getResources().getDimension(R.dimen.city_select_text_height);
		indexHeight = (int) getResources().getDimension(R.dimen.city_select_index_height);
		listView = (ListView) view.findViewById(R.id.city_list);
		resultAdapter = new CityListAdapter();
		listView.setAdapter(resultAdapter);
		listView.setOnItemClickListener(itemClickListener);
		listView.setOnScrollListener(onScrollListener);
//		indexLayout = (LinearLayout) view.findViewById(R.id.list_view_index);
		
//		indexLayout.setOnTouchListener(ontouch);
		editText = (EditText) view.findViewById(R.id.auto_fix_text);
		editText.addTextChangedListener(cityTextWatcher);
		clearImage = (ImageView) view.findViewById(R.id.clean_search_text);
		clearImage.setOnClickListener(clearClickListener);
		resetList();
		return view;
	}

	protected ArrayList<CityModel> getMatchedList(String name) {
		// TODO Auto-generated method stub
		return WeatherCNCitySelectUtil.getAllLikeCityList(name);
	}

	protected void resetList() {
		// TODO Auto-generated method stub
		arrayList.clear();
//		indexStrings.clear();
		if(subedList!=null&&subedList.size()>0){
			CityModel indexModel = new CityModel();
			indexModel.area_id = "-1";
			indexModel.name_cn = "已关注城市";
			arrayList.add(0, indexModel);
			arrayList.addAll(subedList);
//			indexStrings.add("关注");
		}
//		indexStrings.addAll(WeatherCNCitySelectUtil.getIndexList());
//		arrayList.addAll(WeatherCNCitySelectUtil.getCityListWithIndex());
		resultAdapter.notifyDataSetChanged();
//		indexLayout.setVisibility(View.VISIBLE);
//		initIndex();
	}

	protected void hideInput() {
		// TODO Auto-generated method stub
		InputMethodManager im = ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE));
		im.hideSoftInputFromWindow(editText.getWindowToken(), 0);
	}

//	protected int getIndexPosition(int postion) {
//		// TODO Auto-generated method stub
//		if(indexStrings.contains("关注")){
//			if(postion==0){
//				return 0;
//			}else{
//				return WeatherCNCitySelectUtil.getPosition(postion-1)+subedList.size()+1;
//			}
//		}else{
//			return WeatherCNCitySelectUtil.getPosition(postion);
//		}
//	}

//	protected void initIndex() {
//		// TODO Auto-generated method stub
//		if (indexStrings.size() > 0 && getActivity() != null) {
//			indexLayout.removeAllViews();
//			indexHight = CleanWeatherApplication.windowHeight - CleanWeatherApplication.getStatuBar() - getResources().getDimension(R.dimen.titlePreferredHeight)
//					- getResources().getDimension(R.dimen.city_select_search_height);
//			int itemH = (int) (((indexHight) / indexStrings.size()));
//			layoutParams.height = itemH;
//			int textSize = (int) getResources().getDimension(R.dimen.text_size_city_list_index);
//			for (int i = 0, count = indexStrings.size(); i < count; i++) {
//				TextView textView = new TextView(getActivity());
//				textView.setText(indexStrings.get(i));
//				textView.setGravity(Gravity.CENTER);
//				textView.setTextColor(Color.parseColor("#515c68"));
//				if (textSize == 0) {
//					textView.setTextSize(getResources().getDimension(R.dimen.text_size_city_list_index));
//				} else {
//					textView.setTextSize(textSize);
//				}
//				textView.setLayoutParams(layoutParams);
//				indexLayout.addView(textView);
//			}
//			indexLayout.setOnTouchListener(ontouch);
//		}
//	}

	class CityListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return arrayList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return arrayList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder viewHolder;
			if (convertView == null) {
				viewHolder = new ViewHolder();
				convertView = inflater.inflate(R.layout.city_list_item, null);
				viewHolder.cityName = (TextView) convertView.findViewById(R.id.city_name);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			if ("-1".equals(arrayList.get(position).area_id)) {
				viewHolder.cityName.setTextAppearance(convertView.getContext(), R.style.text_16_white);
				viewHolder.cityName.setShadowLayer(1, 0.5f, 0.5f, Color.parseColor("#ff000000"));
				viewHolder.cityName.setText(arrayList.get(position).name_cn);
				viewHolder.cityName.setHeight(indexHeight);
				convertView.setBackgroundResource(R.drawable.city_list_index_backgroud);
			} else {
				viewHolder.cityName.setTextAppearance(convertView.getContext(), R.style.text_20_black);
				viewHolder.cityName.setShadowLayer(0, 0.5f, 0.5f, Color.parseColor("#33ffffff"));
				viewHolder.cityName.setHeight(cellHeight);
				viewHolder.cityName.setText(arrayList.get(position).name_cn);
				convertView.setBackgroundResource(R.drawable.city_list_cell_backgroud_selector);
			}
//			if(subedList.indexOf(arrayList.get(position))>=0){
//				viewHolder.cityName.setTextAppearance(convertView.getContext(), R.style.text_20_33b5e6);
//			}
			return convertView;
		}

		class ViewHolder {
			ImageView selectImage;
			TextView cityName;
		}
	}
}
