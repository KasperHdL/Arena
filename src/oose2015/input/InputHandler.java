package oose2015.input;

import org.newdawn.slick.Input;

/**
 * Created by kaholi on 6/20/15.
 */
public class InputHandler {

    //gets updated every frame
    //polls needed data and sets into playerinput

    public static PlayerInput[] inputs = new PlayerInput[4];
    public static int inputCount = 0;
    public Input input;

    public static void addInput(PlayerInput playerInput) {
        inputs[inputCount++] = playerInput;
    }

    public void update(float dt) {
        for (int i = 0; i < inputCount; i++) {
            inputs[i].update(input);
        }
    }
}
