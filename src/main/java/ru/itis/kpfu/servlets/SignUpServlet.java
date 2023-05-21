package ru.itis.kpfu.servlets;

import ru.itis.kpfu.models.Account;
import ru.itis.kpfu.services.AccountService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/signUp")
public class SignUpServlet extends HttpServlet {

    private AccountService accountService;

    @Override
    public void init() throws ServletException {
        accountService = (AccountService) getServletContext().getAttribute("accountService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/signUp.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        Account account = new Account(username, password, email, null);
        if (accountService.isValid(account)) {
            boolean flag = true;
            if (accountService.checkIfExistsByUsername(account.getUsername())) {
                request.setAttribute("usernameAlreadyExistsError", "Account with this username already exists.");
                flag = false;
                request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/signUp.jsp").forward(request, response);
            }

            if (accountService.checkIfExistsByEmail(account.getEmail())) {
                request.setAttribute("emailAlreadyExistsError", "Account with this email already exists.");
                flag = false;
                request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/signUp.jsp").forward(request, response);
            }

            if (flag) {
                accountService.signUp(account);
                request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/signUpSuccess.jsp").forward(request, response);
            }
        } else {
            accountService.setSignUpErrors(request, account);
            request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/signUp.jsp").forward(request, response);
        }
    }
}
