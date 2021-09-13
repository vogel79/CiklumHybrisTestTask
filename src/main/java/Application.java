import controller.Controller;

import java.io.IOException;

public class Application {
    public static void main(String[] args) {
        Controller controller = new Controller();
        try {
            controller.readConsole();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
