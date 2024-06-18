/*
*    ------ BEGIN LICENSE ATTRIBUTION ------
*    
*    Portions of this file have been appropriated or derived from the following project(s) and therefore require attribution to the original licenses and authors.
*    
*    Release: https://github.com/spring-projects/spring-authorization-server/releases/tag/1.2.3
*    Source File: OAuth2TokenCustomizer.java
*    
*    Copyrights:
*      copyright 2020-2022 the original author or authors
*    
*    Licenses:
*      Apache License 2.0
*      SPDXId: Apache-2.0
*    
*    Auto-attribution by Threatrix, Inc.
*    
*    ------ END LICENSE ATTRIBUTION ------
*/
/*
 * Copyright 2020-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.security.oauth2.server.authorization.token;

/**
 * Implementations of this interface are responsible for customizing the
 * OAuth 2.0 Token attributes contained within the {@link OAuth2TokenContext}.
 *
 * @author Joe Grandja
 * @since 0.1.0
 * @see OAuth2TokenContext
 * @param <T> the type of the context containing the OAuth 2.0 Token attributes
 */
@FunctionalInterface
public interface OAuth2TokenCustomizer<T extends OAuth2TokenContext> {

	/**
	 * Customize the OAuth 2.0 Token attributes.
	 *
	 * @param context the context containing the OAuth 2.0 Token attributes
	 */
	void customize(T context);

}
