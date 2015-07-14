import javax.swing.*;
import java.io.File;

/**
 * Created by tmarsh on 7/14/15.
 */
public interface FilePanel {
    JPanel build(JFrame root, File workingDirectory);
}
