package com.litle.sdk;


import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.util.Properties;

import org.junit.BeforeClass;
import org.junit.Test;

import com.litle.sdk.generate.AuthReversal;
import com.litle.sdk.generate.AuthReversalResponse;
import com.litle.sdk.generate.Authorization;
import com.litle.sdk.generate.AuthorizationResponse;
import com.litle.sdk.generate.Capture;
import com.litle.sdk.generate.CaptureResponse;
import com.litle.sdk.generate.CardType;
import com.litle.sdk.generate.Contact;
import com.litle.sdk.generate.CountryTypeEnum;
import com.litle.sdk.generate.FraudCheckType;
import com.litle.sdk.generate.MethodOfPaymentTypeEnum;
import com.litle.sdk.generate.OrderSourceType;

public class TestCert3AuthReversal {

	private static LitleOnline litle;

	@BeforeClass
	public static void beforeClass() throws Exception {
        Properties config = new Properties();
        FileInputStream fileInputStream = new FileInputStream((new Configuration()).location());
        config.load(fileInputStream);
        config.setProperty("url", "https://prelive.litle.com/vap/communicator/online");
        config.setProperty("proxyHost", "");
        config.setProperty("proxyPort", "");
        litle = new LitleOnline(config);
	}

	@Test
	public void test32() throws Exception {
		Authorization auth = new Authorization();
		auth.setOrderId("32");
		auth.setAmount(10010L);
		auth.setOrderSource(OrderSourceType.ECOMMERCE);
		Contact billToAddress = new Contact();
		billToAddress.setName("John Smith");
		billToAddress.setAddressLine1("1 Main St.");
		billToAddress.setCity("Burlington");
		billToAddress.setState("MA");
		billToAddress.setZip("01803-3747");
		billToAddress.setCountry(CountryTypeEnum.US);
		auth.setBillToAddress(billToAddress);
		CardType card = new CardType();
		card.setNumber("4457010000000009");
		card.setExpDate("0112");
		card.setCardValidationNum("349");
		card.setType(MethodOfPaymentTypeEnum.VI);
		auth.setCard(card);
		auth.setId("id");

		AuthorizationResponse authorizeResponse = litle.authorize(auth);
		assertEquals(authorizeResponse.getMessage(), "000", authorizeResponse.getResponse());
		assertEquals(authorizeResponse.getMessage(), "Approved", authorizeResponse.getMessage());
		assertEquals(authorizeResponse.getMessage(), "11111 ", authorizeResponse.getAuthCode());
		assertEquals(authorizeResponse.getMessage(), "01", authorizeResponse.getFraudResult().getAvsResult());
		assertEquals(authorizeResponse.getMessage(), "M", authorizeResponse.getFraudResult().getCardValidationResult());

		Capture capture = new Capture();
		capture.setLitleTxnId(authorizeResponse.getLitleTxnId());
		capture.setAmount(5005L);
		capture.setId("id");
		CaptureResponse captureResponse = litle.capture(capture);
		assertEquals(captureResponse.getMessage(), "000", captureResponse.getResponse());
		assertEquals(captureResponse.getMessage(), "Approved", captureResponse.getMessage());

		AuthReversal reversal = new AuthReversal();
		reversal.setId("id");
		reversal.setLitleTxnId(authorizeResponse.getLitleTxnId());
		AuthReversalResponse reversalResponse = litle.authReversal(reversal);
		assertEquals(reversalResponse.getMessage(), "000", reversalResponse.getResponse());
		assertEquals(reversalResponse.getMessage(), "Approved", reversalResponse.getMessage());
	}

	@Test
	public void test33() throws Exception {
		Authorization auth = new Authorization();
		auth.setOrderId("33");
		auth.setAmount(20020L);
		auth.setOrderSource(OrderSourceType.ECOMMERCE);
		Contact billToAddress = new Contact();
		billToAddress.setName("Mike J. Hammer");
		billToAddress.setAddressLine1("2 Main St.");
		billToAddress.setAddressLine2("Apt. 222");
		billToAddress.setCity("Riverside");
		billToAddress.setState("RI");
		billToAddress.setZip("02915");
		billToAddress.setCountry(CountryTypeEnum.US);
		auth.setBillToAddress(billToAddress);
		CardType card = new CardType();
		card.setNumber("5112010000000003");
		card.setExpDate("0212");
		card.setCardValidationNum("261");
		card.setType(MethodOfPaymentTypeEnum.MC);
		auth.setCard(card);
		FraudCheckType fraud = new FraudCheckType();
		fraud.setAuthenticationValue("BwABBJQ1AgAAAAAgJDUCAAAAAAA=");
		//TODO 3-D Secure transaction not supported by merchant
		//auth.setCardholderAuthentication(fraud);
		auth.setId("id");

		AuthorizationResponse authorizeResponse = litle.authorize(auth);
		assertEquals(authorizeResponse.getMessage(), "000", authorizeResponse.getResponse());
		assertEquals(authorizeResponse.getMessage(), "Approved", authorizeResponse.getMessage());
		assertEquals(authorizeResponse.getMessage(), "22222", authorizeResponse.getAuthCode().trim());
		assertEquals(authorizeResponse.getMessage(), "10", authorizeResponse.getFraudResult().getAvsResult());
		assertEquals(authorizeResponse.getMessage(), "M", authorizeResponse.getFraudResult().getCardValidationResult());

		AuthReversal reversal = new AuthReversal();
		reversal.setId("id");
		reversal.setLitleTxnId(authorizeResponse.getLitleTxnId());
		AuthReversalResponse reversalResponse = litle.authReversal(reversal);
		assertEquals(reversalResponse.getMessage(), "000", reversalResponse.getResponse());
		assertEquals(reversalResponse.getMessage(), "Approved", reversalResponse.getMessage());
	}

