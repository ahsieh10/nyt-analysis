import { faSearch } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import "./Navbar.scss";
export const TEXT_input_box_accessible_name = "Input box. Type the topic that you want to search here.";
export const TEXT_submit_button_accessible_name =
  "This is the submit button. Click on it or press enter to search for your topic.";
  export const TEXT_about_button_accessible_name =
    "Here is the about button. Click it to learn more about the app and how it works.";

interface NavbarProps {
  queryParam: string;
  submitInput: (input: string) => void;
  togglePopup: () => void;
}

const Navbar = ({ queryParam, submitInput, togglePopup }: NavbarProps) => {
  const [input, setInput] = useState("");
  const [logoText, setLogoText] = useState<JSX.Element>(
    <>
      <span className="main-blue">NYT</span> Sentiment Analysis
    </>
  );

  useEffect(() => {
    setInput(queryParam);
  }, [queryParam]);

  useEffect(() => {
    const handleResize = () => {
      if (window.innerWidth <= 700) {
        setLogoText(
          <>
            <span className="main-blue">N</span>SA
          </>
        );
      } else {
        setLogoText(
          <>
            <span className="main-blue">NYT</span> Sentiment Analysis
          </>
        );
      }
    };
    handleResize();
    window.addEventListener("resize", handleResize);

    return () => window.removeEventListener("resize", handleResize);
  }, []);

  return (
    <nav className="navbar">
      <Link to="/" className="logo">
        {logoText}
      </Link>
      <div className="input-area">
        <button className="about-btn" onClick={togglePopup}>
          <div
            className="button-content"
            aria-label={TEXT_about_button_accessible_name}
            role="about-button"
          >
            <h2>i</h2>
          </div>
        </button>
        <div className="input-box">
          <FontAwesomeIcon icon={faSearch} />
          <input
            value={input}
            aria-label={TEXT_input_box_accessible_name}
            role="textbox"
            type="text"
            placeholder="Search..."
            onChange={(e) => {
              setInput(e.target.value);
            }}
            onKeyDown={(e) => {
              if (e.key === "Enter") {
                submitInput(input);
              }
            }}
          />
        </div>
        <button
          className="search-btn"
          role="submit-button"
          onClick={() => submitInput(input)}
          aria-label={TEXT_submit_button_accessible_name}
        >
          <FontAwesomeIcon icon={faSearch} />
        </button>
      </div>
    </nav>
  );
};

export default Navbar;
