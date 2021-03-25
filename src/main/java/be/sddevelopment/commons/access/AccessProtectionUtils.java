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

/* ----------------------------------------------------------------------------
 *     PROJECT : EURES
 *
 *     PACKAGE : eu.europa.ec.empl.eures.services.commons.api.core.util
 *        FILE : AccessProtectionUtils.java
 *
 *  CREATED BY : ARHS Developments
 *          ON : Feb 26, 2021
 *
 * MODIFIED BY : ARHS Developments
 *          ON :
 *     VERSION :
 *
 * ----------------------------------------------------------------------------
 * Copyright (c) 2011 European Commission - DG EMPL
 * ----------------------------------------------------------------------------
 */
package be.sddevelopment.commons.access;

import static java.lang.String.format;

/**
 * <p>Shortcodes for class access restrictions and common error codes</p>
 *
 * @author <a href="https://github.com/stijn-dejongh" target="_blank">Stijn Dejongh</a>
 * @version $Id: $Id
 * @created 18.10.20, Sunday
 */
@Utility
public final class AccessProtectionUtils {

    private AccessProtectionUtils() {
        AccessProtectionUtils.utilityClassConstructor();
    }

    public static final class AccessProtectionConstants {
    	
        private AccessProtectionConstants() {
            utilityClassConstructor();
        }

        public static final String OPERATION_NOT_ALLOWED = "This operation is not allowed for reason: [ %1$s ]";
        public static final String UTILITY_CLASS = "Utility classes should not have a public or default constructor";
    }

    public static void utilityClassConstructor() {
        unsupportedOperation(
                format(AccessProtectionConstants.OPERATION_NOT_ALLOWED, AccessProtectionConstants.UTILITY_CLASS));
    }

    public static void unsupportedOperation(String reason) {
        throw new UnsupportedOperationException(format(AccessProtectionConstants.OPERATION_NOT_ALLOWED, reason));
    }

}
