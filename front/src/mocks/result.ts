import { Result } from "../interfaces/interfaces";
import { mockArticleData } from "./articles";
import { mockWords } from "./words";

export const mockResult: Result = {
  query: "",
  articles: mockArticleData,
  overallSentiment: "positive",
  mostBiasedSentences: [
    "This is a positive sentence.",
    "There are only good things in this sentence.",
  ],
  keywords: mockWords,
};
