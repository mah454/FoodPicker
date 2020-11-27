package ir.moke.foodpicker.logger;

import java.io.OutputStream;
import java.util.logging.ConsoleHandler;

public class LogHandler extends ConsoleHandler {
    @Override
    protected synchronized void setOutputStream(OutputStream out) throws SecurityException {
        super.setOutputStream(System.out);
    }
}
