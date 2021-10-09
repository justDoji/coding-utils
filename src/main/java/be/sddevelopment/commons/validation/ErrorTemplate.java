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

import java.util.function.Function;

import be.sddevelopment.commons.validation.Failure.FailureBuilder;
import lombok.AllArgsConstructor;

/**
 * <p>Description of file/class</p>
 *
 * <h6>Usage examples</h6>
 * <pre>
 *   <code>
 *      List<Failure> failures = Fallible.of("")
 *           .errorTemplate(template(s -> "The string [" + s + "] does not match rule: {%s}"))
 *           .ensure(StringUtils::isNotBlank, s -> b -> b.reason("Input can not be blank"))
 *           .failures();
 *   </code>
 *  </pre>
 *
 * @author <a href="https://github.com/stijn-dejongh" target="_blank">Stijn Dejongh</a>
 * @version 1.0.0
 * @created : 18.10.20, Sunday
 * @since 1.0.0
 */
@AllArgsConstructor
public class ErrorTemplate<T> {

	private final Function<T, String> template;

	/**
	 * <p>template.</p>
	 *
	 * @param templateCreator a {@link java.util.function.Function} object.
	 * @param <S>             a S object.
	 * @return a {@link be.sddevelopment.commons.validation.ErrorTemplate} object.
	 */
	public static <S> ErrorTemplate<S> template(Function<S, String> templateCreator) {
		return new ErrorTemplate<>(templateCreator);
	}

	/**
	 * <p>template.</p>
	 *
	 * @param <S> a S object.
	 * @return a {@link be.sddevelopment.commons.validation.ErrorTemplate} object.
	 */
	public static <S> ErrorTemplate<S> template() {
		return new ErrorTemplate<>(Object::toString);
	}

	/**
	 * <p>failure.</p>
	 *
	 * @param data a T object.
	 * @return a {@link be.sddevelopment.commons.validation.Failure.FailureBuilder} object.
	 */
	public FailureBuilder failure(T data) {
		return Failure.failure()
				.reasonCreator(message -> String.format(this.template.apply(data), message));
	}
}
