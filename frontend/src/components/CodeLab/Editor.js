import MonacoEditor from "@monaco-editor/react";

const Editor = ({ language, code, setCode }) => {
  return (
    <div>
      <MonacoEditor
        height="600px"
        width="100%"
        language={language}
        value={code}
        onChange={(value) => setCode(value || "")}
        options={{
          theme: "vs-light",
          fontSize: 16,
          minimap: { enabled: false },
        }}
      />
    </div>
  );
};

export default Editor;
