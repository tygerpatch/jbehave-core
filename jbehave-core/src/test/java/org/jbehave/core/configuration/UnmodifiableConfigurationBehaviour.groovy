package org.jbehave.core.configuration

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import static org.hamcrest.JavaLangMatcherAssert.that
import static org.hamcrest.core.Is.is
import static org.jbehave.core.reporters.ANSIConsoleOutput.SGRCode.MAGENTA
import static org.jbehave.core.steps.StepCreator.PARAMETER_VALUE_END
import static org.jbehave.core.steps.StepCreator.PARAMETER_VALUE_START
import static org.junit.Assert.assertThat

import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

import org.hamcrest.Matchers
import org.jbehave.core.embedder.StoryControls
import org.jbehave.core.failures.FailureStrategy
import org.jbehave.core.failures.PendingStepStrategy
import org.jbehave.core.io.StoryLoader
import org.jbehave.core.io.StoryPathResolver
import org.jbehave.core.parsers.StepPatternParser
import org.jbehave.core.parsers.StoryParser
import org.jbehave.core.reporters.StoryReporter
import org.jbehave.core.reporters.StoryReporterBuilder
import org.jbehave.core.reporters.ViewGenerator
import org.jbehave.core.steps.ParameterControls
import org.jbehave.core.steps.ParameterConverters
import org.jbehave.core.steps.StepCollector
import org.jbehave.core.steps.StepMonitor

import spock.lang.Specification

import com.thoughtworks.paranamer.Paranamer


class UnmodifiableConfigurationBehaviour extends Specification {

   def "should provide delegate configuration elements"() {
      when:
      Configuration delegate = new MostUsefulConfiguration();
      Configuration unmodifiable = new UnmodifiableConfiguration(delegate);

      then:
      that unmodifiable.dryRun(), is(delegate.dryRun())
      that unmodifiable.keywords(), is(delegate.keywords())
      that unmodifiable.storyControls(), is(delegate.storyControls())
      that unmodifiable.storyLoader(), is(delegate.storyLoader())
      that unmodifiable.storyParser(), is(delegate.storyParser())
      that unmodifiable.storyPathResolver(), is(delegate.storyPathResolver())
      that unmodifiable.defaultStoryReporter(), is(delegate.defaultStoryReporter())
      that unmodifiable.storyReporter("path"), is(Matchers.<Object>notNullValue(Object.class))
      that unmodifiable.storyReporterBuilder(), is(delegate.storyReporterBuilder())
      that unmodifiable.failureStrategy(), is(delegate.failureStrategy())
      that unmodifiable.pendingStepStrategy(), is(delegate.pendingStepStrategy())
      that unmodifiable.paranamer(), is(delegate.paranamer())
      that unmodifiable.parameterConverters(), is(delegate.parameterConverters())
      that unmodifiable.parameterControls(), is(delegate.parameterControls())
      that unmodifiable.stepCollector(), is(delegate.stepCollector())
      that unmodifiable.stepMonitor(), is(delegate.stepMonitor())
      that unmodifiable.stepPatternParser(), is(delegate.stepPatternParser())
      that unmodifiable.viewGenerator(), is(delegate.viewGenerator())
   }

