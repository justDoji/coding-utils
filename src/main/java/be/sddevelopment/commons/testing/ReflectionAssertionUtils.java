/*-
 * #%L
 * code-utils
 * %%
 * Copyright (C) 2020 - 2021 SD Development
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

package be.sddevelopment.commons.testing;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import be.sddevelopment.commons.access.AccessProtectionUtils;
import be.sddevelopment.commons.access.Utility;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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
 * @created 25.03.21, Thursday
 * @since 1.0.0
 */
@Utility
public final class ReflectionAssertionUtils {

	private ReflectionAssertionUtils() {
		AccessProtectionUtils.utilityClassConstructor();
	}

	public static void assertPrivateMemberReflectionProtection(@SuppressWarnings("rawtypes") final Constructor constructor) {
		constructor.setAccessible(true);
		assertThatThrownBy(constructor::newInstance)
				.isInstanceOf(InvocationTargetException.class)
				.hasCauseInstanceOf(UnsupportedOperationException.class)
				.hasStackTraceContaining("This operation is not allowed for reason: [ Utility classes should not have a public or default constructor ]");
	}

	public static void assertPrivateMember(@SuppressWarnings("rawtypes") final Constructor constructor) {
		assertThatThrownBy(constructor::newInstance, format("Constructor %s is expected to be protected from illegal access", constructor)).isInstanceOfAny(InvocationTargetException.class, IllegalAccessException.class);
	}
}
