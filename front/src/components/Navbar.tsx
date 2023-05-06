import { faSearch } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useState, useEffect, useContext, useRef } from "react";
import { Link } from "react-router-dom";
import { SentimentContext } from "../contexts/sentimentContext";
import {
  getSentimentHighlightBackgroundStyle,
  getSentimentTextStyle,
} from "../constants/constants";
import "./Navbar.scss";

export const TEXT_input_box_accessible_name =
  "Input box. Type the topic that you want to search here.";
export const TEXT_submit_button_accessible_name =
  "This is the submit button. Click on it or press enter to search for your topic.";
export const TEXT_about_button_accessible_name =
  "Here is the about button. Click it to learn more about the app and how it works.";
export const TEXT_return_home_accessible_name =
  "New York Times Sentiment Analysis. Click here to return to the home page.";

  /* Component which is displayed on top side of the Analyze page. User can click
  on all items in the display: NYT Sentiment Analysis (returns to home
  about page button (popup with info about the page), and search input box 
  (allows user to enter more queries). */
interface NavbarProps {
  queryParam: string;
  submitInput: (input: string) => void;
  togglePopup: () => void;
}

const Navbar = ({ queryParam, submitInput, togglePopup }: NavbarProps) => {
  const [input, setInput] = useState("");
  const inputRef = useRef<HTMLInputElement>(null);
  const sentiment = useContext(SentimentContext);
  const [logoText, setLogoText] = useState<JSX.Element>(
    <>
      <span className={`${getSentimentTextStyle(sentiment)}`}>NYT</span>{" "}
      Sentiment Analysis
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
            <span className={`${getSentimentTextStyle(sentiment)}`}>N</span>SA
          </>
        );
      } else {
        setLogoText(
          <>
            <span className={`${getSentimentTextStyle(sentiment)}`}>NYT</span>{" "}
            Sentiment Analysis
          </>
        );
      }
    };
    handleResize();
    window.addEventListener("resize", handleResize);

    return () => window.removeEventListener("resize", handleResize);
  }, [sentiment]);

  useEffect(() => {
    const handleKeyDown = (e: KeyboardEvent) => {
      if (e.ctrlKey && e.key === "f" && inputRef.current) {
        e.preventDefault();
        e.stopPropagation();
        inputRef.current.focus();
      }
    };

    window.addEventListener("keydown", handleKeyDown);

    return () => window.removeEventListener("keydown", handleKeyDown);
  }, [inputRef]);

  return (
    <nav className="navbar">
      <Link
        to="/"
        className="logo"
        aria-label={TEXT_return_home_accessible_name}
        role="return-home"
      >
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
            ref={inputRef}
            value={input}
            aria-label={TEXT_input_box_accessible_name}
            role="textbox"
            type="text"
            placeholder="Search..."
            onChange={(e) => {
              e.stopPropagation();
              setInput(e.target.value);
            }}
            onKeyDown={(e) => {
              e.stopPropagation();
              if (e.key === "Enter") {
                submitInput(input);
              }
            }}
          />
        </div>
        <button
          className={`search-btn ${getSentimentHighlightBackgroundStyle(
            sentiment
          )}`}
          aria-label={TEXT_submit_button_accessible_name}
          role="submit-button"
          onClick={() => submitInput(input)}
        >
          <FontAwesomeIcon icon={faSearch} />
        </button>
      </div>
    </nav>
  );
};

export default Navbar;
