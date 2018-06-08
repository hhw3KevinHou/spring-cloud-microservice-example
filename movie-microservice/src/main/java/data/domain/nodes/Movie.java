package data.domain.nodes;

import org.neo4j.ogm.annotation.*;

@NodeEntity
public class Movie {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