	@Test
	public void test34() throws Exception {
		Authorization auth = new Authorization();
		auth.setOrderId("34");
		auth.setAmount(30030L);
		auth.setOrderSource(OrderSourceType.ECOMMERCE);
		Contact billToAddress = new Contact();
		billToAddress.setName("Eileen Jones");
		billToAddress.setAddressLine1("3 Main St.");
		billToAddress.setCity("Bloomfield");
		billToAddress.setState("CT");
		billToAddress.setZip("06002");
		billToAddress.setCountry(CountryTypeEnum.US);
		auth.setBillToAddress(billToAddress);
		CardType card = new CardType();
		card.setNumber("6011010000000003");
		card.setExpDate("0312");
		card.setCardValidationNum("758");
		card.setType(MethodOfPaymentTypeEnum.DI);
		auth.setCard(card);
		auth.setId("id");

		AuthorizationResponse authorizeResponse = litle.authorize(auth);
		assertEquals(authorizeResponse.getMessage(), "000", authorizeResponse.getResponse());
		assertEquals(authorizeResponse.getMessage(), "Approved", authorizeResponse.getMessage());
		assertEquals(authorizeResponse.getMessage(), "33333", authorizeResponse.getAuthCode().trim());
		assertEquals(authorizeResponse.getMessage(), "10", authorizeResponse.getFraudResult().getAvsResult());
		assertEquals(authorizeResponse.getMessage(), "M", authorizeResponse.getFraudResult().getCardValidationResult());

		AuthReversal reversal = new AuthReversal();
		reversal.setId("id");
		reversal.setLitleTxnId(authorizeResponse.getLitleTxnId());
		AuthReversalResponse reversalResponse = litle.authReversal(reversal);
		assertEquals(reversalResponse.getMessage(), "000", reversalResponse.getResponse());
		assertEquals(reversalResponse.getMessage(), "Approved", reversalResponse.getMessage());
	}

	@Test
	public void test35() throws Exception {
		Authorization auth = new Authorization();
		auth.setOrderId("35");
		auth.setAmount(40040L);
		auth.setOrderSource(OrderSourceType.ECOMMERCE);
		Contact billToAddress = new Contact();
		billToAddress.setName("Bob Black");
		billToAddress.setAddressLine1("4 Main St.");
		billToAddress.setCity("Laurel");
		billToAddress.setState("MD");
		billToAddress.setZip("20708");
		billToAddress.setCountry(CountryTypeEnum.US);
		auth.setBillToAddress(billToAddress);
		CardType card = new CardType();
		card.setNumber("375001000000005");
		card.setExpDate("0412");
		card.setType(MethodOfPaymentTypeEnum.AX);
		auth.setCard(card);
		auth.setId("id");

		AuthorizationResponse authorizeResponse = litle.authorize(auth);
		//TODO Processing Network Unavailable
		//assertEquals(authorizeResponse.getMessage(), "000", authorizeResponse.getResponse());
		//assertEquals(authorizeResponse.getMessage(), "Approved", authorizeResponse.getMessage());
		//assertEquals(authorizeResponse.getMessage(), "44444 ", authorizeResponse.getAuthCode());
		//assertEquals(authorizeResponse.getMessage(), "12", authorizeResponse.getFraudResult().getAvsResult());

		Capture capture = new Capture();
		capture.setLitleTxnId(authorizeResponse.getLitleTxnId());
		capture.setAmount(20020L);
		capture.setId("id");
		CaptureResponse captureResponse = litle.capture(capture);
		assertEquals(captureResponse.getMessage(), "000", captureResponse.getResponse());
		assertEquals(captureResponse.getMessage(), "Approved", captureResponse.getMessage());

		AuthReversal reversal = new AuthReversal();
		reversal.setLitleTxnId(authorizeResponse.getLitleTxnId());
		reversal.setAmount(20020L);
		reversal.setId("id");
		AuthReversalResponse reversalResponse = litle.authReversal(reversal);
		assertEquals(reversalResponse.getMessage(), "000", reversalResponse.getResponse());
		assertEquals(reversalResponse.getMessage(), "Approved", reversalResponse.getMessage());
	}

	@Test
	public void test36() throws Exception {
		Authorization auth = new Authorization();
		auth.setOrderId("36");
		auth.setAmount(20500L);
		auth.setOrderSource(OrderSourceType.ECOMMERCE);
		CardType card = new CardType();
		card.setNumber("375000026600004");
		card.setExpDate("0512");
		card.setType(MethodOfPaymentTypeEnum.AX);
		auth.setCard(card);
		auth.setId("id");

		AuthorizationResponse authorizeResponse = litle.authorize(auth);
		//TODO Processing Network Unavailable
		//assertEquals(authorizeResponse.getMessage(), "000", authorizeResponse.getResponse());
		//assertEquals(authorizeResponse.getMessage(), "Approved", authorizeResponse.getMessage());

		AuthReversal reversal = new AuthReversal();
		reversal.setLitleTxnId(authorizeResponse.getLitleTxnId());
		reversal.setAmount(10000L);
		reversal.setId("id");
		AuthReversalResponse reversalResponse = litle.authReversal(reversal);
		assertEquals(reversalResponse.getMessage(), "000", reversalResponse.getResponse());
		assertEquals(reversalResponse.getMessage(), "Approved", reversalResponse.getMessage());
	}

}
