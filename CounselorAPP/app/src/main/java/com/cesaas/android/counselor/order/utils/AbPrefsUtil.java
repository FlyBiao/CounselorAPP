package com.cesaas.android.counselor.order.utils;

import java.util.Map;
import java.util.Set;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

/**
 * 缓存工具类
 * @author FlyBiao
 *
 */
public class AbPrefsUtil {
	private static AbPrefsUtil prefsUtil;
	public Context context;
	public SharedPreferences prefs;
	public SharedPreferences.Editor editor;

	public synchronized static AbPrefsUtil getInstance() {
		return prefsUtil;
	}

	public static void init(Context context, String prefsname, int mode) {
		prefsUtil = new AbPrefsUtil();
		prefsUtil.context = context;
		prefsUtil.prefs = prefsUtil.context.getSharedPreferences(prefsname, mode);
		prefsUtil.editor = prefsUtil.prefs.edit();
	}

	private AbPrefsUtil() {
	}

	public boolean getBoolean(String key, boolean defaultVal) {
		return this.prefs.getBoolean(key, defaultVal);
	}

	public boolean getBoolean(String key) {
		return this.prefs.getBoolean(key, false);
	}

	public String getString(String key, String defaultVal) {
		return this.prefs.getString(key, defaultVal);
	}

	public String getString(String key) {
		return this.prefs.getString(key, null);
	}

	public int getInt(String key, int defaultVal) {
		return this.prefs.getInt(key, defaultVal);
	}

	public int getInt(String key) {
		return this.prefs.getInt(key, 0);
	}

	public float getFloat(String key, float defaultVal) {
		return this.prefs.getFloat(key, defaultVal);
	}

	public float getFloat(String key) {
		return this.prefs.getFloat(key, 0f);
	}

	public long getLong(String key, long defaultVal) {
		return this.prefs.getLong(key, defaultVal);
	}

	public long getLong(String key) {
		return this.prefs.getLong(key, 0l);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public Set<String> getStringSet(String key, Set<String> defaultVal) {
		return this.prefs.getStringSet(key, defaultVal);
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public Set<String> getStringSet(String key) {
		return this.prefs.getStringSet(key, null);
	}

	public Map<String, ?> getAll() {
		return this.prefs.getAll();
	}

	public void putString(String key, String value) {
		editor.putString(key, value);
		editor.commit();
		// return this;
	}

	public void putInt(String key, int value) {
		editor.putInt(key, value);
		editor.commit();
	}

	public void putFloat(String key, float value) {
		editor.putFloat(key, value);
		editor.commit();
	}

	public void putLong(String key, long value) {
		editor.putLong(key, value);
		editor.commit();
	}

	public void putBoolean(String key, boolean value) {
		editor.putBoolean(key, value);
		editor.commit();
	}

	public void commit() {
		editor.commit();
	}

	public void cleanAll() {
		editor.clear().commit();
	}

	public void removeKey(String key) {
		editor.remove(key).commit();
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public AbPrefsUtil putStringSet(String key, Set<String> value) {
		editor.putStringSet(key, value);
		editor.commit();
		return this;
	}

}
