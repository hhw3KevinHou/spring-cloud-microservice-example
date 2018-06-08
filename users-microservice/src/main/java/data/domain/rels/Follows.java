package data.domain.rels;

import data.domain.nodes.User;
import org.neo4j.ogm.annotation.*;
@RelationshipEntity(type="FOLLOWS")
public class Follows {
    @Id
    @GeneratedValue
    Long id;
    @StartNode
    User follower;
    @EndNode
    User follows;

    public Follows() {
    }

    public User getFollower() {
        return follower;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }

    public User getFollows() {
        return follows;
    }

    public void setFollows(User follows) {
        this.follows = follows;
    }
}
