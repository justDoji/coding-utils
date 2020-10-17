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

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * <p>
 *   <a href="https://deviq.com/value-object/" target="_blank">Value Object</a> used to return issues found when evaluating {@link be.sddevelopment.utils.validation.Fallible} assertions.
 *   Depening on the {@link be.sddevelopment.utils.validation.Severity} of this object, the author can choose to take the appropriate
 *   action (e.g. recover, hard fail, skip execution, ...).
 * </p>
 *
 * @author <a href="https://github.com/justDoji" target="_blank">Stijn Dejongh</a>
 * @version 1.0.0
 * @since 1.0.0
 * @created 2020/10/17
 */
@Getter
@Builder(builderMethodName = "failure", builderClassName = "FailureBuilder")
@EqualsAndHashCode
public class Failure {

  private final Severity severity;
  private final String errorCode;
  private final String reason;

}
