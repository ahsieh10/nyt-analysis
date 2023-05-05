import { useContext } from "react";
import { SentimentContext } from "../../../contexts/sentimentContext";
import { getSentimentBackgroundStyle } from "../../../constants/constants";
import "./ResultSection.scss";

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
    >
      <h3 className="result-subheading">{title}</h3>
      <div>{innerContent}</div>
    </div>
  );
};

export default ResultSection;
