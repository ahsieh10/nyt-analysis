import { useContext } from "react";
import { SentimentContext } from "../../../contexts/sentimentContext";
import { getSentimentBackgroundStyle } from "../../../constants/constants";
import "./ResultSection.scss";
export const TEXT_results_accessible_name =
  "The results have been loaded. There is the overall sentiment, followed by most biased sentences, and then keywords from the articles.";

// Helper component to the results component that sets up the structure cleanly.
interface ResultSectionProps {
  title: string;
  innerContent: React.ReactNode;
  horizontal?: boolean;
}

const ResultSection = ({
  title,
  innerContent,
  horizontal,
}: ResultSectionProps) => {
  const sentiment = useContext(SentimentContext);

  return (
    <div
      className={`result-section ${
        horizontal ? "horizontal" : "vertical"
      } ${getSentimentBackgroundStyle(sentiment)}`}
      aria-label={TEXT_results_accessible_name}
    >
      <h3 className="result-subheading">{title}</h3>
      <div>{innerContent}</div>
    </div>
  );
};

export default ResultSection;
