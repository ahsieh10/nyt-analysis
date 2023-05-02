// Target article format

export interface APISuccessResponse {
  result: string;
  data: SuccessDataResult;
}

export interface APIErrorResponse {
  result: string;
  message: string;
}

export interface SuccessDataResult {
  articles: Article[];
  biased: string[];
  sentiment: "P+" | "P" | "NEU" | "N" | "N+" | "NONE";
}

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
