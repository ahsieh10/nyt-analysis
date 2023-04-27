import { WordDatum } from "../../../interfaces/interfaces";
import WordCloud from "react-d3-cloud";
import { scaleOrdinal } from "d3-scale";
import { schemeCategory10 } from "d3-scale-chromatic";
import React, { useRef } from "react";

const schemeCategory10ScaleOrdinal = scaleOrdinal(schemeCategory10);

interface WordCloudProps {
  words: WordDatum[];
}

const KeywordCloud = ({ words }: WordCloudProps) => {
  return (
    <WordCloud
      data={words}
      font="Times"
      fontWeight="bold"
      height={300}
      fontSize={(word) => 30}
      spiral="rectangular"
      rotate={(word) =>
        Math.random() > 0.3 ? 0 : Math.random() > 0.5 ? 90 : 270
      }
      padding={3}
      fill={(d, i) => (Math.random() > 0.5 ? "#4bc6ff" : "#4b93ff")}
      onWordClick={(event, d) => {
        console.log(`onWordClick: ${d.text}`);
      }}
      onWordMouseOver={(event, d) => {
        console.log(`onWordMouseOver: ${d.text}`);
      }}
      onWordMouseOut={(event, d) => {
        console.log(`onWordMouseOut: ${d.text}`);
      }}
    />
  );
};

export default KeywordCloud;
