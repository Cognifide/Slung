package com.cognifide.slung.manual.action.impl;

import com.cognifide.slung.api.context.handler.HandlerContext;
import com.cognifide.slung.api.context.handler.HandlerContextBuilder;
import com.cognifide.slung.api.handler.picker.HandlerPicker;
import com.cognifide.slung.manual.action.Action;
import com.google.common.base.Preconditions;
import java.lang.reflect.Field;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.scripting.SlingBindings;
import org.apache.sling.api.scripting.SlingScriptHelper;

public class DiffAction implements Action<Object> {

	private final SlingHttpServletRequest request;

	private final String propertyName;

	private final Field field;

	private final Object value;

	public DiffAction(SlingHttpServletRequest request, String propertyName, Field field, Object value) {
		this.request = Preconditions.checkNotNull(request);
		this.propertyName = Preconditions.checkNotNull(propertyName);
		this.field = Preconditions.checkNotNull(field);
		this.value = Preconditions.checkNotNull(value);
	}

	@Override
	public Object execute() {
		HandlerPicker handlerPicker = findHandlerPickerService();
		return null == handlerPicker ? value : handlerPicker.tryToProcessUsing(createHandlerContext());
	}

	private HandlerContext createHandlerContext() {
		return HandlerContextBuilder.from(null)
				.resource(request.getResource())
				.propertyName(propertyName)
				.field(field)
				.value(value)
				.build();
	}

	private HandlerPicker findHandlerPickerService() {
		SlingBindings slingBindings = (SlingBindings) request.getAttribute(SlingBindings.class.getName());
		SlingScriptHelper sling = slingBindings.getSling();
		return sling.getService(HandlerPicker.class);
	}
}
