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

package be.sddevelopment.commons.access;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import be.sddevelopment.commons.testing.ReflectionAssertionUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * <p>Shortcodes for class access restrictions and common error codes</p>
 *
 * @author <a href="https://github.com/stijn-dejongh" target="_blank">Stijn Dejongh</a>
 * @version $Id: $Id
 * @created 18.10.20, Sunday
 */
class AccessProtectionUtilsTest {

	private Class<?> classToTest;

	@BeforeEach
	void setUp() throws ClassNotFoundException {
		classToTest = utilityClass();
	}

	/**
	 * @created: 27/02/2021
	 * @reasoning: a private constructor can still be called using Java's Reflection API.
	 */
	@Test
	void givenAUtilityClassWithAPrivateDefaultConstructor_whenInstantiatingAndOverwritingAccessModifier_anExceptionIsThrown() {
		ReflectionAssertionUtils
				.assertPrivateMemberReflectionProtection(classToTest.getDeclaredConstructors()[0]);
	}

	@Test
	void givenAUtilityClassWithAPrivateConstructor_whenInstantiating_anIllegalExceptionIsThrown() {
		assertThatThrownBy(() -> classToTest.getDeclaredConstructor().newInstance())
				.isInstanceOf(IllegalAccessException.class);
	}

	private Class<?> utilityClass() throws ClassNotFoundException {
		return Class.forName("be.sddevelopment.commons.access.UtilityClass");
	}

}
