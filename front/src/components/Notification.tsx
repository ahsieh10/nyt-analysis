import { motion } from "framer-motion";
import { IoIosWarning } from "react-icons/io";
import "./Notification.scss";

interface NotificationProps {
  message: string;
  animationState: "hidden" | "shown";
}

const animationStates = {
  hidden: { opacity: 0, x: 50 },
  shown: {
    x: -30,
    opacity: 1,
  },
};

const Notification = ({ message, animationState }: NotificationProps) => {
  return (
    <motion.div
      className="notification-box"
      initial={animationStates.hidden}
      animate={animationStates[animationState]}
      transition={{ duration: 0.5, ease: "easeInOut" }}
    >
      <IoIosWarning color="#ffd147" />
      {message}
    </motion.div>
  );
};

export default Notification;
