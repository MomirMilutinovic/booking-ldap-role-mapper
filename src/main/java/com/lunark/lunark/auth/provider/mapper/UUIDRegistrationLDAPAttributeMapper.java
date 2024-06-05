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

import org.jboss.logging.Logger;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserModel;
import org.keycloak.storage.ldap.LDAPStorageProvider;
import org.keycloak.storage.ldap.idm.model.LDAPObject;
import org.keycloak.storage.ldap.idm.query.internal.LDAPQuery;
import org.keycloak.storage.ldap.mappers.AbstractLDAPStorageMapper;

import java.util.Collections;

public class UUIDRegistrationLDAPAttributeMapper extends AbstractLDAPStorageMapper {

    private static final Logger logger = Logger.getLogger(UUIDRegistrationLDAPAttributeMapper.class);

    public static final String LDAP_ATTRIBUTE_NAME = "ldap.attribute.name";

    public UUIDRegistrationLDAPAttributeMapper(ComponentModel mapperModel, LDAPStorageProvider ldapProvider) {
        super(mapperModel, ldapProvider);
    }


    @Override
    public void onRegisterUserToLDAP(LDAPObject ldapUser, UserModel localUser, RealmModel realm) {
        String ldapAttrName = mapperModel.get(LDAP_ATTRIBUTE_NAME);
        ldapUser.setAttribute(ldapAttrName, Collections.singleton(localUser.getId()));
    }


    @Override
    public void onImportUserFromLDAP(LDAPObject ldapUser, UserModel user, RealmModel realm, boolean isCreate) {

    }

    @Override
    public UserModel proxy(LDAPObject ldapUser, UserModel delegate, RealmModel realm) {
        String ldapAttrName = mapperModel.get(LDAP_ATTRIBUTE_NAME);
        if (ldapUser.getAttributeAsString(LDAP_ATTRIBUTE_NAME) == null) {
            ldapUser.setAttribute(ldapAttrName, Collections.singleton(delegate.getId()));
        }
        ldapUser.addReadOnlyAttributeName(ldapAttrName);
        return delegate;
    }

    @Override
    public void beforeLDAPQuery(LDAPQuery query) {
        query.addReturningLdapAttribute(LDAP_ATTRIBUTE_NAME);
    }
}