package com.javarush.springbootforum.controller.constant;

public interface MappingPathKey {

    //########### CATEGORY CONTROLLER ###########
    String CATEGORY_PATH = "/cat";
    String CATEGORIES_WITH_TOPICS = "/{id}";
    String CATEGORIES_WITH_TOPICS_AND_SUB_CATS = "/{catId}/subcat/{subCatId}";
    String CATEGORY_CREATE = "/new";

    //########### HOME CONTROLLER ###########
    String HOME_PATH = "/home";
    String HOME_PATH_SLASH = "/";
    String HOME_PATH_EMPTY = "";

    //########### SECTION CONTROLLER ###########
    String SECTIONS_PATH = "/sections";
    String SECTION_CREATE = "/new";

    //########### SUBCATEGORY CONTROLLER ###########
    String SUBCATEGORY_PATH = "/subcat";
    String SUBCATEGORY_CREATE = "/new";

    //########### TOPIC CONTROLLER ###########
    String TOPIC_PATH = "/topic";
    String TOPIC_WITH_MESSAGES = "/{id}";
    String TOPIC_CREATE = "/new";

    //########### TOPIC MESSAGE CONTROLLER ###########
    String TOPIC_MESSAGE_PATH = "/topic-message";
    String TOPIC_MESSAGE_CREATE = "/new";

    //########### USER CONTROLLER ###########
    String USERS_PATH = "/users";
    String USERS_FIND_BY_ID = "/{id}";
    String USERS_REGISTRATION = "/registration";
    String USERS_UPDATE = "/{id}/update";
    String USERS_DELETE = "/{id}/delete";

    //########### TOPIC CONTROLLER ###########



}
