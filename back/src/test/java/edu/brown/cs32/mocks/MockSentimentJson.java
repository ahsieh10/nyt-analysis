package edu.brown.cs32.mocks;

public class MockSentimentJson {
    public final static String smallMockJson = "{\""
        + "agreement\":\"AGREEMENT\","
        + "\"confidence\":\"98\","
        + "\"irony\":\"NONIRONIC\","
        + "\"model\":\"general_en\","
        + "\"score_tag\":\"P+\","
        + "\"sentence_list\":["
        + "{\"agreement\":"
        + "\"AGREEMENT\","
        + "\"bop\":\"y\","
        + "\"confidence\":\"98\","
        + "\"endp\":\"18\","
        + "\"inip\":\"0\","
        + "\"score_tag\":\"P+\","
        + "\"segment_list\":["
        + "{\"agreement\":\"AGREEMENT\","
        + "\"confidence\":\"98\","
        + "\"endp\":\"18\","
        + "\"inip\":\"0\","
        + "\"polarity_term_list\":[{\"confidence\":\"98\",\"endp\":\"18\",\"inip\":\"15\",\"score_tag\":\"P+\",\"text\":\"(really) good\"}],\"score_tag\":\"P+\",\"segment_type\":\"main\",\"text\":\"this is really good\"}],\"sentimented_concept_list\":[],\"sentimented_entity_list\":[],\"text\":\"this is really good\"}],\"sentimented_concept_list\":[],\"sentimented_entity_list\":[],\"status\":{\"code\":\"0\",\"msg\":\"OK\",\"credits\":\"1\",\"remaining_credits\":\"19984\"},\"subjectivity\":\"SUBJECTIVE\"}";

    public final static String medMockJson = "{\"agreement\":\"DISAGREEMENT\","
        + "\"confidence\":\"86\","
        + "\"irony\":\"NONIRONIC\","
        + "\"model\":\"general_en\","
        + "\"score_tag\":\"N\","
        + "\"sentence_list\":["
        + "{\"agreement\":\"AGREEMENT\",\"bop\":\"y\",\"confidence\":\"92\",\"endp\":\"29\",\"inip\":\"0\",\"score_tag\":\"N\",\"segment_list\":[{\"agreement\":\"AGREEMENT\",\"confidence\":\"92\",\"endp\":\"28\",\"inip\":\"0\",\"polarity_term_list\":[{\"confidence\":\"92\",\"endp\":\"28\",\"inip\":\"25\",\"score_tag\":\"N\",\"sentimented_concept_list\":[{\"endp\":\"8\",\"form\":\"food\",\"id\":\"771eeaec8b\",\"inip\":\"5\",\"score_tag\":\"N\",\"type\":\"Top\\u003eProduct\\u003eFood\",\"variant\":\"food\"}],\"text\":\"(does not taste) good\"}],\"score_tag\":\"N\",\"segment_type\":\"main\",\"text\":\"This food does not taste good\"}],\"sentimented_concept_list\":[{\"form\":\"food\",\"id\":\"771eeaec8b\",\"score_tag\":\"N\",\"type\":\"Top\\u003eProduct\\u003eFood\"}],\"sentimented_entity_list\":[],\"text\":\"This food does not taste good.\"},"
        + "{\"agreement\":\"AGREEMENT\",\"bop\":\"n\",\"confidence\":\"100\",\"endp\":\"52\",\"inip\":\"31\",\"score_tag\":\"P\",\"segment_list\":[{\"agreement\":\"AGREEMENT\",\"confidence\":\"100\",\"endp\":\"35\",\"inip\":\"31\",\"polarity_term_list\":[{\"confidence\":\"100\",\"endp\":\"33\",\"inip\":\"31\",\"score_tag\":\"P\",\"text\":\"win\"}],\"score_tag\":\"P\",\"segment_type\":\"main\",\"text\":\"Won t\"},{\"agreement\":\"AGREEMENT\",\"confidence\":\"100\",\"endp\":\"51\",\"inip\":\"37\",\"polarity_term_list\":[],\"score_tag\":\"NONE\",\"segment_type\":\"secondary\",\"text\":\"come back again\"}],\"sentimented_concept_list\":[],\"sentimented_entity_list\":[],\"text\":\"Won t come back again.\"},"
        + "{\"agreement\":\"AGREEMENT\",\"bop\":\"n\",\"confidence\":\"100\",\"endp\":\"91\",\"inip\":\"53\",\"score_tag\":\"P\",\"segment_list\":[{\"agreement\":\"AGREEMENT\",\"confidence\":\"100\",\"endp\":\"91\",\"inip\":\"53\",\"polarity_term_list\":[{\"confidence\":\"100\",\"endp\":\"90\",\"inip\":\"85\",\"score_tag\":\"P\",\"text\":\"good\"}],\"score_tag\":\"P\",\"segment_type\":\"main\",\"text\":\"The service could not have been better!\"}],\"sentimented_concept_list\":[],\"sentimented_entity_list\":[],\"text\":\"The service could not have been better!\"},"
        + "{\"agreement\":\"AGREEMENT\",\"bop\":\"n\",\"confidence\":\"100\",\"endp\":\"121\",\"inip\":\"93\",\"score_tag\":\"N\",\"segment_list\":[{\"agreement\":\"AGREEMENT\",\"confidence\":\"100\",\"endp\":\"120\",\"inip\":\"97\",\"polarity_term_list\":[{\"confidence\":\"100\",\"endp\":\"110\",\"inip\":\"107\",\"score_tag\":\"N\",\"text\":\"rude\"}],\"score_tag\":\"N\",\"segment_type\":\"main\",\"text\":\"they were rude to others\"}],\"sentimented_concept_list\":[],\"sentimented_entity_list\":[],\"text\":\"But they were rude to others.\"},"
        + "{\"agreement\":\"AGREEMENT\",\"bop\":\"n\",\"confidence\":\"100\",\"endp\":\"171\",\"inip\":\"122\",\"score_tag\":\"N\",\"segment_list\":[{\"agreement\":\"AGREEMENT\",\"confidence\":\"100\",\"endp\":\"170\",\"inip\":\"122\",\"polarity_term_list\":[{\"confidence\":\"100\",\"endp\":\"135\",\"inip\":\"128\",\"score_tag\":\"N\",\"text\":\"too much\"}],\"score_tag\":\"N\",\"segment_type\":\"main\",\"sentimented_concept_list\":[{\"endp\":\"150\",\"form\":\"food\",\"id\":\"771eeaec8b\",\"inip\":\"147\",\"score_tag\":\"NONE\",\"type\":\"Top\\u003eProduct\\u003eFood\",\"variant\":\"food\"}],\"text\":\"Spent too much money for food that made me hungry\"}],\"sentimented_concept_list\":[{\"form\":\"food\",\"id\":\"771eeaec8b\",\"score_tag\":\"NONE\",\"type\":\"Top\\u003eProduct\\u003eFood\"}],\"sentimented_entity_list\":[],\"text\":\"Spent too much money for food that made me hungry.\"}],\"sentimented_concept_list\":[{\"form\":\"food\",\"id\":\"771eeaec8b\",\"score_tag\":\"N\",\"type\":\"Top\\u003eProduct\\u003eFood\"}],\"sentimented_entity_list\":[],\"status\":{\"code\":\"0\",\"msg\":\"OK\",\"credits\":\"1\",\"remaining_credits\":\"19980\"},\"subjectivity\":\"SUBJECTIVE\"}";
}
