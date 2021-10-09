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

import java.util.Optional;
import java.util.function.Function;

import be.sddevelopment.commons.validation.Failure.FailureBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * <p>ValidationRule to be used to check validity of an object's field</p>
 *
 * @author <a href="https://github.com/stijn-dejongh" target="_blank">Stijn Dejongh</a>
 * @version 1.0.0
 * @created 18.10.20, Sunday
 */
@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class FieldValidationRule<R, T> {

	private Function<R, Optional<T>> extractor;
	private Function<T, Boolean> assertion;
	private Function<T, Function<FailureBuilder, FailureBuilder>> failureCreator;

	public static <R, T> FieldValidationRule<R, T> field(Function<R, T> fieldExtractor) {
		return FieldValidationRule.<R, T>builder()
				.extractor(dataStruct -> Optional.of(dataStruct).map(fieldExtractor))
				.build();
	}

}
