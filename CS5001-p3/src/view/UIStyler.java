package view;

import javax.swing.*;
import java.awt.*;

/**
 * Utility methods to apply consistent styling to UI components.
 */
public class UIStyler {

    /**
     * General button style (yellow + purple theme).
     *
     * @param button button to style
     */
    public static void styleButton(JButton button) {

        // Local color variables
        Color bgColor = Color.YELLOW;
        Color fgColor = new Color(128, 0, 128);  // Purple
        Color borderColor = fgColor;

        button.setFocusPainted(false);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));

        button.setBorder(BorderFactory.createLineBorder(borderColor, 2));

        button.setMargin(new Insets(14, 28, 14, 28)); 
    }

    /**
     * Reset button (red theme).
     *
     * @param button button to style
     */
    public static void styleResetButton(JButton button) {

        // Local colors
        Color bgColor = new Color(255, 180, 180);
        Color fgColor = new Color(178, 34, 34);  // Firebrick
        Color borderColor = fgColor;

        button.setFocusPainted(false);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));

        button.setBorder(BorderFactory.createLineBorder(borderColor, 2));

        // Extra padding
        button.setMargin(new Insets(14, 28, 14, 28));
    }

    /**
     * Undo button (blue theme).
     *
     * @param button button to style
     */
    public static void styleUndoButton(JButton button) {

        // Local colors
        Color bgColor = new Color(200, 220, 255);
        Color fgColor = new Color(70, 130, 180);  // Steel blue
        Color borderColor = fgColor;

        button.setFocusPainted(false);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));

        button.setBorder(BorderFactory.createLineBorder(borderColor, 2));
        button.setMargin(new Insets(14, 28, 14, 28));
    }

    /**
     * Redo button (green theme).
     *
     * @param button button to style
     */
    public static void styleRedoButton(JButton button) {

        // Local colors
        Color bgColor = new Color(210, 255, 210);
        Color fgColor = new Color(34, 139, 34); // Forest green
        Color borderColor = fgColor;

        button.setFocusPainted(false);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));

        button.setBorder(BorderFactory.createLineBorder(borderColor, 2));
        button.setMargin(new Insets(14, 28, 14, 28));
    }

    /**
     * Style for the panel containing the buttons.
     *
     * @param panel panel to style
     */
    public static void stylePanel(JPanel panel) {

        // Local colors
        Color panelBg = new Color(245, 245, 245);

        panel.setBackground(panelBg);

        // Much larger padding around the group of buttons
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
    }
}
