import WordCloud from "react-d3-cloud";
import React, { useRef, useState, useCallback } from "react";
import "./KeywordCloud.scss";

// interface WordCloudProps {
//   words: string[];
//   handleSubmit: (input: string) => void;
// }

// interface WordDatum {
//   text: string;
//   value: number;
// }

// const KeywordCloud = ({ words, handleSubmit }: WordCloudProps) => {
//   const [keywords, setKeywords] = useState<WordDatum[]>(
//     words.map((word) => ({ text: word, value: 20 }))
//   );
//   const canvasRef = useRef<HTMLCanvasElement>(null);

//   const fontSize = useCallback((word: WordDatum) => word.value, []);
//   const rotate = useCallback(
//     (word: WordDatum) =>
//       Math.random() > 0.3 ? 0 : Math.random() > 0.5 ? 90 : 270,
//     []
//   );
//   const onWordClick = useCallback((e: any, word: WordDatum) => {
//     handleSubmit(word.text);
//     console.log(`onWordClick: ${word}`);
//   }, []);

//   return (
//     <WordCloud
//       data={keywords}
//       font="Times"
//       fontWeight="bold"
//       height={300}
//       fontSize={fontSize}
//       spiral="rectangular"
//       rotate={rotate}
//       padding={3}
//       // fill={(d, i) => (Math.random() > 0.5 ? "#4bc6ff" : "#4b93ff")}
//       onWordClick={onWordClick}
//     />
//   );
// };

// export default KeywordCloud;
