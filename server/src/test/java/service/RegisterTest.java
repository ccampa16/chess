package service;
import dataAccess.AuthDAOMemory;
import dataAccess.Exceptions.DataAccessException;
import dataAccess.UserDAOMemory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import result.RegisterResult;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterTest {
    private RegisterService registerService;

    @BeforeEach
    public void setUp() {
        UserDAOMemory userDAO = new UserDAOMemory();
        AuthDAOMemory authDAO = new AuthDAOMemory();
        registerService = new RegisterService(userDAO, authDAO);
    }

    @Test
    public void testRegisterSuccess() {
        // Positive test case: Registering a new user successfully
        String username = "testUser";
        String password = "password123";
        String email = "test@example.com";

        assertDoesNotThrow(() -> {
            RegisterResult result = registerService.register(username, password, email);
            assertNotNull(result);
            assertNotNull(result.getAuthToken());
            assertEquals(username, result.getUsername());
        });
    }

    @Test
    public void testRegisterUserAlreadyExists() {
        // Negative test case: Trying to register a user that already exists
        String existingUsername = "existingUser";
        String password = "password123";
        String email = "existing@example.com";

        // Register the user once
        assertDoesNotThrow(() -> registerService.register(existingUsername, password, email));

        // Attempt to register the same user again
        DataAccessException exception = assertThrows(DataAccessException.class, () -> {
            registerService.register(existingUsername, password, email);
        });
        assertEquals("User already exists", exception.getMessage());
    }

    @Test
    public void testRegisterWithEmptyFields() {
        // Negative test case: Trying to register with empty username, password, or email
        String emptyUsername = "";
        String password = "password123";
        String email = "test@example.com";

        DataAccessException exception = assertThrows(DataAccessException.class, () -> {
            registerService.register(emptyUsername, password, email);
        });
        assertEquals("Bad request", exception.getMessage());
    }
}
