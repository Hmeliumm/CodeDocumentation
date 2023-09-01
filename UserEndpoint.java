package com.softserve.taf.services.placeholder.endpoints;

import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.softserve.taf.models.enums.HttpStatus;
import com.softserve.taf.models.placeholder.user.UserDto;
import com.softserve.taf.services.common.AbstractWebEndpoint;

/**
 * This class handles endpoints related to users.
 */
public class UserEndpoint extends AbstractWebEndpoint {

    /** Logger instance for logging activities of UserEndpoint. */
    private static final Logger LOGGER = LogManager.getLogger();

    /** Endpoint for accessing all users. */
    private static final String USERS_END = "/users";

    /** Endpoint for accessing specific user by ID. */
    private static final String USERS_RESOURCE_END = "/users/{userID}";

    /**
     * Constructs a UserEndpoint with a given specification.
     *
     * @param specification RequestSpecification instance used for the API call.
     */
    public UserEndpoint(RequestSpecification specification) {
        super(specification);
    }

    /**
     * Create a user with provided details.
     *
     * @param userDto Details of the user to be created.
     * @return UserDto instance representing the created user.
     */
    public UserDto create(UserDto userDto) {
        return create(userDto, HttpStatus.CREATED)
                .extract().as(UserDto.class);
    }

    /**
     * Create a user with provided details and expect a certain HTTP status.
     *
     * @param userDto Details of the user to be created.
     * @param status Expected HTTP status after user creation.
     * @return ValidatableResponse instance with response details.
     */
    public ValidatableResponse create(UserDto userDto, HttpStatus status) {
        LOGGER.info("Create new User");
        return post(
                this.specification,
                USERS_END,
                userDto)
                .statusCode(status.getCode());
    }

    /**
     * Update user details for a specific user ID.
     *
     * @param id ID of the user to be updated.
     * @param userDto Details of the updated user.
     * @return UserDto instance representing the updated user.
     */
    public UserDto update(int id, UserDto userDto) {
        return update(userDto, id, HttpStatus.OK)
                .extract().as(UserDto.class);
    }

    /**
     * Update user details for a specific user ID and expect a certain HTTP status.
     *
     * @param userDto Details of the updated user.
     * @param id ID of the user to be updated.
     * @param status Expected HTTP status after user update.
     * @return ValidatableResponse instance with response details.
     */
    public ValidatableResponse update(UserDto userDto, int id, HttpStatus status) {
        LOGGER.info("Update User by id [{}]", id);
        return put(
                this.specification,
                USERS_RESOURCE_END,
                userDto,
                id)
                .statusCode(status.getCode());
    }

    /**
     * Retrieve a user by a specific ID.
     *
     * @param id ID of the user to be retrieved.
     * @return UserDto instance representing the retrieved user.
     */
    public UserDto getById(String id) {
        return getById(id, HttpStatus.OK)
                .extract().as(UserDto.class);
    }

    /**
     * Retrieve a user by a specific ID and expect a certain HTTP status.
     *
     * @param id ID of the user to be retrieved.
     * @param status Expected HTTP status after user retrieval.
     * @return ValidatableResponse instance with response details.
     */
    public ValidatableResponse getById(String id, HttpStatus status) {
        LOGGER.info("Get User by id [{}]", id);
        return get(
                this.specification,
                USERS_RESOURCE_END,
                id)
                .statusCode(status.getCode());
    }

    /**
     * Retrieve all users.
     *
     * @return List of UserDto instances representing all users.
     */
    public List<UserDto> getAll() {
        return List.of(getAll(HttpStatus.OK).extract().as(UserDto[].class));
    }

    /**
     * Retrieve all users and expect a certain HTTP status.
     *
     * @param status Expected HTTP status after retrieval.
     * @return ValidatableResponse instance with response details.
     */
    public ValidatableResponse getAll(HttpStatus status) {
        LOGGER.info("Get all Users");
        ValidatableResponse response = get(this.specification, USERS_END);
        response.statusCode(status.getCode());
        return response;
    }

}
