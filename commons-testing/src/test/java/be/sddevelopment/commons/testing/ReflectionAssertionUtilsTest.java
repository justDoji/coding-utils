/*-
 * #%L
 * commons-testing
 * %%
 * Copyright (C) 2020 - 2024 SD Development
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

import static be.sddevelopment.commons.testing.ReflectionAssertionUtils.assertPrivateMember;
import static be.sddevelopment.commons.testing.ReflectionAssertionUtils.assertPrivateMemberReflectionProtection;

import be.sddevelopment.commons.testing.naming.ReplaceUnderscoredCamelCasing;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(ReplaceUnderscoredCamelCasing.class)
class ReflectionAssertionUtilsTest implements WithAssertions {

	@Test
	void constructorIsPrivate() throws NoSuchMethodException {
		assertPrivateMember(ReflectionAssertionUtils.class.getDeclaredConstructor());
	}

	@Test
	void constructorIsReflectionSafe() throws NoSuchMethodException {
		assertPrivateMemberReflectionProtection(ReflectionAssertionUtils.class.getDeclaredConstructor());
	}

	@Nested
	@DisplayName("Method: assertPrivateMember")
	class AssertPrivateMember {
		@Test
		void passesWhenConstructorIsPrivate() throws NoSuchMethodException {
			var constructorToTest = ClassWithPrivateConstructor.class.getDeclaredConstructor();
			assertThat(constructorToTest).isNotNull().extracting(Constructor::getModifiers).matches(Modifier::isPrivate);

			ThrowingCallable assertion = () -> assertPrivateMember(constructorToTest);

			assertThatCode(assertion).doesNotThrowAnyException();
		}

		@Test
		void failsWhenConstructorIsPublic() throws NoSuchMethodException {
			var constructorToTest = ClassWithPublicConstructor.class.getDeclaredConstructor();
			assertThat(constructorToTest).isNotNull().extracting(Constructor::getModifiers).matches(Modifier::isPublic);

			ThrowingCallable assertion = () -> assertPrivateMember(constructorToTest);

			assertThatCode(assertion).isInstanceOf(AssertionError.class).hasMessageContaining("is expected to be protected from illegal access");
		}
	}

	@Nested
	@DisplayName("Method: assertPrivateMemberReflectionProtection")
	class AssertPrivateMemberReflectionProtection {
		@Test
		void failsWhenConstructorIsPublic() throws NoSuchMethodException {
			var constructorToTest = ClassWithPublicConstructor.class.getDeclaredConstructor();
			assertThat(constructorToTest).isNotNull().extracting(Constructor::getModifiers).matches(Modifier::isPublic);

			ThrowingCallable assertion = () -> assertPrivateMemberReflectionProtection(constructorToTest);

			assertThatCode(assertion).isInstanceOf(AssertionError.class)
			                         .hasMessageContaining("is expected to be protected from illegal access");
		}

		@Test
		void failsWhenConstructorIsPrivateButNotReflectionSafe() throws NoSuchMethodException {
			var constructorToTest = ClassWithPrivateConstructor.class.getDeclaredConstructor();
			assertThat(constructorToTest).isNotNull().extracting(Constructor::getModifiers).matches(Modifier::isPrivate);

			ThrowingCallable assertion = () -> assertPrivateMemberReflectionProtection(constructorToTest);

			assertThatCode(assertion).isNotNull().isInstanceOf(AssertionError.class)
			                         .hasMessageContaining("is expected to be protected from illegal access");
		}

		@Test
		void passesWhenConstructorIsPrivateAndReflectionSafe() throws NoSuchMethodException {
			var constructorToTest = ClassWithReflectionSafeConstructor.class.getDeclaredConstructor();
			assertThat(constructorToTest).isNotNull().extracting(Constructor::getModifiers).matches(Modifier::isPrivate);

			ThrowingCallable assertion = () -> assertPrivateMemberReflectionProtection(constructorToTest);

			assertThatCode(assertion).doesNotThrowAnyException();
		}
	}

	static final class ClassWithReflectionSafeConstructor {
		private ClassWithReflectionSafeConstructor() {
			throw new UnsupportedOperationException("Utility classes should not have a public or default constructor");
		}
	}

	static final class ClassWithPrivateConstructor {
		private ClassWithPrivateConstructor() {
		}
	}

	static final class ClassWithPublicConstructor {
		public ClassWithPublicConstructor() {
			// Intentionally left empty to test the assertion
		}
	}

}
