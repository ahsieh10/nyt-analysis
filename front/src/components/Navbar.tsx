import { faSearch } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import "./Navbar.scss";

interface NavbarProps {
  input: string;
  setInput: React.Dispatch<React.SetStateAction<string>>;
  onSubmit: () => void;
}

const Navbar = ({ input, setInput, onSubmit }: NavbarProps) => {
  return (
    <nav className="navbar">
      <div className="logo">NYT Sentiment Analysis</div>
      <div className="input-box">
        <FontAwesomeIcon icon={faSearch} />
        <input
          value={input}
          type="text"
          placeholder="SEARCH..."
          onChange={(e) => {
            setInput(e.target.value);
          }}
        />
        <button className="search-btn" onClick={onSubmit}>
          <FontAwesomeIcon icon={faSearch} />
        </button>
      </div>
    </nav>
  );
};

export default Navbar;
