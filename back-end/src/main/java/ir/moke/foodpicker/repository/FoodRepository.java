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

import ir.moke.foodpicker.entity.Food;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Singleton
public class FoodRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(Food food) {
        em.persist(food);
    }

    public Food update(Food food) {
        return em.merge(food);
    }

    public Optional<Food> findByName(String name) {
        try {
            Food food = (Food) em.createQuery("select f from Food f where f.name=:fName")
                    .setParameter("fName", name)
                    .getSingleResult();
            return Optional.ofNullable(food);
        } catch (NoResultException nre) {
            return Optional.empty();
        }
    }

    public Optional<Food> findById(long id) {
        try {
            Food food = em.find(Food.class, id);
            return Optional.ofNullable(food);
        } catch (NoResultException nre) {
            return Optional.empty();
        }
    }

    @SuppressWarnings("unchecked")
    public List<Food> findAll() {
        return (List<Food>) em.createQuery("from Food").getResultList();
    }
}
