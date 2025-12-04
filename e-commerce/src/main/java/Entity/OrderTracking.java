package Entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Setter
@Getter
@EqualsAndHashCode
public class OrderTracking implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int t_id;
	
	@Column(nullable = false)
	private String status;
	
	@OneToOne
	@JoinColumn(name = "order_num")
	private Order order;

	@Override
	public String toString() {
		return "\ntracking id: "+t_id
				+"\n status: "+status;
	}
	
	
}


