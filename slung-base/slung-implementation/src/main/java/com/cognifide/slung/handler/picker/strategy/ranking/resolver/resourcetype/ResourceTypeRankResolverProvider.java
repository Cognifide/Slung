package com.cognifide.slung.handler.picker.strategy.ranking.resolver.resourcetype;

import com.cognifide.slung.api.Constants;
import com.cognifide.slung.api.context.handler.HandlerContext;
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
import com.cognifide.slung.api.handler.ResourceTypeAware;

@Service
@Component(
    label = Constants.LABEL_PREFIX + "Resource type rank resolver provider",
    description = "Provides rank resolver based on resource type",
    metatype = true)
@Properties({
  @Property(name = RankResolverProviders.ACTIVE_PROPERTY, label = RankResolverProviders.ACTIVE_LABEL, description = RankResolverProviders.ACTIVE_DESCRIPTION,
      boolValue = true),
  @Property(name = org.osgi.framework.Constants.SERVICE_RANKING, propertyPrivate = false,
      intValue = RankResolverProviders.RESOURCE_TYPE_RANK_RESOLVER_PROVIDER_RANKING)})
public class ResourceTypeRankResolverProvider implements RankResolverProvider<ResourceTypeAware> {

  private boolean active;

  private SortingServiceTracker<ResourceTypeAware> serviceTracker;

  @Activate
  @Modified
  protected void activate(final BundleContext bundleContext, final Map<String, Object> configuration) {
    serviceTracker = new SortingServiceTracker<ResourceTypeAware>(bundleContext, ResourceTypeAware.class.getName());
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
  public RankResolver<ResourceTypeAware> provide(HandlerContext handlerContext) {
    return new ResourceTypeRankResolver(filter(handlerContext, serviceTracker.getSortedServices()), handlerContext);
  }

  @Deactivate
  protected void deactivate() {
    serviceTracker.close();
  }
}
