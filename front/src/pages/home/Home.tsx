import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faSearch } from "@fortawesome/free-solid-svg-icons";
import { createSearchParams, useNavigate } from "react-router-dom";
import { useState } from "react";
import { motion } from "framer-motion";
import { animationDuration } from "../../constants/constants";
import Popup from "../analyze/AboutPopup";
import "./Home.scss";
export const TEXT_home_page_accessible_name = "Home page of The New York Times Sentiment Analysis App.";
export const TEXT_input_box_accessible_name = "This is the input box. Search for a topic to find a summary of the sentiment analysis for that query.";
export const TEXT_search_button_accessible_name = "This is the submitt button. Click it to get your results.";
export const TEXT_about_button_accessible_name = "Here is the about button. Click it to learn more about the app and how it works.";

const Home = () => {
  const navigate = useNavigate();
  const [input, setInput] = useState("");
  const [imgLoaded, setImgLoaded] = useState(false);
  const [isEntering, setIsEntering] = useState(true);
  const [popup, setPopup] = useState(false);

  const togglePopup = () => {
    setPopup(!popup);
  };

  const goToAnalyze = () => {
    setIsEntering(false);
    setTimeout(() => {
      navigate({
        pathname: "/analyze",
        search: `?${createSearchParams({
          query: input,
        })}`,
      });
    }, animationDuration * 1000);
  };

  return (
    <div
      className="home"
      aria-label={TEXT_home_page_accessible_name}
      role="home-page"
    >
      <motion.div
        className="home-text-wrapper"
        initial={{ x: -300, opacity: 0 }}
        animate={
          imgLoaded
            ? isEntering
              ? { x: 0, opacity: 1 }
              : { x: -300, opacity: 0 }
            : {}
        }
        transition={{ duration: animationDuration, ease: "easeInOut" }}
      >
        <div className="home-text">
          <div className="welcome-text">Welcome to</div>
          <h1>
            <span className="main-blue">NYT</span> Sentiment Analysis
          </h1>
          <div
            className="input-box"
            aria-label={TEXT_input_box_accessible_name}
            role="home-input-box"
          >
            <input
              type="text"
              value={input}
              placeholder="Enter a term..."
              onChange={(e) => setInput(e.target.value)}
              onKeyDown={(e) => {
                if (e.key === "Enter") goToAnalyze();
              }}
            />
            <button
              className="search-btn"
              aria-label={TEXT_search_button_accessible_name}
              role="search-button"
              onClick={goToAnalyze}
            >
              <FontAwesomeIcon icon={faSearch} />
            </button>
          </div>
        </div>
        <button
          className="about-btn-home"
          aria-label={TEXT_about_button_accessible_name}
          role="about-button"
          onClick={togglePopup}
        >
          <div className="button-content-home">
            <h2>i</h2>
          </div>
        </button>
      </motion.div>
      <motion.div
        initial={{ x: 300, opacity: 0 }}
        animate={
          imgLoaded
            ? isEntering
              ? { x: 0, opacity: 0.4 }
              : { x: 300, opacity: 0 }
            : {}
        }
        transition={{ duration: animationDuration, ease: "easeInOut" }}
        className="home-img"
      >
        <img
          onLoad={() => {
            setImgLoaded(true);
          }}
          src="./assets/images/landing-img.jpg"
        />
      </motion.div>
      <Popup popupActive={popup} togglePopup={togglePopup} />
    </div>
  );
};

export default Home;
