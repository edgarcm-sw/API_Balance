package dao;

import db.DatabaseConnection;
import models.Post;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostDAO {

    public Post createPost(Post post) {
        String sql = "INSERT INTO Post (anonymous_profile_id, content) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, post.getAnonymousProfileId());
            stmt.setString(2, post.getContent());
            
            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        post.setId(rs.getInt(1));
                    }
                }
            }
            return post;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Post> getAllPostsWithAuthor() {
        List<Post> posts = new ArrayList<>();
        // Consulta JOIN entre Post y Anonymous_Profile
        String sql = "SELECT p.id, p.anonymous_profile_id, p.content, p.created_at, a.alias " +
                     "FROM Post p " +
                     "JOIN Anonymous_Profile a ON p.anonymous_profile_id = a.id " +
                     "ORDER BY p.created_at DESC";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
             
            while (rs.next()) {
                Post post = new Post();
                post.setId(rs.getInt("id"));
                post.setAnonymousProfileId(rs.getInt("anonymous_profile_id"));
                post.setContent(rs.getString("content"));
                post.setCreatedAt(rs.getTimestamp("created_at"));
                post.setAuthorAlias(rs.getString("alias"));
                posts.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }
}
