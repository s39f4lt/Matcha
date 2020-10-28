package ru.school.matcha.controllers;

import lombok.extern.slf4j.Slf4j;
import ru.school.matcha.exceptions.MatchaException;
import ru.school.matcha.services.LikeServiceImpl;
import ru.school.matcha.services.interfaces.LikeService;
import spark.HaltException;
import spark.Route;

import java.util.List;

import static java.lang.Long.parseLong;

@Slf4j
public class LikeController {

    private static final LikeService likeService;

    static {
        likeService = new LikeServiceImpl();
    }

    public static Route getLikes = (request, response) -> {
        Long id = parseLong(request.params("id"));
        try {
            List<Long> likes = likeService.getLikes(id, true);
            response.status(200);
            return likes;
        } catch (HaltException ex) {
            log.error("Credentials are invalid");
        } catch (MatchaException ex) {
            log.error("Failed to get likes by user with id: {}", id, ex);
            response.status(400);
            response.body(String.format("Failed to get likes by user with id: %d. %s", id, ex.getMessage()));
        } catch (Exception ex) {
            log.error("An unexpected error occurred while trying to get likes by user with id: {}", id, ex);
            response.status(500);
            response.body(String.format("An unexpected error occurred while trying to get likes by user with id: %d. %s", id, ex.getMessage()));
        }
        return response.body();
    };

    public static Route getDislikes = (request, response) -> {
        Long id = parseLong(request.params("id"));
        try {
            List<Long> likes = likeService.getLikes(id, false);
            response.status(200);
            return likes;
        } catch (HaltException ex) {
            log.error("Credentials are invalid");
        } catch (MatchaException ex) {
            log.error("Failed to get dislikes by user with id: {}", id, ex);
            response.status(400);
            response.body(String.format("Failed to get dislikes by user with id: %d. %s", id, ex.getMessage()));
        } catch (Exception ex) {
            log.error("An unexpected error occurred while trying to get dislikes by user with id: {}", id, ex);
            response.status(500);
            response.body(String.format("An unexpected error occurred while trying to get dislikes by user " +
                    "with id: %d. %s", id, ex.getMessage()));
        }
        return response.body();
    };

    public static Route getAllLikesAndDislikes = (request, response) -> {
        Long id = parseLong(request.params("id"));
        try {
            List<Long> likes = likeService.getLikes(id, null);
            response.status(200);
            return likes;
        } catch (HaltException ex) {
            log.error("Credentials are invalid");
        } catch (MatchaException ex) {
            log.error("Failed to get all likes and dislikes by user with id: {}", id, ex);
            response.status(400);
            response.body(String.format("Failed to get all likes and dislikes by user with id: %d. %s", id, ex.getMessage()));
        } catch (Exception ex) {
            log.error("An unexpected error occurred while trying to get all likes and dislikes by user with id: {}", id, ex);
            response.status(500);
            response.body(String.format("An unexpected error occurred while trying to get all likes and dislikes " +
                    "by user with id: %d. %s", id, ex.getMessage()));
        }
        return response.body();
    };

    public static Route like = (request, response) -> {
        Long from = parseLong(request.params("from")), to = parseLong(request.params("to"));
        try {
            likeService.like(from, to, true);
            response.status(204);
            return response.body();
        } catch (HaltException ex) {
            log.error("Credentials are invalid");
        } catch (MatchaException ex) {
            log.error("Failed to like (from: {} to: {})", from, to, ex);
            response.status(400);
            response.body(String.format("Failed to like (from: %d to: %d). %s", from, to, ex.getMessage()));
        } catch (Exception ex) {
            log.error("An unexpected error occurred while trying to like (from: {} to: {})", from, to, ex);
            response.status(500);
            response.body(String.format("An unexpected error occurred while trying to like (from: %d to: %d). %s", from, to, ex.getMessage()));
        }
        return response.body();
    };

    public static Route dislike = (request, response) -> {
        Long from = parseLong(request.params("from")), to = parseLong(request.params("to"));
        try {
            likeService.like(from, to, false);
            response.status(204);
            return response.body();
        } catch (HaltException ex) {
            log.error("Credentials are invalid");
        } catch (MatchaException ex) {
            log.error("Failed to dislike (from: {} to: {})", from, to, ex);
            response.status(400);
            response.body(String.format("Failed to dislike (from: %d to: %d). %s", from, to, ex.getMessage()));
        } catch (Exception ex) {
            log.error("An unexpected error occurred while trying to dislike (from: {} to: {})", from, to, ex);
            response.status(500);
            response.body(String.format("An unexpected error occurred while trying to dislike (from: %d to: %d). %s", from, to, ex.getMessage()));
        }
        return response.body();
    };

    public static Route deleteLike = (request, response) -> {
        Long from = parseLong(request.params("from")), to = parseLong(request.params("to"));
        try {
            likeService.deleteLike(from, to, true);
            response.status(204);
            return response.body();
        } catch (HaltException ex) {
            log.error("Credentials are invalid");
        } catch (MatchaException ex) {
            log.error("Failed to delete like (from: {} to: {})", from, to, ex);
            response.status(400);
            response.body(String.format("Failed to delete like (from: %d to: %d). %s", from, to, ex.getMessage()));
        } catch (Exception ex) {
            log.error("An unexpected error occurred while trying to delete like (from: {} to: {})", from, to, ex);
            response.status(500);
            response.body(String.format("An unexpected error occurred while trying to delete like (from: %d to: %d). %s", from, to, ex.getMessage()));
        }
        return response.body();
    };

    public static Route deleteDislike = (request, response) -> {
        Long from = parseLong(request.params("from")), to = parseLong(request.params("to"));
        try {
            likeService.deleteLike(from, to, false);
            response.status(204);
            return response.body();
        } catch (HaltException ex) {
            log.error("Credentials are invalid");
        } catch (MatchaException ex) {
            log.error("Failed to delete dislike (from: {} to: {})", from, to, ex);
            response.status(400);
            response.body(String.format("Failed to delete dislike (from: %d to: %d). %s", from, to, ex.getMessage()));
        } catch (Exception ex) {
            log.error("An unexpected error occurred while trying to delete dislike (from: {} to: {})", from, to, ex);
            response.status(500);
            response.body(String.format("An unexpected error occurred while trying to delete dislike (from: %d to: %d). %s", from, to, ex.getMessage()));
        }
        return response.body();
    };

}
