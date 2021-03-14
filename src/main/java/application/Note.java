package application;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Notes")
public class Note {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private long id;

    private String title;
    @NotNull
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
