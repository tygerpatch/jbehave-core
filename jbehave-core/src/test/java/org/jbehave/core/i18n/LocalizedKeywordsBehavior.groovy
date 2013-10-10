package org.jbehave.core.i18n

import static org.hamcrest.JavaLangMatcherAssert.that
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.not
import static org.jbehave.core.configuration.Keywords.AND
import static org.jbehave.core.configuration.Keywords.AS_A
import static org.jbehave.core.configuration.Keywords.EXAMPLES_TABLE
import static org.jbehave.core.configuration.Keywords.EXAMPLES_TABLE_HEADER_SEPARATOR
import static org.jbehave.core.configuration.Keywords.EXAMPLES_TABLE_IGNORABLE_SEPARATOR
import static org.jbehave.core.configuration.Keywords.EXAMPLES_TABLE_ROW
import static org.jbehave.core.configuration.Keywords.EXAMPLES_TABLE_VALUE_SEPARATOR
import static org.jbehave.core.configuration.Keywords.FAILED
import static org.jbehave.core.configuration.Keywords.GIVEN
import static org.jbehave.core.configuration.Keywords.GIVEN_STORIES
import static org.jbehave.core.configuration.Keywords.IGNORABLE
import static org.jbehave.core.configuration.Keywords.IN_ORDER_TO
import static org.jbehave.core.configuration.Keywords.I_WANT_TO
import static org.jbehave.core.configuration.Keywords.META
import static org.jbehave.core.configuration.Keywords.META_PROPERTY
import static org.jbehave.core.configuration.Keywords.NARRATIVE
import static org.jbehave.core.configuration.Keywords.NOT_PERFORMED
import static org.jbehave.core.configuration.Keywords.PENDING
import static org.jbehave.core.configuration.Keywords.SCENARIO
import static org.jbehave.core.configuration.Keywords.THEN
import static org.jbehave.core.configuration.Keywords.WHEN

import org.jbehave.core.configuration.Configuration
import org.jbehave.core.configuration.Keywords
import org.jbehave.core.configuration.MostUsefulConfiguration
import org.jbehave.core.i18n.LocalizedKeywords.LocalizedKeywordNotFound
import org.jbehave.core.i18n.LocalizedKeywords.ResourceBundleNotFound
import org.jbehave.core.steps.StepType

import spock.lang.Specification

public class LocalizedKeywordsBehavior extends Specification {

   def "should allow keywords in English as default"() {
      given:
      ensureKeywordsAreLocalisedFor(null);
      // TODO: Is this supposed to throw a IOException?
   }

   def "should allow keywords in different locales"() {
      given:
      ensureKeywordsAreLocalisedFor(new Locale("de"));
      ensureKeywordsAreLocalisedFor(new Locale("en"));
      ensureKeywordsAreLocalisedFor(new Locale("fr"));
      ensureKeywordsAreLocalisedFor(new Locale("fi"));
      ensureKeywordsAreLocalisedFor(new Locale("it"));
      ensureKeywordsAreLocalisedFor(new Locale("no"));
      ensureKeywordsAreLocalisedFor(new Locale("pt"));
      ensureKeywordsAreLocalisedFor(new Locale("sv"));
      ensureKeywordsAreLocalisedFor(new Locale("tr"));
      ensureKeywordsAreLocalisedFor(new Locale("zh_TW"));
      // TODO: Is this supposed to throw a IOException?
   }

   def "should show keywords in toString representation"() {
      when:
      LocalizedKeywords it = keywordsFor(new Locale("it"));
      LocalizedKeywords pt = keywordsFor(new Locale("pt"));

      then:
      that it.toString(), not(equalTo(pt.toString()))
   }

   def "should fail if resource bundle is not found"() {
      when:
      ensureKeywordsAreLocalisedFor(new Locale("en"), "unknown");

      then:
      thrown(ResourceBundleNotFound)
   }

   def "should fail if keyword is not found"() {
      when:
      ensureKeywordsAreLocalisedFor(new Locale("mk"), null);

      then:
      thrown(LocalizedKeywordNotFound)
   }

   def "should allow keywords to be configured"() {
      given:
      Configuration configuration = new MostUsefulConfiguration();
      ensureKeywordsAreLocalised(configuration, new Locale("en"));
      configuration.useKeywords(new LocalizedKeywords(new Locale("it")));
      ensureKeywordsAreLocalised(configuration, new Locale("it"));
   }

