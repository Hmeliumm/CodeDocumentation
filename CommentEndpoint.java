package com.softserve.taf.services.placeholder.endpoints;

import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.softserve.taf.models.enums.HttpStatus;
import com.softserve.taf.models.placeholder.comment.CommentDto;
import com.softserve.taf.services.common.AbstractWebEndpoint;

/**
 * This class handles endpoints related to comments.
 */
public class CommentEndpoint extends AbstractWebEndpoint {

    /** Logger instance for logging activities of CommentEndpoint. */
    private static final Logger LOGGER = LogManager.getLogger();

    /** Endpoint for accessing all comments. */
    private static final String COMMENTS_END = "/comments";

    /** Endpoint for accessing specific comment by ID. */
    private static final String COMMENTS_RESOURCE_END = "/comments/{commentID}";

    /**
     * Constructs a CommentEndpoint with a given specification.
     *
     * @param specification RequestSpecification instance used for the API call.
     */
    public CommentEndpoint(RequestSpecification specification) {
        super(specification);
    }

    /**
     * Create a comment with provided details.
     *
     * @param commentDto Details of the comment to be created.
     * @return CommentDto instance representing the created comment.
     */
    public CommentDto create(CommentDto commentDto) {
        return create(commentDto, HttpStatus.CREATED)
                .extract().as(CommentDto.class);
    }

    /**
     * Create a comment with provided details and expect a certain HTTP status.
     *
     * @param commentDto Details of the comment to be created.
     * @param status Expected HTTP status after comment creation.
     * @return ValidatableResponse instance with response details.
     */
    public ValidatableResponse create(CommentDto commentDto, HttpStatus status) {
        LOGGER.info("Create new Comment");
        return post(
                this.specification,
                COMMENTS_END,
                commentDto)
                .statusCode(status.getCode());
    }

    /**
     * Update comment details for a specific comment ID.
     *
     * @param id ID of the comment to be updated.
     * @param commentDto Details of the updated comment.
     * @return CommentDto instance representing the updated comment.
     */
    public CommentDto update(int id, CommentDto commentDto) {
        return update(commentDto, id, HttpStatus.OK)
                .extract().as(CommentDto.class);
    }

    /**
     * Update comment details for a specific comment ID and expect a certain HTTP status.
     *
     * @param commentDto Details of the updated comment.
     * @param id ID of the comment to be updated.
     * @param status Expected HTTP status after comment update.
     * @return ValidatableResponse instance with response details.
     */
    public ValidatableResponse update(CommentDto commentDto, int id, HttpStatus status) {
        LOGGER.info("Update Comment by id [{}]", id);
        return put(
                this.specification,
                COMMENTS_RESOURCE_END,
                commentDto,
                id)
                .statusCode(status.getCode());
    }

    /**
     * Retrieve a comment by a specific ID.
     *
     * @param id ID of the comment to be retrieved.
     * @return CommentDto instance representing the retrieved comment.
     */
    public CommentDto getById(int id) {
        return getById(id, HttpStatus.OK)
                .extract().as(CommentDto.class);
    }

    /**
     * Retrieve a comment by a specific ID and expect a certain HTTP status.
     *
     * @param id ID of the comment to be retrieved.
     * @param status Expected HTTP status after comment retrieval.
     * @return ValidatableResponse instance with response details.
     */
    public ValidatableResponse getById(int id, HttpStatus status) {
        LOGGER.info("Get Comment by id [{}]", id);
        return get(
                this.specification,
                COMMENTS_RESOURCE_END,
                String.valueOf(id))
                .statusCode(status.getCode());
    }

    /**
     * Retrieve all comments.
     *
     * @return List of CommentDto instances representing all comments.
     */
    public List<CommentDto> getAll() {
        return List.of(getAll(HttpStatus.OK).extract().as(CommentDto[].class));
    }

    /**
     * Retrieve all comments and expect a certain HTTP status.
     *
     * @param status Expected HTTP status after retrieval.
     * @return ValidatableResponse instance with response details.
     */
    public ValidatableResponse getAll(HttpStatus status) {
        LOGGER.info("Get all Comments");
        ValidatableResponse response = get(this.specification, COMMENTS_END);
        response.statusCode(status.getCode());
        return response;
    }
}
