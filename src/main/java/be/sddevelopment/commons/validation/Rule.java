package be.sddevelopment.commons.validation;

public interface Rule<T> {
	java.util.function.Function<T, Boolean> getAssertion();

	java.util.function.Function<T, java.util.function.Function<Failure.FailureBuilder, Failure.FailureBuilder>> getFailureCreator();
}
