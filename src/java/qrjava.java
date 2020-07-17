/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;






/**
 *
 * @author tanvi
 */
@WebServlet(urlPatterns = {"/NewServlet"})
public class qrjava extends HttpServlet {
    
    Connection conn;
    Statement stmt;
    //String name,bg,en1,en2,en3,hins,alrgy;
    String url="jdbc:mysql://localhost:3306/trial";
    String username="root";
    String password="root";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet NewServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet NewServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        

        //processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
     
        
        String name = (String)request.getParameter("name");
        String bg = (String)request.getParameter("bg");
         String en1 = (String)request.getParameter("en1");
          String en2 = (String)request.getParameter("en2");
           String en3 = (String)request.getParameter("en3"); 
            String hins = (String)request.getParameter("hins");
             String alrgy = (String)request.getParameter("alrgy");
             //System.out.println("connected");
             
             
             try {
            Class.forName("com.mysql.jdbc.Driver");
            conn=DriverManager.getConnection(url,username,password);
            System.out.println("connected");
            //Statement stmt=conn.createStatement();
            //stmt=conn.createStatement();
            //String query="insert into client values('"+name+"','"+bg+"','"+en1+"','"+en2+"','"+en3+"','"+hins+"','"+alrgy+"');";
            //String query="insert into client " + " (name,bg,en1,en2,en3,hins,alrgy)" + " values (?, ?, ?, ?, ?, ?, ?)";
             String query="insert into client " + " values(?,?,?,?,?,?,?)";
            //stmt= conn.prepareStatement(query);
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2,bg);
            ps.setString(3, en1);
            ps.setString(4, en2);
            ps.setString(5, en3);
            ps.setString(6, hins);
            ps.setString(7, alrgy);
            
            ps.execute();
            
           /* stmt.setString(1, name);
            stmt.setString(2, bg);
            stmt.setString(3, en1);
            stmt.setString(4, en2);
            stmt.setString(5, en3);
            stmt.setString(6, hins);
            stmt.setString(7, alrgy);*/
            
            //stmt.execute(query);
            
            stmt.close();
conn.close();

                             
        } catch (Exception e) {
            e.printStackTrace();
        }
             
        String allcontent=" \n NAME : "+ name + " \n BLOOD GROUP : "+ bg + " \n EMERGENCY CONTACT NO.1 : " +en1+ " \n EMERGENCY CONTACT NO.2 : " +en2+ " \n EMERGENCY CONTACT NO.3 : " +en3+ 
                " \n HEALTH INSURANCE : " +hins+ " \n ALLERGIES : " +alrgy;
        
  ByteArrayOutputStream out = QRCode.from(allcontent).to(ImageType.PNG).withSize(300, 300).stream();
          //ByteArrayOutputStream out = QRCode.from(address).to(ImageType.PNG).withSize(300, 300).stream();
        response.setContentType("image/png");
        response.setContentLength(out.size());
          
        OutputStream os = response.getOutputStream();
        os.write(out.toByteArray());
         
        os.flush();
        os.close();
        
        
        
        }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
