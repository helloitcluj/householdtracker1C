package com.helloit.householdtracker.ux.common;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 */
public class SecurityFilter implements Filter {

    public static final String CURRENT_PRINCIPAL_TAG = "currentPrincipal";
    public static final String LOGIN_PAGE = "account/loginpage.html";

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        final HttpServletRequest httpServletRequest = (HttpServletRequest) req;
        final HttpServletResponse httpServletResponse = (HttpServletResponse) resp;

        final String requestURI = httpServletRequest.getRequestURI();
        final String contextPath = httpServletRequest.getContextPath();

        if (requestURI.startsWith(contextPath + "/account/") ||
                requestURI.startsWith(contextPath + "/css/") ||
                requestURI.startsWith(contextPath + "/images") ||
                requestURI.startsWith(contextPath + "/js")) {
            chain.doFilter(req, resp);
        } else {
            HttpSession session = httpServletRequest.getSession(false);
            if (session != null) {
                Object currentPrincipal = session.getAttribute(CURRENT_PRINCIPAL_TAG);
                if (currentPrincipal != null) {
                    chain.doFilter(req, resp);
                } else {
                    httpServletResponse.sendRedirect(LOGIN_PAGE);
                }
            } else {
                httpServletResponse.sendRedirect(LOGIN_PAGE);
            }

        }
    }

}
