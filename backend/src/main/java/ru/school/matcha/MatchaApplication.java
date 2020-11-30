package ru.school.matcha;

import lombok.extern.slf4j.Slf4j;
import ru.school.matcha.controllers.*;
import ru.school.matcha.converters.*;
import ru.school.matcha.enums.Cors;
import ru.school.matcha.enums.Path;
import ru.school.matcha.handlers.ChatWebSocketHandler;
import ru.school.matcha.handlers.ExceptionHandler;
import ru.school.matcha.utils.GoogleDrive;
import ru.school.matcha.utils.MailUtil;

import static spark.Spark.*;

@Slf4j
public class MatchaApplication {

    public static void main(String[] args) {
        port(8080);
        webSocket(Path.SOCKET.getUrl(), ChatWebSocketHandler.class);
        enableCORS();
        ExceptionHandler.enable();
        GoogleDrive.run();
        MailUtil.initMail();
        path(Path.API.getUrl(), () -> {
            path(Path.AUTH.getUrl(), () ->
                    post("/login", AuthenticateController.authenticate, new JsonTransformer()));
            post(Path.USERS.getUrl(), UserController.createUser, new JsonTransformer());
            path(Path.USERS.getUrl(), () -> {
                post("/batch", UserController.batchUsersCreate, new JsonTransformer());
                get("/", UserController.getAllUsers, new JsonTransformer());
                get("/:id", UserController.getUserById, new JsonTransformer());
                get("/username/:username", UserController.getUserByUsername, new JsonTransformer());
                get("/:id/dislikes", LikeController.getDislikes, new JsonTransformer());
                get("/:id/likes", LikeController.getLikes, new JsonTransformer());
                get("/:id/likesDislikes", LikeController.getAllLikesAndDislikes, new JsonTransformer());
                get("/:id/guests", GuestController.getGuestsByUserId, new JsonTransformer());
                get("/:id/images", ImageController.getImagesByUserId, new JsonTransformer());
                get("/:id/tags", TagController.getTagsByUserId, new JsonTransformer());
                post("/:userId/tags/:tagName", TagController.createTag, new JsonTransformer());
                delete("/:userId/tags/:tagName", TagController.deleteTag, new JsonTransformer());
                put("/", UserController.updateUser, new JsonTransformer());
                delete("/:id", UserController.deleteUserById, new JsonTransformer());
                delete("/username/:username", UserController.deleteUserByUsername, new JsonTransformer());
                path(Path.LIKES.getUrl(), () -> {
                    post("/from/:from/to/:to", LikeController.createLike, new JsonTransformer());
                    delete("/from/:from/to/:to", LikeController.deleteLike, new JsonTransformer());
                });
                path(Path.DISLIKES.getUrl(), () -> {
                    post("/from/:from/to/:to", LikeController.createDislike, new JsonTransformer());
                    delete("/from/:from/to/:to", LikeController.deleteDislike, new JsonTransformer());
                });
                path(Path.TAGS.getUrl(), () -> {
                    get("/", TagController.getTags, new JsonTransformer());
                    get("/:tagName", UserController.getUsersByTagName, new JsonTransformer());
                    delete("/:id", TagController.deleteTagById, new JsonTransformer());
                });
                path(Path.GUESTS.getUrl(), () -> {
                    post("/from/:from/to/:to", GuestController.createGuest, new JsonTransformer());
                    delete("/from/:from/to/:to", GuestController.deleteGuest, new JsonTransformer());
                });
                path(Path.IMAGES.getUrl(), () -> {
                    post("/", ImageController.createImage, new JsonTransformer());
                    get("/:id", ImageController.getImageById, new JsonTransformer());
                    delete("/:id", ImageController.deleteImageById, new JsonTransformer());
                });
            });
            path(Path.FORMS.getUrl(), () -> {
                post("/", FormController.createForm, new JsonTransformer());
                get("/all", FormController.getAllForms, new JsonTransformer());
                get("/:id", FormController.getFormById, new JsonTransformer());
                put("/", FormController.updateForm, new JsonTransformer());
                delete("/:id", FormController.deleteFormById, new JsonTransformer());
            });
        });
    }

    private static void enableCORS() {
        before((request, response) -> {
            response.header(Cors.ALLOW_ORIGIN.getHeader(), Cors.ALLOW_ORIGIN.getContent());
            response.header(Cors.ALLOW_METHODS.getHeader(), Cors.ALLOW_METHODS.getContent());
            response.type(Cors.TYPE.getContent());
        });
        options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers(Cors.REQUEST_HEADERS.getHeader());
            if (accessControlRequestHeaders != null) {
                response.header(Cors.ALLOW_HEADERS.getHeader(), Cors.ALLOW_HEADERS.getContent());
            }
            response.header(Cors.EXPOSE_HEADERS.getHeader(), Cors.EXPOSE_HEADERS.getContent());
            response.header(Cors.MAX_AGE.getHeader(), Cors.MAX_AGE.getContent());
            return Cors.RESULT.getContent();
        });
    }

}
