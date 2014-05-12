package com.cleanweather.model;

import com.cleanweather.util.TypefaceManager.IconicTypeface;

public enum WeatherIcon implements Icon{

	baitianqing(0xe022),
	yejianqing(0xe023),
	baitianduoyun(0xe021),
	yejianduoyun(0xe024),
	yin(0xe020),
	zhenyu(0xe01f),
	leizhenyu(0xe01e),
	leizhenyubanyoubingbao(0xe01d),
	yujiaxue(0xe01c),
	xiaoyu(0xe01b),
	zhongyu(0xe01a),
	dayu(0xe019),
	baoyu(0xe018),
	dabaoyu(0xe017),
	tedaboyu(0xe016),
	zhenxue(0xe015),
	xiaoxue(0xe014),
	zhongxue(0xe013),
	daxue(0xe012),
	baoxue(0xe011),
	wu(0xe010),
	dongyu(0xe00f),
	shachenbao(0xe00e),
	xiaodaozhongyu(0xe00d),
	zhongdaodayu(0xe00c),
	dadaobaoyu(0xe00b),
	baoyuzhuanbaoyu(0xe00a),
	dabaoyuzhuantedabaoyu(0xe009),
	xiaodaozhongxue(0xe008),
	zhongdaodaxue(0xe007),
	dadaobaoxue(0xe006),
	fuchen(0xe005),
	yangsha(0xe004),
	qiangshachenbao(0xe003),
	mai(0xe010),
	shuaxin(0xe000), more(0xe025);
	
	private final int mIconUtfValue;
    private WeatherIcon(int iconUtfValue) {
        mIconUtfValue = iconUtfValue;
    }

    @Override
    public IconicTypeface getIconicTypeface() {
        return IconicTypeface.WEATHER;
    }

    @Override
    public int getIconUtfValue() {
        return mIconUtfValue;
    }

}
