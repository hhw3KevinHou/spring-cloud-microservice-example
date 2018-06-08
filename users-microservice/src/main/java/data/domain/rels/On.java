package data.domain.rels;

import data.domain.nodes.Content;
import data.domain.nodes.Event;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type="ON")
public class On {
    @Id
    @GeneratedValue
    Long id;
    @StartNode
    Event event;
    @EndNode
    Content content;

    public On() {
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
}
