/*
*    ------ BEGIN LICENSE ATTRIBUTION ------
*    
*    Portions of this file have been appropriated or derived from the following project(s) and therefore require attribution to the original licenses and authors.
*    
*    Release: https://github.com/spring-projects/spring-authorization-server/releases/tag/1.2.3
*    Source File: Context.java
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
package org.springframework.security.oauth2.server.authorization.context;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * A facility for holding information associated to a specific context.
 *
 * @author Joe Grandja
 * @since 0.1.0
 */
public interface Context {

	/**
	 * Returns the value of the attribute associated to the key.
	 *
	 * @param key the key for the attribute
	 * @param <V> the type of the value for the attribute
	 * @return the value of the attribute associated to the key, or {@code null} if not available
	 */
	@Nullable
	<V> V get(Object key);

	/**
	 * Returns the value of the attribute associated to the key.
	 *
	 * @param key the key for the attribute
	 * @param <V> the type of the value for the attribute
	 * @return the value of the attribute associated to the key, or {@code null} if not available or not of the specified type
	 */
	@Nullable
	default <V> V get(Class<V> key) {
		Assert.notNull(key, "key cannot be null");
		V value = get((Object) key);
		return key.isInstance(value) ? value : null;
	}

	/**
	 * Returns {@code true} if an attribute associated to the key exists, {@code false} otherwise.
	 *
	 * @param key the key for the attribute
	 * @return {@code true} if an attribute associated to the key exists, {@code false} otherwise
	 */
	boolean hasKey(Object key);

}
