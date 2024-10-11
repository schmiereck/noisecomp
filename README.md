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
