package ir.moke.foodpicker.exception;

import org.apache.commons.lang3.exception.ExceptionUtils;

import java.sql.SQLException;

public interface DatabaseExceptionMapper {

    static String getSqlState(Exception e) {
        int index = ExceptionUtils.indexOfType(e, SQLException.class);
        SQLException sqlException = ExceptionUtils.throwableOfType(e, SQLException.class, index);
        return sqlException.getSQLState();
    }
}
