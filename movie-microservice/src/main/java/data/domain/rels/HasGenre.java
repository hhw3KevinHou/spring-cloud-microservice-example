package data.domain.rels;

import data.domain.nodes.Genre;
import data.domain.nodes.Movie;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type="HAS_GENRE")
public class HasGenre {
    @Id
    @GeneratedValue
    Long id;
    
    @StartNode
    Movie movie;
    @EndNode
    Genre genre;

    public HasGenre() {
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
