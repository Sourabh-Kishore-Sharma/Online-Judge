import { useState } from "react";
import "./ChatBot.css";
import { TbMessageChatbotFilled } from "react-icons/tb";
import { FaWindowClose } from "react-icons/fa";

const ChatBot = () => {
  const [active, setActive] = useState(false);
  const [messages, setMessages] = useState([]);
  const [userInput, setUserInput] = useState("");

  const API_URL = process.env.REACT_APP_API_URL || "";

  const handleSend = async () => {
    if (!userInput.trim()) return;

    setMessages((prev) => [...prev, { text: userInput, sender: "user" }]);
    const currInput = userInput;
    setUserInput("");

    try {
      const res = await fetch(`${API_URL}/api/ai/ask`, {
        method: "POST",
        body: JSON.stringify({ query: currInput }),
      });
      const data = await res.json();

      setMessages((prev) => [...prev, { text: data.answer, sender: "bot" }]);
    } catch {
      setMessages((prev) => [
        ...prev,
        { text: "Opps, something went wrong.", sender: "bot" },
      ]);
    }
  };

  if (!active) {
    return (
      <div className="bot-icon" onClick={() => setActive(true)}>
        <TbMessageChatbotFilled size={24} />
      </div>
    );
  }

  return (
    <div className="bot-parent">
      <div className="bot-header">
        Help
        <button className="bot-close" onClick={() => setActive(false)}>
          <FaWindowClose />
        </button>
      </div>
      <div className="bot-body">
        {messages.map((msg, index) => (
          <div
            key={index}
            className={`message ${
              msg.sender === "user" ? "user-msg" : "bot-msg"
            }`}
          >
            {msg.text}
          </div>
        ))}
      </div>
      <div className="bot-query">
        <input
          type="text"
          placeholder="Ask Anything..."
          value={userInput}
          onKeyDown={(e) => {
            if (e.key === "Enter" && !e.shiftKey) {
              e.preventDefault();
              handleSend();
            }
          }}
          onChange={(e) => {
            setUserInput(e.target.value);
          }}
        ></input>
        <button onClick={handleSend}>Send</button>
      </div>
    </div>
  );
};

export default ChatBot;
