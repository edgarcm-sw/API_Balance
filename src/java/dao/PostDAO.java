package dao;

import db.DatabaseConnection;
import models.Post;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostDAO {

    public Post createPost(Post post) {
        String sql = "INSERT INTO Post (user_id, content) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, post.getUserId());
            stmt.setString(2, post.getContent());
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) post.setId(rs.getInt(1));
            }
            return post;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Post> getAllPostsWithAuthor() {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT p.id, p.user_id, p.content, p.created_at, u.alias " +
                     "FROM Post p " +
                     "JOIN User u ON p.user_id = u.id " +
                     "ORDER BY p.created_at DESC";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Post post = new Post();
                post.setId(rs.getInt("id"));
                post.setUserId(rs.getInt("user_id"));
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