package com.cognifide.slung.example.handler;

import com.cognifide.slung.api.Constants;
import com.cognifide.slung.api.context.handler.HandlerContext;
import com.cognifide.slung.api.handler.Handler;
import com.cognifide.slung.api.handler.NameAware;
import com.cognifide.slung.api.handler.ValueAware;
import com.cognifide.slung.api.strategy.RankStrategies;
import com.cognifide.slung.api.strategy.RankStrategy;
import com.google.common.base.Predicate;
import java.lang.reflect.Type;
import java.util.Arrays;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;

@Service({Handler.class, NameAware.class, ValueAware.class})
@Component(
    label = Constants.LABEL_PREFIX + "Example - Pin handler",
    description = "Example pin handler demonstraiting auto handler discovery. Use \"pin\" as handler name in annotations.",
    metatype = true)
@Properties(
    @Property(name = org.osgi.framework.Constants.SERVICE_RANKING, propertyPrivate = false,
        intValue = 0))
public class PinHandler implements Handler, NameAware, ValueAware {

  private static final String PIN = "pin";

  private static final Integer MAX = 10000;

  @Override
  public boolean isActive() {
    return true;
  }

  @Override
  public Iterable<Type> getAcceptedTypes() {
    return Arrays.<Type>asList(Integer.class, Integer.TYPE);
  }

  @Override
  public String getName() {
    return PIN;
  }

  @Override
  public RankStrategy getNameStrategy() {
    return RankStrategies.equals(Arrays.<Object>asList(PIN));
  }

  @Override
  public RankStrategy getValueStrategy() {
    return RankStrategies.unsignificant(new Predicate<Object>() {
      @Override
      public boolean apply(Object o) {
        boolean result = false;
        if (null != o && Integer.class.isAssignableFrom(o.getClass())) {
          result = MAX.compareTo((Integer) o) > 0;
        }
        return result;
      }
    });
  }

  @Override
  public Object handle(HandlerContext handlerContext) {
    return 0;
  }

}
