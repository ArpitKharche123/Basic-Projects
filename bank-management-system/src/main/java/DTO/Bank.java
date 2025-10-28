package DTO;

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
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@NoArgsConstructor
public class Bank implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bank_id")
	private int id;
	@Column(nullable = false)
	@NonNull
	private String name;
	@Column(nullable = false)
	@NonNull
	private String code;// SBI/CBI/BOM etc
	@Column(nullable = false)
	@NonNull
	private String type;// public/private/rural, etc

	@OneToMany(mappedBy = "bank", cascade = CascadeType.REMOVE)
	private List<Branch> branches = new ArrayList<Branch>();

	@OneToMany(mappedBy = "bank", cascade = CascadeType.REMOVE)
	private List<Customer> customers = new ArrayList<Customer>();
}
