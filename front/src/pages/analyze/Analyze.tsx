import { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import Navbar from "../../components/Navbar";
import Sidebar from "./Sidebar";
import "./Analyze.scss";
import Results from "./Results";

const Analyze = () => {
  const [params, setParams] = useSearchParams();
  const [input, setInput] = useState("");

  useEffect(() => {
    const query = params.get("query");
    if (query) {
      setInput(query);
    }
  }, [params]);

  return (
    <div className="analyze">
      <Navbar input={input} setInput={setInput} onSubmit={() => {}}/>
      <div className="main-container">
        <Sidebar />
        <Results />
      </div>
    </div>
  );
};

export default Analyze;
