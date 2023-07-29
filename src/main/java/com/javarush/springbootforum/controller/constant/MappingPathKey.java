package com.javarush.springbootforum.controller.constant;

public interface MappingPathKey {

    //########### CATEGORY CONTROLLER ###########
    String HTTP_CATEGORY_PATH = "/cat";
    String HTTP_CATEGORIES_WITH_TOPICS = "/{id}";
    String HTTP_CATEGORIES_WITH_TOPICS_AND_SUB_CATS = "/{catId}/subcat/{subCatId}";
    String HTTP_CATEGORY_CREATE = "/new";

    //########### HOME CONTROLLER ###########
    String HTTP_HOME_PATH = "/home";
    String HTTP_HOME_PATH_SLASH = "/";
    String HTTP_HOME_PATH_EMPTY = "";

    //########### SECTION CONTROLLER ###########
    String HTTP_SECTIONS_PATH = "/sections";
    String HTTP_SECTION_CREATE = "/new";

    //########### SUBCATEGORY CONTROLLER ###########
    String HTTP_SUBCATEGORY_PATH = "/subcat";
    String HTTP_SUBCATEGORY_CREATE = "/new";

    //########### TOPIC CONTROLLER ###########
    String HTTP_TOPIC_PATH = "/topic";
    String HTTP_TOPIC_WITH_MESSAGES = "/{id}";
    String HTTP_TOPIC_CREATE = "/new";

    //########### TOPIC MESSAGE CONTROLLER ###########
    String HTTP_TOPIC_MESSAGE_PATH = "/topic-message";
    String HTTP_TOPIC_MESSAGE_CREATE = "/new";

    //########### USER CONTROLLER ###########
    String HTTP_USERS_PATH = "/users";
    String HTTP_USERS_FIND_BY_ID = "/{id}";
    String HTTP_USERS_REGISTRATION = "/registration";
    String HTTP_USERS_UPDATE = "/{id}/update";
    String HTTP_USERS_DELETE = "/{id}/delete";

    //*********** REST CONTROLLERS ************

    //########### REST CATEGORY CONTROLLER ###########
    String API_PATH = "/api";
    String REST_CATEGORY_PATH = "/cat";
    String REST_CATEGORY_UPDATE = "/{id}";
    String REST_CATEGORY_DELETE = "/{id}";

    //########### REST SECTION CONTROLLER ###########
    String REST_SECTION_PATH = "/sections";
    String REST_SECTION_UPDATE = "/{id}";
    String REST_SECTION_DELETE = "/{id}";

    //########### REST SUBCATEGORY CONTROLLER ###########
    String REST_SUBCATEGORY_PATH = "/subcat";
    String REST_SUBCATEGORY_UPDATE = "/{id}";
    String REST_SUBCATEGORY_DELETE = "/{id}";

    //########### REST TOPIC MESSAGE CONTROLLER ###########
    String REST_TOPIC_MESSAGE_PATH = "/topic-message";
    String REST_TOPIC_MESSAGE_FIND_BY_ID = "/{id}";
    String REST_TOPIC_MESSAGE_UPDATE = "/{id}";
    String REST_TOPIC_MESSAGE_DELETE = "/{id}";

    //########### REST TOPIC CONTROLLER ###########
    String REST_TOPIC_PATH = "/topic";
    String REST_TOPIC_UPDATE = "/{id}";
    String REST_TOPIC_MOVE_TOPICS_IN_CATEGORY = "/cat/{catId}";
    String REST_TOPIC_MOVE_TOPICS_IN_SUB_CATEGORY = "/cat/{catId}/subCat/{subCatId}";
    String REST_TOPIC_DELETE = "/{id}";
}