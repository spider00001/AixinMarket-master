package com.bluemsun.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class HeaderInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
//        String domain = request.getHeader("Origin");
//        System.out.println(domain+"+++++++++=====");
        response.addHeader("Set-Cookie","Domain=127.0.0.1; Path=/");
        //允许跨域的域名，*号为允许所有,存在被 DDoS攻击的可能。
        String origin = request.getHeader("origin");
//        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
//
//        //表明服务器支持的所有头信息字段
//        response.setHeader("Access-Control-Allow-Headers",
//                    "Origin," +
//                        "Access-Control-Request-Headers," +
//                        "Access-Control-Allow-Headers," +
//                        "DNT," +
//                        "X-Requested-With," +
//                        "X-Mx-ReqToken," +
//                        "Keep-Alive," +
//                        "User-Agent," +
//                        "X-Requested-With," +
//                        "If-Modified-Since," +
//                        "Cache-Control," +
//                        "Content-Type," +
//                        "Accept," +
//                        "Connection," +
//                        "Cookie," +
//                        "X-XSRF-TOKEN," +
//                        "X-CSRF-TOKEN," +
//                        "Authorization," +
//                        "token");
//
//        //如果需要把Cookie发到服务端，需要指定Access-Control-Allow-Credentials字段为true;
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//
//        //首部字段 Access-Control-Allow-Methods 表明服务器允许客户端使用 POST, GET 和 OPTIONS 方法发起请求。
//        //该字段与 HTTP/1.1 Allow: response header 类似，但仅限于在需要访问控制的场景中使用。
//        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//
//        //表明该响应的有效时间为 86400 秒，也就是 24 小时。在有效时间内，浏览器无须为同一请求再次发起预检请求。
//        //请注意，浏览器自身维护了一个最大有效时间，如果该首部字段的值超过了最大有效时间，将不会生效。
//        response.setHeader("Access-Control-Max-Age", "86400");
//
//        // IE8 引入XDomainRequest跨站数据获取功能,也就是说为了兼容IE
//        response.setHeader("XDomainRequestAllowed", "1");
        //允许跨域的域名。单个域名、*(匹配所有域名)
        //request.getHeader("Origin") 即直接获取请求头的origin的值，即请求方的域名
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));

        //若要使用cookie，需要设置值为true，表示允许发送cookie（可选）
        response.setHeader("Access-Control-Allow-Credentials", "true");

        //服务器支持的所有请求头字段
        response.setHeader("Access-Control-Allow-Headers",
                "Origin," +
                        "Access-Control-Request-Headers," +
                        "Access-Control-Allow-Headers," +
                        "Content-Type," +
                        "Keep-Alive," +
                        "User-Agent," +
                        "Cache-Control," +
                        "Cookie," +
                        "DNT," +
                        "X-Requested-With," +
                        "X-Mx-ReqToken," +
                        "X-Requested-With," +
                        "If-Modified-Since," +
                        "Accept," +
                        "Connection," +
                        "X-XSRF-TOKEN," +
                        "X-CSRF-TOKEN," +
                        "Authorization");

        //（预检请求）的响应结果，规定了服务器允许客户端使用的请求方法， 如：POST, GET 和 OPTIONS
        response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");

        //设置（预检请求）的返回结果的过期时间，这里设置响应最大有效时间为 86400 秒，即24 小时
        //即 Access-Control-Allow-Methods 和Access-Control-Allow-Headers 提供的信息可以被缓存多久
        response.setHeader("Access-Control-Max-Age", "86400");

        //设置除了简单响应首部以外，需要暴露给外部的其他首部
        response.setHeader("Access-Control-Expose-Headers", "Authorization");

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}