   def "should not allow modification of configuration elemeents"() {
      given:
      Method method;
      Configuration delegate = new MostUsefulConfiguration();
      Configuration unmodifiable = new UnmodifiableConfiguration(delegate);

      // TODO: Should this throw NoSuchMethodException, IllegalAccessException?

      when:
      method = unmodifiable.getClass().getMethod("useKeywords", Keywords)
      method.invoke(unmodifiable, nullArgsFor(Keywords))

      then:
      thrown(InvocationTargetException)

      when:
      method = unmodifiable.getClass().getMethod("doDryRun", Boolean)
      method.invoke(unmodifiable, nullArgsFor(Boolean))

      then:
      thrown(InvocationTargetException)

      when:
      method = unmodifiable.getClass().getMethod("useStoryControls", StoryControls)
      method.invoke(unmodifiable, nullArgsFor(StoryControls))

      then:
      thrown(InvocationTargetException)

      when:
      method = unmodifiable.getClass().getMethod("useStoryLoader", StoryLoader)
      method.invoke(unmodifiable, nullArgsFor(StoryLoader))

      then:
      thrown(InvocationTargetException)

      when:
      method = unmodifiable.getClass().getMethod("useStoryParser", StoryParser)
      method.invoke(unmodifiable, nullArgsFor(StoryParser))

      then:
      thrown(InvocationTargetException)

      when:
      method = unmodifiable.getClass().getMethod("useDefaultStoryReporter", StoryReporter)
      method.invoke(unmodifiable, nullArgsFor(StoryReporter))

      then:
      thrown(InvocationTargetException)

      when:
      method = unmodifiable.getClass().getMethod("useStoryReporterBuilder", StoryReporterBuilder)
      method.invoke(unmodifiable, nullArgsFor(StoryReporterBuilder))

      then:
      thrown(InvocationTargetException)

      when:
      method = unmodifiable.getClass().getMethod("useStoryPathResolver", StoryPathResolver)
      method.invoke(unmodifiable, nullArgsFor(StoryPathResolver))

      then:
      thrown(InvocationTargetException)

      when:
      method = unmodifiable.getClass().getMethod("useFailureStrategy", FailureStrategy)
      method.invoke(unmodifiable, nullArgsFor(FailureStrategy))

      then:
      thrown(InvocationTargetException)

      when:
      method = unmodifiable.getClass().getMethod("usePendingStepStrategy", PendingStepStrategy)
      method.invoke(unmodifiable, nullArgsFor(PendingStepStrategy))

      then:
      thrown(InvocationTargetException)

      when:
      method = unmodifiable.getClass().getMethod("useParanamer", Paranamer)
      method.invoke(unmodifiable, nullArgsFor(Paranamer))

      then:
      thrown(InvocationTargetException)

      when:
      method = unmodifiable.getClass().getMethod("useParameterConverters", ParameterConverters)
      method.invoke(unmodifiable, nullArgsFor(ParameterConverters))

      then:
      thrown(InvocationTargetException)

      when:
      method = unmodifiable.getClass().getMethod("useParameterControls", ParameterControls)
      method.invoke(unmodifiable, nullArgsFor(ParameterControls))

      then:
      thrown(InvocationTargetException)

      when:
      method = unmodifiable.getClass().getMethod("useStepCollector", StepCollector)
      method.invoke(unmodifiable, nullArgsFor(StepCollector))

      then:
      thrown(InvocationTargetException)

      when:
      method = unmodifiable.getClass().getMethod("useStepMonitor", StepMonitor)
      method.invoke(unmodifiable, nullArgsFor(StepMonitor))

      then:
      thrown(InvocationTargetException)

      when:
      method = unmodifiable.getClass().getMethod("useStepPatternParser", StepPatternParser)
      method.invoke(unmodifiable, nullArgsFor(StepPatternParser))

      then:
      thrown(InvocationTargetException)

      when:
      method = unmodifiable.getClass().getMethod("useViewGenerator", ViewGenerator)
      method.invoke(unmodifiable, nullArgsFor(ViewGenerator))

      then:
      thrown(InvocationTargetException)

      when:
      method = unmodifiable.getClass().getMethod("useStoryPathResolver", StoryPathResolver)
      method.invoke(unmodifiable, nullArgsFor(StoryPathResolver))

      then:
      thrown(InvocationTargetException)
   }

   private Object[] nullArgsFor(Class<?>[] types) {
      Object[] args = new Object[types.length];
      for (int i = 0; i < types.length; i++) {
         args[i] = null;
      }
      return args;
   }

   def "should report delegate in toString"() {
      when:
      Configuration unmodifiable = new UnmodifiableConfiguration(new MostUsefulConfiguration());

      then:
      that unmodifiable.toString(), Matchers.containsString(MostUsefulConfiguration.class.getName())
   }
}
