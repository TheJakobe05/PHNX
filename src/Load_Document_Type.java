import org.fife.ui.rsyntaxtextarea.SyntaxConstants;

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
        case ".txt": gui.docTypeLabel.setText("Plain Text"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".md": case ".markdown": gui.docTypeLabel.setText("Markdown"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_MARKDOWN); break;
        case ".rst": gui.docTypeLabel.setText("reStructuredText"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".rtf": gui.docTypeLabel.setText("Rich Text"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".csv": gui.docTypeLabel.setText("CSV"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_CSV); break;
        case ".tsv": gui.docTypeLabel.setText("TSV"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".log": gui.docTypeLabel.setText("Log File"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;

        // Web
        case ".html": case ".htm": gui.docTypeLabel.setText("HTML"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_HTML); break;
        case ".css": gui.docTypeLabel.setText("CSS"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_CSS); break;
        case ".scss": case ".sass": gui.docTypeLabel.setText("Sass/SCSS"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".less": gui.docTypeLabel.setText("Less"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_SAS); break;
        case ".js": case ".mjs": case ".cjs": gui.docTypeLabel.setText("JavaScript"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT); break;
        case ".ts": gui.docTypeLabel.setText("TypeScript"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_TYPESCRIPT); break;
        case ".tsx": gui.docTypeLabel.setText("TypeScript (TSX)"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_TYPESCRIPT); break;
        case ".jsx": gui.docTypeLabel.setText("JavaScript (JSX)"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_TYPESCRIPT); break;
        case ".vue": gui.docTypeLabel.setText("Vue"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_TYPESCRIPT); break;
        case ".svelte": gui.docTypeLabel.setText("Svelte"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_TYPESCRIPT); break;
        case ".json": gui.docTypeLabel.setText("JSON"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JSON); break;
        case ".xml": gui.docTypeLabel.setText("XML"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_XML); break;
        case ".yaml": case ".yml": gui.docTypeLabel.setText("YAML"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_YAML); break;
        case ".toml": gui.docTypeLabel.setText("TOML"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".ini": case ".cfg": case ".conf": gui.docTypeLabel.setText("Config"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".env": gui.docTypeLabel.setText("Environment"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;

        // Programming languages
        case ".java": gui.docTypeLabel.setText("Java"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA); break;
        case ".kt": case ".kts": gui.docTypeLabel.setText("Kotlin"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_KOTLIN); break;
        case ".scala": gui.docTypeLabel.setText("Scala"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_SCALA); break;
        case ".groovy": gui.docTypeLabel.setText("Groovy"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_GROOVY); break;
        case ".py": case ".pyw": case ".pyx": gui.docTypeLabel.setText("Python"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_PYTHON); break;
        case ".rb": gui.docTypeLabel.setText("Ruby"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_RUBY); break;
        case ".php": gui.docTypeLabel.setText("PHP"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_PHP); break;
        case ".go": gui.docTypeLabel.setText("Go"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_GO); break;
        case ".rs": gui.docTypeLabel.setText("Rust"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_RUST); break;
        case ".c": gui.docTypeLabel.setText("C"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_C); break;
        case ".h": gui.docTypeLabel.setText("C Header"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".cpp": case ".cc": case ".cxx": gui.docTypeLabel.setText("C++"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_CPLUSPLUS); break;
        case ".hpp": case ".hh": case ".hxx": gui.docTypeLabel.setText("C++ Header"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".cs": gui.docTypeLabel.setText("C#"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_CSHARP); break;
        case ".fs": case ".fsi": case ".fsx": gui.docTypeLabel.setText("F#"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".vb": gui.docTypeLabel.setText("Visual Basic"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_VISUAL_BASIC); break;
        case ".swift": gui.docTypeLabel.setText("Swift"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".m": gui.docTypeLabel.setText("Objective-C"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".mm": gui.docTypeLabel.setText("Objective-C++"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".pl": case ".pm": gui.docTypeLabel.setText("Perl"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_PERL); break;
        case ".lua": gui.docTypeLabel.setText("Lua"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_LUA); break;
        case ".r": gui.docTypeLabel.setText("R"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".jl": gui.docTypeLabel.setText("Julia"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".dart": gui.docTypeLabel.setText("Dart"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_DART); break;
        case ".ex": case ".exs": gui.docTypeLabel.setText("Elixir"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".erl": case ".hrl": gui.docTypeLabel.setText("Erlang"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".hs": case ".lhs": gui.docTypeLabel.setText("Haskell"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".clj": case ".cljs": case ".cljc": gui.docTypeLabel.setText("Clojure"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".lisp": case ".cl": case ".el": gui.docTypeLabel.setText("Lisp"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_LISP); break;
        case ".scm": gui.docTypeLabel.setText("Scheme"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".ml": case ".mli": gui.docTypeLabel.setText("OCaml"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".nim": gui.docTypeLabel.setText("Nim"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".zig": gui.docTypeLabel.setText("Zig"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".v": gui.docTypeLabel.setText("V"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".cr": gui.docTypeLabel.setText("Crystal"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".d": gui.docTypeLabel.setText("D"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".pas": case ".pp": gui.docTypeLabel.setText("Pascal"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".ada": case ".adb": case ".ads": gui.docTypeLabel.setText("Ada"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".f": case ".for": case ".f90": case ".f95": gui.docTypeLabel.setText("Fortran"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".cob": case ".cbl": gui.docTypeLabel.setText("COBOL"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".asm": case ".s": gui.docTypeLabel.setText("Assembly"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_ASSEMBLER_X86); break;
        case ".sql": gui.docTypeLabel.setText("SQL"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_SQL); break;
        case ".sh": case ".bash": case ".zsh": case ".fish": gui.docTypeLabel.setText("Shell Script"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".ps1": case ".psm1": gui.docTypeLabel.setText("PowerShell"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".bat": case ".cmd": gui.docTypeLabel.setText("Batch"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".makefile": case ".mk": gui.docTypeLabel.setText("Makefile"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".cmake": gui.docTypeLabel.setText("CMake"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".gradle": gui.docTypeLabel.setText("Gradle"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".dockerfile": gui.docTypeLabel.setText("Dockerfile"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;

        // Data & markup
        case ".graphql": case ".gql": gui.docTypeLabel.setText("GraphQL"); break;
        case ".proto": gui.docTypeLabel.setText("Protocol Buffers"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".avsc": gui.docTypeLabel.setText("Avro Schema"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".thrift": gui.docTypeLabel.setText("Thrift"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".xsd": gui.docTypeLabel.setText("XML Schema"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".dtd": gui.docTypeLabel.setText("DTD"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".tex": gui.docTypeLabel.setText("LaTeX"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".bib": gui.docTypeLabel.setText("BibTeX"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;

        // Office / editable documents
        case ".docx": gui.docTypeLabel.setText("Word Document"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".xlsx": gui.docTypeLabel.setText("Excel Spreadsheet"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".pptx": gui.docTypeLabel.setText("PowerPoint"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".odt": gui.docTypeLabel.setText("OpenDocument Text"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".ods": gui.docTypeLabel.setText("OpenDocument Spreadsheet"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".odp": gui.docTypeLabel.setText("OpenDocument Presentation"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;

        // Images (viewable/editable)
        case ".svg": gui.docTypeLabel.setText("SVG"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".png": gui.docTypeLabel.setText("PNG Image"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".jpg": case ".jpeg": gui.docTypeLabel.setText("JPEG Image"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".gif": gui.docTypeLabel.setText("GIF Image"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".webp": gui.docTypeLabel.setText("WebP Image"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".bmp": gui.docTypeLabel.setText("BMP Image"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".ico": gui.docTypeLabel.setText("Icon"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;

        // Other common editable
        case ".diff": case ".patch": gui.docTypeLabel.setText("Diff/Patch"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".gitignore": gui.docTypeLabel.setText("Git Ignore"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".gitattributes": gui.docTypeLabel.setText("Git Attributes"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".editorconfig": gui.docTypeLabel.setText("EditorConfig"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_NONE); break;
        case ".properties": gui.docTypeLabel.setText("Properties"); gui.textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_PROPERTIES_FILE); break;

        default: gui.docTypeLabel.setText("Unknown"); break;
    }
	}
}
