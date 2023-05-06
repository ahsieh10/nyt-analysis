import { useContext, useEffect, useRef, useState } from "react";
import { SentimentContext } from "../../../contexts/sentimentContext";
import { getSentimentTextStyle } from "../../../constants/constants";
import "./ToggleElement.scss";

// Component that allow for toggling between the different result sections
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
    >
      {label}
    </span>
  );
};

export default ToggleElement;
