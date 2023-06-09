package edu.brown.cs32.mocks;

/**
 * Class containing mocks used for testing MC part of pipeline
 */
public class MockSentimentJson {

    public final static String NYTSentence = "For years, Xi Jinping, China’s leader, has railed against greed and corruption in the country’s financial sector, making an example of a few prominent figures along the way.TAIPEI, Taiwan — A Taiwan-based publisher who disappeared while in China has been detained for suspected violations of security laws, Chinese authorities confirmed on Wednesday, fanning concerns in Taiwan that Beijing is sending a warning to the island’s vibrant publishing sector.";
    public final static String NYTSegment2 = "TAIPEI, Taiwan — A Taiwan-based publisher who disappeared while in China has been detained for suspected violations of security laws, Chinese authorities confirmed on Wednesday, fanning concerns in Taiwan that Beijing is sending a warning to the island’s vibrant publishing sector.";
    public final static String NYTSegment1 = "WASHINGTON — Treasury Secretary Janet L. Yellen on Thursday called for a “constructive” and “healthy” economic relationship between the United States and China, one in which the two nations could work together to confront global challenges in spite of their conflicting national security interests.";
    public final static String NYTText = "Shirt fits weird.My shirt shrunk.The shirt costs too much for the quality.";

    public final static String intMockJson = "{\""
        + "agreement\":\"AGREEMENT\","
        + "\"confidence\":\"98\","
        + "\"irony\":\"NONIRONIC\","
        + "\"model\":\"general_en\","
        + "\"score_tag\":\"P\","
        + "\"sentence_list\":["
        + "{\"agreement\":\"AGREEMENT\",\"bop\":\"y\",\"confidence\":\"92\",\"endp\":\"29\",\"inip\":\"0\",\"score_tag\":\"NEU\",\"segment_list\":[{\"agreement\":\"AGREEMENT\",\"confidence\":\"92\",\"endp\":\"28\",\"inip\":\"0\",\"polarity_term_list\":[{\"confidence\":\"92\",\"endp\":\"28\",\"inip\":\"25\",\"score_tag\":\"N\",\"sentimented_concept_list\":[{\"endp\":\"8\",\"form\":\"food\",\"id\":\"771eeaec8b\",\"inip\":\"5\",\"score_tag\":\"N\",\"type\":\"Top\\u003eProduct\\u003eFood\",\"variant\":\"food\"}],\"text\":\"(does not taste) good\"}],\"score_tag\":\"N\",\"segment_type\":\"main\",\"text\":\"This food does not taste good\"}],\"sentimented_concept_list\":[{\"form\":\"food\",\"id\":\"771eeaec8b\",\"score_tag\":\"N\",\"type\":\"Top\\u003eProduct\\u003eFood\"}],\"sentimented_entity_list\":[],\"text\":\"" + NYTSegment1 +"\"},"
        + "{\"agreement\":"
        + "\"AGREEMENT\","
        + "\"bop\":\"y\","
        + "\"confidence\":\"98\","
        + "\"endp\":\"18\","
        + "\"inip\":\"0\","
        + "\"score_tag\":\"P\","
        + "\"segment_list\":["
        + "{\"agreement\":\"AGREEMENT\","
        + "\"confidence\":\"98\","
        + "\"endp\":\"18\","
        + "\"inip\":\"0\","
        + "\"polarity_term_list\":[{\"confidence\":\"98\",\"endp\":\"18\",\"inip\":\"15\",\"score_tag\":\"P+\",\"text\":\"constructive and healthy\"}],"
        + "\"score_tag\":\"NEU\",\"segment_type\":\"main\",\"text\":\"constructive and healthy\"}],\"sentimented_concept_list\":[],\"sentimented_entity_list\":[],\"text\":\"" + NYTSegment2
        + "\"}],"
        + "\"sentimented_concept_list\":[],\"sentimented_entity_list\":[],"
        + "\"status\":{\"code\":\"0\",\"msg\":\"OK\",\"credits\":\"1\",\"remaining_credits\":\"19984\"},\"subjectivity\":\"SUBJECTIVE\"}";

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
        + "\"polarity_term_list\":[{\"confidence\":\"98\",\"endp\":\"18\",\"inip\":\"15\",\"score_tag\":\"P+\",\"text\":\"(really) good\"}],"
        + "\"score_tag\":\"P+\",\"segment_type\":\"main\",\"text\":\"this is really good\"}],\"sentimented_concept_list\":[],\"sentimented_entity_list\":[],\"text\":\"this is really good\"}],"
        + "\"sentimented_concept_list\":[],\"sentimented_entity_list\":[],"
        + "\"status\":{\"code\":\"0\",\"msg\":\"OK\",\"credits\":\"1\",\"remaining_credits\":\"19984\"},\"subjectivity\":\"SUBJECTIVE\"}";

