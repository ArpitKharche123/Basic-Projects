package Entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;

import Entity.joinentities.OrderItem;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "user_order") // Order will behaves like a keyword, so we have to give different table name
public class Order implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int order_num;

	@Column(nullable = false)
	@NonNull
	private Double order_amount;

	@Column(nullable = false)
	@NonNull
	private String status;

	@CreationTimestamp
	private LocalDateTime order_date;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "user_id")
	private User user;

	@OneToOne(mappedBy = "order", cascade = CascadeType.ALL,orphanRemoval = true)
	private OrderTracking orderTracking;

	 @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	 private List<OrderItem> orderItems = new ArrayList<>();

	@Override
	public String toString() {
		return "\nOrder Number: " + order_num + "\nOrder Amount: " + order_amount + "\nstatus: " + status
				+ "\nOrder Date: " + order_date;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Order))
			return false;
		Order other = (Order) o;
		return this.order_num == other.order_num;
	}

	@Override
	public int hashCode() {
		return Objects.hash(order_num);
	}

}