   private void ensureKeywordsAreLocalised(Configuration configuration, Locale locale) {
      Keywords keywords = keywordsFor(locale, null, null);
      Map<StepType, String> startingWordsByType = keywords.startingWordsByType();
      assertThat(startingWordsByType.get(StepType.GIVEN), equalTo(keywords.given()));
      assertThat(startingWordsByType.get(StepType.WHEN), equalTo(keywords.when()));
      assertThat(startingWordsByType.get(StepType.THEN), equalTo(keywords.then()));
      assertThat(startingWordsByType.get(StepType.AND), equalTo(keywords.and()));
      assertThat(startingWordsByType.get(StepType.IGNORABLE), equalTo(keywords.ignorable()));
   }

   private void ensureKeywordsAreLocalisedFor(Locale locale) throws IOException {
      ensureKeywordsAreLocalisedFor(locale, null);
   }

   private void ensureKeywordsAreLocalisedFor(Locale locale, String bundleName) throws IOException {
      Keywords keywords = keywordsFor(locale, bundleName, null);
      Properties properties = bundleFor(locale);
      ensureKeywordIs(properties, META, keywords.meta());
      ensureKeywordIs(properties, META_PROPERTY, keywords.metaProperty());
      ensureKeywordIs(properties, NARRATIVE, keywords.narrative());
      ensureKeywordIs(properties, IN_ORDER_TO, keywords.inOrderTo());
      ensureKeywordIs(properties, AS_A, keywords.asA());
      ensureKeywordIs(properties, I_WANT_TO, keywords.iWantTo());
      ensureKeywordIs(properties, SCENARIO, keywords.scenario());
      ensureKeywordIs(properties, GIVEN_STORIES, keywords.givenStories());
      ensureKeywordIs(properties, EXAMPLES_TABLE, keywords.examplesTable());
      ensureKeywordIs(properties, EXAMPLES_TABLE_ROW, keywords.examplesTableRow());
      ensureKeywordIs(properties, EXAMPLES_TABLE_HEADER_SEPARATOR, keywords.examplesTableHeaderSeparator());
      ensureKeywordIs(properties, EXAMPLES_TABLE_VALUE_SEPARATOR, keywords.examplesTableValueSeparator());
      ensureKeywordIs(properties, EXAMPLES_TABLE_IGNORABLE_SEPARATOR, keywords.examplesTableIgnorableSeparator());
      ensureKeywordIs(properties, GIVEN, keywords.given());
      ensureKeywordIs(properties, WHEN, keywords.when());
      ensureKeywordIs(properties, THEN, keywords.then());
      ensureKeywordIs(properties, AND, keywords.and());
      ensureKeywordIs(properties, IGNORABLE, keywords.ignorable());
      ensureKeywordIs(properties, PENDING, keywords.pending());
      ensureKeywordIs(properties, NOT_PERFORMED, keywords.notPerformed());
      ensureKeywordIs(properties, FAILED, keywords.failed());
   }

   private LocalizedKeywords keywordsFor(Locale locale) {
      return keywordsFor(locale, null, null);
   }

   private LocalizedKeywords keywordsFor(Locale locale, String bundleName, ClassLoader classLoader) {
      LocalizedKeywords keywords;
      if (bundleName == null) {
         keywords = (locale == null ? new LocalizedKeywords() : new LocalizedKeywords(locale));
      } else {
         keywords = new LocalizedKeywords(locale, bundleName, classLoader);
      }
      if ( locale != null ){
         assertThat(keywords.getLocale(), equalTo(locale));
      }
      return keywords;
   }

   private Properties bundleFor(Locale locale) throws IOException {
      Properties expected = new Properties();
      String bundle = "i18n/keywords_" + (locale == null ? "en" : locale.getLanguage()) + ".properties";
      InputStream stream = this.getClass().getClassLoader().getResourceAsStream(bundle);
      if (stream != null) {
         expected.load(stream);
      }
      return expected;
   }

   private void ensureKeywordIs(Properties properties, String key, String value) {
      assertThat(properties.getProperty(key, value), equalTo(value));
   }
}
