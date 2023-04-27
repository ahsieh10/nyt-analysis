import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faSearch } from "@fortawesome/free-solid-svg-icons";
import { createSearchParams, useNavigate } from "react-router-dom";
import { useState } from "react";
import { motion } from "framer-motion";
import { animationDuration } from "../../constants/constants";
import Popup from "../analyze/AboutPopup";
import "./Home.scss";

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
    <div className="home">
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
        </div>
        <button className="about-btn-home" onClick={togglePopup}>
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
