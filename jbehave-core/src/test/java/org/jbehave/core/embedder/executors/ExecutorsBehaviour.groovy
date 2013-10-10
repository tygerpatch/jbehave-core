package org.jbehave.core.embedder.executors

import static org.hamcrest.JavaLangMatcherAssert.that
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.instanceOf

import java.util.concurrent.ExecutorService

import org.jbehave.core.embedder.EmbedderControls

import spock.lang.Specification

public class ExecutorsBehaviour extends Specification {

   def "should create executors"() {
      given:
      FixedThreadExecutors fixedThreadExecutors = new FixedThreadExecutors()
      SameThreadExecutors sameThreadExecutors = new SameThreadExecutors()

      when:
      ExecutorService fixedThreadExecutorService = fixedThreadExecutors.create(new EmbedderControls())
      ExecutorService sameThreadExecutorService = sameThreadExecutors.create(new EmbedderControls())

      then:
      that fixedThreadExecutorService, instanceOf(ExecutorService)
      that sameThreadExecutorService, instanceOf(ExecutorService)
   }
}
