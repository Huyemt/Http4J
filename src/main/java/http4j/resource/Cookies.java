package http4j.resource;

import java.net.HttpCookie;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Huyemt
 */

public class Cookies extends HttpAttribute {
    private final Map<String, HttpCookie> cookieMap = new LinkedHashMap<>();

    public Cookies() {

    }

    public Cookies(String cookies) {
        if (cookies != null && cookies.length() != 0) {
            for (HttpCookie cookie : HttpCookie.parse(cookies)) {
                HttpCookie cookie1 = new HttpCookie(URLDecoder.decode(cookie.getName(), StandardCharsets.UTF_8), URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8));
                cookie1.setComment(cookie.getComment());
                cookie1.setCommentURL(cookie.getCommentURL());
                cookie1.setDiscard(cookie.getDiscard());
                cookie1.setDomain(cookie.getDomain());
                cookie1.setHttpOnly(cookie.isHttpOnly());
                cookie1.setMaxAge(cookie.getMaxAge());
                cookie1.setPath(cookie.getPath());
                cookie1.setPortlist(cookie.getPortlist());
                cookie1.setSecure(cookie.getSecure());
                cookie1.setVersion(cookie.getVersion());
                this.cookieMap.put(cookie1.getName(), cookie1);
            }

        }
    }

    public Cookies(List<String> cookies) {
        if (cookies != null && cookies.size() != 0) {
            for (String cookie : cookies) {
                HttpCookie cookie1 = HttpCookie.parse(cookie).get(0);
                HttpCookie cookie2 = new HttpCookie(URLDecoder.decode(cookie1.getName(), StandardCharsets.UTF_8), URLDecoder.decode(cookie1.getValue(), StandardCharsets.UTF_8));
                cookie2.setComment(cookie1.getComment());
                cookie2.setCommentURL(cookie1.getCommentURL());
                cookie2.setDiscard(cookie1.getDiscard());
                cookie2.setDomain(cookie1.getDomain());
                cookie2.setHttpOnly(cookie1.isHttpOnly());
                cookie2.setMaxAge(cookie1.getMaxAge());
                cookie2.setPath(cookie1.getPath());
                cookie2.setPortlist(cookie1.getPortlist());
                cookie2.setSecure(cookie1.getSecure());
                cookie2.setVersion(cookie1.getVersion());
                this.cookieMap.put(cookie2.getName(), cookie2);
            }
        }
    }

    public Cookies add(String name, Object value) {
        HttpCookie cookie = HttpCookie.parse(name + "=" + (value == null ? "" : String.valueOf(value))).get(0);
        this.cookieMap.put(URLDecoder.decode(cookie.getName(), StandardCharsets.UTF_8), cookie);
        return this;
    }

    public Cookies add(HttpCookie cookie) {
        this.cookieMap.put(URLDecoder.decode(cookie.getName(), StandardCharsets.UTF_8), cookie);
        return this;
    }

    public Cookies remove(String name) {
        this.cookieMap.remove(name);
        return this;
    }

    public Cookies set(String name, Object value) {
        if (this.cookieMap.containsKey(name)) {
            this.get(name).setValue(value == null ? "" : String.valueOf(value));
        }

        return this;
    }

    public Cookies set(String name, HttpCookie cookie) {
        if (this.cookieMap.containsKey(name)) {
            this.add(name, cookie);
        }

        return this;
    }

    public boolean contains(String name) {
        return this.cookieMap.containsKey(URLDecoder.decode(name, StandardCharsets.UTF_8));
    }

    public HttpCookie get(String name) {
        return this.cookieMap.getOrDefault(URLDecoder.decode(name, StandardCharsets.UTF_8), null);
    }

    public int size() {
        return this.cookieMap.size();
    }

    public String toString(boolean encode) {
        StringBuilder builder = new StringBuilder();

        for (HttpCookie cookie : cookieMap.values()) {
            if (encode) {
                builder.append(URLEncoder.encode(cookie.getName(), StandardCharsets.UTF_8)).append("=").append(URLEncoder.encode(cookie.getValue(), StandardCharsets.UTF_8));
            } else {
                builder.append(cookie.getName()).append("=").append(cookie.getValue());
            }

            builder.append(";");
        }

        return builder.toString();
    }

    public Map<String, HttpCookie> getCookieMap() {
        return (Map<String, HttpCookie>) new LinkedHashMap<>(this.cookieMap).clone();
    }

    public String toString() {
        return this.toString(false);
    }
}
