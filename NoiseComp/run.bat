@echo off
echo Executing application...

SET JAVA_HOME=C:\Programme\Java\j2sdk1.5.0\

java -cp "%CLASSPATH%;./bin/;./lib/smkScreenTools.jar" de.schmiereck.noiseComp.Main
echo Finished.
REM PAUSE