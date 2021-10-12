package com.sripiranavan.java.test.mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MessengerTest {
	private static final String RANDOM_MESSAGE = "Message";
	private static final String RANDOM_EMAIL = "email@email.com";

	@Mock
	private TemplateEngine templateEngine;
	@Mock
	private MailServer mailServer;
	@InjectMocks
	private Messenger messenger;
	@Captor
	private ArgumentCaptor<Email> captor;

	private AutoCloseable closeble;

	@BeforeEach
	void setUp() {
		// We can create mock based on the Interface or a Class
//    	templateEngine = mock(TemplateEngine.class);	

		// init mock with annotations
//    	MockitoAnnotations.initMocks(this);

		// just another version of initilization of mocks with annotation
		// pay attention to tear down method - we have to call close method
    	closeble = MockitoAnnotations.openMocks(this);
	}

	@AfterEach
	void tearDown() throws Exception {
		closeble.close();
	}

	@Test
	void shouldSendMessage() {
//		given
		Client client = new Client(RANDOM_EMAIL);
		var template = new Template();
		when(templateEngine.prepareMessage(client, template)).thenReturn(RANDOM_MESSAGE);

//   	 row below will print RANDOM_MESSAGE
//		System.out.println(templateEngine.prepareMessage(client, template));

		// row below will print null
//		System.out.println(templateEngine.prepareMessage(new Client(RANDOM_EMAIL), new Template()));

//		when
		messenger.sendMessage(client, template);

//		then
		verify(templateEngine).prepareMessage(client, template);
		verify(mailServer).send(any(Email.class));
	}

	@Test
	void shouldSendMessageWithArgumentMatchers() {
//		given
		Client client = new Client(RANDOM_EMAIL);
		var template = new Template();
		when(templateEngine.prepareMessage(any(Client.class), any(Template.class))).thenReturn(RANDOM_EMAIL);

		// this statements will throw an exception
//		when(templateEngine.prepareMessage(client, any(Template.class))).thenReturn(RANDOM_EMAIL);

		// write this statement instead with eq() argument matcher
//		when(templateEngine.prepareMessage(eq(client), any(Template.class))).thenReturn(RANDOM_EMAIL);

		/*
		 * Also otehr matchers available: - eq() - isA(Class type) - isNull() -
		 * isNotNull() - matches(regex) - etc
		 */

//		when
		messenger.sendMessage(client, template);

//		then
		verify(templateEngine).prepareMessage(client, template);
		verify(mailServer).send(any(Email.class));
	}

	@Test
	void shouldThrowExceptionWhenTemplateEngineThrowsException() {
//		given
		Client client = new Client(RANDOM_EMAIL);
		var template = new Template();
		when(templateEngine.prepareMessage(client, template)).thenThrow(new IllegalArgumentException());

//		When & Then
		assertThrows(IllegalArgumentException.class, () -> messenger.sendMessage(client, template));
	}

	@Test
	void shouldSetClientEmailToAddresseInEmail() {
//		given
		Client client = new Client(RANDOM_EMAIL);
		var template = new Template();
		when(templateEngine.prepareMessage(client, template)).thenReturn(RANDOM_MESSAGE);

		messenger.sendMessage(client, template);

		verify(templateEngine).prepareMessage(client, template);
		verify(mailServer).send(captor.capture());
		assertEquals(client.getEmail(), captor.getValue().getAddressee());
		;

	}
}
