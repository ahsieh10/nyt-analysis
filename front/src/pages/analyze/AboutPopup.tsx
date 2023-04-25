import "./AboutPopup.scss";
import {useState, useEffect} from "react";

const Popup = () => {
  const [popup, setPopup] = useState(false);

  const togglePopup = () => {
    setPopup(!popup)
  }

  return (
    <>
      <button onClick={togglePopup} className="open-btn">
        About
      </button>

      {popup && (
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


}