package oose2015.settings;

import oose2015.input.ControllerScheme;
import oose2015.input.InputHandler;
import oose2015.input.KeyboardMouseScheme;
import org.lwjgl.input.Keyboard;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;

/**
 * Created by kaholi on 6/29/15.
 */
public class File {


    public static void read() {
        java.io.File settings = new java.io.File(Settings.PATH);

        boolean keyboardInited = false;
        if (settings.exists()) {
            //does exists - read file
            try {
                InputStream in = Files.newInputStream(settings.toPath());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!(line.isEmpty() || line.startsWith("|"))) {
                        if (line.equals("Keyboard") && !keyboardInited) {
                            keyboardInited = true;

                            int i = 0;

                            int[] keys = new int[InputHandler.NUM_ACTIONS];
                            int[] mouseKeys = new int[InputHandler.NUM_ACTIONS];

                            while ((line = reader.readLine()) != null && !line.startsWith("|")) {
                                if (line.startsWith("m")) {
                                    mouseKeys[i] = Integer.parseInt(line.substring(1));
                                    keys[i] = -1;
                                } else if (line.startsWith("k")) {
                                    keys[i] = Integer.parseInt(line.substring(1));
                                    mouseKeys[i] = -1;

                                }
                                i++;
                                if (i == InputHandler.NUM_ACTIONS) break;
                            }

                            KeyboardMouseScheme.keys = keys;
                            KeyboardMouseScheme.mouseKeys = mouseKeys;


                        } else {
                            readController(line, reader);
                        }

                    }
                }


                System.out.println(InputHandler.CONTROLLER_SCHEMES.size() + " schemes loaded");
                for (int i = 0; i < InputHandler.CONTROLLER_SCHEMES.size(); i++) {
                    System.out.println("    " + InputHandler.CONTROLLER_SCHEMES.get(i));
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!keyboardInited) {
            KeyboardMouseScheme.keys = new int[]{Keyboard.KEY_ESCAPE, -1, Keyboard.KEY_E, Keyboard.KEY_UP, Keyboard.KEY_RIGHT, Keyboard.KEY_DOWN, Keyboard.KEY_LEFT, Keyboard.KEY_D, Keyboard.KEY_W, Keyboard.KEY_A, Keyboard.KEY_S};
            KeyboardMouseScheme.mouseKeys = new int[]{-1, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        }
    }

    public static void create(ArrayList<ControllerScheme> schemes) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("settings.txt"));
            out.write("|Arena Settings\n" +
                            "|Made by Itai Yavin & Kasper HdL\n" +
                            "|2015\n" +
                            "|\n" +
                            "| lines with \"|\" is ignored\n" +
                            "|\n" +
                            "|Action                 (Default Button)    Number:\n" +
                            "|---------------------------------------------------\n" +
                            "|Name for               controller scheme   -\n" +
                            "|Pause                  (Select)            0\n" +
                            "|Attack                 (R1)                1\n" +
                            "|Select item            (X)                 2\n" +
                            "|Up                     (Up)                3\n" +
                            "|Right                  (Right)             4\n" +
                            "|Down                   (Down)              5\n" +
                            "|Left                   (Left)              6\n" +
                            "|Movement   Axis X      (Left Stick X)      7\n" +
                            "|Movement   Axis Y      (Left Stick Y)      8\n" +
                            "|Direction  Axis X      (Right Stick X)     9\n" +
                            "|Direction  Axis Y      (Right Stick Y)     10\n" +
                            "|\n" +
                            "| Keyboards use prefix m for mouse buttons and k for keyboard buttons\n" +
                            "| And Direction is for now controlled by the mouse so, The Actions Direction X and Y is used and negative for movement\n" +
                            "|\n" +
                            "| if prefixed with b then a button index\n" +
                            "| if prefixed with a then an axis is used\n" +
                            "| x,y,z,rz means that it will use the specified named axis if any\n" +
                            "| after an axis minimum, base, max value is displayed (min,base,max)\n"
            );

