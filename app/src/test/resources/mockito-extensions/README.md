## Mock the unmockable: opt-in mocking of final classes/methods

[Official details](https://github.com/mockito/mockito/wiki/What%27s-new-in-Mockito-2#mock-the-unmockable-opt-in-mocking-of-final-classesmethods)

###Brief:
For a long time our users suffered a disbelief when Mockito refused to mock a final class. Mocking of final methods was even more problematic, causing surprising behavior of the framework and generating angry troubleshooting. The lack of mocking finals was a chief limitation of Mockito since its inception in 2007. The root cause was the lack of sufficient support in mock creation / bytecode generation. Until [Rafael Winterhalter](https://twitter.com/rafaelcodes) decided to fix the problem and provide opt-in implementation in Mockito 2.1.0. In the releases, Mockito team will make mocking the unmockable completely seamless, greatly improving developer experience.

Mocking of final classes and methods is an **incubating**, opt-in feature. It uses a combination of Java agent instrumentation and subclassing in order to enable mockability of these types. As this works differently to our current mechanism and this one has different limitations and as we want to gather experience and user feedback, this feature had to be explicitly activated to be available ; it can be done via the mockito extension mechanism by creating the file `src/test/resources/mockito-extensions/org.mockito.plugins.MockMaker` containing a single line:

  ```
  mock-maker-inline
  ```

  After you created this file, Mockito will automatically use this new engine and one can do :

  ```java
  final class FinalClass {
    final String finalMethod() { return "something"; }
  }

  FinalClass concrete = new FinalClass();

  FinalClass mock = mock(FinalClass.class);
  given(mock.finalMethod()).willReturn("not anymore");

  assertThat(mock.finalMethod()).isNotEqualTo(concrete.finalMethod());
  ```

In subsequent milestones, the team will bring a programmatic way of using this feature. We will identify and provide support for all unmockable scenarios. Stay tuned and please let us know what you think of this feature!

