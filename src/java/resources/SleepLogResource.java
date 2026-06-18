package resources;

import com.google.gson.Gson;
import dao.SleepLogDAO;
import dao.DailyLogDAO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import models.SleepLog;
import models.DailyLog;
import util.GsonUtil;

import java.util.List;

@Path("/users/{userId}/sleep-logs")
public class SleepLogResource {

    private final SleepLogDAO sleepLogDAO = new SleepLogDAO();
    private final DailyLogDAO dailyLogDAO = new DailyLogDAO();
    private final Gson gson = GsonUtil.createGson();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSleepLogsByUser(@PathParam("userId") int userId) {
        List<SleepLog> logs = sleepLogDAO.getSleepLogsByUserId(userId);
        return Response.ok(gson.toJson(logs)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSleepLog(@PathParam("userId") int userId, String jsonRequest) {
        try {
            SleepLog log = gson.fromJson(jsonRequest, SleepLog.class);

            // Si dailyLogId es null, obtener o crear el DailyLog de hoy
            if (log.getDailyLogId() == null || log.getDailyLogId() == 0) {
                DailyLog dailyLog = dailyLogDAO.getOrCreateTodayLog(userId);
                if (dailyLog == null) {
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity("{\"error\": \"Could not create daily log\"}").build();
                }
                log.setDailyLogId(dailyLog.getId());
            }

            log.setUserId(userId);

            SleepLog created = sleepLogDAO.createSleepLog(log);
            if (created != null) {
                return Response.status(Response.Status.CREATED).entity(gson.toJson(created)).build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\"error\": \"Could not create sleep log\"}").build();
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
    public Response deleteSleepLog(@PathParam("userId") int userId, @PathParam("id") int id) {
        boolean deleted = sleepLogDAO.deleteSleepLog(id);
        if (deleted) {
            return Response.ok("{\"message\": \"Sleep log deleted successfully\"}").build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"Sleep log not found\"}").build();
        }
    }
}
