package oose2015.input;

/**
 * Created by kaholi on 6/23/15.
 */
public class KeyboardWrapper extends InputWrapper {

    @Override
    public boolean getActionAsBoolean(Action action) {
        return false;
    }

    @Override
    public float getActionAsFloat(Action action) {
        return 0;
    }
}
