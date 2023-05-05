import { useContext, useState } from "react";
import { Article } from "../../interfaces/interfaces";
import { MdOutlineNoPhotography } from "react-icons/md";
import { SentimentContext } from "../../contexts/sentimentContext";
import {
  getSentimentBorderStyle,
  getSentimentHighlightBackgroundStyle,
} from "../../constants/constants";
import "./ArticleCard.scss";

interface ArticleCardProps {
  article: Article;
}

const ArticleCard = ({ article }: ArticleCardProps) => {
  const [mouseOver, setMouseOver] = useState(false);
  const sentiment = useContext(SentimentContext);

  const getImageUrl = (article: Article): string => {
    if (article.thumbnail !== "") {
      return `https://static01.nyt.com/${article.thumbnail}`;
    }
    return "";
  };

  return (
    <a className="card-link" href={article.webUrl} target="_blank">
      <div
        className={`card ${
          mouseOver &&
          "active" +
            " " +
            getSentimentHighlightBackgroundStyle(sentiment) +
            " " +
            getSentimentBorderStyle(sentiment)
        }`}
        onMouseOver={() => setMouseOver(true)}
        onMouseOut={() => setMouseOver(false)}
      >
        <div className="card-text">
          <div className="heading">{article.headline}</div>
          <div className="subheading">
            {/* {new Date(article.pub_date).toDateString()} */}
            {article.snippet}
          </div>
        </div>
        {getImageUrl(article) !== "" ? (
          <div
            className="card-img"
            style={{
              backgroundImage: `url(${getImageUrl(article)})`,
              backgroundSize: "cover",
            }}
          ></div>
        ) : (
          <PlaceholderImage />
        )}
      </div>
    </a>
  );
};

const PlaceholderImage = () => {
  return (
    <div className="placeholder-img">
      <MdOutlineNoPhotography size={"1.3rem"} />
    </div>
  );
};

export default ArticleCard;
