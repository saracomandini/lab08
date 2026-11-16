package it.unibo.mvc.view;

import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawNumberView;
import it.unibo.mvc.api.DrawResult;

/**
 * Terminal {@link DrawNumberView} implementation.
 */
public final class DrawNumberTerminalView implements DrawNumberView {

    private static final String NEW_GAME = ": a new game starts!";

    /**
     * Builds a new Swing-based interactive view.
     */
    public DrawNumberTerminalView() {
        // empty
    }

    @Override
    public void setController(final DrawNumberController observer) {
        // it's unnecessary 
    }

    @Override
    public void start() {
        IO.println(NEW_GAME);
    }

    @Override
    public void result(final DrawResult res) {
        IO.println(res.getDescription());
    }

}
