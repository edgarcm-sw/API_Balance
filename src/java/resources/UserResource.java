package resources;

import com.google.gson.Gson;
import dao.UserDAO;
import dao.DailyLogDAO;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import models.User;
import models.DailyLog;

import java.util.List;

@Path("/users")
public class UserResource {

    private final UserDAO userDAO = new UserDAO();
    private final DailyLogDAO dailyLogDAO = new DailyLogDAO();
    private final Gson gson = new Gson();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        List<User> users = userDAO.getAllUsers();
        String jsonResponse = gson.toJson(users);
        return Response.ok(jsonResponse).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("id") int id) {
        User user = userDAO.getUserById(id);
        if (user != null) {
            String jsonResponse = gson.toJson(user);
            return Response.ok(jsonResponse).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).entity("{\"error\": \"User not found\"}").build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(String jsonRequest) {
        try {
            User newUser = gson.fromJson(jsonRequest, User.class);
            User createdUser = userDAO.createUser(newUser);
            if (createdUser != null) {
                return Response.status(Response.Status.CREATED).entity(gson.toJson(createdUser)).build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Could not create user\"}").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\": \"Invalid JSON format\"}").build();
        }
    }

    @GET
    @Path("/{id}/logs")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserLogs(@PathParam("id") int id) {
        List<DailyLog> logs = dailyLogDAO.getLogsByUserId(id);
        return Response.ok(gson.toJson(logs)).build();
    }
}
