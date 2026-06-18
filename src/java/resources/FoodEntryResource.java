package resources;

import com.google.gson.Gson;
import dao.FoodEntryDAO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import models.FoodEntry;

import java.util.List;

@Path("/users/{userId}/food-entries")
public class FoodEntryResource {

    private final FoodEntryDAO foodEntryDAO = new FoodEntryDAO();
    private final Gson gson = new Gson();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFoodEntriesByUser(@PathParam("userId") int userId) {
        List<FoodEntry> entries = foodEntryDAO.getFoodEntriesByUserId(userId);
        return Response.ok(gson.toJson(entries)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createFoodEntry(@PathParam("userId") int userId, String jsonRequest) {
        try {
            FoodEntry entry = gson.fromJson(jsonRequest, FoodEntry.class);
            FoodEntry created = foodEntryDAO.createFoodEntry(entry);

            if (created != null) {
                return Response.status(Response.Status.CREATED).entity(gson.toJson(created)).build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\"error\": \"Could not create food entry\"}").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Invalid JSON format\"}").build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteFoodEntry(@PathParam("userId") int userId, @PathParam("id") int id) {
        boolean deleted = foodEntryDAO.deleteFoodEntry(id);
        if (deleted) {
            return Response.ok("{\"message\": \"Food entry deleted successfully\"}").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Food entry not found\"}").build();
        }
    }
}
