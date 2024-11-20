/*-
 * #%L
 * commons-testing
 * %%
 * Copyright (C) 2020 - 2024 SD Development
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

package be.sddevelopment.commons.testing.naming;

import static org.junit.jupiter.params.provider.Arguments.of;

import java.util.stream.Stream;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayName("Replace Underscored Camel Casing TestCase naming")
class ReplaceUnderscoredCamelCasingTest implements WithAssertions {

	private final ReplaceUnderscoredCamelCasing toTest = new ReplaceUnderscoredCamelCasing();

	@ParameterizedTest
	@MethodSource("methodNamesForValidation")
	void replacesUnderscoresWithSpaces(String input, String expected) {
		assertThat(input).isNotBlank();

		var result = toTest.replaceCapitals(input);

		assertThat(result).isEqualTo(expected);
	}

	public static Stream<Arguments> methodNamesForValidation() {
		return Stream.of(
				of("canHandleEmptyDataSets", "can handle empty data sets"),
				of("parsingRequiresAtLeastAHeaderLine", "parsing requires at least a header line"),
				of("whenAnExceptionIsSuppressedUsingDefaultSuppressionThenChainInterpretsItAsEmptyResult", "when an exception is suppressed using default suppression then chain interprets it as empty result"),
				of("insultsYouGivenFileWithDataAndYourMum", "insults you given file with data and your mum")
		);
	}
}
