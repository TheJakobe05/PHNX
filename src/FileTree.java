import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.swing.Timer;
import javax.swing.TransferHandler;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
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
    public final DefaultTreeModel model;
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
        
        tree.addTreeWillExpandListener(new javax.swing.event.TreeWillExpandListener() {
            @Override
            public void treeWillExpand(javax.swing.event.TreeExpansionEvent event) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) event.getPath().getLastPathComponent();
                if (node.getChildCount() == 0) {
                    loadChildren(node);
                    ((DefaultTreeModel) tree.getModel()).reload(node);
                }
            }
            @Override
            public void treeWillCollapse(javax.swing.event.TreeExpansionEvent event) {}
        });
        
        setupInternalDragAndDrop();
        setupContextMenu();
        
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
                TreePath path = tree.getPathForLocation(e.getX(), e.getY());
                if (path == null) return;

                DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                Object uo = node.getUserObject();
                if (!(uo instanceof FileNode)) return;

                File f = ((FileNode) uo).file;

                // Single click on a folder → toggle expand/collapse
                if (e.getClickCount() == 1 && f.isDirectory()) {
                    if (tree.isExpanded(path)) {
                        tree.collapsePath(path);
                    } else {
                        // Lazy-load children if needed
                        if (node.getChildCount() == 0) {
                            loadChildren(node);
                            model.reload(node);
                        }
                        tree.expandPath(path);
                    }
                    return;
                }

                // Double click on a file → open it
                if (e.getClickCount() == 2 && f.isFile()) {
                    gui.openFileFromDrop(f);
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
        loadChildren(root);
        model.setRoot(root);
        model.reload();
        tree.setRootVisible(true);
        
        gui.fileTree.setVisible(true);
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

        // Music Icon
        
        private final Icon musicIcon;
        
        // Video Icon
        
        private final Icon videoIcon;

        // Executable File Icons
        
        private final Icon exeIcon, jarIcon;
        
        // Documents
        private final Icon docIcon, xlsIcon, pptIcon;
        
        // zip
        private final Icon zipIcon, rarIcon, svnzIcon, tarIcon;

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
            musicIcon         = loadIcon("mp3.png");
            videoIcon         = loadIcon("mp4.png");
            
            exeIcon         = loadIcon("app.png");
            jarIcon         = loadIcon("jar.png");

            docIcon         = loadIcon("docx.png");
            xlsIcon         = loadIcon("xlsx.png");
            pptIcon         = loadIcon("pptx.png");

            zipIcon         = loadIcon("zip.png");
            rarIcon         = loadIcon("rar.png");
            svnzIcon         = loadIcon("7z.png");
            tarIcon         = loadIcon("tar.png");
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
            
         // Music
            if (name.endsWith(".mp3") || name.endsWith(".wav") || name.endsWith(".m4a") ||
                    name.endsWith(".aac") || name.endsWith(".ogg") || name.endsWith(".flac") ||
                    name.endsWith(".alac") || name.endsWith(".aiff")) return musicIcon;
            
         // Video
            if (name.endsWith(".mp4") || name.endsWith(".mov") || name.endsWith(".mkv") ||
                    name.endsWith(".avi") || name.endsWith(".webm") || name.endsWith(".wmv") ||
                    name.endsWith(".mts") || name.endsWith(".m2ts")) return videoIcon;
            
         // EXEculatable files
            if (name.endsWith(".exe"))  return exeIcon;
            if (name.endsWith(".jar"))  return jarIcon;

            // Documents
            if (name.endsWith(".docx") || name.endsWith(".odt")) return docIcon;
            if (name.endsWith(".xlsx") || name.endsWith(".ods")) return xlsIcon;
            if (name.endsWith(".pptx") || name.endsWith(".odp")) return pptIcon;

            // zips
            if (name.endsWith(".zip") || name.endsWith(".zipx")) return zipIcon;
            if (name.endsWith(".rar")) return rarIcon;
            if (name.endsWith(".7z")) return svnzIcon;
            if (name.endsWith(".tar.gz") || name.endsWith(".tar") || name.endsWith(".tar.bz2") || name.endsWith(".tar.xz")) return tarIcon;

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

    /** Reload the current root folder, keeping expansion state */
    public void refresh() {
        if (rootFolder == null || !rootFolder.exists()) return;

        // 1. Remember expanded folders by absolute path
        java.util.Set<String> expanded = new java.util.HashSet<>();
        collectExpandedByPath(new TreePath(model.getRoot()), expanded);

        // 2. Rebuild the tree (top level only)
        DefaultMutableTreeNode newRoot = new DefaultMutableTreeNode(new FileNode(rootFolder));
        loadChildren(newRoot);
        model.setRoot(newRoot);
        model.reload();

        // 3. Restore expansion (this version loads children as needed)
        restoreExpandedByPath(new TreePath(newRoot), expanded);
    }

    private void collectExpandedByPath(TreePath path, java.util.Set<String> expanded) {
        if (tree.isExpanded(path)) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
            Object uo = node.getUserObject();
            if (uo instanceof FileNode) {
                expanded.add(((FileNode) uo).file.getAbsolutePath());
            }
        }
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
        for (int i = 0; i < node.getChildCount(); i++) {
            collectExpandedByPath(path.pathByAddingChild(node.getChildAt(i)), expanded);
        }
    }

    private void restoreExpandedByPath(TreePath path, java.util.Set<String> expanded) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
        Object uo = node.getUserObject();

        if (uo instanceof FileNode) {
            String abs = ((FileNode) uo).file.getAbsolutePath();
            if (expanded.contains(abs)) {
                // Make sure children are loaded before expanding
                if (node.getChildCount() == 0) {
                    loadChildren(node);
                    model.reload(node);
                }
                tree.expandPath(path);
            }
        }

        // Recurse into children (after possible load)
        for (int i = 0; i < node.getChildCount(); i++) {
            restoreExpandedByPath(path.pathByAddingChild(node.getChildAt(i)), expanded);
        }
    }
    
    private void setupInternalDragAndDrop() {
        tree.setDragEnabled(true);

        tree.setTransferHandler(new TransferHandler() {
            private File draggedFile;

            public int getSourceActions(JComponent c) {
                return MOVE;
            }

            protected Transferable createTransferable(JComponent c) {
                TreePath path = tree.getSelectionPath();
                if (path == null) return null;

                DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                Object uo = node.getUserObject();
                if (!(uo instanceof FileNode)) return null;

                draggedFile = ((FileNode) uo).file;
                return new FileTransferable(draggedFile);
            }

            public boolean canImport(TransferSupport support) {
                if (!support.isDrop() || !support.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                    return false;
                }

                // Only allow drop on a directory
                JTree.DropLocation loc = (JTree.DropLocation) support.getDropLocation();
                TreePath path = loc.getPath();
                if (path == null) return false;

                DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                Object uo = node.getUserObject();
                if (!(uo instanceof FileNode)) return false;

                File target = ((FileNode) uo).file;
                return target.isDirectory() && draggedFile != null && !draggedFile.equals(target)
                        && !isDescendant(draggedFile, target);
            }

            public boolean importData(TransferSupport support) {
                if (!canImport(support)) return false;

                try {
                    JTree.DropLocation loc = (JTree.DropLocation) support.getDropLocation();
                    TreePath path = loc.getPath();
                    DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                    File targetDir = ((FileNode) node.getUserObject()).file;

                    @SuppressWarnings("unchecked")
                    List<File> files = (List<File>) support.getTransferable()
                            .getTransferData(DataFlavor.javaFileListFlavor);

                    for (File src : files) {
                        File dest = new File(targetDir, src.getName());
                        // Avoid overwriting
                        if (dest.exists()) {
                            System.err.println("Target already exists: " + dest);
                            continue;
                        }
                        Files.move(src.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    }

                    // Refresh the tree
                    refresh();
                    return true;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return false;
                }
            }
        });
    }

    // Helper: prevent dropping a folder into itself or its own child
    private boolean isDescendant(File parent, File child) {
        try {
            return child.getCanonicalPath().startsWith(parent.getCanonicalPath() + File.separator);
        } catch (IOException e) {
            return false;
        }
    }

    // Simple transferable
    private static class FileTransferable implements Transferable {
        private final File file;
        FileTransferable(File file) { this.file = file; }

        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[]{DataFlavor.javaFileListFlavor};
        }

        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return DataFlavor.javaFileListFlavor.equals(flavor);
        }

        @Override
        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
            if (!isDataFlavorSupported(flavor)) throw new UnsupportedFlavorException(flavor);
            return java.util.Collections.singletonList(file);
        }
    }
    
    private void setupContextMenu() {
        tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                showPopup(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                showPopup(e);
            }

            private void showPopup(MouseEvent e) {
                if (!e.isPopupTrigger()) return;

                TreePath path = tree.getPathForLocation(e.getX(), e.getY());
                if (path == null) return;

                tree.setSelectionPath(path);
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
                Object uo = node.getUserObject();
                if (!(uo instanceof FileNode)) return;

                File file = ((FileNode) uo).file;
                boolean isDir = file.isDirectory();

                JPopupMenu popup = new JPopupMenu();

                // Open in editor (files only)
                if (!isDir) {
                    JMenuItem open = new JMenuItem("Open");
                    open.addActionListener(ev -> gui.openFileFromDrop(file));
                    popup.add(open);
                    popup.addSeparator();
                }

                // Rename
                JMenuItem rename = new JMenuItem("Rename...");
                rename.addActionListener(ev -> renameFile(file));
                popup.add(rename);

                // Delete
                JMenuItem delete = new JMenuItem("Delete");
                delete.addActionListener(ev -> deleteFile(file));
                popup.add(delete);

                popup.addSeparator();

                // New File / New Folder (only when right-clicking a folder)
                if (isDir) {
                    JMenuItem newFile = new JMenuItem("New File");
                    newFile.addActionListener(ev -> createNewFile(file));
                    popup.add(newFile);

                    JMenuItem newFolder = new JMenuItem("New Folder");
                    newFolder.addActionListener(ev -> createNewFolder(file));
                    popup.add(newFolder);

                    popup.addSeparator();

                    JMenuItem openExplorer = new JMenuItem("Open Folder...");
                    openExplorer.addActionListener(ev -> openInExplorer(file));
                    popup.add(openExplorer);
                } else {
                    // For files: open the containing folder
                    JMenuItem openContaining = new JMenuItem("Open Containing Folder");
                    openContaining.addActionListener(ev -> openInExplorer(file.getParentFile()));
                    popup.add(openContaining);
                }

                popup.show(tree, e.getX(), e.getY());
            }
        });
    }
    
    private void renameFile(File file) {
        String newName = JOptionPane.showInputDialog(this, "Rename to:", file.getName());
        if (newName == null || newName.isBlank() || newName.equals(file.getName())) return;

        File dest = new File(file.getParentFile(), newName);
        if (dest.exists()) {
            JOptionPane.showMessageDialog(this, "A file with that name already exists.");
            return;
        }
        if (file.renameTo(dest)) {
            refresh();
        } else {
            JOptionPane.showMessageDialog(this, "Could not rename.");
        }
    }

    private void deleteFile(File file) {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Delete \"" + file.getName() + "\"?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        try {
            if (file.isDirectory()) {
                deleteRecursive(file.toPath());
            } else {
                Files.delete(file.toPath());
            }
            refresh();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Could not delete: " + ex.getMessage());
        }
    }

    private void deleteRecursive(Path path) throws IOException {
        if (Files.isDirectory(path)) {
            try (var stream = Files.list(path)) {
                for (Path child : stream.toList()) {
                    deleteRecursive(child);
                }
            }
        }
        Files.delete(path);
    }

    private void createNewFile(File parentDir) {
        String name = JOptionPane.showInputDialog(this, "New file name:");
        if (name == null || name.isBlank()) return;

        File f = new File(parentDir, name);
        try {
            if (f.createNewFile()) {
                refresh();
                gui.openFileFromDrop(f);
            } else {
                JOptionPane.showMessageDialog(this, "File already exists.");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Could not create file.");
        }
    }

    private void createNewFolder(File parentDir) {
        String name = JOptionPane.showInputDialog(this, "New folder name:");
        if (name == null || name.isBlank()) return;

        File f = new File(parentDir, name);
        if (f.mkdir()) {
            refresh();
        } else {
            JOptionPane.showMessageDialog(this, "Could not create folder.");
        }
    }

    private void openInExplorer(File folder) {
        if (folder == null || !folder.isDirectory()) return;
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(folder);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Could not open folder in explorer.");
        }
    }
}