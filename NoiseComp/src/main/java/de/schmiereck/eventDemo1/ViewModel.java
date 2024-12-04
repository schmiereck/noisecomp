package de.schmiereck.eventDemo1;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ViewModel {
    private String property1;
    private String property2;
    private Integer property3;
    private final PropertyChangeSupport support;

    public ViewModel() {
        this.support = new PropertyChangeSupport(this);
    }

    public String getProperty1() {
        return this.property1;
    }

    public void setProperty1(final String property1) {
        String oldProperty1 = this.property1;
        this.property1 = property1;
        this.support.firePropertyChange("property1", oldProperty1, property1);
    }

    public String getProperty2() {
        return this.property2;
    }

    public void setProperty2(final String property2) {
        String oldProperty2 = this.property2;
        this.property2 = property2;
        this.support.firePropertyChange("property2", oldProperty2, property2);
    }

    public Integer getProperty3() {
        return this.property3;
    }

    public void setProperty3(final Integer property3) {
        Integer oldProperty3 = this.property3;
        this.property3 = property3;
        this.support.firePropertyChange("property3", oldProperty3, property3);
    }

    public void addPropertyChangeListener(final PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(final PropertyChangeListener listener) {
        this.support.removePropertyChangeListener(listener);
    }
}
