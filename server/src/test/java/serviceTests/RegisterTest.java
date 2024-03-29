package serviceTests;
import dataAccess.Memory.AuthDAOMemory;
import dataAccess.Exceptions.AlreadyTakenException;
import dataAccess.Memory.UserDAOMemory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.RegisterRequest;
import result.RegisterResult;
import service.RegisterService;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterTest {
    private RegisterService registerService;
    private UserDAOMemory userDAOMemory;
    private AuthDAOMemory authDAOMemory;

    @BeforeEach
    public void setUp() {
        userDAOMemory = new UserDAOMemory();
        authDAOMemory = new AuthDAOMemory();
        registerService = new RegisterService(userDAOMemory, authDAOMemory);
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
        userDAOMemory.createUser(existingUsername, password, email);
        RegisterRequest registerRequest = new RegisterRequest(existingUsername, password, email);
        Assertions.assertThrows(AlreadyTakenException.class, ()-> registerService.register(registerRequest.getUsername(), registerRequest.getPassword(), registerRequest.getEmail()));
        Assertions.assertNull(authDAOMemory.getAuth(existingUsername));
        Assertions.assertNull(authDAOMemory.getAuth(password));
    }

//    @Test
//    public void testRegisterWithEmptyFields() {
//        // Negative test case: Trying to register with empty username, password, or email
//        String emptyUsername = "";
//        String password = "password123";
//        String email = "test@example.com";
//
//        DataAccessException exception = assertThrows(DataAccessException.class, () -> {
//            registerService.register(emptyUsername, password, email);
//        });
//        assertEquals("Bad request", exception.getMessage());
//    }
}
