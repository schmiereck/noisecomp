package de.schmiereck.noiseComp.generator.filter;

import de.schmiereck.noiseComp.generator.*;
import de.schmiereck.noiseComp.generator.module.ModuleGenerator;

import static de.schmiereck.noiseComp.service.StartupService.FILTER_GENERATOR_FOLDER_PATH;

/**
 * https://blog.wolfram.com/2020/07/23/digital-vintage-sound-modeling-analog-synthesizers-with-the-wolfram-language-and-system-modeler/
 */
public class SallenKeyFilterGenerator
        extends Generator {
    //**********************************************************************************************
    // Constants:

    public static final int	INPUT_TYPE_SIGNAL		= 1;
    public static final int	INPUT_FILTER		    = 2;

    //**********************************************************************************************
    // Fields:

    /**
     * Voltage across the capacitor.
     */
    float vc1, vc2;

    //**********************************************************************************************
    // Functions:

    /**
     * Constructor.
     *
     * @param frameRate
     * 			are the Frames per Second.
     */
    public SallenKeyFilterGenerator(String name, Float frameRate, GeneratorTypeInfoData generatorTypeInfoData) {
        super(name, frameRate, generatorTypeInfoData);
    }



    /* (non-Javadoc)
     * @see de.schmiereck.noiseComp.generator.Generator#calculateSoundSample(long, float, de.schmiereck.noiseComp.generator.SoundSample, de.schmiereck.noiseComp.generator.module.ModuleGenerator)
     */
    public void calculateSoundSample(final long framePosition, final float frameTime,
                                     final SoundSample signalSample,
                                     final ModuleGenerator parentModuleGenerator,
                                     final GeneratorBufferInterface generatorBuffer,
                                     final ModuleArguments moduleArguments) {
        //==========================================================================================
        //final ResonanceFilterGeneratorData data = (ResonanceFilterGeneratorData) generatorBuffer.getGeneratorData();

        //==========================================================================================
        final InputData signalInputData = this.searchSingleInputByType(this.getGeneratorTypeData().getInputTypeData(INPUT_TYPE_SIGNAL));

        if (signalInputData != null) {
            this.calcInputValue(framePosition,
                    frameTime,
                    signalInputData,
                    signalSample,
                    parentModuleGenerator,
                    generatorBuffer,
                    moduleArguments);
        }
        //------------------------------------------------------------------------------------------
        final float filter =
                this.calcSingleInputMonoValueByInputType(framePosition,
                        frameTime,
                        this.getGeneratorTypeData().getInputTypeData(INPUT_FILTER),
                        parentModuleGenerator,
                        generatorBuffer,
                        moduleArguments);

        //------------------------------------------------------------------------------------------

        // calculate 'p' based on the graphic knob position
        final float p = (float)Math.exp(-5.0F * filter);

        // calculate the time step
        float h = 1.0F / this.getSoundFrameRate();

        // filter the input signal
        final float filteredSignal = filter(signalSample.getMonoValue(), p, h);

        signalSample.setMonoValue(filteredSignal);
    }


    /**
     * @param vin input voltage.
     * @param p potentiometer position  0.0F = no filtering, 1.0F = max filtering
     * @param h simulation time step.
     * @return filtered voltage.
     */
    float filter(float vin, float p, float h) {
        float dvc1 = -(400.0F * (vc1 + 0.5F * vc2 - 0.5F * vin)) / p;
        float dvc2 = (200.0F *vc1) / p;

        // Forward Euler method
        vc1 += dvc1 * h;
        vc2 += dvc2 * h;

        return vc2;
    }

    /* (non-Javadoc)
     * @see de.schmiereck.noiseComp.generator.Generator#createGeneratorTypeData()
     */
    public static GeneratorTypeInfoData createGeneratorTypeData()
    {
        //==========================================================================================
        GeneratorTypeInfoData generatorTypeInfoData = new GeneratorTypeInfoData(FILTER_GENERATOR_FOLDER_PATH, SallenKeyFilterGenerator.class, "SallenKey", "Sallen-Key filter.");

        {
            InputTypeData inputTypeData = new InputTypeData(INPUT_TYPE_SIGNAL, "signal", 1, 1, Float.valueOf(0.0F), "Signal value.");
            generatorTypeInfoData.addInputTypeData(inputTypeData);
        }
        {
            InputTypeData inputTypeData = new InputTypeData(INPUT_FILTER, "filter", 1, 1, Float.valueOf(1.0F), "Filter value.");
            generatorTypeInfoData.addInputTypeData(inputTypeData);
        }

        //==========================================================================================
        return generatorTypeInfoData;
    }
}
