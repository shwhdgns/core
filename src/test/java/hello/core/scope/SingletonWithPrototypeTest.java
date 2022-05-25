package hello.core.scope;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonWithPrototypeTest {

    @Test
    public void singletonWithPrototypeTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

        PrototypeBean prototypeBean1 = ac.getBean((PrototypeBean.class));
        PrototypeBean prototypeBean2 = ac.getBean((PrototypeBean.class));

        System.out.println(prototypeBean1);
        System.out.println(prototypeBean2);

        prototypeBean1.addCount();
        prototypeBean2.addCount();

        assertThat(prototypeBean1.getCount()).isEqualTo(1);
        assertThat(prototypeBean2.getCount()).isEqualTo(1);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        ClientBean clientBean2 = ac.getBean(ClientBean.class);

        System.out.println("clientBean 1.logic");
        int cl1Count = clientBean1.logic();
        System.out.println("clientBean 2.logic");
        int cl2Count = clientBean2.logic();

        // 기대값  : PrototypeBean 객체는 Scope가 prototype이니 각각 생성되겠지.
        // 결과   : Pass
        // 사유   : 메소드 실행 시점에 ObjectProvider 객체에서 새로운 PrototypeBean 객체를 꺼내서 사용하기 때문에 테스트가 통과된다.
        //         해당 개념을 "DL(Dependency Lookup)"이라고 하며 의존성을 주입하는 "DI"와는 다르게 의존객체를 "직접" 찾아서 사용한다.

        assertThat(cl1Count).isEqualTo(1);
        assertThat(cl2Count).isEqualTo(1);
    }

    @Scope("singleton")
    static class ClientBean {

        @Autowired
        private ObjectProvider<PrototypeBean> prototypeBeanProvider;

        public int logic() {
            PrototypeBean prototypeBean = prototypeBeanProvider.getObject();
            prototypeBean.addCount();

            return prototypeBean.getCount();
        }
    }

    @Scope("prototype")
     static class PrototypeBean {

        private int count;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("prototype init");
        }
    }
}
