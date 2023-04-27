import { faSearch } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useState } from "react";
import { Link } from "react-router-dom";
import AboutPopup from "../pages/analyze/AboutPopup";
import "./Navbar.scss";

interface NavbarProps {
  initialInput: string;
  submitInput: (input: string) => void;
  togglePopup: () => void;
}

const Navbar = ({ initialInput, submitInput, togglePopup }: NavbarProps) => {
  const [input, setInput] = useState(initialInput);

  return (
    <nav className="navbar">
      <Link to="/" className="logo">
        <span className="main-blue">NYT</span> Sentiment Analysis
      </Link>
      <div className="input-area">
        <button className="about-btn" onClick={togglePopup}>
          <div className="button-content">
            <h2>i</h2>
          </div>
        </button>
        <div className="input-box">
          <FontAwesomeIcon icon={faSearch} />
          <input
            value={input}
            type="text"
            placeholder="Search..."
            onChange={(e) => {
              setInput(e.target.value);
            }}
          />
        </div>
        <button className="search-btn" onClick={() => submitInput(input)}>
          <FontAwesomeIcon icon={faSearch} />
        </button>
      </div>
    </nav>
  );
};

export default Navbar;
