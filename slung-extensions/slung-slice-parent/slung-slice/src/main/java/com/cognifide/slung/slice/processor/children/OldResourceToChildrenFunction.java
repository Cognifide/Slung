package com.cognifide.slung.slice.processor.children;

import com.cognifide.slice.api.provider.ModelProvider;
import com.cognifide.slice.mapper.annotation.Children;
import com.cognifide.slung.api.foster.AdoptChildrenFunction;
import com.cognifide.slung.api.foster.FosterParent;
import com.cognifide.slung.slice.processor.common.PropertyNameFinder;
import java.lang.reflect.Field;
import java.util.List;
import org.apache.sling.api.resource.Resource;

public class OldResourceToChildrenFunction extends AdoptChildrenFunction {

  private final ModelProvider modelProvider;

  private final Field field;

  private final Object value;

  public OldResourceToChildrenFunction(Resource resource, Field field, Object value, ModelProvider modelProvider) {
    super(resource, value, PropertyNameFinder.find(field));
    this.field = field;
    this.value = value;
    this.modelProvider = modelProvider;
  }

  @Override
  protected FosterParent<?> join(Iterable<Resource> newChildren, Iterable<Resource> oldChildren) {
    final Class<?> childType = field.getAnnotation(Children.class).value();
    return isOrdered()
        ? new MergeChildrenJoiner(newChildren, oldChildren, modelProvider, childType, value)
        : new SearchAndMergeChildrenJoiner(newChildren, oldChildren, modelProvider, childType);
  }

  private boolean isOrdered() {
    final Class<?> type = null == value ? field.getType() : value.getClass();
    return type.isArray() || List.class.isAssignableFrom(type);
  }

  @Override
  protected Class<?> findContainerClass() {
    return null == value ? field.getType() : value.getClass();
  }
}
