// Target article format

// Success response from the NYT or sentiment APIs.
export interface APISuccessResponse {
  result: string;
  data: SuccessDataResult;
}

// Error response from the New York Times or sentiment API. Message written in the back-end
export interface APIErrorResponse {
  status: string;
  error_message: string;
}

/* Main information garnered from a successful query call to the New York Times
API. An array of articles for the sidebar, an array of strings describing
biased sentences, and a string of sentiments depicting the overall bias of articles */
export interface SuccessDataResult {
  articles: Article[];
  biased: string[];
  sentiment: "P+" | "P" | "NEU" | "N" | "N+" | "NONE";
}

// Format of the articles taken from the NYT API
export interface Article {
  headline: string;
  leadParagraph: string;
  newsAbstract: string;
  snippet: string;
  webUrl: string;
  keywords: string[];
  wordCount: number;
  thumbnail: string;
}
