package com.cognifide.slung.handler.picker.strategy.ranking.resolver;

import com.cognifide.slung.api.Constants;

public class RankResolverProviders {

  public static final String ACTIVE_LABEL = "Active";

  public static final String ACTIVE_DESCRIPTION = "Turns provider on/off";

  public static final String ACTIVE_PROPERTY = Constants.PROPERTY_PREFIX + "provider.active";

  public static final int NAME_RANK_RESOLVER_PROVIDER_RANKING = 6000;

  public static final int ANNOTATION_RANK_RESOLVER_PROVIDER_RANKING = 5000;

  public static final int RESOURCE_TYPE_RANK_RESOLVER_PROVIDER_RANKING = 4000;

  public static final int PROPERTY_NAME_RANK_RESOLVER_PROVIDER_RANKING = 3000;

  public static final int X_TYPE_RANK_RESOLVER_PROVIDER_RANKING = 2000;

  public static final int VALUE_RANK_RESOLVER_PROVIDER_RANKING = 1000;

  private RankResolverProviders() {
    throw new AssertionError();
  }
}
