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

import lombok.Getter;

/**
 * <p>
 *   Enumeration indicating the severity of a {@link be.sddevelopment.utils.validation.Failure}.
 *   A severity can be either blocking or non-blocking.
 * </p>
 *
 * @author <a href="https://github.com/justDoji" target="_blank">Stijn Dejongh</a>
 * @version 1.0.0
 * @since 1.0.0
 * @created 2020/10/17
 */
@SuppressWarnings("ALL")
public enum Severity {

  INFO(false),
  WARNING(false),
  ERROR(true);

  @Getter
  private boolean blocking;

  Severity(boolean blocking) {
    this.blocking = blocking;
  }

  public static Severity defaultVal() {
    return WARNING;
  }

}
