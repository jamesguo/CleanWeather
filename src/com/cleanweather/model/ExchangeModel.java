package com.cleanweather.model;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class ExchangeModel extends ParcelableModel {
	public ArrayList<CityModel> cityModels;
	@SuppressWarnings("unchecked")
	public ExchangeModel(Parcel source) {
		// TODO Auto-generated constructor stub
		cityModels = (ArrayList<CityModel>)source.readArrayList(CityModel.class.getClassLoader());
	}

	public ExchangeModel() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeValue(cityModels);
	}
	public static final Parcelable.Creator<ExchangeModel> CREATOR = new Creator<ExchangeModel>() {

		@Override
		public ExchangeModel[] newArray(int size) {
			return new ExchangeModel[size];
		}

		@Override
		public ExchangeModel createFromParcel(Parcel source) {
			return new ExchangeModel(source);
		}
	};

}
