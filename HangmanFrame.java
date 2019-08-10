import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class HangmanFrame extends JFrame implements KeyListener
{
	// Strings
	private String[] words = {"computer", "monitor", "keyboard", "mouse", "speaker", "printer"};
	private String word;
	
	// Letters Used
	private ArrayList <String> correctLettersUsed;
	private ArrayList <String> incorrectLettersUsed;
	
	// JComponents
	private JPanel panel;
	private JLabel label;
	private JLabel incorrectLetters;
	private JButton revealWord;
	
	// Picks a random word from the words array
	private String randomWord()
	{
		int i = (int) (Math.random() * (this.words.length));
		
		return this.words[i];
	}
	
	// Displays the dashes for unrevealed letters
	private String dashes()
	{
		String a = "";
		
		for (int i = 0; i < this.word.length(); i++)
		{
			a += " - ";
		}
		
		return a;
	}
	
	// Decides which letters are revealed
	private String revealedLetters()
	{
		String a = "";
		
		for (int i = 0; i < this.word.length(); i++)
		{
			if (this.correctLettersUsed.contains(this.word.substring(i, i + 1)))
			{
				a += " " + this.correctLettersUsed.get(this.correctLettersUsed.indexOf(this.word.substring(i, i + 1))) + " ";
			}
			
			else
			{
				a += " - ";
			}
		}
		
		return a;
	}
	
	// Adds incorrect letters used to an ArrayList
	public String incorrectText()
	{
		String a = " ";
		
		for (int i = 0; i < this.incorrectLettersUsed.size(); i++)
		{
			a += this.incorrectLettersUsed.get(i) + ", ";
		}
		
		return a;
	}
	
	// Resets the game
	public void reset()
	{
		this.word = randomWord();
		this.incorrectLettersUsed.clear();
		this.correctLettersUsed.clear();
		this.label.setText(dashes());
		this.incorrectLetters.setText(incorrectText());
	}
	
	// When its too much
	public void revealWord()
	{
		this.label.setText(word);
		JOptionPane.showMessageDialog(HangmanFrame.getFrames()[0], "The word was " + word);
		this.reset();
	}
	
	@Override
	public void keyTyped(KeyEvent e)
	{

	}

	// Key Pressed Event
	@Override
	public void keyPressed(KeyEvent e)
	{
		String letter = e.getKeyChar() + "";
		boolean isInWord = false;
		
		for (int i = 0; i < word.length(); i++)
		{
			if (this.word.substring(i, i + 1).equals(letter))
			{
				isInWord = true;
				this.correctLettersUsed.add(letter);
			}
		}
		
		if (!isInWord)
		{
			this.incorrectLettersUsed.add(letter);
			
		}
		
		this.label.setText(revealedLetters());
		this.incorrectLetters.setText(incorrectText() + "   " + (6 - this.incorrectLettersUsed.size()) + " tries remaining");
		
		if (incorrectLettersUsed.size() > 5)
		{
			JOptionPane.showMessageDialog(this, "You lose! The word was " + word + "!");
			this.reset();
		}
		
		if (!label.getText().contains("-"))
		{
			JOptionPane.showMessageDialog(this, "You win! The word was " + word + "!");
			this.reset();
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{

	}
	
	public HangmanFrame()
	{
		// Initialize
		this.word = randomWord();
		this.correctLettersUsed = new ArrayList <String> ();
		this.incorrectLettersUsed = new ArrayList <String> ();
		this.panel = new JPanel();
		this.label = new JLabel(dashes());
		this.incorrectLetters = new JLabel(incorrectText() + "   " + (6 - incorrectLettersUsed.size()) + " tries remaining | Press a letter");
		this.revealWord = new JButton("Give Up");
		
		// Frame Settings
		this.setTitle("Hangman");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setSize(250, 135);
		this.addKeyListener(this);
		
		// Reveal Word On Click
		revealWord.addActionListener(
			new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					revealWord();
				}
			}
		);
		
		// Panel and Labels Settings
		this.panel.add(label);
		this.incorrectLetters.setPreferredSize(new Dimension(200, 25));
		this.panel.add(incorrectLetters);
		this.panel.add(revealWord);
		this.setContentPane(panel);
	}
}
