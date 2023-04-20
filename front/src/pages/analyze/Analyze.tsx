import { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import Navbar from "../../components/Navbar";
import Sidebar from "./Sidebar";
import Results from "./Results";
import "./Analyze.scss";
import Scrollbars from "react-custom-scrollbars-2";

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
    <Scrollbars width="100%" height="100%">
      <div className="analyze">
        <Navbar input={input} setInput={setInput} onSubmit={() => {}} />
        <div className="main-container">
          <Sidebar />
          <Results query={input} />
        </div>
      </div>
    </Scrollbars>
  );
};

export default Analyze;
