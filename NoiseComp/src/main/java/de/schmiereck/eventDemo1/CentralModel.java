package de.schmiereck.eventDemo1;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CentralModel {
    private String property1;
    private String property2;
    private Integer property3;
    private final List<PropertyChangeListener> listeners = new ArrayList<>();

    public String getProperty1() {
        return this.property1;
    }

    public void setProperty1(final String property1) {
        final String oldProperty1 = this.property1;
        this.property1 = property1;
        System.out.println("CentralModel.setProperty1: property1=" + property1);
        this.firePropertyChange("property1", oldProperty1, property1);
    }

    public String getProperty2() {
        return this.property2;
    }

    public void setProperty2(final String property2) {
        final String oldProperty2 = this.property2;
        this.property2 = property2;
        System.out.println("CentralModel.setProperty2: property2=" + property2);
        this.firePropertyChange("property2", oldProperty2, property2);
    }

    public Integer getProperty3() {
        return this.property3;
    }

    public void setProperty3(final Integer property3) {
        final Integer oldProperty3 = this.property3;
        this.property3 = property3;
        System.out.println("CentralModel.setProperty3: property3=" + property3);
        this.firePropertyChange("property3", oldProperty3, property3);
    }

    public void addPropertyChangeListener(final PropertyChangeListener listener) {
        this.listeners.add(listener);
    }

    public void removePropertyChangeListener(final PropertyChangeListener listener) {
        this.listeners.remove(listener);
    }

    private void firePropertyChange(final String propertyName, final Object oldValue, final Object newValue) {
        if (!Objects.equals(oldValue, newValue)) {
            final PropertyChangeEvent event = new PropertyChangeEvent(this, propertyName, oldValue, newValue);
            for (final PropertyChangeListener listener : this.listeners) {
                listener.propertyChange(event);
            }
        }
    }
}
