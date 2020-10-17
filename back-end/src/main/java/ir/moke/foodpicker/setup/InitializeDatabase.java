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

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.sql.DataSource;

@Startup
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
public class InitializeDatabase {

    @Resource(name = "jdbc/jta-datasource")
    private DataSource dataSource;

    @PostConstruct
    public void init() {
        Flyway flyway = Flyway.configure().configuration(getConfiguration()).load();
        flyway.repair();
        flyway.migrate();
    }

    private Configuration getConfiguration() {
        return Flyway.configure()
                .baselineOnMigrate(true)
                .locations("classpath:db/migration")
                .dataSource(dataSource);
    }
}
