import ResultSection from "./ResultSection";
import { motion } from "framer-motion";
import {
  animationDuration,
  getSentimentBackgroundStyle,
  getSentimentTextStyle,
} from "../../../constants/constants";
import { SuccessDataResult } from "../../../interfaces/interfaces";
import { useRef, useState, useEffect, useContext } from "react";
import ToggleElement from "./ToggleElement";
import Keywords from "./keywords/Keywords";
import "./Results.scss";
import { SentimentContext } from "../../../contexts/sentimentContext";

/* Results display for the Analyze page. Shows the sentiment, biased sentences,
keywords, navigation bar, and the sidebar of articles analyzed */
interface ResultsProps {
  result: SuccessDataResult;
  query: string;
  handleSubmit: (input: string) => void;
}

const toggleItems = ["All", "Sentiment", "Biased Sentences", "Keywords"];

const Results = ({ result, query, handleSubmit }: ResultsProps) => {
  const parentRef = useRef<HTMLDivElement>(null);
  const [activeToggle, setActiveToggle] = useState("All");
  const [indicatorStyle, setIndicatorStyle] = useState({
    x: 0,
    y: 0,
    width: 0,
    height: 0,
  });
  const sentiment = useContext(SentimentContext);

  const moveIndicator = (box: DOMRect) => {
    if (!parentRef.current) return;
    const left = box.left - parentRef.current.getBoundingClientRect().left;
    const top = box.top - parentRef.current.getBoundingClientRect().top;
    const width = box.width;
    const height = box.height;
    setIndicatorStyle({ x: left, y: top, width, height });
  };

  useEffect(() => {
    const handleKeyDown = (e: KeyboardEvent) => {
      if (e.key === "ArrowLeft") {
        const nextIndex =
          toggleItems.indexOf(activeToggle) === 0
            ? toggleItems.length - 1
            : (toggleItems.indexOf(activeToggle) - 1) % toggleItems.length;
        setActiveToggle(toggleItems[nextIndex]);
      } else if (e.key === "ArrowRight") {
        const nextIndex =
          (toggleItems.indexOf(activeToggle) + 1) % toggleItems.length;
        setActiveToggle(toggleItems[nextIndex]);
      }
    };
    window.addEventListener("keydown", handleKeyDown);

    return () => window.removeEventListener("keydown", handleKeyDown);
  }, [activeToggle]);

  return (
    <motion.div
      className="results"
      initial={{ x: 300, opacity: 0 }}
      animate={{ x: 0, opacity: 1 }}
      transition={{ duration: animationDuration, ease: "easeInOut" }}
    >
      <h2>Results for "{`${query}`}"</h2>
      <div className="sort-toggles" ref={parentRef}>
        {toggleItems.map((item) => (
          <ToggleElement
            key={item}
            label={item}
            active={activeToggle === item}
            setActive={setActiveToggle}
            moveIndicator={moveIndicator}
          />
        ))}
        <motion.div
          className={`toggle-indicator ${getSentimentBackgroundStyle(
            sentiment
          )}`}
          initial={{ x: 0, width: 0 }}
          animate={indicatorStyle}
        ></motion.div>
      </div>
      {(activeToggle === "All" || activeToggle === "Sentiment") && (
        <ResultSection
          title="Overall Sentiment"
          horizontal
          innerContent={
            <div
              className={`sentiment-label ${getSentimentTextStyle(sentiment)}`}
            >
              {result.sentiment === "P+" ? (
                <div>Very Positive</div>
              ) : result.sentiment === "P" ? (
                <div>Positive</div>
              ) : result.sentiment === "NEU" ? (
                <div>Neutral</div>
              ) : result.sentiment === "N" ? (
                <div>Negative</div>
              ) : result.sentiment === "N+" ? (
                <div>Very Negative</div>
              ) : (
                <div>No sentiment found.</div>
              )}
            </div>
          }
        />
      )}
      {(activeToggle === "All" || activeToggle === "Biased Sentences") && (
        <ResultSection
          title="Most biased sentences"
          innerContent={
            <div>
              {result.biased.slice(0, 10).map((sentence, i) => (
                <div key={i}>
                  <div>{sentence}</div>
                  <br />
                </div>
              ))}
            </div>
          }
        />
      )}
      {(activeToggle === "All" || activeToggle === "Keywords") && (
        <ResultSection
          title="Keywords"
          innerContent={
            <Keywords
              onKeywordClick={handleSubmit}
              words={Array.from(
                new Set(result.articles.map((a) => a.keywords).flat())
              ).slice(0, 18)}
            />
          }
        />
      )}
    </motion.div>
  );
};

export default Results;
