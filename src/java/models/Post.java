package models;

import java.sql.Timestamp;

public class Post {
    private int id;
    private int anonymousProfileId;
    private String content;
    private Timestamp createdAt;
    
    // Campo adicional para guardar el alias al hacer JOIN
    private String authorAlias;

    public Post() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getAnonymousProfileId() { return anonymousProfileId; }
    public void setAnonymousProfileId(int anonymousProfileId) { this.anonymousProfileId = anonymousProfileId; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    public String getAuthorAlias() { return authorAlias; }
    public void setAuthorAlias(String authorAlias) { this.authorAlias = authorAlias; }
}
