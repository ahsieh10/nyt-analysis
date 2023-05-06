import {
  APIErrorResponse,
  APISuccessResponse,
  Article,
  SuccessDataResult,
} from "../interfaces/interfaces";

export const isResultSuccessResponse = (
  rjson: any
): rjson is APISuccessResponse => {
  if (
    !(
      "result" in rjson &&
      typeof rjson.result === "string" &&
      rjson.result === "success"
    )
  )
    return false;
  if (!("data" in rjson)) return false;
  if (!isSuccessDataResult(rjson.data)) return false;
  return true;
};

export const isSuccessDataResult = (json: any): json is SuccessDataResult => {
  if (!("articles" in json && isArticleArray(json.articles))) return false;
  if (!("biased" in json && isStringArray(json.biased))) return false;
  if (!("sentiment" in json && typeof json.sentiment === "string"))
    return false;
  return true;
};

const isArticleArray = (res: any): res is Article[] => {
  if (Array.isArray(res)) {
    const resArr: any[] = res;
    return resArr.every((elem) => isArticle(elem));
  }
  return false;
};

const isArticle = (res: any): res is Article => {
  if (!("headline" in res && typeof res.headline === "string")) return false;
  if (!("leadParagraph" in res && typeof res.leadParagraph === "string"))
    return false;
  if (!("newsAbstract" in res && typeof res.newsAbstract === "string"))
    return false;
  if (!("snippet" in res && typeof res.snippet === "string")) return false;
  if (!("webUrl" in res && typeof res.webUrl === "string")) return false;
  if (!("keywords" in res && isStringArray(res.keywords))) return false;
  if (!("wordCount" in res && typeof res.wordCount === "number")) return false;
  if (!("thumbnail" in res)) return false;
  return true;
};

const isStringArray = (res: any): res is string[] => {
  if (Array.isArray(res)) {
    const resArr: any[] = res;
    return resArr.every((elem) => typeof elem === "string");
  }
  return false;
};

export const isResultErrorResponse = (
  rjson: any
): rjson is APIErrorResponse => {
  if (!("status" in rjson && typeof rjson.status === "string")) return false;
  if (!("error_message" in rjson && typeof rjson.error_message === "string"))
    return false;
  return true;
};
