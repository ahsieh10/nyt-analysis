import "./Keyword.scss";
import { motion } from "framer-motion";
import { useState } from "react";

interface KeywordProps {
  word: string;
  onWordClick: (word: string) => void;
}

const animationDuration = 0.3;

const Keyword = ({ word, onWordClick }: KeywordProps) => {
  const [overlayActive, setOverlayActive] = useState(false);

  return (
    <div
      className="keyword"
      onMouseOver={() => setOverlayActive(true)}
      onMouseOut={() => setOverlayActive(false)}
      onClick={() => {
        onWordClick(word);
      }}
    >
      <motion.div className="btn-primary"></motion.div>
      <motion.div
        className="btn-overlay"
        initial={{ width: 0 }}
        animate={overlayActive ? { width: "calc(100% + 2px)" } : { width: 0 }}
        transition={{ duration: animationDuration, ease: "easeInOut" }}
      ></motion.div>
      <div
        className={"text-wrapper"}
        style={overlayActive ? { color: "#ffffff" } : {}}
      >
        {word}
      </div>
    </div>
  );
};

export default Keyword;
