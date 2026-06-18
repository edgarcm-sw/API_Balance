package resources;

import com.google.gson.Gson;
import dao.ExerciseEntryDAO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import models.ExerciseEntry;

import java.util.List;

@Path("/users/{userId}/exercise-entries")
public class ExerciseEntryResource {

    private final ExerciseEntryDAO exerciseEntryDAO = new ExerciseEntryDAO();
    private final Gson gson = new Gson();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getExerciseEntriesByUser(@PathParam("userId") int userId) {
        List<ExerciseEntry> entries = exerciseEntryDAO.getExerciseEntriesByUserId(userId);
        return Response.ok(gson.toJson(entries)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createExerciseEntry(@PathParam("userId") int userId, String jsonRequest) {
        try {
            ExerciseEntry entry = gson.fromJson(jsonRequest, ExerciseEntry.class);
            ExerciseEntry created = exerciseEntryDAO.createExerciseEntry(entry);

            if (created != null) {
                return Response.status(Response.Status.CREATED).entity(gson.toJson(created)).build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\"error\": \"Could not create exercise entry\"}").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Invalid JSON format\"}").build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteExerciseEntry(@PathParam("userId") int userId, @PathParam("id") int id) {
        boolean deleted = exerciseEntryDAO.deleteExerciseEntry(id);
        if (deleted) {
            return Response.ok("{\"message\": \"Exercise entry deleted successfully\"}").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Exercise entry not found\"}").build();
        }
    }
}
