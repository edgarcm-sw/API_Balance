package resources;

import com.google.gson.Gson;
import dao.ExerciseEntryDAO;
import dao.DailyLogDAO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import models.ExerciseEntry;
import models.DailyLog;
import util.GsonUtil;

import java.util.List;

@Path("/users/{userId}/exercise-entries")
public class ExerciseEntryResource {

    private final ExerciseEntryDAO exerciseEntryDAO = new ExerciseEntryDAO();
    private final DailyLogDAO dailyLogDAO = new DailyLogDAO();
    private final Gson gson = GsonUtil.createGson();

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

            // Si dailyLogId es null, obtener o crear el DailyLog de hoy
            if (entry.getDailyLogId() == null || entry.getDailyLogId() == 0) {
                DailyLog dailyLog = dailyLogDAO.getOrCreateTodayLog(userId);
                if (dailyLog == null) {
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity("{\"error\": \"Could not create daily log\"}").build();
                }
                entry.setDailyLogId(dailyLog.getId());
            }

            ExerciseEntry created = exerciseEntryDAO.createExerciseEntry(entry);
            if (created != null) {
                created.setCreatedAt(new java.sql.Timestamp(System.currentTimeMillis()));
                return Response.status(Response.Status.CREATED).entity(gson.toJson(created)).build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\"error\": \"Could not create exercise entry\"}").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"Invalid JSON format: " + e.getMessage() + "\"}").build();
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
