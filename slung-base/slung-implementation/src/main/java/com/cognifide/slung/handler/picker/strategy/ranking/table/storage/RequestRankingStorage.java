package com.cognifide.slung.handler.picker.strategy.ranking.table.storage;

import com.cognifide.slung.api.Constants;
import com.cognifide.slung.api.context.handler.HandlerContext;
import com.cognifide.slung.api.util.Optionals;
import com.cognifide.slung.handler.picker.strategy.ranking.table.Ranking;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import org.apache.commons.lang3.StringUtils;
import org.apache.jackrabbit.core.fs.FileSystem;

public class RequestRankingStorage implements RankingStorage {

  private static final String ATTRIBUTE_NAME_FORMAT = Constants.PROPERTY_PREFIX + "cached.ranking" + FileSystem.SEPARATOR + "%s" + FileSystem.SEPARATOR + "%s[%s]";

  private final HandlerContext handlerContext;

  private String attributeName;

  private Boolean stored;

  public RequestRankingStorage(HandlerContext handlerContext) {
    this.handlerContext = Preconditions.checkNotNull(handlerContext);
    this.attributeName = null;
    this.stored = null;
  }

  @Override
  public Ranking store(Ranking ranking) {
    Optionals.ifPresent(handlerContext.findRequest(), new ServletRequestConsumer(makeAttributeName(), ranking));
    return ranking;
  }

  private String makeAttributeName() {
    if (StringUtils.isBlank(attributeName)) {
      attributeName = String.format(ATTRIBUTE_NAME_FORMAT, handlerContext.getOriginalResource().getResourceType(), handlerContext.getPropertyName(), handlerContext.getValueType());
    }
    return attributeName;
  }

  @Override
  public boolean wasStored() {
    if (null == stored) {
      stored = handlerContext.findRequest().transform(new RequestToBooleanFunction(makeAttributeName())).or(Boolean.FALSE);
    }
    return stored;
  }

  @Override
  public Ranking retriveOrCreate(Supplier<Ranking> supplier) {
    return Optionals.flatten(handlerContext.findRequest().transform(new RequestToRankingFunction(makeAttributeName())))
        .or(supplier);
  }

}
