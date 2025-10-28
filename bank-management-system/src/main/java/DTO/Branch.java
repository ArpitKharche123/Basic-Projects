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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@NoArgsConstructor
public class Branch implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "br_id")
	@SequenceGenerator(name = "br_id", initialValue = 000001, allocationSize = 1, sequenceName = "branchId_seq")
	@Column(name = "branch_id")
	private int id;
	@Column(nullable = false)
	@NonNull
	private String name;
	@Column(nullable = true, unique = true)
	@NonNull
	private String ifsc;
	private String address;

	@ManyToOne
	@JoinColumn
	private Bank bank;

	@OneToMany(mappedBy = "branch", cascade = CascadeType.REMOVE)
	private List<Loan> loan_offers = new ArrayList<Loan>();

}
