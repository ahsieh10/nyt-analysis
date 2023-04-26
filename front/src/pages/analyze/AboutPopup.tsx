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
          <div className="overlay"> </div>
          <div className="popup-content">
            <h2> About the App</h2>
            <p> Lorem ipsum.</p>
            <button className="close-btn" onClick={togglePopup}>
              CLOSE
            </button>
          </div>
        </div>
      )}
    </>
  );
};

export default Popup;
