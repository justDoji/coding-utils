/*-
 * #%L
 * code-utils
 * %%
 * Copyright (C) 2020 SD Development
 * %%
 * Licensed under the EUPL, Version 1.1 or – as soon they will be
 * approved by the European Commission - subsequent versions of the
 * EUPL (the "Licence");
 *
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://ec.europa.eu/idabc/eupl5
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 * #L%
 */

package be.sddevelopment.commons.validation;

import static be.sddevelopment.commons.validation.ErrorTemplate.template;
import static be.sddevelopment.commons.validation.FailureBuilderClause.withCode;
import static be.sddevelopment.commons.validation.FallibleTest.ConsumerServiceStub.consumerStub;
import static java.util.Collections.singletonList;
import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;
import static org.assertj.core.api.Assertions.assertThat;

import be.sddevelopment.commons.testing.ReplaceUnderscoredCamelCasing;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("Tests for the Fallible class")
@DisplayNameGeneration(ReplaceUnderscoredCamelCasing.class)
class FallibleTest {

	public final List<String> NAUGHTY_WORDS = singletonList("string");
	public final String NAUGHTY_ERROR = "Your word seems to contain a naughty bit [%s]";

	static class ConsumerServiceStub<T> {

		private int amountOfCalls = 0;

		public static <S> ConsumerServiceStub<S> consumerStub() {
			return new ConsumerServiceStub<>();
		}

		public void doSomething(T input) {
			this.amountOfCalls++;
		}

		public boolean isCalled() {
			return amountOfCalls > 0;
		}

		public int callAmount() {
			return this.amountOfCalls;
		}
	}

	@Nested
	@DisplayName("Fallible basic ensure functionality")
	class FallibleEnsureTest {

		@Test
		void when1ContitionIsNotMet_thenTheErrorListContains1Failure() {
			List<Failure> errors = Fallible.of("StringToCheck").ensure(FallibleTest.this::mayNotContainNaughtyWords).failures();

			assertThat(errors).hasSize(1);
		}

		@Test
		void whenAllConditionsAreMet_thenReturnedFailures_mustBeEmpty() {
			List<Failure> failures = Fallible.of("This text contains no naught words").ensure(FallibleTest.this::mayNotContainNaughtyWords).failures();

			assertThat(failures).isEmpty();
		}

		@Test
		void whenAllConditionsAreMet_thenIsValid_returnsTrue() {
			assertThat(Fallible.of("This text contains no naughty words").ensure(FallibleTest.this::mayNotContainNaughtyWords).isValid()).isTrue();
		}
	}

	@Nested
	@DisplayName("Ensures takes the failure builder into account")
	class FallibleFailureBuilderTests {

		@Test
		void givenAConditionWithAnErrorCode_whenContitionIsNotMet_thenTheFailureContainsTheCode() {
			List<Failure> errors = Fallible.of("StringToCheck").ensure(FallibleTest.this::mayNotContainNaughtyWords, d -> b -> b.errorCode("NAUGHTY_ERROR")).ensure(StringUtils::isNotBlank, d -> b -> b.errorCode("MUST_BE_FILLED_IN")).failures();

			assertThat(errors).hasSize(1);
			assertThat(errors).extracting(Failure::getErrorCode).containsExactly("NAUGHTY_ERROR");
		}

		@Test
		void givenMultipleConditionsWithErrorCodes_whenContitionsAreNotMet_thenTheFailureContainsAllCodes() {
			List<Failure> errors = Fallible
					                       .of("")
					                       .ensure(FallibleTest.this::mayNotContainNaughtyWords, d -> b -> b.errorCode("NAUGHTY_ERROR"))
					                       .ensure(StringUtils::isNotBlank, d -> b -> b.errorCode("MUST_BE_FILLED_IN"))
					                       .ensure(StringUtils::isMixedCase, d -> b -> b.errorCode("MUST_BE_MIXED_CASE"))
					                       .failures();

			assertThat(errors).extracting(Failure::getErrorCode).containsExactlyInAnyOrder("MUST_BE_MIXED_CASE", "MUST_BE_FILLED_IN");
		}

		@Test
		void givenConditionWithSeverity_whenContitionsAreNotMet_thenTheFailureHasSetSeverity() {
			List<Failure> errors = Fallible.of("").ensure(StringUtils::isNotBlank, d -> b -> b.severity(Severity.ERROR)).failures();

			assertThat(errors).extracting(Failure::getSeverity).containsExactlyInAnyOrder(Severity.ERROR);
		}

