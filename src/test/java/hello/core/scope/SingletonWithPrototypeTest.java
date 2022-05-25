package hello.core.scope;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
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
        // 결과   : Err.
        // 사유   : ClientBean 객체가 Singleton으로 되어 있기 때문에 객체 생성 시점에 이미 PrototypeBean이 주입되어 있어 해당 객체만 사용함.
        /* 에러 메세지
        * clientBean 1.logic
          hello.core.scope.SingletonWithPrototypeTest$PrototypeBean@23941fb4
          clientBean 2.logic
          hello.core.scope.SingletonWithPrototypeTest$PrototypeBean@23941fb4
        * */
        assertThat(cl1Count).isEqualTo(1);
//        assertThat(cl2Count).isEqualTo(1);
        assertThat(cl2Count).isEqualTo(2);
    }

    @Scope("singleton")
    @RequiredArgsConstructor
    static class ClientBean {
        private final PrototypeBean prototypeBean;

        public int logic() {
            System.out.println(this.prototypeBean);
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

        @PreDestroy
        public void destroy() {
            System.out.println("prototype destroy");
        }

    }
}
