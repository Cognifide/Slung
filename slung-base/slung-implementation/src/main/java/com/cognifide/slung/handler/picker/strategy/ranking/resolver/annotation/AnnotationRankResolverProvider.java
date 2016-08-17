package com.cognifide.slung.handler.picker.strategy.ranking.resolver.annotation;

import com.cognifide.slung.api.Constants;
import com.cognifide.slung.api.context.handler.HandlerContext;
import com.cognifide.slung.api.handler.AnnotationAware;
import static com.cognifide.slung.api.handler.picker.HandlerPickingStrategies.filter;
import com.cognifide.slung.api.handler.picker.strategy.ranking.resolver.RankResolver;
import com.cognifide.slung.api.handler.picker.strategy.ranking.resolver.RankResolverProvider;
import com.cognifide.slung.handler.picker.strategy.ranking.resolver.RankResolverProviders;
import com.cognifide.slung.api.osgi.PropertyReader;
import java.util.Map;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Modified;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.commons.osgi.SortingServiceTracker;
import org.osgi.framework.BundleContext;

@Service
@Component(
    label = Constants.LABEL_PREFIX + "Annotation rank resolver provider",
    description = "Provides rank resolver based on field annotation",
    metatype = true)
@Properties({
  @Property(name = RankResolverProviders.ACTIVE_PROPERTY, label = RankResolverProviders.ACTIVE_LABEL, description = RankResolverProviders.ACTIVE_DESCRIPTION,
      boolValue = true),
  @Property(name = org.osgi.framework.Constants.SERVICE_RANKING, propertyPrivate = false,
      intValue = RankResolverProviders.ANNOTATION_RANK_RESOLVER_PROVIDER_RANKING)})
public class AnnotationRankResolverProvider implements RankResolverProvider<AnnotationAware> {

  private boolean active;

  private SortingServiceTracker<AnnotationAware> serviceTracker;

  @Activate
  @Modified
  protected void activate(final BundleContext bundleContext, final Map<String, Object> configuration) {
    serviceTracker = new SortingServiceTracker<AnnotationAware>(bundleContext, AnnotationAware.class.getName());
    serviceTracker.open();

    active = PropertyReader.toBoolean(configuration, RankResolverProviders.ACTIVE_PROPERTY);
  }

  @Override
  public boolean isActive() {
    return active;
  }

  @Override
  public boolean resultCanBeCached() {
    return true;
  }

  @Override
  public RankResolver<AnnotationAware> provide(HandlerContext handlerContext) {
    return new AnnotationRankResolver(filter(handlerContext, serviceTracker.getSortedServices()), handlerContext);
  }

  @Deactivate
  protected void deactivate() {
    serviceTracker.close();
  }
}
