package resources;

import com.google.gson.Gson;
import dao.MealCategoryDAO;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import models.MealCategory;

import java.util.List;

@Path("/meal-categories")
public class MealCategoryResource {

    private final MealCategoryDAO mealCategoryDAO = new MealCategoryDAO();
    private final Gson gson = new Gson();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMealCategories() {
        List<MealCategory> categories = mealCategoryDAO.getAllMealCategories();
        return Response.ok(gson.toJson(categories)).build();
    }
}
