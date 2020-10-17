package be.sddevelopment.utils.validation;

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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * <p>Fallible class.</p>
 *
 * @author doji
 * @version $Id: $Id
 */
public class Fallible<T> {

  private final T data;
  private List<Failure> failures = new ArrayList<>();

  private Fallible(T toValidate) {
    this.data = toValidate;
  }

  /**
   * <p>of.</p>
   *
   * @param toValidate a S object.
   * @param <S> a S object.
   * @return a {@link be.sddevelopment.utils.validation.Fallible} object.
   */
  public static <S> Fallible<S> of(S toValidate) {
    return new Fallible<S>(toValidate);
  }

  /**
   * <p>ensure.</p>
   *
   * @param toValidate a {@link java.util.function.Function} object.
   * @return a {@link be.sddevelopment.utils.validation.Fallible} object.
   */
  public Fallible<T> ensure(Function<T, List<Failure>> toValidate) {
    this.failures.addAll(toValidate.apply(data));
    return this;
  }

  /**
   * <p>failures.</p>
   *
   * @return a {@link java.util.List} object.
   */
  public List<Failure> failures() {
    return new ArrayList<>(failures);
  }
}
