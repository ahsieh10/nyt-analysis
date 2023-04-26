import ArticleCard from "./ArticleCard";
import { motion } from "framer-motion";
import { animationDuration } from "../../constants/constants";
import { Scrollbars } from "react-custom-scrollbars-2";
import { Result } from "../../interfaces/interfaces";
import Popup from "./AboutPopup"
import "./Sidebar.scss";

interface SidebarProps {
  result: Result;
}
const Sidebar = ({ result }: SidebarProps) => {
  return (
    <motion.div
      className="sidebar"
      initial={{ x: -300, opacity: 0 }}
      animate={{ x: 0, opacity: 1 }}
      transition={{ duration: animationDuration, ease: "easeInOut" }}
      onWheel={(e) => e.stopPropagation()}
    >
      <Scrollbars style={{ width: "100%", height: "100%" }}>
        <div className="sidebar-contents">
          <div className="section-subheading"> Articles Analyzed</div>
          {result.articles.map((article) => (
            <ArticleCard key={article._id} article={article} />
          ))}
        </div>
      </Scrollbars>
    </motion.div>
  );
};

export default Sidebar;
