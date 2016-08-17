package com.cognifide.slung.handler.picker.strategy.ranking.table.storage;

import com.cognifide.slung.api.util.Consumer;
import com.cognifide.slung.handler.picker.strategy.ranking.table.Ranking;
import javax.servlet.ServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServletRequestConsumer implements Consumer<ServletRequest> {

  private static final Logger LOGGER = LoggerFactory.getLogger(ServletRequestConsumer.class);

  private final String attributeName;

  private final Ranking ranking;

  public ServletRequestConsumer(String attributeName, Ranking ranking) {
    this.attributeName = attributeName;
    this.ranking = ranking;
  }

  @Override
  public void consume(ServletRequest request) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("Storing ranking into request under attribute name '{}'", attributeName);
    }
    request.setAttribute(attributeName, ranking);
  }

}
