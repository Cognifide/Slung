package com.cognifide.slung.manual;

import com.cognifide.slung.manual.action.Action;
import com.cognifide.slung.manual.action.impl.DiffAction;
import com.cognifide.slung.manual.action.impl.SetAction;
import com.google.common.collect.Sets;
import java.lang.reflect.Field;
import java.util.Set;
import org.apache.sling.api.SlingHttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FluentDiff {

	private final static Logger LOGGER = LoggerFactory.getLogger(FluentDiff.class);

	private final SlingHttpServletRequest request;

	private final Object that;

	private final Set<Action<Void>> actions;

	public FluentDiff(SlingHttpServletRequest request, Object that) {
		this.request = request;
		this.that = that;
		this.actions = Sets.newLinkedHashSet();
	}

	public FluentDiff with(String propertyName) {
		return with(propertyName, propertyName);
	}

	public FluentDiff with(String propertyName, String fieldName) {
		try {
			Field field = that.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			with(propertyName, field, field.get(that));
		} catch (NoSuchFieldException x) {
			log(x, fieldName);
		} catch (IllegalAccessException x) {
			log(x, fieldName);
		} catch (SecurityException x) {
			log(x, fieldName);
		}
		return this;
	}

	private void with(String propertyName, Field field, Object value) {
		actions.add(new SetAction(new DiffAction(request, propertyName, field, value), that, field));
	}

	private void log(Throwable t, String fieldName) {
		LOGGER.error("Error while reading value from field " + fieldName, t);
	}

	public FluentDiff with(String propertyName, Object value) {
		return with(propertyName, propertyName, value);
	}

	public FluentDiff with(String propertyName, String fieldName, Object value) {
		try {
			with(propertyName, that.getClass().getDeclaredField(fieldName), value);
		} catch (NoSuchFieldException x) {
			log(x, fieldName);
		} catch (SecurityException x) {
			log(x, fieldName);
		}
		return this;
	}

	public void diff() {
	}

}
