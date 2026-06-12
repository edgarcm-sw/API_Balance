package resources;

import com.google.gson.Gson;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import models.FoodItem;

import java.util.ArrayList;
import java.util.List;

@Path("/food")
public class FoodResource {

    private final Gson gson = new Gson();

    // Mockup para FoodResource
    // En un caso real se llamaría al FoodDAO
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllFoods() {
        // Ejemplo estático para demostrar la estructura
        List<FoodItem> mockList = new ArrayList<>();
        FoodItem item = new FoodItem();
        item.setId(1);
        item.setName("Avena con frutas");
        item.setBaseCalories(250.0);
        item.setDescription("Tazón de avena con manzana y plátano");
        mockList.add(item);
        
        return Response.ok(gson.toJson(mockList)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createFood(String jsonRequest) {
        try {
            FoodItem newFood = gson.fromJson(jsonRequest, FoodItem.class);
            // Simular guardado
            newFood.setId((int)(Math.random() * 100));
            return Response.status(Response.Status.CREATED).entity(gson.toJson(newFood)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\": \"Invalid JSON format\"}").build();
        }
    }
}
