package data.domain.nodes;

import org.neo4j.graphdb.Direction;
import org.neo4j.ogm.annotation.*;

import java.util.Set;

@NodeEntity
public class Job {
    @Id
     @GeneratedValue
    private Long id;

    private String title;
    private String url;

   
    @Relationship(type = "ON", direction = "NCOMING")
    Set<Event> events;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
