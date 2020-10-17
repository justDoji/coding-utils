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

import lombok.Getter;

/**
 * <p>Severity class.</p>
 *
 * @author doji
 * @version $Id: $Id
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

}
