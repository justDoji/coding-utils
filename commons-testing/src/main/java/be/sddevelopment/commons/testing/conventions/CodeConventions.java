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

package be.sddevelopment.commons.testing.conventions;

import static com.tngtech.archunit.lang.SimpleConditionEvent.satisfied;
import static com.tngtech.archunit.lang.SimpleConditionEvent.violated;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_USE_FIELD_INJECTION;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING;
import static com.tngtech.archunit.library.GeneralCodingRules.NO_CLASSES_SHOULD_USE_JODATIME;
import static org.junit.platform.commons.util.ReflectionUtils.makeAccessible;

import be.sddevelopment.commons.annotations.Constants;
import be.sddevelopment.commons.annotations.Utility;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaConstructor;
import com.tngtech.archunit.core.domain.JavaModifier;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.syntax.elements.GivenClassesConjunction;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;


@DisplayName("Complies with code conventions")
public interface CodeConventions {

	@ArchTest
	ArchRule CLASSES_DO_NOT_ACCESS_STANDARD_STREAMS = NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS;

	@ArchTest
	ArchRule NO_CLASSES_USE_FIELD_INJECTION = NO_CLASSES_SHOULD_USE_FIELD_INJECTION;

	@ArchTest
	ArchRule NO_GENERIC_EXCEPTION = NO_CLASSES_SHOULD_THROW_GENERIC_EXCEPTIONS;

	@ArchTest
	ArchRule NO_JAVA_UTIL_LOGGING = NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING;

	@ArchTest
	ArchRule NO_JODATIME = NO_CLASSES_SHOULD_USE_JODATIME;

	@ArchTest
	ArchRule UTILITY_CLASSES_CAN_NOT_BE_INSTANTIATED = utilityClasses()
			                                                   .should(notBeInstantiatable())
			                                                   .because(
					                                                   "Utility classes should not be instantiated")
			                                                   .allowEmptyShould(true);

	@ArchTest
	ArchRule UTILITY_CLASSES_ARE_FINAL = utilityClasses()
			                                     .should()
			                                     .haveModifier(JavaModifier.FINAL)
			                                     .because("Utility classes should be final")
			                                     .allowEmptyShould(true);

	private static GivenClassesConjunction utilityClasses() {
		return classes().that().areAnnotatedWith(Utility.class).and().areNotAnnotations();
	}

	private static GivenClassesConjunction constantClasses() {
		return classes().that().areAnnotatedWith(Constants.class).and().areNotAnnotations();
	}

	static ArchCondition<JavaClass> notBeInstantiatable() {
		return new ArchCondition<>("not be instantiatable") {
			@Override
			public void check(JavaClass item, ConditionEvents events) {
				if (isInstantiatable(item)) {
					events.add(violated(item, "Class " + item.getName() + " is instantiatable"));
				} else {
					events.add(satisfied(item, "Class " + item.getName() + " is not instantiatable"));
				}
			}
		};
	}

	static boolean isInstantiatable(JavaClass classToCheck) {

		try {
			if (classToCheck
					    .getConstructors()
					    .stream()
					    .map(JavaConstructor::getParameters)
					    .anyMatch(List::isEmpty)) {
				var constructor = classToCheck.getConstructor().reflect();
				makeAccessible(constructor);
				constructor.newInstance();
				return true;
			} else {
				return false;
			}
		} catch (UnsupportedOperationException | InstantiationException | IllegalAccessException |
		         InvocationTargetException e) {
			return false;
		}
	}

}
