package eu.nets.crazycrow.nsa;

import static eu.nets.crazycrow.nsa.TwitterPaymentProcessor.toPaymentInstruction;
import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.Test;

import twitter4j.Status;
import twitter4j.User;

public class TwitterPaymentProcessorTest {

	// 1 - @fra: @til ... Her får du 100 kroner ...
	// 2 - @fra: @til ... Jeg sender deg 100 spenn ...
	
	@Test
	public void shouldAcceptFreeFormatWithKroner() throws Exception {
		PaymentInstruction paymentInstruction = toPaymentInstruction(status("runepeter", "@steingrd Aiabaia 100 kroner tralala"));
		
		assertThat(paymentInstruction.getSource()).isEqualTo(Source.Twitter);
		assertThat(paymentInstruction.getDebitSocialId()).isEqualTo("@runepeter");
		assertThat(paymentInstruction.getCreditSocialId()).isEqualTo("@steingrd");
		assertThat(paymentInstruction.getAmount()).isEqualTo(new BigDecimal("100"));
	}
	
	@Test
	public void shouldAcceptFreeFormatWithoutSpace() throws Exception {
		PaymentInstruction paymentInstruction = toPaymentInstruction(status("runepeter", "@steingrd Aiabaia 100kr tralala"));
		
		assertThat(paymentInstruction.getSource()).isEqualTo(Source.Twitter);
		assertThat(paymentInstruction.getDebitSocialId()).isEqualTo("@runepeter");
		assertThat(paymentInstruction.getCreditSocialId()).isEqualTo("@steingrd");
		assertThat(paymentInstruction.getAmount()).isEqualTo(new BigDecimal("100"));
	}
	
	@Test
	public void shouldAcceptFreeFormatWithKr() throws Exception {
		PaymentInstruction paymentInstruction = toPaymentInstruction(status("runepeter", "@steingrd Aiabaia 100 kr tralala"));
		
		assertThat(paymentInstruction.getSource()).isEqualTo(Source.Twitter);
		assertThat(paymentInstruction.getDebitSocialId()).isEqualTo("@runepeter");
		assertThat(paymentInstruction.getCreditSocialId()).isEqualTo("@steingrd");
		assertThat(paymentInstruction.getAmount()).isEqualTo(new BigDecimal("100"));
	}
	
	@Test
	public void shouldAcceptFreeFormatWithCommaDash() throws Exception {
		PaymentInstruction paymentInstruction = toPaymentInstruction(status("runepeter", "@steingrd Aiabaia 100,- tralala"));
		
		assertThat(paymentInstruction.getSource()).isEqualTo(Source.Twitter);
		assertThat(paymentInstruction.getDebitSocialId()).isEqualTo("@runepeter");
		assertThat(paymentInstruction.getCreditSocialId()).isEqualTo("@steingrd");
		assertThat(paymentInstruction.getAmount()).isEqualTo(new BigDecimal("100"));
	}
	
	@Test
	public void shouldAcceptFormat1() throws Exception {
		PaymentInstruction paymentInstruction = toPaymentInstruction(status("runepeter", "@steingrd Her får du 100 kroner"));
		
		assertThat(paymentInstruction.getSource()).isEqualTo(Source.Twitter);
		assertThat(paymentInstruction.getDebitSocialId()).isEqualTo("@runepeter");
		assertThat(paymentInstruction.getCreditSocialId()).isEqualTo("@steingrd");
		assertThat(paymentInstruction.getAmount()).isEqualTo(new BigDecimal("100"));
	}
	
	@Test
	public void shouldAcceptFormat2() throws Exception {
		PaymentInstruction paymentInstruction = toPaymentInstruction(status("runepeter", "@steingrd Jeg sender deg 100 spenn"));
		
		assertThat(paymentInstruction.getSource()).isEqualTo(Source.Twitter);
		assertThat(paymentInstruction.getDebitSocialId()).isEqualTo("@runepeter");
		assertThat(paymentInstruction.getCreditSocialId()).isEqualTo("@steingrd");
		assertThat(paymentInstruction.getAmount()).isEqualTo(new BigDecimal("100"));
	}

	private Status status(String sender, String text) {
		User user = mock(User.class);
		when(user.getScreenName()).thenReturn(sender);
		
		Status status = mock(Status.class);
		when(status.getText()).thenReturn(text);
		when(status.getUser()).thenReturn(user);
		return status;
	}
	
}
