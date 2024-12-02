NoiseComp is a Java synthesizer build to calculate realtime sound synthesis.
With a graphically editor one can connect the inputs and outputs of sound generators on a timeline.

![image](https://github.com/user-attachments/assets/c2438e4d-d0e8-46d7-bea3-4944c78bd8f7)


# SoundDataLogic 
is a Java library that provides a simple way to create and manipulate sound data. 
It is used by NoiseComp to generate sound data.
Uses SoundSourceLogic as a source to generate sound data.
Uses SoundBufferManager to manage the generated sound data.

# SoundBufferManager 
is a class that manages sound data. 

# SoundBuffer 
is a class that represents sound data. 

# SoundSourceLogic
is a class that represents a sound source. 

# SoundSchedulerLogic
is a class that represents a sound scheduler to calculate and play sound.

## SoundCalcSchedulerLogic
is a class that represents a sound calculation scheduler.
Runs a SoundCalcLogic in a separate thread.

### SoundCalcLogic
is a class that represents a sound calculator. 

## SoundOutSchedulerLogic
is a class that represents a sound output scheduler.
Runs a SoundOutLogic in a separate thread.

### SoundOutLogic
is a class that represents a sound output.

# Architecture

* Generator
  * GeneratorTypeInfoData

  * ModuleGenerator

* Timeline
  - Visible, editable and playable Instances of a Generator.
  
# TODO
* Special
  - Feedback 
* Effects
  - Reverb
  - Delay
  - Distortion
  - Equalizer
  - Compressor
  - Limiter
  - NoiseGate
  - Phaser
  - Flanger
  - Chorus
  - Dft-Filter (see DftDemo1Main)
* Arithmetic
  - add
  - sub
  - mult
  - div
  - mod
  - pow
  - log
  - exp
  - sqrt
  - sin
  - cos
  - tan
  - asin
  - acos
  - atan
  - atan2
  - sinh
  - cosh
  - tanh
  - asinh
* Sequencer
  - Loop
  - Keyboard
  - Midi

# Infos
## Notes
| TONES | FREQUENCY |
|-------|-----------|
| C_{4} | 261.63    |  
| D_{4} | 293.66    |
| E_{4} | 329.63    |
| F_{4} | 349.23    |
| G_{4} | 392.00    |
| A_{4} | 440.00    |
| B_{4} | 493.88    |
| C_{5} | 523.25    |

# Links
Schematic of a simple synthesizer:
https://www.falstad.com/circuit/circuitjs.html?ctz=CQAgjCAMB0l5YCsA2ATIkBmGZPIBx774FgCcyyIyEYkIikAUAIZaQAsWBIHmq3fCCEZ6deHChTx8JgHd2XTIgHYlnKPN79F21T2YK++ocb2aATubNrzYiVtuZ86rmeZXbZ1JFM77DgDGurY+Qrb0mNBkErGxiACmALRCMHBgTKhgEDY8iGAm0sLw4FphuuXuTFaVFOCopnUBcNUgtVQNjVTNhm2+5u2aAEr1XaO8yFz09Bz0olIwiEwA5m3EvHWdDAWabKH9HGAd-SLScQvgsY4a5Ko3yNPXXEczR+APmkZvL7xv5b1ODShVBTTLZX7HMb-cDFSSoJhgADsbh0Th4qERAlosSgsEgGCGCQAzgBLIkAFxYADtAgktLN6BiBN5MZ8BqybBoPIIQkCuZcWgB7YTvKSHSBUOi4iRkRHIFDoaRMYU+UWvCWXXG0UpAA

DirectX Factor - Simulating an Analog Synthesizer
https://learn.microsoft.com/en-us/archive/msdn-magazine/2013/july/directx-factor-simulating-an-analog-synthesizer

Digital Vintage Sound
Modeling Analog Synthesizers with the Wolfram Language and System Modeler
https://blog.wolfram.com/2020/07/23/digital-vintage-sound-modeling-analog-synthesizers-with-the-wolfram-language-and-system-modeler/
