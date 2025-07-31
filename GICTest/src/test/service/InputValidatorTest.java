package test.service;

import main.service.InputValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InputValidatorTest {
    @Test
    public void testValidInputs() {
        assertTrue(InputValidator.isValid("A1", 5));
        assertTrue(InputValidator.isValid("C3", 5));
        assertTrue(InputValidator.isValid("E5", 5));
        assertTrue(InputValidator.isValid("B2", 4));
    }
    @Test
    public void testInvalidDueToColumn() {
        assertFalse(InputValidator.isValid("A0", 5));
        assertFalse(InputValidator.isValid("A6", 5));
        assertFalse(InputValidator.isValid("B10", 5));
        assertFalse(InputValidator.isValid("", 5));
        assertFalse(InputValidator.isValid("AA1", 5));
    }

}
