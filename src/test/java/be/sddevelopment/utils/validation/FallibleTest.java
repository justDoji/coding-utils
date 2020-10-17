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

package be.sddevelopment.utils.validation;

import static be.sddevelopment.utils.validation.Failure.failure;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

class FallibleTest {

  public static final List<String> NAUGHTY_WORDS = Collections.singletonList("string");
  public static final String NAUGHTY_ERROR = "Your words %s, seems to contain a naughty word [%s]";

  @Test
  void aFallible_returnsAListOfErrors_ifAConditionIsNotMet() {
    List<Failure> errors = Fallible.of("StringToCheck")
        .ensure(this::mayNotContainNaughtyWords)
        .failures();

    assertThat(errors).hasSize(1);
  }

  @Test
  void whenAllConditionsAreMet_theReturnedFailures_mustBeEmpty() {
    List<Failure> failures = Fallible.of("This text contains no naught words")
        .ensure(this::mayNotContainNaughtyWords)
        .failures();

    assertThat(failures).isEmpty();
  }

  private List<Failure> mayNotContainNaughtyWords(String toCheck) {
    return NAUGHTY_WORDS.stream()
        .filter(w -> containsIgnoreCase(toCheck, w))
        .map(w -> format(NAUGHTY_ERROR, toCheck, w))
        .map(e -> failure().errorCode("BAD_WORD").reason(e).severity(Severity.ERROR).build())
        .collect(Collectors.toList());
  }

}
