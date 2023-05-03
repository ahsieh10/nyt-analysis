import { faSearch } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import "./Navbar.scss";

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
            onKeyDown={(e) => {
              if (e.key === "Enter") {
                submitInput(input);
              }
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
