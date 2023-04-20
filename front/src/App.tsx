import { BrowserRouter, Routes, Route } from "react-router-dom";
import Home from "./pages/home/Home";
import Analyze from "./pages/analyze/Analyze";
import "./App.scss";
import Scrollbars from "react-custom-scrollbars-2";

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
