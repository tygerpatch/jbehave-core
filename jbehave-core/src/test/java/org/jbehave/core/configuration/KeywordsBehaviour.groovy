package org.jbehave.core.configuration

import static org.junit.Assert.assertEquals

import org.jbehave.core.configuration.Keywords.KeywordNotFound

import spock.lang.Specification
import static org.hamcrest.JavaLangMatcherAssert.that
import static org.hamcrest.core.Is.is

public class KeywordsBehaviour extends Specification {

   def "should have all keywords set by default"() {
      when:
      Keywords keywords = new Keywords();

      then:
      that keywords.narrative(), is("Narrative:")
      that keywords.scenario(), is("Scenario:")
      that keywords.givenStories(), is("GivenStories:")
      that keywords.examplesTable(), is("Examples:")
      that keywords.examplesTableRow(), is("Example:")
      that keywords.examplesTableHeaderSeparator(), is("|")
      that keywords.examplesTableValueSeparator(), is("|")
      that keywords.examplesTableIgnorableSeparator(), is("|--")
      that keywords.given(), is("Given")
      that keywords.when(), is("When")
      that keywords.then(), is("Then")
      that keywords.and(), is("And")
      that keywords.ignorable(), is("!--")
      that keywords.pending(), is("PENDING")
      that keywords.notPerformed(), is("NOT PERFORMED")
      that keywords.failed(), is("FAILED")
      that keywords.dryRun(), is("DRY RUN")
   }

   def "should fail if some keyword is missing in map constructor"() {
      when:
      new Keywords(new HashMap<String, String>())

      then:
      thrown(KeywordNotFound)
   }
}
