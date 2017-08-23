package com.pqt.client.gui.ressources.components.generics.validators.listeners;

public interface IValidatorComponentFirerer {
    void addListener(IValidatorComponentListener l);
    void removeListener(IValidatorComponentListener l);

    void fireValidationEvent();
    void fireCancelEvent();
}
