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

package be.sddevelopment.commons.testing;

import java.lang.reflect.Method;

import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;

/**
 * <p>Description of file/class</p>
 *
 * @author <a href="https://github.com/stijn-dejongh" target="_blank">Stijn Dejongh</a>
 * @version $Id: $Id
 * @created 18.10.20, Sunday
 */
public class ReplaceUnderscoredCamelCasing extends ReplaceUnderscores {

	/**
	 * <p>Constructor for ReplaceUnderscoredCamelCasing.</p>
	 */
	public ReplaceUnderscoredCamelCasing() {
		super();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String generateDisplayNameForClass(Class<?> testClass) {
		return this.replaceCapitals(super.generateDisplayNameForClass(testClass));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String generateDisplayNameForNestedClass(Class<?> nestedClass) {
		return this.replaceCapitals(super.generateDisplayNameForNestedClass(nestedClass));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String generateDisplayNameForMethod(Class<?> testClass, Method testMethod) {
		return this.replaceCapitals(super.generateDisplayNameForMethod(testClass, testMethod));
	}

	private String replaceCapitals(String name) {
		name = name.replaceAll("([A-Z])", " $1").toLowerCase();
		name = name.replaceAll("([0-9]+)", " $1");
		name = name.replace("given", "GIVEN");
		name = name.replace("when", "WHEN");
		name = name.replace("then", "THEN");
		name = name.replace("and", "AND");
		return name;
	}

}
