package data.domain.rels;

import data.domain.nodes.Event;
import data.domain.nodes.User;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "CAUSED")
public class Caused {
    @Id
    @GeneratedValue
    Long id;
    @StartNode
    User user;
    @EndNode
    Event event;
}
