package caredog;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import static caredog.ColorPanel.*;

public class Text {

        public Text() throws IOException, FontFormatException {
            URL fontUrl = new URL("https://drive.google.com/uc?export=download&id=0B9ZK6IxThvNFajB2MTNtSmVTRVk");
            block_font= Font.createFont(Font.TRUETYPE_FONT,fontUrl.openStream());
            block_font=block_font.deriveFont(Font.PLAIN,20);
            block_fontB=block_font.deriveFont(Font.PLAIN,22);
            GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(block_font);
        }
}
