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

/* ----------------------------------------------------------------------------
 *     PROJECT : EURES
 *
 *     PACKAGE : eu.europa.ec.empl.eures.services.commons.api.core.util.testing
 *        FILE : AssertionUtils.java
 *
 *  CREATED BY : ARHS Developments
 *          ON : Feb 27, 2021
 *
 * MODIFIED BY : ARHS Developments
 *          ON :
 *     VERSION :
 *
 * ----------------------------------------------------------------------------
 * Copyright (c) 2011 European Commission - DG EMPL
 * ----------------------------------------------------------------------------
 */
package be.sddevelopment.commons.testing;

import static org.apache.commons.lang3.StringUtils.isAlpha;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.function.Supplier;

import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Condition;
import org.assertj.core.api.OptionalAssert;

import be.sddevelopment.commons.access.AccessProtectionUtils;
import be.sddevelopment.commons.access.Utility;

/**
 * <p>Description of file/class</p>
 *
 * @author <a href="https://github.com/stijn-dejongh" target="_blank">Stijn Dejongh</a>
 * @version $Id: $Id
 * @created 18.10.20, Sunday
 */
@Utility
public final class TextualAssertionUtils {

	private static final String FIELD_NOT_EMPTY = "Field expected to not be empty";

	private TextualAssertionUtils() {
		AccessProtectionUtils.utilityClassConstructor();
	}

	@SuppressWarnings("OptionalGetWithoutIsPresent")
	public static OptionalAssert<String> assertThatNumber(Supplier<Optional<String>> fieldSelector) {
		return assertNotEmpty(fieldSelector)
				.as("must be numeric field")
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
		return assertThat(fieldSelector.get())
				.as("Expecting empty Optional")
				.isEmpty();
	}
}
