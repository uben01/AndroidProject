package utobe.learn2code.util;

@SuppressWarnings("unused")
public enum Constants {
    ABSTRACT_ENTITY_ID("id", String.class),

    LANGUAGE_ENTITY_NAME("language", String.class),
    LANGUAGE_ENTITY_SET_NAME("languages", String.class),
    LANGUAGE_FIELD_ICON("icon", String.class),
    LANGUAGE_FIELD_NAME("name", String.class),
    LANGUAGE_FIELD_CREATED_BY("createdBy", String.class),
    LANGUAGE_FIELD_PUBLISHED("published", String.class),

    PAGE_ENTITY_NAME("page", String.class),
    PAGE_ENTITY_SET_NAME("pages", String.class),
    PAGE_FIELD_A("A", String.class),
    PAGE_FIELD_B("B", String.class),
    PAGE_FIELD_C("C", String.class),
    PAGE_FIELD_CORRECT("correct", Character[].class),
    PAGE_FIELD_D("D", String.class),
    PAGE_FIELD_PARENT("parent", String.class),
    PAGE_FIELD_SERIAL_NUMBER("serialNumber", Long.class),
    PAGE_FIELD_TEXT("text", String.class),
    PAGE_FIELD_TITLE("title", String.class),

    RESULT_ENTITY_NAME("result", String.class),
    RESULT_ENTITY_SET_NAME("results", String.class),
    RESULT_FIELD_RESULT("result", Double.class),
    RESULT_FIELD_TOPIC("topic", String.class),
    RESULT_FIELD_USER("user", String.class),

    TOPIC_ENTITY_NAME("topic", String.class),
    TOPIC_ENTITY_SET_NAME("topics", String.class),
    TOPIC_FIELD_IS_TEST("isTest", Boolean.class),
    TOPIC_FIELD_PARENT("parent", String.class),
    TOPIC_FIELD_SERIAL_NUMBER("serialNumber", Long.class),
    TOPIC_FIELD_TITLE("title", String.class);

    public final String dbName;
    public final Class<?> type;

    Constants(String dbName, Class<?> type) {
        this.dbName = dbName;
        this.type = type;
    }
}