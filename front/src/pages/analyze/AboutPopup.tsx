import "./AboutPopup.scss";
import { useState, useEffect } from "react";

interface PopupProps {
  popupActive: boolean;
  togglePopup: () => void;
}

const Popup = ({ popupActive, togglePopup }: PopupProps) => {
  return (
    <>
      {popupActive && (
        <div className="about-page">
          <div className="overlay">
            <div className="popup-content">
              <div className="popup-background">
                <div className="about-app">
                  <h2> About Page</h2>
                </div>
                <div className="about-body">
                  <p>
                    {" "}
                    This website utilizes The New York Times's Application
                    Programming Interface and a natural language processing
                    model in order to conduct a sentiment analysis on the top 10
                    articles related to the topic that you searched for.
                    <br></br>
                    <br></br>
                    The website's purpose is to inform users on and uncover
                    potential biases in modern-day journalism regarding word
                    choice, syntax, and prose. To accomplish this, we display
                    the overall sentiment that the natural language processing
                    model has computed, examples of most biased sentences within
                    the articles analyzed, as well as a keyword word cloud that
                    highlights notable words & phrases commonly used for the
                    given topic. We also provide the analyzed article links for
                    reference.
                    <br></br>
                    <br></br>
                    Please note that although we focus on The New York Times
                    specifically, this project is intended to point out subtle
                    journalistic bias as a whole. We utilized The New York
                    Times's API because it is free for non-commercial use and
                    has an extensive database.
                    <br></br>
                    <br></br>
                    DISCLAIMER: User search queries are cached and stored in the
                    website's system in order to gather data about common
                    topics. Personalized data or any identifying information is
                    not stored within the system.
                  </p>
                </div>
                <button className="close-btn" onClick={togglePopup}>
                  <div className="close-button-content">
                    <h2>x</h2>
                  </div>
                </button>
              </div>
            </div>
          </div>
        </div>
      )}
    </>
  );
};

export default Popup;
