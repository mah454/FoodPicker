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
import ir.moke.foodpicker.exception.BusinessException;
import ir.moke.foodpicker.exception.DatabaseExceptionMapper;
import ir.moke.foodpicker.exception.ExceptionCode;
import ir.moke.foodpicker.utils.JsonUtils;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class FoodRepository {

    @Inject
    private Logger logger;

    @PersistenceContext
    private EntityManager em;

    @Resource
    private UserTransaction transaction;

    private void handleDatabaseException(Food food, Exception e) {
        String foodType = food.getFoodType() != null ? food.getFoodType().getName() : "غذا";
        String sqlState = DatabaseExceptionMapper.getSqlState(e);
        if (sqlState.equals("23505")) {
            throw new BusinessException(ExceptionCode.OBJECT_EXISTS, String.format("این %s در حال حاظر موجود می باشد.", foodType));
        } else {
            e.printStackTrace();
            throw new BusinessException(ExceptionCode.UNKNOWN_ERROR);
        }
    }

    public void save(Food food) {
        try {
            transaction.begin();
            em.persist(food);
            transaction.commit();
        } catch (Exception e) {
            handleDatabaseException(food, e);
        }
        logger.fine("Save food:" + JsonUtils.toJson(food));
    }

    public Food update(Food food) {
        logger.fine("Update food:" + JsonUtils.toJson(food));
        return em.merge(food);
    }

    public Food saveOrUpdate(Food food) {
        Optional<Food> optionalFood = findById(food.getId());
        optionalFood.ifPresentOrElse(e -> update(food), () -> save(food));
        return food;
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

    public void delete(long id) {
        findById(id).ifPresent(em::remove);
    }
}
