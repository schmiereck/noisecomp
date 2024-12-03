
# Sound
## Generator
    * GeneratorTypeInfoData

    * ModuleGenerator

## Timeline
    - Visible, editable and playable Instances of a Generator.

## SoundDataLogic
is a Java library that provides a simple way to create and manipulate sound data.
It is used by NoiseComp to generate sound data.
Uses SoundSourceLogic as a source to generate sound data.
Uses SoundBufferManager to manage the generated sound data.

## SoundBufferManager
is a class that manages sound data.

## SoundBuffer
is a class that represents sound data.

## SoundSourceLogic
is a class that represents a sound source.

## SoundSchedulerLogic
is a class that represents a sound scheduler to calculate and play sound.

### SoundCalcSchedulerLogic
is a class that represents a sound calculation scheduler.
Runs a SoundCalcLogic in a separate thread.

#### SoundCalcLogic
is a class that represents a sound calculator.

### SoundOutSchedulerLogic
is a class that represents a sound output scheduler.
Runs a SoundOutLogic in a separate thread.

#### SoundOutLogic
is a class that represents a sound output.


# View

## AppController
this.inputSelectController.getInputSelectModel().getSelectedRowNoChangedNotifier().addModelPropertyChangedListener(

## TimelinesDrawPanelController / TimelinesDrawPanelModel
- SelectedTimelineModel selectedTimelineModel

## SelectedTimelineModel (Timeline)
selectedInputEntry

## InputSelectController / InputSelectModel / InputSelectView (Tabelle)
Input-Select-Table
InputSelectModel.selectedRow

## InputEditController
