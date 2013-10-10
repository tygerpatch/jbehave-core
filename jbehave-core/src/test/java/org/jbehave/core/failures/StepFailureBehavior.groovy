package org.jbehave.core.failures

import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.sameInstance
import static org.junit.Assert.assertThat
import spock.lang.Specification
import static org.hamcrest.JavaLangMatcherAssert.that

public class StepFailureBehavior extends Specification {

   def "should append step name to failure"() {
      given:
      Throwable cause = new IllegalArgumentException("Can't we all just get along?");
      String stepAsString = "Given something that could never work";
      StepFailed failure = new StepFailed(stepAsString, cause);

      when:
      String message = failure.getMessage();

      then:
      that message, equalTo("'" + stepAsString + "': "+cause.getMessage())
   }

   def "should keep original exception as cause"() {
      given:
      Throwable originalCause = new IllegalArgumentException("Can't we all just get along?")
      StepFailed decorator = new StepFailed("Given something that could never work", originalCause)

      when:
      Throwable cause = decorator.getCause()

      then:
      that cause, sameInstance(originalCause)
   }
}
