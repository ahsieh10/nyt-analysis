import {
  APIErrorResponse,
  APISuccessResponse,
  SuccessDataResult,
} from "../interfaces/interfaces";

export const isResultSuccessResponse = (
  rjson: any
): rjson is APISuccessResponse => {
  if (!("result" in rjson)) return false;
  if (rjson.result !== "success") return false;
  if (!("data" in rjson)) return false;
  if (!isSuccessDataResult(rjson.data)) return false;
  return true;
};

export const isResultErrorResponse = (
  rjson: any
): rjson is APIErrorResponse => {
  if (!("status" in rjson)) return false;
  if (!("error_message" in rjson)) return false;
  return true;
};

export const isSuccessDataResult = (json: any): json is SuccessDataResult => {
  if (!("articles" in json)) return false;
  // add checking for each article
  if (!("biased" in json)) return false;
  // add checking for string[]
  if (!("sentiment" in json)) return false;
  return true;
};

export const getArticleAnalysis = async (
  query: string
): Promise<SuccessDataResult | APIErrorResponse> => {
  const baseUrl = "http://localhost:4000/search?";
  const res = await fetch(`${baseUrl}keyword=${query}`);
  const data = await res.json();
  if (isResultSuccessResponse(data)) {
    return data.data;
  } else if (isResultErrorResponse(data)) {
    return data;
  }
  return { status: "error", error_message: "An unknown error occured." };
};
