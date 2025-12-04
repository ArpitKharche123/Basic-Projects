package Entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "user_info") // table named user will be present in postgres, so we have to give different
							// name
public class User implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int u_id;

	@Column(nullable = false)
	@NonNull
	private String name;

	@Column(nullable = false, unique = true)
	@NonNull
	private String email;

	@Column(nullable = false)
	@NonNull
	private String role = "user";

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Payment> payments;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Address> addresses;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Order> orders;

	@OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
	private Cart cart;

	@Override
	public String toString() {
		return "\nId: " + u_id + "\nName: " + name + "\nEmail: " + email + "\nRole: " + role;
	}

}
