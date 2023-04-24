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
  return (
    <div className={`result-section ${horizontal ? "horizontal" : "vertical"}`}>
      <h3 className="result-subheading">{title}</h3>
      <div>{innerContent}</div>
    </div>
  );
};

export default ResultSection;
