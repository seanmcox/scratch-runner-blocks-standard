/**
 * 
 */
package com.shtick.utils.scratch.runner.standard;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Set;

import com.shtick.utils.scratch.runner.core.OpcodeSubaction;
import com.shtick.utils.scratch.runner.core.ScriptTupleRunner;
import com.shtick.utils.scratch.runner.core.ValueListener;
import com.shtick.utils.scratch.runner.core.elements.ScriptContext;
import com.shtick.utils.scratch.runner.standard.bundle.Activator;

/**
 * @author sean.cox
 *
 */
public class StandardBlocksExtensions {
	private static String ANSWER;
	private static final LinkedList<ValueListener> ANSWER_LISTENERS = new LinkedList<>();
	private static long TIMER_TARE;
	private static final Font SAY_FONT = new Font("sansserif", Font.BOLD, 15);
	private static final Stroke SAY_STROKE = new BasicStroke(4.0f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER);
	private static final Stroke SAY_STROKE_2 = new BasicStroke(3.0f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER);
	private static final Stroke SAY_STROKE_3 = new BasicStroke(2.0f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER);
	private static final Stroke SAY_STROKE_4 = new BasicStroke(1.0f,BasicStroke.CAP_ROUND,BasicStroke.JOIN_MITER);
	private static final BufferedImage TEST_IMAGE;
	private static final FontMetrics SAY_FONT_METRICS;
	static {
		TEST_IMAGE = new BufferedImage(1, 1, BufferedImage.TRANSLUCENT);
		SAY_FONT_METRICS = TEST_IMAGE.getGraphics().getFontMetrics(SAY_FONT);
	}
	
	/**
	 * 
	 * @param message
	 * @return A Set of ScriptTupleRunners. Modification of the set has no side-effects.
	 */
	public static Set<ScriptTupleRunner> broadcast(String message) {
		return Activator.WHEN_I_RECEIVE.broadcast(message);
		
	}
	
	/**
	 * @param context
	 * @param procName 
	 * @param params 
	 * @param runner 
	 * @return An OpcodeSubaction of type SUBSCRIPT giving the subscript to be run.
	 * 
	 */
	public static OpcodeSubaction call(ScriptContext context, String procName, Object[] params, ScriptTupleRunner runner) {
		return Activator.PROC_DEF.call(context, procName, params, runner);
	}

	/**
	 * @return the answer
	 */
	public static String getAnswer() {
		return ANSWER;
	}
	
	/**
	 * 
	 * @param valueListener
	 */
	public static void addAnswerListener(ValueListener valueListener) {
		synchronized(ANSWER_LISTENERS) {
			ANSWER_LISTENERS.add(valueListener);
		}
	}
	
	/**
	 * 
	 * @param valueListener
	 */
	public static void removeAnswerListener(ValueListener valueListener) {
		synchronized(ANSWER_LISTENERS) {
			ANSWER_LISTENERS.remove(valueListener);
		}
	}

	/**
	 * @param answer the answer to set
	 */
	public static void setAnswer(String answer) {
		if((answer==ANSWER)||((answer!=null)&&(!answer.equals(ANSWER)))) {
			String oldAnswer = ANSWER;
			ANSWER = answer;
			for(ValueListener listener:ANSWER_LISTENERS)
				listener.valueUpdated(oldAnswer, answer);
		}
	}

	/**
	 * 
	 */
	public static void resetTimer() {
		TIMER_TARE = System.currentTimeMillis();
	}
	
	/**
	 * 
	 * @return The number of milliseconds since resetTimer was last called.
	 */
	public static long getElapsedTimeMillis() {
		return System.currentTimeMillis() - TIMER_TARE;
	}
	
