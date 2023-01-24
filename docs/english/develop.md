# Foreword
In order to facilitate the use and understanding, the developer redefined the original network request library format of the `Java` ecosystem.
<br><br>
You only need to use one line of code to send the request.
# Example
## Simple
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
            // The first parameter of the request must be the request address, and other parameter positions can be changed at will, but `HttpConfig` can only be in the last parameter position.
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
                            .autoHeaders(true) // auto-fill header
                            .allowsRedirect(true) // follow redirect
                            .timeout(3000) // timeout
            );
            System.out.println(response.html);
            System.out.println(response.status_code);
            System.out.println(response.cookies);
            System.out.println(response.headers);
            /**
             * If you need to download, you can use `response.content` to get the bytes of the response content
             */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```
## Senior
`Http4J` provides <kbd>[Session](../../src/main/java/http4j/session/Session.java)</kbd> and <kbd>[SaveCookieSession](../../src/main/java/http4j/session/SaveCookieSession.java)</kbd> to keep the session.
<br>
If you want to know the difference between the two, please click on the corresponding class to read the source code.
```java
import http4j.Http4J;
import http4j.HttpResponse;
import http4j.session.Session;
import http4j.session.SaveCookieSession;
public class Main {
    public static void main(String[] args) {
        try {
            Session session;
            // The following methods can create a `Session`.
            session = Http4J.session();
            // session = new Session();
            // session = new SaveCookieSession();
            // session = Http4J.saveCookieSession();
            
            // Request to send
            // Consistent with the simple example above
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