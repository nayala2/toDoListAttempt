<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<head>
    <link rel="stylesheet"
          href="style.css"/>

        <title>My To Do List App</title>

</head>
    <div class="container" >
        <div class="example"></div>
        <figure>
        <%--https://cdn.pixabay.com/photo/2015/05/31/15/07/coffee-792113_1280.jpg--%>
            <img src="coffee-792113_1280.jpeg" class="main-background">
            <div class="inner"></div>
            <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
            <script src="http://www.jqueryscript.net/demo/jQuery-Plugin-For-Water-Ripple-Animation-ripples/js/jquery.ripples.js"></script>

            <script>
                $(document).ready(function() {
                    $('.inner').ripples('show');
                });
            </script>

            <div class="videos-list">
                <div class="video">
                        <iframe class="giphy-embed" frameborder="0" src='https://giphy.com/embed/9bTjZrytydVRK' ></iframe>
                </div>
            </div>

            <div class="card-container">
                <div class="berries-image-card berries-perspective-right"></div>
            </div>

            <div class="card-container">
                <div class="leaves-image-card leaves-perspective-right"></div>
            </div>

            <div class="form-style-3" >
                <form action="hello-servlet" method ="get">
                    <figcaption>
                        <h1>My To Do List App</h1>
                            <p>Welcome! Where would you like to start?</p>
                            <fieldset><legend>TO DO ITEM</legend>
            <%--                    <label><span>Selection<span class="required">*</span></span><input type="text" name="userSelection"><br>--%>
                                    <label><span>Select One:</span><select name="userSelection" id="userSelection" onchange="getOption()">
                                        <option value="1">View all items on your list.</option>
                                        <option value="2">Add a new item.</option>
                                        <option value="3">Delete an item.</option>
                                    </select></label>
                                    <input type="submit" value="Go" onclick="getOption()"/> <!-- use `submit` instead of `button` -->
                            </fieldset>
                    </figcaption>
                </form>
            </div>
        </figure>
    </div>
</html>


