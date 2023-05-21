package ru.itis.kpfu.servlets;

import ru.itis.kpfu.models.Account;
import ru.itis.kpfu.models.Comment;
import ru.itis.kpfu.models.Post;
import ru.itis.kpfu.services.AccountService;
import ru.itis.kpfu.services.CommentService;
import ru.itis.kpfu.services.PostService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    private PostService postService;

    private AccountService accountService;

    @Override
    public void init() throws ServletException {
        postService = (PostService) getServletContext().getAttribute("postService");
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
                List<Post> posts = postService.getPostsByAuthorId(accountId);
                request.setAttribute("profileAccount", account);
                request.setAttribute("posts", posts);
                request.setAttribute("isSubscribed", accountService.isSubscribed(accountId, currentAccount.getId()));
                request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/profile.jsp").forward(request, response);
            }
        }
    }
}