		@Test
		void givenConditionsWithoutExplicitSeverity_whenContitionsAreNotMet_thenTheFailureHasDefaultSeverity() {
			List<Failure> errors = Fallible.of("").ensure(StringUtils::isNotBlank).failures();

			assertThat(errors).extracting(Failure::getSeverity).containsExactlyInAnyOrder(Severity.defaultVal());
		}
	}

	@Nested
	@DisplayName("Ensure supports the Fluent failure builder API")
	class FallibleFluentFailureBuilderTest {

		@Test
		void givenAConditionWithAnErrorCode_whenContitionIsNotMet_thenTheFailureContainsTheCode() {
			List<Failure> errors = Fallible
					                       .of("StringToCheck")
					                       .ensure(FieldValidationRule.<String>data().compliesTo(FallibleTest.this::mayNotContainNaughtyWords).elseFail(withCode("NAUGHTY_ERROR")))
					                       .ensure(FieldValidationRule.<String>data().compliesTo(StringUtils::isNotBlank).elseFail(withCode("MUST_BE_FILLED_IN")))
					                       .failures();

			assertThat(errors).hasSize(1);
			assertThat(errors).extracting(Failure::getErrorCode).containsExactly("NAUGHTY_ERROR");
		}
	}

	@Nested
	@DisplayName("Fallible conditional menthod executions when failure")
	class FallibleFailedConditionalTests {

		public final Condition<ConsumerServiceStub<String>> NOT_YET_CALLED = new Condition<>(c -> !c.isCalled(), "Precondition: Service is not yet called");
		private ConsumerServiceStub<String> stringConsumer;

		@BeforeEach
		void init() {
			stringConsumer = consumerStub();
		}

		@Test
		void givenAFailureActionIsDefined_whenNoConditionsAreDefined_thenTHeActionIsNotExecuted() {
			assertThat(stringConsumer).is(NOT_YET_CALLED);

			Fallible.of("This should works").orElse(stringConsumer::doSomething);

			assertThat(stringConsumer.isCalled()).isFalse();
		}

		@Test
		void givenAFailureActionIsDefined_whenNoConditionsAreMet_thenTHeActionIsNotExecuted() {
			assertThat(stringConsumer).is(NOT_YET_CALLED);

			Fallible.of("This should works").ensure(StringUtils::isBlank).orElse(stringConsumer::doSomething);

			assertThat(stringConsumer.isCalled()).isTrue();
		}

		@Test
		void givenAFailureActionIsDefined_andMultipleConditionsAreChained_whenTheFirstConditionIsMet_thenTHeActionIsExecutedOnce() {
			assertThat(stringConsumer).is(NOT_YET_CALLED);

			Fallible.of("This should works").ensure(StringUtils::isAlphanumericSpace).orElse(stringConsumer::doSomething).ensure(StringUtils::isBlank).orElse(stringConsumer::doSomething);

			assertThat(stringConsumer.callAmount()).isEqualTo(1);
		}

	}

	@Nested
	@DisplayName("Fallible conditional menthod executions when succesful")
	class FallibleConditionalExecutionsTests {

		public final Condition<ConsumerServiceStub<String>> NOT_YET_CALLED = new Condition<>(c -> !c.isCalled(), "Precondition: Service is not yet called");
		private ConsumerServiceStub<String> stringConsumer;

		@BeforeEach
		void init() {
			stringConsumer = consumerStub();
		}

		@Test
		void givenASuccesActionIsDefined_whenThereAreNoConditions_thenTheActionIsExecuted() {
			assertThat(stringConsumer).is(NOT_YET_CALLED);

			Fallible.of("This should works").ifValid(stringConsumer::doSomething);

			assertThat(stringConsumer.isCalled()).isTrue();
		}

		@Test
		void givenASuccesActionIsDefined_whenAllConditionsAreMet_thenTheActionIsExecuted() {
			assertThat(stringConsumer).is(NOT_YET_CALLED);

			Fallible.of("word123").ensure(StringUtils::isNotBlank).ifValid(stringConsumer::doSomething);

			assertThat(stringConsumer.isCalled()).isTrue();
		}

		@Test
		void givenASuccesActionIsDefined_whenConditionsAreNotMet_thenTheActionIsSkipped() {
			assertThat(stringConsumer).is(NOT_YET_CALLED);

			Fallible.of("word123").ensure(StringUtils::isBlank).ifValid(stringConsumer::doSomething);

			assertThat(stringConsumer.isCalled()).isFalse();
		}

