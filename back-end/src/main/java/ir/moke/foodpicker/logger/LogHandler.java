package ir.moke.foodpicker.logger;

import java.io.IOException;
import java.util.logging.FileHandler;

public class LogHandler extends FileHandler {
    public LogHandler(String pattern, long limit, int count, boolean append) throws IOException {
        super(pattern, limit, count, append);
    }
}
