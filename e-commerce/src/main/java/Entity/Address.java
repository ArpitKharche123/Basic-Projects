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
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Address implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int a_id;

	@Column(nullable = false)
	private int house_number;

	@Column(nullable = false)
	@NonNull
	private String area;

	@Column(nullable = false)
	@NonNull
	private String city;

	@Column(nullable = false)
	private int pincode;

	@Column(nullable = false)
	@NonNull
	private String state;

	@Column(nullable = false)
	@NonNull
	private String country;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinColumn(name = "user_id")
	private User user;

	@Override
	public String toString() {
		return "\nAddress id: " + a_id + "\nAddress: " + house_number + ", " + area + ", " + city + "-" + pincode
				+ "\nState: " + state + "\nCountry: " + country;
	}

	// for presence check in list
	@Override
	public int hashCode() {
		return Objects.hash(a_id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Address other = (Address) obj;
		return a_id == other.a_id;
	}

}
