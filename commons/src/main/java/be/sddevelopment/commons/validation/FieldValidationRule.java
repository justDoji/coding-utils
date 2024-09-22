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

import be.sddevelopment.commons.validation.Failure.FailureBuilder;
import java.util.Optional;
import java.util.function.Function;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * <p>ValidationRule to be used to check validity of an object's field</p>
 *
 * @author <a href="https://github.com/stijn-dejongh" target="_blank">Stijn Dejongh</a>
 * @version 1.0.0
 */
@AllArgsConstructor
@Builder(toBuilder = true)
public class FieldValidationRule<R, T> implements Rule<R> {

	Function<R, T> extractor;
	Function<T, Boolean> fieldAssertion;
	FailureBuilderClause<T> failureCreator;

	public static <R, T> FieldValidationRule<R, T> field(Function<R, T> fieldExtractor) {
		return FieldValidationRule
				       .<R, T>builder()
				       .extractor(dataStruct -> Optional.of(dataStruct).map(fieldExtractor).orElse(null))
				       .build();
	}

	public static <R> FieldValidationRule<R, R> data() {
		return FieldValidationRule
				       .<R, R>builder()
				       .extractor(dataStruct -> Optional.of(dataStruct).orElse(null))
				       .build();
	}

	public FieldValidationRule<R, T> elseFail(FailureBuilderClause<T> failureDelta) {
		return this.toBuilder().failureCreator(failureDelta).build();
	}

	public FieldValidationRule<R, T> andTo(Function<T, Boolean> toAssert) {
		return compliesTo(toAssert);
	}

	public FieldValidationRule<R, T> compliesTo(Function<T, Boolean> toAssert) {
		return this.toBuilder().fieldAssertion(toAssert).build();
	}

	@Override
	public Function<R, Boolean> getAssertion() {
		return data -> this.fieldAssertion.apply(this.extractor.apply(data));
	}

	@Override
	public Function<R, Function<FailureBuilder, FailureBuilder>> getFailureCreator() {
		return data -> failureCreator.apply(this.extractor.apply(data));
	}
}