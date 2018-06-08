package data.domain.rels;

import data.domain.nodes.Job;
import data.domain.nodes.Event;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type="ON")
public class On {
    @Id
    @GeneratedValue
    Long id;
    @StartNode
    Event event;
    @EndNode
    Job job;

    public On() {
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
}
