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

package ir.moke.foodpicker;

import ir.moke.foodpicker.entity.*;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.SchemaUpdate;
import org.hibernate.tool.schema.TargetType;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class HibernateQueryGenerator {
    public static void main(String[] args) {
//        createSchema();
        updateSchema();
    }

    private static void addEntityClass(MetadataSources metadata) {
        metadata.addAnnotatedClass(Beverage.class);
        metadata.addAnnotatedClass(Food.class);
        metadata.addAnnotatedClass(FoodCategory.class);
        metadata.addAnnotatedClass(Profile.class);
        metadata.addAnnotatedClass(Role.class);
    }

    private static void updateSchema() {
        MetadataSources metadata = getMetadataSources();
        SchemaUpdate schemaUpdate = new SchemaUpdate();
        schemaUpdate.setHaltOnError(true);
        schemaUpdate.setFormat(false);
        schemaUpdate.setDelimiter(";");
        schemaUpdate.execute(EnumSet.of(TargetType.STDOUT), metadata.buildMetadata());
    }

    private static void createSchema() {
        MetadataSources metadata = getMetadataSources();
        SchemaExport schemaExport = new SchemaExport();
        schemaExport.setHaltOnError(true);
        schemaExport.setFormat(false);
        schemaExport.setDelimiter(";");
        schemaExport.execute(EnumSet.of(TargetType.STDOUT), SchemaExport.Action.CREATE, metadata.buildMetadata());
    }

    private static MetadataSources getMetadataSources() {
        MetadataSources metadata = new MetadataSources(
                new StandardServiceRegistryBuilder()
                        .applySettings(getSettings())
                        .build());
        addEntityClass(metadata);
        return metadata;
    }

    private static Map<String, String> getSettings() {
        Map<String, String> settings = new HashMap<>();
        settings.put("connection.driver_class", "org.postgresql.Driver");
        settings.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQL10Dialect");
        settings.put("hibernate.connection.url", "jdbc:postgresql://postgresql.moke.ir:5432/foodpicker");
        settings.put("hibernate.connection.username", "admin");
        settings.put("hibernate.connection.password", "adminpass");
        return settings;
    }

    private static void sleep() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
