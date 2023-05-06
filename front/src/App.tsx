import { Routes, Route } from "react-router-dom";
import Home from "./pages/home/Home";
import Analyze from "./pages/analyze/Analyze";
import "./App.scss";

function App() {
  return (
    <div className="App">
      <Routes>
        <Route path="/" element={<Home />}></Route>
        <Route path="/analyze" element={<Analyze />}></Route>
      </Routes>
    </div>
  );
}

export default App;
