package Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class ProductCategory implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int c_id;

	@Column(nullable = false)
	@NonNull
	private String name;

	@OneToMany(mappedBy = "category", cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	List<Product> products = new ArrayList<Product>();

	@Override
	public String toString() {
		return "\nId: " + c_id + "\nName: " + name;
	}

}
