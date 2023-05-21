package ru.itis.kpfu.listeners;

import ru.itis.kpfu.parsers.PostTextParser;
import ru.itis.kpfu.repositories.*;
import ru.itis.kpfu.services.CommentServiceImpl;
import ru.itis.kpfu.services.PostServiceImpl;
import ru.itis.kpfu.validators.PostCreateDataErrorMessageGenerator;
import ru.itis.kpfu.validators.SignUpDataErrorMessageGenerator;
import ru.itis.kpfu.hashers.PasswordHasher;
import ru.itis.kpfu.hashers.PasswordHasherPBKDF2Impl;
import ru.itis.kpfu.jdbcUtils.SimpleDataSource;
import ru.itis.kpfu.services.AccountServiceImpl;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@WebListener
public class InitListener implements ServletContextListener {

    private DataSource dataSource;
    private AccountsRepository accountsRepository;

    private PostRepository postRepository;

    private CommentRepository commentRepository;

    private SignUpDataErrorMessageGenerator signUpErrorsGenerator;

    private PostCreateDataErrorMessageGenerator postCreateDataErrorsGenerator;

    private PasswordHasher hasher;

    private PostTextParser parser;

    private String postImagesPath;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Properties properties = new Properties();
        try {
            properties.load(InitListener.class.getResourceAsStream("/application.properties"));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        dataSource = new SimpleDataSource(
                properties.getProperty("db.url"),
                properties.getProperty("db.username"),
                properties.getProperty("db.password"),
                properties.getProperty("db.driver")
        );

        accountsRepository = new AccountsRepositoryJdbcImpl(dataSource);

        postRepository = new PostRepositoryJdbcImpl(dataSource);

        commentRepository = new CommentRepositoryJdbcImpl(dataSource);

        signUpErrorsGenerator = new SignUpDataErrorMessageGenerator();

        postCreateDataErrorsGenerator = new PostCreateDataErrorMessageGenerator();

        hasher = new PasswordHasherPBKDF2Impl();

        parser = new PostTextParser();

        postImagesPath = properties.getProperty("postImagesPath");

        sce.getServletContext().setAttribute("accountService", new AccountServiceImpl(accountsRepository, signUpErrorsGenerator, hasher));
        sce.getServletContext().setAttribute("postService", new PostServiceImpl(postRepository, parser, postCreateDataErrorsGenerator));
        sce.getServletContext().setAttribute("commentService", new CommentServiceImpl(commentRepository));
        sce.getServletContext().setAttribute("postImagesPath", postImagesPath);
    }
}
