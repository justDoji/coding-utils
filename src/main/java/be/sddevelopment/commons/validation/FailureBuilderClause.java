package be.sddevelopment.commons.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class FailureBuilderClause<T> implements Function<T, Function<Failure.FailureBuilder, Failure.FailureBuilder>> {

	private final Function<T, Function<Failure.FailureBuilder, Failure.FailureBuilder>> self;
	private final List<FailureBuilderClause<T>> constituents = new ArrayList<>();

	public static <T> FailureBuilderClause<T> of(Function<T, Function<Failure.FailureBuilder, Failure.FailureBuilder>> function) {
		return new FailureBuilderClause<>(function);
	}

	public static <T> FailureBuilderClause<T> withReason(String reason) {
		return of(s -> failureBuilder -> failureBuilder.reason(reason));
	}

	public FailureBuilderClause<T> andReason(String reason) {
		return this.and(withReason(reason));
	}

	public static <T> FailureBuilderClause<T> withCode(String code) {
		return of(s -> failureBuilder -> failureBuilder.errorCode(code));
	}

	public FailureBuilderClause<T> andCode(String code) {
		return this.and(withCode(code));
	}

	public static <T> FailureBuilderClause<T> withSeverity(Severity level) {
		return of(s -> failureBuilder -> failureBuilder.severity(level));
	}

	public FailureBuilderClause<T> andSeverity(Severity level) {
		return this.and(withSeverity(level));
	}

	private FailureBuilderClause(
			Function<T, Function<Failure.FailureBuilder, Failure.FailureBuilder>> self
	) {
		this.self = self;
	}

	@Override
	public Function<Failure.FailureBuilder, Failure.FailureBuilder> apply(T t) {
		return this.self.apply(t).andThen(applyConstituents(t));
	}

	private Function<Failure.FailureBuilder, Failure.FailureBuilder> applyConstituents(T t) {
		return constituents.stream()
				.map(clause -> clause.apply(t))
				.reduce(Function::andThen).orElse(Function.identity());
	}

	private FailureBuilderClause<T> and(FailureBuilderClause<T> toAppend) {
		this.constituents.add(toAppend);
		return this;
	}
}
