import Keyword from "./Keyword";
import "./Keywords.scss";

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
