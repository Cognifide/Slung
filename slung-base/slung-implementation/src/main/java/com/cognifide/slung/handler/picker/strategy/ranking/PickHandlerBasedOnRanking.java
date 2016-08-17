package com.cognifide.slung.handler.picker.strategy.ranking;

import com.cognifide.slung.api.Constants;
import com.cognifide.slung.api.context.handler.HandlerContext;
import com.cognifide.slung.api.handler.Handler;
import com.cognifide.slung.api.handler.picker.HandlerPickingStrategy;
import com.cognifide.slung.api.handler.picker.strategy.ranking.resolver.RankResolverProvider;
import com.cognifide.slung.handler.picker.strategy.ranking.resolver.ActiveRankResolverProviderPredicate;
import com.cognifide.slung.handler.picker.strategy.ranking.table.Ranking;
import com.cognifide.slung.handler.picker.strategy.ranking.table.RankingProvider;
import com.google.common.base.Optional;
import com.google.common.collect.Collections2;
import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.commons.osgi.SortingServiceTracker;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Component(
    label = Constants.LABEL_PREFIX + "Pick handler based on ranking",
    description = "Selects handler based on ranking computed on the fly. Name to select explicitly: " + PickHandlerBasedOnRanking.SHORT_NAME + ".",
    metatype = true)
@Properties(
    @Property(name = org.osgi.framework.Constants.SERVICE_RANKING, propertyPrivate = false,
        intValue = 200))
public class PickHandlerBasedOnRanking implements HandlerPickingStrategy {

  private static final Logger LOGGER = LoggerFactory.getLogger(HandlerPickingStrategy.class);

  public static final String SHORT_NAME = "ranking";

  private SortingServiceTracker<RankResolverProvider<? extends Handler>> serivceTracker;

  @Activate
  protected void activate(BundleContext bundleContext) {
    serivceTracker = new SortingServiceTracker<>(bundleContext, RankResolverProvider.class.getName());
    serivceTracker.open();
  }

  @Override
  public String getName() {
    return SHORT_NAME;
  }

  @Override
  public Optional<Handler> pickUsing(HandlerContext handlerContext) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("Handler selection by ranking started for '{}' resource's '{}' property",
          handlerContext.getResourcePath(), handlerContext.getPropertyName());
    }

    Ranking ranking = new RankingProvider(handlerContext, Collections2.filter(serivceTracker.getSortedServices(), ActiveRankResolverProviderPredicate.INSTANCE)).provide();
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("Ranking for resource type '{}' and property name '{}':\n{}", new Object[]{handlerContext.getResourceType(), handlerContext.getPropertyName(), ranking});
    }

    return Optional.fromNullable(ranking.selectHandler());
  }

  @Deactivate
  protected void deactivate() {
    serivceTracker.close();
  }
}
