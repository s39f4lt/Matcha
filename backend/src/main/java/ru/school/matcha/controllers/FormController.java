package ru.school.matcha.controllers;

import lombok.extern.slf4j.Slf4j;
import ru.school.matcha.converters.Converter;
import ru.school.matcha.converters.FormConverter;
import ru.school.matcha.domain.Form;
import ru.school.matcha.dto.FormDto;
import ru.school.matcha.exceptions.MatchaException;
import ru.school.matcha.serializators.Serializer;
import ru.school.matcha.services.FormServiceImpl;
import ru.school.matcha.services.interfaces.FormService;
import spark.HaltException;
import spark.Route;

import java.util.List;

import static java.lang.Long.parseLong;
import static spark.Spark.halt;

@Slf4j
public class FormController {

    private static final Converter<FormDto, Form> formConverter;
    private static final FormService formService;

    static {
        formService = new FormServiceImpl();
        formConverter = new FormConverter();
    }

    public static Route createForm = (request, response) -> {
        try {
            AuthorizationController.authorize(request);
            Serializer<FormDto> serializer = new Serializer<>();
            FormDto formDto = serializer.deserialize(request.body(), FormDto.class);
            Form form = formConverter.convertFromDto(formDto);
            Long formId = formService.createForm(form);
            response.status(200);
            return formId;
        } catch (HaltException ex) {
            log.error("Credentials are invalid");
            response.status(ex.statusCode());
            response.body(ex.body());
        } catch (MatchaException ex) {
            log.error("Failed to create form", ex);
            response.status(400);
            response.body(String.format("Failed to create form. %s", ex.getMessage()));
        } catch (Exception ex) {
            log.error("An unexpected error occurred while trying to create form", ex);
            response.status(500);
            response.body(String.format("An unexpected error occurred while trying to create form. %s", ex.getMessage()));
        }
        return response.body();
    };

    public static Route getAllForms = (request, response) -> {
        try {
            AuthorizationController.authorize(request);
            List<Form> forms = formService.getAllForms();
            List<FormDto> result = formConverter.createFromEntities(forms);
            response.status(200);
            return result;
        } catch (HaltException ex) {
            log.error("Credentials are invalid");
            response.status(ex.statusCode());
            response.body(ex.body());
        } catch (MatchaException ex) {
            log.error("Failed to get all forms", ex);
            response.status(400);
            response.body(String.format("Failed to get all forms. %s", ex.getMessage()));
        } catch (Exception ex) {
            log.error("An unexpected error occurred while trying to get all forms", ex);
            response.status(500);
            response.body(String.format("An unexpected error occurred while trying to get all forms. %s", ex.getMessage()));
        }
        return response.body();
    };

    public static Route getFormById = (request, response) -> {
        Long id = parseLong(request.params("id"));
        try {
            AuthorizationController.authorize(request);
            Form form = formService.getFormById(id)
                    .orElseThrow(() -> new MatchaException(String.format("Form with id: %s doesn't exist", id)));
            FormDto result = formConverter.convertFromEntity(form);
            response.status(200);
            return result;
        } catch (HaltException ex) {
            log.error("Credentials are invalid");
            response.status(ex.statusCode());
            response.body(ex.body());
        } catch (MatchaException ex) {
            log.error("Failed to get form by id: {}", id, ex);
            response.status(400);
            response.body(String.format("Failed to get form by id: %d. %s", id, ex.getMessage()));
        } catch (Exception ex) {
            log.error("An unexpected error occurred while trying to get form by id: {}", id, ex);
            response.status(500);
            response.body(String.format("An unexpected error occurred while trying to get form by id: %d. %s", id, ex.getMessage()));
        }
        return response.body();
    };

    public static Route getFormByUserId = (request, response) -> {
        Long userId = parseLong(request.params("id"));
        try {
            AuthorizationController.authorize(request);
            Form form = formService.getFormByUserId(userId)
                    .orElseThrow(() -> new MatchaException(String.format("Form by user with id %d doesn't exist", userId)));
            FormDto result = formConverter.convertFromEntity(form);
            response.status(200);
            return result;
        } catch (HaltException ex) {
            log.error("Credentials are invalid");
            response.status(ex.statusCode());
            response.body(ex.body());
        } catch (MatchaException ex) {
            log.error("Failed to get form by user id: {}", userId, ex);
            response.status(400);
            response.body(String.format("Failed to get form by user id: %d. %s", userId, ex.getMessage()));
        } catch (Exception ex) {
            log.error("An unexpected error occurred while trying to get form by user id: {}", userId, ex);
            response.status(500);
            response.body(String.format("An unexpected error occurred while trying to get form by user id: %d. %s", userId, ex.getMessage()));
        }
        return response.body();
    };

    public static Route updateFormByUserId = (request, response) -> {
        Long userId = parseLong(request.params("userId"));
        try {
            AuthorizationController.authorize(request);
            Serializer<FormDto> serializer = new Serializer<>();
            FormDto formDto = serializer.deserialize(request.body(), FormDto.class);
            Form form = formConverter.convertFromDto(formDto);
            formService.updateForm(form, userId);
            response.status(204);
        } catch (HaltException ex) {
            log.error("Credentials are invalid");
            response.status(ex.statusCode());
            response.body(ex.body());
        } catch (MatchaException ex) {
            log.error("Failed to update form", ex);
            response.status(400);
            response.body(String.format("Failed to update form. %s", ex.getMessage()));
        } catch (Exception ex) {
            log.error("An unexpected error occurred while trying to update form", ex);
            response.status(500);
            response.body(String.format("An unexpected error occurred while trying to update form. %s", ex.getMessage()));
        }
        return response.body();
    };

    public static Route deleteFormById = (request, response) -> {
        Long id = parseLong(request.params("id"));
        try {
            AuthorizationController.authorize(request);
            formService.deleteFormById(id);
            response.status(204);
        } catch (HaltException ex) {
            log.error("Credentials are invalid");
            response.status(ex.statusCode());
            response.body(ex.body());
        } catch (MatchaException ex) {
            log.error("Failed to delete form by id: {}", id, ex);
            response.status(400);
            response.body(String.format("Failed to delete form by id: %d. %s", id, ex.getMessage()));
        } catch (Exception ex) {
            log.error("An unexpected error occurred while trying to delete form by id: {}", id, ex);
            response.status(500);
            response.body(String.format("An unexpected error occurred while trying to delete form by id: %d. %s", id, ex.getMessage()));
        }
        return response.body();
    };

    public static Route deleteFormByUserId = (request, response) -> {
        Long userId = parseLong(request.params("id"));
        try {
            AuthorizationController.authorize(request);
            formService.deleteFormByUserId(userId);
            response.status(204);
        } catch (HaltException ex) {
            log.error("Credentials are invalid");
            response.status(ex.statusCode());
            response.body(ex.body());
        } catch (MatchaException ex) {
            log.error("Failed to delete form by user id: {}", userId, ex);
            response.status(400);
            response.body(String.format("Failed to delete form by user id: %d. %s", userId, ex.getMessage()));
        } catch (Exception ex) {
            log.error("An unexpected error occurred while trying to delete form by user id: {}", userId, ex);
            response.status(500);
            response.body(String.format("An unexpected error occurred while trying to delete form by user id: %d. %s", userId, ex.getMessage()));
        }
        return response.body();
    };

}
