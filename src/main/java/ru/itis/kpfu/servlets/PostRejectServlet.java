package ru.itis.kpfu.servlets;

import ru.itis.kpfu.models.Post;
import ru.itis.kpfu.services.PostService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/reject")
public class PostRejectServlet extends HttpServlet {
    PostService postService;

    @Override
    public void init() throws ServletException {
        postService = (PostService) getServletContext().getAttribute("postService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        if (id == null || id.equals("") || !id.matches("^[1-9]\\d*$")) {
            request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/pageNotFound.jsp").forward(request, response);
        } else {
            Long postId = Long.parseLong(id);
            Post post = postService.getPostById(postId);
            if (post == null || !post.getStatus().equals("suggested")) {
                request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/pageNotFound.jsp").forward(request, response);
            } else {
                postService.deletePost(postId);
                response.sendRedirect(getServletContext().getContextPath() + "/suggestedPosts");
            }
        }
    }
}
