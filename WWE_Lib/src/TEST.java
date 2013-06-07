import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import fhv.eclipse2013.wwe.control.ScopeControl;
import fhv.eclipse2013.wwe.impl.scope.SimulationScope;

public class TEST {

	public static void main(String[] args) {
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		new TEST();
	}

	JFrame w;
	SimulationScope scope;
	Timer timer = new Timer();
	Boolean paused = false;

	public TEST() {
		// HAHA 2
		this.w = new JFrame();
		this.w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.w.setLayout(new BorderLayout());

		this.scope = null;
		/*
		 * if (new File("d:\\text.xml").exists()) { this.scope =
		 * SimulationScope.load("d:\\text.xml"); } else { this.scope = new
		 * SimulationScope(100, 100); }
		 */
		int x = 100;
		this.scope = new SimulationScope(x, x);
		JScrollPane pane = new JScrollPane(new ScopeControl(this.scope),
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.w.add(pane, BorderLayout.CENTER);

		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1, 4));

		JButton backBtn = new JButton("Back");
		backBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				TEST.this.scope.backStep();
			}
		});
		bottomPanel.add(backBtn);

		JButton playBtn = new JButton("Play");
		playBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				TEST.this.scope.start();
			}
		});
		bottomPanel.add(playBtn);

		JButton nextBtn = new JButton("Next");
		nextBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				TEST.this.scope.nextStep();
			}
		});
		bottomPanel.add(nextBtn);

		JButton stopBtn = new JButton("Stopp");
		stopBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TEST.this.scope.stop();
			}
		});
		bottomPanel.add(stopBtn);

		JButton saveBtn = new JButton("Save");
		saveBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TEST.this.scope.save("d:\\text.xml");
			}
		});
		bottomPanel.add(saveBtn);

		this.w.add(bottomPanel, BorderLayout.SOUTH);

		this.w.setSize(300, 300);
		this.w.setLocation(500, 100);

		this.w.setVisible(true);
	}
}
