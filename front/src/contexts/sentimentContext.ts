import { createContext } from "react";
import { Sentiment } from "../enums/enums";

/* 
This context enables the application to keep track of the current sentiment of the query
to sync the UI visually with the sentiment.
*/
export const SentimentContext = createContext<Sentiment>(Sentiment.POSITIVE);
