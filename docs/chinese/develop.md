# 前言
为了方便使用与理解，开发者重新定义了`Java`生态原有的网络请求库格式。
<br><br>
您只需要使用一行代码，就可以达到发送请求的目的。
# 例子
## 简单例子

```java
import http4j.Http4J;
import http4j.HttpResponse;
import http4j.resource.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Simple
            HttpResponse response;

            response = Http4J.get("http://localhost:80");
            System.out.println(response.html);
            System.out.println(response.status_code);
            System.out.println(response.cookies);
            System.out.println(response.headers);

            // Medium Difficulty
            // 请求的参数第一位必须是请求地址，其他参数位置皆可随意变动，但是 `HttpConfig` 只能在最后一个参数位置
            response = Http4J.post(
                    "http://localhost:80",
                    new Params()
                            .add("params", "test"),
                    new Cookies()
                            .add("who", "Huyemt"),
                    new RequestBody()
                            .add("postBody", "Http4J"),
                    new Headers()
                            .add("acs-token", "21232f297a57a5a743894a0e4a801fc3"),
                    new HttpConfig()
                            .autoHeaders(true) // 自动填充报头
                            .allowsRedirect(true) // 允许跟随重定向
                            .timeout(3000) // 超时时间
            );
            System.out.println(response.html);
            System.out.println(response.status_code);
            System.out.println(response.cookies);
            System.out.println(response.headers);
            /**
             * 如果您需要下载，那么就可以使用 `response.content` 获取响应内容的字节
             */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```
## 高级例子
`Http4J`提供了<kbd>[Session](../../src/main/java/http4j/session/Session.java)</kbd>与<kbd>[SaveCookieSession](../../src/main/java/http4j/session/SaveCookieSession.java)</kbd>以保持会话
<br>
如果您想知道两者区别，请点击对应类阅读源码。
```java
import http4j.Http4J;
import http4j.HttpResponse;
import http4j.session.Session;
import http4j.session.SaveCookieSession;

public class Main {
    public static void main(String[] args) {
        try {
            Session session;
            // 下列方法都可以创建一个`Session`
            session = Http4J.session();
            // session = new Session();
            // session = new SaveCookieSession();
            // session = Http4J.saveCookieSession();

            // 发送请求
            // 与上面的例子一致
            HttpResponse response = session.get("http://localhost:80");
            System.out.println(response.html);
            System.out.println(response.status_code);
            System.out.println(response.cookies);
            System.out.println(response.headers);
            
            System.out.println(session.cookies);
            System.out.println(session.headers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```