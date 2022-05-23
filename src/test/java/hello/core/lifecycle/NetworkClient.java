package hello.core.lifecycle;

public class NetworkClient {

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = "+ url);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void connect() {
        System.out.println("connect = "+ url);
    }

    public void call(String message) {
        System.out.println("call :"+url + "message = "+ message);
    }

    public void disconnect() {
        System.out.println("close " + url);
    }

    private void init() {
        System.out.println("init Method");
        connect();
        call("초기화 연결 메시지");
    }

    private void close() {
        System.out.println("Destory Method");
        disconnect();
    }
}
