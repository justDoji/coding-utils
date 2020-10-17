package be.sddevelopment.utils.validation;

/*-
 * #%L
 * code-utils
 * $Id:$
 * $HeadURL:$
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

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * <p>Failure class.</p>
 *
 * @author doji
 * @version $Id: $Id
 */
@Getter
@Builder(builderMethodName = "failure", builderClassName = "FailureBuilder")
@EqualsAndHashCode
public class Failure {

  private Severity severity;
  private String errorCode;
  private String reason;

}
