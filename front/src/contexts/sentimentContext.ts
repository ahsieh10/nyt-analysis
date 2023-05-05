import { createContext } from "react";
import { Sentiment } from "../constants/constants";

export const SentimentContext = createContext<Sentiment>(Sentiment.POSITIVE);