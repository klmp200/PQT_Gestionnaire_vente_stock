package com.pqt.client.gui.frames.startup_frame.listeners.procedure;

public interface IStartupProcedureEventFirerer {

    void fireServerFoundEvent(String URL, Integer Port);
    void fireUserAccountUnknownEvent(String username);
    void fireUserAccountConnectedEvent(String username);
    void fireUserAccountDisconnectedEvent(String username);
    void fireStartupProcedureFinishedEvent(boolean success);

    void addListener(IStartupProcedureListener l );
    void removeListener(IStartupProcedureListener l );
}
