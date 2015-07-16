import javax.swing.*;
import java.io.File;

public interface FilePanel {
    JPanel build(JFrame root, File workingDirectory);
}