	/**
	 * 
	 * @param text The text to show.
	 * @return The talk bubble image.
	 */
	public static Image createTalkBubbleImage(String text) {
		if((text==null)||(text.length()==0))
			return null;
		int fontHeight = SAY_FONT_METRICS.getHeight();
		
		Image textImage = createTextImage(text);
		int imageWidth = textImage.getWidth(null)+fontHeight*2;
		int imageHeight = textImage.getHeight(null)+fontHeight*3;
		int controlDistance = (int)Math.sqrt(fontHeight*fontHeight/6);
		BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TRANSLUCENT);
		GeneralPath path = new GeneralPath();
		path.moveTo(fontHeight, fontHeight/2);
		path.lineTo(imageWidth-fontHeight, fontHeight/2);
		path.quadTo(imageWidth-fontHeight+controlDistance, fontHeight-controlDistance, imageWidth-fontHeight/2, fontHeight);
		path.lineTo(imageWidth-fontHeight/2, imageHeight - fontHeight*2);
		path.quadTo(imageWidth-fontHeight+controlDistance, imageHeight - fontHeight*2+controlDistance, imageWidth-fontHeight, imageHeight - fontHeight*3/2);
		path.lineTo(fontHeight*5/2, imageHeight - fontHeight*3/2);
		path.lineTo(fontHeight/2, imageHeight - fontHeight/2);
		path.lineTo(fontHeight, imageHeight - fontHeight*3/2);
		path.quadTo(fontHeight-controlDistance, imageHeight - fontHeight*2+controlDistance, fontHeight/2, imageHeight - fontHeight*2);
		path.lineTo(fontHeight/2, fontHeight);
		path.quadTo(fontHeight-controlDistance, fontHeight-controlDistance, fontHeight, fontHeight/2);
		path.closePath();
		Graphics2D g2 = (Graphics2D)image.getGraphics();
		g2.setColor(Color.WHITE);
		g2.fill(path);
		g2.setColor(Color.GRAY);
		g2.setStroke(SAY_STROKE);
		g2.draw(path);
		g2.drawImage(textImage, fontHeight, fontHeight, null);
		return image;
	}
	
	/**
	 * 
	 * @param text The text to show.
	 * @return The talk bubble image.
	 */
	public static Image createThoughtBubbleImage(String text) {
		if((text==null)||(text.length()==0))
			return null;
		int fontHeight = SAY_FONT_METRICS.getHeight();
		
		Image textImage = createTextImage(text);
		int imageWidth = textImage.getWidth(null)+fontHeight*2;
		int imageHeight = textImage.getHeight(null)+fontHeight*3;
		BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TRANSLUCENT);
		Shape mainBubble;
		Shape bubble2;
		Shape bubble3;
		Shape bubble4;
		{
			mainBubble = new RoundRectangle2D.Float(fontHeight/2,fontHeight/2,textImage.getWidth(null)+fontHeight,textImage.getHeight(null)+fontHeight,fontHeight,fontHeight);
			bubble2 = new Ellipse2D.Float(fontHeight+3*fontHeight/4,imageHeight - fontHeight-fontHeight/3,fontHeight,3*fontHeight/5);
			bubble3 = new Ellipse2D.Float(fontHeight+fontHeight/2,imageHeight - fontHeight/2-fontHeight/3,2*fontHeight/3,2*fontHeight/5);
			bubble4 = new Ellipse2D.Float(fontHeight,imageHeight - fontHeight/2,fontHeight/2,fontHeight/4);
		}
		
		Graphics2D g2 = (Graphics2D)image.getGraphics();
		g2.setColor(Color.WHITE);
		g2.fill(mainBubble);
		g2.fill(bubble2);
		g2.fill(bubble3);
		g2.fill(bubble4);
		g2.setColor(Color.GRAY);
		g2.setStroke(SAY_STROKE);
		g2.draw(mainBubble);
		g2.setStroke(SAY_STROKE_2);
		g2.draw(bubble2);
		g2.setStroke(SAY_STROKE_3);
		g2.draw(bubble3);
		g2.setStroke(SAY_STROKE_4);
		g2.draw(bubble4);
		g2.drawImage(textImage, fontHeight, fontHeight, null);
		return image;
	}
	
	/**
	 * 
	 * @param text
	 * @return An image containing the text layed out on a transparent background.
	 */
	public static Image createTextImage(String text) {
		if((text==null)||(text.length()==0))
			return null;
		String[] words = text.split("\\s+");
		int minWidth = 0;
		for(String word:words)
			minWidth = Math.max(minWidth, SAY_FONT_METRICS.stringWidth(word));
		int fontHeight = SAY_FONT_METRICS.getHeight();
		int fontAscent = SAY_FONT_METRICS.getAscent();
		int lineWidth = SAY_FONT_METRICS.stringWidth(text);
		int requiredAreaInSquareFontHeights = (int)Math.ceil(lineWidth/(double)fontHeight);
		int unitsOf4x3Ratio = (int)Math.ceil(Math.sqrt(requiredAreaInSquareFontHeights/(3.0*4)));
		int estimatedWidth = unitsOf4x3Ratio * 4 * fontHeight;
		if(estimatedWidth<minWidth)
			estimatedWidth = (int)Math.ceil(minWidth/(double)fontHeight);
		String textRemaining = text;
		String normalizedTextRemaining = textRemaining.replaceAll("\\s", " ");
		LinkedList<String> lines = new LinkedList<>();
		int index;
		while(textRemaining.length()>0) {
			int currentLineLength = 0;
			index = 0;
			while(index<textRemaining.length()) {
				index = normalizedTextRemaining.indexOf(' ', currentLineLength+1);
				if(index<0) {
					int tempLineWidth = SAY_FONT_METRICS.stringWidth(textRemaining);
					if(tempLineWidth<estimatedWidth) {
						currentLineLength = textRemaining.length();
					}
					break;
				}
				int tempLineWidth = SAY_FONT_METRICS.stringWidth(textRemaining.substring(0, index));
				if(tempLineWidth>=estimatedWidth)
					break;
				currentLineLength = index;
			}
			lines.add(textRemaining.substring(0, currentLineLength));
			textRemaining = textRemaining.substring(currentLineLength).replaceAll("^\\s+","");
			normalizedTextRemaining = normalizedTextRemaining.substring(currentLineLength).replaceAll("^\\s+","");
		}
		
		int imageWidth = estimatedWidth;
		int imageHeight = lines.size()*fontHeight;
		BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TRANSLUCENT);
		Graphics2D g2 = (Graphics2D)image.getGraphics();
		g2.setFont(SAY_FONT);
		g2.setColor(Color.BLACK);
		int i=0;
		for(String line:lines) {
			g2.drawString(line, 0, i*fontHeight+fontAscent);
			i++;
		}

		return image;
	}
	
	/**
	 * 
	 * @param e 
	 * @return The Scratch key ID for the key that generated the given event.
	 */
	public static String getKeyIdForEvent(KeyEvent e) {
		String keyID;
		if(((e.getKeyChar()>='a')&&(e.getKeyChar()<='z'))||((e.getKeyChar()>='A')&&(e.getKeyChar()<='Z'))||((e.getKeyChar()>='0')&&(e.getKeyChar()<='9'))){
			keyID = ""+e.getKeyChar();
		}
		else {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_SPACE:
				keyID = "space";
				break;
			case KeyEvent.VK_UP:
				keyID = "up arrow";
				break;
			case KeyEvent.VK_DOWN:
				keyID = "down arrow";
				break;
			case KeyEvent.VK_LEFT:
				keyID = "left arrow";
				break;
			case KeyEvent.VK_RIGHT:
				keyID = "right arrow";
				break;
			case KeyEvent.VK_ENTER:
				keyID = "enter";
				break;
			default:
				keyID = null;
			}
		}
		return keyID;
	}
}
