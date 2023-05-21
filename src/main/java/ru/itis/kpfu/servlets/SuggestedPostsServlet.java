package ru.itis.kpfu.servlets;

import ru.itis.kpfu.models.Post;
import ru.itis.kpfu.services.PostService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/suggestedPosts")
public class SuggestedPostsServlet extends HttpServlet {

    private PostService postService;

    @Override
    public void init() throws ServletException {
        postService = (PostService) getServletContext().getAttribute("postService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Post> suggestedPosts = postService.getSuggestedPosts();
        List<String> firstPictures = new ArrayList<>();
        for (Post post : suggestedPosts) {
            firstPictures.add(postService.getFirstPostImageUri(post.getId(), getServletContext()));
        }

        request.setAttribute("suggestedPosts", suggestedPosts);
        request.setAttribute("firstPictures", firstPictures);
        request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/suggestedPosts.jsp").forward(request, response);
    }
}
