package de.schmiereck.noiseComp.generator;
/*
 * Created on 09.04.2005, Copyright (c) schmiereck, 2005
 */
/**
 * <p>
 * 	EG
H�llkurvengenerator zur Manipulation der H�llkurve einer Voice (Attack, Delay, Sustain, Release)

ADSR:

Attack
Zeit die ein Signal ben�tigt, um von Wert Null auf die max. Amplitude (Lautst�rke) zu gelangen; Einschwingphase einer Voice
Anschlag: Ansteigen auf hohen Wert.

Delay
Verz�gerungseffekt 
Schnelles Absinken auf den Sustain Wert.

Sustain
Voice aushalten
Halten des Tones auf Lautst�rke.

Release
Loslassen der Taste, Nachklingen einer Voice
Absenken und Ausklingen zu Null.


       .
      / \
     /   \_________
    /              \
 __/                \___
--+----+--+-------+--+-------
  A    D  S       R


 * </p>
 * 
 * @author smk
 * @version <p>09.04.2005:	created, smk</p>
 */
public class ADSRGenerator
{
}
