/*
 * This file is part of Libresonic.
 *
 *  Libresonic is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Libresonic is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Libresonic.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.libresonic.player.dao.schema.hsql;

import org.springframework.jdbc.core.JdbcTemplate;

import org.libresonic.player.Logger;
import org.libresonic.player.dao.schema.Schema;

/**
 * Used for creating and evolving the database schema.
 * This class implements the database schema for Libresonic version 6.1.
 *
 * @author François-Xavier Thomas
 */
public class Schema61 extends Schema {

    private static final Logger LOG = Logger.getLogger(Schema61.class);

    @Override
    public void execute(JdbcTemplate template) {

        if (template.queryForInt("select count(*) from version where version = 26") == 0) {
            LOG.info("Updating database schema to version 26.");
            template.execute("insert into version values (26)");
        }

        if (!columnExists(template, "list_reload_delay", "user_settings")) {
            LOG.info("Database column 'user_settings.list_reload_delay' not found.  Creating it.");
            template.execute("alter table user_settings add list_reload_delay int default 60 not null");
            LOG.info("Database column 'user_settings.list_reload_delay' was added successfully.");
        }
    }
}
