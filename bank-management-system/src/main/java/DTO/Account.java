package DTO;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Data
@NoArgsConstructor
public class Account implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "acc_no")
	@SequenceGenerator(name = "acc_no", initialValue = 812173523, allocationSize = 11, sequenceName = "accNo_seq")
	@Column(name = "account_no")
	private long accNo; 
	@Column(nullable = false)
	@NonNull
	private String holderName;
	@Column(nullable = false)
	@NonNull
	private String type;
	private double balance;

	@ManyToOne
	@JoinColumn
	private Customer customer;

}
