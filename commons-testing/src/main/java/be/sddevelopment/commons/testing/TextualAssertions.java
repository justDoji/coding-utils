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

import static org.apache.commons.lang3.StringUtils.isAlpha;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.function.Supplier;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Condition;
import org.assertj.core.api.OptionalAssert;

/**
 * <p>Description of file/class</p>
 *
 * @author <a href="https://github.com/stijn-dejongh" target="_blank">Stijn Dejongh</a>
 */
public final class TextualAssertions {

	private static final String FIELD_NOT_EMPTY = "Field expected to not be empty";

	private TextualAssertions() {
		throw new UnsupportedOperationException(
				"Utility classes should not have a public or default constructor");
	}

	@SuppressWarnings("OptionalGetWithoutIsPresent")
	public static OptionalAssert<String> assertThatNumber(Supplier<Optional<String>> fieldSelector) {
		return assertNotEmpty(fieldSelector)
				       .as("must be numeric field")
				       .isPresent()
				       .is(new Condition<>(s -> StringUtils.isNumeric(s.get()), "numeric"));
	}

	@SuppressWarnings("OptionalGetWithoutIsPresent")
	public static OptionalAssert<String> assertThatAlpha(Supplier<Optional<String>> fieldSelector) {
		return assertNotEmpty(fieldSelector)
				       .as("must be alphabetical field")
				       .is(new Condition<>(s -> isAlpha(s.get()), "alphabetical"));
	}

	@SuppressWarnings("OptionalGetWithoutIsPresent")
	public static OptionalAssert<String> assertThatAlphanumeric(
			Supplier<Optional<String>> fieldSelector) {
		return assertNotEmpty(fieldSelector)
				       .as("must be alphabetical field")
				       .is(new Condition<>(s -> StringUtils.isAlphanumeric(s.get()), "alphanumeric"));
	}

	private static OptionalAssert<String> assertNotEmpty(Supplier<Optional<String>> fieldSelector) {
		return assertThat(fieldSelector.get()).as(FIELD_NOT_EMPTY).isPresent().isNotEmpty();
	}

	public static <T> OptionalAssert<T> assertEmpty(Supplier<Optional<T>> fieldSelector) {
		return assertThat(fieldSelector.get()).as("Expecting empty Optional").isEmpty();
	}
}