    public final static String medMockJson = "{\"agreement\":\"DISAGREEMENT\","
        + "\"confidence\":\"86\","
        + "\"irony\":\"NONIRONIC\","
        + "\"model\":\"general_en\","
        + "\"score_tag\":\"N\","
        + "\"sentence_list\":["
        + "{\"agreement\":\"AGREEMENT\",\"bop\":\"y\",\"confidence\":\"92\",\"endp\":\"29\",\"inip\":\"0\",\"score_tag\":\"N\",\"segment_list\":[{\"agreement\":\"AGREEMENT\",\"confidence\":\"92\",\"endp\":\"28\",\"inip\":\"0\",\"polarity_term_list\":[{\"confidence\":\"92\",\"endp\":\"28\",\"inip\":\"25\",\"score_tag\":\"N\",\"sentimented_concept_list\":[{\"endp\":\"8\",\"form\":\"food\",\"id\":\"771eeaec8b\",\"inip\":\"5\",\"score_tag\":\"N\",\"type\":\"Top\\u003eProduct\\u003eFood\",\"variant\":\"food\"}],\"text\":\"(does not taste) good\"}],\"score_tag\":\"N\",\"segment_type\":\"main\",\"text\":\"This food does not taste good\"}],\"sentimented_concept_list\":[{\"form\":\"food\",\"id\":\"771eeaec8b\",\"score_tag\":\"N\",\"type\":\"Top\\u003eProduct\\u003eFood\"}],\"sentimented_entity_list\":[],\"text\":\"This food does not taste good.\"},"
        + "{\"agreement\":\"AGREEMENT\",\"bop\":\"n\",\"confidence\":\"100\",\"endp\":\"52\",\"inip\":\"31\",\"score_tag\":\"P\",\"segment_list\":[{\"agreement\":\"AGREEMENT\",\"confidence\":\"100\",\"endp\":\"35\",\"inip\":\"31\",\"polarity_term_list\":[{\"confidence\":\"100\",\"endp\":\"33\",\"inip\":\"31\",\"score_tag\":\"P\",\"text\":\"win\"}],\"score_tag\":\"P\",\"segment_type\":\"main\",\"text\":\"Won t\"},{\"agreement\":\"AGREEMENT\",\"confidence\":\"100\",\"endp\":\"51\",\"inip\":\"37\",\"polarity_term_list\":[],\"score_tag\":\"NONE\",\"segment_type\":\"secondary\",\"text\":\"come back again\"}],\"sentimented_concept_list\":[],\"sentimented_entity_list\":[],\"text\":\"Won t come back again.\"},"
        + "{\"agreement\":\"AGREEMENT\",\"bop\":\"n\",\"confidence\":\"100\",\"endp\":\"91\",\"inip\":\"53\",\"score_tag\":\"P\",\"segment_list\":[{\"agreement\":\"AGREEMENT\",\"confidence\":\"97\",\"endp\":\"91\",\"inip\":\"53\",\"polarity_term_list\":[{\"confidence\":\"97\",\"endp\":\"90\",\"inip\":\"85\",\"score_tag\":\"P\",\"text\":\"good\"}],\"score_tag\":\"P\",\"segment_type\":\"main\",\"text\":\"The service could not have been better!\"}],\"sentimented_concept_list\":[],\"sentimented_entity_list\":[],\"text\":\"The service could not have been better!\"},"
        + "{\"agreement\":\"AGREEMENT\",\"bop\":\"n\",\"confidence\":\"100\",\"endp\":\"121\",\"inip\":\"93\",\"score_tag\":\"N\",\"segment_list\":[{\"agreement\":\"AGREEMENT\",\"confidence\":\"95\",\"endp\":\"120\",\"inip\":\"97\",\"polarity_term_list\":[{\"confidence\":\"95\",\"endp\":\"110\",\"inip\":\"107\",\"score_tag\":\"N\",\"text\":\"rude\"}],\"score_tag\":\"N\",\"segment_type\":\"main\",\"text\":\"they were rude to others\"}],\"sentimented_concept_list\":[],\"sentimented_entity_list\":[],\"text\":\"But they were rude to others.\"},"
        + "{\"agreement\":\"AGREEMENT\",\"bop\":\"n\",\"confidence\":\"100\",\"endp\":\"171\",\"inip\":\"122\",\"score_tag\":\"N\",\"segment_list\":[{\"agreement\":\"AGREEMENT\",\"confidence\":\"100\",\"endp\":\"170\",\"inip\":\"122\",\"polarity_term_list\":[{\"confidence\":\"100\",\"endp\":\"135\",\"inip\":\"128\",\"score_tag\":\"N\",\"text\":\"too much\"}],\"score_tag\":\"N\",\"segment_type\":\"main\",\"sentimented_concept_list\":[{\"endp\":\"150\",\"form\":\"food\",\"id\":\"771eeaec8b\",\"inip\":\"147\",\"score_tag\":\"NONE\",\"type\":\"Top\\u003eProduct\\u003eFood\",\"variant\":\"food\"}],\"text\":\"Spent too much money for food that made me hungry\"}],\"sentimented_concept_list\":[{\"form\":\"food\",\"id\":\"771eeaec8b\",\"score_tag\":\"NONE\",\"type\":\"Top\\u003eProduct\\u003eFood\"}],\"sentimented_entity_list\":[],\"text\":\"Spent too much money for food that made me hungry.\"}],\"sentimented_concept_list\":[{\"form\":\"food\",\"id\":\"771eeaec8b\",\"score_tag\":\"N\",\"type\":\"Top\\u003eProduct\\u003eFood\"}],\"sentimented_entity_list\":[],\"status\":{\"code\":\"0\",\"msg\":\"OK\",\"credits\":\"1\",\"remaining_credits\":\"19980\"},\"subjectivity\":\"SUBJECTIVE\"}";

