import { APIErrorResponse, SuccessDataResult } from "../interfaces/interfaces";
import {
  isResultErrorResponse,
  isResultSuccessResponse,
} from "./responseValidation";

// Controller for the front-end. Handles fetching and returning from calls to
// the NYT and sentiment APIs.

export const getArticleAnalysis = async (
  query: string
): Promise<SuccessDataResult | APIErrorResponse> => {
  const baseUrl = "http://localhost:4000/search?";
  try {
    const res = await fetch(`${baseUrl}keyword=${query}`);
    const data = await res.json();
    if (isResultSuccessResponse(data)) {
      return data.data;
    } else if (isResultErrorResponse(data)) {
      return data;
    }
    return {
      status: "error",
      error_message: "Server response was ill-formatted.",
    };
  } catch (err) {
    console.error(err);
    return {
      status: "error",
      error_message: "An unknown error occured. The server may not be running.",
    };
  }
};
