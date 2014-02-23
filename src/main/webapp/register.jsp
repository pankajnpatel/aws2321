<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Signup</title>
        <link href="css/global.css" rel="stylesheet" type="text/css" />
    </head>
    <body>
        <div>
            <div class="header">
                <h2>Welcome to ONEUI</h2>
            </div>
            <form action="signup" method="POST">
                <div class="signup_box" style="min-height: 420px;">
                    <h1 class="heading">Register with:
                        <select name="clientapp" class="select">
                            <option value="MyopenIssues" selected>MyopenIssues</option>
                        </select>
                    </h1>
                    <h3 class="error"><%= request.getParameter("aws_error") != null ? request.getParameter("aws_error") : "" %></h3>
                    <ul>
                        <li>
                            <label>Firstname:</label>
                            <input type="text" id="firstname" name="firstname" tabindex="1" value="Pankaj" class="textbox" placeholder="Enter Firstname..."/>
                        </li>
                        <li>
                            <label>Lastname:</label>
                            <input type="text" id="lastname" name="lastname" tabindex="2" value="Patel" class="textbox" placeholder="Enter Lastname..."/>
                        </li>
                        <li>
                            <label>Username:</label>
                            <input type="text" id="display" name="display" tabindex="2" value="Pankaj Patel" class="textbox" placeholder="Enter Username..."/>
                        </li>
                        <li>
                            <label>Email:</label>
                            <input type="text" id="email" name="email" tabindex="3" class="textbox" value="pankajnpatel@yahoo.com" placeholder="Enter your email address..."/>
                        </li>
                        <li>
                            <label>Password:</label>
                            <input type="password" id="password" name="password" tabindex="4" class="textbox" value="Pankaj1" placeholder="Enter your password..."/>
                        </li>
                        <li>
                            <label>Conform Password:</label>
                            <input type="password" id="confirmation" name="confirmation" tabindex="5" class="textbox" value="Pankaj1" placeholder="Retype password..."/>
                        </li>
                        <li class="mt20 pt20">
                            <div class="float-left">
                                <input type="submit" value="Register" class="btn_green" name="btn_bogin"/>
                            </div>
                            <div class="clearfix"></div>
                        </li>
                    </ul>
                </div>
            </form>
            <div class="footer">
                &copy; 2014 aws2321
            </div>
        </div>
    </body>
</html>
