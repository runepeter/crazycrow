package eu.nets.crazycrow.nsa;

import java.math.BigDecimal;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class PaymentInstruction {

	private Long id;
	private Source source;
	private BigDecimal amount;
	private String debitSocialId;
	private String creditSocialId;
	private String debitAccount;
	private String creditAccount;

	@Override
	public String toString() {
		return String.format("%s -- %s NOK -> %s.", debitSocialId, amount, creditSocialId);
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

}
