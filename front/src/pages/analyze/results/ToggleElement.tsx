import { useContext, useEffect, useRef, useState } from "react";
import "./ToggleElement.scss";
import { SentimentContext } from "../../../contexts/sentimentContext";
import { getSentimentTextStyle } from "../../../constants/constants";

// Helper component that makes the Analyze page responsive
interface ToggleElementProps {
  label: string;
  active: boolean;
  setActive: React.Dispatch<React.SetStateAction<string>>;
  moveIndicator: (box: DOMRect) => void;
}

const ToggleElement = ({
  label,
  active,
  setActive,
  moveIndicator,
}: ToggleElementProps) => {
  const elementRef = useRef<HTMLSpanElement>(null);

  const [fontsLoaded, setFontsLoaded] = useState(false);
  const sentiment = useContext(SentimentContext);

  const handleClick = () => {
    setActive(label);
  };

  useEffect(() => {
    if (!fontsLoaded) return;
    if (active && elementRef.current) {
      moveIndicator(elementRef.current.getBoundingClientRect());
    }
  }, [active, fontsLoaded]);

  useEffect(() => {
    // document.fonts.ready.then(() => {
    setFontsLoaded(true);
    // });
  }, []);

  useEffect(() => {
    const handleResize = () => {
      if (active && elementRef.current) {
        moveIndicator(elementRef.current.getBoundingClientRect());
      }
    };

    window.addEventListener("resize", handleResize);
    return () => window.removeEventListener("resize", handleResize);
  }, [active]);

  return (
    <span
      ref={elementRef}
      className={`toggle ${active ? getSentimentTextStyle(sentiment) : ""}`}
      onClick={handleClick}
      // style={active ? { color: "#3aacff" } : {}}
    >
      {label}
    </span>
  );
};

export default ToggleElement;
