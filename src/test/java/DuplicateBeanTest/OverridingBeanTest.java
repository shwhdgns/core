package DuplicateBeanTest;

import hello.core.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

public class OverridingBeanTest {

    @Test
    void OverridingBeanNameTest() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(ClassConfiguration.class);

        /*
         * Result
         * -> Overriding bean definition for bean 'classAC' with a different definition:
         *   replacing [Generic bean: class [DuplicateBeanTest.OverrideBeanTest$ClassConfiguration$ClassA];
         *        with [Generic bean: class [DuplicateBeanTest.OverrideBeanTest$ClassConfiguration$ClassB];
         * */

        Assertions.assertThat(ac.getBean("classAC")).isInstanceOf(ClassConfiguration.ClassB.class);
        // -> Pass
        Assertions.assertThat(ac.getBean("classAC")).isInstanceOf(ClassConfiguration.ClassA.class);
        // -> Fail
            /*
                java.lang.AssertionError:
                Expecting:
                DuplicateBeanTest.OverridingBeanTest$ClassConfiguration$ClassB@6c0d7c83
                to be an instance of:
                DuplicateBeanTest.OverridingBeanTest.ClassConfiguration.ClassA
                but was instance of:
                DuplicateBeanTest.OverridingBeanTest.ClassConfiguration.ClassB
            */
    }

    @Configuration
    static class ClassConfiguration {

        @Component("classAC")
        class ClassB {

        }

        @Component("classAC")
        class ClassA {

        }
    }

}
