package com.cognifide.slung.example;

import com.cognifide.slice.api.injector.InjectorRunner;
import com.cognifide.slice.commons.SliceModulesFactory;
import com.cognifide.slice.cq.module.CQModulesFactory;
import com.cognifide.slung.slice.mapper.SlungMapperModule;
import com.google.inject.util.Modules;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

  private static final String BUNDLE_NAME_FILTER = "slung*";

  private static final String BASE_PACKAGE = "com.cognifide.slung.example";

  private static final String INJECTOR_NAME = "slung";

  @Override
  public void start(BundleContext bundleContext) throws Exception {
    final InjectorRunner injectorRunner = new InjectorRunner(bundleContext, INJECTOR_NAME, BUNDLE_NAME_FILTER, BASE_PACKAGE);
    injectorRunner.installModules(SliceModulesFactory.createModules(bundleContext));
    injectorRunner.installModule(Modules.override(CQModulesFactory.createModules()).with(new SlungMapperModule()));
    injectorRunner.start();
  }

  @Override
  public void stop(BundleContext context) throws Exception {
  }
}
