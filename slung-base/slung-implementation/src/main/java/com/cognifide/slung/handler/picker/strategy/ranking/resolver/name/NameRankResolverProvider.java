package com.cognifide.slung.handler.picker.strategy.ranking.resolver.name;

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
import com.cognifide.slung.api.handler.NameAware;

@Service
@Component(
    label = Constants.LABEL_PREFIX + "Name rank resolver provider",
    description = "Provides rank resolver based on handler name",
    metatype = true)
@Properties({
  @Property(name = RankResolverProviders.ACTIVE_PROPERTY, label = RankResolverProviders.ACTIVE_LABEL, description = RankResolverProviders.ACTIVE_DESCRIPTION,
      boolValue = true),
  @Property(name = org.osgi.framework.Constants.SERVICE_RANKING, propertyPrivate = false,
      intValue = RankResolverProviders.NAME_RANK_RESOLVER_PROVIDER_RANKING)})
public class NameRankResolverProvider implements RankResolverProvider<NameAware> {

  private boolean active;

  private SortingServiceTracker<NameAware> serviceTracker;

  @Activate
  @Modified
  protected void activate(final BundleContext bundleContext, final Map<String, Object> configuration) {
    serviceTracker = new SortingServiceTracker<NameAware>(bundleContext, NameAware.class.getName());
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
  public RankResolver<NameAware> provide(HandlerContext handlerContext) {
    return new NameRankResolver(filter(handlerContext, serviceTracker.getSortedServices()), handlerContext);
  }

  @Deactivate
  protected void deactivate() {
    serviceTracker.close();
  }
}
