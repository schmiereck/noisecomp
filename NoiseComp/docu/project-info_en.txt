NoiseComp
=========
NoiseComp is a Java application build to calculate realtime sound synthesis.
With a graphically editor one can connect the inputs and outputs of sound generators
on a timeline in any way.

Calculating (Sound synthesis):
------------------------------
Simple sound sources (like Sinus, Ramps, Mixer, ...) with multiple Inputs
and one Output are arranged on a timeline. The inputs and outputs can connected
in any way. The finaly output signal are calculated in realtime and outputted
via Java-Sound.

Graphically Editor:
-------------------
The Application has a graphically editor in witch the signal sources pictured
as tracks with her output signals.
The connections between the inouts and outputs are visible.
This things are displayed in a full screen application with self rendered controlls
(no Swing or things like that).

Sound-Modules:
--------------
You are able to compendious generators/tracks to a module. This module works
from outside like an internal generator with a set of inputs and one output.
You are able to reuse this modules on the timeline like more complex "instruments".
