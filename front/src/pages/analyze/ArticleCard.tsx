import { useState } from "react";
import { Article } from "../../interfaces/interfaces";
import "./ArticleCard.scss";

interface ArticleCardProps {
  article: Article;
}

const ArticleCard = ({ article }: ArticleCardProps) => {
  const [mouseOver, setMouseOver] = useState(false);

  const getImageUrl = (article: Article): string => {
    const thumbnail = article.multimedia.filter(
      (media) => media.subType === "thumbnail"
    )[0];

    if (thumbnail) {
      console.log("https://static01.nyt.com/${thumbnail.url}`");
      return `https://static01.nyt.com/${thumbnail.url}`;
    }
    return "";
  };

  return (
    <a className="card-link" href={article.web_url} target="_blank">
      <div
        className={`card ${mouseOver && "active"}`}
        onMouseOver={() => setMouseOver(true)}
        onMouseOut={() => setMouseOver(false)}
      >
        <div className="card-text">
          <div className="heading">{article.headline.main}</div>
          <div className="subheading">
            {new Date(article.pub_date).toDateString()}
          </div>
        </div>
        <div
          className="card-img"
          style={{
            backgroundImage: `url(${getImageUrl(article)})`,
            backgroundSize: "cover",
          }}
        ></div>
      </div>
    </a>
  );
};

export default ArticleCard;
