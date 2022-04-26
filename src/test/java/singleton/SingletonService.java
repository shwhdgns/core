package singleton;

public class SingletonService {

    // static 영역에 객체를 딱 1개만 생성.
    private static final SingletonService instance = new SingletonService();

    // public 하게 사용하기 위해서는 반드시 getInstance 함수를 통해서만 생성이 가능하다.
    public static SingletonService getInstance() {
        return instance;
    }

    // 외부에서 new 로 객체 생성이 불가능하게. private 생성자 추가.
    private SingletonService() {
    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }
}
