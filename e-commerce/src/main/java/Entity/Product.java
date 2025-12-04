package Entity;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
public class Product implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int p_id;

	@Column(nullable = false)
	@NonNull
	private String name;

	@Column(nullable = false)
	private double price;

	@NonNull
	private String description;

	private int totalQuantity;// available stock

	private boolean isAvailable = (totalQuantity == 0) ? false : true;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinColumn(name = "c_id")
	private ProductCategory category;

	@Override
	public String toString() {
		return "\nId: " + p_id + "\nName: " + name + "\nPrice: " + price + "\nDescription: " + description
				+ "\nAvailability: " + ((totalQuantity>0) ? "in stock" : "out of stock") + "\nCategory: "
				+ ((category != null) ? category.getName() : "null" + "---------\n");
	}

}
