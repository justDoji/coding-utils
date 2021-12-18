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

package be.sddevelopment.commons.exceptions;

import static be.sddevelopment.commons.constants.Strings.EMPTY_STRING;
import static be.sddevelopment.commons.exceptions.ExceptionSuppressor.ignore;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import be.sddevelopment.commons.constants.Strings;
import be.sddevelopment.commons.testing.ReplaceUnderscoredCamelCasing;
import java.net.MalformedURLException;
import java.util.Optional;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;

/**
 * <p>
 * Description of file/class
 * </p>
 *
 * <h6>Example usage</h6>
 * <pre>
 *  <code>
 *    // No example available yet
 *  </code>
 * </pre>
 *
 * <h6>References</h6>
 *
 * @author <a href="https://github.com/stijn-dejongh" target="_blank">Stijn Dejongh</a>
 * @version 1.0.0
 * @created 01.11.20, Sunday
 * @since 1.0.0
 */
@DisplayNameGeneration(ReplaceUnderscoredCamelCasing.class)
@DisplayName("ExceptionSuppressor makes chaining more fluent")
class ExceptionSuppressorTest {

	public final Condition<Throwable> messageOfOriginalException = new Condition<>(
			c -> org.apache.commons.lang3.StringUtils.equalsIgnoreCase(c.getMessage(),
			                                                           TestMethods.EXCEPTION_MESSAGE
			), "Precondition: Throwable contains the original exception message");

	private static final class TestMethods {

		public static final String EXCEPTION_MESSAGE = "You are not allowed to enter a non-empty String";
		private static final String NO_THROW = "I did not throw it on the floor.";

		static String throwExceptionIfNotBLank(String url) throws MalformedURLException {
			if (StringUtils.isNotBlank(url)) {
				throw new MalformedURLException(EXCEPTION_MESSAGE);
			}

			return NO_THROW;
		}

	}

	@Test
	void whenAnExceptionIsSuppressed_usingDefaultSupress_thenChainInterpretsItAsEmptyResult() {
		Optional<String> result = Optional
				.of(Strings.NON_EMPTY_STRING)
				.map(ignore(TestMethods::throwExceptionIfNotBLank));

		assertThat(result).isNotPresent();
	}

	@Test
	void whenAnExceptionIsNotSuppressed_usingDefaultSupress_thenChainUsesMethodReturnObject() {
		Optional<String> result = Optional
				.of(EMPTY_STRING)
				.map(ignore(TestMethods::throwExceptionIfNotBLank));

		assertThat(result).contains(TestMethods.NO_THROW);
	}

	@Test
	void whenAnExceptionIsSupressed_ByConvertingToRuntime_thenItIsThrown() {
		Optional<String> nonEmptyString = Optional.of(Strings.NON_EMPTY_STRING);
		assertThatThrownBy(() -> nonEmptyString.map(
				ExceptionSuppressor.uncheck(TestMethods::throwExceptionIfNotBLank)))
				.isInstanceOf(WrappedException.class)
				.hasCauseInstanceOf(MalformedURLException.class);
	}

	@Test
	void whenAnExceptionIsSupressed_ByConvertingToRuntime_thenTheThrownExceptionContainsTheOriginalOne() {
		assertThatThrownBy(() -> Optional
				.of(Strings.NON_EMPTY_STRING)
				.map(ExceptionSuppressor.uncheck(TestMethods::throwExceptionIfNotBLank))).satisfies(
				messageOfOriginalException);
	}

	@Test
	void whenAnExceptionIsSupressed_usingIgnoreAsOptional_givenAMethodThatThrowsAnException_TheResultIsAnEmptyOptional() {
		assertThat(Optional
				           .of(Strings.NON_EMPTY_STRING)
				           .flatMap(ExceptionSuppressor.ignoreAsOptional(
						           TestMethods::throwExceptionIfNotBLank))).isEmpty();
	}
}
