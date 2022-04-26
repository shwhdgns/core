package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
// @Component Annotation이 붙은 Component를 찾아 스프링에 등록해준다.
// 예제 코드의 유지를 위해 Configuration 어노테이션은 스캔에서 제외한다.
@ComponentScan(
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {

}