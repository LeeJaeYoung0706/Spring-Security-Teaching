package com.example.security_study.lib;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class URLMatcher implements RequestMatcher {

    private OrRequestMatcher requestMatcher;

    public URLMatcher(List<String> pathList) {
        if(!pathList.isEmpty()) {
            List<RequestMatcher> requestMatcherList = pathList.stream()
                    .map(AntPathRequestMatcher::new)
                    //.map(result -> new AntPathRequestMatcher(result))
                    .collect(Collectors.toList());

            requestMatcher = new OrRequestMatcher(requestMatcherList);
        }
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        return requestMatcher.matches(request);
    }
}