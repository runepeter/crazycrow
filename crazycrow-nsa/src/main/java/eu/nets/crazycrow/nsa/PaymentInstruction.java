package eu.nets.crazycrow.nsa;

import static javax.persistence.GenerationType.SEQUENCE;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity
public class PaymentInstruction {

    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "instr_seq")
    @SequenceGenerator(name = "instr_seq", sequenceName = "instr_seq", allocationSize = 1)
	private Long id;

	private Source source;
	private BigDecimal amount;
	private String debitSocialId;
	private String creditSocialId;
	private String debitAccount;
	private String creditAccount;
	private String remittanceInformation;

	@Override
	public String toString() {
		return String.format("%s -> %s :: %s NOK", debitSocialId, creditSocialId, amount);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getDebitSocialId() {
		return debitSocialId;
	}

	public void setDebitSocialId(String debitSocialId) {
		this.debitSocialId = debitSocialId;
	}

	public String getCreditSocialId() {
		return creditSocialId;
	}

	public void setCreditSocialId(String creditSocialId) {
		this.creditSocialId = creditSocialId;
	}

	public String getDebitAccount() {
		return debitAccount;
	}

	public void setDebitAccount(String debitAccount) {
		this.debitAccount = debitAccount;
	}

	public String getCreditAccount() {
		return creditAccount;
	}

	public void setCreditAccount(String creditAccount) {
		this.creditAccount = creditAccount;
	}
	
	public String getRemittanceInformation() {
		return remittanceInformation;
	}
	
	public void setRemittanceInformation(String remittanceInformation) {
		this.remittanceInformation = remittanceInformation;
	}

}
