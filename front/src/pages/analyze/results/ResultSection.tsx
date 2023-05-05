import "./ResultSection.scss";
export const TEXT_results_accessible_name =
  "The results have been loaded. There is the overall sentiment, followed by most biased sentences, and then keywords from the articles.";

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
  return (
    <div
      className={`result-section ${horizontal ? "horizontal" : "vertical"}`}
      aria-label={TEXT_results_accessible_name}
    >
      <h3 className="result-subheading">{title}</h3>
      <div>{innerContent}</div>
    </div>
  );
};

export default ResultSection;
