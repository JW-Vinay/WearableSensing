package com.wearables.zephyr;

public interface ConnectedListener<T> {
	public void Connected(ConnectedEvent<T> eventArgs);
}
