package com.cognifide.slung.manual.action.impl;

import com.cognifide.slung.manual.action.Action;
import com.cognifide.slung.manual.action.ActionWrapper;
import java.lang.reflect.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SetAction extends ActionWrapper<Void, Object> {

	private static final Logger LOGGER = LoggerFactory.getLogger(SetAction.class);

	private final Object that;

	private final Field field;

	public SetAction(Action<Object> action, Object that, Field field) {
		super(action);
		this.that = that;
		this.field = field;
	}

	@Override
	protected Void transform(Object value) {
		try {
			field.setAccessible(true);
			field.set(that, value);
		} catch (IllegalAccessException x) {
			log(x);
		} catch (IllegalArgumentException x) {
			log(x);
		} catch (SecurityException x) {
			log(x);
		}
		return null;
	}

	private void log(Throwable t) {
		LOGGER.error("Error while setting value for " + field.getName(), t);
	}

}
