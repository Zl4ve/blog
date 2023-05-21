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

@WebServlet("/post")
public class PostServlet extends HttpServlet {

    private PostService postService;

    private AccountService accountService;

    private CommentService commentService;

    @Override
    public void init() throws ServletException {
        postService = (PostService) getServletContext().getAttribute("postService");
        accountService = (AccountService) getServletContext().getAttribute("accountService");
        commentService = (CommentService) getServletContext().getAttribute("commentService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        if (id == null || id.equals("") || !id.matches("^[1-9]\\d*$")) {
            request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/pageNotFound.jsp").forward(request, response);
        } else {
            Long postId = Long.parseLong(id);
            Post post = postService.getPostById(postId);
            Account currentAccount = (Account) request.getSession().getAttribute("account");
            if (post == null) {
                request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/pageNotFound.jsp").forward(request, response);
            } else {
                if (post.getStatus().equals("accepted") || (currentAccount != null && currentAccount.getRole().equals("admin") && post.getStatus().equals("suggested"))) {
                    List<String> postContent = postService.getPostContentByPostId(postId);
                    Account author = accountService.getAccountById(post.getAuthorId());

                    request.setAttribute("post", post);
                    request.setAttribute("postContent", postService.replaceNewLineSymbolsWithHtmlNewLineTag(postContent));
                    request.setAttribute("author", author);

                    if (post.getStatus().equals("accepted")) {
                        List<Comment> comments = commentService.getCommentsByPostId(post.getId());
                        request.setAttribute("comments", comments);

                        List<String> commentAuthorsUsernames = new ArrayList<>();
                        for (Comment comment : comments) {
                            commentAuthorsUsernames.add(accountService.getAccountById(comment.getAuthorId()).getUsername());
                        }
                        request.setAttribute("commentAuthorsUsernames", commentAuthorsUsernames);
                    }

                    request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/post.jsp").forward(request, response);
                } else {
                    request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/pageNotFound.jsp").forward(request, response);
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commentText = request.getParameter("comment");
        if (commentText == null || commentText.equals("")) {
            response.sendRedirect(getServletContext().getContextPath() + "/post?id=" + request.getParameter("id"));
        } else {
            HttpSession session = request.getSession(false);
            Account currentAccount = (Account) session.getAttribute("account");
            Comment comment = new Comment(Long.parseLong(request.getParameter("id")), currentAccount.getId(), commentText);
            commentService.saveComment(comment);
            response.sendRedirect(getServletContext().getContextPath() + "/post?id=" + request.getParameter("id"));
        }
    }
}
