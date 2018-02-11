package ru.dskozin.resumeapp.web;

import ru.dskozin.resumeapp.Config;
import ru.dskozin.resumeapp.model.*;
import ru.dskozin.resumeapp.storage.SqlStorage;
import ru.dskozin.resumeapp.storage.Storage;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ResumeServlet extends javax.servlet.http.HttpServlet {

    private Storage storage;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        storage = new SqlStorage(
                Config.getInstance().getDBUrl(),
                Config.getInstance().getDBUser(),
                Config.getInstance().getDBPassword());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid"),
                fullName = request.getParameter("fullName");

        if (uuid == null) {
            response.sendRedirect("resume");
            return;
        }

        Resume resume = storage.get(uuid);
        resume.setFullName(fullName);

        for (ContactType type : ContactType.values()){
            String value = request.getParameter(type.name());
            if(value != null && value.trim().length() != 0) {
                resume.addContact(type, value);
                continue;
            }

            resume.getContacts().remove(type);
        }

        for(SectionType section : SectionType.values()){
            String value = request.getParameter(section.name());
            if(value != null && value.trim().length() !=0){
                switch (section.name()){
                    case "PERSONAL":
                    case "OBJECTIVE":
                        resume.addSection(section, new SectionString(value));
                        break;
                    case "ACHIEVEMENT":
                    case "QUALIFICATION":
                        resume.addSection(section, new SectionList(value.split("\n")));
                        break;
                }
            }
        }

        storage.update(resume);
        response.sendRedirect("resume");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String action = request.getParameter("action"),
                uuid = request.getParameter("uuid"),
                page;
        Resume resume;

        if(action == null || uuid == null){
            request.setAttribute("resumes_list", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/resume_list.jsp").forward(request, response);
            return;
        }

        switch (action){
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
                resume = storage.get(uuid);
                page = "view.jsp";
                break;
            case "edit":
                resume = storage.get(uuid);
                page = "edit.jsp";
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }

        request.setAttribute("resume", resume);
        request.getRequestDispatcher("/WEB-INF/jsp/" + page).forward(request, response);
    }
}
