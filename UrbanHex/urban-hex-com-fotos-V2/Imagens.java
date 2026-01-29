import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.text.Normalizer;
import java.util.Locale;

/**
 * Subclasse de Room que tenta mostrar uma imagem correspondente à sala.
 * A imagem deve estar em fotos/<nome_sanitizado>.(png|jpg|jpeg|gif)
 * A exibição é feita em uma janela Swing por alguns segundos (assincronamente).
 */
public class Imagens extends Room {
    private final String imageBaseName;

    public Imagens(String description) {
        super(description);
        this.imageBaseName = sanitizeFileName(description);
    }

    /**
     * Mostra a imagem correspondente por displayMillis milissegundos (assincronamente).
     * Se não houver imagem, não faz nada.
     */
    public void showImage(long displayMillis) {
        String[] exts = {".png", ".jpg", ".jpeg", ".gif"};
        File imageFile = null;
        String basePath = "fotos" + File.separator + imageBaseName;
        for (String ext : exts) {
            File f = new File(basePath + ext);
            if (f.exists() && f.isFile()) {
                imageFile = f;
                break;
            }
        }
        if (imageFile == null) {
            // nenhuma imagem encontrada; nada a fazer
            return;
        }

        final File toShow = imageFile;
        // mostra em thread separada para não bloquear o jogo
        new Thread(() -> {
            try {
                SwingUtilities.invokeAndWait(() -> {
                    // usar JWindow para uma janela sem decorações
                    final JWindow window = new JWindow();
                    ImageIcon icon = new ImageIcon(toShow.getAbsolutePath());

                    JLabel label = new JLabel(icon);
                    label.setOpaque(true);
                    window.getContentPane().add(label);
                    window.pack();

                    // centraliza na tela
                    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
                    int x = (screen.width - window.getWidth()) / 2;
                    int y = (screen.height - window.getHeight()) / 2;
                    window.setLocation(x, y);

                    window.setVisible(true);

                    // descarte após displayMillis usando Swing Timer
                    new javax.swing.Timer((int) displayMillis, e -> {
                        window.setVisible(false);
                        window.dispose();
                    }) {{
                        setRepeats(false);
                        start();
                    }};
                });
            } catch (Exception e) {
                // falha em renderizar UI; falha silenciosa com log
                System.err.println("Erro ao exibir imagem: " + e.getMessage());
            }
        }, "imagens-show-thread").start();
    }

    private String sanitizeFileName(String s) {
        if (s == null) return "";
        // remove acentos
        String normalized = Normalizer.normalize(s, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        // to lower, replace non-alphanumeric by underscore
        String cleaned = normalized.toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9]+", "_")
                .replaceAll("^_+|_+$", ""); // remove underscores nas pontas
        if (cleaned.isEmpty()) cleaned = "imagem";
        return cleaned;
    }
}