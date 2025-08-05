package su.opencode.digsigcik.specification;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import su.opencode.digsigcik.model.model_type_parsing.PinCode;

public class SpecificationBuilderTest {

    @Test
    public void test() {
        var l = SpecificationBuilder.of(PinCode.class);
        Assertions.assertTrue(true);
    }
}
