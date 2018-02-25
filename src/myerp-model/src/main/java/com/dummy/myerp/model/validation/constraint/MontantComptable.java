package com.dummy.myerp.model.validation.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Digits;

@Digits(integer = 13, fraction = 2)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@Documented
public @interface MontantComptable {

    String message() default "Taux de TVA invalide";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        MontantComptable[] value();
    }
}
