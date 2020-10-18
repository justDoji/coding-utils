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

package be.sddevelopment.utils.validation;

import be.sddevelopment.utils.validation.Failure.FailureBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>Summary.</p>
 *
 * @author <a href="https://github.com/justDoji" target="_blank">Stijn Dejongh</a>
 * @version 1.0.0
 * @created 2020/10/17
 * @since 1.0.0
 */
public class Fallible<T> {

  private static final String DEFAULT_REASON = "Assertion error";

  private final T data;
  private final List<ValidationRule<T>> validations = new ArrayList<>();
  private ErrorTemplate<T> errorTemplate = ErrorTemplate.template();
  private List<Failure> failures;

  private Fallible(T toValidate) {
    this.data = toValidate;
  }

  /**
   * <p>
   * Create a {@link be.sddevelopment.utils.validation.Fallible} for an {@link Object} of type S.
   * </p>
   *
   * @param toValidate the data object to execute conditional operations on.
   * @param <S>        class of the data object, will be inferred at creation time.
   * @return a {@link be.sddevelopment.utils.validation.Fallible} for a data object of class S.
   */
  public static <S> Fallible<S> of(S toValidate) {
    return new Fallible<>(toValidate);
  }

  public Fallible<T> ensure(Function<T, Boolean> assertion,
      Function<T, Function<FailureBuilder, FailureBuilder>> error) {
    this.validations.add(new ValidationRule<>(assertion, error));
    return this;
  }

  public Fallible<T> ensure(Function<T, Boolean> assertion) {
    return this.ensure(assertion, d -> (b -> b.reason(DEFAULT_REASON)));
  }

  public Fallible<T> ifValid(Consumer<T> actionToTake) {
    if (isValid()) {
      actionToTake.accept(this.data);
    }
    return this;
  }

  public boolean isValid() {
    return this.failures().isEmpty();
  }

  public Fallible<T> errorTemplate(ErrorTemplate<T> template) {
    this.errorTemplate = template;
    return this;
  }

  /**
   * <p>Get the {@link Failure} objects resulting from applying all required rules to the data
   * object.</p>
   *
   * @return a {@link java.util.List} containing {@link Failure} objects.
   */
  public List<Failure> failures() {
    validate();
    return new ArrayList<>(failures);
  }

  private void validate() {
    if (Objects.isNull(this.failures)) {
      this.failures = validations.stream()
          .filter(this::assertionFailed)
          .map(rule -> rule.getFailureCreator().apply(this.data)
              .apply(errorTemplate.failure(this.data)))
          .map(FailureBuilder::build)
          .collect(Collectors.toList());
    }
  }

  private boolean assertionFailed(ValidationRule<T> a) {
    return !a.getAssertion().apply(this.data);
  }
}
