package com.softwaredevii.todolistattempt;

import java.io.*;
import java.util.List;

import javax.servlet.http.*;
import javax.servlet.annotation.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;
    String userSelection;
    String newTask;
    String[] tasks;
    Boolean quit;
    int counter = 0;
    SQLSingleton listEditor;

    static List items;

    private static final Logger logger = LogManager.getLogger(HelloServlet.class);


    public void init() {

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        userSelection = request.getParameter("userSelection");
        listEditor = SQLSingleton.getInstance();

        logger.debug("User Selection was made: " + userSelection);

        newTask = request.getParameter("newTask");
        logger.debug("Add new task entry was made: " + newTask);

        tasks = request.getParameterValues("deleteItem");

        if (tasks != null) {
            for (String task : tasks) {
                logger.debug("Item selected for deletion: " + task);
            }
        }


        response.setContentType("text/html");
        logger.debug("Initial User Selection Sorting...");

        if (newTask == null && tasks == null) {
            taskSorter(request, response);
            logger.debug("Routing to taskSorter()...");

        } else if (newTask != null){
            addNewTask(response);
            logger.debug("Routing to addNewTask()...");

        } else if( tasks != null ) {
            deleteTask(tasks, response);
            logger.debug("Routing to deleteTask()...");

        }
    }

    /**
     * Method that executes user selection.
     *
     * @throws IOException
     */
    public void taskSorter(HttpServletRequest request, HttpServletResponse response) throws IOException {

        if (userSelection.equals("1")) {
            logger.debug("View All Items selected...");
            viewAllItems(response);
        }
        if (userSelection.equals("2")) {
            logger.debug("Add item selected...");

            addItem(response);
        }
        if (userSelection.equals("3")) {
            deleteItem(request, response);
        }
        if (userSelection.equals("4")) {
            quit = true;
        }
    }

    /**
     * Method to view all tasks.
     */
    public void viewAllItems(HttpServletResponse response) throws IOException {

        listEditor.viewAll();

        PrintWriter out = response.getWriter();
        header(out);
        viewAllBody(out);
        counter++;
    }

    /**
     * Method to add new task.
     *
     * @throws IOException
     */
    public void addItem(HttpServletResponse response) throws IOException {

        counter++;

        // Hello
        PrintWriter out = response.getWriter();
        header(out);
        addItemBody(out);
    }

    public void addNewTask(HttpServletResponse response) throws IOException {
        logger.debug("Item to be entered: " + newTask);

        listEditor.addItemToList(newTask);
        logger.debug("completed addItem");

        viewAllItems(response);
    }

    /**
     * Method to delete existing.
     *
     * @throws IOException
     */
    public void deleteItem(HttpServletRequest request, HttpServletResponse response) throws IOException {

        System.out.println("Enter the number of the task to be deleted:\n");

        listEditor.viewAll();

        // Hello
        PrintWriter out = response.getWriter();
        header(out);
        deleteItemBody(out);
    }

    public void deleteTask(String[] tasks, HttpServletResponse response) throws IOException {
        System.out.println(tasks);

        System.out.println("Entered New Task");
        for(String task: tasks) {
            listEditor.deleteItemFromList(task);
            System.out.println("completed deletion");
        }

        viewAllItems(response);
        System.out.println("completed viewall");
    }

    public void header(PrintWriter out) {
        out.println("<html><body>");
        out.println("<head>\n" +
                "    <link rel=\"stylesheet\"\n" +
                "          href=\"style.css\"/>\n" +
                "    <script src=\"functions.js\"></script>\n" +
                "    <title>My To Do List App</title>\n" +
                "</head>" +
                "<div class=\"container\" >" +
        "<div class=\"example\"></div>" +
        "<figure>" +
        "    <img src=\"coffee-792113_1280.jpeg\" class=\"main-background\">" +
        "    <div class=\"inner\"></div>" +
        "    <script src=\"http://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js\"></script>" +
        "    <script src=\"http://www.jqueryscript.net/demo/jQuery-Plugin-For-Water-Ripple-Animation-ripples/js/jquery.ripples.js\">" +
        "    </script>" +
        "" +
        "   <script>" +
        "        $(document).ready(function() {" +
        "    $('.inner').ripples('show');" +
        "});" +
        "    </script>" +


                "<div class=\"videos-list\">" +
                "<div class=\"video\">" +
                 "       <iframe class=\"giphy-embed\" frameborder=\"0\" src='https://giphy.com/embed/9bTjZrytydVRK' ></iframe>" +
                "</div>" +
            "</div>" +

            "<div class=\"card-container\">" +
             "   <div class=\"berries-image-card berries-perspective-right\"></div>" +
            "</div>" +

            "<div class=\"card-container\">" +
             "   <div class=\"leaves-image-card leaves-perspective-right\"></div>" +
            "</div>   " +
        "    <div class=\"form-style-3\" >" +
        "        <form action=\"hello-servlet\" method =\"get\">" +
        "            <figcaption>" +
        "                <h1>My To Do List App</h1>");

    }

    public void viewAllBody(PrintWriter out) {
        out.println("                           <p>" + "Here's what's left on your list." + "</p>" +
                "                           <fieldset>");
        out.println("");

        for(Object toDoItems : items) {
            out.println("<li>" + toDoItems + "</li>");
        }
        out.println("                   </fieldset>" +
                "                    </figcaption>\n" +
                "            </div>\n" +
                "        <figure>\n" +
                "    </div>\n" +
                "</html>\n");
    }

    public void addItemBody(PrintWriter out) {

        out.println("<h2>" + "Add an item to your to do list." + "</h2>");
        out.println("<form action=\"hello-servlet\" method =\"get\">");
        out.println("Enter your new to do item: <input type=\"text\" name=\"newTask\"><br>");
        out.println("</form>");
        out.println("                    </figcaption>\n" +
                "            </div>\n" +
                "        <figure>\n" +
                "    </div>\n" +
                "</html>\n");
    }

    public void deleteItemBody(PrintWriter out) {

        out.println("<p>" + "Select items to delete." + "</p>");

        out.println("<form action=\"hello-servlet\" method =\"get\">\n" +
                "<fieldset>\n");
        int i = 1;

        for(Object toDoItems : items) {
            out.println("<div>\n");
            out.println("<label for=\"checkbox" + i + "\"\n>" );
            out.println("   <input id=\"checkbox" + i + "\" type=\"checkbox\" name=\"deleteItem\" value=\"" + toDoItems.toString() + "\">" + toDoItems);
            out.println("</label>\n");
            out.println("</div>\n");

            System.out.println("<input id=\"checkbox" + i + "\" type=\"checkbox\" name=\"deleteItem\" value=\"" + toDoItems + "\">  " + toDoItems);
            i++;
        }

        out.println("<div class=\"submit-button\"><button type=\"submit\">Submit form</button></div>");
        out.println("</fieldset>\n");
        out.println("</form>");
        out.println("                    </figcaption>\n" +
                "            </div>\n" +
                "        <figure>\n" +
                "    </div>\n" +
                "</html>\n");
    }

    public void destroy() {
    }
}