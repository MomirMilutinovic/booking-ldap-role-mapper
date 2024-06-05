package com.lunark.lunark.auth.provider.mapper;

/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates
 * and other contributors as indicated by the @author tags.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.ArrayList;
import java.util.List;

import org.keycloak.component.ComponentModel;
import org.keycloak.component.ComponentValidationException;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.provider.ConfigurationValidationHelper;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.storage.ldap.LDAPStorageProvider;
import org.keycloak.storage.ldap.mappers.AbstractLDAPStorageMapper;
import org.keycloak.storage.ldap.mappers.AbstractLDAPStorageMapperFactory;

public class UUIDRegistrationLDAPAttributeMapperFactory extends AbstractLDAPStorageMapperFactory {

    public static final String PROVIDER_ID = "uuid-registration-mapper";

    protected static final List<ProviderConfigProperty> configProperties = new ArrayList<ProviderConfigProperty>();

    static {
        ProviderConfigProperty attrName = createConfigProperty(UUIDRegistrationLDAPAttributeMapper.LDAP_ATTRIBUTE_NAME,
                "LDAP ID Attribute Name",
                "Name of the LDAP ID attribute, which will be added to the new user during registration",
                ProviderConfigProperty.STRING_TYPE,
                null,
                true);

        configProperties.add(attrName);
    }

    @Override
    public String getHelpText() {
        return "This mapper is supported just if syncRegistrations is enabled. When new user is registered in Keycloak, he will be written to the LDAP with the hardcoded value of some specified attribute.";
    }

    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
        return configProperties;
    }

    @Override
    public String getId() {
        return PROVIDER_ID;
    }

    @Override
    public void validateConfiguration(KeycloakSession session, RealmModel realm, ComponentModel config) throws ComponentValidationException {
        ConfigurationValidationHelper.check(config)
                .checkRequired(UUIDRegistrationLDAPAttributeMapper.LDAP_ATTRIBUTE_NAME, "LDAP Attribute Name");
    }

    @Override
    protected AbstractLDAPStorageMapper createMapper(ComponentModel mapperModel, LDAPStorageProvider federationProvider) {
        return new UUIDRegistrationLDAPAttributeMapper(mapperModel, federationProvider);
    }


}