            out.write("|--\n");
            out.write("Keyboard\n");
            for (int i = 0; i < InputHandler.NUM_ACTIONS; i++) {
                if (KeyboardMouseScheme.keys[i] != -1)
                    out.write("k" + KeyboardMouseScheme.keys[i] + "\n");
                else if (KeyboardMouseScheme.mouseKeys[i] != -1)
                    out.write("m" + KeyboardMouseScheme.mouseKeys[i] + "\n");
            }

            for (int i = 0; i < schemes.size(); i++) {
                out.write("|--\n");
                out.write(schemes.get(i).name + "\n");
                for (int j = 0; j < InputHandler.NUM_ACTIONS; j++) {
                    if (schemes.get(i).buttons[j] != -1)
                        out.write("b" + schemes.get(i).buttons[j] + (schemes.get(i).axis[j] != -1 ? "," : ""));
                    if (schemes.get(i).axis[j] != -1) {
                        if (schemes.get(i).axis[j] < -1)
                            switch (schemes.get(i).axis[j]) {
                                case -2:
                                    out.write("x");
                                    break;
                                case -3:
                                    out.write("y");
                                    break;
                                case -4:
                                    out.write("z");
                                    break;
                                case -5:
                                    out.write("rz");
                                    break;
                            }
                        else
                            out.write("a" + schemes.get(i).axis[j]);

                        out.write("(" + schemes.get(i).axisMin[j] + "," + schemes.get(i).axisBase[j] + "," + schemes.get(i).axisMax[j] + ")");
                    }

                    out.write("\n");
                }
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //////////
    // Utility

    private static void readController(String name, BufferedReader reader) throws IOException {

        int i = 0;

        ControllerScheme scheme = new ControllerScheme();
        scheme.name = name;
        scheme.buttons = new int[InputHandler.NUM_ACTIONS];
        scheme.axis = new int[InputHandler.NUM_ACTIONS];

        scheme.axisMin = new float[InputHandler.NUM_ACTIONS];
        scheme.axisBase = new float[InputHandler.NUM_ACTIONS];
        scheme.axisMax = new float[InputHandler.NUM_ACTIONS];

        //fill with -1
        for (int j = 0; j < InputHandler.NUM_ACTIONS; j++) {
            scheme.buttons[j] = -1;
            scheme.axis[j] = -1;
        }

        String line;

        while ((line = reader.readLine()) != null && !line.startsWith("|")) {
            int comma = line.indexOf(",");
            int paren = line.indexOf("(");

            if (comma != -1 && comma < paren) {
                convertLineToIndicis(line.substring(0, comma), i, scheme);
                line = line.substring(comma + 1);
            }

            if (paren != -1) {
                paren = line.indexOf("(");
                convertStringToAxisValues(line.substring(paren + 1), i, scheme);
                line = line.substring(0, paren);
            }

            convertLineToIndicis(line, i, scheme);

            i++;
            if (i == InputHandler.NUM_ACTIONS) break;
        }

        InputHandler.CONTROLLER_SCHEMES.add(scheme);

    }


    private static void convertLineToIndicis(String line, int i, ControllerScheme scheme) {
        if (line.startsWith("a"))
            scheme.axis[i] = Integer.parseInt(line.substring(1));
        else if (line.startsWith("b"))
            scheme.buttons[i] = Integer.parseInt(line.substring(1));

            //special cases for x, y, z, rz
        else if (line.startsWith("x"))
            scheme.axis[i] = -2;
        else if (line.startsWith("y"))
            scheme.axis[i] = -3;
        else if (line.startsWith("z"))
            scheme.axis[i] = -4;
        else if (line.startsWith("rz"))
            scheme.axis[i] = -5;
    }

    private static void convertStringToAxisValues(String line, int i, ControllerScheme scheme) {
        int end = line.indexOf(",");
        scheme.axisMin[i] = Float.parseFloat(line.substring(0, end));

        line = line.substring(end + 1);

        end = line.indexOf(",");
        scheme.axisBase[i] = Float.parseFloat(line.substring(0, end));

        line = line.substring(end + 1);

        end = line.indexOf(")");
        scheme.axisMax[i] = Float.parseFloat(line.substring(0, end));

    }

}
