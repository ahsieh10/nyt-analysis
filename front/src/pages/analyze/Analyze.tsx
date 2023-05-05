import { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import { SuccessDataResult } from "../../interfaces/interfaces";
import { getArticleAnalysis, isSuccessDataResult } from "../../api/api";
import Navbar from "../../components/Navbar";
import Sidebar from "./Sidebar";
import Results from "./results/Results";
import Scrollbars from "react-custom-scrollbars-2";
import Popup from "./AboutPopup";
import { BallTriangle } from "react-loader-spinner";
import { Sentiment } from "../../constants/constants";
import { SentimentContext } from "../../contexts/sentimentContext";
import "./Analyze.scss";

const Analyze = () => {
  const [params, setParams] = useSearchParams();
  const [queryParam, setQueryParam] = useState("");
  const [sentiment, setSentiment] = useState<Sentiment>(Sentiment.POSITIVE);
  const [result, setResult] = useState<SuccessDataResult | null>(null);
  const [loading, setLoading] = useState(true);
  const [popup, setPopup] = useState(false);

  const togglePopup = () => {
    setPopup(!popup);
  };

  const getResult = async (query: string) => {
    setLoading(true);
    const res = await getArticleAnalysis(query);
    if (isSuccessDataResult(res)) {
      setResult(res);
      setSentiment(sentimentToEnum(res.sentiment));
    } else {
      setResult(null);
      console.log(res.message);
    }
    setLoading(false);
  };

  const sentimentToEnum = (sentString: string): Sentiment => {
    switch (sentString) {
      case "P":
      case "P+":
        return Sentiment.POSITIVE;
      case "N":
      case "N+":
        return Sentiment.NEGATIVE;
      default:
        return Sentiment.NEUTRAL;
    }
  };

  const handleSubmit = (input: string) => {
    if (input !== "" && input !== queryParam) {
      setParams({ query: input });
      setLoading(true);
    } else {
      // display message saying cannot submit empty string
    }
  };

  useEffect(() => {
    setLoading(false);
    setSentiment(Sentiment.POSITIVE);
    const query = params.get("query");
    if (query && query !== "") {
      setQueryParam(query);
      getResult(query);
    }
  }, [params]);

  return (
    <SentimentContext.Provider value={sentiment}>
      <Scrollbars width="100%" height="100%" autoHide>
        <div className="analyze">
          <Navbar
            queryParam={queryParam}
            submitInput={handleSubmit}
            togglePopup={togglePopup}
          />
          {loading ? (
            <div className="loading-box">
              Loading...
              <BallTriangle
                height={25}
                width={25}
                radius={5}
                color="#34abff"
                ariaLabel="ball-triangle-loading"
                visible={true}
              />
            </div>
          ) : result ? (
            <div className="main-container">
              <Sidebar result={result} />
              <Results
                result={result}
                query={queryParam}
                handleSubmit={handleSubmit}
              />
            </div>
          ) : (
            <div>
              No valid query has been submitted, or the server has errored.
            </div>
          )}
        </div>
        <Popup popupActive={popup} togglePopup={togglePopup} />
      </Scrollbars>
    </SentimentContext.Provider>
  );
};

export default Analyze;
