package com.cesaas.android.counselor.order.bean.weather;

import java.util.List;

/**
 * Author FGB
 * Description
 * Created 2017/4/20 14:13
 * Version 1.zero
 */
public class WeatherResultsBean {
    private List<DailyBean> daily;

    private String last_update;

    private LocationBean location;

    public List<DailyBean> getDaily() {
        return daily;
    }

    public void setDaily(List<DailyBean> daily) {
        this.daily = daily;
    }

    public String getLast_update() {
        return last_update;
    }

    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }

    public LocationBean getLocation() {
        return location;
    }

    public void setLocation(LocationBean location) {
        this.location = location;
    }
}
