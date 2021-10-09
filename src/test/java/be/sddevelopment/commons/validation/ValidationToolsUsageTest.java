package be.sddevelopment.commons.validation;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.assertj.core.api.Assertions;
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
				.ensure(emailContact -> Optional.of(emailContact).map(EmailContact::getEmail).map(Objects::nonNull).orElse(false),
						emailContact -> failureBuilder -> failureBuilder.errorCode("ERROR").reason("email should not be null"));

		// I Would rather write this as ensure(field(EmailContact::getEmail)
		// .compliesTo(Objects::nonNull)
		// .otherwise(fail().withCode("123").andReason("email should not be null").andSeverity(CRITICAL)))

		Assertions.assertThat(toBeValid.isValid()).isTrue();
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
