package com.sripiranavan.java.test.mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
/**
 * 
 * @author sripiranavan
 *
 */
@ExtendWith(MockitoExtension.class)
public class MessengerTest2 {
	private static final String RANDOM_MESSGE = "Message";
	private static final String RANDOM_EMAIL = "email@email.com";

	@Mock
	private TemplateEngine templateEngine;
	/*
	 * Other possible answers: - CALLS_REAL_METHODS - RETURNS_DEFAULTS -
	 * RETURNS_MOCKS - RETURNS_SELF - RETURNS_SMART_NULLS
	 */
	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	private MailServer mailServer;

	@InjectMocks
	private Messenger messenger;

	@Captor
	private ArgumentCaptor<Email> captor;

	private AutoCloseable closeable;

	@Spy
	private ArrayList<Integer> list;

	@BeforeEach
	void setUp() {
		// We can create mock based on the Interface or a Class
//    	templateEngine = mock(TemplateEngine.class);	

		// init mock with annotations
//    	MockitoAnnotations.initMocks(this);

		// just another version of initilization of mocks with annotation
		// pay attention to tear down method - we have to call close method
		closeable = MockitoAnnotations.openMocks(this);
	}

	@AfterEach
	void tearDown() throws Exception {
		closeable.close();
	}

	@Test
	void shouldSendMessage() {
//		given
		var client = new Client(RANDOM_EMAIL);
		var template = new Template();
		when(templateEngine.prepareMessage(client, template)).thenReturn(RANDOM_MESSGE);

//   	 row below will print RANDOM_MESSAGE
//		System.out.println(templateEngine.prepareMessage(client, template));
//		 row below will print null
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
		var client = new Client(RANDOM_EMAIL);
		var template = new Template();
		when(templateEngine.prepareMessage(any(Client.class), any(Template.class))).thenReturn(RANDOM_EMAIL);
		/*
		 * Other possible matchers: - anyString() - anyInt() - etc
		 */

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
		var client = new Client(RANDOM_EMAIL);
		var template = new Template();
		when(templateEngine.prepareMessage(client, template)).thenThrow(new IllegalArgumentException());

//		When & Then
		assertThrows(IllegalArgumentException.class, () -> messenger.sendMessage(client, template));
	}

	@Test
	void shouldSetClientEmailToAddresseInEmail() {
//		given
		var client = new Client(RANDOM_EMAIL);
		var template = new Template();
		when(templateEngine.prepareMessage(client, template)).thenReturn(RANDOM_MESSGE);

		messenger.sendMessage(client, template);

		verify(templateEngine).prepareMessage(client, template);
		verify(mailServer).send(captor.capture());
		assertEquals(client.getEmail(), captor.getValue().getAddressee());
	}

	@Test
	void shouldSendMessageOnlyOneTime() {
//		given
		var client = new Client(RANDOM_EMAIL);
		var template = new Template();
		when(templateEngine.prepareMessage(client, template)).thenReturn(RANDOM_MESSGE);

//		when
		messenger.sendMessage(client, template);

//		then
		verify(templateEngine, times(1)).prepareMessage(client, template);
		verify(mailServer, times(1)).send(any(Email.class));

		verify(mailServer, atLeastOnce()).send(any(Email.class));
		verify(mailServer, atLeast(1)).send(any(Email.class));
		verify(mailServer, atMost(1)).send(any(Email.class));
//		verify(mailServer,never()).send(any(Email.class));
	}

	@Test
	void shouldSendMessageAndPrepareTemplateBeforeThat() {
//		given
		var inOrder = Mockito.inOrder(templateEngine, mailServer);
		var client = new Client(RANDOM_EMAIL);
		var template = new Template();
		when(templateEngine.prepareMessage(client, template)).thenReturn(RANDOM_MESSGE);

//		when
		messenger.sendMessage(client, template);

//		then
		inOrder.verify(templateEngine).prepareMessage(client, template);
		inOrder.verify(mailServer).send(any(Email.class));

//		this fail
//		inOrder.verify(mailServer).send(any(Email.class));
//		inOrder.verify(templateEngine).prepareMessage(client, template);
	}

