/*
 * Copyright (c) 2020.  FanapSoft Software Inc
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package ir.moke.foodpicker.setup;

import ir.moke.foodpicker.utils.TtyCodecs;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.logging.Logger;

@Startup
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
public class InitializeDatabase implements TtyCodecs {

    @Inject
    private Logger logger;

    @Resource(name = "jdbc/jta-datasource")
    private DataSource dataSource;

    private SQLException sqlException;

    @PostConstruct
    public void initializeDatabase() {
        if (isConnected()) {
            logger.info(BACKGROUND_GREEN + "*** DATABASE INITIALIZATION STARTED ***" + RESET);
            Flyway flyway = Flyway.configure().configuration(getConfiguration()).load();
            flyway.repair();
            flyway.migrate();
            logger.info(BACKGROUND_GREEN + "*** DATABASE INITIALIZATION COMPLETED ***" + RESET);
        } else {
            logger.info(BACKGROUND_RED + "WARNING: Cannot connect to database" + RESET);
            logger.info(BACKGROUND_RED + sqlException.getMessage() + RESET);
        }
    }

    private Configuration getConfiguration() {
        return Flyway.configure()
                .baselineOnMigrate(true)
                .locations("classpath:db/migration")
                .dataSource(dataSource);
    }

    private boolean isConnected() {
        try {
            dataSource.getConnection();
            return true;
        } catch (SQLException throwable) {
            this.sqlException = throwable;
            return false;
        }
    }
}
