package data.domain.nodes;


import org.neo4j.ogm.annotation.*;

@NodeEntity
public class Event {
    @Id
    @GeneratedValue
    private Long id;
    private String eventType;

 
    @Relationship(type = "ACTION", direction = Relationship.INCOMING)
    User user;


    @Relationship(type = "ON", direction = Relationship.INCOMING)        
    Content content;

    public Event() {
    }

    public Event(String eventType) {
        this.eventType = eventType;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}
