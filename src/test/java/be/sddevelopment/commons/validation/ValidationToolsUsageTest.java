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

package be.sddevelopment.commons.validation;

import static be.sddevelopment.commons.validation.FieldValidationRule.*;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;

import be.sddevelopment.commons.testing.ReplaceUnderscoredCamelCasing;
import lombok.Builder;
import lombok.Value;

/**
 * These tests are meant to check, and illustrate, the usage of the validation library.
 * As the library aims to make it easier for developers to write reusable, encapsulated validation code inside their projects,
 * the look and feel of client code should be tested.
 *
 */
@DisplayNameGeneration(ReplaceUnderscoredCamelCasing.class)
@DisplayName("Tests to illustrate usage of the Validation library")
public class ValidationToolsUsageTest {

	@Test
	void asADeveloper_givenADataClass_IWantToValidateItsContentsAndDisplayErrors() {
		EmailContact toValidate = EmailContact.builder()
				.email("Invalid Format")
				.name("Bob")
				.lastName("The Buider")
				.userIdentifier(UUID.randomUUID())
				.build();

		Fallible<EmailContact> toBeValid = Fallible.of(toValidate)
				.ensure(emailContact -> Optional.of(emailContact)
								.map(EmailContact::getEmail)
								.map(mail -> StringUtils.containsIgnoreCase("invalid", mail))
								.orElse(false),
						emailContact -> failureBuilder -> failureBuilder
								.errorCode("ERROR")
								.reason("email should not be null or contain invalid atoms")
								.severity(Severity.ERROR)
				);

//		 I Would rather write this as ensure(field(EmailContact::getEmail)
//		 .compliesTo(Objects::nonNull)
// 		 .and(mail -> StringUtils.containsIgnoreCase("invalid", mail))
//		 .elseFail(withCode("123").andReason("email should not be null").andSeverity(CRITICAL)))

		Fallible<EmailContact> redesigned = Fallible.of(toValidate)
				.ensure(field(EmailContact::getEmail)
						.compliesTo(Objects::nonNull)
						.compliesTo(mail -> StringUtils.containsIgnoreCase("invalid", mail))
						.elseFail(withReason("email should not be null"))
				);

		Assertions.assertThat(toBeValid.isValid()).isFalse();
		Assertions.assertThat(toBeValid.failures())
				.extracting(Failure::getErrorCode, Failure::getReason, Failure::getSeverity)
				.containsExactlyInAnyOrder(Tuple.tuple("ERROR", "email should not be null or contain invalid atoms", Severity.ERROR));

		Assertions.assertThat(redesigned.isValid()).isFalse();
		Assertions.assertThat(redesigned.failures())
				.extracting(Failure::getErrorCode, Failure::getReason, Failure::getSeverity)
				.containsExactlyInAnyOrder(Tuple.tuple("ERROR", "email should not be null or contain invalid atoms", Severity.ERROR));
	}

	private <T> Function<T, Function<Failure.FailureBuilder, Failure.FailureBuilder>> withCode(String errorCode) {
		return t -> failureBuilder -> failureBuilder.errorCode(errorCode);
	}

	@Value
	@Builder(toBuilder = true)
	private static class EmailContact {
		UUID userIdentifier;
		String email;
		String name;
		String lastName;
	}
}
