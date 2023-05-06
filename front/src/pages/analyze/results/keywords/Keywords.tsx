import Keyword from "./Keyword";
import "./Keywords.scss";

// Structural component for the keyword section. Enables keyword buttons to be displayed
// in a grid.
interface KeywordsProps {
  words: string[];
  onKeywordClick: (word: string) => void;
}

const Keywords = ({ words, onKeywordClick }: KeywordsProps) => {
  return (
    <div className="keyword-grid">
      {words.map((word) => (
        <Keyword key={word} word={word} onWordClick={onKeywordClick} />
      ))}
    </div>
  );
};

export default Keywords;
