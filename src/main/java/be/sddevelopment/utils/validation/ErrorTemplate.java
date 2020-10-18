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
import java.util.function.Function;
import lombok.AllArgsConstructor;

/**
 * <p>Description of file/class</p>
 *
 * @author : <a href="https://github.com/justDoji" target="_blank">Stijn Dejongh</a>
 * @created : 18.10.20, Sunday
 **/
@AllArgsConstructor
public class ErrorTemplate<T> {

  private final Function<T, String> template;

  public static <S> ErrorTemplate<S> template(Function<S, String> templateCreator) {
    return new ErrorTemplate<>(templateCreator);
  }

  public static <S> ErrorTemplate<S> template() {
    return new ErrorTemplate<>(Object::toString);
  }

  public FailureBuilder failure(T data) {
    return Failure.failure().reasonCreator(message -> String.format(this.template.apply(data), message));
  }
}
