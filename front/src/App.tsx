import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "./pages/home/Home";
import Analyze from "./pages/analyze/Analyze";
import { useEffect } from "react";
import "./App.scss";

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Home />}></Route>
          <Route path="/analyze" element={<Analyze />}></Route>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
