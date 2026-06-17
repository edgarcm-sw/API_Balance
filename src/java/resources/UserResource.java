package resources;

import com.google.gson.Gson;
import dao.UserDAO;
import dao.DailyLogDAO;
import dao.UserProfileDAO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import models.User;
import models.UserProfile;
import models.DailyLog;

import java.util.List;

@Path("/users")
public class UserResource {

    private final UserDAO userDAO = new UserDAO();
    private final UserProfileDAO profileDAO = new UserProfileDAO();
    private final DailyLogDAO dailyLogDAO = new DailyLogDAO();
    private final Gson gson = new Gson();

    // GET /api/users
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsers() {
        List<User> users = userDAO.getAllUsers();
        for (User u : users) u.setPassword(null);
        return Response.ok(gson.toJson(users)).build();
    }

    // GET /api/users/{id}
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("id") int id) {
        User user = userDAO.getUserById(id);
        if (user == null)
            return Response.status(Response.Status.NOT_FOUND).entity("{\"error\": \"User not found\"}").build();
        user.setPassword(null);
        return Response.ok(gson.toJson(user)).build();
    }

    // POST /api/users (registro completo: User + User_Profile)
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(String jsonRequest) {
        try {
            User newUser = gson.fromJson(jsonRequest, User.class);

            if (newUser.getPassword() == null || newUser.getPassword().trim().isEmpty())
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\": \"Password is required\"}").build();
            if (newUser.getAlias() == null || newUser.getAlias().trim().isEmpty())
                return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\": \"Alias is required\"}").build();

            User created = userDAO.createUser(newUser);
            if (created == null)
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Could not create user\"}").build();

            created.setPassword(null);
            return Response.status(Response.Status.CREATED).entity(gson.toJson(created)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\": \"Invalid JSON format\"}").build();
        }
    }

    // PUT /api/users/{id} (actualizar alias y avatar)
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") int id, String jsonRequest) {
        try {
            User updates = gson.fromJson(jsonRequest, User.class);
            boolean updated = userDAO.updateUser(id, updates);
            if (!updated)
                return Response.status(Response.Status.NOT_FOUND).entity("{\"error\": \"User not found\"}").build();
            return Response.ok("{\"message\": \"User updated successfully\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\": \"Invalid JSON format\"}").build();
        }
    }

    // GET /api/users/{id}/profile
    @GET
    @Path("/{id}/profile")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserProfile(@PathParam("id") int id) {
        UserProfile profile = profileDAO.getProfileByUserId(id);
        if (profile == null)
            return Response.status(Response.Status.NOT_FOUND).entity("{\"error\": \"Profile not found\"}").build();
        return Response.ok(gson.toJson(profile)).build();
    }

    // PUT /api/users/{id}/profile (actualizar datos físicos)
    @PUT
    @Path("/{id}/profile")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUserProfile(@PathParam("id") int id, String jsonRequest) {
        try {
            UserProfile updates = gson.fromJson(jsonRequest, UserProfile.class);
            boolean updated = profileDAO.updateProfile(id, updates);
            if (!updated)
                return Response.status(Response.Status.NOT_FOUND).entity("{\"error\": \"Profile not found\"}").build();
            return Response.ok("{\"message\": \"Profile updated successfully\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\": \"Invalid JSON format\"}").build();
        }
    }

    // GET /api/users/{id}/logs
    @GET
    @Path("/{id}/logs")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserLogs(@PathParam("id") int id) {
        List<DailyLog> logs = dailyLogDAO.getLogsByUserId(id);
        return Response.ok(gson.toJson(logs)).build();
    }
}