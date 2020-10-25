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

import ir.moke.foodpicker.entity.Profile;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Singleton
public class ProfileRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Profile profile) {
        em.persist(profile);
    }

    public void update(Profile profile) {
        em.merge(profile);
    }

    public void saveOrUpdate(Profile profile) {
        Optional<Profile> optionalProfile = findByUsername(profile.getUsername());
        optionalProfile.ifPresentOrElse(e -> {
            profile.setId(e.getId());
            update(profile);
        }, () -> save(profile));
    }

    public Optional<Profile> findByUsername(String username) {
        try {
            Profile profile = (Profile) em.createQuery("select p from Profile p join fetch p.roles r where p.username=:username")
                    .setParameter("username", username)
                    .getSingleResult();
            return Optional.ofNullable(profile);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
