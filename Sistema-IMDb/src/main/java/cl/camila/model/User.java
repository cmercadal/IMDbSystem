package cl.camila.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import javax.persistence.JoinColumn;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", nullable = false, unique = true)
	private Long id;
	
	
	@Size(min=3, message="Username must be present")//validacion de atributo
    private String username;
    @Size(min=5, message="Email mus be greater than 5")
    private String email;
    @Size(min=3, message="Password must be greater than 8 characters")
    private String password;
    @Transient//dice a Spring boot si este atributo debe ser ejecutado completamente o no
    private String passwordConfirmation;
    
    //Relaciones
    //1:N Show
    @OneToMany(mappedBy = "creatorShow", fetch = FetchType.LAZY)
    private List<Show> show;
    //1:N Rating
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Rating> ratings;
    //N:N Roles
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name="user_id"), inverseJoinColumns = @JoinColumn(name ="role_id"))
    private List<Role> roles;

}
