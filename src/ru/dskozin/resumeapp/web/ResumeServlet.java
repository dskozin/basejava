package ru.dskozin.resumeapp.web;

import ru.dskozin.resumeapp.Config;
import ru.dskozin.resumeapp.storage.SqlStorage;
import ru.dskozin.resumeapp.storage.Storage;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ResumeServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String uuid, page;
        Storage storage = new SqlStorage(
                Config.getInstance().getDBUrl(),
                Config.getInstance().getDBUser(),
                Config.getInstance().getDBPassword());

        if((uuid = request.getParameter("uuid")) != null){
            request.setAttribute("resume", storage.get(uuid));
            page = "resume.jsp";
        } else {
            request.setAttribute("resumes_list", storage.getAllSorted());
            page = "resume_list.jsp";
        }

        request.getRequestDispatcher(page).forward(request, response);
    }
}
