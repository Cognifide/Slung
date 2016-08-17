package com.cognifide.slung.api.strategy;

import com.cognifide.slung.api.strategy.predicate.ContainsPredicate;
import com.cognifide.slung.api.strategy.predicate.EqualsPredicate;
import com.google.common.base.Enums;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.Collections;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;

public enum RankStrategies {

  EQUALS {
    @Override
    public RankStrategy get(Iterable<Object> objects) {
      return RankStrategies.equals(objects);
    }
  },
  CONTAINS {
    @Override
    public RankStrategy get(Iterable<Object> objects) {
      return RankStrategies.contains(objects);
    }
  },
  ANY {
    @Override
    public RankStrategy get(Iterable<Object> objects) {
      return RankStrategies.any();
    }
  },
  SKIP {
    @Override
    public RankStrategy get(Iterable<Object> objects) {
      return RankStrategies.skip();
    }
  };

  @Override
  public String toString() {
    return StringUtils.capitalize(name().toLowerCase());
  }

  public static final String EQUALS_NAME = "Equals";

  public static final String CONTAINS_NAME = "Contains";

  public static final String ANY_NAME = "Any";

  public static final String SKIP_NAME = "Skip";

  public abstract RankStrategy get(Iterable<Object> objects);

  public static RankStrategies from(String s) {
    final String name = StringUtils.defaultString(s).toUpperCase(Locale.ENGLISH);
    return Enums.getIfPresent(RankStrategies.class, name)
        .or(RankStrategies.SKIP);
  }

  public static RankStrategy significant(final Predicate<Object> predicate, final Iterable<Object> objects) {
    return new ImmutableRankStrategy(RankInformation.SIGNIFICANT, predicate, objects);
  }

  public static RankStrategy significant(final Predicate<Object> predicate) {
    return new ImmutableRankStrategy(RankInformation.SIGNIFICANT, predicate, Collections.emptyList());
  }

  public static RankStrategy unsignificant(final Predicate<Object> predicate, final Iterable<Object> objects) {
    return new ImmutableRankStrategy(RankInformation.UNSIGNIFICANT, predicate, objects);
  }

  public static RankStrategy unsignificant(final Predicate<Object> predicate) {
    return new ImmutableRankStrategy(RankInformation.UNSIGNIFICANT, predicate, Collections.emptyList());
  }

  public static RankStrategy equals(final Iterable<Object> objects) {
    return significant(new EqualsPredicate(objects), objects);
  }

  public static RankStrategy contains(final Iterable<Object> objects) {
    return unsignificant(new ContainsPredicate(objects), objects);
  }

  public static RankStrategy any() {
    return new ImmutableRankStrategy(RankInformation.ANY, Predicates.alwaysTrue());
  }

  public static RankStrategy skip() {
    return new ImmutableRankStrategy(RankInformation.SKIP, Predicates.alwaysTrue());
  }
}
