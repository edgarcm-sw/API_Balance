package resources;

import com.google.gson.Gson;
import dao.PostDAO;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import models.Post;

import java.util.List;

@Path("/community")
public class CommunityResource {

    private final PostDAO postDAO = new PostDAO();
    private final Gson gson = new Gson();

    @GET
    @Path("/posts")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPosts() {
        List<Post> posts = postDAO.getAllPostsWithAuthor();
        return Response.ok(gson.toJson(posts)).build();
    }

    @POST
    @Path("/posts")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPost(String jsonRequest) {
        try {
            Post newPost = gson.fromJson(jsonRequest, Post.class);
            Post createdPost = postDAO.createPost(newPost);
            
            if (createdPost != null) {
                return Response.status(Response.Status.CREATED).entity(gson.toJson(createdPost)).build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"Could not create post\"}").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("{\"error\": \"Invalid JSON format\"}").build();
        }
    }
}
