package Entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Payment implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int p_id;

	@Column(nullable = false)
	@NonNull
	private String method;

	@Column(nullable = false)
	@NonNull
	private Double amount;

	@NonNull
	private String status;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "user_id")
	private User user;

	@OneToOne
	@JoinColumn(name = "order_num")
	private Order order;

	@Override
	public String toString() {
		return "\nPayment id: " + p_id + "\nMethod: " + method + "\nAmount: " + amount + "\nstatus: " + status
				+ "\nInitiated by: " + user.getName();
	}

	@Override
	public int hashCode() {
		return Objects.hash(order, p_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Payment other = (Payment) obj;
		return Objects.equals(order, other.order) && p_id == other.p_id;
	}

}
