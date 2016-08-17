package com.cognifide.slung.handler.picker.strategy.ranking.table.storage;

import com.cognifide.slung.handler.picker.strategy.ranking.table.Ranking;
import com.google.common.base.Function;
import com.google.common.base.Optional;
import javax.servlet.ServletRequest;

public class RequestToRankingFunction implements Function<ServletRequest, Optional<Ranking>> {

  private final String attributeName;

  public RequestToRankingFunction(String attributeName) {
    this.attributeName = attributeName;
  }

  @Override
  public Optional<Ranking> apply(ServletRequest request) {
    return Optional.fromNullable(request.getAttribute(attributeName))
        .transform(ObjectToRankingFunction.INSTANCE);
  }

}
