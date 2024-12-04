package de.schmiereck.eventDemo1;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ViewModel {
    private String property;
    private final PropertyChangeSupport support;

    public ViewModel() {
        this.support = new PropertyChangeSupport(this);
    }

    public String getProperty() {
        return this.property;
    }

    public void setProperty(final String property) {
        String oldProperty = this.property;
        this.property = property;
        this.support.firePropertyChange("property", oldProperty, property);
    }

    public void addPropertyChangeListener(final PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(final PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}
