package oose2015.input;

import oose2015.Settings;

public class ControllerScheme {
    public String name;
    public int[] buttons;
    public int[] axis;

    public String toString() {
        String s = name + ": ";
        Action[] actions = Action.values();
        for (int i = 0; i < Settings.NUM_ACTIONS; i++) {
            s += actions[i].name() + "(" + (axis[i] == -1 ? "b" + i : "a" + i) + ") ";
        }
        return s;
    }
}