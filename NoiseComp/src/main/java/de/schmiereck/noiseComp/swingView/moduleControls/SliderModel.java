package de.schmiereck.noiseComp.swingView.moduleControls;

public class SliderModel {
    private int min;
    private int max;
    private String label;
    private int height;
    private int width;
    private int x;
    private int y;

    public SliderModel(String label, int min, int max) {
        this.label = label;
        this.min = min;
        this.max = max;
    }

    public SliderModel(String label, int min, int max, int width, int height) {
        this.label = label;
        this.min = min;
        this.max = max;
        this.width = width;
        this.height = height;
    }

    public int getMin() { return min; }

    public void setMin(int min) { this.min = min; }

    public int getMax() { return max; }

    public void setMax(int max) { this.max = max; }

    public String getLabel() { return label; }

    public void setLabel(String label) { this.label = label; }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}
