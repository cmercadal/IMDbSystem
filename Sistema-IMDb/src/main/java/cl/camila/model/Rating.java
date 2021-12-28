package cl.camila.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ratings")
public class Rating {
    
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//Oracle SEQUENCE
    @Column(name = "ratings_id", nullable = false, unique = true)
    private Long id;
    @Max(5)
    @Min(1)
    private int rating;
    @Column(name="show_id")
    private Long showId;
    
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;
    @ManyToMany(mappedBy = "ratings")
    private List<Show> shows;
}
