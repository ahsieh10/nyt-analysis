import "./Results.scss";

interface ResultsProps {
  query: string;
}

const Results = ({ query }: ResultsProps) => {
  return (
    <div className="results">
      <h2>Results for "{`${query}`}"</h2>
    </div>
  );
};

export default Results;
