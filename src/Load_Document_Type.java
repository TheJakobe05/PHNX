
public class Load_Document_Type {
	
	GUI gui;
	
	String extension = ".txt";
	
	public Load_Document_Type(GUI gui) {
		
		this.gui = gui;
		
	}
	
	public void loadDocumentType(String fileName) {
		int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0) {
            extension = fileName.substring(dotIndex);
        }
        switch (extension.toLowerCase()) {
        // Plain text & documents
        case ".txt": gui.docTypeLabel.setText("Plain Text"); break;
        case ".md": case ".markdown": gui.docTypeLabel.setText("Markdown"); break;
        case ".rst": gui.docTypeLabel.setText("reStructuredText"); break;
        case ".rtf": gui.docTypeLabel.setText("Rich Text"); break;
        case ".csv": gui.docTypeLabel.setText("CSV"); break;
        case ".tsv": gui.docTypeLabel.setText("TSV"); break;
        case ".log": gui.docTypeLabel.setText("Log File"); break;

        // Web
        case ".html": case ".htm": gui.docTypeLabel.setText("HTML"); break;
        case ".css": gui.docTypeLabel.setText("CSS"); break;
        case ".scss": case ".sass": gui.docTypeLabel.setText("Sass/SCSS"); break;
        case ".less": gui.docTypeLabel.setText("Less"); break;
        case ".js": case ".mjs": case ".cjs": gui.docTypeLabel.setText("JavaScript"); break;
        case ".ts": gui.docTypeLabel.setText("TypeScript"); break;
        case ".tsx": gui.docTypeLabel.setText("TypeScript (TSX)"); break;
        case ".jsx": gui.docTypeLabel.setText("JavaScript (JSX)"); break;
        case ".vue": gui.docTypeLabel.setText("Vue"); break;
        case ".svelte": gui.docTypeLabel.setText("Svelte"); break;
        case ".json": gui.docTypeLabel.setText("JSON"); break;
        case ".xml": gui.docTypeLabel.setText("XML"); break;
        case ".yaml": case ".yml": gui.docTypeLabel.setText("YAML"); break;
        case ".toml": gui.docTypeLabel.setText("TOML"); break;
        case ".ini": case ".cfg": case ".conf": gui.docTypeLabel.setText("Config"); break;
        case ".env": gui.docTypeLabel.setText("Environment"); break;

        // Programming languages
        case ".java": gui.docTypeLabel.setText("Java"); break;
        case ".kt": case ".kts": gui.docTypeLabel.setText("Kotlin"); break;
        case ".scala": gui.docTypeLabel.setText("Scala"); break;
        case ".groovy": gui.docTypeLabel.setText("Groovy"); break;
        case ".py": case ".pyw": case ".pyx": gui.docTypeLabel.setText("Python"); break;
        case ".rb": gui.docTypeLabel.setText("Ruby"); break;
        case ".php": gui.docTypeLabel.setText("PHP"); break;
        case ".go": gui.docTypeLabel.setText("Go"); break;
        case ".rs": gui.docTypeLabel.setText("Rust"); break;
        case ".c": gui.docTypeLabel.setText("C"); break;
        case ".h": gui.docTypeLabel.setText("C Header"); break;
        case ".cpp": case ".cc": case ".cxx": gui.docTypeLabel.setText("C++"); break;
        case ".hpp": case ".hh": case ".hxx": gui.docTypeLabel.setText("C++ Header"); break;
        case ".cs": gui.docTypeLabel.setText("C#"); break;
        case ".fs": case ".fsi": case ".fsx": gui.docTypeLabel.setText("F#"); break;
        case ".vb": gui.docTypeLabel.setText("Visual Basic"); break;
        case ".swift": gui.docTypeLabel.setText("Swift"); break;
        case ".m": gui.docTypeLabel.setText("Objective-C"); break;
        case ".mm": gui.docTypeLabel.setText("Objective-C++"); break;
        case ".pl": case ".pm": gui.docTypeLabel.setText("Perl"); break;
        case ".lua": gui.docTypeLabel.setText("Lua"); break;
        case ".r": gui.docTypeLabel.setText("R"); break;
        case ".jl": gui.docTypeLabel.setText("Julia"); break;
        case ".dart": gui.docTypeLabel.setText("Dart"); break;
        case ".ex": case ".exs": gui.docTypeLabel.setText("Elixir"); break;
        case ".erl": case ".hrl": gui.docTypeLabel.setText("Erlang"); break;
        case ".hs": case ".lhs": gui.docTypeLabel.setText("Haskell"); break;
        case ".clj": case ".cljs": case ".cljc": gui.docTypeLabel.setText("Clojure"); break;
        case ".lisp": case ".cl": case ".el": gui.docTypeLabel.setText("Lisp"); break;
        case ".scm": gui.docTypeLabel.setText("Scheme"); break;
        case ".ml": case ".mli": gui.docTypeLabel.setText("OCaml"); break;
        case ".nim": gui.docTypeLabel.setText("Nim"); break;
        case ".zig": gui.docTypeLabel.setText("Zig"); break;
        case ".v": gui.docTypeLabel.setText("V"); break;
        case ".cr": gui.docTypeLabel.setText("Crystal"); break;
        case ".d": gui.docTypeLabel.setText("D"); break;
        case ".pas": case ".pp": gui.docTypeLabel.setText("Pascal"); break;
        case ".ada": case ".adb": case ".ads": gui.docTypeLabel.setText("Ada"); break;
        case ".f": case ".for": case ".f90": case ".f95": gui.docTypeLabel.setText("Fortran"); break;
        case ".cob": case ".cbl": gui.docTypeLabel.setText("COBOL"); break;
        case ".asm": case ".s": gui.docTypeLabel.setText("Assembly"); break;
        case ".sql": gui.docTypeLabel.setText("SQL"); break;
        case ".sh": case ".bash": case ".zsh": case ".fish": gui.docTypeLabel.setText("Shell Script"); break;
        case ".ps1": case ".psm1": gui.docTypeLabel.setText("PowerShell"); break;
        case ".bat": case ".cmd": gui.docTypeLabel.setText("Batch"); break;
        case ".makefile": case ".mk": gui.docTypeLabel.setText("Makefile"); break;
        case ".cmake": gui.docTypeLabel.setText("CMake"); break;
        case ".gradle": gui.docTypeLabel.setText("Gradle"); break;
        case ".dockerfile": gui.docTypeLabel.setText("Dockerfile"); break;

        // Data & markup
        case ".graphql": case ".gql": gui.docTypeLabel.setText("GraphQL"); break;
        case ".proto": gui.docTypeLabel.setText("Protocol Buffers"); break;
        case ".avsc": gui.docTypeLabel.setText("Avro Schema"); break;
        case ".thrift": gui.docTypeLabel.setText("Thrift"); break;
        case ".xsd": gui.docTypeLabel.setText("XML Schema"); break;
        case ".dtd": gui.docTypeLabel.setText("DTD"); break;
        case ".tex": gui.docTypeLabel.setText("LaTeX"); break;
        case ".bib": gui.docTypeLabel.setText("BibTeX"); break;

        // Office / editable documents
        case ".docx": gui.docTypeLabel.setText("Word Document"); break;
        case ".xlsx": gui.docTypeLabel.setText("Excel Spreadsheet"); break;
        case ".pptx": gui.docTypeLabel.setText("PowerPoint"); break;
        case ".odt": gui.docTypeLabel.setText("OpenDocument Text"); break;
        case ".ods": gui.docTypeLabel.setText("OpenDocument Spreadsheet"); break;
        case ".odp": gui.docTypeLabel.setText("OpenDocument Presentation"); break;

        // Images (viewable/editable)
        case ".svg": gui.docTypeLabel.setText("SVG"); break;
        case ".png": gui.docTypeLabel.setText("PNG Image"); break;
        case ".jpg": case ".jpeg": gui.docTypeLabel.setText("JPEG Image"); break;
        case ".gif": gui.docTypeLabel.setText("GIF Image"); break;
        case ".webp": gui.docTypeLabel.setText("WebP Image"); break;
        case ".bmp": gui.docTypeLabel.setText("BMP Image"); break;
        case ".ico": gui.docTypeLabel.setText("Icon"); break;

        // Other common editable
        case ".diff": case ".patch": gui.docTypeLabel.setText("Diff/Patch"); break;
        case ".gitignore": gui.docTypeLabel.setText("Git Ignore"); break;
        case ".gitattributes": gui.docTypeLabel.setText("Git Attributes"); break;
        case ".editorconfig": gui.docTypeLabel.setText("EditorConfig"); break;
        case ".properties": gui.docTypeLabel.setText("Properties"); break;

        default: gui.docTypeLabel.setText("Unknown"); break;
    }
	}
}
