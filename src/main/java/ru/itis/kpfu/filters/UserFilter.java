package ru.itis.kpfu.filters;

import ru.itis.kpfu.models.Account;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebFilter("/*")
public class UserFilter extends HttpFilter {

    private static final String[] AUTH_PAGES = {"/profile", "/comment", "/subscribe", "/unsubscribe", "/logout", "/createPost", "/subscribers"};

    private static final String[] UNAUTH_PAGES = {"/signIn", "/signUp"};
    private static final String[] ADMIN_PAGES = {"/suggestedPosts", "/accept", "/reject"};
    private ServletContext servletContext;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        servletContext = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession(false);

        boolean isAuthPage = false;
        boolean isUnauthPage = false;
        boolean isAdminPage = false;

        for (String page : AUTH_PAGES) {
            if (request.getRequestURI().equals(request.getContextPath() + page)) {
                isAuthPage = true;
            }
        }

        for (String page : UNAUTH_PAGES) {
            if (request.getRequestURI().equals(request.getContextPath() + page)) {
                isUnauthPage = true;
            }
        }

        for (String page : ADMIN_PAGES) {
            if (request.getRequestURI().equals(request.getContextPath() + page)) {
                isAdminPage = true;
            }
        }

        if (session == null) {
            if (isAuthPage || isAdminPage) {
                response.sendRedirect(request.getContextPath() + "/signIn");
            } else {
                filterChain.doFilter(request, response);
            }
        } else {
            Account account = (Account) session.getAttribute("account");
            if (account == null) {
                if (isAuthPage || isAdminPage) {
                    response.sendRedirect(request.getContextPath() + "/signIn");
                } else {
                    filterChain.doFilter(request, response);
                }
            } else {

                if (isUnauthPage) {
                    response.sendRedirect(request.getContextPath() + "/profile?id=" + account.getId());
                } else if (isAdminPage && !account.getRole().equals("admin")) {
                    response.sendRedirect(request.getContextPath() + "/main");
                } else {
                    filterChain.doFilter(request, response);
                }
            }
        }
    }

    @Override
    public void destroy() {
    }
}
