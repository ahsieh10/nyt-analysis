import { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import Navbar from "../../components/Navbar";
import Sidebar from "./Sidebar";
import Results from "./results/Results";
import Scrollbars from "react-custom-scrollbars-2";
import { mockResult } from "../../mocks/result";
import "./Analyze.scss";
import Popup from "./AboutPopup";

const Analyze = () => {
  const [params, setParams] = useSearchParams();
  const [input, setInput] = useState("");
  const [sentiment, setSentiment] = useState("");
  const [popup, setPopup] = useState(false);

  const togglePopup = () => {
    setPopup(!popup);
  };

  useEffect(() => {
    const query = params.get("query");
    if (query) {
      setInput(query);
    }
  }, [params]);

  return (
    <Scrollbars width="100%" height="100%" autoHide>
      <div className="analyze">
        <Navbar
          initialInput={input}
          submitInput={(input) => setInput(input)}
          togglePopup={togglePopup}
        />
        <div className="main-container">
          <Sidebar result={mockResult} />
          <Results result={{ ...mockResult, query: input }} />
        </div>
      </div>
      <Popup popupActive={popup} togglePopup={togglePopup} />
    </Scrollbars>
  );
};

export default Analyze;
