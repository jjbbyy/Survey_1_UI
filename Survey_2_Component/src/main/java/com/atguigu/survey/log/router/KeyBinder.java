package com.atguigu.survey.log.router;

public class KeyBinder {
	
	private static ThreadLocal<String> local = new ThreadLocal<>();
	
	public static String getKey() {
		return local.get();
	}
	
	public static void bindKey(String key) {
		local.set(key);
	}
	
	public static void removeKey() {
		local.remove();
	}
}
