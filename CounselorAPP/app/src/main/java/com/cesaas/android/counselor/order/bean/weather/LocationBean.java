package com.cesaas.android.counselor.order.bean.weather;

import java.io.Serializable;

/**
 * Author FGB
 * Description
 * Created 2017/4/20 14:07
 * Version 1.zero
 */
public class LocationBean implements Serializable {

    /**
     * country : CN
     * path : 深圳,深圳,广东,中国
     * timezone : Asia/Shanghai
     * timezone_offset : +08:00
     * name : 深圳
     * id : WS10730EM8EV
     */
    private String country;
    private String path;
    private String timezone;
    private String timezone_offset;
    private String name;
    private String id;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getTimezone_offset() {
        return timezone_offset;
    }

    public void setTimezone_offset(String timezone_offset) {
        this.timezone_offset = timezone_offset;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
