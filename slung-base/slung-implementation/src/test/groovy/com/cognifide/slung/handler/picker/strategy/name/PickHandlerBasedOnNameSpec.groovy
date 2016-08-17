package com.cognifide.slung.handler.picker.strategy.name

import com.cognifide.slung.api.context.handler.HandlerContext
import com.cognifide.slung.api.handler.Handler
import com.cognifide.slung.handler.picker.strategy.tracker.FilteredSortedHandlerServiceTracker
import com.google.common.base.Optional
import org.apache.sling.commons.osgi.SortingServiceTracker
import org.mockito.internal.util.reflection.Whitebox
import spock.lang.Specification

class PickHandlerBasedOnNameSpec extends Specification {

  static final String HANDLER_NAME = 'name'

  static final String HANDLER_NAME_A = 'a'

  static final String HANDLER_NAME_B = 'b'

  static final String HANDLER_NAME_C = 'c'

  FilteredSortedHandlerServiceTracker<Handler> tracker = Mock()

  HandlerContext handlerContext = Mock()

  Handler handler1 = Mock()

  Handler handler2 = Mock()

  Handler handler3 = Mock()

  PickHandlerBasedOnName testedObject

  def 'absent handler should be picked when no handler was registered'() {
    given: 'handler picking strategy'
      testedObject = new PickHandlerBasedOnName()
      Whitebox.setInternalState(testedObject, 'tracker', tracker)

    when: 'handler is picked'
      final def actual = testedObject.pickUsing(handlerContext)

    then: 'strategy is used to search for best match'
      1 * tracker.findServicesFor(handlerContext) >> []
      0 * _

    and: 'handler is absent'
      Optional.<Handler>absent() == actual
  }

  def 'first (default) handler should be picked when handler with name "name" was not found'() {
    given: 'handler picking strategy'
      testedObject = new PickHandlerBasedOnName()
      Whitebox.setInternalState(testedObject, 'tracker', tracker)

    when: 'picking handler with name "name"'
      final def actual = testedObject.pickUsing(handlerContext)

    then: 'service is asked for handlers matching criteria'
      1 * tracker.findServicesFor(handlerContext) >> [handler1, handler2, handler3]
    and: 'context is asked for searched handler details'
      3 * handlerContext.handlerName >> HANDLER_NAME
    and: 'handlers are asked for name'
      1 * handler1.name >> HANDLER_NAME_A  
      1 * handler2.name >> HANDLER_NAME_B
      1 * handler3.name >> HANDLER_NAME_C
      0 * _

    and: 'selected handler is handler1'
      handler1 == actual.get()
  }

  def 'should return handler with name "name"'() {
    given: 'handler picking strategy'
      testedObject = new PickHandlerBasedOnName()
      Whitebox.setInternalState(testedObject, 'tracker', tracker)

    when: 'picking handler with name "name"'
      final def actual = testedObject.pickUsing(handlerContext)

    then: 'service is asked for handlers matching criteria'
      1 * tracker.findServicesFor(handlerContext) >> [handler1, handler2, handler3]
    and: 'context is asked for searched handler details'
      2 * handlerContext.handlerName >> HANDLER_NAME
    and: 'only first and second handler are asked for name'
      1 * handler1.name >> HANDLER_NAME_A
      1 * handler2.name >> HANDLER_NAME
      0 * _

    and: 'selected handler is handler2 with name "name"'
      handler2 == actual.get()
  }
}