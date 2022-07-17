# perlito5-cached-jar
cached(local repository hosted) version of perlito5 built on linux mint, see https://github.com/fglock/Perlito

Perlito is a an implementation of "Perl5" javax.scripting.ScriptEngine for javax.scripting.ScriptEngineManager. It can be used for making a script console in your Java program and shares a model with it, allowing you to alter the java model from the scripting context. It is not completely obvious how to share the model with it, but based on my tests, if you are clever, you can do it(worst case scenario, by sending the shared object to a subroutine as an argument via `apply`, and have that subroutine hook it up to the global state for you).

javadoc can be found here unless it gets moved: https://codedmitry.github.io/perlito5-cached-jar/

**Unordered Thoughts**:

I made this cache for two reasons: 
1. perlito javadoc is hard to find online if it exists 
2.  i could not build it on mingw, but after building it on mint, it seemed to work fine on windows, so I wanted to publish it so I don't have to repeat building it for a while.

`Main.java` does a collection of tests poking at the engine from various angles.

`Gift.java` contains a trivial java object used in Main.java to pass to a subroutine, in which it gets used.

`perlito5.jar` seems to be the only jar necessary to enable ScriptEngineManager to find the "Perl5" ScriptEngine.

`perlito5-lib` was created alongside `perlito5.jar` during the build process, but I cannot for the life of me figure out how it differs from `perlito5.jar` based on inspection using [jd-gui](https://github.com/java-decompiler/jd-gui/releases/tag/v1.6.6), it does not get used in this project's tests.

testing was done via `bash build && bash run`.

Compiled on Linux Mint, but works on Windows as well.

**Known Issues**:
1. Perl has no null, so everything works until suddenly you want to do `$jframe->setLocationRelativeTo(null)` but null does not exist, undef is a PlLvalue, not null, there are no nulls, and all my attempts to smuggle "null" over for such cases like this or `$jframe->getContentPane()->setLayout(null)` have failed, so it seems necessary to provide alternative null-less apis or workarounds if you want to deal with functions that accept null.