    public final static String bigMockJson = "{\"agreement\":\"DISAGREEMENT\","
        + "\"confidence\":\"94\","
        + "\"irony\":\"NONIRONIC\","
        + "\"model\":\"general_en\","
        + "\"score_tag\":\"P\","
        + "\"sentence_list\":["
        + "{\"confidence\":\"92\", \"score_tag\":\"P\", \"text\":\"Spent the day at the park, found a lucky penny, and now I have to go home but I would rather not\", "
        + "\"segment_list\":["
        + "{\"polarity_term_list\":[]},"
        + "{\"polarity_term_list\":[{\"text\":\"lucky\"}]},"
        + "{\"polarity_term_list\":[{\"text\":\"would rather not\"}]}]},"
        + "{\"confidence\":\"97\", \"score_tag\":\"P\", \"text\":\"Wow this could have been so good but I wish there was more to it\", "
        + "\"segment_list\":["
        + "{\"polarity_term_list\":[{\"text\":\"Wow\"},{\"text\":\"so good\"}]},"
        + "{\"polarity_term_list\":[]}]},"
        + "{\"confidence\":\"99\", \"score_tag\":\"N\", \"text\":\"Spent too much money for food that made me hungry but there was a good environment if not for the mediocre view\", "
        + "\"segment_list\":["
        + "{\"polarity_term_list\":[{\"text\":\"too much money\"}]},"
        + "{\"polarity_term_list\":[{\"text\":\"good environment\"}, {\"text\":\"mediocre\"}]}]}]}";

    public final static String NYTMockJson = "{\"agreement\":\"DISAGREEMENT\","
        + "\"confidence\":\"94\","
        + "\"irony\":\"NONIRONIC\","
        + "\"model\":\"general_en\","
        + "\"score_tag\":\"N\","
        + "\"sentence_list\":["
        + "{\"confidence\":\"94\", \"score_tag\":\"N\", \"text\":\"Shirt fits weird.\", "
        + "\"segment_list\":["
        + "{\"polarity_term_list\":[{\"text\":\"weird\"}]}]},"
        + "{\"confidence\":\"99\", \"score_tag\":\"NEU\", \"text\":\"My shirt shrunk.\", "
        + "\"segment_list\":["
        + "{\"polarity_term_list\":[]}]},"
        + "{\"confidence\":\"90\", \"score_tag\":\"N\", \"text\":\"The shirt costs too much for the quality.\", "
        + "\"segment_list\":["
        + "{\"polarity_term_list\":[{\"text\":\"too much\"}]}]}]}";
}
