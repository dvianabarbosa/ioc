package step7;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

final class StdoutCapture {

    static String capture(Runnable action) {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer));
        try {
            action.run();
            return buffer.toString();
        } finally {
            System.setOut(originalOut);
        }
    }
}
