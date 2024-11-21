/*-
 * #%L
 * code-utils
 * %%
 * Copyright (C) 2020 - 2021 SD Development
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

package be.sddevelopment.commons.testing;

import static java.util.Optional.of;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import be.sddevelopment.commons.testing.naming.ReplaceUnderscoredCamelCasing;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * <p>Shortcodes for class access restrictions and common error codes</p>
 *
 * @author <a href="https://github.com/stijn-dejongh" target="_blank">Stijn Dejongh</a>
 * @version $Id: $Id
 */
@DisplayName("Test for AssertionUtils")
class TextualAssertionsTest implements BaseTest{

	@Nested
	@DisplayName("Test with empty input")
	class EmptyInputTests {

		@Test
		void whenAssertingANumericalOptional_andTheStringIsEmpty_thenTheAssertFails() {
			assertThatThrownBy(() -> TextualAssertions.assertThatNumber(this::emptyInputSupplier))
					.isInstanceOf(AssertionError.class)
					.hasMessageContaining("to be numeric");
		}

		@Test
		void whenAssertingAnAlphaNumericalOptional_andTheStringIsEmpty_thenTheAssertFails() {
			assertThatThrownBy(() -> TextualAssertions.assertThatAlphanumeric(this::emptyInputSupplier))
					.isInstanceOf(AssertionError.class)
					.hasMessageContaining("to be alphanumeric");
		}

		@Test
		void whenAssertingAnAlphaOptional_andTheStringIsEmpty_thenTheAssertFails() {
			assertThatThrownBy(() -> TextualAssertions.assertThatAlpha(this::emptyInputSupplier))
					.isInstanceOf(AssertionError.class)
					.hasMessageContaining("to be alphabetical");
		}

		@Test
		void whenAssertingAnEmptyInput_andTheStringIsEmpty_thenTheAssertFails() {
			assertThatThrownBy(() -> TextualAssertions.assertEmpty(this::emptyInputSupplier))
					.isInstanceOf(AssertionError.class)
					.hasMessageContaining("Expecting empty Optional");
		}

		private Optional<String> emptyInputSupplier() {
			return of("");
		}
	}

	@Nested
	@DisplayName("Test with null as input")
	class NullInputTests {

		@Test
		void whenAssertingANumericalOptional_andTheInputIsNull_thenTheAssertFails() {
			assertThatThrownBy(() -> TextualAssertions.assertThatAlphanumeric(Optional::empty))
					.isInstanceOf(AssertionError.class)
					.hasMessageContaining("Field expected to not be empty");
		}

		@Test
		void whenAssertingAnAlphaNumericalOptional_andTheInputIsNull_thenTheAssertFails() {
			assertThatThrownBy(() -> TextualAssertions.assertThatAlphanumeric(Optional::empty))
					.isInstanceOf(AssertionError.class)
					.hasMessageContaining("Field expected to not be empty");
		}

		@Test
		void whenAssertingAnAlphaOptional_andTheInputIsNull_thenTheAssertFails() {
			assertThatThrownBy(() -> TextualAssertions.assertThatAlpha(Optional::empty))
					.isInstanceOf(AssertionError.class)
					.hasMessageContaining("Field expected to not be empty");
		}
	}

	@Nested
	@DisplayName("Test with expected input")
	class CorrectInputTests {

		@Test
		void whenAssertingANumericalOptional_andTheInputContainsOnlyDigits_thenTheAssertFails() {
			assertThatCode(
					() -> TextualAssertions.assertThatNumber(() -> of("1000290"))).doesNotThrowAnyException();
		}

		@Test
		void whenAssertingAnAlphaNumericalOptional_andTheInputContainsLettersAndDigits_thenTheAssertFails() {
			assertThatCode(() -> TextualAssertions.assertThatAlphanumeric(
					() -> of("abcd123jajaja"))).doesNotThrowAnyException();
		}

		@Test
		void whenAssertingAnAlphaOptional_andTheInputIsNull_thenTheAssertFails() {
			assertThatCode(() -> TextualAssertions.assertThatAlpha(
					() -> of("aaaaahjhjahzjahzjahzjahzj"))).doesNotThrowAnyException();
		}
	}

}
