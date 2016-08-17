package com.cognifide.slung.component.filter.configuration

import spock.lang.Specification
import spock.lang.Unroll

class DiffComponentFilterConfigurationSpec extends Specification {

  static final String RESOURCE_TYPE = 'resource/type'
  
  static final String OTHER_RESOURCE_TYPE = 'other/resource'

  static final Map NO_CONFIGURATION = [
    (DiffComponentFilterConfiguration.ENABLE_DIFF_COMPONENT_FILTER_PROPERTY_NAME): false]

  static final Map EMPTY_CONFIGURATION = [
    (DiffComponentFilterConfiguration.ENABLE_DIFF_COMPONENT_FILTER_PROPERTY_NAME): false,
    (DiffComponentFilterConfiguration.RESOURCE_TYPE_PROPERTY) : []]

  static final Map CONFIGURATION_WITH_DIFFERENT_RESOURCE_TYPE = [
    (DiffComponentFilterConfiguration.ENABLE_DIFF_COMPONENT_FILTER_PROPERTY_NAME): true,
    (DiffComponentFilterConfiguration.RESOURCE_TYPE_PROPERTY) : [OTHER_RESOURCE_TYPE] as String[]]

  static final Map PROPER_CONFIGURATION = [
    (DiffComponentFilterConfiguration.ENABLE_DIFF_COMPONENT_FILTER_PROPERTY_NAME): true,
    (DiffComponentFilterConfiguration.RESOURCE_TYPE_PROPERTY) : [RESOURCE_TYPE] as String[]]

  static final Map PROPER_ENHANCED_CONFIGURATION = [
    (DiffComponentFilterConfiguration.ENABLE_DIFF_COMPONENT_FILTER_PROPERTY_NAME): true,
    (DiffComponentFilterConfiguration.RESOURCE_TYPE_PROPERTY) : [OTHER_RESOURCE_TYPE, RESOURCE_TYPE] as String[]]

  DiffComponentFilterConfiguration testedObject = new DiffComponentFilterConfiguration()

  def 'configuration should be disabled by default'() {
    given: 'activated and configured service'
      testedObject.activate(NO_CONFIGURATION)

    when: 'service is checked if is enabled'
      final def actual = testedObject.enabled

    then: 'service is disabled by default'
      !actual
  }

  def 'configuration should be enabled when truned on'() {
    given: 'activated and configured service'
      testedObject.activate(PROPER_CONFIGURATION)

    when: 'service is checked if is enabled'
      final def actual = testedObject.enabled

    then: 'service is enabled'
      actual
  }

  @Unroll
  def 'configuration should allow filtering resource type when it is on the list'() {
    given: 'activated and configured service'
      testedObject.activate(configuration)

    when: 'service is asked if it can filter given resource with type'
      final def actual = testedObject.canFilter(RESOURCE_TYPE)

    then: 'resource can be filter if it type is on the configuration list'
      expected == actual

    where:
      configuration                              || expected
      NO_CONFIGURATION                           || false
      EMPTY_CONFIGURATION                        || false
      CONFIGURATION_WITH_DIFFERENT_RESOURCE_TYPE || false
      PROPER_CONFIGURATION                       || true
      PROPER_ENHANCED_CONFIGURATION              || true
  }

  @Unroll
  def 'configuration should allow filtering resource type when it is on the list after list modification'() {
    given: 'activated and configured service'
      testedObject.activate(configuration)

    and: 'configuration is modified'
      testedObject.modified(modifiedConfiguration)

    when: 'service is asked if it can filter given resource with type'
      final def actual = testedObject.canFilter(RESOURCE_TYPE)

    then: 'resource can be filter if it type is on the configuration list'
      actual

    where:
      configuration << [NO_CONFIGURATION, EMPTY_CONFIGURATION]
      modifiedConfiguration << [PROPER_CONFIGURATION, PROPER_CONFIGURATION]
  }
}

