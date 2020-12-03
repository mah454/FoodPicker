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

package ir.moke.foodpicker.api;

import ir.moke.foodpicker.entity.Food;
import ir.moke.foodpicker.repository.FoodRepository;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;
import java.util.logging.Logger;

@Path("food")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Stateless
public class FoodResources {

    @Inject
    private Logger logger;

    @EJB
    private FoodRepository foodRepository;

    @GET
    @Path("list")
    @RolesAllowed({"FOOD_MANAGER"})
    public Response list() {
        return Response.ok(foodRepository.findAll()).build();
    }

    @POST
    @Path("save")
    @RolesAllowed("FOOD_MANAGER")
    public Response add(@Valid Food food) {
        logger.info("Add new food " + food.getName());
        foodRepository.saveOrUpdate(food);
        return Response.ok(food).build();
    }

    @GET
    @Path("find")
    @RolesAllowed({"FOOD_MANAGER"})
    public Response findFoodByName(@QueryParam("name") String name) {
        if (name == null || name.isEmpty()) {
            return Response.serverError().entity("name can not be null").build();
        }
        Optional<Food> optionalFood = foodRepository.findByName(name);
        if (optionalFood.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity("can not found food with name " + name).build();
        }
        return Response.ok(optionalFood).build();
    }

    @GET
    @Path("{id}")
    @RolesAllowed({"FOOD_MANAGER", "FOOD_PICKER"})
    public Response findFoodById(@PathParam("id") long id) {
        return Response.ok(foodRepository.findById(id)).build();
    }

    @DELETE
    @Path("/delete/{id}")
    @RolesAllowed({"FOOD_MANAGER"})
    public Response deleteFood(@PathParam("id") long id) {
        logger.warning("Remove food id:" + id);
        foodRepository.delete(id);
        return Response.ok().build();
    }
}
