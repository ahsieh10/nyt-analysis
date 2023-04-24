import { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import Navbar from "../../components/Navbar";
import Sidebar from "./Sidebar";
import Results from "./results/Results";
import Scrollbars from "react-custom-scrollbars-2";
import { mockResult } from "../../mocks/result";
import "./Analyze.scss";

const Analyze = () => {
  const [params, setParams] = useSearchParams();
  const [input, setInput] = useState("");
  const [sentiment, setSentiment] = useState("");

  useEffect(() => {
    const query = params.get("query");
    if (query) {
      setInput(query);
    }
  }, [params]);

  return (
    <Scrollbars width="100%" height="100%" autoHide>
      <div className="analyze">
        <Navbar initialInput={input} submitInput={(input) => setInput(input)} />
        <div className="main-container">
          <Sidebar result={mockResult} />
          <Results result={{ ...mockResult, query: input }} />
        </div>
      </div>
    </Scrollbars>
  );
};

export default Analyze;
