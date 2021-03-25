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

/**
 * <p>
 * When working with external/JDK libraries, the checked exceptions they throw
 * sometimes prevent you from writing the code you want.
 * The most common strategies of dealing with unwanted checked exceptions are:
 * - ignoring them
 * - converting them to unchecked exceptions
 * - converting the return type to an Optional.empty()
 * - handling them with an alternative flow / logging
 * </p>
 *
 * @author <a href="https://github.com/stijn-dejongh" target="_blank">Stijn Dejongh</a>
 * @created 31.10.20, Saturday
 * @since 1.0.0
 **/
package be.sddevelopment.commons.exceptions;
