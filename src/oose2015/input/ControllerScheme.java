package oose2015.input;

public class ControllerScheme {
    public String name;
    public int[] buttons;
    public int[] axis;
    //-1 = non, -2 = x, -3 = y, -4 = z, -5 = rz

    public float[] axisMin;
    public float[] axisBase;
    public float[] axisMax;

    public String toString() {
        String s = name + ": ";
        Action[] actions = Action.values();
        for (int i = 0; i < InputHandler.NUM_ACTIONS; i++) {
            s += actions[i].name() + "(";
            if (buttons[i] != -1)
                s += "b" + buttons[i] + (axis[i] != -1 ? "," : "");

            if (axis[i] != -1) {
                if (axis[i] < -1) {
                    switch (axis[i]) {
                        case -2:
                            s += "x";
                            break;
                        case -3:
                            s += "y";
                            break;
                        case -4:
                            s += "z";
                            break;
                        case -5:
                            s += "rz";
                            break;
                    }
                } else
                    s += "a" + axis[i];

                s += "[" + axisMin[i] + "," + axisBase[i] + "," + axisMax[i] + "]";
            }

            s += ") ";
        }
        return s;
    }

    public String toPrint() {
        return null;
    }
}