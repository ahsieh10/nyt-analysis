import ResultSection from "./ResultSection";
import KeywordCloud from "./KeywordCloud";
import { Result } from "../../../interfaces/interfaces";
import { motion } from "framer-motion";
import "./Results.scss";
import { animationDuration } from "../../../constants/constants";

interface ResultsProps {
  result: Result;
}

const Results = ({ result }: ResultsProps) => {
  return (
    <motion.div
      className="results"
      initial={{ x: 300, opacity: 0 }}
      animate={{ x: 0, opacity: 1 }}
      transition={{ duration: animationDuration, ease: "easeInOut" }}
    >
      <h2>Results for "{`${result.query}`}"</h2>
      <ResultSection
        title="Overall Sentiment"
        horizontal
        innerContent={
          <div className="sentiment-label">{result.overallSentiment}</div>
        }
      />
      <ResultSection
        title="Most biased sentences"
        innerContent={
          <div>
            {result.mostBiasedSentences.map((sentence) => (
              <>
                <div>{sentence}</div>
                <br />
              </>
            ))}
          </div>
        }
      />
      <ResultSection
        title="Keywords"
        innerContent={<KeywordCloud words={result.keywords} />}
      />
    </motion.div>
  );
};

export default Results;
