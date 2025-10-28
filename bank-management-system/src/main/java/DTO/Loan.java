package DTO;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@NoArgsConstructor
public class Loan implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ln_id")
	@SequenceGenerator(name = "ln_id", initialValue = 1, allocationSize = 1, sequenceName = "loanId_seq")
	@Column(name = "loan_id")
	private int id;
	@Column(nullable = false, unique = true)
	@NonNull
	private String type;
	@Column(nullable = false)
	private double amount;

	@ManyToOne
	@JoinColumn
	private Branch branch;

	@ManyToMany(mappedBy = "loans")
	private List<Customer> customers;

}
