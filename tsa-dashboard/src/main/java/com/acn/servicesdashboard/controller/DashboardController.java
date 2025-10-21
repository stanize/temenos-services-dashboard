package com.acn.servicesdashboard.controller;

import com.acn.servicesdashboard.service.TsmService;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet("/dashboard")
public class DashboardController extends HttpServlet {

    private final TsmService tsmService = new TsmService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Use TsmService instead of hardcoded values
        req.setAttribute("jbossStatus", "Pending Logic Implementation");
        req.setAttribute("tsmStatus", tsmService.getTsmStatus());

        RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String action = req.getParameter("action");

        if ("restart".equals(action)) {
            String result = tsmService.restartTsm();
            req.setAttribute("message", result);
        }

        doGet(req, resp); // reload page after action
    }
}
