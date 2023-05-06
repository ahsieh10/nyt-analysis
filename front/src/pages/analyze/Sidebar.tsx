import ArticleCard from "./ArticleCard";
import { motion } from "framer-motion";
import { animationDuration } from "../../constants/constants";
import { Scrollbars } from "react-custom-scrollbars-2";
import { SuccessDataResult } from "../../interfaces/interfaces";
import "./Sidebar.scss";
export const TEXT_sidebar_accessible_name =
  "This is the sidebar. It has clickable buttons that lead to links of the top 10 articles used to calculate the sentiment analysis";

// Sidebar component that displays the article card buttons and makes the
// display scrollable

interface SidebarProps {
  result: SuccessDataResult;
}
const Sidebar = ({ result }: SidebarProps) => {
  return (
    <motion.div
      className="sidebar"
      initial={{ x: -300, opacity: 0 }}
      animate={{ x: 0, opacity: 1 }}
      transition={{ duration: animationDuration, ease: "easeInOut" }}
    >
      <Scrollbars style={{ width: "100%", height: "100%" }}>
        <div
          className="sidebar-contents"
          aria-label={TEXT_sidebar_accessible_name}
          role="sidebar"
        >
          <div className="section-subheading"> Articles Analyzed</div>
          {result.articles.map((article) => (
            <ArticleCard key={article.webUrl} article={article} />
          ))}
        </div>
      </Scrollbars>
    </motion.div>
  );
};

export default Sidebar;
