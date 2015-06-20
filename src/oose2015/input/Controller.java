package oose2015.input;

import oose2015.Settings;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Vector2f;

/**
 * Created by kaholi on 6/20/15.
 */
public class Controller extends PlayerInput {

    Stick left, right;
    int attack;
    boolean attackPressed;
    int index;

    public void Controller(int index, int attack, int lX, int lY, int rX, int rY) {
        setInput(index, attack, lX, lY, rX, rY);
        InputHandler.addInput(this);
    }

    public void setInput(int index, int attack, int lX, int lY, int rX, int rY) {
        this.index = index;
        this.attack = attack;
        attackPressed = false;
        left = new Stick(lX, lY);
        right = new Stick(rX, rY);
    }

    @Override
    public void update(Input input) {
        float x, y;
        x = input.getAxisValue(index, left.axisX);
        y = input.getAxisValue(index, left.axisY);
        left.update(x, y);

        x = input.getAxisValue(index, right.axisX);
        y = input.getAxisValue(index, right.axisY);
        right.update(x, y);

        attackPressed = input.isButtonPressed(attack, index);

    }

    @Override
    public Vector2f getMovement() {
        return left.value;
    }

    @Override
    public Vector2f getDirection() {
        return right.value;
    }

    @Override
    public boolean getAttackPressed() {
        return attackPressed;
    }


    class Stick {
        int axisX, axisY;

        float deadzoneX, deadzoneY;

        Vector2f value;

        Stick(int x, int y) {
            value = new Vector2f();
            setInput(x, y);
            setDeadzone(Settings.LEFT_DEAD_X, Settings.LEFT_DEAD_Y);
        }

        void update(float x, float y) {
            if (x < deadzoneX && y < deadzoneY)
                value.set(0, 0);
            else
                value.set(x, y);
        }

        public void setInput(int x, int y) {
            axisX = x;
            axisY = y;
        }

        public void setDeadzone(float x, float y) {
            deadzoneX = x;
            deadzoneY = y;
        }

    }
}
