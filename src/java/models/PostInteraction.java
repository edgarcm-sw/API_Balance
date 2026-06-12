package models;

import java.sql.Timestamp;

public class PostInteraction {
    private int id;
    private int postId;
    private String interactionType;
    private String interactionValue;
    private Timestamp createdAt;

    public PostInteraction() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getPostId() { return postId; }
    public void setPostId(int postId) { this.postId = postId; }

    public String getInteractionType() { return interactionType; }
    public void setInteractionType(String interactionType) { this.interactionType = interactionType; }

    public String getInteractionValue() { return interactionValue; }
    public void setInteractionValue(String interactionValue) { this.interactionValue = interactionValue; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
