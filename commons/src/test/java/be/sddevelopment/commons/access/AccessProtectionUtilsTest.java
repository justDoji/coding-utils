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

import static be.sddevelopment.commons.testing.ReflectionAssertionUtils.assertPrivateMemberReflectionProtection;

import be.sddevelopment.commons.testing.naming.ReplaceUnderscoredCamelCasing;
import java.lang.reflect.Constructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;

/**
 * <p>Shortcodes for class access restrictions and common error codes</p>
 *
 * @author <a href="https://github.com/stijn-dejongh" target="_blank">Stijn Dejongh</a>
 * @version $Id: $Id
 * @created 18.10.20, Sunday
 */
@DisplayName("Test for Access Protection Utilities")
@DisplayNameGeneration(ReplaceUnderscoredCamelCasing.class)
class AccessProtectionUtilsTest {

	/**
	 * @created: 27/02/2021
	 * @reasoning: a private constructor can still be called using Java's Reflection API.
	 */
	@Test
	void isAUtilityClass() throws NoSuchMethodException {
		Constructor<?> constructor = AccessProtectionUtils.class.getDeclaredConstructor();

		assertPrivateMemberReflectionProtection(constructor);
	}

	@Test
	void constantsAreAUtilityClass() throws NoSuchMethodException {
		Constructor<?> constructor = AccessProtectionUtils.AccessProtectionConstants.class.getDeclaredConstructor();

		assertPrivateMemberReflectionProtection(constructor);
	}

}
