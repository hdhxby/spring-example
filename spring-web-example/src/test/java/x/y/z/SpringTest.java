package x.y.z;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SpringTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void test(){
        assertNotNull(objectMapper);
    }
}
