import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.Timer;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class FileTree extends JPanel {
	
	GUI gui;
	
	private static final long serialVersionUID = 6675394901528304287L;
	
    public final JTree tree;
    JScrollPane scroll;
    private final DefaultTreeModel model;
    private File rootFolder;
    JLabel title;
    
    private Timer refreshTimer;
    
    public DefaultTreeCellRenderer renderer;

    public FileTree(GUI gui) {
        super(new BorderLayout());
        this.gui = gui;
        
     // Title above the tree
        title = new JLabel("  FOLDERS");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 14f));
        title.setBorder(BorderFactory.createEmptyBorder(6, 4, 6, 4));
        add(title, BorderLayout.NORTH);
        title.setOpaque(true);
        title.setBackground(new Color(255, 0, 0));

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Folder Tree Loading...");
        model = new DefaultTreeModel(root);
        tree = new JTree(model);
        
        Font treeFont = tree.getFont();
        tree.setFont(new Font(treeFont.getName(), treeFont.getStyle(), 12));
        tree.setRootVisible(true);
        tree.setShowsRootHandles(true);
        
        renderer = new FileTreeCellRenderer();
        renderer.setBackgroundNonSelectionColor(new Color(40, 40, 45));
        renderer.setBackgroundSelectionColor(new Color(70, 90, 120));
        renderer.setTextNonSelectionColor(new Color(210, 210, 210));
        renderer.setTextSelectionColor(Color.WHITE);
        tree.setCellRenderer(renderer);
        
        tree.setForeground(new Color(0, 255, 0));
        tree.setBackground(new Color(0, 155, 255));
        
        tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() != 2) return;

                TreePath path = tree.getPathForLocation(e.getX(), e.getY());
                if (path == null) return;

                DefaultMutableTreeNode node =
                        (DefaultMutableTreeNode) path.getLastPathComponent();
                Object uo = node.getUserObject();
                if (!(uo instanceof FileNode)) return;

                File f = ((FileNode) uo).file;
                if (f.isFile()) {
                    gui.openFileFromDrop(f);
                } else if (f.isDirectory() && node.isLeaf()) {
                    loadChildren(node); // expand folder on first open
                    model.reload(node);
                    tree.expandPath(path);
                }
            }
        });

        scroll = new JScrollPane(tree);
        scroll.setBorder(null);                    // removes the 1px grey border
        scroll.setViewportBorder(null);            // extra safety
        tree.setBorder(null);
        scroll.setPreferredSize(new Dimension(220, 100));
        add(scroll, BorderLayout.CENTER);
    }

    /** Open / change the folder shown in the tree */
    public void setRootFolder(File folder) {
    	startAutoRefresh(2000);
        if (folder == null || !folder.isDirectory()) return;

        rootFolder = folder;
        FileNode rootNode = new FileNode(folder);
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootNode);
        loadChildrenRecursive(root);
        model.setRoot(root);
        model.reload();
        tree.setRootVisible(true);
    }

    private void loadChildren(DefaultMutableTreeNode parent) {
        parent.removeAllChildren();
        Object uo = parent.getUserObject();
        if (!(uo instanceof FileNode)) return;

        File dir = ((FileNode) uo).file;
        File[] files = dir.listFiles();
        if (files == null) return;

        // Folders first, then files (simple sort)
        java.util.Arrays.sort(files, (a, b) -> {
            if (a.isDirectory() && !b.isDirectory()) return -1;
            if (!a.isDirectory() && b.isDirectory()) return 1;
            return a.getName().compareToIgnoreCase(b.getName());
        });

        for (File f : files) {
            // skip hidden
            if (f.isHidden()) continue;
            DefaultMutableTreeNode child = new DefaultMutableTreeNode(new FileNode(f));
            parent.add(child);
        }
    }
    
    public void loadChildrenRecursive(DefaultMutableTreeNode parent) {
        Object uo = parent.getUserObject();
        if (!(uo instanceof FileNode)) return;

        File dir = ((FileNode) uo).file;
        if (!dir.isDirectory()) return;

        File[] files = dir.listFiles();
        if (files == null) return;

        java.util.Arrays.sort(files, (a, b) -> {
            if (a.isDirectory() && !b.isDirectory()) return -1;
            if (!a.isDirectory() && b.isDirectory()) return 1;
            return a.getName().compareToIgnoreCase(b.getName());
        });

        for (File f : files) {
            if (f.isHidden()) continue;

            DefaultMutableTreeNode child = new DefaultMutableTreeNode(new FileNode(f));
            parent.add(child);

            // Preload subfolders so they can be expanded later
            if (f.isDirectory()) {
                loadChildrenRecursive(child);
            }
        }
    }

    public File getRootFolder() {
        return rootFolder;
    }

    /** Display name in the tree */
    static class FileNode {
        final File file;
        FileNode(File file) { this.file = file; }
        @Override
        public String toString() {
            return file.getName().isEmpty() ? file.getPath() : file.getName();
        }
    }
    
    public class FileTreeCellRenderer extends DefaultTreeCellRenderer {

    	// Folders
        private final Icon folderIcon;
        private final Icon openFolderIcon;
        private final Icon defaultFileIcon;

        // Text / data
        private final Icon txtIcon, mdIcon, csvIcon, logIcon, jsonIcon, xmlIcon, yamlIcon, propertiesIcon;

        // Web
        private final Icon htmlIcon, cssIcon, jsIcon, tsIcon, vueIcon;

        // Languages
        private final Icon javaIcon, ktIcon, pyIcon, rbIcon, phpIcon, goIcon, rsIcon;
        private final Icon cIcon, cppIcon, csIcon, vbIcon, perlIcon, luaIcon, dartIcon, lispIcon, sqlIcon;

        // Scripts / config
        private final Icon shIcon, batIcon, makefileIcon, gitIcon;

        // Images
        private final Icon imageIcon, svgIcon;

        // Documents
        private final Icon docIcon, xlsIcon, pptIcon;

        public FileTreeCellRenderer() {
            // Load your icons (put the PNGs on the classpath, e.g. res/ or resources/)
        	folderIcon      = loadIcon("folder.png");
            openFolderIcon  = loadIcon("openfolder.png");
            defaultFileIcon = loadIcon("default.png");

            txtIcon         = loadIcon("txt.png");
            mdIcon          = loadIcon("md.png");
            csvIcon         = loadIcon("default.png");
            logIcon         = loadIcon("log.png");
            jsonIcon        = loadIcon("json.png");
            xmlIcon         = loadIcon("xml.png");
            yamlIcon        = loadIcon("yaml.png");
            propertiesIcon  = loadIcon("properties.png");
            gitIcon  = loadIcon("git.png");

            htmlIcon        = loadIcon("html.png");
            cssIcon         = loadIcon("css.png");
            jsIcon          = loadIcon("js.png");
            tsIcon          = loadIcon("ts.png");
            vueIcon         = loadIcon("vue.png");

            javaIcon        = loadIcon("java.png");
            ktIcon          = loadIcon("kt.png");
            pyIcon          = loadIcon("py.png");
            rbIcon          = loadIcon("rb.png");
            phpIcon         = loadIcon("php.png");
            goIcon          = loadIcon("go.png");
            rsIcon          = loadIcon("rust.png");
            cIcon           = loadIcon("c.png");
            cppIcon         = loadIcon("cpp.png");
            csIcon          = loadIcon("cs.png");
            vbIcon          = loadIcon("vb.png");
            perlIcon        = loadIcon("perl.png");
            luaIcon         = loadIcon("lua.png");
            dartIcon        = loadIcon("dart.png");
            lispIcon        = loadIcon("lisp.png");
            sqlIcon         = loadIcon("sql.png");

            shIcon          = loadIcon("ps1.png");
            batIcon         = loadIcon("bat.png");
            makefileIcon    = loadIcon("default.png");

            imageIcon       = loadIcon("img.png");
            svgIcon         = loadIcon("svg.png");

            docIcon         = loadIcon("docx.png");
            xlsIcon         = loadIcon("xlsx.png");
            pptIcon         = loadIcon("pptx.png");
        }

        private Icon loadIcon(String name) {
            try {
                java.net.URL url = getClass().getClassLoader().getResource(name);
                if (url != null) {
                    return new ImageIcon(url);
                }
            } catch (Exception ignored) {}
            return null; // falls back to default Swing icon if missing
        }

        @Override
        public Component getTreeCellRendererComponent(
                JTree tree, Object value, boolean sel,
                boolean expanded, boolean leaf, int row, boolean hasFocus) {

            super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            Object uo = node.getUserObject();

            if (uo instanceof FileTree.FileNode) {
                File f = ((FileTree.FileNode) uo).file;

                if (f.isDirectory()) {
                    setIcon(expanded ? openFolderIcon : folderIcon);
                } else {
                    setIcon(iconForFile(f));
                }
            }

            return this;
        }

        private Icon iconForFile(File f) {
            String name = f.getName().toLowerCase();

            // Text / data
            if (name.endsWith(".txt") || name.endsWith(".rst") || name.endsWith(".rtf") ||
                name.endsWith(".log") || name.endsWith(".tsv")) return txtIcon;
            if (name.endsWith(".md") || name.endsWith(".markdown")) return mdIcon;
            if (name.endsWith(".csv")) return csvIcon;
            if (name.endsWith(".json")) return jsonIcon;
            if (name.endsWith(".xml") || name.endsWith(".xsd") || name.endsWith(".dtd")) return xmlIcon;
            if (name.endsWith(".yaml") || name.endsWith(".yml")) return yamlIcon;
            if (name.endsWith(".properties") || name.endsWith(".ini") || name.endsWith(".cfg") ||
                name.endsWith(".conf") || name.endsWith(".env") || name.endsWith(".toml")) return propertiesIcon;

            // Web
            if (name.endsWith(".html") || name.endsWith(".htm")) return htmlIcon;
            if (name.endsWith(".css") || name.endsWith(".scss") || name.endsWith(".sass") ||
                name.endsWith(".less")) return cssIcon;
            if (name.endsWith(".js") || name.endsWith(".mjs") || name.endsWith(".cjs") ||
                name.endsWith(".jsx")) return jsIcon;
            if (name.endsWith(".ts") || name.endsWith(".tsx")) return tsIcon;
            if (name.endsWith(".vue") || name.endsWith(".svelte")) return vueIcon;

            // Languages
            if (name.endsWith(".java")) return javaIcon;
            if (name.endsWith(".kt") || name.endsWith(".kts")) return ktIcon;
            if (name.endsWith(".scala") || name.endsWith(".groovy")) return javaIcon;
            if (name.endsWith(".py") || name.endsWith(".pyw") || name.endsWith(".pyx")) return pyIcon;
            if (name.endsWith(".rb")) return rbIcon;
            if (name.endsWith(".php")) return phpIcon;
            if (name.endsWith(".go")) return goIcon;
            if (name.endsWith(".rs")) return rsIcon;
            if (name.endsWith(".c") || name.endsWith(".h")) return cIcon;
            if (name.endsWith(".cpp") || name.endsWith(".cc") || name.endsWith(".cxx") ||
                name.endsWith(".hpp") || name.endsWith(".hh") || name.endsWith(".hxx")) return cppIcon;
            if (name.endsWith(".cs")) return csIcon;
            if (name.endsWith(".vb")) return vbIcon;
            if (name.endsWith(".pl") || name.endsWith(".pm")) return perlIcon;
            if (name.endsWith(".lua")) return luaIcon;
            if (name.endsWith(".dart")) return dartIcon;
            if (name.endsWith(".lisp") || name.endsWith(".cl") || name.endsWith(".el") ||
                name.endsWith(".scm")) return lispIcon;
            if (name.endsWith(".sql")) return sqlIcon;
            if (name.endsWith(".asm") || name.endsWith(".s")) return cIcon;

            // Scripts / build
            if (name.endsWith(".sh") || name.endsWith(".bash") || name.endsWith(".zsh") ||
                name.endsWith(".fish") || name.endsWith(".ps1") || name.endsWith(".psm1")) return shIcon;
            if (name.endsWith(".bat") || name.endsWith(".cmd")) return batIcon;
            if (name.equals("makefile") || name.endsWith(".mk") || name.endsWith(".cmake") ||
                name.endsWith(".gradle") || name.equals("dockerfile")) return makefileIcon;

            // Images
            if (name.endsWith(".svg")) return svgIcon;
            if (name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".jpeg") ||
                name.endsWith(".gif") || name.endsWith(".webp") || name.endsWith(".bmp") ||
                name.endsWith(".ico")) return imageIcon;

            // Documents
            if (name.endsWith(".docx") || name.endsWith(".odt")) return docIcon;
            if (name.endsWith(".xlsx") || name.endsWith(".ods")) return xlsIcon;
            if (name.endsWith(".pptx") || name.endsWith(".odp")) return pptIcon;

            // Git / misc
            if (name.equals(".gitattributes") ||
                    name.equals(".editorconfig") || name.endsWith(".diff") || name.endsWith(".patch")) {
                    return propertiesIcon;
                }
            if (name.equals(".gitignore")) return gitIcon;

            return defaultFileIcon;
        }
    }
    
    public void startAutoRefresh(int intervalMs) {
        if (refreshTimer != null) {
            refreshTimer.stop();
        }
        refreshTimer = new Timer(intervalMs, e -> refresh());
        refreshTimer.setRepeats(true);
        refreshTimer.start();
    }

    public void stopAutoRefresh() {
        if (refreshTimer != null) {
            refreshTimer.stop();
        }
    }

    /** Reload the current root folder, keeping expansion state as best as possible */
    public void refresh() {
        if (rootFolder == null || !rootFolder.exists()) return;

        // Remember which paths were expanded
        java.util.Set<String> expanded = new java.util.HashSet<>();
        collectExpandedPaths(new TreePath(model.getRoot()), expanded);

        // Reload
        FileNode rootNode = new FileNode(rootFolder);
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootNode);
        loadChildrenRecursive(root);
        model.setRoot(root);
        model.reload();

        // Restore expansion
        restoreExpanded(new TreePath(root), expanded);
    }

    private void collectExpandedPaths(TreePath path, java.util.Set<String> expanded) {
        if (tree.isExpanded(path)) {
            expanded.add(path.toString());
        }
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
        for (int i = 0; i < node.getChildCount(); i++) {
            collectExpandedPaths(path.pathByAddingChild(node.getChildAt(i)), expanded);
        }
    }

    private void restoreExpanded(TreePath path, java.util.Set<String> expanded) {
        if (expanded.contains(path.toString())) {
            tree.expandPath(path);
        }
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
        for (int i = 0; i < node.getChildCount(); i++) {
            restoreExpanded(path.pathByAddingChild(node.getChildAt(i)), expanded);
        }
    }
}