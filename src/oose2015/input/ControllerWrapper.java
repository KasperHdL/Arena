package oose2015.input;

import org.lwjgl.input.Controller;

/**
 * Created by kaholi on 6/23/15.
 *
 * Wrapper for the Controller, directly takes the lwjgl.input.controller and make it more usable
 */
public class ControllerWrapper extends InputWrapper {

    public Controller controller;
    int index;
    ControllerScheme scheme;

    State[] buttonStates;
    float[] axisValues;

    public ControllerWrapper(int index, ControllerScheme scheme) {
        controller = org.lwjgl.input.Controllers.getController(index);

        this.index = index;
        this.scheme = scheme;

        buttonStates = new State[InputHandler.NUM_ACTIONS];
        axisValues = new float[InputHandler.NUM_ACTIONS];

        for (int i = 0; i < buttonStates.length; i++)
            buttonStates[i] = State.None;

        InputHandler.addWrapper(this);


        System.out.print(scheme.name + ": ");
        System.out.print(controller.getButtonCount() + " buttons, ");
        System.out.print(controller.getAxisCount() + " axis, ");
        System.out.print(controller.getRumblerCount() + " rumblers\n");
    }

///////////////
// Constructor

    @Override
    public void update() {
        for (int i = 0; i < InputHandler.NUM_ACTIONS; i++) {
            if (scheme.buttons[i] != -1) {
                int index = scheme.buttons[i];
                boolean isPressed = controller.isButtonPressed(index);

                updateButtonState(i, isPressed);

                //convert boolean to float
                if (scheme.axis[i] == -1)
                    axisValues[i] = isPressed ? 1f : -1f;
            }
            if (scheme.axis[i] != -1) {
                int index = scheme.axis[i];
                float value = getAxisValue(index);
                axisValues[i] = value;

                //convert float to boolean
                if (scheme.buttons[i] == -1) {
                    float base;
                    if (i >= InputHandler.NUM_ACTIONS - 4)
                        base = 0f;
                    else
                        base = -1f;

                    boolean isPressed = Math.abs(value - base) > .5f;

                    updateButtonState(i, isPressed);
                }
            }

        }
    }


////////////////////
// Update

    private void updateButtonState(int action, boolean isPressed) {

        State state = State.None;
        switch (buttonStates[action]) {
            case None:
                if (isPressed)
                    state = State.Down;
                else
                    state = State.None;
                break;
            case Down:
                if (isPressed)
                    state = State.Hold;
                else
                    state = State.Up;
                break;
            case Hold:
                if (!isPressed)
                    state = State.Up;
                else
                    state = State.Hold;
                break;
            case Up:
                if (isPressed)
                    state = State.Down;
                else
                    state = State.None;
                break;
        }

        buttonStates[action] = state;
    }

    private float getAxisValue(int index) {
        float value = 0f;
        if (index < -1) {
            //special cases for x,y,z,rz = (-2,-3,-4,-5)
            switch (index) {
                case -2:
                    value = controller.getXAxisValue();
                    break;
                case -3:
                    value = controller.getYAxisValue();
                    break;
                case -4:
                    value = controller.getZAxisValue();
                    break;
                case -5:
                    value = controller.getRZAxisValue();
                    break;
            }
        } else
            value = controller.getAxisValue(index);
        return value;
    }

    public ControllerScheme getScheme() {
        return scheme;
    }

////////////////////
// Getter & Setters

    public void setScheme(ControllerScheme scheme) {
        this.scheme = scheme;
    }

    public String getSchemeName() {
        return scheme.name;
    }

    public int getControllerIndex() {
        return index;
    }

    @Override
    public boolean getActionDown(Action action) {
        int index = action.ordinal();
        return buttonStates[index] == State.Down;
    }


/////////////
// Actions

    @Override
    public boolean getAction(Action action) {
        int index = action.ordinal();
        return buttonStates[index] == State.Hold;
    }

    @Override
    public boolean getActionUp(Action action) {
        int index = action.ordinal();
        return buttonStates[index] == State.Up;
    }

    @Override
    public float getActionAxis(Action action) {
        int index = action.ordinal();
        return axisValues[index];
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

/////////////
// Prints

    public void printActionStates() {
        String s = "";
        Action[] actions = Action.values();
        for (int i = 0; i < InputHandler.NUM_ACTIONS; i++) {
            System.out.print(actions[i].name() + "(" + buttonStates[i] + ") ");
        }
        System.out.println();

    }

    enum State {
        None,
        Down,
        Hold,
        Up
    }

}
