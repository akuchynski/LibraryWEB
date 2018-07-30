package by.htp.library.servlets.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class UploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String SAVE_DIR = "assets/images/users";

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String appPath = request.getServletContext().getRealPath("");
		String savePath = appPath + SAVE_DIR + File.separator;

		HttpSession session = request.getSession();
		int id = (Integer) session.getAttribute("editId");

		for (Part part : request.getParts()) {
			String filePath = extractFileName(part);
			String fileName = new File(filePath).getName();
			String fileType = getFileExtension(fileName);
			if (fileType.equals("jpg") == false) {
				session.setAttribute("messageClass", "upload-file-error");
				break;
			}
			part.write(savePath + "avatar" + id + "." + fileType);
			int currentId = (Integer) session.getAttribute("currentId");
			request.getSession().setAttribute("avatarNumber", currentId);
			session.setAttribute("messageClass", "upload-file-success");
		}
		response.sendRedirect("user-edit?num=" + id);
	}

	private String extractFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				return s.substring(s.indexOf("=") + 2, s.length() - 1);
			}
		}
		return "";
	}

	private String getFileExtension(String name) {
		try {
			return name.substring(name.lastIndexOf(".") + 1);
		} catch (Exception e) {
			return "";
		}
	}
}