Howto start the application:
============================
The easy way to run NoiseComp under Windows is 
to use the "run.bat" in the Homedir of the Application.

The full name of the Main Class is:
* de.schmiereck.noiseComp.Main

The required Java classes are in the directory "/bin/".
Include this directory in the Classpath:
* /bin/

The required libraries are in the directory "/lib/".
Include the following JARs in the Classpath:
* smkScreenTools.jar
* http://sourceforge.net/projects/jmdiframework/ with extensions by me

Up to this, the startup command line ist:
java -cp "%CLASSPATH%;./bin/;./lib/smkScreenTools.jar" de.schmiereck.noiseComp.Main
