package ru.itis.kpfu.servlets;

import ru.itis.kpfu.models.Account;
import ru.itis.kpfu.models.Post;
import ru.itis.kpfu.services.PostService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/createPost")
public class PostCreateServlet extends HttpServlet {

    private PostService postService;

    @Override
    public void init() throws ServletException {
        postService = (PostService) getServletContext().getAttribute("postService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/createPost.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String text = request.getParameter("text");
        HttpSession session = request.getSession(false);
        Account account = (Account) session.getAttribute("account");
        if (!(title == null || title.equals("")) && !(text == null || text.equals(""))) {
            List<String> parsedText = postService.getParser().parse(text);
            if (postService.validateParsedPostContent(parsedText)) {
                postService.createPost(new Post(account.getId(), title, "suggested"));
                postService.saveContent(parsedText, postService.getMaxPostId());
                request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/createPostSuccess.jsp").forward(request, response);
            } else {
                postService.setTextError(parsedText, request);
                request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/createPost.jsp").forward(request, response);
            }
        } else {
            if (title == null || title.equals("")) {
                request.setAttribute("titleError", "Enter a title");
            }
            if (text == null || text.equals("")) {
                request.setAttribute("textError", "Enter text");
            }
            request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/createPost.jsp").forward(request, response);
        }
    }
}
