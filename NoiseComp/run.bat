@echo off
echo Executing application...

java -cp "%CLASSPATH%;./bin/;./lib/smkScreenTools.jar" de.schmiereck.noiseComp.Main
echo Finished.
REM PAUSE