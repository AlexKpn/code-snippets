package java.test.utils.keycloak;

import org.junit.jupiter.api.Assertions;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import javax.ws.rs.core.Response;
import java.util.List;

//todo: add links for dependencies

public final class KeycloakTestUtils {

    private KeycloakTestUtils() {
        throw new IllegalStateException("You should not instantiate Utility class");
    }

    public static class GroupsUtils {
        public static Long getAmount(final String realm, final Keycloak adminClient) {
            return adminClient.realm(realm).groups().count().get("count");
        }

        public static void create(final String groupName, final String realm, final Keycloak adminClient) {
            final GroupRepresentation group = new GroupRepresentation();
            group.setName(groupName);

            final Response response = adminClient.realm(realm).groups().add(group);

            response.close();
        }

        public static GroupRepresentation get(final String groupName, final String realm, final Keycloak adminClient) {
            return adminClient.realm(realm)
                    .groups()
                    .groups(groupName, 0, 1)
                    .stream()
                    .findFirst()
                    .get(); //we expect that group exists
        }

        public static String getId(final String groupName, final String realm, final Keycloak adminClient) {
            return get(groupName, realm, adminClient).getId();
        }

        public static Long getAmountOfMembers(final String groupName, final String realm, final Keycloak adminClient) {
            return (long) getMembers(groupName, realm, adminClient).size();
        }

        public static List<UserRepresentation> getMembers(final String groupName, final String realm, final Keycloak adminClient) {
            return adminClient.realm(realm).groups()
                    .group(getId(groupName, realm, adminClient))
                    .members();
        }

        public static void delete(final String id, final String realm, final Keycloak adminClient) {
            adminClient.realm(realm).groups().group(id).remove();
        }

        public static void deleteAll(final String realm, final Keycloak adminClient) {
            adminClient.realm(realm).groups().groups()
                    .forEach(group -> delete(group.getId(), realm, adminClient));
        }

        public static void verifyAmount(final Long expected, final String realm, final Keycloak keycloakAdminClient) {
            Assertions.assertEquals(expected, getAmount(realm, keycloakAdminClient));
        }

        public static void verifyAmountOfMembers(final Long expected, final String groupName, final String realm, final Keycloak keycloakAdminClient) {
            Assertions.assertEquals(expected, getAmountOfMembers(groupName, realm, keycloakAdminClient));
        }
    }

    public static class UserUtils {

        public static String getId(final String userName, final String realm, final Keycloak adminClient) {
            return get(userName, realm, adminClient).getId();
        }

        public static UserRepresentation get(final String userName, final String realm, final Keycloak adminClient) {
            return adminClient.realm(realm)
                    .users()
                    .search(userName)
                    .stream()
                    .findFirst()
                    .get(); //we expect that user exists
        }


        public static void delete(final String id, final String realm, final Keycloak adminClient) {
            adminClient.realm(realm).users().get(id).remove();
        }

        public static void deleteAll(final String realm, final Keycloak adminClient) {
            adminClient.realm(realm).users().list()
                    .stream()
                    .filter(userRepresentation -> !userRepresentation.getUsername().equals("admin"))
                    .forEach(user -> delete(user.getId(), realm, adminClient));
        }

        public static Integer getAmount(final String realm, final Keycloak adminClient) {
            return adminClient.realm(realm).users().count();
        }

        public static void verifyAmount(final Integer expected, final String realm, final Keycloak keycloakAdminClient) {
            Assertions.assertEquals(expected, UserUtils.getAmount(realm, keycloakAdminClient));
        }

        public static void create(final String userName, final String realm, final Keycloak adminClient) {
            final UserRepresentation user = new UserRepresentation();
            user.setUsername(userName);

            final Response response = adminClient.realm(realm).users().create(user);

            response.close();
        }
    }

}
