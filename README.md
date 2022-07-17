# perlito5-cached-jar
cached(local repository hosted) version of perlito5 built on linux mint, see https://github.com/fglock/Perlito

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

Compiled on linux mint, but works on Windows as well.

