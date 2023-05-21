package ru.itis.kpfu.servlets;

import ru.itis.kpfu.models.Account;
import ru.itis.kpfu.services.AccountService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/signIn")
public class SignInServlet extends HttpServlet {

    private AccountService accountService;

    @Override
    public void init() throws ServletException {
        accountService = (AccountService) getServletContext().getAttribute("accountService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/signIn.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (!(username == null || username.equals("")) && !(password == null || password.equals(""))) {
            Account account = accountService.findByUsernameAndPassword(username, password);
            if (account == null) {
                request.setAttribute("errorMessage", "Wrong username or password");
                request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/signIn.jsp").forward(request, response);
            } else {
                HttpSession session = request.getSession(true);
                session.setAttribute("account", account);
                response.sendRedirect( getServletContext().getContextPath() + "/profile?id=" + account.getId());
            }
        } else {
            if (username == null || username.equals("")) {
                request.setAttribute("usernameError", "Enter a username.");
            }
            if (password == null || password.equals("")) {
                request.setAttribute("passwordError", "Enter a password.");
            }
            request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/signIn.jsp").forward(request, response);
        }
    }
}
