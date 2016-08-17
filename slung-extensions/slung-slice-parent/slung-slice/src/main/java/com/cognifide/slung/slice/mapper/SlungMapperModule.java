package com.cognifide.slung.slice.mapper;

import com.cognifide.slice.api.provider.ModelProvider;
import com.cognifide.slice.api.qualifier.Nullable;
import com.cognifide.slice.api.scope.ContextScoped;
import com.cognifide.slice.cq.mapper.processor.ImageFieldProcessor;
import com.cognifide.slice.mapper.MapperBuilder;
import com.cognifide.slice.mapper.api.Mapper;
import com.cognifide.slice.mapper.api.processor.FieldPostProcessor;
import com.cognifide.slung.api.handler.picker.HandlerPicker;
import com.cognifide.slung.slice.processor.DiffChildrenFieldPostProcessor;
import com.cognifide.slung.slice.processor.DiffFieldPostProcessor;
import com.cognifide.slung.slice.processor.OldFieldPostProcessor;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.servlet.ServletRequest;
import org.apache.sling.api.scripting.SlingBindings;
import org.apache.sling.api.scripting.SlingScriptHelper;
import org.ops4j.peaberry.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SlungMapperModule extends AbstractModule {

  private static final Logger LOGGER = LoggerFactory.getLogger(SlungMapperModule.class);

  @Override
  protected void configure() {
  }

  @Provides
  @ContextScoped
  public Mapper getMapper(final MapperBuilder mapperBuilder, @Nullable final ServletRequest request, @Nullable final ModelProvider modelProvider) {
    mapperBuilder
        .addDefaultSliceProcessors()
        .addFieldProcessor(new ImageFieldProcessor());
    updateWithDiffPostProcessors(mapperBuilder, request, modelProvider);
    return mapperBuilder.build();
  }

  private void updateWithDiffPostProcessors(final MapperBuilder mapperBuilder, final ServletRequest request, final ModelProvider modelProvider) {
    HandlerPicker handlerPicker = retrieveHandlerPicker(request);
    if (null != handlerPicker) {
      addFieldPostProcessorAsFirst(mapperBuilder, new DiffFieldPostProcessor(request, handlerPicker));
    }
    mapperBuilder.addFieldPostProcessor(new OldFieldPostProcessor(request));
    if (null != modelProvider) {
      mapperBuilder.addFieldPostProcessor(new DiffChildrenFieldPostProcessor(request, modelProvider));
    }
  }

  private HandlerPicker retrieveHandlerPicker(ServletRequest request) {
    SlingScriptHelper sling = findSlingScriptHelperInRequest(request);
    return null == sling ? null : findHandlerPickerIn(sling);
  }

  private SlingScriptHelper findSlingScriptHelperInRequest(ServletRequest request) {
    SlingScriptHelper slingScriptHelper = null;
    if (null != request) {
      Object o = request.getAttribute(SlingBindings.class.getName());
      if (null != o) {
        SlingBindings slingBindings = (SlingBindings) o;
        slingScriptHelper = slingBindings.getSling();
      }
    }
    return slingScriptHelper;
  }

  private HandlerPicker findHandlerPickerIn(SlingScriptHelper sling) {
    HandlerPicker handlerPicker = null;
    try {
      handlerPicker = sling.getService(HandlerPicker.class);
    } catch (ServiceException x) {
      LOGGER.error("Error while retrieving " + HandlerPicker.class, x);
    }
    return handlerPicker;
  }

  private MapperBuilder addFieldPostProcessorAsFirst(MapperBuilder mapperBuilder, FieldPostProcessor fieldPostProcessor) {
    try {
      Field postProcessorsField = MapperBuilder.class.getDeclaredField("postProcessors");
      postProcessorsField.setAccessible(true);
      Object postProcessors = postProcessorsField.get(mapperBuilder);
      Method clearMethod = postProcessors.getClass().getDeclaredMethod("addLast", Object.class);
      clearMethod.invoke(postProcessors, fieldPostProcessor);
    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException | NoSuchMethodException | SecurityException x) {
      LOGGER.error("Error while cleaning post processors", x);
    }
    return mapperBuilder;
  }
}
