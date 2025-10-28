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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@NoArgsConstructor
public class Customer implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cust_id")
	@SequenceGenerator(name = "cust_id", initialValue = 1, allocationSize = 1, sequenceName = "CustomerId_seq")
	@Column(name = "customer_id")
	private int id;
	@Column(nullable = false)
	@NonNull
	private String name;
	private long phone;
	private String address;

	@ManyToOne
	private Bank bank;

	@OneToMany(mappedBy = "customer", cascade = CascadeType.REMOVE)
	private List<Account> accounts = new ArrayList<Account>();

	@ManyToMany
	@JoinTable(joinColumns = @JoinColumn(name = "customer_id"), inverseJoinColumns = @JoinColumn(name = "loan_id"))
	private List<Loan> loans;
}
