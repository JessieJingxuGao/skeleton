package api;

import io.dropwizard.jersey.validation.Validators;
import org.junit.Test;

import javax.validation.Validator;
import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.collection.IsEmptyCollection.empty;

public class CreateTagRequestTest {
    private final Validator validator = Validators.newValidator();

//not needed as we just use input.val() and receipt-id from dom to get tagging related information


//    @Test
//    public void testValid() {
//        CreateTagRequest tag = new CreateTagRequest();
//        tag.tag = "OK";
//        tag.id=5;
//        assertThat(validator.validate(tag), empty());
//    }


//    @Test
//    public void testValid() {
//        CreateTagRequest tag = new CreateTagRequest();
//        tag.tag = "OK";
//        assertThat(validator.validate(tag), empty());
//    }

//    @Test
//    public void testMissingTagName() {
//        CreateTagRequest tag = new CreateTagRequest();
//        assertThat(validator.validate(tag), hasSize(1));
//    }

}