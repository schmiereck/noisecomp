@echo off
echo Executing application...

SET JAVA_HOME=C:\Program Files\Java\jdk1.6.0_16\

java -cp "%CLASSPATH%;./bin/;./lib/smkScreenTools.jar" de.schmiereck.noiseComp.smkScreen.ViewMain
echo Finished.
REM PAUSE