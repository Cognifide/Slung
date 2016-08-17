package com.cognifide.slung.handler.image;

import com.cognifide.slung.api.Constants;
import com.cognifide.slung.api.context.handler.HandlerContext;
import com.cognifide.slung.api.strategy.RankStrategies;
import com.cognifide.slung.handler.AbstractHandler;
import com.cognifide.slung.handler.Handlers;
import com.cognifide.slung.handler.image.configuration.ImageCssClassesConfiguration;
import com.day.cq.commons.jcr.JcrConstants;
import com.day.cq.wcm.foundation.Image;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import javax.jcr.RepositoryException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Modified;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.PropertyOption;
import org.apache.felix.scr.annotations.PropertyUnbounded;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.jackrabbit.core.fs.FileSystem;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Component(
    label = Constants.LABEL_PREFIX + "Image handler",
    description = "Creates diff for image components, information is retured as css classes. Use \"" + ImageHandlers.IMAGE_HANDLER_NAME + "\" as handler name in annotations.",
    metatype = true)
@Properties({
  @Property(name = Handlers.ACTIVE_PROPERTY, label = Handlers.ACTIVE_LABEL, description = Handlers.ACTIVE_DESCRIPTION,
      boolValue = true),
  @Property(name = org.osgi.framework.Constants.SERVICE_RANKING, propertyPrivate = false,
      intValue = ImageHandlers.IMAGE_HANDLER_RANKING),
  @Property(name = Handlers.NAME_STRATEGY_PROPERTY, label = Handlers.NAME_STRATEGY_LABEL, description = Handlers.NAME_STRATEGY_DESCRIPTION,
      options = {
        @PropertyOption(name = RankStrategies.ANY_NAME, value = RankStrategies.ANY_NAME),
        @PropertyOption(name = RankStrategies.CONTAINS_NAME, value = RankStrategies.CONTAINS_NAME),
        @PropertyOption(name = RankStrategies.EQUALS_NAME, value = RankStrategies.EQUALS_NAME),
        @PropertyOption(name = RankStrategies.SKIP_NAME, value = RankStrategies.SKIP_NAME)},
      value = RankStrategies.EQUALS_NAME),
  @Property(name = Handlers.ANNOTATIONS_PROPERTY, label = Handlers.ANNOTATIONS_LABEL, description = Handlers.ANNOTATIONS_DESCRIPTION, unbounded = PropertyUnbounded.ARRAY),
  @Property(name = Handlers.ANNOTATION_STRATEGY_PROPERTY, label = Handlers.ANNOTATION_STRATEGY_LABEL, description = Handlers.ANNOTATION_STRATEGY_DESCRIPTION,
      options = {
        @PropertyOption(name = RankStrategies.ANY_NAME, value = RankStrategies.ANY_NAME),
        @PropertyOption(name = RankStrategies.CONTAINS_NAME, value = RankStrategies.CONTAINS_NAME),
        @PropertyOption(name = RankStrategies.EQUALS_NAME, value = RankStrategies.EQUALS_NAME),
        @PropertyOption(name = RankStrategies.SKIP_NAME, value = RankStrategies.SKIP_NAME)},
      value = RankStrategies.CONTAINS_NAME),
  @Property(name = Handlers.RESOURCE_TYPES_PROPERTY, label = Handlers.RESOURCE_TYPES_LABEL, description = Handlers.RESOURCE_TYPES_DESCRIPTION, unbounded = PropertyUnbounded.ARRAY),
  @Property(name = Handlers.RESOURCE_TYPE_STRATEGY_PROPERTY, label = Handlers.RESOURCE_TYPE_STRATEGY_LABEL, description = Handlers.RESOURCE_TYPE_STRATEGY_DESCRIPTION,
      options = {
        @PropertyOption(name = RankStrategies.ANY_NAME, value = RankStrategies.ANY_NAME),
        @PropertyOption(name = RankStrategies.CONTAINS_NAME, value = RankStrategies.CONTAINS_NAME),
        @PropertyOption(name = RankStrategies.EQUALS_NAME, value = RankStrategies.EQUALS_NAME),
        @PropertyOption(name = RankStrategies.SKIP_NAME, value = RankStrategies.SKIP_NAME)},
      value = RankStrategies.CONTAINS_NAME),
  @Property(name = Handlers.PROPERTY_NAMES_PROPERTY, label = Handlers.PROPERTY_NAMES_LABEL, description = Handlers.PROPERTY_NAMES_DESCRIPTION, unbounded = PropertyUnbounded.ARRAY,
      value = ImageHandlers.IMAGE_PROPERTY_NAME),
  @Property(name = Handlers.PROPERTY_NAME_STRATEGY_PROPERTY, label = Handlers.PROPERTY_NAME_STRATEGY_LABEL, description = Handlers.PROPERTY_NAME_STRATEGY_DESCRIPTION,
      options = {
        @PropertyOption(name = RankStrategies.ANY_NAME, value = RankStrategies.ANY_NAME),
        @PropertyOption(name = RankStrategies.CONTAINS_NAME, value = RankStrategies.CONTAINS_NAME),
        @PropertyOption(name = RankStrategies.EQUALS_NAME, value = RankStrategies.EQUALS_NAME),
        @PropertyOption(name = RankStrategies.SKIP_NAME, value = RankStrategies.SKIP_NAME)},
      value = RankStrategies.CONTAINS_NAME),
  @Property(name = Handlers.X_TYPES_PROPERTY, label = Handlers.X_TYPES_LABEL, description = Handlers.X_TYPES_DESCRIPTION, unbounded = PropertyUnbounded.ARRAY,
      value = ImageHandlers.IMAGE_X_TYPE),
  @Property(name = Handlers.X_TYPE_STRATEGY_PROPERTY, label = Handlers.X_TYPE_STRATEGY_LABEL, description = Handlers.X_TYPE_STRATEGY_DESCRIPTION,
      options = {
        @PropertyOption(name = RankStrategies.ANY_NAME, value = RankStrategies.ANY_NAME),
        @PropertyOption(name = RankStrategies.CONTAINS_NAME, value = RankStrategies.CONTAINS_NAME),
        @PropertyOption(name = RankStrategies.EQUALS_NAME, value = RankStrategies.EQUALS_NAME),
        @PropertyOption(name = RankStrategies.SKIP_NAME, value = RankStrategies.SKIP_NAME)},
      value = RankStrategies.CONTAINS_NAME),
  @Property(name = Handlers.VALUES_PROPERTY, label = Handlers.VALUES_LABEL, description = Handlers.VALUES_DESCRIPTION, unbounded = PropertyUnbounded.ARRAY),
  @Property(name = Handlers.VALUE_STRATEGY_PROPERTY, label = Handlers.VALUE_STRATEGY_LABEL, description = Handlers.VALUE_STRATEGY_DESCRIPTION,
      options = {
        @PropertyOption(name = RankStrategies.ANY_NAME, value = RankStrategies.ANY_NAME),
        @PropertyOption(name = RankStrategies.CONTAINS_NAME, value = RankStrategies.CONTAINS_NAME),
        @PropertyOption(name = RankStrategies.EQUALS_NAME, value = RankStrategies.EQUALS_NAME),
        @PropertyOption(name = RankStrategies.SKIP_NAME, value = RankStrategies.SKIP_NAME)},
      value = RankStrategies.SKIP_NAME)})
