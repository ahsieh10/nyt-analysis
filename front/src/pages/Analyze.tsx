import { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";

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
    <>
      <h1>This is the Analyze page.</h1>
      <input
        value={input}
        type="text"
        onChange={(e) => {
          setInput(e.target.value);
        }}
      />
    </>
  );
};

export default Analyze;
