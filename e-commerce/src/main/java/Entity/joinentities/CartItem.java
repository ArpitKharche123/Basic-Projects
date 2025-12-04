package Entity.joinentities;

import Entity.Cart;
import Entity.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
//JOINT ENTITY BETWEEN CART AND PRODUCT
public class CartItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int item_id;

	@ManyToOne
	@JoinColumn(name = "cart_id")
	Cart cart;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "product_id")
	Product product;

	private int quantity;

	@Override
	public String toString() {
		return "Product Details: " + product + "\nQuantity in cart: " + quantity;
	}

}
