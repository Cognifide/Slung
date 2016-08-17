package com.cognifide.slung.handler.picker.strategy.ranking.resolver.xtype;

import com.cognifide.slung.api.Constants;
import com.cognifide.slung.api.context.handler.HandlerContext;
import com.cognifide.slung.api.handler.XTypeAware;
import static com.cognifide.slung.api.handler.picker.HandlerPickingStrategies.filter;
import com.cognifide.slung.api.handler.picker.strategy.ranking.resolver.RankResolver;
import com.cognifide.slung.api.handler.picker.strategy.ranking.resolver.RankResolverProvider;
import com.cognifide.slung.handler.picker.strategy.ranking.resolver.RankResolverProviders;
import com.cognifide.slung.handler.picker.strategy.ranking.resolver.xtype.cache.TypeCache;
import com.cognifide.slung.api.osgi.PropertyReader;
import java.util.Map;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Modified;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.commons.osgi.SortingServiceTracker;
import org.osgi.framework.BundleContext;

@Service
@Component(
    label = Constants.LABEL_PREFIX + "X type rank resolver provider",
    description = "Provides rank resolver based on x type",
    metatype = true)
@Properties({
  @Property(name = RankResolverProviders.ACTIVE_PROPERTY, label = RankResolverProviders.ACTIVE_LABEL, description = RankResolverProviders.ACTIVE_DESCRIPTION,
      boolValue = true),
  @Property(name = org.osgi.framework.Constants.SERVICE_RANKING, propertyPrivate = false,
      intValue = RankResolverProviders.X_TYPE_RANK_RESOLVER_PROVIDER_RANKING)})
public class XTypeRankResolverProvider implements RankResolverProvider<XTypeAware> {

  @Reference
  private TypeCache typeCache;

  private boolean active;

  private SortingServiceTracker<XTypeAware> serviceTracker;

  @Activate
  @Modified
  protected void activate(final BundleContext bundleContext, final Map<String, Object> configuration) {
    serviceTracker = new SortingServiceTracker<XTypeAware>(bundleContext, XTypeAware.class.getName());
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
  public RankResolver<XTypeAware> provide(HandlerContext handlerContext) {
    return new XTypeRankResolver(filter(handlerContext, serviceTracker.getSortedServices()), handlerContext, typeCache);
  }

  @Deactivate
  protected void deactivate() {
    serviceTracker.close();
  }
}
