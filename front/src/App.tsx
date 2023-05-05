import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "./pages/home/Home";
import Analyze from "./pages/analyze/Analyze";
import { useState, useEffect } from "react";

import "./App.scss";

function App() {
  const [popup, setPopup] = useState(false);

    const togglePopup = () => {
      setPopup(!popup);
    };

  useEffect(() => {
    const fetchData = async () => {
      const url =
        "https://api.meaningcloud.com/sentiment-2.1?" +
        new URLSearchParams({
          key: "f5cb66634417b2b3b554b1d3359bc1f2",
          lang: "en",
          txt: "On her first day of work, each airplane mechanic should complete all of the necessary new-hire paperwork. Every doctor should carry her pager with her when she is on call. Before leaving work each day, the secretary should write down his most important tasks to be completed the next day.",
          model: "general",
        });

      const res = await fetch(url);
      const data = await res.json();
      console.log(data);
    };
  }, []);
  return (
    <div className="App">
        <Routes>
          <Route path="/" element={<Home/>}></Route>
          <Route path="/analyze" element={<Analyze />}></Route>
        </Routes>
    </div>
  );
}

export default App;
