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

package ir.moke.foodpicker.repository;

import ir.moke.foodpicker.entity.Role;
import ir.moke.foodpicker.entity.RoleType;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class RoleRepository {

    private static final List<Role> roles = new ArrayList<>();

    @PersistenceContext
    private EntityManager em;

    public void save(Role role) {
        em.persist(role);
    }

    @SuppressWarnings("unchecked")
    public List<Role> find() {
        if (roles.isEmpty()) {
            roles.addAll(em.createQuery("from Role").getResultList());
        }
        return roles;
    }

    public Role find(RoleType roleType) {
        return roles.stream().filter(e -> e.getRoleType().equals(roleType))
                .findFirst()
                .orElse(null);
    }
}
