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

package ir.moke.foodpicker.cdi;

import ir.moke.foodpicker.logger.LogFormat;
import ir.moke.foodpicker.logger.LogHandler;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.inject.Inject;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@ApplicationScoped
public class LoggerProducer {

    @Produces
    private Logger logger(InjectionPoint injectionPoint) {
        LogManager.getLogManager().reset();

        Handler handler = new LogHandler();
        handler.setLevel(Level.ALL);
        handler.setFormatter(new LogFormat());

        Logger logger = Logger.getGlobal();
        logger.setLevel(Level.ALL);
        logger.addHandler(handler);
        return logger;
    }
}