		@Test
		void givenASuccesActionIsDefined_andConditionsAreAddedAfterSuccesAction_whenConditionsAreNotMet_thenTheActionIsSkipped() {
			assertThat(stringConsumer).is(NOT_YET_CALLED);

			Fallible.of("word123").ensure(StringUtils::isBlank).ifValid(stringConsumer::doSomething).ensure(StringUtils::isNotBlank).ifValid(stringConsumer::doSomething);

			assertThat(stringConsumer.isCalled()).isFalse();
		}

		@Test
		void givenASuccesActionIsDefined_AndMultipleConditionsExists_whenConditionsAreNotMet_thenTheActionIsSkipped() {
			assertThat(stringConsumer).is(NOT_YET_CALLED);

			Fallible.of("word123").ensure(StringUtils::isNotBlank).ensure(StringUtils::isMixedCase).ifValid(stringConsumer::doSomething);

			assertThat(stringConsumer.isCalled()).isFalse();
		}

		@Test
		void givenASuccesActionIsDefined_AndMultipleConditionsExists_whenConditionsAreMet_thenTheActionIsExecutedOnce() {
			assertThat(stringConsumer).is(NOT_YET_CALLED);

			Fallible.of("word123").ensure(StringUtils::isNotBlank).ensure(StringUtils::isAlphanumeric).ifValid(stringConsumer::doSomething);

			assertThat(stringConsumer.callAmount()).isEqualTo(1);
		}

		@Test
		void givenASuccesActionIsDefined_andConditionsAreAddedAfterSuccesAction_whenConditionsAreMet_thenTheActionIsCalledTwice() {
			assertThat(stringConsumer).is(NOT_YET_CALLED);

			Fallible.of("word123").ensure(StringUtils::isAlphanumeric).ifValid(stringConsumer::doSomething).ensure(StringUtils::isNotBlank).ifValid(stringConsumer::doSomething);

			assertThat(stringConsumer.callAmount()).isEqualTo(2);
		}

		@Test
		void givenASuccesActionIsDefined_andConditionsAreAddedAfterSuccesAction_whenOnlyTheFirstConditionIsMet_thenTheActionIsCalledOnce() {
			assertThat(stringConsumer).is(NOT_YET_CALLED);

			Fallible.of("word123").ensure(StringUtils::isAlphanumeric).ifValid(stringConsumer::doSomething).ensure(StringUtils::isBlank).ifValid(stringConsumer::doSomething);

			assertThat(stringConsumer.callAmount()).isEqualTo(1);
		}
	}

	@Nested
	@DisplayName("Fallible accepts an ErrorTemplate to generate reason messages")
	class FallibleWorksWithErrorTemplates {

		@Test
		void givenATemplate_whenAFailureOccurs_thenAFailureExistsWithADerivativeMessage() {

			List<Failure> failures = Fallible.of("").errorTemplate(template(s -> "The string [" + s + "] does not match rule: {%s}")).ensure(StringUtils::isNotBlank, s -> b -> b.reason("Input can not be blank")).failures();

			assertThat(failures).hasSize(1);
			assertThat(failures).extracting(Failure::getReason).contains("The string [] does not match rule: {Input can not be blank}");
		}

		@Test
		void givenThatNoTemplateIsProvided_andAReasonIsProvided_whenAFailureOccurs_theReasonIsReturnedAsIs() {
			List<Failure> failures = Fallible.of("").ensure(StringUtils::isNotBlank, s -> b -> b.reason("Input can not be blank")).failures();

			assertThat(failures).hasSize(1);
			assertThat(failures).extracting(Failure::getReason).contains("Input can not be blank");
		}

		@Test
		void givenATemplateWithoutDataReference_whenAFailureOccurs_thenAFailureExistsWithADerivativeMessage() {

			List<Failure> failures = Fallible.of("").errorTemplate(template(s -> "The string [" + s + "] does not match rule: {%s}")).ensure(StringUtils::isNotBlank, s -> b -> b.reason("Input can not be blank")).failures();

			assertThat(failures).hasSize(1);
			assertThat(failures).extracting(Failure::getReason).contains("The string [] does not match rule: {Input can not be blank}");
		}

	}

	Boolean mayNotContainNaughtyWords(String toCheck) {
		return NAUGHTY_WORDS.stream().noneMatch(w -> containsIgnoreCase(toCheck, w));
	}

}

