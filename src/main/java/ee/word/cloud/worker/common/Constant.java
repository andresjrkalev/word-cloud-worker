package ee.word.cloud.worker.common;

import java.util.Set;

public class Constant {
    public static final Set<String> REDUNDANT_WORDS = Set.of("and", "or", "the", "a");

    public static final String REGEX_SPLIT_MESSAGE = "[\\. ]+";

    public static final String QUEUE = "message_queue";
}
