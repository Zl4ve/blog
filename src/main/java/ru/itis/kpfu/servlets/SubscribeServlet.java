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

@WebServlet("/subscribe")
public class SubscribeServlet extends HttpServlet {
    private AccountService accountService;

    @Override
    public void init() throws ServletException {
        accountService = (AccountService) getServletContext().getAttribute("accountService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        if (id == null || id.equals("") || !id.matches("^[1-9]\\d*$")) {
            request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/pageNotFound.jsp").forward(request, response);
        } else {
            Long accountId = Long.parseLong(id);
            Account account = accountService.getAccountById(accountId);
            if (account == null) {
                request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/pageNotFound.jsp").forward(request, response);
            } else {
                HttpSession session = request.getSession(false);
                Account currentAccount = (Account) session.getAttribute("account");
                if (currentAccount.getId() == accountId || accountService.isSubscribed(accountId, currentAccount.getId())) {
                    response.sendRedirect( getServletContext().getContextPath() + "/profile?id=" + accountId);
                } else {
                    accountService.subscribe(accountId, currentAccount.getId());
                    response.sendRedirect( getServletContext().getContextPath() + "/profile?id=" + accountId);
                }
            }
        }
    }
}
