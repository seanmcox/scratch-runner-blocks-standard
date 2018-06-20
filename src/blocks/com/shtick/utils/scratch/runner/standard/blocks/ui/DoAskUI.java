/**
 * 
 */
package com.shtick.utils.scratch.runner.standard.blocks.ui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.DefaultButtonModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author sean.cox
 *
 */
public class DoAskUI extends JPanel{
	/**
	 * 
	 */
	public static final Font FONT_NORMAL = new Font("sansserif", Font.BOLD, 11);
	/**
	 * 
	 */
	public static final Font FONT_LARGE = new Font("sansserif", Font.BOLD, 14);

	private static final Stroke NORMAL_STROKE = new BasicStroke(1.0f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER);
	private static final Stroke PANEL_STROKE = new BasicStroke(4.0f,BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER);
	private static final Color COLOR_BACKGROUND = Color.WHITE;
	private static final Color COLOR_BORDER = new Color(74, 173, 222);
	private static final int VERTICAL_PAD = 2;
	private static final int HORIZONTAL_PAD = 5;

	private static FontMetrics FONT_METRICS_NORMAL;
	private static FontMetrics FONT_METRICS_LARGE;
	
	private JTextField textField = new JTextField();
	// TODO Stretch button image for unusual stage sizes.
	private DoAskButton button = new DoAskButton();
	
	private int labelWidth;
	private int fontHeight;

	/**
	 * @param queryText 
	 * 
	 */
	public DoAskUI(String queryText) {
		setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
		labelWidth = getFontMetricsNormal().stringWidth(queryText);
		fontHeight = getFontMetricsNormal().getHeight();
		JLabel label = new JLabel("  "+queryText);
		label.setFont(FONT_NORMAL);
		label.setForeground(Color.BLACK);
		add(label, BorderLayout.NORTH);
		add(textField,BorderLayout.CENTER);
		add(button,BorderLayout.EAST);
		textField.setBackground(new Color(242,242,242));
		textField.setBorder(BorderFactory.createMatteBorder(3, 3, 0, 0, Color.WHITE));
		setDoubleBuffered(false);

		setVisible(true);
		Dimension preferredSize = getPreferredSize();
		doLayout();
	}

	/* (non-Javadoc)
	 * @see java.awt.Component#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		Stroke defaultStroke = g2.getStroke();
		Shape panelShape = new RoundRectangle2D.Float(1, 1, getWidth()-2, getHeight()-2, 10, 10);
		g2.setColor(COLOR_BACKGROUND);
		g2.fill(panelShape);
		g2.setColor(COLOR_BORDER);
		g2.setStroke(PANEL_STROKE);
		g2.draw(panelShape);
		paintChildren(g);
		
		Rectangle rectangle = textField.getBounds();
		g2.setStroke(defaultStroke);
		g2.setColor(new Color(248,248,248));
		g2.drawLine(rectangle.x+1, rectangle.y+1, rectangle.x+1, rectangle.y+rectangle.height-2);
		g2.drawLine(rectangle.x+1, rectangle.y+1, rectangle.x+rectangle.width-2, rectangle.y+1);
		g2.setColor(new Color(194,194,194));
		g2.drawLine(rectangle.x+2, rectangle.y+2, rectangle.x+2, rectangle.y+rectangle.height-2);
		g2.drawLine(rectangle.x+2, rectangle.y+2, rectangle.x+rectangle.width-2, rectangle.y+2);
		g2.drawLine(rectangle.x+3, rectangle.y+3, rectangle.x+3, rectangle.y+rectangle.height-2);
		g2.drawLine(rectangle.x+3, rectangle.y+3, rectangle.x+rectangle.width-2, rectangle.y+3);
	}
	
	/**
	 * @return the FontMetrics for the FONT_NORMAL
	 */
	public FontMetrics getFontMetricsNormal() {
		synchronized(FONT_NORMAL) {
			if(FONT_METRICS_NORMAL == null)
				FONT_METRICS_NORMAL = this.getFontMetrics(FONT_NORMAL);
			return FONT_METRICS_NORMAL;
		}
	}
	
	/**
	 * @return the FontMetrics for the FONT_LARGE
	 */
	public FontMetrics getFontMetricsLarge() {
		synchronized(FONT_LARGE) {
			if(FONT_METRICS_LARGE == null)
				FONT_METRICS_LARGE = this.getFontMetrics(FONT_LARGE);
			return FONT_METRICS_LARGE;
		}
	}

	/**
	 * 
	 * @param actionListener
	 */
	public void addActionListener(ActionListener actionListener) {
		textField.addActionListener(actionListener);
		button.addActionListener(actionListener);
	}

	/**
	 * 
	 * @param actionListener
	 */
	public void removeActionListener(ActionListener actionListener) {
		textField.removeActionListener(actionListener);
		button.removeActionListener(actionListener);
	}
	
	/**
	 * 
	 * @return The text represented by the text field.
	 */
	public String getText() {
		return textField.getText();
	}

	/* (non-Javadoc)
	 * @see javax.swing.JComponent#reshape(int, int, int, int)
	 */
	@Override
	public void reshape(int x, int y, int w, int h) {
		super.reshape(x, y, w, h);
		doLayout();
	}
	
	private class DoAskButton extends AbstractButton{
		private ImageIcon image = new ImageIcon(getClass().getResource("/com/shtick/utils/scratch/runner/standard/blocks/ui/DoAskButton.png"));
		
		public DoAskButton() {
			setModel(new DefaultButtonModel());
			addMouseListener(new MouseListener() {
				@Override
				public void mouseReleased(MouseEvent e) {}
				
				@Override
				public void mousePressed(MouseEvent e) {}
				
				@Override
				public void mouseExited(MouseEvent e) {}
				
				@Override
				public void mouseEntered(MouseEvent e) {}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount()==1) {
						doClick();
					}
				}
			});
		}

		/* (non-Javadoc)
		 * @see javax.swing.AbstractButton#getIcon()
		 */
		@Override
		public Icon getIcon() {
			return image;
		}

		/* (non-Javadoc)
		 * @see javax.swing.JComponent#getPreferredSize()
		 */
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(image.getIconWidth()/2, image.getIconHeight()/2);
		}

		/* (non-Javadoc)
		 * @see javax.swing.JComponent#paint(java.awt.Graphics)
		 */
		@Override
		public void paint(Graphics g) {
			Graphics2D g2 = (Graphics2D)g;
			g2.scale(0.5, 0.5);
			g2.drawImage(image.getImage(), 0, 0, null);
		}
	}
}
