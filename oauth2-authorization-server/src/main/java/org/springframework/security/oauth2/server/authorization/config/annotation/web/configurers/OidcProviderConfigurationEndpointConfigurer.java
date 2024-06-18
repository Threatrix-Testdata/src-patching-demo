/*
*    ------ BEGIN LICENSE ATTRIBUTION ------
*    
*    Portions of this file have been appropriated or derived from the following project(s) and therefore require attribution to the original licenses and authors.
*    
*    Release: https://github.com/spring-projects/spring-authorization-server/releases/tag/1.2.3
*    Source File: OidcProviderConfigurationEndpointConfigurer.java
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
package org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers;

import java.util.function.Consumer;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.authorization.oidc.OidcProviderConfiguration;
import org.springframework.security.oauth2.server.authorization.oidc.web.OidcProviderConfigurationEndpointFilter;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * Configurer for the OpenID Connect 1.0 Provider Configuration Endpoint.
 *
 * @author Joe Grandja
 * @since 0.4.0
 * @see OidcConfigurer#providerConfigurationEndpoint
 * @see OidcProviderConfigurationEndpointFilter
 */
public final class OidcProviderConfigurationEndpointConfigurer extends AbstractOAuth2Configurer {
	private RequestMatcher requestMatcher;
	private Consumer<OidcProviderConfiguration.Builder> providerConfigurationCustomizer;
	private Consumer<OidcProviderConfiguration.Builder> defaultProviderConfigurationCustomizer;

	/**
	 * Restrict for internal use only.
	 */
	OidcProviderConfigurationEndpointConfigurer(ObjectPostProcessor<Object> objectPostProcessor) {
		super(objectPostProcessor);
	}

	/**
	 * Sets the {@code Consumer} providing access to the {@link OidcProviderConfiguration.Builder}
	 * allowing the ability to customize the claims of the OpenID Provider's configuration.
	 *
	 * @param providerConfigurationCustomizer the {@code Consumer} providing access to the {@link OidcProviderConfiguration.Builder}
	 * @return the {@link OidcProviderConfigurationEndpointConfigurer} for further configuration
	 */
	public OidcProviderConfigurationEndpointConfigurer providerConfigurationCustomizer(
			Consumer<OidcProviderConfiguration.Builder> providerConfigurationCustomizer) {
		this.providerConfigurationCustomizer = providerConfigurationCustomizer;
		return this;
	}

	void addDefaultProviderConfigurationCustomizer(
			Consumer<OidcProviderConfiguration.Builder> defaultProviderConfigurationCustomizer) {
		this.defaultProviderConfigurationCustomizer =
				this.defaultProviderConfigurationCustomizer == null ?
						defaultProviderConfigurationCustomizer :
						this.defaultProviderConfigurationCustomizer.andThen(defaultProviderConfigurationCustomizer);
	}

	@Override
	void init(HttpSecurity httpSecurity) {
		this.requestMatcher = new AntPathRequestMatcher(
				"/.well-known/openid-configuration", HttpMethod.GET.name());
	}

	@Override
	void configure(HttpSecurity httpSecurity) {
		OidcProviderConfigurationEndpointFilter oidcProviderConfigurationEndpointFilter =
				new OidcProviderConfigurationEndpointFilter();
		Consumer<OidcProviderConfiguration.Builder> providerConfigurationCustomizer = getProviderConfigurationCustomizer();
		if (providerConfigurationCustomizer != null) {
			oidcProviderConfigurationEndpointFilter.setProviderConfigurationCustomizer(providerConfigurationCustomizer);
		}
		httpSecurity.addFilterBefore(postProcess(oidcProviderConfigurationEndpointFilter), AbstractPreAuthenticatedProcessingFilter.class);
	}

	private Consumer<OidcProviderConfiguration.Builder> getProviderConfigurationCustomizer() {
		Consumer<OidcProviderConfiguration.Builder> providerConfigurationCustomizer = null;
		if (this.defaultProviderConfigurationCustomizer != null || this.providerConfigurationCustomizer != null) {
			if (this.defaultProviderConfigurationCustomizer != null) {
				providerConfigurationCustomizer = this.defaultProviderConfigurationCustomizer;
			}
			if (this.providerConfigurationCustomizer != null) {
				providerConfigurationCustomizer =
						providerConfigurationCustomizer == null ?
								this.providerConfigurationCustomizer :
								providerConfigurationCustomizer.andThen(this.providerConfigurationCustomizer);
			}
		}
		return providerConfigurationCustomizer;
	}

	@Override
	RequestMatcher getRequestMatcher() {
		return this.requestMatcher;
	}

}
