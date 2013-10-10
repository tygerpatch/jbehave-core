package org.jbehave.core

import static java.util.Arrays.asList
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.verify

import org.jbehave.core.embedder.Embedder

import spock.lang.Specification

public class InjectableEmbedderBehavior extends Specification {

   def "should run stories as path using injected"() {
      given:
      Embedder embedder = mock(Embedder.class); // TODO: use Spock's Mock()
      StoriesAsPaths stories = new StoriesAsPaths();

      when:
      stories.useEmbedder(embedder);
      stories.run();

      then:
      verify(embedder).runStoriesAsPaths(asList("org/jbehave/core/story1", "org/jbehave/core/story2"));
   }

   private class StoriesAsPaths extends InjectableEmbedder {
      public void run() throws Throwable {
         injectedEmbedder().runStoriesAsPaths(asList("org/jbehave/core/story1", "org/jbehave/core/story2"));
      }
   }
}
