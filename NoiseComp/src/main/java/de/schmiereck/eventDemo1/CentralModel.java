package de.schmiereck.eventDemo1;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class CentralModel {
    private String property;
    private final List<PropertyChangeListener> listeners = new ArrayList<>();

    public String getProperty() {
        return this.property;
    }

    public void setProperty(final String property) {
        final String oldProperty = this.property;
        this.property = property;
        System.out.println("CentralModel.setProperty: property=" + property);
        this.firePropertyChange("property", oldProperty, property);
    }

    public void addPropertyChangeListener(final PropertyChangeListener listener) {
        this.listeners.add(listener);
    }

    public void removePropertyChangeListener(final PropertyChangeListener listener) {
        this.listeners.remove(listener);
    }

    private void firePropertyChange(final String propertyName, final String oldValue, final String newValue) {
        final PropertyChangeEvent event = new PropertyChangeEvent(this, propertyName, oldValue, newValue);
        for (PropertyChangeListener listener : this.listeners) {
            listener.propertyChange(event);
        }
    }
}
