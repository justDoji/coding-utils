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

package be.sddevelopment.commons;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.reflections.Reflections;

import be.sddevelopment.commons.access.Utility;
import be.sddevelopment.commons.testing.ReflectionAssertionUtils;

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
class CodeInvariantTests {

	@Test
	void givenMyCodeBase_allUtilityClasses_shouldHaveASafeguardedPrivateConstructor() {
		Reflections reflections = new Reflections("be.sddevelopment.commons");
		Set<Class<?>> classesToTest = reflections.getTypesAnnotatedWith(Utility.class);

		List<Constructor<?>> constructorsToTest = classesToTest.stream()
				.map(Class::getDeclaredConstructors)
				.flatMap(Arrays::stream)
				.filter(constructor -> !constructor.isAccessible())
				.collect(toList());

		assertThat(constructorsToTest).isNotEmpty()
				.allSatisfy(ReflectionAssertionUtils::assertPrivateMember)
				.allSatisfy(ReflectionAssertionUtils::assertPrivateMemberReflectionProtection);

	}

}
