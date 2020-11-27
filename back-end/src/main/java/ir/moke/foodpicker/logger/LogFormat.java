package ir.moke.foodpicker.logger;

import ir.moke.foodpicker.utils.TtyCodecs;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

@ApplicationScoped
public class LogFormat extends SimpleFormatter implements TtyCodecs {

    @Override
    public String format(LogRecord record) {
        final LocalDateTime localDateTime = LocalDateTime.now();
        String levelName = record.getLevel().getName();
        int threadID = record.getThreadID();
        String className = record.getSourceClassName();
        String methodName = record.getSourceMethodName();
        String message = record.getMessage();
        return localDateTime + " " + levelName + " [" + threadID + "] " + className + " [" + methodName + "] " + message;
    }
}
