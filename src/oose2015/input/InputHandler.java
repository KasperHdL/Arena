package oose2015.input;

import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

/**
 * Created by kaholi on 6/27/15.
 * Input handler updates all wrappers, and provides api to get a specific wrapper
 */
public class InputHandler {

    public static final int NUM_ACTIONS = 11;
    public static ArrayList<ControllerScheme> CONTROLLER_SCHEMES = new ArrayList<ControllerScheme>();

    private static ArrayList<InputWrapper> wrappers = new ArrayList<InputWrapper>(4);


    public static void addWrapper(InputWrapper wrapper) {
        wrappers.add(wrapper);
    }

    public static void removeWrapper(int i) {
        wrappers.remove(i);
    }

    public static void removeWrapper(InputWrapper wrapper) {
        wrappers.remove(wrapper);
    }

    public static InputWrapper getWrapper(int i) {
        return wrappers.get(i);
    }

    public static ArrayList<InputWrapper> getArrayList() {
        return wrappers;
    }

    public static int getSize() {
        return wrappers.size();
    }

    public static void update() {
        for (int i = 0; i < wrappers.size(); i++)
            wrappers.get(i).update();
    }

    public static boolean getKeyDown(int key) {
        return Keyboard.isKeyDown(key);
    }

}