	@Test
	void shouldSendMessageAndThereIsNoMoreInteractionWithTemplateEngine() {
//		given
		var inOrder = Mockito.inOrder(templateEngine, mailServer);
		var client = new Client(RANDOM_EMAIL);
		var template = new Template();
		when(templateEngine.prepareMessage(client, template)).thenReturn(RANDOM_MESSGE);

//		when
		messenger.sendMessage(client, template);

//		then
		inOrder.verify(templateEngine).prepareMessage(client, template);
		inOrder.verify(mailServer).send(any(Email.class));
		verifyNoMoreInteractions(templateEngine, mailServer);

//      In this case verifyNoMoreInteractions will throw an exception
//		templateEngine.evaluateTemplate(null);
//		verifyNoMoreInteractions(templateEngine);
	}

	@Test
	void shouldCallRealMethodOnMockExample() {
		Client client = new Client("");
		Template template = new Template();

//		null is returned
		System.out.println(templateEngine.prepareMessage(client, template));

		when(templateEngine.prepareMessage(client, template)).thenCallRealMethod();
		// 'Some template' will be returned - because that is the result of prepare
		// message
		System.out.println(templateEngine.prepareMessage(client, template));
	}

	@Test
	void shouldNotThrowExceptionWithDeepStub() {
//		var client = new Client(RANDOM_EMAIL);
//		var template = new Template();

		var validator = mailServer.getValidator();

		// this line will throw NPE without DEEP STUBS
		validator.validate(new Email());
	}

	@Test
	void additionalMatchersDemo() {
		@SuppressWarnings({ "unchecked", "unused" })
		List<String> listMock = mock(List.class);
//    	when(listMock.get(or(eq(1), eq(2)))).thenReturn("more");
//    	when(listMock.get(gt(2))).thenThrow(new RuntimeException());
//    	
//    	System.out.println(listMock.get(1)); // more is returned
//    	System.out.println(listMock.get(2)); // more is returned
//    	System.out.println(listMock.get(3)); // RuntimeException is thrown

		/*
		 * There are other matchers, such as: - or() - not() - lt() - less than - leq()
		 * - less than or equal to - gt() - greater than - geq() - greater than or equal
		 * to - find(regex) - eq() - cmpEq() - aryEq() - and()
		 * 
		 * 
		 */
	}

	@Test
	void verifyVoidMethodExample() {
		@SuppressWarnings({ "unused", "unchecked" })
		List<String> listMock = mock(List.class);

		// You can't use when method like below with void methods
//		when(listMock.add(1, "first argumnet")).thenThrow(new IllegalArgumentException());

		// instead you can set behavior to void methods like this
//		doThrow(IllegalArgumentException.class).when(listMock).add(1, "first argument");
//		listMock.add(1, "");

//		@SuppressWarnings("unchecked")
//		ArgumentCaptor<Comparator<String>> captor = ArgumentCaptor.forClass(Comparator.class);
//		doNothing().when(listMock).sort(captor.capture());

		doAnswer(methodInvocation -> {
			Object arg0 = methodInvocation.getArgument(0);
			Object arg1 = methodInvocation.getArgument(1);

			assertEquals(3, arg0);
			assertEquals("List Element", arg1);
			return null;
		}).when(listMock).add(anyInt(), anyString());
		listMock.add(3, "List Element");
	}

	@Test
	void shouldThrowExceptionWhenMockFinalClass() {
//		Can't mock final class
//		String string = mock(String.class);
//		when(string.length()).thenReturn(20);
//		assertThat(string.length(), equalTo(20));

//		Can't mock static method
//		when(Map.of()).thenReturn(null);

//		Can't mock equals of hashCode
	}

	@Test
	void spyExample() {
		List<Integer> listInts = new ArrayList<Integer>();
		List<Integer> spy = Mockito.spy(listInts);

		// OutOfBoundsException because get method is called on real list that is empty
//		when(spy.get(0)).thenReturn(0);

		doReturn(0).when(spy).get(0);
		System.out.println(spy.get(0));

		verify(spy).get(0);
	}
}
