package org.jbehave.core.configuration

import static org.hamcrest.JavaLangMatcherAssert.that
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.instanceOf
import spock.lang.Specification

import com.thoughtworks.paranamer.CachingParanamer
import com.thoughtworks.paranamer.Paranamer

public class ParanamerConfigurationBehaviour extends Specification {

   def "should use CachingParanamer"() {
      given:
      ParanamerConfiguration paranamerConfiguration = new ParanamerConfiguration()

      when:
      Paranamer paranamer = paranamerConfiguration.paranamer()

      then:
      that paranamer, instanceOf(CachingParanamer)
   }
}
