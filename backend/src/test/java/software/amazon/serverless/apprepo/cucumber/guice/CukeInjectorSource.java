package software.amazon.serverless.apprepo.cucumber.guice;

import com.google.common.collect.Lists;
import com.google.inject.Guice;
import com.google.inject.Injector;

import cucumber.runtime.java.guice.InjectorSource;

/**
 * Guice injector source for Cucumber.
 */
public class CukeInjectorSource implements InjectorSource {
  @Override
  public Injector getInjector() {
    return Guice.createInjector(Lists.newArrayList(new ApplicationModule()));
  }
}
