/*
 * www.schmiereck.de (c) 2010
 */
package de.schmiereck.noiseComp.frequences;

/**
 * <p>
 * 	Tone ToneFrequences
 * </p>
 * 
 * @author smk
 * @version <p>01.12.2010:	created, smk</p>
 */
public class ToneFrequences
{
	//**********************************************************************************************
	// Constants:

	/**
	 * see: http://www.ifi.uio.no/~inf3150/grupper/1/pcspeaker.html
	 * 
	 * Octave
	 *		0	1	2	3	4	5	6	7
	 *	Note
	 *	C	16	33	65	131	262	523	1046	2093
	 *	C#	17	35	69	139	277	554	1109	2217
	 *	D	18	37	73	147	294	587	1175	2349
	 *	D#	19	39	78	155	311	622	1244	2489
	 *	E	21	41	82	165	330	659	1328	2637
	 *	F	22	44	87	175	349	698	1397	2794
	 *	F#	23	46	92	185	370	740	1480	2960
	 *	G	24	49	98	196	392	784	1568	3136
	 *	G#	26	52	104	208	415	831	1661	3322
	 *	A	27	55	110	220	440	880	1760	3520
	 *	A#	29	58	116	233	466	932	1865	3729
	 *	B	31	62	123	245	494	988	1975	3951
	 **/
	private static final float frequences[][] = 
	{
		//	C		C#		D		D#		E		F		F#		G		G#		A		A#		B
		{	16,		17,		18,		19,		21,		22,		23,		24,		26,		27,		29,		31	},
		{	33,		35,		37,		39,		41,		44,		46,		49,		52,		55,		58,		62	},
		{	65,		69,		73,		78,		82,		87,		92,		98,		104,	110,	116,	123	},
		{	131,	139,	147,	155,	165,	175,	185,	196,	208,	220,	233,	245	},
		{	262,	277,	294,	311,	330,	349,	370,	392,	415,	440,	466,	494	},
		{	523,	554,	587,	622,	659,	698,	740,	784,	831,	880,	932,	988	},
		{	1046,	1109,	1175,	1244,	1328,	1397,	1480,	1568,	1661,	1760,	1865,	1975	},
		{	2093,	2217,	2349,	2489,	2637,	2794,	2960,	3136,	3322,	3520,	3729,	3951	}
	};
	
	public enum Tone
	{
		C,
		Csharp,
		D,
		Dsharp,
		E,
		F,
		Fsharp,
		G,
		Gsharp,
		A,
		Asharp,
		B
	}

	public static final int OCTAVE_MIN = 0;
	public static final int OCTAVE_MAX = 7;
	
	//**********************************************************************************************
	// Functions:

	/**
	 * @param octave
	 * 			is the ocave.
	 * @param tone
	 * 			is the tone.
	 * @return
	 * 			the frequence.
	 */
	public static float makeFrequence(int octave, Tone tone)
	{
		//==========================================================================================
		float freq;
		
		//------------------------------------------------------------------------------------------
		int tonePos = makeTonePos(tone);
		
		freq = frequences[octave][tonePos];
		
		//==========================================================================================
		return freq;
	}

	/**
	 * @param tone
	 * 			is the tone.
	 * @return
	 * 			the tone posistion.
	 */
	private static int makeTonePos(Tone tone)
	{
		//==========================================================================================
		int tonePos;
		
		switch (tone)
		{
			case C:
			{
				tonePos = 0;
				break;
			}
			case Csharp:
			{
				tonePos = 1;
				break;
			}
			case D:
			{
				tonePos = 2;
				break;
			}
			case Dsharp:
			{
				tonePos = 3;
				break;
			}
			case E:
			{
				tonePos = 4;
				break;
			}
			case F:
			{
				tonePos = 5;
				break;
			}
			case Fsharp:
			{
				tonePos = 6;
				break;
			}
			case G:
			{
				tonePos = 7;
				break;
			}
			case Gsharp:
			{
				tonePos = 8;
				break;
			}
			case A:
			{
				tonePos = 9;
				break;
			}
			case Asharp:
			{
				tonePos = 10;
				break;
			}
			case B:
			{
				tonePos = 11;
				break;
			}
			default:
			{
				throw new RuntimeException("Unexpected tone \"" + tone + "\".");
			}
		}
		//==========================================================================================
		return tonePos;
	}

	/**
	 * @param octave
	 * 			is the ocave.
	 * @param tone
	 * 			is the tone.
	 * @return
	 * 			the label
	 */
	public static String makeLabel(int octave, Tone tone)
	{
		//==========================================================================================
		String label;
		
		if (tone != null)
		{
			label = octave + ":" + makeToneLabel(tone);
		}
		else
		{
			label = null;
		}
		
		//==========================================================================================
		return label;
	}

	/**
	 * @param tone
	 * 			is the tone.
	 * @return
	 * 			the tone label.
	 */
	private static String makeToneLabel(Tone tone)
	{
		//==========================================================================================
		String toneLabel;
		
		switch (tone)
		{
			case C:
			{
				toneLabel = "C";
				break;
			}
			case Csharp:
			{
				toneLabel = "C#";
				break;
			}
			case D:
			{
				toneLabel = "D";
				break;
			}
			case Dsharp:
			{
				toneLabel = "D#";
				break;
			}
			case E:
			{
				toneLabel = "E";
				break;
			}
			case F:
			{
				toneLabel = "F";
				break;
			}
			case Fsharp:
			{
				toneLabel = "F#";
				break;
			}
			case G:
			{
				toneLabel = "G";
				break;
			}
			case Gsharp:
			{
				toneLabel = "G#";
				break;
			}
			case A:
			{
				toneLabel = "A";
				break;
			}
			case Asharp:
			{
				toneLabel = "A#";
				break;
			}
			case B:
			{
				toneLabel = "B";
				break;
			}
			default:
			{
				throw new RuntimeException("Unexpected tone \"" + tone + "\".");
			}
		}
		//==========================================================================================
		return toneLabel;
	}
}
