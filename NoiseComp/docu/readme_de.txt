NoiseComp
=========
NoiseComp ist eine Java Anwendung zur Echzeit Sound Synthese.
Mit einem grafischen Editor können die Ein- und Ausgänge von Sound-Generatoren 
auf einer Zeitleiste beliebig verschaltet werden.

Berechnungen (Sound Synthese):
------------------------------
Einfache Signalquellen (Sinus, Rampen, Mixer, ...) mit mehreren Eingängen
und einem Ausgang werden auf einer Zeitleiste anzuordnen. Die Ein und Ausgänge
beliebig verschalten zu können. Der endgültige Output Signal wird mit Java-Sound 
in Echtzeit berechnet und ausgegeben.
Der Teil tut soweit (ich bin beeindruckt, was man mit Java mittlerweile so
an Echtzeit-Berechnungen so hinbekommt !).

Grafische Editor:
-----------------
Die Anwendung hat einem grafischen Editor auf dem die Signalquellen als
Spuren mit ihrem Ausgangssignalen dargestellt werden.
Die Verbindungen der Inputs und Outputs werden grafisch angezeigt.
Der Teil wird als Full-Screen Anwendung mit selbstgezeichneten Controlls dargestellt 
(das alles nur weil es Spass macht und ich die Hoffnung hatte so die Geschwindigkeit 
in den Griff zu bekommen ohne von Swing oder so anhängig zu sein).

Sound-Module:
-------------
Die Generatoren/Spuren sollen zu Modulen zusammengefasst werden können, 
die nach aussen wie ein neuer Generator arbeiten, über die Eingänge parametrisiert 
werden können und wiederum mehrfach auf der Zeitleiste eingesetzt werden können. 
Damit können "Instrumente" erstellt werden.
