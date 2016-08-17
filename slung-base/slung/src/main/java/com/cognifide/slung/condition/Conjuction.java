package com.cognifide.slung.condition;

import com.cognifide.slung.condition.aggregate.AndCondition;
import com.cognifide.slung.condition.aggregate.OrCondition;
import javax.annotation.Nonnull;

/**
 * Conjuctions between conditions.
 */
public enum Conjuction {
  /**
   * Produces "all" conjuction - condition. Will be true when all conditions are true.
   */
  AND {
    @Override
    public Condition combine(Iterable<Condition> conditions) {
      return new AndCondition(conditions);
    }
  },
  /**
   * Produces "or" conjuction - condition. Will be true when at least one condition is true.
   */
  OR {
    @Override
    public Condition combine(Iterable<Condition> conditions) {
      return new OrCondition(conditions);
    }
  };

  /**
   * Joins conditions.
   *
   * @param conditions non null conditions to combine
   * @return condition which is result of conditions join
   */
  public abstract Condition combine(@Nonnull Iterable<Condition> conditions);
}
