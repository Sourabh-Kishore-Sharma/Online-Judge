import MonacoEditor from "@monaco-editor/react";
import { useState } from "react";

const Editor = ({ language }) => {
  const [code, setCode] = useState("");

  return (
    <div>
      <MonacoEditor
        height="600px"
        width="100%"
        language={language}
        value={code}
        onChange={(value) => setCode(value || "")}
        options={{
          theme: "vs-dark",
          fontSize: 16,
          minimap: { enabled: false },
        }}
      />
    </div>
  );
};

export default Editor;
