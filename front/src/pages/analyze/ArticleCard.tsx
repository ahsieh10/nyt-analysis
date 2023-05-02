import { useState } from "react";
import { Article } from "../../interfaces/interfaces";
import { MdOutlineNoPhotography } from "react-icons/md";
import "./ArticleCard.scss";

interface ArticleCardProps {
  article: Article;
}

const ArticleCard = ({ article }: ArticleCardProps) => {
  const [mouseOver, setMouseOver] = useState(false);

  const getImageUrl = (article: Article): string => {
    // const thumbnail = article.multimedia.filter(
    //   (media) => media.subType === "thumbnail"
    // )[0];

    if (article.thumbnail !== "") {
      return `https://static01.nyt.com/${article.thumbnail}`;
    }
    return "";
  };

  return (
    <a className="card-link" href={article.webUrl} target="_blank">
      <div
        className={`card ${mouseOver && "active"}`}
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
      {/* Put a camera/image icon with a slash through it */}
    </div>
  );
};

export default ArticleCard;
