import "./Keyword.scss";
import { motion } from "framer-motion";
import { useContext, useState } from "react";
import {
  getSentimentBackgroundStyle,
  getSentimentBorderStyle,
  getSentimentHighlightBackgroundStyle,
  getSentimentTextStyle,
} from "../../../../constants/constants";
import { SentimentContext } from "../../../../contexts/sentimentContext";

interface KeywordProps {
  word: string;
  onWordClick: (word: string) => void;
}

const animationDuration = 0.3;

const Keyword = ({ word, onWordClick }: KeywordProps) => {
  const [overlayActive, setOverlayActive] = useState(false);
  const sentiment = useContext(SentimentContext);

  return (
    <div
      className={`keyword ${getSentimentBorderStyle(sentiment)}`}
      onMouseOver={() => setOverlayActive(true)}
      onMouseOut={() => setOverlayActive(false)}
      onClick={() => {
        onWordClick(word);
      }}
    >
      <motion.div className="btn-primary"></motion.div>
      <motion.div
        className={`btn-overlay ${getSentimentHighlightBackgroundStyle(
          sentiment
        )}`}
        initial={{ width: 0 }}
        animate={overlayActive ? { width: "calc(100% + 2px)" } : { width: 0 }}
        transition={{ duration: animationDuration, ease: "easeInOut" }}
      ></motion.div>
      <div
        className={`text-wrapper ${getSentimentTextStyle(sentiment)}`}
        style={overlayActive ? { color: "#ffffff" } : {}}
      >
        {word}
      </div>
    </div>
  );
};

export default Keyword;
