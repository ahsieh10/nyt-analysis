import { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import { SuccessDataResult } from "../../interfaces/interfaces";
import { getArticleAnalysis, isSuccessDataResult } from "../../api/api";
import { BallTriangle } from "react-loader-spinner";
import { Sentiment } from "../../constants/constants";
import { SentimentContext } from "../../contexts/sentimentContext";
import Navbar from "../../components/Navbar";
import Sidebar from "./Sidebar";
import Results from "./results/Results";
import Scrollbars from "react-custom-scrollbars-2";
import Popup from "./AboutPopup";
import Notification from "../../components/Notification";
import "./Analyze.scss";

export const TEXT_navbar_accessible_name =
  "This is the navigation bar. You can get back to the home page from here, learn about the app through the info page, or search for more terms.";

const Analyze = () => {
  const [params, setParams] = useSearchParams();
  const [queryParam, setQueryParam] = useState("");
  const [sentiment, setSentiment] = useState<Sentiment>(Sentiment.POSITIVE);
  const [result, setResult] = useState<SuccessDataResult | null>(null);
  const [errorMessage, setErrorMessage] = useState("");
  const [notification, setNotification] = useState("");
  const [notifictionState, setNotificationState] = useState<"hidden" | "shown">(
    "hidden"
  );
  const [timeoutId, setTimeoutId] = useState<NodeJS.Timeout | null>(null);
  const [loading, setLoading] = useState(true);
  const [popup, setPopup] = useState(false);

  const togglePopup = () => {
    setPopup((prev) => !prev);
  };

  const getResult = async (query: string) => {
    setLoading(true);
    const res = await getArticleAnalysis(query);
    if (isSuccessDataResult(res)) {
      setResult(res);
      setSentiment(sentimentToEnum(res.sentiment));
    } else {
      setResult(null);
      setErrorMessage(res.error_message);
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
      if (input === "") {
        setNotification("Cannot submit an empty query.");
      } else {
        setNotification("You've entered the same query.");
      }
      setNotificationState("shown");
      if (timeoutId) {
        clearTimeout(timeoutId);
      }
      const newId = setTimeout(() => {
        setNotificationState("hidden");
      }, 2000);
      setTimeoutId(newId);
    }
  };

  useEffect(() => {
    return () => {
      if (timeoutId) {
        clearTimeout(timeoutId);
      }
    };
  }, [timeoutId]);

  useEffect(() => {
    setLoading(false);
    setSentiment(Sentiment.POSITIVE);
    const query = params.get("query");
    if (query && query !== "") {
      setQueryParam(query);
      getResult(query);
    } else {
      setErrorMessage("Invalid query.");
    }
  }, [params]);

  useEffect(() => {
    const handleKeyDown = (e: KeyboardEvent) => {
      if (e.key === "a") {
        togglePopup();
      }
    };
    window.addEventListener("keydown", handleKeyDown);

    return () => window.removeEventListener("keydown", handleKeyDown);
  }, []);

  return (
    <SentimentContext.Provider value={sentiment}>
      <Scrollbars width="100%" height="100%" autoHide>
        <div
          className="analyze"
          aria-label={TEXT_navbar_accessible_name}
          role="navbar"
        >
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
            <div className="main-container" role="main-body">
              <Sidebar result={result} />
              <Results
                result={result}
                query={queryParam}
                handleSubmit={handleSubmit}
              />
            </div>
          ) : (
            <div className="msg-box">{errorMessage} 😢</div>
          )}
        </div>
        <Popup popupActive={popup} togglePopup={togglePopup} />
      </Scrollbars>
      {notification !== "" && (
        <div className="analyze-overlay">
          <Notification
            message={notification}
            animationState={notifictionState}
          />
        </div>
      )}
    </SentimentContext.Provider>
  );
};

export default Analyze;
