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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * <p>Summary.</p>
 *
 * @author <a href="https://github.com/justDoji" target="_blank">Stijn Dejongh</a>
 * @version 1.0.0
 * @since 1.0.0
 * @created 2020/10/17
 */
public class Fallible<T> {

  private final T data;
  private List<Failure> failures = new ArrayList<>();

  private Fallible(T toValidate) {
    this.data = toValidate;
  }

  /**
   * <p>
   *   Create a {@link be.sddevelopment.utils.validation.Fallible} for an {@link Object} of type S.
   * </p>
   *
   *
   * @param toValidate the data object to execute conditional operations on.
   * @param <S>        class of the data object, will be inferred at creation time.
   * @return a {@link be.sddevelopment.utils.validation.Fallible} for a data object of class S.
   */
  public static <S> Fallible<S> of(S toValidate) {
    return new Fallible<S>(toValidate);
  }

  /**
   * <p>ensure.</p>
   *
   * @param toValidate a {@link java.util.function.Function} object taking an object of type T as an input, and returning a {@link List} of {@link Failure} objects.
   * @return a {@link be.sddevelopment.utils.validation.Fallible} object for further chaining.
   */
  public Fallible<T> ensure(Function<T, List<Failure>> toValidate) {
    this.failures.addAll(toValidate.apply(data));
    return this;
  }

  /**
   * <p>Get the {@link Failure} objects resulting from applying all required rules to the data object.</p>
   *
   * @return a {@link java.util.List} containing {@link Failure} objects.
   */
  public List<Failure> failures() {
    return new ArrayList<>(failures);
  }
}
