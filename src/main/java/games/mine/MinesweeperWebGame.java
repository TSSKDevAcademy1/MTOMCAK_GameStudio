package games.mine;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/mines")
public class MinesweeperWebGame extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	private Field field;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		HttpSession httpSession = request.getSession();
		field = (Field) httpSession.getAttribute("field");
		boolean mark;
		out = response.getWriter();
		out.println("<HTML>");
		out.println("<HEAD>");
		out.print("<TITLE> Hello world </TITLE>");
		out.print("</HEAD>");
		out.print("<BODY>");

		int r = -1, c = -1;

		out.print("<a href=\"?nGame\">New Game</a><br>");

		// mark tile
		if (request.getParameter("mark") == null || request.getParameter("mark").equals("true") ) {
			out.print("<a href=\"?mark=false\">Mark tile</a><br>");
			mark = false;
		} else {
			out.print("<a href=\"?mark=true\">Open tile</a><br>");
			mark = true;
		}

		if (field == null || request.getParameter("nGame") != null) {
			field = new Field(5, 5, 5);
			httpSession.setAttribute("field", field);
		}
		try {
			// get clicked position of clicked tile
			if (field.getState().equals(GameState.PLAYING)) {
				r = Integer.parseInt(request.getParameter("row"));
				c = Integer.parseInt(request.getParameter("column"));
			}
		} catch (NumberFormatException e) {
			r = c = -1;
		}
		out.print(field.getRemainingMineCount() + "<p>");
		// open tile
		if (r >= 0 && c >= 0) {
			// open tile
			if (!mark) {
				field.openTile(r, c);
				if (field.getTile(r, c) instanceof Mine) {
					field.setState(GameState.FAILED);
				}
			} else {
				field.markTile(r, c);
			}
		}
		if (field.getState() == GameState.PLAYING) {
			update(mark);
		} else if (field.getState() == GameState.SOLVED) {
			update(mark);
			out.println("<p><b>vyhral si !</b>");
			httpSession.setAttribute("field", null);
			r = c = -1;
		} else if (field.getState() == GameState.FAILED) {
			field.showAllMines();
			update(mark);
			out.println("<p><b>prehral si !</b>");
			httpSession.setAttribute("field", null);
			r = c = -1;
		}
		out.print("</BODY>");
		out.print("</HTML>");
	}

	/**
	 * Print playing field.
	 */
	private void update(boolean mark) {
		String image;
		for (int row = 0; row < field.getRowCount(); row++) {
			for (int column = 0; column < field.getColumnCount(); column++) {
				Tile tile = field.getTile(row, column);
				String tileValue = tile.toString();
				switch (tileValue) {
				case "-":
					image = "closed";
					break;
				case "X":
					image = "mine";
					break;
				case "M":
					image = "marked";
					break;
				default:
					image = "open" + tileValue;
					break;
				}
				
				if (mark){
				out.printf(
						"<a href=\"?row=%d&column=%d&mark=false\"><img src=\"resources\\images\\mine\\%s.png\"></a>",
						row, column, image);}
				else {out.printf(
						"<a href=\"?row=%d&column=%d&mark=true\"><img src=\"resources\\images\\mine\\%s.png\"></a>",
						row, column, image);}
			}
			out.print("<br>");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
}
