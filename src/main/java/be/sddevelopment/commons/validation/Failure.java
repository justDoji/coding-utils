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
import java.util.function.UnaryOperator;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * <p>
 * <a href="https://deviq.com/value-object/" target="_blank">Value Object</a> used to return issues
 * found when evaluating {@link be.sddevelopment.commons.validation.Fallible} assertions. <br />
 * Depending on the {@link be.sddevelopment.commons.validation.Severity} of this object, the author
 * can choose to take the appropriate action (e.g. recover, hard fail, skip execution, ...).
 * </p>
 *
 * @author <a href="https://github.com/stijn-dejongh" target="_blank">Stijn Dejongh</a>
 * @version 1.0.0
 * @created 2020/10/17
 * @since 1.0.0
 */
@Getter
@EqualsAndHashCode
@SuppressWarnings("FieldMayBeFinal")
public class Failure {

  private Severity severity;
  private String errorCode;
  private String reason;

  private Failure(Severity severity, String errorCode, String reason) {
    this.severity = severity;
    this.errorCode = errorCode;
    this.reason = reason;
  }

  /**
   * <p>failure.</p>
   *
   * @return a {@link be.sddevelopment.commons.validation.Failure.FailureBuilder} object.
   */
  public static FailureBuilder failure() {
    return new FailureBuilder();
  }

  public static class FailureBuilder {

    private Severity severity = Severity.defaultVal();
    private String errorCode;
    private String reason;
    private Function<String, String> reasonCreator = Function.identity();

    public FailureBuilder severity(Severity severity) {
      this.severity = severity;
      return this;
    }

    public FailureBuilder errorCode(String errorCode) {
      this.errorCode = errorCode;
      return this;
    }

    public FailureBuilder reason(String reason) {
      this.reason = reason;
      return this;
    }

    public FailureBuilder reasonCreator(
        UnaryOperator<String> reasonCreator) {
      this.reasonCreator = reasonCreator;
      return this;
    }

    public Failure build() {
      return new Failure(this.severity, this.errorCode, this.reasonCreator.apply(this.reason));
    }
  }

}
