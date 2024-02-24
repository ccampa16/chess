package serviceTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import result.ClearResult;
import service.ClearService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ClearServiceTest {
    private HashSet<String> db;
    private ClearService cs;
    @BeforeEach
    public void setUp(){
        db = new HashSet<>();
    }

    @Test
    public void testClear(){

    }

}
