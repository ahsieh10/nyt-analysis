import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faSearch } from "@fortawesome/free-solid-svg-icons";
import { createSearchParams, useNavigate } from "react-router-dom";
import { useState } from "react";
import "./Home.scss";

const Home = () => {
  const navigate = useNavigate();
  const [input, setInput] = useState("");

  const goToAnalyze = () => {
    navigate({
      pathname: "/analyze",
      search: `?${createSearchParams({
        query: input,
      })}`,
    });
  };

  return (
    <div className="home">
      <div className="home-text-wrapper">
        <div className="home-text">
          <div className="welcome-text">Welcome to</div>
          <h1>
            <span className="highlight-text">NYT</span> Sentiment Analysis
          </h1>
          <div className="input-box">
            <input
              type="text"
              value={input}
              placeholder="Enter a term..."
              onChange={(e) => setInput(e.target.value)}
              onKeyDown={(e) => {
                if (e.key === "Enter") goToAnalyze();
              }}
            />
            <button className="search-btn" onClick={goToAnalyze}>
              <FontAwesomeIcon icon={faSearch} />
            </button>
          </div>
          {/* <div className="suggestions">Suggestions</div> */}
        </div>
      </div>
      <div className="home-img"></div>
    </div>
  );
};

export default Home;
