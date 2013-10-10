package org.jbehave.core.reporters

import static org.hamcrest.core.Is.is
import static org.jbehave.core.reporters.ANSIConsoleOutput.SGRCode.MAGENTA
import static org.jbehave.core.steps.StepCreator.PARAMETER_VALUE_END
import static org.jbehave.core.steps.StepCreator.PARAMETER_VALUE_START
import static org.junit.Assert.assertThat

import org.junit.Before
import org.junit.Test

import spock.lang.Specification
import static org.hamcrest.JavaLangMatcherAssert.that

class ANSIConsoleOutputBehaviour extends Specification {
   ANSIConsoleOutput output = new ANSIConsoleOutput()

   def "should not format an unknown event type"() {
      expect:
      that output.format("unknown", "unknown"), is("unknown")
      // TODO: Is this supposed to throw an Exception?
   }

   def "should format in green for successful output"() {
      when:
      output.overwritePattern("successful", "{0}");

      then:
      that output.format("successful", "", "success"), is("\033[32msuccess\033[0m")
      // TODO: Is this supposed to throw an Exception?
   }

   def "should format in yellow for pending outputs"() {
      when:
      output.overwritePattern("pending", "{0}");
      output.overwritePattern("pendingMethod", "{0}");

      then:
      that output.format("pending", "", "pending"), is("\033[33mpending\033[0m")
      that output.format("pendingMethod", "", "pending method"), is("\033[33mpending method\033[0m")
      // TODO: Is this supposed to throw an Exception?
   }

   def "should format in magenta for not performed outputs"() {
      when:
      output.overwritePattern("notPerformed", "{0}");

      then:
      that output.format("notPerformed", "", "not performed"), is("\033[35mnot performed\033[0m")
      // TODO: Is this supposed to throw an Exception?
   }

   def "should format in blue for ignorable output"() {
      when:
      output.overwritePattern("ignorable", "{0}");

      then:
      that output.format("ignorable", "", "ignore"), is("\033[34mignore\033[0m")
      // TODO: Is this supposed to throw an Exception?
   }

   def "should format in red for failed output"() {
      when:
      output.overwritePattern("failed", "{0}");

      then:
      that output.format("failed", "", "failure"), is("\033[31mfailure\033[0m")
      // TODO: Is this supposed to throw an Exception?
   }

   def "should boldify params that are demarcated with start end values"() {
      when:
      output.assignCodeToEvent("params", MAGENTA);
      output.overwritePattern("params", "{0} and {1}");

      then:
      that output.format("params", "", value("one"), value("two")), is("\033[35m\033[1;35mone\033[0;35m and \033[1;35mtwo\033[0;35m\033[0m")
      // TODO: Is this supposed to throw an Exception?
   }

   private String value(String str) {
      return PARAMETER_VALUE_START + str + PARAMETER_VALUE_END;
   }
}
