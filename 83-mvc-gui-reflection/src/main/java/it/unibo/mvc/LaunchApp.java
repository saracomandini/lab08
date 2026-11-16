package it.unibo.mvc;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawNumberView;
import it.unibo.mvc.controller.DrawNumberControllerImpl;
import it.unibo.mvc.model.DrawNumberImpl;

/**
 * Application entry-point.
 */
public final class LaunchApp {

    private static final int VIEWS = 3;
    private static final String SWING_VIEW = "it.unibo.mvc.view.DrawNumberSwingView";
    private static final String TERMINAL_VIEW = "it.unibo.mvc.view.DrawNumberTerminalView";

    private LaunchApp() { }

    /**
     * Runs the application.
     *
     * @throws ClassNotFoundException if the fetches class does not exist
     * @throws NoSuchMethodException if the 0-ary constructor do not exist
     * @throws InvocationTargetException if the constructor throws exceptions
     * @throws InstantiationException if the constructor throws exceptions
     * @throws IllegalAccessException in case of reflection issues
     * @throws IllegalArgumentException in case of reflection issues
     */
    public static void main() throws
        ClassNotFoundException,
        NoSuchMethodException,
        InvocationTargetException,
        InstantiationException,
        IllegalAccessException {

        final var model = new DrawNumberImpl();
        final DrawNumberController app = new DrawNumberControllerImpl(model);

        final Class<?> swingViewClass = Class.forName(SWING_VIEW);
        final Class<?> terminalViewClass = Class.forName(TERMINAL_VIEW);

        final Constructor<?> swingConstructor = swingViewClass.getConstructor();
        final Constructor<?> terminalConstructor = terminalViewClass.getConstructor();

        for (int i = 0; i < VIEWS; i++) {
            app.addView((DrawNumberView) swingConstructor.newInstance());
            app.addView((DrawNumberView) terminalConstructor.newInstance());
        }
    }
}
