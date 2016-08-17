package com.cognifide.slung.manual.action;

public abstract class ActionWrapper<T, V> implements Action<T> {

	private final Action<V> action;

	public ActionWrapper(Action<V> action) {
		this.action = action;
	}

	@Override
	public T execute() {
		return transform(action.execute());
	}

	protected abstract T transform(V v);
}