public class ImageHandler extends AbstractHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(ImageHandler.class);

  private static final Joiner CSS_CLASSES_JOINER = Joiner.on(StringUtils.SPACE).skipNulls();

  @Reference
  private ImageCssClassesConfiguration configuration;

  @Activate
  @Modified
  @Override
  protected void activate(final Map<String, Object> configuration) {
    super.activate(configuration);
  }

  @Override
  public Iterable<Type> getAcceptedTypes() {
    return Arrays.<Type>asList(String.class);
  }

  @Override
  public String getName() {
    return ImageHandlers.IMAGE_HANDLER_NAME;
  }

  @Override
  public Object handle(HandlerContext handlerContext) {
    Object result = StringUtils.EMPTY;

    final Image oldImage = createOldImage(handlerContext);
    final Image image = createNewImage(handlerContext);

    if (exists(image)) {
      if (exists(oldImage)) {
        String compareResult = compareImages(image, oldImage);
        if (StringUtils.isNotBlank(compareResult)) {
          result = compareResult;
        }
      } else {
        result = join(configuration.getAddedCssClasses());
      }
    } else if (exists(oldImage)) {
      result = join(configuration.getRemovedCssClasses());
    }

    return result;
  }

  private Image createOldImage(HandlerContext handlerContext) {
    return createImage(handlerContext, handlerContext.findOldResource(), "oldImage", handlerContext.findOldValue());
  }

  private Image createImage(final HandlerContext handlerContext, final Resource resource, final String name, final Object path) {
    Image image = createImage(resource, handlerContext);
    if (!exists(image) && hasValid(path)) {
      image = createOrUpdateTemporaryImage(handlerContext, name, path);
    }
    return image;
  }

  private Image createImage(final Resource resource, final HandlerContext handlerContext) {
    return null == resource ? null : new Image(resource, handlerContext.getPropertyName());
  }

  private boolean hasValid(final Object path) {
    return null != path && String.class.isAssignableFrom(path.getClass());
  }

  private Image createOrUpdateTemporaryImage(final HandlerContext handlerContext, final String name, final Object path) {
    Image image = null;
    final ResourceResolver resourceResolver = handlerContext.getOriginalResource().getResourceResolver();
    if (resourceResolver.isLive()) {
      Resource temp = resourceResolver.getResource("/tmp");
      if (null != temp) {
        Resource child = temp.getChild(name);
        image = null == child
            ? createTemporaryImage(resourceResolver, temp, name, path)
            : updateTemporaryImage(child, path);
      }
    }
    return image;
  }

  private Image createTemporaryImage(final ResourceResolver resourceResolver, Resource temp, final String name, final Object path) {
    Image image = null;
    try {
      image = new Image(resourceResolver.create(temp, name, ImmutableMap.<String, Object>of("fileReference", path)));
    } catch (PersistenceException x) {
      LOGGER.error("Error while creating image resource under /tmp node.", x);
    }
    return image;
  }

  private Image updateTemporaryImage(Resource child, final Object path) {
    Image image = null;
    ModifiableValueMap map = child.adaptTo(ModifiableValueMap.class);
    if (null != map) {
      map.put("fileReference", path);
      image = new Image(child);
    }
    return image;
  }

  private Image createNewImage(HandlerContext handlerContext) {
    return createImage(handlerContext, handlerContext.findNewResource(), "newImage", handlerContext.findNewValue());
  }

  private boolean exists(Image image) {
    return null != image && image.hasContent();
  }

  private String compareImages(Image image, Image oldImage) {
    String result = StringUtils.EMPTY;
    if (StringUtils.equals(image.getHref(), oldImage.getHref())) {
      if (!lastModifiedIsSame(image, oldImage)) {
        result = join(configuration.getModifiedCssClasses());
      }
    } else if (StringUtils.isBlank(image.getHref())) {
      result = join(configuration.getRemovedCssClasses());
    } else if (StringUtils.isBlank(oldImage.getHref())) {
      result = join(configuration.getAddedCssClasses());
    } else if (imagesOrPathsAreDifferent(image, oldImage)) {
      result = join(configuration.getModifiedCssClasses());
    }
    return result;
  }

  private String join(Iterable<String> cssClasses) {
    return CSS_CLASSES_JOINER.join(cssClasses);
  }

  private boolean lastModifiedIsSame(Image image, Image oldImage) {
    Calendar c1 = null;
    Calendar c2 = null;
    try {
      c1 = image.getLastModified();
      c2 = oldImage.getLastModified();
    } catch (RepositoryException x) {
      LOGGER.error("Error while reading last modified property.", x);
    }
    return null != c1 && null != c2 ? DateUtils.isSameInstant(c1, c2) : false;
  }

  private boolean imagesOrPathsAreDifferent(Image image, Image oldImage) {
    return !imageAndOldImageHaveSameEnding(image, oldImage) || !lastModifiedIsSame(image, oldImage);
  }

  private boolean imageAndOldImageHaveSameEnding(Image image, Image oldImage) {
    return image.getHref().endsWith(
        StringUtils.substringAfter(
            oldImage.getHref(), JcrConstants.JCR_FROZENNODE + FileSystem.SEPARATOR));
  }
}
