package oose2015.input;

import oose2015.Settings;
import org.lwjgl.input.Controller;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created by kaholi on 6/23/15.
 *
 * Wrapper for the Controller, directly takes the lwjgl.input.controller and make it more usable
 */
public class ControllerWrapper extends InputWrapper {

    public Controller controller;
    int index;
    ControllerScheme scheme;

///////////////
// Constructor

    public ControllerWrapper(int index, ControllerScheme scheme) {
        controller = org.lwjgl.input.Controllers.getController(index);

        this.index = index;
        this.scheme = scheme;

        System.out.print(scheme.name + ": ");
        System.out.print(controller.getButtonCount() + " buttons, ");
        System.out.print(controller.getAxisCount() + " axis, ");
        System.out.println(controller.getRumblerCount() + " rumblers");
    }


////////////////////
// Getter & Setters

    public ControllerScheme getScheme() {
        return scheme;
    }

    public void setScheme(ControllerScheme scheme) {
        this.scheme = scheme;
    }

    public String getSchemeName() {
        return scheme.name;
    }

    public int getControllerIndex() {
        return index;
    }


/////////////
// Actions

    @Override
    public boolean getActionAsBoolean(Action action) {
        int index = action.ordinal();
        if (scheme.buttons[index] != -1)
            return controller.isButtonPressed(scheme.buttons[index]);
        else
            return controller.getAxisValue(scheme.axis[index]) > .5f;
    }

    @Override
    public float getActionAsFloat(Action action) {
        int index = action.ordinal();
        if (scheme.axis[index] != -1)
            if (scheme.axis[index] < -1) {
                //special cases for x,y,z,rz = (-2,-3,-4,-5)
                switch (scheme.axis[index]) {
                    case -2:
                        return controller.getXAxisValue();
                    case -3:
                        return controller.getYAxisValue();
                    case -4:
                        return controller.getZAxisValue();
                    case -5:
                        return controller.getRZAxisValue();
                    default:
                        System.out.println("axis index below -5");
                        return 0f;
                }
            } else
                return controller.getAxisValue(scheme.axis[index]);
        else
            return controller.isButtonPressed(scheme.buttons[index]) ? 1f : 0f;
    }

    @Override
    public Vector2f getMovement() {
        return new Vector2f(getActionAsFloat(Action.Movement_X), getActionAsFloat(Action.Movement_Y)).normalise();
    }

    @Override
    public Vector2f getDirection() {
        return new Vector2f(getActionAsFloat(Action.Direction_X), getActionAsFloat(Action.Direction_Y)).normalise();
    }

/////////////
// Prints

    public void printActions() {
        String s = "";
        Action[] actions = Action.values();
        for (int i = 0; i < Settings.NUM_ACTIONS; i++) {
            switch (actions[i]) {
                case Pause:
                    s += getActionAsBoolean(Action.Pause);
                    break;
                case Attack:
                    s += getActionAsBoolean(Action.Attack);
                    break;
                case Movement_X:
                    s += getActionAsVector(Action.Movement_X, Action.Movement_Y);
                    i++;
                    break;
                case Direction_X:
                    s += getActionAsVector(Action.Direction_X, Action.Direction_Y);
                    i++;
                    break;
                case Select:
                    s += getActionAsBoolean(Action.Select);
                    break;
                case Up:
                    s += getActionAsBoolean(Action.Up);
                    break;
                case Right:
                    s += getActionAsBoolean(Action.Right);
                    break;
                case Down:
                    s += getActionAsBoolean(Action.Down);
                    break;
                case Left:
                    s += getActionAsBoolean(Action.Left);
                    break;
            }
        }
    }

    public void printAll() {
        String s = "";
        for (int b = 0; b < controller.getButtonCount(); b++) {
            s += " b" + b + "(" + controller.isButtonPressed(b) + ")";
        }

        for (int a = 0; a < controller.getAxisCount(); a++) {
            s += " a" + a + "(" + controller.getAxisValue(a) + ")";
        }

        System.out.println(s);
    }

}
