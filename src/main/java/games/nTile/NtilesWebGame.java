package games.nTile;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/nTiles")
public class NtilesWebGame extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PrintWriter out;
	private PlayingField fieldNtiles;
	// position of clicked tile
	int r, c;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		HttpSession httpSession = request.getSession();
		fieldNtiles = (PlayingField) httpSession.getAttribute("fieldNtiles");
		out = response.getWriter();

		out.println("<HTML>");
		out.println("<HEAD>");
		out.print("<TITLE>15 puzzle</TITLE>");
		out.print("</HEAD>");
		out.print("<BODY>");
		out.print("<a href=\"?nGame\">Scramble</a><br>");
		// new game
		if (fieldNtiles == null || request.getParameter("nGame") != null) {
			fieldNtiles = new PlayingField(4, 4);
			httpSession.setAttribute("fieldNtiles", fieldNtiles);
		}

		try {
			r = Integer.parseInt(request.getParameter("row"));
			c = Integer.parseInt(request.getParameter("column"));
		} catch (NumberFormatException e) {
			r = c = -1;
		}

		if (!fieldNtiles.isSolved()) {
			if (r >= 0 && c >= 0) {
				fieldNtiles.Move(r, c);
			}
		} else {
			out.print("<h1> You win !!!</h1>");
		}
		update(r, c);
		out.print("</BODY>");
		out.print("</HTML>");
	}

	private void update(int r, int c) {
		int emptyX = fieldNtiles.emptyPos.getX();
		int emptyY = fieldNtiles.emptyPos.getY();

		for (int row = 0; row < fieldNtiles.getRowCount(); row++) {
			for (int column = 0; column < fieldNtiles.getColumnCount(); column++) {
				Cell cell = fieldNtiles.getCell(row, column);
				String tileValue = cell.toString();
				// tiles around the empty position
				if (row <= emptyX + 1 && row >= emptyX - 1
						&& column <= emptyY + 1 && column >= emptyY - 1) {
					if ((column == emptyY && (row == emptyX - 1 || row == emptyX + 1))
							|| (row == emptyX && (column == emptyY - 1 || column == emptyY + 1)))
						out.printf(
								"<a href=\"?row=%d&column=%d\"><img src=\"resources\\images\\nTiles\\%s.jpg\" border=\"2\" height=\"64\" width=\"64\"></a>",
								row, column, "puzzle" + tileValue);
					else
						out.printf(
								"<img src=\"resources\\images\\nTiles\\%s.jpg\" border=\"2\" height=\"64\" width=\"64\"></a>",
								"puzzle" + tileValue);
				}
				// other tiles
				else {
					out.printf(
							"<img src=\"resources\\images\\nTiles\\%s.jpg\" border=\"2\" height=\"64\" width=\"64\"></a>",
							"puzzle" + tileValue);
				}
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
