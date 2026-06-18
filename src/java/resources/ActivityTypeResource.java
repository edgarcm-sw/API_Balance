package resources;

import com.google.gson.Gson;
import dao.ActivityTypeDAO;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import models.ActivityType;

import java.util.List;

@Path("/activity-types")
public class ActivityTypeResource {

    private final ActivityTypeDAO activityTypeDAO = new ActivityTypeDAO();
    private final Gson gson = new Gson();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllActivityTypes() {
        List<ActivityType> types = activityTypeDAO.getAllActivityTypes();
        return Response.ok(gson.toJson(types)).build();
    }
}
