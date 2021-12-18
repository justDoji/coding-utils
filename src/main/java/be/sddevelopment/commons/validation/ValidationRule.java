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
import java.util.function.Function;
import lombok.Value;

/**
 * <p>Description of file/class</p>
 *
 * @author <a href="https://github.com/stijn-dejongh" target="_blank">Stijn Dejongh</a>
 * @version 1.0.0
 */
@Value
public class ValidationRule<T> implements Rule<T> {

	Function<T, Boolean> assertion;
	Function<T, Function<FailureBuilder, FailureBuilder>> failureCreator;

}
