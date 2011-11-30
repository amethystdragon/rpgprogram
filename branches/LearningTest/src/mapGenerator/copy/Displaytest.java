package mapGenerator.copy;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Displaytest extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -958658925145518960L;

	private int x_offset;

	private int y_offset;

	private int grid_size;

	private JFrame container;

	private DragHandler handle;

	private List<Line> walls;

	private Point nearestPoint;

	public static void main(String[] args) {

		JFrame test = new JFrame();

		test.add(new Displaytest(test));

		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		test.setPreferredSize(new Dimension(1000, 1000));

		test.pack();

		test.setVisible(true);

	}

	public Displaytest(JFrame container) {

		nearestPoint = null;

		walls = new ArrayList<Line>();

		x_offset = 0;

		y_offset = 0;

		grid_size = 10;

		this.container = container;

		handle = new DragHandler();

		this.addMouseListener(handle);

		this.addMouseMotionListener(handle);

		this.addMouseWheelListener(handle);

		this.setSize(this.container.getWidth(), this.container.getHeight());

	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		this.setSize(this.container.getWidth(), this.container.getHeight());

		g.setColor(Color.BLACK);

		for (int a = -50; a <= 50; a++) {

			for (int b = -50; b <= 50; b++) {

				g.fillOval(a * grid_size - x_offset, b * grid_size - y_offset,
						2, 2);

			}

		}

		if (nearestPoint != null) {

			g.drawRect(
					toGrid((int) (nearestPoint.getX()),
							true) - 3,
					toGrid((int) (nearestPoint.getY()),
							false) - 3, 6, 6);
			
			g.drawString(nearestPoint.getX() + " " + nearestPoint.getY(), 5, 25);

		}

		for (Line l : walls) {

			l.paint(g);

		}

	}

	public int toGrid(int value, boolean x) {

		return value * grid_size - (x ? x_offset : y_offset);

	}

	private class DragHandler implements MouseListener, MouseMotionListener,
			MouseWheelListener {

		private int x_loc;

		private int y_loc;

		private Point line_start;

		private Point line_end;

		private boolean panning;

		public DragHandler() {

			this.x_loc = 0;

			this.y_loc = 0;

			line_start = null;

			line_end = null;

			panning = false;

		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent arg0) {

			if (arg0.getButton() == MouseEvent.BUTTON3) {

				this.x_loc = arg0.getX();

				this.y_loc = arg0.getY();

				panning = true;

			} else if (arg0.getButton() == MouseEvent.BUTTON1) {

				int x_pos = (int) (((arg0.getX() + x_offset) / (double) (grid_size)) + (0.5 * ( Math.abs(arg0.getX()) / arg0.getX())));
				int y_pos = (int) (((arg0.getY() + y_offset) / (double) (grid_size)) + 0.5);

				if (line_start == null) {

					line_start = new Point(x_pos, y_pos);

				} else {

					line_end = new Point(x_pos, y_pos);

					walls.add(new Line(line_start, line_end));

					line_start = null;

					line_end = null;

					repaint();

				}

			}

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {

			panning = false;

		}

		@Override
		public void mouseDragged(MouseEvent arg0) {

			if (panning) {

				x_offset = x_offset - (arg0.getX() - x_loc);

				y_offset = y_offset - (arg0.getY() - y_loc);

				x_loc = arg0.getX();

				y_loc = arg0.getY();

				repaint();

			}

		}

		@Override
		public void mouseMoved(MouseEvent arg0) {

			int x_pos = (int) (((arg0.getX() + x_offset) / (double) (grid_size)) + 0.5);
			int y_pos = (int) (((arg0.getY() + y_offset) / (double) (grid_size)) + 0.5);
			
			nearestPoint = new Point( x_pos, y_pos);
			
			repaint();

		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent arg0) {
			//Checks to see if the grid size is 0 and if the 
			//scroll is trying to scroll out
			if(grid_size == 0 && !(arg0.getWheelRotation() < 0)) return;  
				//Shrinks the grid by the rotation
				grid_size = grid_size - arg0.getWheelRotation(); 
			repaint(); //Repaints the window
		}
	}

	private class Line {

		private Point start;

		private Point end;

		public Line(Point start, Point end) {

			this.start = start;

			this.end = end;

		}

		public void paint(Graphics g) {

			g.drawLine(toGrid((int) start.getX(), true),
					toGrid((int) start.getY(), false),
					toGrid((int) end.getX(), true),
					toGrid((int) end.getY(), false));

		}

	}
}
