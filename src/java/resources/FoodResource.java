package resources;

import com.google.gson.Gson;
import dao.FoodItemDAO;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import models.FoodItem;

import java.util.List;

@Path("/food")
public class FoodResource {

    private final FoodItemDAO foodItemDAO = new FoodItemDAO();
    private final Gson gson = new Gson();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllFoods() {
        List<FoodItem> items = foodItemDAO.getAllFoodItems();
        return Response.ok(gson.toJson(items)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createFood(String jsonRequest) {
        try {
            FoodItem newFood = gson.fromJson(jsonRequest, FoodItem.class);
            FoodItem created = foodItemDAO.createFoodItem(newFood);

            if (created != null) {
                return Response.status(Response.Status.CREATED).entity(gson.toJson(created)).build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\"error\": \"Could not create food item\"}").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Invalid JSON format\"}").build();
        }
    }
}
