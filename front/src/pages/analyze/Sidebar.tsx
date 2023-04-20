import { mockArticleData } from "../../mocks/articles";
import ArticleCard from "./ArticleCard";
import { motion } from "framer-motion";
import "./Sidebar.scss";
import { animationDuration } from "../../constants/constants";

const Sidebar = () => {
  return (
    <motion.div
      className="sidebar"
      initial={{ x: -300, opacity: 0 }}
      animate={{ x: 0, opacity: 1 }}
      transition={{ duration: animationDuration, ease: "easeInOut" }}
    >
      <div className="sidebar-contents">
        <div className="section-subheading"> ARTICLES ANALYZED</div>
        {mockArticleData.map((article) => (
          <ArticleCard key={article._id} article={article} />
        ))}
      </div>
    </motion.div>
  );
};

export default Sidebar;
