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

package be.sddevelopment.commons.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class FailureBuilderClause<T> implements
                                     Function<T, Function<Failure.FailureBuilder, Failure.FailureBuilder>> {

	private final Function<T, Function<Failure.FailureBuilder, Failure.FailureBuilder>> self;
	private final List<FailureBuilderClause<T>> constituents = new ArrayList<>();

	private FailureBuilderClause(
			Function<T, Function<Failure.FailureBuilder, Failure.FailureBuilder>> self) {
		this.self = self;
	}

	public static <T> FailureBuilderClause<T> of(
			Function<T, Function<Failure.FailureBuilder, Failure.FailureBuilder>> function) {
		return new FailureBuilderClause<>(function);
	}

	public static <T> FailureBuilderClause<T> withReason(String reason) {
		return of(s -> failureBuilder -> failureBuilder.reason(reason));
	}

	public static <T> FailureBuilderClause<T> withCode(String code) {
		return of(s -> failureBuilder -> failureBuilder.errorCode(code));
	}

	public static <T> FailureBuilderClause<T> withSeverity(Severity level) {
		return of(s -> failureBuilder -> failureBuilder.severity(level));
	}

	public FailureBuilderClause<T> andReason(String reason) {
		return this.and(withReason(reason));
	}

	public FailureBuilderClause<T> andCode(String code) {
		return this.and(withCode(code));
	}

	public FailureBuilderClause<T> andSeverity(Severity level) {
		return this.and(withSeverity(level));
	}

	@Override
	public Function<Failure.FailureBuilder, Failure.FailureBuilder> apply(T t) {
		return this.self.apply(t).andThen(applyConstituents(t));
	}

	private Function<Failure.FailureBuilder, Failure.FailureBuilder> applyConstituents(T t) {
		return constituents
				       .stream()
				       .map(clause -> clause.apply(t))
				       .reduce(Function::andThen)
				       .orElse(Function.identity());
	}

	private FailureBuilderClause<T> and(FailureBuilderClause<T> toAppend) {
		this.constituents.add(toAppend);
		return this;
	}
}
