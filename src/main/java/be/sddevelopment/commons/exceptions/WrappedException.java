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

package be.sddevelopment.commons.exceptions;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * Description of file/class
 * </p>
 *
 * <h6>Example usage</h6>
 * <pre>
 *  <code>
 *    // No example available yet
 *  </code>
 * </pre>
 *
 * <h6>References</h6>
 *
 * @author <a href="https://github.com/stijn-dejongh" target="_blank">Stijn Dejongh</a>
 * @version 1.0.0
 * @created 01.11.20, Sunday
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public class WrappedException extends RuntimeException {

	private final Exception originalException;

	public WrappedException(String message, Exception cause) {
		super(message, cause);
		this.originalException = cause;
	}

	@Override
	public String getMessage() {
		return this.originalException.getMessage();
	}

	@Override
	public synchronized Throwable getCause() {
		Throwable cause = this.originalException.getCause();
		return Objects.isNull(cause) ? this.originalException : cause;
	}

	@Override
	public StackTraceElement[] getStackTrace() {
		return this.originalException.getStackTrace();
	}

}
