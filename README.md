# booking-ldap-role-mapper

An implementation of [Keycloak](github.com/keycloak/keycloak)'s LDAPStorageMapper that maps a LDAP attribute to a Keycloak role.

This mapper is based on [HardcodedLdapRoleStorageMapper](https://github.com/keycloak/keycloak/blob/main/federation/ldap/src/main/java/org/keycloak/storage/ldap/mappers/HardcodedLDAPRoleStorageMapper.java).

The name of the LDAP role attribute and the roles the attribute maps to are hardcoded.