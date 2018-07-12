package com.cesaas.android.counselor.order.base;

public class BaseEventBus<T> {

	public T t;
	
	public BaseEventBus(T t){
		this.t=t;
	}
	
	public BaseEventBus(){
	}

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}
	
	
}
