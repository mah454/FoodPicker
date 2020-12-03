package ir.moke.foodpicker.logger;

import ir.moke.foodpicker.utils.TtyCodecs;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

@ApplicationScoped
public class LogFormat extends SimpleFormatter implements TtyCodecs {

    @Override
    public String format(LogRecord record) {
        final LocalDateTime localDateTime = LocalDateTime.now();
        String levelName = record.getLevel().getName();

        switch (levelName) {
            case "FINE":
            case "FINER":
            case "FINEST":
                levelName = BLUE + "DEBUG" + RESET;
                break;
            case "INFO":
                levelName = PURPLE + "INFO" + RESET;
                break;
            case "WARNING":
                levelName = GREEN + "WARNING" + RESET;
                break;
            case "SEVERE":
                levelName = BACKGROUND_RED + BLINK + "ERROR" + RESET;
                break;
        }

        int threadID = record.getThreadID();
        String className = record.getSourceClassName();
        String methodName = record.getSourceMethodName();
        String message = record.getMessage();
        return "[" + levelName + "] "  + localDateTime.format(getDateTimeFormatter()) + " [" + threadID + "] " + className + " [" + methodName + "]: " + message + "\n";
    }

    private String getDateFormat() {
        return "yyyy-MM-dd HH:mm:ss.SSS";
    }

    private DateTimeFormatter getDateTimeFormatter() {
        return DateTimeFormatter.ofPattern(getDateFormat());
    }
}
