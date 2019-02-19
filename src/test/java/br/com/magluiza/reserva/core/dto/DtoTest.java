package br.com.magluiza.reserva.core.dto;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import pl.pojo.tester.api.assertion.Assertions;

@RunWith(SpringRunner.class)
public class DtoTest {

    @Test
    public void testAllDtos() {
        Assertions.assertPojoMethodsFor(ErrorDto.class).areWellImplemented();
        Assertions.assertPojoMethodsFor(FieldErrorDto.class).areWellImplemented();
        Assertions.assertPojoMethodsFor(ParameterizedErrorDto.class).areWellImplemented();
    }

    @Test
    public void testErrorDtoAdd() {
        ErrorDto errorDto = new ErrorDto("message1", "description1");
        Assert.assertEquals(0, errorDto.getFieldErrors().size());
        errorDto.add("ObjectErrorName", "field1", "messageError");
        Assert.assertEquals(1, errorDto.getFieldErrors().size());
    }
}
