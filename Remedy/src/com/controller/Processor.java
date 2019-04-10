package com.controller;

import com.bean.*;
import com.service.TicketCreateservice;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "/Processor", urlPatterns = "/Processor")
public class Processor extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TicketCreateservice tcs= new TicketCreateservice();
        User user = null;
        UserDetails ud = null;
        String target = "";

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        HttpSession hsn = request.getSession();
        String action = request.getParameter("action");


        if(action.equals("Login")){
            String uname = request.getParameter("uname");
            String pwd = request.getParameter("pword");
            user = new User(uname, pwd);
            String username = null;
            try{
                username = tcs.validateUser(user);
                System.out.println(username);
                if(username!=null){
                    hsn.setAttribute("uname", username);
                    target = "Mailbox.jsp";
                }
                else{
                    target = "wrongPwd.jsp";
                }
            }
            catch(Exception ex){
                System.out.println("Error: " + ex.getMessage());
                target = "wrongPwd.jsp";
            }
        }


        if(action.equals("Submit"))
        {
            String name=request.getParameter("Name");
            String gender=request.getParameter("gender");
            int age= Integer.parseInt(request.getParameter("age"));
            String allergy=request.getParameter("alergy");
            String bloodgroup=request.getParameter("bloodgroup");
            String treatment=request.getParameter("treatment");
            String email=request.getParameter("email");
            long contact=Long.parseLong(request.getParameter("contact"));
            String description=request.getParameter("description");

            ud = new UserDetails(age, contact, name, gender, email, allergy, treatment, bloodgroup, description);


            try {
                boolean flag = tcs.addUserDetails(ud);
                if(flag){
                    target = "userMessage.jsp";
                }
                else{
                    target = "notUserMessage.jsp";
                }
            }
            catch(Exception e)
            {
                System.out.println("Error : " + e.getMessage());
            }
        }


        if(action.equals("Connect"))
        {
            target = "connectremedy.jsp";
        }


        if(action.equals("Search")){
            int id = Integer.parseInt(request.getParameter("SearchBox"));
            try{
                Status st = tcs.getStatus(id);
                hsn.setAttribute("status", st);
                target = "SearchToken.jsp";
            }
            catch(Exception ex){
                out.println("Error : " + ex.getMessage());
            }
        }

        if(action.equals("Change Password"))
        {
            ChangePassword pb = null;
            String name = request.getParameter("name");
            String Oldpwd = request.getParameter("OldPassword");
            String Newpwd = request.getParameter("conpassword");

            System.out.println(name);

            pb = new ChangePassword(name, Oldpwd, Newpwd);

            try {
                boolean status = tcs.changePassword(pb);
                if(status)
                {
                    target = "passwordMessage.jsp";
                }
                else{
                    target = "notPasswordMessage.jsp";

                }
            }
            catch (Exception e) {
                System.out.println("Error : " + e.getMessage());
                target = "notPasswordMessage.jsp";
            }

        }


        if(action.equals("Send Acknowledgement")){
            //System.out.println(action);
            int id = Integer.parseInt(request.getParameter("ticketno"));
            String doctor = request.getParameter("doctor");

            String iName[] = doctor.split("#");
            int docId = Integer.parseInt(iName[0].toString());

            String date = request.getParameter("date");
            String time = request.getParameter("time");
            String ack = iName[1].toString() + "," + iName[2].toString() + "," + date + "," + time;

            Ack a = new Ack(id, ack);

            try{
                //System.out.println(id + " # " + ack);
                boolean flag1 = tcs.setAcknow(a);
                boolean flag2 = tcs.updateStatus(id);
                boolean flag3 = tcs.updateDocStatus(docId);
                //System.out.println("Flag : " + flag);
                if(flag1 && flag2 && flag3){
                    target = "ackMessage.jsp";
                }
                else{
                    target = "notAckMessage.jsp";
                }
            }
            catch (Exception e) {
                System.out.println("Error : " + e.getMessage());
                target = "notAckMessage.jsp";
            }
        }

        System.out.println(target);

        RequestDispatcher rd = request.getRequestDispatcher(target);
        rd.forward(request, response);

        out.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
