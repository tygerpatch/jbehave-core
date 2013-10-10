package org.jbehave.core.failures

import spock.lang.Specification

public class FailureStrategyBehaviour extends Specification {

   def "should allow failures to be absorbed"() {
      given:
      SilentlyAbsorbingFailure silentlyAbsorbingFailure = new SilentlyAbsorbingFailure()
      silentlyAbsorbingFailure.handleFailure(new IllegalStateException())
      // TODO: Is this supposed to throw a Throwable?
   }

   def "should allow failures to be rethrown"() {
      given:
      RethrowingFailure rethrowingFialure = new RethrowingFailure()

      when:
      rethrowingFialure.handleFailure(new IllegalStateException())

      then:
      thrown(IllegalStateException)
   }

   def "should allow failures to be rethrown when wrapped as UUIDExceptions"() {
      given:
      RethrowingFailure rethrowingFialure = new RethrowingFailure()

      when:
      rethrowingFialure.handleFailure(new UUIDExceptionWrapper(new IllegalStateException()))

      then:
      thrown(IllegalStateException)
   }
}
